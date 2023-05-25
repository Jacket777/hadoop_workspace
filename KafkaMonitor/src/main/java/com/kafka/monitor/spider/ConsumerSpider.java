package com.kafka.monitor.spider;

import com.kafka.monitor.model.ConsumerDetailInfo;
import com.kafka.monitor.model.PartitionDetailInfo;
import com.kafka.monitor.model.TopicInfo;
import com.kafka.monitor.model.TroubleType;
import com.kafka.monitor.notify.INotifier;
import com.kafka.monitor.utils.CookieHelper;
import com.kafka.monitor.utils.DateUtils;
import com.kafka.monitor.utils.TimeUnits;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 从kafka消费详情页面，过滤出owner、lag、lastSeen等信息
 *
 * @author 18061263
 * @create 2019-03-29
 * @since v1.0.0
 */
public class ConsumerSpider implements ISpider {
    private Log LOG = LogFactory.getLog(ConsumerSpider.class);
    private final INotifier notifier;
    private final int expiredDay;
    private boolean doubleCheck = true;
    private int maxLag = 1000;
    private int maxDelayMinutes = 30;
    private int maxSleep = 300000;
    private int maxTotalLag = 10000000;

    public ConsumerSpider(int expiredDay, INotifier notifier) {
        this.expiredDay = expiredDay;
        this.notifier = notifier;
    }

    @Override
    public void crawler(BasicCookieStore cookieStore, long startTimeInMill, long endTimeInMill, long execTimeInMill, List<Cookie> cookieList, List<TopicInfo> list) throws InterruptedException {
        Map<String, String> cookies = CookieHelper.retrieveCookies(cookieList);
        List<ConsumerDetailInfo> firstCheckResult = firstCheck(cookies, list);
        if (null != firstCheckResult &&
                !firstCheckResult.isEmpty() && doubleCheck) {
            //延迟5分钟之后再校验
            LOG.info("延时5分钟...");
            Thread.sleep(maxSleep);
            List<ConsumerDetailInfo> doubleCheckResult = doubleCheck(cookies, firstCheckResult);
            if (null != doubleCheckResult && !doubleCheckResult.isEmpty()) {
                notify(doubleCheckResult);
            } else {
                LOG.info("再次筛选之后，未发现Owner丢失、Owner Halt、消费大量堆积的问题");
            }
        } else {
            LOG.info("初步筛选之后，未发现Owner丢失、Owner Halt、消费大量堆积的问题");
        }
    }


    /**
     * 第一次校验，初步过滤出Owner丢失、Consumer Halt等场景的Topic列表
     *
     * @param cookies
     * @param allList
     * @return
     */
    private List<ConsumerDetailInfo> firstCheck(Map<String, String> cookies, List<TopicInfo> allList) {
        LOG.info("开始初步筛选...");
        List<ConsumerDetailInfo> resultList = new ArrayList<>(); //疑似问题列表
        for (TopicInfo topicInfo : allList) {
            try {
                String msg = String.format("first check %s owner,see %s", topicInfo.getGroupId(), topicInfo.getDetailUrl());
                LOG.info(msg);
                Document document = Jsoup.connect(topicInfo.getDetailUrl())
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                        .cookies(cookies)
                        .timeout(30000).get();
                Elements ownerTdElements = document.select(".table-head td:eq(5)");
                //没有消费记录的Topic，暂时被排除
                if (null == ownerTdElements || ownerTdElements.isEmpty()) {
                    continue;
                }

                ConsumerDetailInfo detailInfo = new ConsumerDetailInfo(topicInfo);
                detailInfo.setPartitionDetailInfoList(parseTable(topicInfo.getGroupId(), document));
                detailInfo.setTroubleType(checkTrouble(detailInfo));
                if (detailInfo.getTroubleType() == TroubleType.MISS_OWNER
                        || detailInfo.getTroubleType() == TroubleType.HALT_CONSUMER
                        || detailInfo.getTroubleType() == TroubleType.CONSUMER_SLOWING) {
                    resultList.add(detailInfo);
                }
            } catch (IOException e) {
                LOG.error(e);
            }
        }

        if (!resultList.isEmpty()) {
            LOG.info("[疑似存在问题的Topic列表]");
            printList(resultList);
        }
        LOG.info("结束初步筛选...");
        return resultList;
    }


