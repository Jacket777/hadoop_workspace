package com.etc.chapter;

public class Customer {
	public String custname;
	public String pwd;
	public int age;
	public String address;
	
	public Customer(String custname, String pwd, int age, String address) {
		this.custname = custname;
		this.pwd = pwd;
		this.age = age;
		this.address = address;
	}

	
	
	
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	
	
	
	
	

}
