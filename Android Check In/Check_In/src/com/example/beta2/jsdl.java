package com.example.beta2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TabHost.TabSpec;

import android.widget.CheckBox;
import android.widget.EditText;


public class jsdl extends TabActivity{

	private TabHost tabHost;
	Socket s = null;// ����Socket������
	DataOutputStream dout = null;// �����
	DataInputStream din = null;// ������
	TextView jsdltv1;
	
	String cpms = "f";
	String opms = "t";
	
	
	ActionBar actionBar;
 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jsdl);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);					
		actionBar.setDisplayHomeAsUpEnabled(true);
		tabHost = getTabHost();//��ȡ����
		TabWidget tabWidget = getTabWidget();
		LayoutInflater.from(this).inflate(R.layout.jsdl_qd,tabHost.getTabContentView(), true);

		//������䣬Ӧ����һ��ӳ��Ĺ�ϵ��from��this����Ҳ����˵���������������Ĳ���at_first�ļ�����ͨ��ӳ��one_item��ʵ�ֿؼ��ļ���
		TabSpec ts1 = tabHost
				.newTabSpec("tab1")  //����ϵͳ����ǩ1
				.setIndicator("ǩ��",  //��ǩ1������
				getResources().getDrawable(R.drawable.ic_launcher))//��ǩ1 ��ͼ��
				.setContent(R.id.jsdl_qd);   //��ǩ1ӳ��󣬶�Ӧ�Ĳ���
		TextView jsdltv1 = (TextView)findViewById(R.id.jsdltv1);	
		Bundle bundle=this.getIntent().getExtras();	
		final String tname=bundle.getString("t_name");
		final String tid=bundle.getString("t_id");
		jsdltv1.setText(tname);		
		
			
		tabHost.addTab(ts1);
		
		
		//--------------------------
		
		//����Ϊԭ�е�ǩ��
				Button checking = (Button) findViewById(R.id.button1);
				checking.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

   				final Toast toast = Toast.makeText(getApplicationContext(),
					     "�¿�����һ��ǩ��", Toast.LENGTH_SHORT);
					   toast.setGravity(Gravity.CENTER, 0, 0);
				final Toast toast1 = Toast.makeText(getApplicationContext(),
							     "�¿���ʧ��", Toast.LENGTH_SHORT);
							   toast1.setGravity(Gravity.CENTER, 0, 0);
			
				new Thread() {
						public void run() {
							try {// �������粢����
								//�޸�����ip��ַ����
								s = new Socket("192.168.191.1",9997);
								System.out.println("new Socket");
								dout = new DataOutputStream(s.getOutputStream());
								din = new DataInputStream(s.getInputStream());
								System.out.println("in "+din);
								System.out.println("out "+dout);
							} catch (Exception e) {// �����쳣
								e.printStackTrace();// ��ӡ�쳣

							}
							String opms = "t";
							String msg = "<#OPMS#>" + tid + "|" +opms ;
							System.out.println("msg "+msg);
							try {
								dout.writeUTF(msg);// �����������ע����Ϣ
								System.out.println("�����������ע����Ϣ");
							
								String msg2 = din.readUTF();// ���շ���������������Ϣ
								System.out.println("���շ���������������Ϣ");
								if (msg2.startsWith("<#OPMSOK#>")) {// ����Ȩ�޳ɹ�
									toast.show();
								}
								else{
									toast1.show();
								}
								 
							} catch (IOException e) {// �����쳣
								e.printStackTrace();// ��ӡ�쳣
							} finally {
								try {
									if (dout != null) {
										dout.close();
										dout = null;
										System.out.println("dout = null;");
									}
								} catch (Exception e) {// �����쳣
									e.printStackTrace();// ��ӡ�쳣��Ϣ
								}
								try {
									if (din != null) {
										din.close();
										din = null;
										System.out.println("din = null;");
									}
								} catch (Exception e) {// �����쳣
									e.printStackTrace();// ��ӡ�쳣��Ϣ
								}
								try {
									if (s != null) {
										s.close();
										s = null;
										System.out.println("s = null;");
									}
								} catch (Exception e) {// �����쳣
									e.printStackTrace();// ��ӡ�쳣��Ϣ
								}

					  }
						}
					}.start();
				}});

   				
				Button bt2 = (Button)findViewById(R.id.button2);
				bt2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						final Toast toast2 = Toast.makeText(getApplicationContext(),
							     "�ر��˴˴�ǩ��", Toast.LENGTH_SHORT);
							   toast2.setGravity(Gravity.CENTER, 0, 0);
						final Toast toast3 = Toast.makeText(getApplicationContext(),
									     "�ر�ʧ��", Toast.LENGTH_SHORT);
									   toast3.setGravity(Gravity.CENTER, 0, 0);
						new Thread() {
							public void run() {
								try {// �������粢����
									//�޸�����ip��ַ����
									s = new Socket("192.168.191.1",9997);
									System.out.println("new Socket");
									dout = new DataOutputStream(s.getOutputStream());

									din = new DataInputStream(s.getInputStream());
									System.out.println("in "+din);
									System.out.println("out "+dout);
								} catch (Exception e) {// �����쳣
									e.printStackTrace();// ��ӡ�쳣

								}
							
								String cpms = "f";
								String msg = "<#CPMS#>" + tid + "|" +cpms ;
								System.out.println("msg "+msg);
								try {
									dout.writeUTF(msg);// �������������Ϣ
									System.out.println("�����������ע����Ϣ");
								
									String msg2 = din.readUTF();// ���շ���������������Ϣ
									System.out.println("���շ���������������Ϣ");
									if (msg2.startsWith("<#CPMSOK#>")) {// �ر�Ȩ�޳ɹ�
										toast2.show();
									}
									else{
										toast3.show();
									}
									 
								} catch (IOException e) {// �����쳣
									e.printStackTrace();// ��ӡ�쳣
								} finally {
									try {
										if (dout != null) {
											dout.close();
											dout = null;
											System.out.println("dout = null;");
										}
									} catch (Exception e) {// �����쳣
										e.printStackTrace();// ��ӡ�쳣��Ϣ
									}
									try {
										if (din != null) {
											din.close();
											din = null;
											System.out.println("din = null;");
										}
									} catch (Exception e) {// �����쳣
										e.printStackTrace();// ��ӡ�쳣��Ϣ
									}
									try {
										if (s != null) {
											s.close();
											s = null;
											System.out.println("s = null;");
										}
									} catch (Exception e) {// �����쳣
										e.printStackTrace();// ��ӡ�쳣��Ϣ
									}

						  }
							}
						}.start();
						
					}
				});
				
	}
	//������ԭ�е�ǩ������
			@Override
			public boolean onCreateOptionsMenu(Menu menu)
			{
				MenuInflater inflator = new MenuInflater(this);
				// ״̬R.menu.context��Ӧ�Ĳ˵�������ӵ�menu��
				inflator.inflate(R.menu.main, menu);
				for(int i=0; i<menu.size(); i++)
				{             
					menu.getItem(i).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);   
					}
			return super.onCreateOptionsMenu(menu);
			}

			@Override
			// ѡ��˵��Ĳ˵��������Ļص�����
			public boolean onOptionsItemSelected(MenuItem mi)
			{
				if(mi.isCheckable())
				{
					mi.setChecked(true);  //��
				}
				// �жϵ��������ĸ��˵��������Ե�������Ӧ��
				switch (mi.getItemId())
				{
					case android.R.id.home:
					Intent intent = new Intent(jsdl.this, MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
						break;
					case R.id.help:
						Intent i1 = new Intent(jsdl.this,help.class);
						 startActivity(i1);
						 finish();
						 break;
					case R.id.tc:
						showDialog(0);
						break;
					/*case R.id.gl1:
						showDialog(0);
						break;
					case R.id.gl2:
						showDialog(0);
						break;
					case R.id.gl3:
						showDialog(0);
						break;
					*/
				}
				return true;
			}
}