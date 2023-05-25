package com.kafka.lab04.serial;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 3.实现序列化类
 */

public class JObjectSerial implements Serializable{

	private static final long serialVersionUID = 5703934259906982689L;
	private static Logger LOG = LoggerFactory.getLogger(JObjectSerial.class);
	
	public byte id =1; //用户ID
	public byte money = 100;//充值金额

	public static void main(String[] args) {
		try {
		FileOutputStream fos = new FileOutputStream("F:/salary.out");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		JObjectSerial jos = new JObjectSerial();
		oos.writeObject(jos);
		oos.flush();
		oos.close();
		}catch(Exception e) {
			LOG.error("Serial has error, msg is "+e.getMessage());
		}

	}

}
