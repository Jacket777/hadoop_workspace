package com.kafka.monitor.model;

/**
 * @author 18061263
 * @create 2019-04-03
 * @since v1.0.0
 */
public enum TroubleType {
    NORMAL(0), //正常
    MISS_OWNER(1),  //owner丢失
    HALT_CONSUMER(2), //consumer halt停止消费
    CONSUMER_SLOWING(3); //消费堆积

    int type;

    TroubleType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
