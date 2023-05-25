package com.kafka.lab05;


import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

/*
 * 2.手动分配分区进行消费
 */
public class JConsumerAssign extends Thread{
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
		//创建一个消费者程序对象
		KafkaConsumer<String,String>consumer = new KafkaConsumer<String,String>(configure());
		//设置自定义分区
		TopicPartition tp = new TopicPartition("ip_login2",0);
		//手动分配分区索引值为0的分区
		consumer.assign(Collections.singleton(tp));
	    //实时消费标识
		boolean flag = true;
		while(flag) {
			//获取主题消息数据
			ConsumerRecords<String,String>records = consumer.poll(1000);
			for(ConsumerRecord<String,String>record:records) {
				System.out.printf("offset=%d key=%s, value=%s%n", record.offset(),record.key(),record.value());
			}	
		}
		consumer.close();	
	}

	public static void main(String[] args) {
		JConsumerAssign jconsumer = new JConsumerAssign();
		jconsumer.start();
	}

}
