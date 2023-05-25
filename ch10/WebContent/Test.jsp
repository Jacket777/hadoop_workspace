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
<title>Test Tag</title>
</head>
<body>
Simple Tag 1<br>
<myTags:simple1/>
<br>
Simple Tag 2<br>
<myTags:simple2>
   This is the body
</myTags:simple2>
<br>
Simple Tag 3<br>
<myTags:simple3>
   Message is :${message}
</myTags:simple3>
<br>
Simple Tag 4<br>
<myTags:simple4>
   <tr><td>
   ${movie}
   <tr><td><br>
</myTags:simple4>
Simple Tag 5<br>
<table>
<%
Movie m1 = new Movie("test1", "A");
Movie m2 = new Movie("test2", "B");
List <Movie>list = new LinkedList<Movie>();
list.add(m1);
list.add(m2);
request.setAttribute("movieCollection", list);
%>
<myTags:simple5 movieList="${movieCollection}">
<tr>
<td>${movie.name}</td>
<td>${movie.genre}</td>
</tr>
</myTags:simple5>
</table>
</body>
</html>