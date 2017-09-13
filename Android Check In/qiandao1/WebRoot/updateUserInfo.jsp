<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="java.util.*,
	lala.*,
	java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<LINK href="mstx.css" type=text/css rel=stylesheet>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>个人信息修改</title>
<script type="text/javascript">/*javascript代码用于本地验证*/
		String.prototype.Trim = function(){/*通过原型向String类添加trim方法*/
		   return this.replace(/(^\s*)|(\s*$)/g,"");/*通过正则式处理字符串*/
		};
		function checkUserName(){//监测输入的数据是否合法
			
			if(document.myform.u_name.value.Trim()== ""){//设置密码为空
			 	alert("请输入姓名！");/*弹出提示框*/
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
			else if(document.myform.u_phone.value.Trim()==""){//设置密码为空
			 	alert("请输入手机号码！");/*弹出提示框*/
			 	return false;
			}
			else if(document.myform.u_phone.value.length!=11){//设置密码为空
			 	alert("请输入正确的手机号码！");/*弹出提示框*/
			 	return false;	
			 	}		
			return true;//监测成功
		} 
	</script>
</head> 
<body background="images/background.jpg">
	
	<table bgcolor="#ffffef" align="center" width="900px">
	<tr><td width="70%">
		<table>
			<tr>
				<td>
					
					<font size="3">个人资料</font><br/><br/>
					<div class=arinfo>请填写完整准确的个人资料.</div>
				</td>				
			</tr>
			<tr><td><hr color="ffde9e"/></td></tr>	
			<tr>
				<td>
					<form  name="myform" onsubmit="return checkUserName();" method="post"action="ControlServelet?action=updateUserInfo">
					<table>						
					<tr><td></td></tr>			
						<tr><td align="center"><em>用户ID：</em></td><td><%=session.getAttribute("u_id")%></td></tr>
						<tr><td ><em>姓名：</em></td><td><input type="text" name="u_name" class="f2_input" value="<%=session.getAttribute("u_name")%>"/></td></tr>						
						<tr><td><em>手机号：</em></td><td><input type="text"  name="u_phone" class="f2_input" /></td></tr>	
						<tr><td><em>密码：</em></td><td><input type="password" name="u_pwd1" class="f2_input" /></td></tr>
						<tr><td><em>请确认密码：</em></td><td><input type="password" name="u_pwd2" class="f2_input" /></td></tr>
						<tr><td><a href="/qiandao1/home.jsp">返回</a></td><td align="left"  height="50px"><input type="submit" value="确认修改" class="btn"></td></tr>									
					
					</table>
					</form>
				</td>
			</tr>
		</table>
	</td>
	<td width="30%"></td>
	</tr>		
	</table>
</body>
</html>