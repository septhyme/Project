package lala;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import lala.DBUtil;
public class ServerAgent extends Thread{
	Socket sc;//声明Socket的引用
	ServerThread father;//声明ServerThread的引用
	DataInputStream din = null;//输入流
	DataOutputStream dout = null;//输出流
	private boolean flag=true;//循环变量 
	
	public ServerAgent(Socket sc,ServerThread father){//构造器
		System.out.println("00000000000我要看看初始化的值哦"+din+dout+flag);
		this.sc=sc;//得到Socket
		this.father=father;//得到ServerThread的引用
		System.out.println("11111111111111我要看看初始化的值哦"+sc+father);
		try{
			din=new DataInputStream(sc.getInputStream());//输入流
			System.out.println("222222我要看看初始化的值哦"+din+dout+flag);
			dout=new DataOutputStream(sc.getOutputStream());//输入出流
			System.out.println("3333333333我要看看初始化的值哦"+din+dout+flag);
		}
		catch(Exception e){//捕获异常
			e.printStackTrace();//打印异常
		}
	}
	public void run(){
		while(flag){//循环
			try{
				System.out.println("444444444444我要看看初始化的值哦"+din+dout+flag);
				
				String msg=din.readUTF();//收消息
				System.out.println("5555555555"+din+dout+flag);
				System.out.println("武如果收到安卓端的消息我就打印这句Client msg = " + msg);
				
				//学生登录动作
				if(msg.startsWith("<#LOGIN#>")){
					msg=msg.substring(9);//截取子串
					String[] str = msg.split("\\|");//分割字符串
					String u_name = DBUtil.android_check_student_user(str[0], str[1]);//检查数据库中是否含有该用户
					if(u_name == null){//当u_name为空时，登录失败
						System.out.println("u_name == null");
						dout.writeUTF("<#LOGINERROR#>");//通知手机客户端登录失败
					}
					else {//登录成功
						System.out.println(u_name+" <#sLOGINOK#>");
						dout.writeUTF("<#LOGINOK#>"+u_name);//登录成功，将用户名返回
					}
					break;
				}
				//教师登录动作
				else if(msg.startsWith("<#TLOGIN#>")){
					msg=msg.substring(10);//截取子串
					String[] str = msg.split("\\|");//分割字符串
					String u_name = DBUtil.android_check_teacher_user(str[0], str[1]);//检查数据库中是否含有该用户
					if(u_name == null){//当u_name为空时，登录失败
						System.out.println("u_name == null");
						dout.writeUTF("<#TLOGINERROR#>");//通知手机客户端登录失败
					}
					else {//登录成功
						System.out.println(u_name+" <#sTALOGINOK#>");
						dout.writeUTF("<#TLOGINOK#>"+u_name);//登录成功，将用户名返回
					}
					break;
				}
				//学生注册动作
				else if(msg.startsWith("<#REGISTER#>")){
					msg=msg.substring(12);//截取子串
					String[] str = msg.split("\\|");//分割字符串
					String uid = DBUtil.android_insert_student_user(str[0], str[1], str[2], str[3], str[4]);
					dout.writeUTF("<#REGISTEROK#>"+str[0]+"|"+uid);//向客户端发送用户ID和用户名
					break;
				}
				//教师注册动作
				else if(msg.startsWith("<#TREGISTER#>")){
					msg=msg.substring(13);//截取子串
					String[] str = msg.split("\\|");//分割字符串
					String uid = DBUtil.android_insert_teacher_user(str[0], str[1], str[2],str[3]);
					dout.writeUTF("<#TREGISTEROK#>"+str[0]+"|"+uid);//向客户端发送用户ID和用户名
					break;
				}
				//教师开启权限
				else if(msg.startsWith("<#OPMS#>")){
					msg=msg.substring(8);//截取子串
					System.out.println("cccccccccccc"+msg);
					String[] str = msg.split("\\|");//分割字符串
					String uid = DBUtil.open_permission(str[0], str[1]);
					System.out.println("cccccccccccc"+uid);
					dout.writeUTF("<#OPMSOK#>"+str[0]+"|"+uid);//向客户端发送用户ID和权限
					break;
				}
				//教师关权限
				else if(msg.startsWith("<#CPMS#>")){
					msg=msg.substring(8);//截取子串
					String[] str = msg.split("\\|");//分割字符串
					String uid = DBUtil.close_permission(str[0], str[1]);
					dout.writeUTF("<#CPMSOK#>"+str[0]+"|"+uid);//向客户端发送用户ID和权限
					break;
				}
				//学生刷新动作
				else if(msg.startsWith("<#SX#>")){
					msg=msg.substring(6);//截取子串
					System.out.println("刷新权限时传过来的值"+msg);
					String[] str = msg.split("\\|");//分割字符串
					String cid = DBUtil.SXquanxian( str[0],str[1]);//检查数据库中是否含有该用户
					System.out.println("查找到的id"+cid);
						if(cid == null){
						dout.writeUTF("<#SXERROR#>");}
						else{
							String cname = DBUtil.SXquanxian1( cid );
							dout.writeUTF("<#SXOK#>"+cname);
						}
					break;
				}
				//学生签到动作
				else if(msg.startsWith("<#QD#>")){
					msg=msg.substring(6);//截取子串
					String[] str = msg.split("\\|");//分割字符串
					String la = DBUtil.QDgongneng(str[0],str[1],str[2],str[3]);
					System.out.println("看一下签到后返回的值："+la);
					if(la.equals("t"))
					{dout.writeUTF("<#QDOK#>");}
					else
					{dout.writeUTF("<#QDERROR#>");}//向客户端发送用户ID和用户名
					break;
				}
				else if(msg.startsWith("<#ClientDown#>")){//客户端下线
					try{
						din.close();//关闭输入流
						dout.close();//关闭输出流
						sc.close();//关闭Socket
						flag = false;
					}
					catch(Exception e){//捕获异常
						e.printStackTrace();//打印异常
					}
				}
			}
			catch(EOFException e){//捕获异常
				
				flag = false;
				System.out.println("如果我问题，那么我就打印这句，服务器锻断了Client Down"+flag);
				try{
					if(din != null){
						din.close();
						din = null;
					}
				}
				catch(Exception el){
					el.printStackTrace();
				}
				try{
					if(dout != null){
						dout.close();
						dout = null;
					}
				}
				catch(Exception el){
					el.printStackTrace();
				}
				try{
					if(sc != null){
						sc.close();
						sc = null;
					}
				}
				catch(Exception el){
					el.printStackTrace();
				}		
			}
			catch(Exception e){//捕获异常
				e.printStackTrace();//打印异常
			}
		}
	}
	public void setFlag(boolean flag){//循环标志位的设置方法
		this.flag = flag;
	}
}
