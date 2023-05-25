package com.etc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.service.CustomerService;
import com.etc.vo.Customer;


public class GetPersonalServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session =request.getSession();
		String custname = (String)session.getAttribute("custname");
		CustomerService cs  = new CustomerService();
		Customer cust=cs.viewPersonal(custname);
		request.setAttribute("cust", cust);
		request.getRequestDispatcher("personal.jsp").forward(request, response);
	}
}