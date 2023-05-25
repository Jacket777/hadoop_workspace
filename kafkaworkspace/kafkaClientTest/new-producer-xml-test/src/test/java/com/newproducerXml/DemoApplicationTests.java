package com.newproducerXml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@PropertySource("classpath:kafka.properties")
@ImportResource(locations = {"classpath:/application-kafka-bean.xml"})
@RunWith(SpringRunner.class)
@SpringBootTest

public class DemoApplicationTests {
	@Autowired
	private kafkaService service;


	@Test
	public void contextLoads() {
		System.out.println("测试用例1 .......");
		service.sendMsg();
	}



	@Test
	public void test02(){
		System.out.println("测试用例2 .......");
		service.sendMsgWithPartition();
	}


	@Test
	public void test03(){
		System.out.println("测试用例3 .......");
		service.sendMsgWithPartition();
	}


	@Test
	public void test4(){
		System.out.println("测试用例4 .......");
		service.sendMsgWithTimeStamp();
	}


	@Test
	public void test5(){
		System.out.println("测试用例5 .......");
		service.sendMsgWithHeaderInfo();
	}


	@Test
	public void test6(){
		System.out.println("测试用例6 ......");
		System.out.println("发送带有record的信息");
		service.sendMsgWithRecord();
	}

	@Test
	public void test7(){
		System.out.println("测试用例7 .......");
		System.out.println("发送带有回调的信息");
		service.sendMsgWithCallBack();
	}


	@Test
	public void test8(){
		System.out.println("测试用例8 .......");
		System.out.println("发送metrics");
		service.getMetrics();
	}


}
