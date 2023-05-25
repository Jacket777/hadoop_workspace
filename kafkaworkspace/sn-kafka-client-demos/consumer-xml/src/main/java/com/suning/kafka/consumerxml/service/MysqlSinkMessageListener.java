package com.suning.kafka.consumerxml.service;

import com.suning.kafka.client.consumer.KafkaMessageListener;
import com.suning.kafka.client.consumer.MessageRecord;
import com.suning.kafka.client.consumer.SnKafkaConsumer;

/**
 * Created by Claire(18044864) on 2021/2/3 9:27
 */
public class MysqlSinkMessageListener implements KafkaMessageListener<String, String> {
    @Override
    public void onMessage(SnKafkaConsumer<String, String> snKafkaConsumer, MessageRecord<String, String> messageRecord) {
        String message = messageRecord.getMessage();
        saveToMysql(message);
    }




    //演示代码
    private void saveToMysql(String message) {
        System.out.println("Save message: '" + message + "' into Mysql");
    }
}
