package lala;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
public class MyServletContextListener implements ServletContextListener{
	ServerThread st = null;//声明服务器线程的引用
	public void contextInitialized(ServletContextEvent sce){//重写的contextInitialized方法
		sce.getServletContext().log("[ytl] Context Initialized...");
		System.out.println("我要重写一个服务器了哦");
		st = new ServerThread();//创建服务线程 
		System.out.println("我创建了一个服务器线程哦servethread"+st);
		st.start();//启动服务线程
		System.out.println("我启动了这个服务线程哦st");
	}
	public void contextDestroyed(ServletContextEvent sce){//重写的contextDestroyed方法
		sce.getServletContext().log("[ytl] Context Destroyed...");
		System.out.println("我要销毁这个服务器哦哦哦"+sce);
		st.setFlag(false);//停止服务线程 
	}
}