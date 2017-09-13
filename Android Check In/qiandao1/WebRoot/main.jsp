<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.*,
	lala.*,
	java.sql.*" 
	%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <table border="1" width="70%">
  <caption><font size="6" color="red">课程表</font></caption>
  	<tr style="color:red">
  		
  		<th>课程名</th>
  		<th>学号</th>
  		<th>姓名</th>
  		<th>签到次数</th>
  		
  	</tr>
   <%  Object i= session.getAttribute("i"); 
       Object a= session.getAttribute("u_id"); 
     ResultSet rs=DBUtil.webgetmyclass(i,a);
     while(rs.next())
     {%>
     	<tr align="center">
    	<td><%=rs.getString("c_name")%></td>
    	<td><%=rs.getString("s_id")%></td>
    	<td><%=rs.getString("s_name")%></td>
    	<td><%=rs.getString("QD_number")%></td>
     
    <%}%> 	
    </tr>
    
     </table>
  </body>
</html>
