package com.kafka.monitor.model;

/**
 * @author 18061263
 * @create 2019-03-29
 * @since v1.0.0
 */
public class TopicInfo {
    protected String detailUrl;
    protected int indexId;
    protected String groupId;
    protected int topicId = -1;
    protected String topicName;

    public TopicInfo(String detailUrl, int indexId, String groupId) {
        this.detailUrl = detailUrl;
        this.indexId = indexId;
        this.groupId = groupId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public int getIndexId() {
        return indexId;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        return "TopicInfo{" +
                "detailUrl='" + detailUrl + '\'' +
                ", indexId=" + indexId +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
