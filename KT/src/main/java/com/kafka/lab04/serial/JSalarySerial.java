package com.kafka.lab04.serial;

import java.io.Serializable;

/*
 * 4.声明一个序列化类
 */

public class JSalarySerial implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;        //用户金额
	private String salary;    //金额
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "JSalarySerial [id="+id+", salary="+salary+"]";
	}
	
}
