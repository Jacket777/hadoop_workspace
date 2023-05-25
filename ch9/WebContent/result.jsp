<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>result</title>
</head>
<body>
Use Script: <br>
<table>
<% String[]items = (String[])request.getAttribute("movieList");
   String var = null;
   for(int i = 0;i<items.length;i++){
	   var = items[i];
	   %>
	   <tr><td><%=var %></td></tr>
   <% } %>
</table>
<br>
USE JSTL:<br>
<table>
<c:forEach var="movie" items="${movieList}" varStatus="movieLoopCount">
<tr>
<td>Count:${movieLoopCount.count}</td>
</tr>
<tr>
 <td>${movie}<br><br></td>
</tr>
</c:forEach>
</table>
<br>

DOUBLE ITERATOR USE JSTL:<br>
<c:forEach var="listElement" items="${movies}">
<c:forEach var="movie" items="${listElement}">
 <tr>
   <td>${movie}</td>
   <br>
 </tr>
 </c:forEach>
 <br><br>
</c:forEach>
<br>
<strong>Member Comments</strong><br>
<hr>This sit rocks</hr>
<c:if test="${userType eq 'member' }">
   <jsp:include page="inputComments.jsp"/>
</c:if>








</body>
</html>