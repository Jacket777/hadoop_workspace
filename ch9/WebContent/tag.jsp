<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="mine" uri="randomThings" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>tag test</title>
<%
String userName = (String)request.getParameter("userName");
System.out.println(userName);
request.setAttribute("userName", userName);
%>
</head>
<body>
Advisor Page<br>
<mine:advice user="${userName}"/>
<br>
TEST JSP ATTRIBUTE<br>
<mine:advice >
<jsp:attribute name="user">${userName}</jsp:attribute>
</mine:advice>
</body>
</html>