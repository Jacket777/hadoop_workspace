package com.producer.annotation.service;

import com.suning.kafka.client.producer.SnKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by 17081290 on 2021/4/9.
 */

//@Service
//public class ProduceService {
//
//    @Autowired
//    private SnKafkaProducer<String, String> producer;
//
//    @PostConstruct
//    public void startProduce() {
//        while (true) {
//            System.out.println("=========AAAAA==================");
//            producer.send("s", "AAAAAAA");
//        }
//    }
//
//}
