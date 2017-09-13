package com.example.beta2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class jszc extends Activity {
	Button registerButton;
	ActionBar actionBar;
	
	EditText t_id;
	EditText t_pwd;
	EditText t_pwd2;
	EditText t_phone;
	EditText t_name;
	
	Socket s = null;// ����Socket������
	DataOutputStream dout = null;// �����
	DataInputStream din = null;// ������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jszc);
		actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);			
		actionBar.setDisplayHomeAsUpEnabled(true);
		System.out.println("getActionBar");
		t_id =(ClearEditText)findViewById(R.id.jszc_cet1);
		t_pwd =(ClearEditText)findViewById(R.id.jszc_cet2);
		t_pwd2 =(ClearEditText)findViewById(R.id.jszc_cet3);
		t_phone =(ClearEditText)findViewById(R.id.jszc_cet4);
		t_name =(EditText)findViewById(R.id.jszc_cet5);
		registerButton =(Button)this.findViewById(R.id.jszcbt1);	
		registerButton.setOnClickListener(new OnClickListener()
  		{
			
  			@Override
  			public void onClick(View v)
  			{
  				System.out.println("onClick");
  				
  				if (v == registerButton) {// ����ע������"����ע��"��ť
  					
  					String T_id = t_id.getText().toString();
  					String T_pwd = t_pwd.getText().toString();
  					String T_pwd2 = t_pwd2.getText().toString();
  					String T_phone = t_phone.getText().toString();
  					String T_name = t_name.getText().toString();
  					

  					if (T_id.trim().equals("")) {// ���û���Ϊ��ʱ
  						Toast.makeText(getApplicationContext(), "���Ų���Ϊ��!", Toast.LENGTH_SHORT).show();
  						return;
  					} else if (T_id.length()!=7){
  						Toast.makeText(getApplicationContext(), "������7λ����", Toast.LENGTH_SHORT).show();
  						return;
  					} else if (T_phone.length()!=11){
  						Toast.makeText(getApplicationContext(), "������11λ�ֻ���", Toast.LENGTH_SHORT).show();
  						return;
  					} 
  					else if (T_pwd.trim().equals("")) {// ���벻��Ϊ��
  						Toast.makeText(getApplicationContext(), "���벻��Ϊ��!", Toast.LENGTH_SHORT).show();
  						return;
  					}	
  						else if (T_name.trim().equals("")) {
  							Toast.makeText(getApplicationContext(), "��������Ϊ��!", Toast.LENGTH_SHORT).show();
  	  						return;
  	  						
  					} else if (!T_pwd.trim().equals(T_pwd2.trim())) {// �������벻һ��ʱ
  						Toast.makeText(getApplicationContext(), "�������벻һ��!", Toast.LENGTH_SHORT).show();
  						return;
  					}
  				
  				   final Toast toast = Toast.makeText(getApplicationContext(),"ע��ɹ�", Toast.LENGTH_LONG);
  				   toast.setGravity(Gravity.CENTER, 0, 0);
  				
  				   final Toast toast1 = Toast.makeText(getApplicationContext(), "ע��ʧ��", Toast.LENGTH_LONG);
  				   toast1.setGravity(Gravity.CENTER, 0, 0);
  						
  					new Thread() {
  						public void run() {
  							try {// �������粢����
  							//�޸�����ip��ַ����
  								System.out.println(" �������粢����");
  								s = new Socket("192.168.191.1", 9997);
  								System.out.println("new Socket");
  								dout = new DataOutputStream(s.getOutputStream());
  								din = new DataInputStream(s.getInputStream());
  								System.out.println("in "+din);
  								System.out.println("out "+dout);
  							} catch (Exception e) {// �����쳣
  								e.printStackTrace();// ��ӡ�쳣

  							}

  							String T_id = t_id.getText().toString();// �˺�
  							String T_pwd = t_pwd.getText().toString();// ����
  							String T_phone = t_phone.getText().toString();// �ֻ���
  							String T_name = t_name.getText().toString();

  							String msg = "<#TREGISTER#>"  + T_id + "|" +T_name + "|"
  									+ T_phone + "|" +T_pwd ;
  							System.out.println("msg "+msg);
  							try {
  								dout.writeUTF(msg);// �������������Ϣ
  								System.out.println("�����������ע����Ϣ");
  							
  								String msg2 = din.readUTF();// ���շ���������������Ϣ
  								System.out.println("���շ���������������Ϣ");
  								if (msg2.startsWith("<#TREGISTEROK#>")) {// ע��ɹ�
  									
  									msg2 = msg2.substring(11);// ��ȡ�ִ�
  									String[] str = msg2.split("\\|");// �ָ��ַ���
  									toast.show();
  									Intent intent = new Intent();
  									intent.setClass(jszc.this, jsdl.class);
  									
  									startActivity(intent);
  									
  									jszc.this.finish();
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
  				}
  				
  				
  				}
  		});	
		
		
}


	protected void cxtx() {
		// TODO �Զ����ɵķ������
		t_id =(ClearEditText)findViewById(R.id.jszc_cet1);
		t_pwd =(ClearEditText)findViewById(R.id.jszc_cet2);
		t_pwd2 =(ClearEditText)findViewById(R.id.jszc_cet3);
		t_phone =(ClearEditText)findViewById(R.id.jszc_cet4);
		t_id.setText("");
		t_pwd.setText("");
		t_pwd2.setText("");
		t_phone.setText("");
		Toast.makeText(jszc.this, "��������д", Toast.LENGTH_SHORT).show();
	}
	
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
				Intent intent = new Intent(jszc.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
				break;
			case R.id.help:
				Intent i1 = new Intent(jszc.this,help.class);
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
