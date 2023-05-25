package com.demo.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class TestServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String[]movieList = {"Amelie","Return of the King","Mean Girls"};
		request.setAttribute("movieList", movieList);
		String[]movies1 = {"Matrix Revolutions","Kill Bill","Boonddock Saints"};
		String[]movies2 = {"Amelie","Return of the King","Mean Girls"};
		ArrayList list = new ArrayList();
		list.add(movies1);
		list.add(movies2);
		request.setAttribute("movies", list);
		String checkMember = request.getParameter("userType");
		if(checkMember.equals("1")) {
			request.setAttribute("userType", "member");
		}
		
		
		
		
		request.getRequestDispatcher("/result.jsp").forward(request,
					response);		
	}

}