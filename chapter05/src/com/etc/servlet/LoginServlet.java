package com.etc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.service.LoginService;


public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String custname = request.getParameter("custname");
		String pwd = request.getParameter("pwd");
		//
		LoginService ls=new LoginService();
		boolean flag = ls.login(custname, pwd);
		if(flag) {
			response.sendRedirect("/chapter05/welcome.jsp");	
		}else {
			response.sendRedirect("/chapter05/index.jsp");	
		}
		
		
	}

}
