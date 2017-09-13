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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中南民族大学签到系统</title>

<link href="/ssfw/resources/css/newlogin.css" type="text/css"
	rel="stylesheet" />
<jsp:include page="top.jsp" />

<script type="text/javascript" language="javascript">

 String.prototype.Trim = function(){/*通过原型向String类添加trim方法*/
		  return this.replace(/(^\s*)|(\s*$)/g,"");/*通过正则式处理字符串*/
		};
 function ValueCheck(){//监测输入的数据是否合法
            vU=document.all.UID.innerText;
			vU=vU.substring(0,3);
			if(document.myform.u_id.value.Trim()==""){//用户名为空
			 	alert("请输入"+vU+"!");/*弹出提示框*/
			 	return false;
			}
			else if(document.myform.u_pwd.value.Trim()==""){//设置密码为空
			 	alert("请输入密码！");/*弹出提示框*/
			 	return false;
			}
			else if(document.myform.myrand.value.Trim()==""){//验证码为空
			alert("请输入验证码！");
			return false;
			}
			else return true;
  }
   function SelType(theID){
 			 var s=theID.options[theID.selectedIndex].value;
 				if(s=='s'){
    						document.all.UID.innerHTML='学 号：';
 			}
  				else if(s=='t'){
    						document.all.UID.innerHTML='工 号：';
 			}
}   

function loadimage() {
		document.getElementById("randImage").src = "image.jsp?" + Math.random();
	}
function f1(){ 
		window.location.href="register.jsp";
	}        
        </script>

</head>

<body background="images/background.jpg">

	<form id="myform" name="myform" method="post" onsubmit="return ValueCheck();"  action="ControlServelet?action=login">
		<table width="980" border="0" align="center" cellpadding="0"cellspacing="0">
			<tr>
				<td style="height:150px; background-repeat:no-repeat; background-position:20px 60px;">
					<table width="40%" border="0" align="right" cellpadding="0" cellspacing="0">
					</table>
				</td>
			</tr>
		</table>   <!-- 用来放top的table区域 -->
		<table width="980" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td valign="top"
					style="width:980px; height:330px; background-image:url(images/main_bg.jpg); background-repeat:no-repeat;">
					<!-- 新建了一table，tr这一大列，里面又建一个table -->
					<table width="40%" border="0" align="right" cellpadding="0"cellspacing="0">
						<tr>
							<td width="81%" height="30"></td>
							<td width="19%">&nbsp;</td>
						</tr><!-- 对这个大的一列，分成了八二开 -->
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									
									<tr>
										<td colspan="2" align="center" height="10" valign="middle">
											
										</td>
									</tr>
									<tr>
										<td height="40" style="font-size:12px">&nbsp;&nbsp;&nbsp;&nbsp;请输入用户信息。
										</td>
									</tr>
									<tr>
									<td>
										<table width="300" border="0" align="right" cellpadding="0" cellspacing="0">
											<tr>
												<td width="20" height="40" style="font-size:12px">身&nbsp;&nbsp;份：</td>
												<td class="content" align="left" valign="middle" width="124">
													<select style="font-size:12px"onChange="SelType(this)" name="mytype" class="content">
															<option value="s" selected="selected">学生</option>
															<option value="t">教师</option>
													</select>
												</td>
											</tr>
											<!-- 输入学号的行 -->
											<tr>
												<td width="61" id=UID  height="40" style="font-size:12px">学 号：</td>
												<td colspan="2"><input type="text" id="u_id"name="u_id"
												style="width:180px; height:20px; border:1px solid #7ab8d8" />
												</td>
											</tr>
											<!-- 输入密码的行 -->
											<tr>
												<td height="40" style="font-size:12px">密&nbsp;&nbsp; 码：</td>
												<td colspan="2">
												<input type="password" id="u_pwd" name="u_pwd"
												style="width:180px; height:20px; border:1px solid #7ab8d8" />
												</td>
											</tr>
											<!-- 输入验证码的行 -->
											<tr>
												<td height="40" style="font-size:12px">验证码：</td>
												<td width="118" height="22" valign="middle" align="center">
												<input type="text" id="myrand" name="myrand" size="15">
												</td>
												<td width="60" valign="middle" align="center">
												<img alt="验证码" name="randImage" id="randImage"
													src="image.jsp" width="60" height="20" border="1"><!-- 这里调用iamge.jsp作为验证码的资源 -->
												</td>
												<td height="36" colspan="2" align="center" valign="middle">
												<a href="javascript:loadimage();"><font class=pt95>刷新<!-- 调用js函数，见页面上面 -->
													<%System.out.println("我要看一看，刷新之后，是否把验证码的值存入了session"+session.getAttribute("memeda")); %></font>
												</a>
												</td>
											</tr>
											<tr>
												<td height="50">&nbsp;
												</td>
												<td height="36" colspan="2" align="center" valign="middle">
												
												<input type="submit" value="登录" name="login"
												style="border:0px;width:84px;height:30px;" /> 
												
												<input type="button" value="注册" onclick="f1();"style="border:20px;width:84px;height:30px;"/>
													</td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table></td>
			</tr>
		</table>
		<table width="980" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td style="height:10px;"></td>
			</tr>
		</table>
		<table width="980" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="center">Copyright <span
					style="font-family:Arial, Helvetica, sans-serif; font-size:12px">&copy;</span>
					版权信息归中南民族大学所有，此处仅作学习之用</td>
			</tr>
		</table>
		<table width="980" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td style="height:20px;"></td>
			</tr>
		</table>
	</form>
</body>
</html>
