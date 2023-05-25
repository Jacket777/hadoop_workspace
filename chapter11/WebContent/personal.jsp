<%@ page import="com.etc.vo.Customer"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Personal Info</title>
</head>
<body>
<% Customer cust = (Customer)request.getAttribute("cust"); %>
你的个人信息:<br>
用户名: <%=cust.getName() %><br>
密码: <%=cust.getPwd() %><br>
年龄: <%=cust.getAge() %><br>
地址: <%=cust.getAddress() %><br>
</body>
</html>