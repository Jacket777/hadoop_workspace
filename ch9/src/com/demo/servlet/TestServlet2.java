package com.demo.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;



public class TestServlet2 extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String value = request.getParameter("userPref");
		System.out.println("value==============="+value);
		HttpSession session = request.getSession();
		session.setAttribute("userPref", value);
		request.getRequestDispatcher("/choose.jsp").forward(request,
					response);		
	}

}