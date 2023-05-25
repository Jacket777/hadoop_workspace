package com.etc;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ShowColorServlet extends HttpServlet {
	//
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8"); 
		String[] values = request.getParameterValues("check");
		//response.setHeader("Content-type", "text/html;charset=UTF-8");
		
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("你选择的颜色是: ");
		for(int i = 0; i <values.length; i++) {
			
//			String txt = URLEncoder.encode(values[i], "ISO-8859-1");
//			String value = URLDecoder.decode(txt, "UTF-8");

			out.println(values[i]);
			System.out.println("======="+values[i]);
		}
		
		out.close();
	}
}