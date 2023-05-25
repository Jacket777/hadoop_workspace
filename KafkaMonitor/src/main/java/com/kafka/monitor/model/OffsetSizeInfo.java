package com.kafka.monitor.model;

import java.io.Serializable;

/**
 * @author 18061263
 * @create 2019-04-16
 * @since v1.0.0
 */
public class OffsetSizeInfo implements Serializable {
    private int clusterId;
    private String createTime;
    private String groupId;
    private String id;
    private Long lag;
    private Long logSize;
    private Long offset;
    private long partition;
    private String topic;

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getLag() {
        return lag;
    }

    public void setLag(Long lag) {
        this.lag = lag;
    }

    public Long getLogSize() {
        return logSize;
    }

    public void setLogSize(Long logSize) {
        this.logSize = logSize;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public long getPartition() {
        return partition;
    }

    public void setPartition(long partition) {
        this.partition = partition;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "LogSizeInfo{" +
                "clusterId=" + clusterId +
                ", createTime='" + createTime + '\'' +
                ", groupId='" + groupId + '\'' +
                ", id='" + id + '\'' +
                ", lag=" + lag +
                ", logSize=" + logSize +
                ", offset=" + offset +
                ", partition=" + partition +
                ", topic='" + topic + '\'' +
                '}';
    }
}
