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
<a href="http://www.w3school.com.cn">查看个人信息</a><br>
<a href="/chapter07/GetAll">查看所有用户信息</a><br>
</body>
</html>