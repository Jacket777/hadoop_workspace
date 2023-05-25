package com.kafka.lab05;

import java.util.List;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JConsumerMutil {
	private final static Logger LOG = LoggerFactory.getLogger(JConsumerMutil.class);
	private KafkaConsumer<String,String>consumer;
	private ExecutorService executorService;
	
	
	
	public JConsumerMutil() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "master:9092,slave01:9092,slave02:9092");
		props.put("group.id", "testgroup01"); //指定消费组
		props.put("enable.auto.commit", "true");//开启自动提交
		props.put("auto.commit.interval.ms", "10000");//自动提交的时间间隔
		//反序列化消息主键
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//反序列化消息记录
		props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
		consumer=new KafkaConsumer<String,String>(props);
		consumer.subscribe(Arrays.asList("ip_login2"));
		
		
	}
	
	
	/*执行多线程消费者程序*/
	public void execute() {
		//初始化线程池
		executorService = Executors.newFixedThreadPool(6);
		while(true) {
			ConsumerRecords<String,String>records = consumer.poll(100);
			if(null!=records) {
				executorService.submit(new KafkaConsumerThread(records,consumer));
			}	
		}	
	}
	
	
	/*关闭消费者程序对象和线程池*/
	public void shutdown() {
		if(consumer !=null) {
			consumer.close();
		}
		if(executorService!=null) {
			executorService.shutdown();
		}
		try {
			if(!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
				LOG.error("Shutdown kafka consumer thread timeout");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/*消费者线程实例*/
	class KafkaConsumerThread implements Runnable{
		private ConsumerRecords<String,String>records;
		
		public KafkaConsumerThread(ConsumerRecords<String,String>records,
				KafkaConsumer<String,String>consumer) {
			this.records = records;	
		}
		

		public void run() {
			for(TopicPartition partition:records.partitions()) {
				//获取消费记录数据集
				List<ConsumerRecord<String,String>>partitionRecords = records.records(partition);
				//监控当前线程ID
				LOG.info("Thread id :"+Thread.currentThread().getId());
				//打印消费记录
				for(ConsumerRecord<String,String>record:partitionRecords) {
					System.out.printf("offset=%d key=%s, value=%s%n", record.offset(),record.key(),record.value());
				}
			}	
		}
		
		
	}
	

	/*多线程消费程序入口*/
	public static void main(String[] args) {
		JConsumerMutil consumer = new JConsumerMutil();
		try {
			consumer.execute();
		}catch(Exception e) {
			LOG.error("Mutil consumer from kafka has error, msg is"+e.getMessage());
			consumer.shutdown();
		}

	}

}
