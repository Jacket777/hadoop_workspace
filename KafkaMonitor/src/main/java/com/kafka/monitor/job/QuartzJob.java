package com.kafka.monitor.job;

import com.kafka.monitor.model.TopicInfo;
import com.kafka.monitor.model.WhiteTopic;
import com.kafka.monitor.utils.Constants;
import com.kafka.monitor.utils.DBUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;

import java.sql.SQLException;
import java.util.*;

/**
 * @author 18061263
 * @create 2019-06-17
 * @since v1.0.0
 */
public abstract class QuartzJob implements Job {

    private Log LOG = LogFactory.getLog(QuartzJob.class);

    public List<TopicInfo> filterWhiteList(List<TopicInfo> allList) throws SQLException {
        List<TopicInfo> targetList = new ArrayList<>();
        targetList.addAll(allList);
        Map<String, WhiteTopic> whiteMap = new HashMap<String, WhiteTopic>();
        DBUtils dbUtils = new DBUtils(Constants.DRIVER, Constants.DB_URL, Constants.DB_USER_NAME, Constants.DB_PASSWORD);
        if (!dbUtils.test()) {
            LOG.info("数据库连接不通");
        } else {
            List<WhiteTopic> whiteTopicList = dbUtils.queryWhiteTopicList();
            for (WhiteTopic whiteTopic : whiteTopicList) {
                whiteMap.put(whiteTopic.getGroupId().toLowerCase(), whiteTopic);
            }
            dbUtils.closeConnection();
        }

        LOG.info(String.format("before filter, all topic size %s", targetList.size()));
        Iterator<TopicInfo> iterator = targetList.iterator();
        while (iterator.hasNext()) {
            TopicInfo topicInfo = iterator.next();
            if (whiteMap.containsKey(topicInfo.getGroupId().toLowerCase())) {
                LOG.info("filter:" + topicInfo.toString());
                iterator.remove();
            }
        }
        LOG.info(String.format("after filter, all topic size %s", targetList.size()));
        return targetList;
    }

    abstract void process(final Date startTime, final Date endTime, final Date execTime);
}
