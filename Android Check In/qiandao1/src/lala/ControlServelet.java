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
		System.out.println("��ӡ����"+action);//����ǲ������������Ƿ�����ȷ
		
		if(action == null){//��actionΪ��ʱ
			request.getRequestDispatcher("error.jsp?msg=actionΪnull").forward(request,response);
			System.out.println("��ӡ����ʧ�ܣ�����");
			return;
		}
		
		//��¼����
		else if(action.equals("login")){
			String u_id = request.getParameter("u_id");
			int i=u_id.length();
			System.out.println("��Ҫ���������id�ĳ���"+i);
			String u_pwd = request.getParameter("u_pwd");
			HttpSession session=request.getSession(true);
			String rand = (String) session.getAttribute("memeda");//��ȡ�����session��֤��ֵ
		
			String input = request.getParameter("myrand");//��ȡҳ���������֤��ֵ
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
		else if(action.equals("exit")){//ע��
			HttpSession session=request.getSession(true);
			session.removeAttribute("u_id");
			session.removeAttribute("u_name");
			request.getRequestDispatcher("index.jsp").forward(request,response);
			//������Ӧ����һ��ˢ�µ���䣬��Ϊ����֮���ǻ������ֻ�ǲ����ڽ��в�����
			
		}
		else if(action.equals("updateUserInfo")){//�����û���Ϣ����
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
		
	
					
		//����Ƿ��½���ĺ���
	public boolean isLogin(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession(true);
		String u_name = (String) session.getAttribute("u_name");
		if(u_name == null){//û�е�¼��
			return false;
		}
		else{//��¼��
			return true;
		}

	}

	
}
