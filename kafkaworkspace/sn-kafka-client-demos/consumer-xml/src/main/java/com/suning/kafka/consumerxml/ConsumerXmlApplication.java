package com.suning.kafka.consumerxml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:/application-kafka-bean.xml"})
public class ConsumerXmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerXmlApplication.class, args);
    }

}
