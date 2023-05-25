package com.kafka.monitor.model;

import java.io.Serializable;
import java.util.List;

/**
 * kafka消费者tps数据模型对象
 *

 */
public class ConsumeSpeedData implements Serializable {
    private List<DataWrapper> dataList;
    //消费速率 msg/s
    private String consumePerSec;
    //生产速率 msg/s
    private String producePerSec;
    private int showCode;

    public String getConsumePerSec() {
        return consumePerSec;
    }

    public void setConsumePerSec(String consumePerSec) {
        this.consumePerSec = consumePerSec;
    }

    public List<DataWrapper> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataWrapper> dataList) {
        this.dataList = dataList;
    }

    public String getProducePerSec() {
        return producePerSec;
    }

    public void setProducePerSec(String producePerSec) {
        this.producePerSec = producePerSec;
    }

    public int getShowCode() {
        return showCode;
    }

    public void setShowCode(int showCode) {
        this.showCode = showCode;
    }

    @Override
    public String toString() {
        return "ConsumeSpeedData{" +
                "consumePerSec='" + consumePerSec + '\'' +
                ", dataList=" + dataList +
                ", producePerSec='" + producePerSec + '\'' +
                ", showCode=" + showCode +
                '}';
    }
}
