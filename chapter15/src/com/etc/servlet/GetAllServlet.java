package com.etc.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.service.CustomerService;
import com.etc.vo.Customer;


public class GetAllServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		CustomerService cs = new CustomerService();
		ArrayList <Customer> list = cs.viewAll();
		request.setAttribute("allcustomers", list);
		request.getRequestDispatcher("allcustomers.jsp").forward(request, response);	
	}
}
