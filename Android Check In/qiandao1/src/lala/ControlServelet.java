package lala;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ControlServelet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 this.doPost(request, response);

	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {request.setCharacterEncoding("utf-8");	
		String action = request.getParameter("action");
		System.out.println("打印动作"+action);//这个是测试用来看，是否动作正确
		
		if(action == null){//当action为空时
			request.getRequestDispatcher("error.jsp?msg=action为null").forward(request,response);
			System.out.println("打印动作失败！！！");
			return;
		}
		
		//登录动作
		else if(action.equals("login")){
			String u_id = request.getParameter("u_id");
			int i=u_id.length();
			System.out.println("我要看看输入的id的长度"+i);
			String u_pwd = request.getParameter("u_pwd");
			HttpSession session=request.getSession(true);
			String rand = (String) session.getAttribute("memeda");//获取存入的session验证码值
		
			String input = request.getParameter("myrand");//获取页面输入的验证码值
			if (rand.equals(input)) {
			String u_name = DBUtil.webcheckUser(i,u_id, u_pwd);
			
			if(u_name != null){	
		    	session.setAttribute("u_id",u_id);
		    	session.setAttribute("u_name",u_name);
		    	session.setAttribute("i",i);
		    	request.getRequestDispatcher("home.jsp").forward(request,response);
			}
			else{
				request.getRequestDispatcher("error.jsp").forward(request,response);
			}
			}
			else{
				request.getRequestDispatcher("index.jsp").forward(request,response);
			}
		}
		else if(action.equals("exit")){//注销
			HttpSession session=request.getSession(true);
			session.removeAttribute("u_id");
			session.removeAttribute("u_name");
			request.getRequestDispatcher("index.jsp").forward(request,response);
			//在这里应当加一个刷新的语句，因为后退之后还是会回来，只是不能在进行操作了
			
		}
		else if(action.equals("updateUserInfo")){//更新用户信息动作
			HttpSession session=request.getSession(true);
			String u_id = (String) session.getAttribute("u_id");
			String u_name = request.getParameter("u_name");
			String u_pwd = request.getParameter("u_pwd1");
			String u_phone = request.getParameter("u_phone");
			Object i=session.getAttribute("i");
			String newu_name=DBUtil.webupdateUserInfo(i,u_id,u_name,u_pwd,u_phone);
			session.setAttribute("u_name",newu_name);
			request.getRequestDispatcher("refresh.jsp").forward(request,response);
			
		}
			
	}
		
	
					
		//检查是否登陆过的函数
	public boolean isLogin(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession(true);
		String u_name = (String) session.getAttribute("u_name");
		if(u_name == null){//没有登录过
			return false;
		}
		else{//登录过
			return true;
		}

	}

	
}
