package com.kafka.monitor.model;

/**

 */
public class LogSizeInfo {
    private int clusterId;
    private String createTime;
    private Integer id;
    private Long logSize;
    private Long partition;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getLogSize() {
        return logSize;
    }

    public void setLogSize(Long logSize) {
        this.logSize = logSize;
    }

    public Long getPartition() {
        return partition;
    }

    public void setPartition(Long partition) {
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
                ", id=" + id +
                ", logSize=" + logSize +
                ", partition=" + partition +
                ", topic='" + topic + '\'' +
                '}';
    }
}
