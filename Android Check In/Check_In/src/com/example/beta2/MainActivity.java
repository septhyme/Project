package com.example.beta2;




import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.net.Socket;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Menu;
import android.app.TabActivity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.ViewFlipper;

public class MainActivity extends TabActivity {
	Builder	b1;
	Builder	b2;
	private TabHost tabHost;
	EditText sid ;
	EditText spwd ;
	
	private EditText tid;//教工号
	private EditText tpwd;//教师密码
	private Button LoginButton;//教师界面“立即注册”按钮
	
	Spinner sp;
	ActionBar actionBar;
	 CheckBox cb = null;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);//主界面，实际上只是一个容器，什么控件都没有
			System.out.println("1");
			// 实例化，加载布局等一系列工作
			tabHost = getTabHost();//获取容器
			TabWidget tabWidget = getTabWidget();
		//	tabWidget.setDividerDrawable(R.drawable.tab_divider);//这个是按钮之间，进行分割的图片，这是可以设置的
			
			LayoutInflater.from(this).inflate(R.layout.help,tabHost.getTabContentView(), true);
			LayoutInflater.from(this).inflate(R.layout.js,tabHost.getTabContentView(), true);
			LayoutInflater.from(this).inflate(R.layout.xs,tabHost.getTabContentView(), true);
			
			
			//上面这句，应该是一个映射的关系，from（this），也就是说，现在这个主界面的布局at_first文件，是通过映射one_item来实现控件的加载
			TabSpec ts1 = tabHost
					.newTabSpec("tab1")  //告诉系统，标签1
					.setIndicator("学生",  //标签1的名字
							getResources().getDrawable(R.drawable.ic_launcher))//标签1 的图标
					.setContent(R.id.xs);   //标签1映射后，对应的布局,是one_item里的一个小布局
			TabSpec ts2 = tabHost
					.newTabSpec("tab2")
					.setIndicator("教师",
							getResources().getDrawable(R.drawable.ic_launcher))
					.setContent(R.id.js);
			tid = (EditText)findViewById(R.id.met2);//得到用户名文本框的引用
	 		tpwd = (EditText)findViewById(R.id.met1);//得到密码框的引用
	 		cb = (CheckBox)findViewById(R.id.mcb1);

			
			
			tabHost.addTab(ts1);
			tabHost.addTab(ts2);
		
			/////////////////////////////////////////////////////////  
			//然后都添加到容器里去,名字上面都写了，ts12345，而且他们对应使用的空白布局都是one_item
			Button LoginButton = (Button)this.findViewById(R.id.mbt1);
			 LoginButton.setOnClickListener(new OnClickListener()
		      		{
		      			@Override
		      			public void onClick(View v)
		      			{
		    				String editUid = tid.getText().toString();
		    				String editPwd = tpwd.getText().toString();
		    			
		    				if(editUid.trim().equals(""))
		    				{//当用户名为空时 
		    					Toast toast = Toast.makeText(getApplicationContext(),
			    					     "用户名不能为空", Toast.LENGTH_SHORT);
		    					toast.show();
		    					return;
		    				}
		    			
		    				if(editPwd.trim().equals(""))
		    				{//当密码为空时
		    					Toast toast = Toast.makeText(getApplicationContext(),
			    					     "密码不能为空", Toast.LENGTH_SHORT);
		    					toast.show();
		    					return;
		    				}
		    			
		    				final Toast toast = Toast.makeText(getApplicationContext(),
		    					     "登陆成功", Toast.LENGTH_SHORT);
		    					   toast.setGravity(Gravity.CENTER, 0, 0);
		    		//			   toast.show();
		    				new Thread()
		    				{//将耗时的动作放到线程中
		    					public void run()
		    					{//重写的run方法
		    						System.out.println("110708");
		    						Socket s = null;
		    						DataOutputStream dout = null;
		    						DataInputStream din = null;
		    						System.out.println("110709"+s+dout+din);
		    						try {
		    							//修改以下ip地址即可
		    							s = new Socket("192.168.191.1",9997);//连接服务器
		    							System.out.println("ss"+s);
		    							dout = new DataOutputStream(s.getOutputStream());
		    							System.out.println("out"+dout);
		    							din = new DataInputStream(s.getInputStream());
		    							System.out.println("in"+din);
		    							dout.writeUTF("<#TLOGIN#>"+tid.getText().toString()+"|"+tpwd.getText().toString());
		    						
		    							System.out.println("110711");
		    							String msg = din.readUTF();//接收服务器发来的消息
		    							System.out.println("110712");
		    			
		    							if(msg.startsWith("<#TLOGINOK#>"))
		    							{//登录成功
		    								msg=msg.substring(12);
		    								Intent intent = new Intent();
		    								intent.setClass(MainActivity.this, jsdl.class);
		    							
		    								Bundle bundle = new Bundle();
		    								bundle.putString("t_name", msg);
		    								bundle.putString("t_id",tid.getText().toString());
		    								intent.putExtras(bundle);
		    								 toast.show();
		    								startActivity(intent);
		    								MainActivity.this.finish();
		    								
		    							}
		    							else if(msg.startsWith("<#TLOGINERROR#>")){//登录失败
		    								Toast toast = Toast.makeText(getApplicationContext(),
		   			    					     "登录失败！", Toast.LENGTH_SHORT);
		   		    					    toast.show();
		    							}
		    						} catch (Exception e) 
		    						{//捕获异常
		    							e.printStackTrace();//打印异常
		    						} finally
		    						{//使用finally保证之后的语句一定被执行
		    							try
		    							{
		    								if(din != null)
		    								{
		    									din.close();
		    									din = null;
		    								}
		    							}
		    							catch(Exception e)
		    							{
		    								e.printStackTrace();
		    							}
		    							try
		    							{
		    								if(dout != null)
		    								{
		    									dout.close();
		    									dout = null;
		    								}							
		    							}
		    							catch(Exception e)
		    							{
		    								e.printStackTrace();
		    							}
		    							try
		    							{
		    								if(s != null)
		    								{
		    									s.close();
		    									s = null;
		    								}							
		    							}
		    							catch(Exception e)
		    							{
		    								e.printStackTrace();
		    							}	
		    						
		    						
		    						}
		    					}
		    				}.start();
		    				if(cb.isChecked())
		    				{
		    					rememberMe(tid.getText().toString().trim(),tpwd.getText().toString().trim());
		    				}

		    		
		      			}
		      			}
		      				
		      		);		
		        
		        
		        Button btn2 = (Button)this.findViewById(R.id.mbt2);
		        btn2.setOnClickListener(new OnClickListener()
		      		{
		      			@Override
		      			public void onClick(View v)
		      			{   
		      				
		      					jszc();
		      			
		      			}
		      		});	
		        ///////////////////////////////////////////////////
			Button btn11 = (Button)this.findViewById(R.id.mbt11);
            btn11.setOnClickListener(new OnClickListener()
          		{
          			@Override
          			public void onClick(View v)
          			{   sid = (EditText)findViewById(R.id.met11);
          				String Sid =sid.getText().toString().trim();
          				spwd = (EditText)findViewById(R.id.s_pwd);
          				String Spwd =spwd.getText().toString().trim();
          				if(Sid.trim().equals("")){//当用户名为空时 
          					Toast toast = Toast.makeText(getApplicationContext(),
          						     "学号不能为空！", Toast.LENGTH_SHORT);
          						   toast.setGravity(Gravity.CENTER, 0, 0);
          						   toast.show();
        					return;}
        				
        				if(Spwd.trim().equals("")){//当密码为空时
        					Toast toast = Toast.makeText(getApplicationContext(),
         						     "密码不能为空！", Toast.LENGTH_SHORT);
         						   toast.setGravity(Gravity.CENTER, 0, 0);
         						   toast.show();
        					return;}
        				new Thread(){//将耗时的动作放到线程中
        					public void run(){//重写的run方法
        						Socket s = null;
        						DataOutputStream dout = null;
        						DataInputStream din = null;

        						try {
        							//修改以下ip地址即可
        							s = new Socket("192.168.191.1",9997);//连接服务器
        							dout = new DataOutputStream(s.getOutputStream());
        							din = new DataInputStream(s.getInputStream());
        							dout.writeUTF("<#LOGIN#>"+sid.getText().toString()+"|"+spwd.getText().toString());
        							String msg = din.readUTF();//接收服务器发来的消息
        							if(msg.startsWith("<#LOGINOK#>")){//登录成功
        								msg=msg.substring(11);
        								Intent intent = new Intent();
        								intent.setClass(MainActivity.this, xsdl.class);
        								
        								Bundle bundle = new Bundle();
        								
        								bundle.putString("s_name",msg);
        								bundle.putString("s_id",sid.getText().toString());
        								
        								intent.putExtras(bundle);
        								
        								
        								startActivity(intent);
        								MainActivity.this.finish();
        							}
        							else if(msg.startsWith("<#LOGINERROR#>")){//登录失败
        								Toast toast = Toast.makeText(getApplicationContext(),
        	         						     "登录失败！！", Toast.LENGTH_SHORT);
        	         						   toast.setGravity(Gravity.CENTER, 0, 0);
        	         						   toast.show();
        							}
        						} catch (Exception e) {//捕获异常
        							e.printStackTrace();//打印异常
        						} finally{//使用finally保证之后的语句一定被执行
        							try{
        								if(din != null){
        									din.close();
        									din = null;
        								}
        							}
        							catch(Exception e){
        								e.printStackTrace();
        							}
        							try{
        								if(dout != null){
        									dout.close();
        									dout = null;
        								}							
        							}
        							catch(Exception e){
        								e.printStackTrace();
        							}
        							try{
        								if(s != null){
        									s.close();
        									s = null;
        								}							
        							}
        							catch(Exception e){
        								e.printStackTrace();
        							}	
        							
        						}
        					}
        				}.start();          			
          			}
          		});
            
            Button btn12 = (Button)this.findViewById(R.id.mbt12);
            btn12.setOnClickListener(new OnClickListener()
          		{
          			@Override
          			public void onClick(View v)
          			{   
          				xszc();
          				}
          			
          		});
		}
	//////////////////////////////////////////////////////////////	

		protected void jszc() {
			// TODO 自动生成的方法存根
			Intent i1 = new Intent(MainActivity.this,jszc.class);
			 startActivity(i1);
			 finish();
		}

		

	

		protected void xszc() {
			// TODO 自动生成的方法存根
			Intent i1 = new Intent(MainActivity.this,xszc.class);
			 startActivity(i1);
			 finish();
		}

		protected void xsdl() {
			// TODO 自动生成的方法存根
			 Intent i1 = new Intent(MainActivity.this,xsdl.class);
			 startActivity(i1);
			 finish();
			
		}


