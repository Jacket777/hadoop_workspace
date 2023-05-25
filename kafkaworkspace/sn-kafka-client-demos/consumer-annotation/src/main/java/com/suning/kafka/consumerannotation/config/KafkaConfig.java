package com.suning.kafka.consumerannotation.config;

import com.suning.kafka.client.consumer.SnKafkaConsumer;
import com.suning.kafka.consumerannotation.service.MysqlSinkMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by Claire(18044864) on 2021/2/3 10:17
 */

@Configuration
public class KafkaConfig {
    @Bean
    public SnKafkaConsumer<String, String> consumer() {
        Properties properties = new Properties();
        properties.setProperty("group.id", "claire_consumer");
        SnKafkaConsumer<String, String> consumer = new SnKafkaConsumer<>("kafka_client_demo_topic_1", properties);
        consumer.setListener(new MysqlSinkMessageListener());
        consumer.start();
        return consumer;
    }
}
