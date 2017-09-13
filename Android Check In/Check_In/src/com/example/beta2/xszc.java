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
	EditText sname = null;//注册界面的用户名
	EditText sid = null;//注册界面的id
	EditText spwd = null;//密码
	EditText spwd1 = null;//密码
	EditText sphone = null;//手机号
	Socket s = null;//声明Socket的引用
	DataOutputStream dout = null;//输出流
	DataInputStream din = null;//输入流	
	ProgressDialog myDialog = null;//进度框
	ActionBar actionBar;
	Button btn1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xszc);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);//===获取到手机串码，并显示====
		TextView tv5 =(TextView)findViewById(R.id.xszctv5);
		tv5.setText(tm.getDeviceId());
		//========================
		sname = (EditText)findViewById(R.id.xszc_cET1);
		sid = (EditText)findViewById(R.id.xszc_cET2);
		spwd = (EditText)findViewById(R.id.s_pwd);
		System.out.println("spwd1开始");
		spwd1 = (EditText)findViewById(R.id.s_pwd1);
		System.out.println("spwd1结束");
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
  				


  				if(S_name.trim().equals("")){//姓名名为空时
  					Toast toast = Toast.makeText(getApplicationContext(),
						     "姓名不能为空", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
						   
  					return;}
  				else if(S_id.trim().equals("")){//学号不能为空
  					Toast toast = Toast.makeText(getApplicationContext(),  "学号不能为空", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
  					return;}
  				else if ((S_id.length()!=8)&&(S_id.length()!=10)){
  					Toast toast = Toast.makeText(getApplicationContext(),  "请输入8位或10位学号", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
						  
					return;}
  				else if(S_pwd.trim().equals("")){//密码不能为空
  					Toast toast = Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
  					return;}
  				else if(S_pwd1.trim().equals("")){//密码不能为空
  					Toast toast = Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
  					return;}
  				else if (!S_pwd.trim().equals(S_pwd1.trim())) {// 两次密码不一致时
  					Toast toast = Toast.makeText(getApplicationContext(), "两次输入密码不一致！", Toast.LENGTH_SHORT);
					       toast.setGravity(Gravity.CENTER, 0, 0);
					       toast.show();
					return;}
  				else if(S_phone.length()!=11){//手机号不能为空
  					Toast toast = Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
  					return;}
			    
	                        
  				final Toast toast = Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
  				
			new Thread(){
  					public void run(){
  						//修改以下ip地址即可
  						try{//连接网络并打开流192.168.56.101
  					        s = new Socket("192.168.191.1", 9997);
  					        dout = new DataOutputStream(s.getOutputStream());
  					        din = new DataInputStream(s.getInputStream());
  						}catch(Exception e){//捕获异常
  							e.printStackTrace();//打印异常
  						}
  						String S_name = sname.getText().toString();
  						String S_id	= sid.getText().toString();
  						String S_pwd = spwd.getText().toString();
  						String S_phone = sphone.getText().toString();
  						String S_phonecm = tm.getDeviceId();
  						String msg = "<#REGISTER#>"+S_id+"|"+S_name+"|"+S_pwd+"|"+S_phone+"|"+S_phonecm;
  						System.out.println("发送注册数据"+msg);
  						try {
  							dout.writeUTF(msg);//向服务器发送注册消息
  							String msg2 = din.readUTF();//接收服务器发送来的消息
  							System.out.println("接受注册数据"+msg2);
  							if(msg2.startsWith("<#REGISTEROK#>")){//注册成功		
  								toast.show();
  								Intent intent = new Intent();
  								intent.setClass(xszc.this, MainActivity.class);
  								startActivity(intent);
  								xszc.this.finish();
  							}
  							else {
  								///////////////////
  								Toast toast = Toast.makeText(getApplicationContext(),
  									     "手机号不能为空", Toast.LENGTH_SHORT);
  									   toast.setGravity(Gravity.CENTER, 0, 0);
  									   toast.show();
  								//////////////////
  							}
  						} catch (IOException e) {//捕获异常
  							e.printStackTrace();//打印异常
  						} finally{
  							try{
  								if(dout != null){
  									dout.close();
  									dout = null;
  								}
  							}
  							catch(Exception e){//捕获异常
  								e.printStackTrace();//打印异常信息
  							}
  							try{
  								if(din != null){
  									din.close();
  									din = null;
  								}
  							}
  							catch(Exception e){//捕获异常
  								e.printStackTrace();//打印异常信息
  							}
  							try{
  								if(s != null){
  									s.close();
  									s = null;
  								}
  							}
  							catch(Exception e){//捕获异常
  								e.printStackTrace();//打印异常信息
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
		MenuInflater inflator = new MenuInflater(this);// 状态R.menu.context对应的菜单，并添加到menu中
		inflator.inflate(R.menu.main, menu);
		for(int i=0; i<menu.size(); i++)
		{             
			menu.getItem(i).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);   
			}
	return super.onCreateOptionsMenu(menu);
	}

	@Override// 选项菜单的菜单项被单击后的回调方法
	public boolean onOptionsItemSelected(MenuItem mi)
	{
		if(mi.isCheckable())
		{
			mi.setChecked(true);  // 判断单击的是哪个菜单项，并针对性的作出响应。
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

