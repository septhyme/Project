<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>中南民族大学签到系统</title>
<style>
body {
	background: url(images/top.jpg) no-repeat center 10px;
}

#linkbar {
	font-size: 12px;
	position: absolute;
	top: 90px;
	right: 10px;
	height: 30px;
	line-height: 30px;
	width: 580px;
}

#linkbar a:link {
	color: #333;
	text-decoration: none;
}

#linkbar a:visited {
	color: #333;
	text-decoration: none;
}

#linkbar a:hover {
	color: #FF0000;
	text-decoration: none;
}
</style>
</head>
<body background="images/background.jpg" >
	<div id="linkbar">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<!--<img src="images/xx.gif"/> <a href="/qiandao/login.jsp" >登录</a>-->
		<!--<img src="images/xx.gif"/> <a href="/qiandao/register.jsp" >注册</a>-->
		<!--  <img src="images/xx.gif"/> <a href="/qiandao/perinfor.jsp" >修改个人信息</a>-->
		<img src="images/xx.gif" /> <a href="/qiandao1/sorry.jsp">联系本站管理员</a>

	</div>
</body>
</html>