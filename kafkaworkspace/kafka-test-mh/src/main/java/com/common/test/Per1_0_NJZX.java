package com.common.test;

import com.suning.kafka.client.producer.SnKafkaCommonProducer;

import java.util.Properties;

public class Per1_0_NJZX {
	private static String topic = "test_mh_zkadmin_njxz_pt_02";

	public static void main(String[] args) {
//		topic=args[0];
		Properties properties = new Properties();
		properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.setProperty("auditor.type", "kafka");
		SnKafkaCommonProducer snKafkaCommonProducer = new SnKafkaCommonProducer(properties);
		Integer index = 0;
		while (true) {
			String msg = "Message5_" + index ++;
			snKafkaCommonProducer.send(topic, null, msg);
			System.out.println(msg);
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
