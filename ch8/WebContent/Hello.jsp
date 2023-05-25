<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Hello</title>
</head>
<body>
Welcome to our page!
<% if(request.getParameter("userName")==null) { %>
   <jsp:forward page="HandleIt.jsp"/>
<%} %>

</body>
</html>