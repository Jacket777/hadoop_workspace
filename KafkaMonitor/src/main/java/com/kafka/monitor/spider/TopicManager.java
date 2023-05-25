package com.kafka.monitor.spider;

import com.kafka.monitor.model.TopicInfo;
import com.kafka.monitor.model.WhiteTopic;
import com.kafka.monitor.utils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.cookie.Cookie;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * 从http://kafka.cnsuning.com/consumer/list.htm页面拉取全量的Group ID列表
 * 从http://kafka.cnsuning.com/consumer/{indexId}/offset.htm页面拉取对应的topicId以及topicName
 * <p>
 * 全量的Group ID列表有效时间为30分钟
 *
 * @author 18061263
 * @create 2019-03-29
 * @since v1.0.0
 */
public class TopicManager {
    private Log LOG = LogFactory.getLog(TopicManager.class);

    private final int startPage = 1;

    private int maxPage;

    private int pageSize;

    private List<TopicInfo> cacheList;

    private List<String> whiteList;

    //更新时间
    private Date updateTime;

    private static final TopicManager TOPIC_INDEX_SPIDER = new TopicManager();


    private TopicManager() {
        updateTime = new Date();
        cacheList = new ArrayList<TopicInfo>();
        whiteList = new ArrayList<String>();
    }


    public static TopicManager getInstance() {
        return TOPIC_INDEX_SPIDER;
    }

    /**
     * 获取全量的Topic Index,该列表为只读列表
     *
     * @param cookieList
     * @return
     * @throws IOException
     */
    public synchronized List<TopicInfo> getTopicList(List<Cookie> cookieList, boolean isForceUpdate) throws IOException, SQLException {
        if (!isForceUpdate && !cacheList.isEmpty()) {
            LOG.info("获取缓存的Topic全量列表...");
            return Collections.unmodifiableList(cacheList);
        } else {
            LOG.info("强制更新Topic全量缓存...");
            updateTime = new Date();
            cacheList.clear();
            //从listUrl链接获取detailUrl、indexId、groupId等三个参数
            Map<String, String> cookies = CookieHelper.retrieveCookies(cookieList);
            for (int i = startPage; i <= maxPage; i++) {
                Map<String, String> formData = buildIndexFormData(i, pageSize);
                Document indexDocument = Jsoup.connect(Constants.TOPIC_LIST_URL)
                        .data(formData)
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                        .cookies(cookies)
                        .timeout(30000).post();
                List<TopicInfo> topicInfoSubList = parseIndexDocument(indexDocument);
                if (null != topicInfoSubList && !topicInfoSubList.isEmpty()) {
                    cacheList.addAll(topicInfoSubList);
                }
            }

            //从offsetUrl连接获取topicId、topicName等两个参数
            Iterator<TopicInfo> ite = cacheList.iterator();
            while (ite.hasNext()) {
                TopicInfo topicInfo = ite.next();
                String topicOffsetUrl = String.format(Constants.OFFSET_URL, topicInfo.getIndexId());
                LOG.debug("topicOffsetUrl:" + topicOffsetUrl);
                Document offsetDocument = Jsoup.connect(topicOffsetUrl)
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                        .cookies(cookies)
                        .timeout(30000).get();
                boolean isSuccessfully = parseOffsetDocument(topicInfo, offsetDocument);
                if (!isSuccessfully) {
                    //获取不到topicId、topicName则移除该topic, 因为该Topic是不合法的
                    ite.remove();
                }
            }
            LOG.info("Topic全量个数:" + cacheList.size());
            LOG.info("结束强制更新Topic全量缓存...");
            return Collections.unmodifiableList(cacheList);
        }
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 解析Topic列表文档
     *
     * @param doc
     * @return
     */
    private List<TopicInfo> parseIndexDocument(Document doc) {
        List<TopicInfo> result = new ArrayList<>();
        Elements elements = doc.body().select("tr a[href*='Detail.htm' title],a[title]");
        for (Element element : elements) {
            TopicInfo info = parseIndexItem(element);
            if (null != info && (info.getGroupId().toLowerCase().startsWith("bigdata") || info.getGroupId().toLowerCase().startsWith("bdes"))) {
                if (!whiteList.contains(info.getGroupId())) {
                    result.add(info);
                }
            }
        }
        return result;
    }

    /**
     * 解析Topic列表项
     *
     * @param element
     * @return
     */
    private TopicInfo parseIndexItem(Element element) {
        TopicInfo indexInfo = null;
        String url = element.attr("href");
        String detailUrl = "";
        if (StringUtil.isBlank(url) || url.indexOf("consumerDetail") < 0) {
            return null;
        }
        detailUrl = String.format(Constants.TOPIC_INDEX_TEMPLATE, url);
        if (detailUrl.indexOf("javascript") > -1 || url.indexOf("javascript") > -1) {
            return null;
        }
        int beginIndex = detailUrl.lastIndexOf("consumer/") + 9;
        int endIndex = detailUrl.lastIndexOf("/consumerDetail");
        String idValue = detailUrl.substring(beginIndex, endIndex);
        int id = Integer.parseInt(idValue);
        String groupId = element.text();
        indexInfo = new TopicInfo(detailUrl, id, groupId);
        return indexInfo;
    }


    /**
     * 构造Topic列表页面的表单参数
     *
     * <p>
     * code:xWnkliVQJURqB2x1
     * grant_type:authorization_code
     * redirect_uri:https://www.getpostman.com/oauth2/callback
     * client_id:abc123
     * client_secret:ssh-secret
     * selfData:1
     * pageNo:1
     * pageSize:1000
     * </p>
     *
     * @param index
     * @param pageSize
     * @return
     */
    private Map<String, String> buildIndexFormData(int index, int pageSize) {
        Map<String, String> formData = new HashMap<>();
        formData.put("selfData", "1");
        formData.put("pageNo", String.valueOf(index));
        formData.put("pageSize", String.valueOf(pageSize));
        formData.put("code", "xWnkliVQJURqB2x1");
        formData.put("grant_type", "authorization_code");
        formData.put("client_id", "abc123");
        formData.put("client_secret", "ssh-secret");
        return formData;
    }

    /**
     * 解析offset页面，获取topicId、topicName等参数
     *
     * @param topicInfo
     * @param doc
     * @return
     */
    private boolean parseOffsetDocument(TopicInfo topicInfo, Document doc) {
        boolean isSuccessfully = false;
        Elements selectElements = doc.select("select#topicId");
        if (selectElements.size() >= 1) {
            Elements optionElements = selectElements.get(0).select("option");
            int elementSize = optionElements.size();
            if (elementSize >= 1) {
                Element optionEle = optionElements.get(elementSize - 1);
                String topicName = optionEle.text();
                String topicIdValue = optionEle.attr("value");
                if (!topicInfo.getGroupId().contains(topicName)) {
                    LOG.debug("groupId:" + topicInfo.getGroupId() + "/topic:" + topicName + " 规则不一");
                }
                if (StringUtil.isNumeric(topicIdValue)) {
                    topicInfo.setTopicId(Integer.parseInt(topicIdValue));
                    topicInfo.setTopicName(topicName);
                    isSuccessfully = true;
                }
            } else {
                LOG.info(topicInfo.getGroupId() + "获取offset页面，Topic ID下拉列表为空");
                isSuccessfully = false;
            }
        } else {
            LOG.info(topicInfo.getGroupId() + "获取offset页面，Topic ID下拉列表元素不存在");
            isSuccessfully = false;
        }

        return isSuccessfully;
    }
}
