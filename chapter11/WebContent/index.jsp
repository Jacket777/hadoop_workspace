<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
</head>
<%
String custname = null;
String pwd = null;
Cookie[]cookies = request.getCookies();
if(cookies!=null){
	for(Cookie c:cookies){
		if(c.getName().equals("custname")){
			custname = c.getPath();
		}
		if(c.getName().equals("pwd")){
			pwd = c.getPath();
		}
	}
}

if(custname!=null&&pwd!=null){
	request.getRequestDispatcher("login?custname="+custname+"&pwd="+pwd).
	forward(request, response);
	return;
}


%>
<body>
<form action="login" method="post">
用户名 : <input type="text" name="custname"><br>
密码  : <input type="password" name = "pwd"><br>
<input type="submit" value="登录">
</form>


</body>
</html>