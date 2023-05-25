package com.kafka.monitor.spider;

import com.kafka.monitor.ES.ESSink;
import com.kafka.monitor.model.*;
import com.kafka.monitor.utils.Constants;
//import com.kafka.monitor.utils.CookieHelper;
import com.kafka.monitor.utils.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.helper.StringUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 查询每个Topic的TPS，包含写入TPS和消费TPS
 *
 * @author 18061263
 * @create 2019-04-04
 * @since v1.0.0
 */
public class ConsumeSpeedSpider implements ISpider {
    private Log LOG = LogFactory.getLog(ConsumeSpeedSpider.class);

    private final ESSink sink;

    public static final int delay = -2;

    public static final int interval = -15;

    public ConsumeSpeedSpider(ESSink sink) {
        this.sink = sink;
    }

    public void crawler(BasicCookieStore cookieStore, long startTimeInMill, long endTimeInMill, long execTimeInMill, List<Cookie> cookieList, List<TopicInfo> list) {
        List<ConsumeSpeedRecord> resultList = new ArrayList<ConsumeSpeedRecord>();
        Date spiderTime = new Date(execTimeInMill);
        Date startTime = new Date(startTimeInMill);
        Date endTime = new Date(endTimeInMill);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.info(String.format("抓取时间 startTime:%s, endTime:%s, spiderTime:%s", dateFormat.format(startTime), dateFormat.format(endTime), dateFormat.format(spiderTime)));
        for (TopicInfo topicInfo : list) {
            try {
                ConsumeSpeedResponse response = crawlerSpeedInfo(cookieStore, topicInfo, startTime, endTime);
                if (null != response && null != response.getData()) {
                    ConsumeSpeedRecord record = calculate(topicInfo, response, startTime, endTime, spiderTime);
                    resultList.add(record);
                    LOG.info(record.toString());
                } else {
                    LOG.info(topicInfo.toString() + " response为空");
                }
            } catch (URISyntaxException e) {
                LOG.error(e);
            } catch (IOException e) {
                LOG.error(e);
            }
        }

        if (!resultList.isEmpty()) {
            boolean isSuccessfully = sink.put(record2Map(resultList));
        }
    }

    /**
     * POST请求,抓取每个Topic的tps,目前的查询规则是查询当前时间距离30分钟之前的均值
     * <p>
     * groupId: bigdata_ssa_mp_launchend_log
     * partitionNum: 3
     * topicName: ssa_mp_launchend_log
     * topicId: 3576
     * partition: 2147483647
     * time: 2019-04-15 17:52:28 to 2019-04-15 19:52:28
     * </p>
     */
    private ConsumeSpeedResponse crawlerSpeedInfo(BasicCookieStore cookieStore, TopicInfo topicInfo, Date startTime, Date endTime) throws URISyntaxException, IOException {
        ConsumeSpeedResponse consumeSpeedResponse = null;
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore).build();
        StringBuffer buffer = new StringBuffer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeVal = dateFormat.format(startTime);
        String endTimeVal = dateFormat.format(endTime);
        buffer.append(startTimeVal).append(" to ").append(endTimeVal);
        HttpUriRequest postRequest = RequestBuilder.post()
                .setUri(new URI(Constants.SPEED_TEMPLATE))
                .addParameter("groupId", topicInfo.getGroupId())
                .addParameter("partitionNum", "3")
                .addParameter("topicName", topicInfo.getTopicName())
                .addParameter("topicId", String.valueOf(topicInfo.getTopicId()))
                .addParameter("partition", "2147483647")
                .addParameter("time", buffer.toString())
                .build();
        HttpEntityEnclosingRequestBase inerT = (HttpEntityEnclosingRequestBase) postRequest;
        HttpEntity entity = inerT.getEntity();
        LOG.info("request params: " + EntityUtils.toString(entity));
        CloseableHttpResponse response = httpclient.execute(postRequest);
        String responseValue = EntityUtils.toString(response.getEntity(),
                StandardCharsets.UTF_8);
        if (!StringUtil.isBlank(responseValue)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(responseValue);
            }
            consumeSpeedResponse = JsonUtil.json2Bean(responseValue, ConsumeSpeedResponse.class);
        }
        return consumeSpeedResponse;
    }


    /**
     * 逻辑计算，将ConsumeSpeedResponse转换为ConsumeSpeedRecord
     *
     * @param topicInfo
     * @param response
     * @param startTime
     * @param endTime
     * @return
     */
    private ConsumeSpeedRecord calculate(TopicInfo topicInfo, ConsumeSpeedResponse response, Date startTime, Date endTime, Date spiderTime) {
        if (null == response || null == response.getData()) {
            return null;
        }

        ConsumeSpeedData speedData = response.getData();
        float consumePerSec = !StringUtil.isBlank(speedData.getConsumePerSec()) ? Float.parseFloat(speedData.getConsumePerSec()) : 0f;
        float producePerSec = !StringUtil.isBlank(speedData.getProducePerSec()) ? Float.parseFloat(speedData.getProducePerSec()) : 0f;
        float diffPerSec = consumePerSec - producePerSec;
        ConsumeSpeedRecord record = new ConsumeSpeedRecord();
        record.setConsumePerSec(consumePerSec);
        record.setProducePerSec(producePerSec);
        record.setDiffPerSec(diffPerSec);
        record.setGroupId(topicInfo.getGroupId());
        record.setTopicName(topicInfo.getTopicName());
        record.setStartTime(startTime.getTime());
        record.setEndTime(endTime.getTime());
        record.setSpiderTime(spiderTime.getTime());

        //获取logSize最大的值为当前的logSize值,兼容处理sit和prd环境不一致的问题
        long currentLogSize = 0;
        if (null != speedData.getDataList() && !speedData.getDataList().isEmpty() && speedData.getDataList().size() > 0) {
            DataWrapper dataWrapper = speedData.getDataList().get(0);
            List<OffsetSizeInfo> offsetList = dataWrapper.getOffsetList();
            List<LogSizeInfo> logSizeList = dataWrapper.getLogSizeList();
            if (null != offsetList && !offsetList.isEmpty()) {
                for (OffsetSizeInfo OffsetSizeInfo : offsetList) {
                    if (OffsetSizeInfo.getLogSize() > currentLogSize) {
                        currentLogSize = OffsetSizeInfo.getLogSize();
                    }
                }
            }

            if (null != logSizeList && !logSizeList.isEmpty()) {
                for (LogSizeInfo logSizeInfo : logSizeList) {
                    if (logSizeInfo.getLogSize() > currentLogSize) {
                        currentLogSize = logSizeInfo.getLogSize();
                    }
                }
            }
        }
        record.setLogSize(currentLogSize);
        return record;
    }

    private List<Map> record2Map(List<ConsumeSpeedRecord> record) {
        List<Map> result = new ArrayList<>();
        for (ConsumeSpeedRecord item : record) {
            Map<String, Object> ret = new HashMap<String, Object>();
            ret.put("consumePerSec", item.getConsumePerSec());
            ret.put("producePerSec", item.getProducePerSec());
            ret.put("diffPerSec", item.getDiffPerSec());
            ret.put("groupId", item.getGroupId());
            ret.put("topicName", item.getTopicName());
            ret.put("startTime", item.getStartTime());
            ret.put("endTime", item.getEndTime());
            ret.put("spiderTime", item.getSpiderTime());
            ret.put("logSize", item.getLogSize());
            result.add(ret);
        }
        return result;
    }
}
