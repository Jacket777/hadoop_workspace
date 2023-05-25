<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>
</head>
<body>
 欢迎你！<%=request.getParameter("custname") %><br>
 你是第 <%=application.getAttribute("count") %>位访问者!<br>
<a href="<%=response.encodeURL("getPersonal") %>">查看个人信息</a><br>
<!--<a href="getPersonal">查看个人信息</a><br> -->
<a href="GetAll">查看所有用户信息</a><br>


</body>
</html>