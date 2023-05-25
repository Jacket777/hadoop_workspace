package com.common.test;

import com.suning.kafka.client.consumer.SnKafkaConsumerFactory;

import java.util.Properties;

public class Cer1_0_NJXZ {

	private static String topic = "test_mh_zkadmin_njxz_pt_02";   //kafka 1.0 topic
	private static String group = "KYUBI_test_mh_putong_yuhua_01";
//private static String group = "KFKADM_mh_test_ordinary_01";

	public static void main(String[] args) {

		Properties properties = new Properties();
		properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.setProperty("group.id", group);
		properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.setProperty("auditor.type", "kafka");
		properties.setProperty("kafka.consumer.lazy.init", "false");
		properties.setProperty("kafka.cluster.version", "1.0");
		properties.setProperty("enable.dynamic.config", "true");
		properties.setProperty("enable.dynamic.consume.shadow.topics", "false");
//		properties.setProperty("client.secret", "C2CB2D6A774944CBAA565A25C9A20F41");

		MessageListener messageListener = new MessageListener();

		SnKafkaConsumerFactory snKafkaConsumerFactory = new SnKafkaConsumerFactory();
		snKafkaConsumerFactory.setTopic(topic);
		snKafkaConsumerFactory.setProperties(properties);
		snKafkaConsumerFactory.setListener(messageListener);
		snKafkaConsumerFactory.setParallelCount(3);
		try {
			snKafkaConsumerFactory.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

