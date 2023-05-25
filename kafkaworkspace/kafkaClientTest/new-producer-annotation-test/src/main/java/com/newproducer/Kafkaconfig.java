package com.newproducer;

import com.suning.kafka.client.producer.SnKafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by 17081290 on 2021/4/7.
 */
@Configuration
public class Kafkaconfig {
    @Bean
    public SnKafkaProducer<String,String> producer(){
        Properties properties = new Properties();
        //1.设置消息序列化
        properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //2.设置topic
        SnKafkaProducer<String,String>producer =  new SnKafkaProducer< >("kafka20601",properties);
        return producer;
    }

}
