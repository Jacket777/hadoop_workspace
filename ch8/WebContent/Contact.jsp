<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Contact</title>
</head>
<body>
<jsp:include page="Header.jspf">
   <jsp:param name="subTitle" value="We take the sting out of SOAP"/>
</jsp:include>
<em>We can help.</em><br><br>
Contact us at : ${initParam.mainEmail}<br>
<%@ include file="footer.html" %>
</body>
</html>