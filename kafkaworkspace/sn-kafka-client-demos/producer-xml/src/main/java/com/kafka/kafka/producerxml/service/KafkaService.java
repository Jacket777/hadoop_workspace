package com.kafka.kafka.producerxml.service;

import com.suning.kafka.client.producer.SnKafkaCommonProducer;
import com.suning.kafka.client.producer.SnKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Claire(18044864) on 2021/2/2 19:28
 */

@Service
public class KafkaService {

    @Autowired
    private SnKafkaProducer<String, String> producer;

    @Autowired
    private SnKafkaCommonProducer<String, String> oldProducer;

    public void sendMessage() {
        producer.send("message key", "message");
    }

    private void sendMessageWithOldProducer() {
        oldProducer.send("kafka_client_demo_topic_1", "message key", "message");
    }







    //演示方法，应用时请忽略
    @PostConstruct
    private void startProduce() {
        new Thread(() -> {
            while (true) {
                sendMessage();
                //演示使用，
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                sendMessageWithOldProducer();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "old-producer-thread").start();
    }
}
