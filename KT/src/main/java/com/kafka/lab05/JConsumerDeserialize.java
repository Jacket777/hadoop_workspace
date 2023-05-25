package com.kafka.lab05;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/*
 * 消费者程序
 */

public class JConsumerDeserialize extends Thread{
	/*
	 * 初始化kafka集群消息
	 */
	private Properties configure() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "master:9092,slave01:9092,slave02:9092");
		props.put("group.id", "testgroup01"); //指定消费组
		props.put("enable.auto.commit", "true");//开启自动提交
		props.put("auto.commit.interval.ms", "10000");//自动提交的时间间隔
		//反序列化消息主键
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//反序列化消息记录
		props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
		return props;	
	}
	
	@Override
	public void run() {
		KafkaConsumer<String,String>consumer = new KafkaConsumer<String,String>(configure());
		consumer.subscribe(Arrays.asList("ip_login2"));
		boolean flag = true;
		while(flag) {
			ConsumerRecords<String,String>records = consumer.poll(1000);
			for(ConsumerRecord<String,String>record:records) {
				System.out.printf("offset=%d key=%s, value=%s%n", record.offset(),record.key(),record.value());
			}	
		}
		consumer.close();	
	}
	
	

	public static void main(String[] args) {
		JConsumerDeserialize jconsumer = new JConsumerDeserialize();
		jconsumer.start();
	}

}
