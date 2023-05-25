package com.suning.kafka.producerannotation.config;

import com.suning.kafka.client.producer.SnKafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by Claire(18044864) on 2021/2/2 19:48
 */

@Configuration
public class KafkaConfig {

    @Bean
    public SnKafkaProducer<String, String> producer() {
        Properties properties = new Properties();
        properties.setProperty("property-name", "property-value");
        return new SnKafkaProducer<>("kafka_client_demo_topic_1", properties);
    }
}
