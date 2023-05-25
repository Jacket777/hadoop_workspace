<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
  function checkUser(){
	  if(document.login.inputname.value==""){
		  alert("the user cannot be empty");
		  return false;
	  }
	  
	  if(document.login.inputpass.value==""){
		  alert("the password cannot be empty");
		  return false;  
	  }
	  return true;
  }
</script>
<title>Login</title>
<link rel="stylesheet" href="assets/css/style.css">
</head>
<body
    style="background-color:#D3A4FF;background-position:center;background=repeat:repeat-y"
>
<div class="login-container">
   <h1>Clound Disc</h1>
   <div class="connect">
      <p>www.suning.com</p>
   </div>
   <form action="LoginServlet" method="post" id="loginForm" name="login" onsubmit="return checkUser()">
       <div>
         <input type="text" id="iputname" name="username" class="username" placeholder="用户名" autocomplete="off"/>
       </div>
   
        <div>
         <input type="password" id="iputpass" name="password" class="password" placeholder="密码" oncontextmenu="return false" onpaste="return false"/>
       </div>
       <button id="submit" type="submit">登录</button>
   </form>
   <a href="register.jsp">
     <button type="button" class="register-tis">还没有账号？</button>
   </a>
</div>
<div
    style="text-alig:center;margin:50px 0;font:normal 14px/24px 'MicroSoft YaHei';">
    <p>使用浏览器：Chrome,Safari</p>
    <p>来源:<a href="http://wwww.suning.com">suning</a>
</div>
</body>
</html>