package lala;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
public class MyServletContextListener implements ServletContextListener{
	ServerThread st = null;//�����������̵߳�����
	public void contextInitialized(ServletContextEvent sce){//��д��contextInitialized����
		sce.getServletContext().log("[ytl] Context Initialized...");
		System.out.println("��Ҫ��дһ����������Ŷ");
		st = new ServerThread();//���������߳� 
		System.out.println("�Ҵ�����һ���������߳�Ŷservethread"+st);
		st.start();//���������߳�
		System.out.println("����������������߳�Ŷst");
	}
	public void contextDestroyed(ServletContextEvent sce){//��д��contextDestroyed����
		sce.getServletContext().log("[ytl] Context Destroyed...");
		System.out.println("��Ҫ�������������ŶŶŶ"+sce);
		st.setFlag(false);//ֹͣ�����߳� 
	}
}