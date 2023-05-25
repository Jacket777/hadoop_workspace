package test;


import com.suning.kafka.client.consumer.SnKafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by 17081290 on 2021/4/8.
 */
@Configuration
public class KafkaConfig {
    @Bean
    public SnKafkaConsumer<String,String>consumer(){
        Properties properties = new Properties();
        //徐庄--主topic
        properties.setProperty("group.id","BDMS_kafka206_yuhua_backtopic01");
        SnKafkaConsumer<String, String> consumer = new SnKafkaConsumer<>("kafka206_yuhua_backtopic01", properties);
        consumer.setListener(new MyMessageListener());
        consumer.start();
        return consumer;
    }
}
