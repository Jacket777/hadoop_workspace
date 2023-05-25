package com.suning.kafka.producerannotation.service;

import com.suning.kafka.client.producer.SnKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Claire(18044864) on 2021/2/2 19:54
 */

@Service
public class KafkaService {
    @Autowired
    SnKafkaProducer<String, String> producer;

    public void sendMessage() {
        System.out.println("==========>>>>>======");
        producer.send("message key", "message");
        System.out.println("==========^^^^^======");
    }








//    //演示方法，应用时请忽略
//    @PostConstruct
//    private void startProduce() {
//        new Thread(() -> {
//            while (true) {
//                sendMessage();
//                //演示使用，
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
}
