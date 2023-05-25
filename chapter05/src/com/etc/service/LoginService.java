package com.etc.service;

public class LoginService {
	public boolean login(String custname,String pwd) {
		if(custname!=null&&pwd!=null&&
				custname.equals("admin")&&pwd.equals("123")
				) {
			return true;
		}else {
			return false;
		}
	}
	

}
