<%@ page import="com.etc.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Dispose</title>
</head>
<body>
<%
Customer cust =(Customer) request.getAttribute("cust");
if(cust==null){
	cust = new Customer();
	request.setAttribute("cust", cust);
}
cust.setName(request.getParameter("custname"));
cust.setPwd(request.getParameter("pwd"));
cust.setAge(Integer.parseInt(request.getParameter("age")));
cust.setAddress(request.getParameter("address"));
boolean flag = cust.register();
%><br>
<%if(flag) {%>
 Welcome , your personal Info:<br>
 Custname: <%=cust.getCustname() %><br>
 Password: <%=cust.getPwd() %><br>
 Age: <%=cust.getAge() %><br>
 Address:<%=cust.getAddress() %><br>
 Thanks for your registration!
 <%}else{ %>
      <jsp:forward page="register.jsp"></jsp:forward>
<%} %>

</body>
</html>