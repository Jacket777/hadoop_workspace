<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>chooseJSP</title>
</head>
<body>
<h2>
<%
  String pref = (String)session.getAttribute("userPref");
  System.out.println("==session================"+pref);
  if(pref.equals("Performance")){
	  out.println("Now you can stop even if you <em>do</em> drive insanely fast");
  }else if(pref.equals("Safety")){
	  out.println("Our brakes won't lock up, no matter how bad a driver you are.");
  }else if(pref.equals("Maintenance")){
	  out.println("Lost your tech job? No problem--you won't have to service  these for at least three year.");		 
  }else{
	  out.println("Our brakes are the best");
  }
%>
</h2>
<strong>The Brakes</strong><br>
Our advanced anti-lock brake system(ABS) is engineered to give you the ability to
steer even as you're stopping, we have the 
best speed sensors of any car this size<br>
===========================================================================<br>
USE JSTL CHOOSE<br>
<c:choose>
<c:when test= "${userPref == 'Performance'}">
Now you can stop even if you <em>do</em> drive insanely fast<br>
</c:when>
<c:when test="${userPref == 'Safety'}">
Our brakes won't lock up, no matter how bad a driver you are.<br>
</c:when>
<c:when test="${userPref == 'Maintenance'}">
Lost your tech job? No problem--you won't have to service  these for at least three year.<br>
</c:when>
<c:otherwise>
 Our brakes are the best<br>
</c:otherwise>
</c:choose>



</body>
</html>