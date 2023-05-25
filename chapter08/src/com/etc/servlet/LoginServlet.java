package com.etc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.service.CustomerService;
import com.etc.service.LoginService;


public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//ÇëÇó²ÎÊý
		String custname = request.getParameter("custname");
		String pwd = request.getParameter("pwd");
		//
		CustomerService cs = new CustomerService();
		boolean flag = cs.login(custname, pwd);
		if(flag) {
			Cookie c1 = new Cookie("custname", custname);
			Cookie c2 = new Cookie("pwd", pwd);
			c1.setMaxAge(3600);
			c2.setMaxAge(3600);
			response.addCookie(c1);
			response.addCookie(c2);
			request.getRequestDispatcher("/welcome.jsp").forward(request,
					response);	
		}else {
			response.sendRedirect("/chapter08/index.jsp");	
		}
		
		
	}

}
