package com.etc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class FirstServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("doGet : Hello,ETC!");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String custname = request.getParameter("custname");
		String pwd = request.getParameter("pwd");
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		out.println("Welcome! your name is: <font color=red>"+custname+
				"</font><br>");
		out.println("Your password is: "+pwd);
		out.close();
	}
}