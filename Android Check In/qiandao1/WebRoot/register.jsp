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
<meta http-equiv="Content-Type" content="text/html">
<title>个人注册</title>
<!-- 标题 -->

<script type="text/javascript">/*javascript代码用于本地验证*/
		String.prototype.Trim = function(){/*通过原型向String类添加trim方法*/
		   return this.replace(/(^\s*)|(\s*$)/g,"");/*通过正则式处理字符串*/
		};
		function checkUserName(){//监测输入的数据是否合法
			if(document.myform.u_name.value.Trim()== ""){//用户名为空
			 	alert("请输入昵称！");/*弹出提示框*/
			 	return false;
			}
			else if(document.myform.u_pwd1.value.Trim()== ""){//设置密码为空
			 	alert("请输入密码！");/*弹出提示框*/
			 	return false;
			}
			else if(document.myform.u_pwd2.value.Trim()== ""){//设置密码为空
			 	alert("请输入确认密码！");/*弹出提示框*/
			 	return false;
			}	
			else if(document.myform.u_pwd2.value.Trim()!=document.myform.u_pwd1.value.Trim()){//两次密码不一致
			 	alert("两次密码不一致！");/*弹出提示框*/
			 	return false;
			}	
			else if(document.myform.phonenumber.value.Trim()==""){//设置密码为空
			 	alert("请输入手机号码！");/*弹出提示框*/
			 	return false;
			}		
			else if(document.myform.phonenumber.value.length!=11){//设置密码为空
			 	alert("请输入正确的手机号码！");/*弹出提示框*/
			 	return false;	
			 	}		
			return true;//监测成功
		} 
	</script>
</head>

<body background="images/background.jpg">
	<!-- 设置body的背景颜色 -->

	<table bgcolor="#fffff" align="center" width="900px">
		<tr>
			<td>

				<form name="myform" onsubmit="return checkUserName();" method="post"
					action="ControlServlet1?action=register">
					<table width="30%" align="center">
						<tr align="center"><td><font size="6">注册页面</font></td></tr>
						<tr align="center"><td><hr color="ffdeee" /></td></tr><!-- 画一条横线 -->
						
						<tr align="left"><td><font size="3">用户名:</font></td></tr>
						<tr align="center"><td><input type="text" name="u_name"></td></tr>
						<tr align="left"><td><font size="3">设置密码:</font></td></tr>
						<tr align="center"><td><input type="password" name="u_pwd1"></td></tr>
						<tr align="left"><td><font size="3">确认密码:</font></td></tr>
						<tr align="center"><td><input type="password" name="u_pwd2"></td></tr>
						<tr align="left"><td><font size="3">手机号码:</font></td></tr>
						<tr align="center"><td><input type="text" class="f2_input" name="phonenumber"></td></tr>
						<tr align="left"><td><input type="submit" value="立即注册" class="btn"></input></td></tr>
					</table>
					<!-- 表格结束 -->
					<a href="/qiandao1/login.jsp">返回主页</a>
				</form>
			</td>
		</tr>
	</table>
</body>
</html>