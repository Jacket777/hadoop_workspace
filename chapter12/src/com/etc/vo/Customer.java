package com.etc.vo;

public class Customer {
	private String name;
	private String pwd;
	private Integer age;
	private String address;
	
	public Customer(String name, String pwd, Integer age, String address) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.age = age;
		this.address = address;
	}

	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}