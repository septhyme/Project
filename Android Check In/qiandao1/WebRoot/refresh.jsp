<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>提示界面</title>
    <Meta http-equiv="refresh" content="3;url=index.jsp">

  </head>
  
  <body>
  <h1>修改个人信息成功，3秒后请重新登录</h1>
    
  </body>
</html>
