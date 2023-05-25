package com.producer.annotation.service;

import com.suning.kafka.client.producer.SnKafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by 17081290 on 2021/4/7.
 */
//@Service
//public class kafkaService {
//    @Autowired
//    SnKafkaProducer<String,String>producer;
//
//
//   // @PostConstruct
//    public void sendMsg(){
//        System.out.println("开始生产消息");
//        Future<RecordMetadata> result = producer.send("No1", "message a 01");
//        System.out.println("发送结束");
//    }
//
//
//    /**
//     * 发送到指定分区
//     */
//    public void sendMsgWithPartition(){
//        System.out.println("开始生产消息");
//        Future<RecordMetadata> result = producer.send(0,"No1", "message to partition 0");
//        System.out.println("发送结束");
//    }
//
//
//
//    public void sendMsgWithTimeStamp(){
//        System.out.println("开始生产消息");
//        long tiemstamp = System.currentTimeMillis();
//        producer.send("timestamp",Long.toString(tiemstamp));
//        System.out.println("发送结束");
//    }
//
//
//    /**
//     * 带有头信息的消息发送
//     */
//    public void sendMsgWithHeaderInfo(){
//        System.out.println("开始生产消息");
//        List<Header> headers = new ArrayList<>();
//        headers.add(new RecordHeader("message-header","Send message with header info".getBytes()));
//        Future<RecordMetadata> result = producer.send("No1", "message with header 222",headers);
//        System.out.println("发送结束");
//    }
//
//
//    /**
//     * 发送record
//     */
//    public void sendMsgWithRecord(){
//        System.out.println("开始生产消息");
//        ProducerRecord<String,String>record = new ProducerRecord<String, String>("kafka20601", "record", "record message");
//        producer.send(record);
//        System.out.println("发送结束");
//    }
//
//
//    /**
//     * 带回调的生产
//     */
//    public void sendMsgWithCallBack(){
//        ProducerRecord<String,String>record = new ProducerRecord<String, String>("kafka20601","record", "record with call back");
//        producer.send(record,new SendCallBack(System.currentTimeMillis(),1,"message a 01"));
//        try {
//            TimeUnit.SECONDS.sleep(30);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("发送结束");
//    }
//
//
//    /**
//     * 睡眠模式
//     */
//
//    public void setSleepMode(){
//        //在属性中设置
//    }
//
//
//    public void getMetrics(){
//        System.out.println("开始生产消息");
//        Future<RecordMetadata> result = producer.send("N001", "message a 0001");
//        Map<MetricName, ? extends Metric> metrics = producer.metrics();
//        for (Map.Entry<MetricName, ? extends Metric> entry : metrics.entrySet()) {
//            System.out.println(entry.getKey().name() + " : " + entry.getValue().metricValue());
//        }
//    }
//}