<%@page import="java.util.LinkedList"%>
<%@page import="com.demo.bean.Movie"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="myTags" uri="simpleTags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test C</title>
</head>
<body>
<myTags:SimpleTest>
<br>
  This simple tag body
  <br>
</myTags:SimpleTest>
<br>
<myTags:classTest>
<br>
  This traditional tag body
  <br>
</myTags:classTest>

</body>
</html>