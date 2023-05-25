package com.kafka.monitor.model;

import java.io.Serializable;

/**
 * kafka消费者tps response模型对象
 *
 * @author 18061263
 * @create 2019-04-16
 * @since v1.0.0
 */
public class ConsumeSpeedResponse implements Serializable {
    private String code;
    private ConsumeSpeedData data;
    private String errorMessage;
    private String message;
    private boolean reqSuccess;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ConsumeSpeedData getData() {
        return data;
    }

    public void setData(ConsumeSpeedData data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isReqSuccess() {
        return reqSuccess;
    }

    public void setReqSuccess(boolean reqSuccess) {
        this.reqSuccess = reqSuccess;
    }

    @Override
    public String toString() {
        return "ConsumeSpeedResponse{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", errorMessage='" + errorMessage + '\'' +
                ", message='" + message + '\'' +
                ", reqSuccess=" + reqSuccess +
                '}';
    }
}
