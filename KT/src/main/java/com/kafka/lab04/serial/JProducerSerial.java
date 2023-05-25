package com.kafka.lab04.serial;


import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.kafka.lab04.JProducer;

public class JProducerSerial extends Thread{
	    //创建日志对象
		private final Logger LOG = LoggerFactory.getLogger(JProducerSerial.class);
		
		//配置连接信息
		public Properties configure() {
			Properties  props = new Properties();
			//指定集群
			props.put("bootstrap.servers", "192.168.150.130:9092,192.168.150.131:9092,192.168.150.132:9092");
			props.put("acks", "1");//设置应答机制
			//props.put("retries", 0);
			props.put("batch.size", 16384);//批量提交大小
			props.put("linger.ms", 1);//延时提交
			props.put("buffer.memory", 33554432);//设置缓存大小
			//序列化主键
			props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			//序列化值
			props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			return props;
		}
		
		
		public void run() {
			System.out.println("======>>>>========");
			Producer<String,String>producer = new KafkaProducer<String,String>(configure());
			JSalarySerial jss = new JSalarySerial();
			jss.setId("2018");
			jss.setSalary("100");
			//异步发送，调用回调函数
			ProducerRecord pr = new ProducerRecord<String,JSalarySerial>("ip_login2","key",jss);
			producer.send(pr, new Callback() {
				public void onCompletion(RecordMetadata metadata, Exception e) {
					if(e!=null) {
						System.out.println("======AAAAAA======");
						LOG.error("Send error, msg is "+e.getMessage());
					}else {
						System.out.println("======BBBBB========="+metadata.offset());	
						LOG.info("The offset of the record we just sent is "+metadata.offset());
					}	
				}	
			});
			try {
				sleep(3000);
				} catch (InterruptedException e) {
				e.printStackTrace();
			}
			producer.close();
		}
		
		
		public static void main(String[] args) {
			JProducer producer = new JProducer();
			producer.start();	
		}

}
