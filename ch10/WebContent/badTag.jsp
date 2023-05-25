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
About to invoke a tag that throws SkipPageException<br>
<myTags:simple6/>
<br>Back in the page after invoking the tag.
</body>
</html>