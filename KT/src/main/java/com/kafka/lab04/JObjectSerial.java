package com.kafka.lab04;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JObjectSerial implements Serializable{
	private static final long serialVersionUID = -5625285308287046726L;

	private static Logger LOG=LoggerFactory.getLogger(JObjectSerial.class);
	
	public byte id =1;//用户ID
	public byte money =100;//充值金额
	

	public static void main(String[] args) {
		try {
			FileOutputStream fos = new FileOutputStream("/tmp/salary.out");
			try {
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				JObjectSerial jos = new JObjectSerial();
				oos.writeObject(jos);
				oos.flush();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			   LOG.error("Serial has error,msg is "+e.getMessage());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
