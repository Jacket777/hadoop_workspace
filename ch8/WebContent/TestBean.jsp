<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="com.demo.bean.*" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TEST</title>
</head>
<body>
Hello<br>
<jsp:useBean id="p"  type="com.demo.bean.Person" class="com.demo.bean.Employee">
<jsp:setProperty name ="p" property="*" />
</jsp:useBean>
name is:<jsp:getProperty name="p" property="name" /><br>
ID is:<jsp:getProperty  name="p" property="empID"/><br>
</body>
</html>