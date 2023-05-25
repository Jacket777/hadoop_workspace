<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bad</title>
</head>
<body>
<strong>About to be bad.....</strong>
<c:catch var="myException">
<%int x = 100/0; %>
</c:catch>

<c:if test="${myException !=null }">
  There was an exception: ${myException.message}<br>
</c:if>

If you see this,we survived.<br>
</body>
</html>