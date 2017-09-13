<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.*,
	lala.*,
	java.sql.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF	-8">
<title>欢迎来到签到系统</title>
<%@ page contentType="text/html; charset=gb2312" language="java"
	import="java.sql.*" errorPage=""%>
<script language="javascript" type="text/javascript" src="LeftMenu.js"></script>
<script type="text/javascript">
	var myMenu;
	window.onload = function() {
		myMenu = new SDMenu("my_menu");
		myMenu.init();
	};
</script>
<!-- /////////////////////////////////////////////////////////////////////// -->
<style type="text/css">
span {
	background-image: url(imgs/navbar1.png);
	background-repeat: repeat-x;
	border-bottom: 1px solid #cfcfcf;
	display: block;
	line-height: 27px;
	padding-left: 20px;
	font-size: 14px;
	font-family: 宋体;
}

a {
	padding: 3px 0 3px 40px;
	display: block;
	color: #636363;
	margin: 9px 8px 8px 0px;
	font-size: 14px;
	font-family: 宋体;
	text-decoration: none;
}

a:hover {
	color: #0000CD;
}

#my_menu {
	width: 198px;
	background: #ffffff;
	height: 420px;
	border: 1px solid #cfcfcf;
}

div.sdmenu div.collapsed {
	height: 25px;
}

div.sdmenu div {
	overflow: hidden;
}
</style>
<style type="text/css">
.style1 {
	height: 45px;
}

.style2 {
	height: 44px;
}

.style3 {
	height: 45px;
	width: 638px;
}

.style4 {
	height: 44px;
	width: 638px;
}

.style6 {
	height: 45px;
	width: 184px;
}

.style7 {
	height: 44px;
	width: 184px;
}

#Img1 {
	margin-top: 0px;
}
</style>

</head>
<body background="images/background.jpg">
	<div class="container"></div>
	<form action="" method="get" id="form1">
		<table width="1030" height="132" border="0" align="center">
			<tr>
				<td width="1029" height="113">
					<div align="center">
						<jsp:include page="top1.jsp" />
					</div>
				</td>
			</tr>
		</table>
		<table width="1030" height="719" border="4" align="center"
			cellspacing="0" bordercolor="#0066CC">
			<tr>
				<td width="172" height="54" align="center" valign="top">
					<table width="180" border="0">
						<tr>&nbsp;</tr>
						<tr><%=session.getAttribute("u_name")%>登陆成功！</tr> 
						<tr>	账号为：<%=session.getAttribute("u_id")%></tr>
					</table>
					<table width="180" height="615" border="0">
						<tr>
							<td width="156" align="center" valign="top"><div
									class="container">
									<div class="lefttree">
										<div
											style="background: url(imgs/gongNengGuanLi.png); height: 34px; width: 200px;">

											<div
												style=" height:450px; background-color:#E0FFFF; float: left"
												id="my_menu" class="sdmenu">
												<div>
													<span>我的课程</span>

													<%  Object i = session.getAttribute("i");
														Object a = session.getAttribute("u_id");
														ResultSet rs = DBUtil.webgetmyclassname(i,a);
														while (rs.next()) {
													%>
													<a href="/qiandao1/sorry.jsp"><%=rs.getString("c_name")%></a>
													<%
														}
													%>


												</div>

												<div>
													<span>个人信息管理</span> <a href="/qiandao1/updateUserInfo.jsp">修改个人信息</a>
													<a href="/qiandao1/sorry.jsp">联系本站管理员</a> 
													<a href="ControlServelet?action=exit">注销</a>
												</div>

											</div>
										</div>
									</div>
								</div>&nbsp;</td>
						</tr>
					</table>
					<p>&nbsp;</p>
				</td>
				<td width="842" align="center" valign="top"><jsp:include page="main.jsp" />&nbsp;</td>
			</tr>
		</table>
		<p>&nbsp;</p>
	</form>
</body>
</html>