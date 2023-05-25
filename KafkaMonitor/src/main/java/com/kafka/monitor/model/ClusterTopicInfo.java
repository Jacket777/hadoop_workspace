package com.kafka.monitor.model;


public class ClusterTopicInfo {
    private int id;
    private String clusterName;
    private int jobId;
    private String user;
    private String type;
    private int tps;
    private String topic;
    private String groupId;


    public ClusterTopicInfo() {
    }

    public ClusterTopicInfo(int id, String clusterName, int jobId, String user, String type, int tps, String topic, String groupId) {
        this.id = id;
        this.clusterName = clusterName;
        this.jobId = jobId;
        this.user = user;
        this.type = type;
        this.tps = tps;
        this.topic = topic;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTps() {
        return tps;
    }

    public void setTps(int tps) {
        this.tps = tps;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;

    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
