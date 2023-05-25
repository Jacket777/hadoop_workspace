package com.kafka.lab04.partition;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;


/*
 * 2.自定义分区类使用
 */
public class JProducerThread extends Thread{
	//创建日志对象
	private final Logger LOG = LoggerFactory.getLogger(JProducerThread.class);
	//设置最大线程数
	private final static int MAX_THREAD_SIZE=6;

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
		//指定自定义分区类
		props.put("partitioner.class", "com.kafka.lab04.partition.JPartitioner");
		return props;
	}

	public void run() {
		System.out.println("======>>>>========");
		Producer<String,String>producer = new KafkaProducer<String,String>(configure());
		for(int i =0; i<100;i++) {
			JSONObject json = new JSONObject();
			json.put("id", 1);
			json.put("ip", "192.168.0."+i);
			json.put("date",new Date().toString());
			String k = "key"+i;
			//异步发送，调用回调函数
			ProducerRecord <String,String>pr = new ProducerRecord<String,String>("ip_login2",k,json.toJSONString());
			producer.send(pr, new Callback() {
				public void onCompletion(RecordMetadata metadata, Exception e) {
					if(e!=null) {
						System.out.println("======AAAAAA============");
						LOG.error("Send error, msg is "+e.getMessage());
					}else {
						System.out.println("======BBBBB============"+metadata.offset());
						
						LOG.info("The offset of the record we just sent is "+metadata.offset());
					}	
				}	
			});
		}
		
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		producer.close();
	}
	
	
	public static void main(String[] args) {
		//创建一个固定线程数量的线程池
		ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_SIZE);
		//提交任务
		executorService.submit(new JProducerThread());
		//关闭线程池
		executorService.shutdown();
		
	}

}
