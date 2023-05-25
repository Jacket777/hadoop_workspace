package com.simple.controller;


import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simple.model.HdfsDAO;
import com.simple.model.UserDAO;
import org.apache.hadoop.fs.FileStatus;

/*
 * 检查登录
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 3789345827824133191L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserDAO user = new UserDAO();
		if(user.checkUser(username,password)) {
			//用户合法，跳转到界面
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			HdfsDAO hdfs = new HdfsDAO();
			FileStatus[]documentList = hdfs.getDirectoryFromHdfs();
			request.setAttribute("documentList", documentList);
			System.out.println("得到list数据: "+documentList);
			request.getRequestDispatcher("index.jsp").forward(request,response);	
		}else {
			//用户不合法，跳转到登录页面，并提示错误信息
			request.getRequestDispatcher("login.jsp").forward(request,response);
		}
	}
}
