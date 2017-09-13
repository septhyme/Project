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
	
	Socket s = null;// 声明Socket的引用
	DataOutputStream dout = null;// 输出流
	DataInputStream din = null;// 输入流
	
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
  				
  				if (v == registerButton) {// 按下注册界面的"立即注册"按钮
  					
  					String T_id = t_id.getText().toString();
  					String T_pwd = t_pwd.getText().toString();
  					String T_pwd2 = t_pwd2.getText().toString();
  					String T_phone = t_phone.getText().toString();
  					String T_name = t_name.getText().toString();
  					

  					if (T_id.trim().equals("")) {// 当用户名为空时
  						Toast.makeText(getApplicationContext(), "工号不能为空!", Toast.LENGTH_SHORT).show();
  						return;
  					} else if (T_id.length()!=7){
  						Toast.makeText(getApplicationContext(), "请输入7位工号", Toast.LENGTH_SHORT).show();
  						return;
  					} else if (T_phone.length()!=11){
  						Toast.makeText(getApplicationContext(), "请输入11位手机号", Toast.LENGTH_SHORT).show();
  						return;
  					} 
  					else if (T_pwd.trim().equals("")) {// 密码不能为空
  						Toast.makeText(getApplicationContext(), "密码不能为空!", Toast.LENGTH_SHORT).show();
  						return;
  					}	
  						else if (T_name.trim().equals("")) {
  							Toast.makeText(getApplicationContext(), "姓名不能为空!", Toast.LENGTH_SHORT).show();
  	  						return;
  	  						
  					} else if (!T_pwd.trim().equals(T_pwd2.trim())) {// 两次密码不一致时
  						Toast.makeText(getApplicationContext(), "两次密码不一致!", Toast.LENGTH_SHORT).show();
  						return;
  					}
  				
  				   final Toast toast = Toast.makeText(getApplicationContext(),"注册成功", Toast.LENGTH_LONG);
  				   toast.setGravity(Gravity.CENTER, 0, 0);
  				
  				   final Toast toast1 = Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_LONG);
  				   toast1.setGravity(Gravity.CENTER, 0, 0);
  						
  					new Thread() {
  						public void run() {
  							try {// 连接网络并打开流
  							//修改以下ip地址即可
  								System.out.println(" 连接网络并打开流");
  								s = new Socket("192.168.191.1", 9997);
  								System.out.println("new Socket");
  								dout = new DataOutputStream(s.getOutputStream());
  								din = new DataInputStream(s.getInputStream());
  								System.out.println("in "+din);
  								System.out.println("out "+dout);
  							} catch (Exception e) {// 捕获异常
  								e.printStackTrace();// 打印异常

  							}

  							String T_id = t_id.getText().toString();// 账号
  							String T_pwd = t_pwd.getText().toString();// 密码
  							String T_phone = t_phone.getText().toString();// 手机号
  							String T_name = t_name.getText().toString();

  							String msg = "<#TREGISTER#>"  + T_id + "|" +T_name + "|"
  									+ T_phone + "|" +T_pwd ;
  							System.out.println("msg "+msg);
  							try {
  								dout.writeUTF(msg);// 向服务器发送消息
  								System.out.println("向服务器发送注册消息");
  							
  								String msg2 = din.readUTF();// 接收服务器发送来的消息
  								System.out.println("接收服务器发送来的消息");
  								if (msg2.startsWith("<#TREGISTEROK#>")) {// 注册成功
  									
  									msg2 = msg2.substring(11);// 截取字串
  									String[] str = msg2.split("\\|");// 分割字符串
  									toast.show();
  									Intent intent = new Intent();
  									intent.setClass(jszc.this, jsdl.class);
  									
  									startActivity(intent);
  									
  									jszc.this.finish();
  								}
  								else{
  									toast1.show();
  								}
  								 
  							} catch (IOException e) {// 捕获异常
  								e.printStackTrace();// 打印异常
  							} finally {
  								try {
  									if (dout != null) {
  										dout.close();
  										dout = null;
  										System.out.println("dout = null;");
  									}
  								} catch (Exception e) {// 捕获异常
  									e.printStackTrace();// 打印异常信息
  								}
  								try {
  									if (din != null) {
  										din.close();
  										din = null;
  										System.out.println("din = null;");
  									}
  								} catch (Exception e) {// 捕获异常
  									e.printStackTrace();// 打印异常信息
  								}
  								try {
  									if (s != null) {
  										s.close();
  										s = null;
  										System.out.println("s = null;");
  									}
  								} catch (Exception e) {// 捕获异常
  									e.printStackTrace();// 打印异常信息
  								}

  					  }
  						}
  					}.start();
  				}
  				
  				
  				}
  		});	
		
		
}


	protected void cxtx() {
		// TODO 自动生成的方法存根
		t_id =(ClearEditText)findViewById(R.id.jszc_cet1);
		t_pwd =(ClearEditText)findViewById(R.id.jszc_cet2);
		t_pwd2 =(ClearEditText)findViewById(R.id.jszc_cet3);
		t_phone =(ClearEditText)findViewById(R.id.jszc_cet4);
		t_id.setText("");
		t_pwd.setText("");
		t_pwd2.setText("");
		t_phone.setText("");
		Toast.makeText(jszc.this, "请重新填写", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflator = new MenuInflater(this);
		// 状态R.menu.context对应的菜单，并添加到menu中
		inflator.inflate(R.menu.main, menu);
		for(int i=0; i<menu.size(); i++)
		{             
			menu.getItem(i).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);   
			}
	return super.onCreateOptionsMenu(menu);
	}

	@Override
	// 选项菜单的菜单项被单击后的回调方法
	public boolean onOptionsItemSelected(MenuItem mi)
	{
		if(mi.isCheckable())
		{
			mi.setChecked(true);  //②
		}
		// 判断单击的是哪个菜单项，并针对性的作出响应。
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
