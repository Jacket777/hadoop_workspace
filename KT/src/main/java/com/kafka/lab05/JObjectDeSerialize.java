package com.kafka.lab05;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kafka.lab04.serial.JObjectSerial;


/*
 * 3.实现反序列化类
 */

public class JObjectDeSerialize {
	private static Logger LOG = LoggerFactory.getLogger(JObjectDeSerialize.class);

	public static void main(String[] args) {
		FileInputStream fis =null;
		ObjectInputStream ois=null;
		try {
			fis = new FileInputStream("F:/salary.out");
			ois = new ObjectInputStream(fis);
			JObjectSerial jos = (JObjectSerial) ois.readObject();
			LOG.info("ID: "+jos.id+", Money:"+jos.money);
		} catch (Exception e) {
			LOG.error("Deserial has error, msg is "+e.getMessage());
		}finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
