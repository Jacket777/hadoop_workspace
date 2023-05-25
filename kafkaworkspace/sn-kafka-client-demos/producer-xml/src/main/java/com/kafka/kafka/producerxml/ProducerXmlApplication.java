package com.kafka.kafka.producerxml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:/application-kafka-bean.xml"})
public class ProducerXmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerXmlApplication.class, args);
	}

}
