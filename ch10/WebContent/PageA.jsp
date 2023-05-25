<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="myTags" uri="simpleTags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Tag</title>
</head>
<body>
This is page(A) that includes another page(B).<BR>
Doing the include now:<br>
<jsp:include page="badTagInclude.jsp"/>
<br>Back in Page A after the include。。。。。。
</body>
</html>