    /**
     * 第二次校验，在第一步过滤出列表的基础上再进行过滤，
     * 确保因网络抖动、Kafka Rebalance等场景导致暂时Owner丢失、Consumer Halt等误报场景降低到最低
     *
     * @param cookies
     * @param suspectList 疑似问题列表
     * @return
     */
    private List<ConsumerDetailInfo> doubleCheck(Map<String, String> cookies, List<ConsumerDetailInfo> suspectList) {
        LOG.info("开始Double Check筛选...");
        List<ConsumerDetailInfo> resultList = new ArrayList<>(); //确定问题列表
        for (ConsumerDetailInfo itemInfo : suspectList) {
            try {
                String msg = String.format("double check %s owner,see %s", itemInfo.getGroupId(), itemInfo.getDetailUrl());
                LOG.info(msg);
                Document document = Jsoup.connect(itemInfo.getDetailUrl())
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                        .cookies(cookies)
                        .timeout(30000).get();
                Elements ownerTdElements = document.select(".table-head td:eq(5)");
                //没有消费记录的Topic，暂时被排除
                if (null == ownerTdElements || ownerTdElements.isEmpty()) {
                    continue;
                }

                ConsumerDetailInfo detailInfo = new ConsumerDetailInfo(itemInfo);
                detailInfo.setPartitionDetailInfoList(parseTable(itemInfo.getGroupId(), document));
                detailInfo.setTroubleType(checkTrouble(detailInfo));
                if (detailInfo.getTroubleType() == TroubleType.MISS_OWNER
                        || detailInfo.getTroubleType() == TroubleType.HALT_CONSUMER
                        || detailInfo.getTroubleType() == TroubleType.CONSUMER_SLOWING) {
                    resultList.add(detailInfo);
                }
            } catch (IOException e) {
                LOG.error(e);
            }
        }

        if (!resultList.isEmpty()) {
            LOG.info("[确认存在问题的Topic列表]");
            printList(resultList);
        }
        LOG.info("结束Double Check筛选...");
        return resultList;
    }

    private void printList(List<ConsumerDetailInfo> list) {

        List<ConsumerDetailInfo> missOwnerList = new ArrayList<ConsumerDetailInfo>();
        List<ConsumerDetailInfo> haltConsumerList = new ArrayList<ConsumerDetailInfo>();
        List<ConsumerDetailInfo> slowConsumerList = new ArrayList<ConsumerDetailInfo>();

        for (ConsumerDetailInfo consumerDetailInfo : list) {
            switch (consumerDetailInfo.getTroubleType()) {
                case MISS_OWNER:
                    consumerDetailInfo.caculate();
                    missOwnerList.add(consumerDetailInfo);
                    break;
                case HALT_CONSUMER:
                    consumerDetailInfo.caculate();
                    haltConsumerList.add(consumerDetailInfo);
                    break;
                case CONSUMER_SLOWING:
                    consumerDetailInfo.caculate();
                    slowConsumerList.add(consumerDetailInfo);
                    break;
                default:
                    break;
            }
        }

        Collections.sort(missOwnerList, new Comparator<ConsumerDetailInfo>() {

            @Override
            public int compare(ConsumerDetailInfo o1, ConsumerDetailInfo o2) {
                int flag = o2.getTotalLag().compareTo(o1.getTotalLag());
                return flag;
            }
        });

        Collections.sort(haltConsumerList, new Comparator<ConsumerDetailInfo>() {

            @Override
            public int compare(ConsumerDetailInfo o1, ConsumerDetailInfo o2) {
                int flag = o2.getTotalLag().compareTo(o1.getTotalLag());
                return flag;
            }
        });

        LOG.info("[Owner丢失列表]");
        for (ConsumerDetailInfo consumerDetailInfo : missOwnerList) {
            LOG.info(consumerDetailInfo.toString());
        }

        LOG.info("[消费暂停列表]");
        for (ConsumerDetailInfo consumerDetailInfo : haltConsumerList) {
            LOG.info(consumerDetailInfo.toString());
        }

        LOG.info("[消费缓慢列表]");
        for (ConsumerDetailInfo consumerDetailInfo : slowConsumerList) {
            LOG.info(consumerDetailInfo.toString());
        }
    }

