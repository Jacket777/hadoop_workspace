package com.kafka.monitor.model;


public class AdminUser {
    private final String id;
    private final String password2;

    public AdminUser(String id, String password2) {
        this.id = id;
        this.password2 = password2;
    }

    public String getId() {
        return id;
    }

    public String getPassword2() {
        return password2;
    }
}
