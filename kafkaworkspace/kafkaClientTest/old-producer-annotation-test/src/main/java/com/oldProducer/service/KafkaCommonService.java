package com.oldProducer.service;

import com.suning.kafka.client.producer.SnKafkaCommonProducer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17081290 on 2021/4/7.
 */
@Service
public class KafkaCommonService {
    @Autowired
    SnKafkaCommonProducer<String,String>producer;

    public void sendMessage(){
        System.out.println("开始生产信息......");
        producer.send("","Number01","this message A");
        System.out.println("生产信息结束......");
    }



    public void sendMsgWithMessageId(){
        System.out.println("开始生产信息......");
        producer.send("kafka20601","messageId01","key01","value01");
        System.out.println("生产信息结束......");
    }


    public void sendMsgWithHeader(){
        System.out.println("开始生产信息......");
        List<Header>headers = new ArrayList<>();
        headers.add(new RecordHeader("message-header","Send message with header info".getBytes()));
        producer.send("kafka20601","messageId02","key02","value02",headers);
        System.out.println("生产信息结束......");
    }


}
