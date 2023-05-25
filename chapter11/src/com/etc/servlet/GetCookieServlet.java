package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCookieServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
          Cookie[]cookies = request.getCookies();
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          if(cookies==null) {
        	  out.println("No cookie");
          }else {
        	  for(Cookie c:cookies) {
        		  if(c.getName().equals("username")) {
        			  out.println("Username: "+c.getValue()+"<br> ");
        		  }
        		  if(c.getName().equals("password")) {
        			  out.println("Password: "+c.getValue()+"<br> ");
        		  }	  
        	  }
          }
          out.close();
	}
}