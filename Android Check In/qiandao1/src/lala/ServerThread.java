package lala;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread
{
	private boolean flag = true;//循环标志位
	ServerSocket ss;//ServerSocket的引用
	public void run(){//重写的run方法
		try {
			ss=new ServerSocket(9995);//监听到9999端口
			System.out.println("我正在监听9995端口"+ss);
		} catch (IOException e1) {//捕获异常
			e1.printStackTrace();//打印异常信息
		}
		while(flag){//进入主循环
			try{//ss得到用户连接就仙剑一个socket sc，serveagent调用sc创建一个sa
				System.out.println("主循环的过程是，全局变量我声明一个servesocket ss,然后ss监听9999端口，");
				System.out.println("我正在等待用户连接~~~~"+flag);
				Socket sc=ss.accept();//阻塞式方法，等待用户连接
				System.out.println("如果有用户连接我就打赢这一句~~~~~~~~~~"+ss+sc);
				ServerAgent sa = new ServerAgent(sc,this);//创建代理线程
				System.out.println("我创建了一个代理线程serveagent"+sa);
				sa.start();//启动代理线程
				System.out.println("我启动了这个代理线程serveagent sa");
			}
			catch(Exception e){//捕获异常
				e.printStackTrace();//打印异常
			}
		}
	}
	public void setFlag(boolean flag){//设置循环变量
		this.flag = flag;
	}
}