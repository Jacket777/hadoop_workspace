package com.kafka.monitor.model;

/**
 * 穆加系统附件类
 *
 * @author 18061263
 * @create 2018-07-26
 * @link http://wiki.cnsuning.com/pages/viewpage.action?pageId=28007852
 * @since v1.0.0
 */
public class Attachments {
    private String phone_list;
    private String email_list;
    private String user_list;
    private String group_list;
    private String im_list;
    private String only_attachments;

    public String getPhone_list() {
        return phone_list;
    }

    public void setPhone_list(String phone_list) {
        this.phone_list = phone_list;
    }

    public String getEmail_list() {
        return email_list;
    }

    public void setEmail_list(String email_list) {
        this.email_list = email_list;
    }

    public String getUser_list() {
        return user_list;
    }

    public void setUser_list(String user_list) {
        this.user_list = user_list;
    }

    public String getGroup_list() {
        return group_list;
    }

    public void setGroup_list(String group_list) {
        this.group_list = group_list;
    }

    public String getIm_list() {
        return im_list;
    }

    public void setIm_list(String im_list) {
        this.im_list = im_list;
    }

    public String getOnly_attachments() {
        return only_attachments;
    }

    public void setOnly_attachments(String only_attachments) {
        this.only_attachments = only_attachments;
    }
}
