package com.consumer.annotation.listener;

import com.suning.kafka.client.consumer.KafkaMessageListener;
import com.suning.kafka.client.consumer.MessageRecord;
import com.suning.kafka.client.consumer.SnKafkaConsumer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;

import java.util.Iterator;

/**
 * Created by 17081290 on 2021/4/7.
 */
public class MyMessageListener implements KafkaMessageListener {
    @Override
    public void onMessage(SnKafkaConsumer snKafkaConsumer, MessageRecord messageRecord) {
        String topic = messageRecord.getTopic();
        int partition = messageRecord.getPartition();
        long timestamp = messageRecord.getTimestamp();
        String msgkey = (String)messageRecord.getKey();
        String msg = (String)messageRecord.getMessage();
        System.out.println("消费信息的topic： "+topic+" 分区为："+partition+" 时间戳为: "+timestamp
                +" key = "+msgkey+" msg ="+msg);
        //处理头信息
        Headers headers = messageRecord.getHeaders();
        Iterator<Header> iterator = headers.iterator();
        System.out.println("header info......");
        while(iterator.hasNext()){
            System.out.println(" header key: "+iterator.next().key()+" header value: "+iterator.next().value().toString());
            System.out.println("=====下一个header 信息=======");
        }
    }
}
