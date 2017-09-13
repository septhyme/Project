package com.example.beta2;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class xszc extends Activity{
	TelephonyManager tm;
	EditText sname = null;//ע�������û���
	EditText sid = null;//ע������id
	EditText spwd = null;//����
	EditText spwd1 = null;//����
	EditText sphone = null;//�ֻ���
	Socket s = null;//����Socket������
	DataOutputStream dout = null;//�����
	DataInputStream din = null;//������	
	ProgressDialog myDialog = null;//���ȿ�
	ActionBar actionBar;
	Button btn1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xszc);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);//===��ȡ���ֻ����룬����ʾ====
		TextView tv5 =(TextView)findViewById(R.id.xszctv5);
		tv5.setText(tm.getDeviceId());
		//========================
		sname = (EditText)findViewById(R.id.xszc_cET1);
		sid = (EditText)findViewById(R.id.xszc_cET2);
		spwd = (EditText)findViewById(R.id.s_pwd);
		System.out.println("spwd1��ʼ");
		spwd1 = (EditText)findViewById(R.id.s_pwd1);
		System.out.println("spwd1����");
		sphone = (EditText)findViewById(R.id.xszc_cET3);
        //========================
		
		btn1 =(Button)this.findViewById(R.id.xszcbt1);
		btn1.setOnClickListener(new OnClickListener()
  		{
  			@Override
  			public void onClick(View v)
  			{
  				String S_name = sname.getText().toString();
  				String S_id = sid.getText().toString();
  				String S_pwd = spwd.getText().toString();
  				String S_pwd1 = spwd1.getText().toString();
  				String S_phone = sphone.getText().toString();
  				


  				if(S_name.trim().equals("")){//������Ϊ��ʱ
  					Toast toast = Toast.makeText(getApplicationContext(),
						     "��������Ϊ��", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
						   
  					return;}
  				else if(S_id.trim().equals("")){//ѧ�Ų���Ϊ��
  					Toast toast = Toast.makeText(getApplicationContext(),  "ѧ�Ų���Ϊ��", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
  					return;}
  				else if ((S_id.length()!=8)&&(S_id.length()!=10)){
  					Toast toast = Toast.makeText(getApplicationContext(),  "������8λ��10λѧ��", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
						  
					return;}
  				else if(S_pwd.trim().equals("")){//���벻��Ϊ��
  					Toast toast = Toast.makeText(getApplicationContext(), "���벻��Ϊ��", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
  					return;}
  				else if(S_pwd1.trim().equals("")){//���벻��Ϊ��
  					Toast toast = Toast.makeText(getApplicationContext(), "���벻��Ϊ��", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
  					return;}
  				else if (!S_pwd.trim().equals(S_pwd1.trim())) {// �������벻һ��ʱ
  					Toast toast = Toast.makeText(getApplicationContext(), "�����������벻һ�£�", Toast.LENGTH_SHORT);
					       toast.setGravity(Gravity.CENTER, 0, 0);
					       toast.show();
					return;}
  				else if(S_phone.length()!=11){//�ֻ��Ų���Ϊ��
  					Toast toast = Toast.makeText(getApplicationContext(), "��������ȷ���ֻ���", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
  					return;}
			    
	                        
  				final Toast toast = Toast.makeText(getApplicationContext(), "ע��ɹ�", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
  				
			new Thread(){
  					public void run(){
  						//�޸�����ip��ַ����
  						try{//�������粢����192.168.56.101
  					        s = new Socket("192.168.191.1", 9997);
  					        dout = new DataOutputStream(s.getOutputStream());
  					        din = new DataInputStream(s.getInputStream());
  						}catch(Exception e){//�����쳣
  							e.printStackTrace();//��ӡ�쳣
  						}
  						String S_name = sname.getText().toString();
  						String S_id	= sid.getText().toString();
  						String S_pwd = spwd.getText().toString();
  						String S_phone = sphone.getText().toString();
  						String S_phonecm = tm.getDeviceId();
  						String msg = "<#REGISTER#>"+S_id+"|"+S_name+"|"+S_pwd+"|"+S_phone+"|"+S_phonecm;
  						System.out.println("����ע������"+msg);
  						try {
  							dout.writeUTF(msg);//�����������ע����Ϣ
  							String msg2 = din.readUTF();//���շ���������������Ϣ
  							System.out.println("����ע������"+msg2);
  							if(msg2.startsWith("<#REGISTEROK#>")){//ע��ɹ�		
  								toast.show();
  								Intent intent = new Intent();
  								intent.setClass(xszc.this, MainActivity.class);
  								startActivity(intent);
  								xszc.this.finish();
  							}
  							else {
  								///////////////////
  								Toast toast = Toast.makeText(getApplicationContext(),
  									     "�ֻ��Ų���Ϊ��", Toast.LENGTH_SHORT);
  									   toast.setGravity(Gravity.CENTER, 0, 0);
  									   toast.show();
  								//////////////////
  							}
  						} catch (IOException e) {//�����쳣
  							e.printStackTrace();//��ӡ�쳣
  						} finally{
  							try{
  								if(dout != null){
  									dout.close();
  									dout = null;
  								}
  							}
  							catch(Exception e){//�����쳣
  								e.printStackTrace();//��ӡ�쳣��Ϣ
  							}
  							try{
  								if(din != null){
  									din.close();
  									din = null;
  								}
  							}
  							catch(Exception e){//�����쳣
  								e.printStackTrace();//��ӡ�쳣��Ϣ
  							}
  							try{
  								if(s != null){
  									s.close();
  									s = null;
  								}
  							}
  							catch(Exception e){//�����쳣
  								e.printStackTrace();//��ӡ�쳣��Ϣ
  							}
//  							myDialog.dismiss();
  						}				
  					}
  				}.start();
  				}
  		});	
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflator = new MenuInflater(this);// ״̬R.menu.context��Ӧ�Ĳ˵�������ӵ�menu��
		inflator.inflate(R.menu.main, menu);
		for(int i=0; i<menu.size(); i++)
		{             
			menu.getItem(i).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);   
			}
	return super.onCreateOptionsMenu(menu);
	}

	@Override// ѡ��˵��Ĳ˵��������Ļص�����
	public boolean onOptionsItemSelected(MenuItem mi)
	{
		if(mi.isCheckable())
		{
			mi.setChecked(true);  // �жϵ��������ĸ��˵��������Ե�������Ӧ��
		}
		switch (mi.getItemId())
		{
			case android.R.id.home:
				Intent intent = new Intent(xszc.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
				break;
			case R.id.help:
				Intent i1 = new Intent(xszc.this,help.class);
				 startActivity(i1);
				 finish();
				 break;
			case R.id.tc:
				showDialog(0);
				break;	
		}
		return true;
	}
}

