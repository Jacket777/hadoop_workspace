package com.common.test;

import com.suning.kafka.client.consumer.KafkaMessageListener;
import com.suning.kafka.client.consumer.MessageRecord;
import com.suning.kafka.client.consumer.SnKafkaConsumer;
import com.suning.kafka.client.consumer.SnKafkaConsumerFactory;

import java.util.Properties;

public class Cer0_8 {

//	private static String topic = "mh_test_yiqi_01";  //kafka 0.8 firsttopic
//    private static String topic = "mh_test_kfk08_ordinary_njxz_01";  //kafka 0.8 ordinarytopic
//	private static String topic = "mh_test_ordinary_01_bei";
//	private static String group = "KFKADM_mh_test_ordinary_01";
	private static String topic;
	private static String group;
	public static void main(String[] args) {
		topic=args[0];
		group=args[1];
		Properties properties = new Properties();
		properties.setProperty("deserializer.class", "kafka.deserializer.StringDecoder");
		properties.setProperty("group.id", group);
		properties.setProperty("key.deserializer.class", "kafka.deserializer.StringDecoder");
		properties.setProperty("auditor.type", "kafka");
		properties.setProperty("kafka.consumer.lazy.init", "false");
		properties.setProperty("kafka.cluster.version", "0.8");
		properties.setProperty("enable.dynamic.config", args[2]);
		properties.setProperty("enable.dynamic.consume.shadow.topics", args[3]);
//		properties.setProperty("client.secret", args[2]);
		MessageListener0_8 messageListener = new MessageListener0_8();

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

class MessageListener0_8 implements KafkaMessageListener {

	@Override
	public void onMessage(SnKafkaConsumer snKafkaConsumer, MessageRecord messageRecord) {
		System.out.println(messageRecord.getTopic() + " : " + messageRecord.getPartition() + " : " + messageRecord.getOffset() +
				" : "+ messageRecord.getMessage().toString());
		System.out.println(messageRecord.getTopic() + " : " + this.hashCode());
	}
}