package com.simple.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.hadoop.fs.FileStatus;

import com.simple.model.HdfsDAO;





public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       request.setCharacterEncoding("UTF-8");
       File file;
       int maxFileSize = 50*1024*1024;
       int maxMemSize = 50*1024*1024;
       ServletContext context = getServletContext();
       String filePath = context.getInitParameter("file-upload");
       System.out.println("source file path: "+filePath+"");
       String contentType = request.getContentType();
       if((contentType.indexOf("multipart/form-data")>=0)) {
    	   DiskFileItemFactory factory = new DiskFileItemFactory();
    	   factory.setSizeThreshold(maxMemSize);
    	   factory.setRepository(new File("c:\\temp"));
    	   ServletFileUpload upload = new ServletFileUpload(factory);
    	   upload.setSizeMax(maxFileSize);
    	   try {
    		   List fileItems = upload.parseRequest(request);
    		   Iterator i = fileItems.iterator();
    		   System.out.println("begin to upload file to tomcat server</p>");
    		   while(i.hasNext()) {
    			   FileItem fi = (FileItem)i.next();
    			   if(!fi.isFormField()) {
    				   String filedName = fi.getFieldName();
    				   String fileName = fi.getName();
    				   String fn = fileName.substring(fileName.lastIndexOf("\\")+1);
    				   System.out.println("<br>"+fn+"<br>");
    				   boolean isInMemory = fi.isInMemory();
    				   long sizeInBytes = fi.getSize();
    				   //写入文件
    				   if(fileName.lastIndexOf("\\")>=0) {
    					   file = new File(filePath,
    							   fileName.substring(fileName.lastIndexOf("\\")));
    				   }else {
    					   file = new File(filePath,
    							   fileName.substring(fileName.lastIndexOf("\\")+1));
    				   }
    				   fi.write(file);
    				   System.out.println("upload file to tomcat server success!");
    				   System.out.println("begin to upload file to hadoop hdfs</p>");
    				   String name = filePath+File.separator+fileName;
    				   //将tomcat上的文件上传到hadoop上
    				   HdfsDAO hdfs = new HdfsDAO();
    				   hdfs.copyFile(name);
    				   System.out.println("upload file to hadoop hdfs success!");
    				   FileStatus[]documentList = hdfs.getDirectoryFromHdfs();
    				   request.setAttribute("documentList", documentList);
    				   System.out.println("得到list数据"+documentList);
    				   request.getRequestDispatcher("index.jsp").forward(request, response);	   
    			   }   
    		   }
    	   }catch(Exception ex) {
    		   System.out.println(ex);
    	   }  
       }else {
    	   System.out.println("<p>No file uploaded</p>");
       }
	}
}