    /**
     * 解析表格，提取出owner、lag、lastSeen等信息
     * 页面样式对应 http://kafka.cnsuning.com/consumer/5849/consumerDetail.htm
     *
     * @param document
     * @return
     */
    private List<PartitionDetailInfo> parseTable(String groupId, Document document) {
        if (doubleCheck) {
            LOG.info("[解析" + groupId + "  owner页面数据]");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<PartitionDetailInfo> list = new ArrayList<PartitionDetailInfo>();
        Elements trs = document.select("table#EventTable").select("tbody").select("tr");
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < trs.size() - 1; i++) {
            Elements tds = trs.get(i).select("td");
            PartitionDetailInfo info = new PartitionDetailInfo();
            buffer.setLength(0);//清空buffer
            for (int j = 0; j < tds.size(); j++) {
                String text = tds.get(j).text();
                buffer.append(text + "\t");
                switch (j) {
                    case 0:
                        if (!StringUtil.isBlank(text)) {
                            info.setTopic(text);
                        }
                        break;
                    case 1:
                        if (!StringUtil.isBlank(text)) {
                            info.setPartition(Integer.parseInt(text));
                        }
                        break;
                    case 2:
                        if (!StringUtil.isBlank(text)) {
                            info.setOffset(Long.parseLong(text));
                        }
                        break;
                    case 3:
                        if (!StringUtil.isBlank(text)) {
                            info.setLogSize(Long.parseLong(text));
                        }
                        break;
                    case 4:
                        if (!StringUtil.isBlank(text)) {
                            info.setLag(Long.parseLong(text));
                        }
                        break;
                    case 5:
                        if (StringUtil.isBlank(text)) {
                            info.setHasOwner(false);
                        } else {
                            info.setHasOwner(true);
                        }
                        break;
                    case 6:
                        if (!StringUtil.isBlank(text)) {
                            try {
                                Date createTime = dateFormat.parse(text);
                                info.setCreated(createTime);
                            } catch (ParseException e) {
                                LOG.error(e);
                            }
                        }
                        break;
                    case 7:
                        if (!StringUtil.isBlank(text)) {
                            try {
                                Date lastSeen = dateFormat.parse(text);
                                info.setLastSeen(lastSeen);
                            } catch (ParseException e) {
                                LOG.error(e);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            list.add(info);
            if (doubleCheck) {
                LOG.info(buffer.toString());
            }
        }
        return list;
    }

    private TroubleType checkTrouble(ConsumerDetailInfo detailInfo) {
        Date current = new Date();
        TroubleType type = TroubleType.NORMAL;
        Date newLastSeen = getNewLastSeen(detailInfo);
        boolean hasOwner = checkOwner(detailInfo);
        if (null != newLastSeen) {
            long diff = DateUtils.timeDiff(TimeUnits.DAY, newLastSeen, current);
            //有部分owner丢失，且last seen时间在指定范围内，则认定为owner丢失
            if (!hasOwner && diff <= expiredDay) {
                type = TroubleType.MISS_OWNER;
                return type;
            }
        }

        //owner未丢失，但是lastSeen与当前时间相比，以及大于10分钟，则认为消费Halt
        boolean isHalt = checkHaltConsumer(detailInfo);
        if (hasOwner && isHalt) {
            type = TroubleType.HALT_CONSUMER;
            return type;
        }

        //owner未丢失，lastSeen也符合要求，但是totalLag堆积很多，大于阈值，则认为消费缓慢
        detailInfo.caculate();
        long totalLag = detailInfo.getTotalLag();
        if (hasOwner && !isHalt && totalLag > maxTotalLag) {
            type = TroubleType.CONSUMER_SLOWING;
            return type;
        }

        return type;
    }

    /**
     * 校验owner是否部分丢失或者全部丢失
     *
     * @param detailInfo
     * @return false: 有owner丢失 true: 无owner丢失
     */
    private boolean checkOwner(ConsumerDetailInfo detailInfo) {
        if (null != detailInfo.getPartitionDetailInfoList() && detailInfo.getPartitionDetailInfoList().size() > 0) {
            //只要有部分owner丢失，则判定该Topic为Owner丢失
            for (PartitionDetailInfo partitionDetailInfo : detailInfo.getPartitionDetailInfoList()) {
                if (partitionDetailInfo.isHasOwner() == false) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取最靠近当前时间的Last Seen
     *
     * @param detailInfo
     * @return
     */
    private Date getNewLastSeen(ConsumerDetailInfo detailInfo) {
        if (null != detailInfo.getPartitionDetailInfoList() && detailInfo.getPartitionDetailInfoList().size() > 0) {
            //所有partition的lastSeen比较，取最后时间
            Collections.sort(detailInfo.getPartitionDetailInfoList(), new Comparator<PartitionDetailInfo>() {

                @Override
                public int compare(PartitionDetailInfo o1, PartitionDetailInfo o2) {
                    if (o1.getLastSeen() == null || o2.getLastSeen() == null) {
                        return 1;
                    } else {
                        return o2.getLastSeen().compareTo(o1.getLastSeen());
                    }
                }
            });
            return detailInfo.getPartitionDetailInfoList().get(0).getLastSeen();
        } else {
            return null;
        }
    }

    /**
     * 校验是否存在Partition Consumer Halt的情况
     * 校验规则是：某个partition lag大于maxLag 且 lastSeen时间与现在相差maxDelayMinutes分钟以上
     *
     * @param detailInfo
     * @return false:没有halt consumer, true:包含halt consumer
     */
    private boolean checkHaltConsumer(ConsumerDetailInfo detailInfo) {
        if (doubleCheck) {
            LOG.info("校验" + detailInfo.getGroupId() + "是否有消费暂停的partition");
        }
        Date current = new Date();
        List<PartitionDetailInfo> list = detailInfo.getPartitionDetailInfoList();
        for (PartitionDetailInfo info : list) {
            if (doubleCheck) {
                LOG.info("[topicId:" + detailInfo.getTopicId() + ", partition:" + info.getPartition() + ", lag:" + info.getLag() + ", lastSeen:" + DateUtils.formatDateTime(info.getLastSeen()) + "]");
            }
            if (info.getLag() > maxLag) {
                Date lastSeen = info.getLastSeen();
                long diff = DateUtils.timeDiff(TimeUnits.MINUTE, lastSeen, current);
                if (diff >= maxDelayMinutes) {
                    return true;
                }
            }
        }
        return false;
    }

    private void notify(List<ConsumerDetailInfo> list) {
        if (!list.isEmpty()) {
            StringBuffer missOwnerBuffer = new StringBuffer();
            StringBuffer haltConsumerBuffer = new StringBuffer();
            StringBuffer slowConsumerBuffer = new StringBuffer();
            for (ConsumerDetailInfo detailInfo : list) {
                switch (detailInfo.getTroubleType()) {
                    case MISS_OWNER:
                        missOwnerBuffer.append(detailInfo.getGroupId() + ", ");
                        break;
                    case HALT_CONSUMER:
                        haltConsumerBuffer.append(detailInfo.getGroupId() + ", ");
                        break;
                    case CONSUMER_SLOWING:
                        slowConsumerBuffer.append(detailInfo.getGroupId() + ":" + detailInfo.getTotalLag() + ", ");
                        break;
                    default:
                        break;
                }
            }

            StringBuffer alertMsgBuffer = new StringBuffer();
            alertMsgBuffer.append("数据流项目,近" + expiredDay + "天");
            if (!StringUtil.isBlank(missOwnerBuffer.toString())) {
                alertMsgBuffer.append("丢失Owner[" + missOwnerBuffer.toString() + "]");
            }

            if (!StringUtil.isBlank(haltConsumerBuffer.toString())) {
                alertMsgBuffer.append("消费暂停[" + haltConsumerBuffer.toString() + "]");
            }

            if (!StringUtil.isBlank(slowConsumerBuffer.toString())) {
                alertMsgBuffer.append("消费堆积[" + slowConsumerBuffer.toString() + "]");
            }

            boolean flag = notifier.notify(alertMsgBuffer.toString());
            if (flag) {
                LOG.info("发送成功");
            }
        }
    }
}
