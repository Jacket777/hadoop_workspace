<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>import</title>
</head>
<body>
<c:import url="Header.jsp">
<c:param name="subTitle" value="We take the sting out of SOAP"></c:param>
</c:import>
<br>
<em>welcome to our web services support group</em><br><br>
Contact us at: ${initParam.mainEmail}

</body>
</html>