package com.etc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCookieServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Cookie c1 = new Cookie("username", "etc");
		Cookie c2 = new Cookie("password", "123");
		c1.setMaxAge(30);
		response.addCookie(c1);
		response.addCookie(c2);	
	}
}