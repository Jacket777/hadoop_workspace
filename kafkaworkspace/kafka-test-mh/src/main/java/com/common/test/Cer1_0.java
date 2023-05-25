package com.common.test;

import com.suning.kafka.client.consumer.KafkaMessageListener;
import com.suning.kafka.client.consumer.MessageRecord;
import com.suning.kafka.client.consumer.SnKafkaConsumer;
import com.suning.kafka.client.consumer.SnKafkaConsumerFactory;

import java.util.Properties;

public class Cer1_0 {

//	private static String topic = "mh_test_kfk10_ordinary_01";   //kafka 1.0 topic
//	private static String topic = "mh_test_kfk10_yiqi_01";   //kafka 1.0 topic yiqi
//	private static String group = "KFKADM_mh_test_kfk10_ordinary_01";
	private static String topic;
	private static String group;
	public static void main(String[] args) {
		topic=args[0];
		group=args[1];
		Properties properties = new Properties();
		properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.setProperty("group.id", group);
		properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.setProperty("auditor.type", "kafka");
		properties.setProperty("kafka.consumer.lazy.init", "false");
		properties.setProperty("kafka.cluster.version", "1.0");
		properties.setProperty("enable.dynamic.config",args[2]);
		properties.setProperty("enable.dynamic.consume.shadow.topics",args[3]);
//		properties.setProperty("client.secret", "C2CB2D6A774944CBAA565A25C9A20F41");
//		properties.setProperty("client.secret", args[2]);

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

class MessageListener implements KafkaMessageListener {

	@Override
	public void onMessage(SnKafkaConsumer snKafkaConsumer, MessageRecord messageRecord) {
		System.out.println(messageRecord.getTopic() + " : " + messageRecord.getPartition() + " : " + messageRecord.getOffset() +
				" : "+ messageRecord.getMessage().toString());
		System.out.println(messageRecord.getTopic() + " : " + this.hashCode());
	}
}
