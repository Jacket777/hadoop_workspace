package com.common.test;

import com.suning.kafka.client.producer.SnKafkaCommonProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;

public class Per0_8 {
//	private static String topic = "mh_test_yiqi_01";   //kafka 0.8 firsttopic
//	private static String topic = "mh_test_kfk08_ordinary_njxz_01"; //kafka 0.8 ordinarytopic
//	private static String topic = "mh_test_ordinary_01_bei"; //kafka 0.8 firsttopic bei

	private static String topic;
	public static void main(String[] args) {
		topic=args[0];
		Properties properties = new Properties();
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
