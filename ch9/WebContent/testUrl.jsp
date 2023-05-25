<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>testUrl</title>
</head>
<body>
<c:set var="last" value="Hidden Cursor"/>
<c:set var="first" value="Crouching Pixels"/>
<c:url value="/inputComments.jsp?first=${first}&last=${last}" var="inputURL"/>
The URL using params is: ${inputURL}<br>
<c:url value="/inputComments.jsp" var="inputURL">
 <c:param name="firstName" value="${first}"/>
 <c:param name="lastName"  value="${last}"/>
</c:url>
<br>
<br>
The URL using params is: ${inputURL}<br>

</body>
</html>