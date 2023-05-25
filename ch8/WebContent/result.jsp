<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.demo.bean.*" %>
    <%@ taglib prefix="mine" uri="DiceFunctions" %> 
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>result</title>
</head>
<body>
<!--
Hello<br>
<%--Person p = (Person)request.getAttribute("person"); --%>
 <%---=request.getAttribute("name") --%>
 
Person is object to string <%--=request.getAttribute("person") --%><br>
Person is <%--=p.getName() --%><br>

<!--  jsp:useBean id="person" class="com.demo.bean.Person" scope="request"/>-->




<!--  Person created by servlet:--><!--  jsp:getProperty name="person" property="name"/>-->

<!--
<jsp:useBean id="p"  type="com.demo.bean.Person" class="com.demo.bean.Employee">
<jsp:setProperty name ="p" property="name" value="Fred"/>
<jsp:setProperty name ="p" property="empID" value="100"/>
</jsp:useBean> 
-->

<!-- 
name is:<jsp:getProperty name="p" property="name" />
ID is:<jsp:getProperty  name="p" property="empID"/>
 -->
 Use script
 dog name is : <%=((People) request.getAttribute("people")).getDog().getName() %><br>
 Use action:<br>
 <jsp:useBean id="p1" class="com.demo.bean.People" scope="request"/>
 dog name is : <jsp:getProperty name="p1" property="dog"/><br>
 Use EL:<br>
 dog name is: ${people.dog.name}<br>
 Foods are: ${favoriteFood}<br>
 First food is ${favoriteFood[0]}<br>
 Second food is ${favoriteFood["1"]}<br>
 <br>
 Music is:${musicList}<br>
 First song is:${musicList[0]}<br>
 Second song is:${musicList["1"]}<br>
 
 Ambient is:${musicMap.Ambient}<BR>
 Ambient is:${musicMap["Ambient"]}<BR>
 
 Music is ${musicMap[Genre]}<br>
 The other Music<br>
 Music is ${musicMap[MusicType[0]]}
 
 Request param empID is :${param.empID}<br>
 Request param name is :${param.name}<br>
 
 Request param food is :${param.food}<br>
 First food request param is :${paramValues.food[0]}<br>
 Second food request param is :${paramValues.food[1]}<br>
 Request param name :${paramValues.name[0]}<br>
 
 Use Script: <br>
 Host is : <%=request.getHeader("host") %><br>
 Use EL:<br>
 Host is :${header["host"]}<br>
 Host is :${header.host}<br>
 
 Use Script:<br>
 Method is:<%=request.getMethod() %><br>
 Use EL: <br>
 Method is: ${pageContext.request.method}<br>
 
 Use Script:<br>
 email is <%=application.getInitParameter("mainEmail") %><BR>
 Use EL:<br>
 email is:${initParam.mainEmail}<br>
 Use taglib:<br>
 ${mine:rollIt() }<br>
 
 
 Test EL<br>
 TEST 1: ${num >3} <br>
 TEST 2: ${integer le 12} <br>
 TEST 3-1: ${requestScope["integer"]} <br>
 TEST 3-3: ${ 6 le num ||false } <br>

 TEST 4: ${list[0] || list["1"] and true} <br>
 TEST 5: ${num >integer} <br>
 TEST 6: ${num == integer-1} <br>
 <jsp:useBean class="com.demo.bean.Dog" id="myDog">
  <jsp:setProperty name="myDog" property="name" value="${list[1]}"/>
 </jsp:useBean>
 TEST 7: ${myDog.name and true} <br>
 
 TEST 9: ${mine:rollIt() le 0 }

</body>
</html>