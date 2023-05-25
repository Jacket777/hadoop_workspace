package com.consumer.annotation.config;

import com.consumer.annotation.listener.MyListener;
import com.suning.kafka.client.consumer.SnKafkaConsumerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by 17081290 on 2021/4/8.
 */
//@Configuration
//public class KafkaFactoryConfig {
//    @Bean
//    public SnKafkaConsumerFactory<String,String>consumerFactory(){
//        Properties properties = new Properties();
//        properties.setProperty("group.id","testG2");
//        SnKafkaConsumerFactory<String, String> consumerFactory = new SnKafkaConsumerFactory<>();
//        consumerFactory.setTopic("kafka20601");
//        consumerFactory.setListener(new MyListener());
//        return consumerFactory;
//    }
//
//
//}