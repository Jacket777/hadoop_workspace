package com.kafka.monitor.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 写入ES的TPS模型对象
 *
 * @author 18061263
 * @create 2019-04-16
 * @since v1.0.0
 */
public class ConsumeSpeedRecord implements Serializable {
    private float consumePerSec;
    private float producePerSec;
    //使用消费速率 - 生产速率，正值为瞬时无堆积，负值为瞬时有堆积
    private float diffPerSec;
    private String groupId;
    private String topicName;
    //数据时间区间
    private long startTime;
    private long endTime;
    //采集时间
    private long spiderTime;
    //总消息条数
    private long logSize;

    public float getConsumePerSec() {
        return consumePerSec;
    }

    public void setConsumePerSec(float consumePerSec) {
        this.consumePerSec = consumePerSec;
    }

    public float getProducePerSec() {
        return producePerSec;
    }

    public void setProducePerSec(float producePerSec) {
        this.producePerSec = producePerSec;
    }

    public float getDiffPerSec() {
        return diffPerSec;
    }

    public void setDiffPerSec(float diffPerSec) {
        this.diffPerSec = diffPerSec;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getSpiderTime() {
        return spiderTime;
    }

    public void setSpiderTime(long spiderTime) {
        this.spiderTime = spiderTime;
    }

    public long getLogSize() {
        return logSize;
    }

    public void setLogSize(long logSize) {
        this.logSize = logSize;
    }

    @Override
    public String toString() {
        return "ConsumeSpeedRecord{" +
                "consumePerSec=" + consumePerSec +
                ", producePerSec=" + producePerSec +
                ", diffPerSec=" + diffPerSec +
                ", groupId='" + groupId + '\'' +
                ", topicName='" + topicName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", spiderTime=" + spiderTime +
                ", logSize=" + logSize +
                '}';
    }
}
