package com.oldProducer;

import com.oldProducer.service.KafkaCommonService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OldProducerApplicationTests {

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
