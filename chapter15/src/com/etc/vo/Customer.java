package com.etc.vo;

import com.etc.dao.CustomerDAO;

public class Customer {
	private String custname;
	private String pwd;
	private Integer age;
	private String address;
	
	
	public Customer() {
		super();
	}
	
	public Customer(String custname,String pwd) {
		super();
		this.custname = custname;
		this.pwd = pwd;
	}
	
	public Customer(String custname, String pwd, Integer age, String address) {
		super();
		this.custname = custname;
		this.pwd = pwd;
		this.age = age;
		this.address = address;
	}

	
	
	public String getCustname() {
		return custname;
	}
	public void setName(String custname) {
		this.custname = custname;
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
	
	public boolean register() {
		CustomerDAO dao = new CustomerDAO();
		if(dao.selectByName(custname)!=null) {
			return false;
		}else {
			dao.insert(this);
			return true;
		}
	}
	
	
	
}