package com.etc.service;


import java.util.ArrayList;
import com.etc.dao.CustomerDAO;
import com.etc.vo.Customer;

public class CustomerService {
	private CustomerDAO dao = new CustomerDAO();
	
	public boolean login(String custname,String pwd) {
		Customer cust=dao.selectByNamePwd(custname, pwd);
		if(cust!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean register(Customer cust) {
		Customer customer = dao.selectByName(cust.getName());
		if(customer==null) {
			dao.insert(cust);
			return true;
		}else {
			return false;
		}
	}
	
	public Customer viewPersonal(String custname) {
		return dao.selectByName(custname);
	}
	
	public ArrayList<Customer>viewAll(){
		return dao.selectAll();
	}
}