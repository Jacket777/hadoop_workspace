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
 * ����¼
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
			//�û��Ϸ�����ת������
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			HdfsDAO hdfs = new HdfsDAO();
			FileStatus[]documentList = hdfs.getDirectoryFromHdfs();
			request.setAttribute("documentList", documentList);
			System.out.println("�õ�list����: "+documentList);
			request.getRequestDispatcher("index.jsp").forward(request,response);	
		}else {
			//�û����Ϸ�����ת����¼ҳ�棬����ʾ������Ϣ
			request.getRequestDispatcher("login.jsp").forward(request,response);
		}
	}
}
