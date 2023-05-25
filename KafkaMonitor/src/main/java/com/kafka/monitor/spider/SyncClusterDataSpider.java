package com.kafka.monitor.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kafka.monitor.model.ClusterTopicInfo;
import com.kafka.monitor.utils.HttpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author 18061263
 * @create 2019-06-10
 * @since v1.0.0
 */
public class SyncClusterDataSpider {
    private Log LOG = LogFactory.getLog(SyncClusterDataSpider.class);

    public void sync() {
        Properties properties = new Properties();
        InputStream in = SyncClusterDataSpider.class.getClassLoader().getResourceAsStream("cluster-host.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            LOG.error("解析cluster属性文件异常!", e);
        }

        Set<String> clusterNames = properties.stringPropertyNames();
        List<ClusterTopicInfo> topicInfoList = null;
        for (String clusterName : clusterNames) {
            String clusterVal = properties.getProperty(clusterName);
            String path = "http://" + clusterVal;
            String clusterKey = clusterVal.substring(0, clusterVal.indexOf(':'));
            topicInfoList = new ArrayList<ClusterTopicInfo>();
            for (int i = 1; i <= 1000; i++) {
                String clusterPath = "http://" + clusterVal + i;
                String response = HttpUtils.doGet(clusterPath, null);
                if (null != response && !response.equals("")) {
                    String groupId;
                    JSONObject var = JSON.parseObject(response);
                    String type = var.getString("type");
                    int jobId = var.getIntValue("id");
                    JSONObject prop = var.getJSONObject("props");
                    int tps = prop.getIntValue("tps");
                    if ("k2es".equals(type)) {
                        groupId = prop.getString("kafka.zkConsumer.groupId");
                    } else {
                        groupId = prop.getString("groupId");
                    }
                    String topic = prop.getString("kafka.topic");
                    ClusterTopicInfo topicInfo = new ClusterTopicInfo();
                    topicInfo.setJobId(jobId);
                    topicInfo.setGroupId(groupId);
                    topicInfo.setTopic(topic);
                    topicInfo.setTps(tps);
                    topicInfo.setClusterName(clusterKey);
                    topicInfoList.add(topicInfo);
                }
            }
        }

        if (null != topicInfoList && !topicInfoList.isEmpty()) {

        }
    }
}
