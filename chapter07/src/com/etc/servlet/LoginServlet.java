package com.etc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
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
			request.getRequestDispatcher("/welcome.jsp").forward(request,
					response);	
		}else {
			response.sendRedirect("/chapter07/index.jsp");	
		}
		
		
	}

}
