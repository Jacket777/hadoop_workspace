package com.oldProduce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:/application-kafka-bean.xml"})
@SpringBootApplication
public class OldProduceXmlApplication {
	public static void main(String[] args) {
		SpringApplication.run(OldProduceXmlApplication.class, args);
	}

}
