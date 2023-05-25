package com.producer;

import com.suning.kafka.client.producer.SnKafkaCommonProducer;
import com.suning.kafka.client.producer.SnKafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by 17081290 on 2021/4/30.
 */
@Configuration
public class KafkaConfig {
    @Bean
    public SnKafkaCommonProducer<String, String>producer(){
        Properties properties = new Properties();
        properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //徐庄
        //SnKafkaCommonProducer<String,String>producer = new SnKafkaCommonProducer<>(Arrays.asList("kafka206_xuzhuang_maintopic01"),properties);
        //雨花
        SnKafkaCommonProducer<String,String>producer = new SnKafkaCommonProducer<>(Arrays.asList(TopicName.topicName),properties);
        return producer;
    }
}
