package com.common.test;

import com.suning.kafka.client.producer.SnKafkaCommonProducer;
import java.util.Properties;

public class Per0_8_putong {
//	private static String topic = "mh_test_yiqi_01";   //kafka 0.8 firsttopic
	private static String topic = "test_mh_putong_yuhua_01"; //kafka 0.8 ordinarytopic
//	private static String topic = "mh_test_ordinary_01_bei"; //kafka 0.8 firsttopic bei


	public static void main(String[] args) {
		Properties properties = new Properties();
//		properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.setProperty("serializer.class", "kafka.serializer.StringEncoder");
		properties.setProperty("key.serializer.class", "kafka.serializer.StringEncoder");
		properties.setProperty("auditor.type", "kafka");
		SnKafkaCommonProducer snKafkaCommonProducer = new SnKafkaCommonProducer(properties);
		Integer index = 0;
		while (true) {
			String msg = "Message5_" + index++;
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
