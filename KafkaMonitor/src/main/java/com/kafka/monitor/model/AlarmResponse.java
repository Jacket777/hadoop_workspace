package com.kafka.monitor.model;

/**
 * 穆加系统告警服务Response
 *
 * @author 18061263
 * @create 2018-07-26
 * @since v1.0.0
 */
public class AlarmResponse {
    private Integer success;
    private String errorMessage;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