//--------------------------------------------------------------------

		//这是对话框的操作
		@Override
		protected Dialog onCreateDialog(int id) {		//重写的onCreateDialog方法
			Dialog dialog = null;
			switch(id){		
			case 0:
			    b1 = new AlertDialog.Builder(this);
				b1.setTitle("确定要退出吗？");
				b1.setPositiveButton("取消",new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog,int whichButton)
					{
									
						}
				}
				);
				b1.setNegativeButton("退出",new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog,int whichButton)
					{
						
						System.exit(0);
			        }
				}
				);
				dialog=b1.create();break;
			
	
			default:break;			
			}
			return dialog;									//返回Dialog对象
		}
	//--------------------------------------------------------------	
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
				case R.id.help:
					Intent i1 = new Intent(MainActivity.this,help.class);
					 startActivity(i1);
					 finish();
					 break;
				case R.id.tc:
					showDialog(0);
					break;
			}
			return true;
		}
	    public void rememberMe(String s_id,String s_pwd)
	    {//方法：将用户的id和密码存入Preferences
	    	SharedPreferences sp = getPreferences(MODE_PRIVATE);	//获得Preferences
	    	SharedPreferences.Editor editor = sp.edit();			//获得Editor
	    	editor.putString("s_id", s_id);							//将用户名存入Preferences
	    	editor.putString("s_pwd", s_pwd);							//将密码存入Preferences
	    	editor.commit();
	    }
	    
	    public void checkIfRemember()
	    {//方法：从Preferences中读取用户名和密码
	    	SharedPreferences sp = getPreferences(MODE_PRIVATE);	//获得Preferences
	    	String s_id = sp.getString("s_id", null);
	    	String s_pwd = sp.getString("s_pwd", null);
	    	if(s_id != null && s_pwd!= null)
	    	{
	    		EditText etUid = (EditText)findViewById(R.id.met2);
	    		EditText etPwd = (EditText)findViewById(R.id.met1);
	    		CheckBox cbRemember = (CheckBox)findViewById(R.id.mcb1); 
	    		etUid.setText(s_id);
	    		etPwd.setText(s_pwd);
	    		cbRemember.setChecked(true);
	    	}
	    }
}
