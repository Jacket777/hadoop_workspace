package com.producer.annotation.config;

import com.suning.kafka.client.producer.SnKafkaCommonProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by 17081290 on 2021/4/7.
 *  SnKafkaCommonProducer配置
 */
@Configuration
public class KafkaCommonConfig {
    @Bean
    public SnKafkaCommonProducer<String,String>producer(){
        Properties properties = new Properties();
        //1.设置消息序列化
        properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        return new SnKafkaCommonProducer<>(Arrays.asList("kafka20601"),properties);
    }

}
