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
	Socket sc;//����Socket������
	ServerThread father;//����ServerThread������
	DataInputStream din = null;//������
	DataOutputStream dout = null;//�����
	private boolean flag=true;//ѭ������ 
	
	public ServerAgent(Socket sc,ServerThread father){//������
		System.out.println("00000000000��Ҫ������ʼ����ֵŶ"+din+dout+flag);
		this.sc=sc;//�õ�Socket
		this.father=father;//�õ�ServerThread������
		System.out.println("11111111111111��Ҫ������ʼ����ֵŶ"+sc+father);
		try{
			din=new DataInputStream(sc.getInputStream());//������
			System.out.println("222222��Ҫ������ʼ����ֵŶ"+din+dout+flag);
			dout=new DataOutputStream(sc.getOutputStream());//�������
			System.out.println("3333333333��Ҫ������ʼ����ֵŶ"+din+dout+flag);
		}
		catch(Exception e){//�����쳣
			e.printStackTrace();//��ӡ�쳣
		}
	}
	public void run(){
		while(flag){//ѭ��
			try{
				System.out.println("444444444444��Ҫ������ʼ����ֵŶ"+din+dout+flag);
				
				String msg=din.readUTF();//����Ϣ
				System.out.println("5555555555"+din+dout+flag);
				System.out.println("������յ���׿�˵���Ϣ�Ҿʹ�ӡ���Client msg = " + msg);
				
				//ѧ����¼����
				if(msg.startsWith("<#LOGIN#>")){
					msg=msg.substring(9);//��ȡ�Ӵ�
					String[] str = msg.split("\\|");//�ָ��ַ���
					String u_name = DBUtil.android_check_student_user(str[0], str[1]);//������ݿ����Ƿ��и��û�
					if(u_name == null){//��u_nameΪ��ʱ����¼ʧ��
						System.out.println("u_name == null");
						dout.writeUTF("<#LOGINERROR#>");//֪ͨ�ֻ��ͻ��˵�¼ʧ��
					}
					else {//��¼�ɹ�
						System.out.println(u_name+" <#sLOGINOK#>");
						dout.writeUTF("<#LOGINOK#>"+u_name);//��¼�ɹ������û�������
					}
					break;
				}
				//��ʦ��¼����
				else if(msg.startsWith("<#TLOGIN#>")){
					msg=msg.substring(10);//��ȡ�Ӵ�
					String[] str = msg.split("\\|");//�ָ��ַ���
					String u_name = DBUtil.android_check_teacher_user(str[0], str[1]);//������ݿ����Ƿ��и��û�
					if(u_name == null){//��u_nameΪ��ʱ����¼ʧ��
						System.out.println("u_name == null");
						dout.writeUTF("<#TLOGINERROR#>");//֪ͨ�ֻ��ͻ��˵�¼ʧ��
					}
					else {//��¼�ɹ�
						System.out.println(u_name+" <#sTALOGINOK#>");
						dout.writeUTF("<#TLOGINOK#>"+u_name);//��¼�ɹ������û�������
					}
					break;
				}
				//ѧ��ע�ᶯ��
				else if(msg.startsWith("<#REGISTER#>")){
					msg=msg.substring(12);//��ȡ�Ӵ�
					String[] str = msg.split("\\|");//�ָ��ַ���
					String uid = DBUtil.android_insert_student_user(str[0], str[1], str[2], str[3], str[4]);
					dout.writeUTF("<#REGISTEROK#>"+str[0]+"|"+uid);//��ͻ��˷����û�ID���û���
					break;
				}
				//��ʦע�ᶯ��
				else if(msg.startsWith("<#TREGISTER#>")){
					msg=msg.substring(13);//��ȡ�Ӵ�
					String[] str = msg.split("\\|");//�ָ��ַ���
					String uid = DBUtil.android_insert_teacher_user(str[0], str[1], str[2],str[3]);
					dout.writeUTF("<#TREGISTEROK#>"+str[0]+"|"+uid);//��ͻ��˷����û�ID���û���
					break;
				}
				//��ʦ����Ȩ��
				else if(msg.startsWith("<#OPMS#>")){
					msg=msg.substring(8);//��ȡ�Ӵ�
					System.out.println("cccccccccccc"+msg);
					String[] str = msg.split("\\|");//�ָ��ַ���
					String uid = DBUtil.open_permission(str[0], str[1]);
					System.out.println("cccccccccccc"+uid);
					dout.writeUTF("<#OPMSOK#>"+str[0]+"|"+uid);//��ͻ��˷����û�ID��Ȩ��
					break;
				}
				//��ʦ��Ȩ��
				else if(msg.startsWith("<#CPMS#>")){
					msg=msg.substring(8);//��ȡ�Ӵ�
					String[] str = msg.split("\\|");//�ָ��ַ���
					String uid = DBUtil.close_permission(str[0], str[1]);
					dout.writeUTF("<#CPMSOK#>"+str[0]+"|"+uid);//��ͻ��˷����û�ID��Ȩ��
					break;
				}
				//ѧ��ˢ�¶���
				else if(msg.startsWith("<#SX#>")){
					msg=msg.substring(6);//��ȡ�Ӵ�
					System.out.println("ˢ��Ȩ��ʱ��������ֵ"+msg);
					String[] str = msg.split("\\|");//�ָ��ַ���
					String cid = DBUtil.SXquanxian( str[0],str[1]);//������ݿ����Ƿ��и��û�
					System.out.println("���ҵ���id"+cid);
						if(cid == null){
						dout.writeUTF("<#SXERROR#>");}
						else{
							String cname = DBUtil.SXquanxian1( cid );
							dout.writeUTF("<#SXOK#>"+cname);
						}
					break;
				}
				//ѧ��ǩ������
				else if(msg.startsWith("<#QD#>")){
					msg=msg.substring(6);//��ȡ�Ӵ�
					String[] str = msg.split("\\|");//�ָ��ַ���
					String la = DBUtil.QDgongneng(str[0],str[1],str[2],str[3]);
					System.out.println("��һ��ǩ���󷵻ص�ֵ��"+la);
					if(la.equals("t"))
					{dout.writeUTF("<#QDOK#>");}
					else
					{dout.writeUTF("<#QDERROR#>");}//��ͻ��˷����û�ID���û���
					break;
				}
				else if(msg.startsWith("<#ClientDown#>")){//�ͻ�������
					try{
						din.close();//�ر�������
						dout.close();//�ر������
						sc.close();//�ر�Socket
						flag = false;
					}
					catch(Exception e){//�����쳣
						e.printStackTrace();//��ӡ�쳣
					}
				}
			}
			catch(EOFException e){//�����쳣
				
				flag = false;
				System.out.println("��������⣬��ô�Ҿʹ�ӡ��䣬�������Ͷ���Client Down"+flag);
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
			catch(Exception e){//�����쳣
				e.printStackTrace();//��ӡ�쳣
			}
		}
	}
	public void setFlag(boolean flag){//ѭ����־λ�����÷���
		this.flag = flag;
	}
}
