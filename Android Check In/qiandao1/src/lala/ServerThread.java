package lala;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread
{
	private boolean flag = true;//ѭ����־λ
	ServerSocket ss;//ServerSocket������
	public void run(){//��д��run����
		try {
			ss=new ServerSocket(9995);//������9999�˿�
			System.out.println("�����ڼ���9995�˿�"+ss);
		} catch (IOException e1) {//�����쳣
			e1.printStackTrace();//��ӡ�쳣��Ϣ
		}
		while(flag){//������ѭ��
			try{//ss�õ��û����Ӿ��ɽ�һ��socket sc��serveagent����sc����һ��sa
				System.out.println("��ѭ���Ĺ����ǣ�ȫ�ֱ���������һ��servesocket ss,Ȼ��ss����9999�˿ڣ�");
				System.out.println("�����ڵȴ��û�����~~~~"+flag);
				Socket sc=ss.accept();//����ʽ�������ȴ��û�����
				System.out.println("������û������Ҿʹ�Ӯ��һ��~~~~~~~~~~"+ss+sc);
				ServerAgent sa = new ServerAgent(sc,this);//���������߳�
				System.out.println("�Ҵ�����һ�������߳�serveagent"+sa);
				sa.start();//���������߳�
				System.out.println("����������������߳�serveagent sa");
			}
			catch(Exception e){//�����쳣
				e.printStackTrace();//��ӡ�쳣
			}
		}
	}
	public void setFlag(boolean flag){//����ѭ������
		this.flag = flag;
	}
}