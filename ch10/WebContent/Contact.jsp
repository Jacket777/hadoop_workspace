<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Contact</title>
</head>
<body>
<myTags:Header subTitle="We take the sting out of SOAP" />
<br>
<myTags:test fontColor="#660099">
So it's not Jini<br>
but we'll help you get through it with the least<br>
frustration and hair loss.<br>
</myTags:test>
<em>We can help.</em><br><br>
Contact us at : ${initParam.mainEmail}<br>
<%@ include file="footer.html" %>
</body>
</html>