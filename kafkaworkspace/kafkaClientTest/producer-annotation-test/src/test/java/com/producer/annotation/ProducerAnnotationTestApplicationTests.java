package com.producer.annotation;

import com.producer.annotation.service.KafkaCommonService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@PropertySource("classpath:kafka.properties")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerAnnotationTestApplicationTests {


    //-------------------SnKafkaCommonProducer--API测试-----------------------------
	@Autowired
	KafkaCommonService commonService;


    /**
	 * 测试send(String topic,K k,V v)
	 */
	@Test
	public void test9(){
		System.out.println("测试用例9 .......");
		commonService.sendMessage();
	}



	/**
	 * 测试send(String topic,String messageId,K k,V v)
	 */
	@Test
	public void test10(){
		System.out.println("测试用例10 .......");
		commonService.sendMsgWithMessageId();
	}


	@Test
	public void test11(){
		System.out.println("测试用例11 .......");
		commonService.sendMsgWithHeader();
	}





}
