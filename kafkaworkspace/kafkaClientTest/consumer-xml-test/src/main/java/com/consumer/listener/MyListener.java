package com.consumer.listener;

import com.suning.kafka.client.consumer.KafkaMessageListener;
import com.suning.kafka.client.consumer.MessageRecord;
import com.suning.kafka.client.consumer.SnKafkaConsumer;

/**
 * Created by 17081290 on 2021/4/7.
 */
public class MyListener implements KafkaMessageListener<String,String> {

    @Override
    public void onMessage(SnKafkaConsumer<String, String> snKafkaConsumer, MessageRecord<String, String> messageRecord) {
       String message =  messageRecord.getMessage();
        System.out.println("message = "+message);
    }
}
