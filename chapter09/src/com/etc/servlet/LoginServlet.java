package com.etc.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.service.CustomerService;



public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//请求参数
		String custname = request.getParameter("custname");
		String pwd = request.getParameter("pwd");
		//
		CustomerService cs = new CustomerService();
		boolean flag = cs.login(custname, pwd);
		if(flag) {
//			Cookie c1 = new Cookie("custname", custname);
//			Cookie c2 = new Cookie("pwd", pwd);
//			c1.setMaxAge(3600);
//			c2.setMaxAge(3600);
//			response.addCookie(c1);
//			response.addCookie(c2);
			
			//统计多少个人访问网站
			ServletContext context = this.getServletContext();
			Integer count =(Integer) context.getAttribute("count");
			if(count==null) {
				count=0;
			}
			count++;
			context.setAttribute("count", count);
			
			HttpSession session = request.getSession();
			session.setAttribute("custname", custname);
			request.getRequestDispatcher("/welcome.jsp").forward(request,
					response);	
			
			
			
			
			
		}else {
			response.sendRedirect("/chapter91/index.jsp");	
		}
		
		
	}

}
