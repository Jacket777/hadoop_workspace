package com.newproducerXml;

import com.suning.kafka.client.producer.SnKafkaProducer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 17081290 on 2021/4/30.
 */
@Configuration
public class KafkaConfig {

//    @Bean
//    public SnKafkaProducer getProducer(){
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-kafka-bean.xml");
//        SnKafkaProducer bean = (SnKafkaProducer)applicationContext.getBean("snNewProduce");
//        return bean;
//    }
}
