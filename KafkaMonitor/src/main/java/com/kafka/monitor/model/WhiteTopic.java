package com.kafka.monitor.model;

import java.io.Serializable;

/**
 * @author 18061263
 * @create 2019-05-29
 * @since v1.0.0
 */
public class WhiteTopic implements Serializable {
    private int id;
    private String groupId;
    private String dc;

    public WhiteTopic(int id, String groupId, String dc) {
        this.id = id;
        this.groupId = groupId;
        this.dc = dc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }
}
