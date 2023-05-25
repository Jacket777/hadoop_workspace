<%@page import="com.etc.vo.Customer"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AllCustomers</title>
</head>
<body>

<% ArrayList<Customer> list = (ArrayList<Customer>)request.getAttribute("allcustomers"); %>
所有用户信息: <br>
<table width="200" border="1">
<tbody>
<tr>
<td>&nbsp;用户名</td>
<td>&nbsp;年龄</td>
<td>&nbsp;地址</td>
</tr>
<% for(Customer c:list){ %>
<tr>
<td><%=c.getName() %></td>
<td><%=c.getAge() %></td>
<td><%=c.getAddress() %></td>
</tr>
<% } %>

</tbody>
</table><br>

</body>
</html>