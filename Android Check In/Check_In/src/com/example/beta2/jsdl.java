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
	Socket s = null;// 声明Socket的引用
	DataOutputStream dout = null;// 输出流
	DataInputStream din = null;// 输入流
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
		tabHost = getTabHost();//获取容器
		TabWidget tabWidget = getTabWidget();
		LayoutInflater.from(this).inflate(R.layout.jsdl_qd,tabHost.getTabContentView(), true);

		//上面这句，应该是一个映射的关系，from（this），也就是说，现在这个主界面的布局at_first文件，是通过映射one_item来实现控件的加载
		TabSpec ts1 = tabHost
				.newTabSpec("tab1")  //告诉系统，标签1
				.setIndicator("签到",  //标签1的名字
				getResources().getDrawable(R.drawable.ic_launcher))//标签1 的图标
				.setContent(R.id.jsdl_qd);   //标签1映射后，对应的布局
		TextView jsdltv1 = (TextView)findViewById(R.id.jsdltv1);	
		Bundle bundle=this.getIntent().getExtras();	
		final String tname=bundle.getString("t_name");
		final String tid=bundle.getString("t_id");
		jsdltv1.setText(tname);		
		
			
		tabHost.addTab(ts1);
		
		
		//--------------------------
		
		//以下为原有的签到
				Button checking = (Button) findViewById(R.id.button1);
				checking.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

   				final Toast toast = Toast.makeText(getApplicationContext(),
					     "新开启了一次签到", Toast.LENGTH_SHORT);
					   toast.setGravity(Gravity.CENTER, 0, 0);
				final Toast toast1 = Toast.makeText(getApplicationContext(),
							     "新开启失败", Toast.LENGTH_SHORT);
							   toast1.setGravity(Gravity.CENTER, 0, 0);
			
				new Thread() {
						public void run() {
							try {// 连接网络并打开流
								//修改以下ip地址即可
								s = new Socket("192.168.191.1",9997);
								System.out.println("new Socket");
								dout = new DataOutputStream(s.getOutputStream());
								din = new DataInputStream(s.getInputStream());
								System.out.println("in "+din);
								System.out.println("out "+dout);
							} catch (Exception e) {// 捕获异常
								e.printStackTrace();// 打印异常

							}
							String opms = "t";
							String msg = "<#OPMS#>" + tid + "|" +opms ;
							System.out.println("msg "+msg);
							try {
								dout.writeUTF(msg);// 向服务器发送注册消息
								System.out.println("向服务器发送注册消息");
							
								String msg2 = din.readUTF();// 接收服务器发送来的消息
								System.out.println("接收服务器发送来的消息");
								if (msg2.startsWith("<#OPMSOK#>")) {// 开启权限成功
									toast.show();
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
				}});

   				
				Button bt2 = (Button)findViewById(R.id.button2);
				bt2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						final Toast toast2 = Toast.makeText(getApplicationContext(),
							     "关闭了此次签到", Toast.LENGTH_SHORT);
							   toast2.setGravity(Gravity.CENTER, 0, 0);
						final Toast toast3 = Toast.makeText(getApplicationContext(),
									     "关闭失败", Toast.LENGTH_SHORT);
									   toast3.setGravity(Gravity.CENTER, 0, 0);
						new Thread() {
							public void run() {
								try {// 连接网络并打开流
									//修改以下ip地址即可
									s = new Socket("192.168.191.1",9997);
									System.out.println("new Socket");
									dout = new DataOutputStream(s.getOutputStream());

									din = new DataInputStream(s.getInputStream());
									System.out.println("in "+din);
									System.out.println("out "+dout);
								} catch (Exception e) {// 捕获异常
									e.printStackTrace();// 打印异常

								}
							
								String cpms = "f";
								String msg = "<#CPMS#>" + tid + "|" +cpms ;
								System.out.println("msg "+msg);
								try {
									dout.writeUTF(msg);// 向服务器发送消息
									System.out.println("向服务器发送注册消息");
								
									String msg2 = din.readUTF();// 接收服务器发送来的消息
									System.out.println("接收服务器发送来的消息");
									if (msg2.startsWith("<#CPMSOK#>")) {// 关闭权限成功
										toast2.show();
									}
									else{
										toast3.show();
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
				});
				
	}
	//以下是原有的签到函数
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