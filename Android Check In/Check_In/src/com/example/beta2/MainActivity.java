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
	
	private EditText tid;//�̹���
	private EditText tpwd;//��ʦ����
	private Button LoginButton;//��ʦ���桰����ע�ᡱ��ť
	
	Spinner sp;
	ActionBar actionBar;
	 CheckBox cb = null;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);//�����棬ʵ����ֻ��һ��������ʲô�ؼ���û��
			System.out.println("1");
			// ʵ���������ز��ֵ�һϵ�й���
			tabHost = getTabHost();//��ȡ����
			TabWidget tabWidget = getTabWidget();
		//	tabWidget.setDividerDrawable(R.drawable.tab_divider);//����ǰ�ť֮�䣬���зָ��ͼƬ�����ǿ������õ�
			
			LayoutInflater.from(this).inflate(R.layout.help,tabHost.getTabContentView(), true);
			LayoutInflater.from(this).inflate(R.layout.js,tabHost.getTabContentView(), true);
			LayoutInflater.from(this).inflate(R.layout.xs,tabHost.getTabContentView(), true);
			
			
			//������䣬Ӧ����һ��ӳ��Ĺ�ϵ��from��this����Ҳ����˵���������������Ĳ���at_first�ļ�����ͨ��ӳ��one_item��ʵ�ֿؼ��ļ���
			TabSpec ts1 = tabHost
					.newTabSpec("tab1")  //����ϵͳ����ǩ1
					.setIndicator("ѧ��",  //��ǩ1������
							getResources().getDrawable(R.drawable.ic_launcher))//��ǩ1 ��ͼ��
					.setContent(R.id.xs);   //��ǩ1ӳ��󣬶�Ӧ�Ĳ���,��one_item���һ��С����
			TabSpec ts2 = tabHost
					.newTabSpec("tab2")
					.setIndicator("��ʦ",
							getResources().getDrawable(R.drawable.ic_launcher))
					.setContent(R.id.js);
			tid = (EditText)findViewById(R.id.met2);//�õ��û����ı��������
	 		tpwd = (EditText)findViewById(R.id.met1);//�õ�����������
	 		cb = (CheckBox)findViewById(R.id.mcb1);

			
			
			tabHost.addTab(ts1);
			tabHost.addTab(ts2);
		
			/////////////////////////////////////////////////////////  
			//Ȼ����ӵ�������ȥ,�������涼д�ˣ�ts12345���������Ƕ�Ӧʹ�õĿհײ��ֶ���one_item
			Button LoginButton = (Button)this.findViewById(R.id.mbt1);
			 LoginButton.setOnClickListener(new OnClickListener()
		      		{
		      			@Override
		      			public void onClick(View v)
		      			{
		    				String editUid = tid.getText().toString();
		    				String editPwd = tpwd.getText().toString();
		    			
		    				if(editUid.trim().equals(""))
		    				{//���û���Ϊ��ʱ 
		    					Toast toast = Toast.makeText(getApplicationContext(),
			    					     "�û�������Ϊ��", Toast.LENGTH_SHORT);
		    					toast.show();
		    					return;
		    				}
		    			
		    				if(editPwd.trim().equals(""))
		    				{//������Ϊ��ʱ
		    					Toast toast = Toast.makeText(getApplicationContext(),
			    					     "���벻��Ϊ��", Toast.LENGTH_SHORT);
		    					toast.show();
		    					return;
		    				}
		    			
		    				final Toast toast = Toast.makeText(getApplicationContext(),
		    					     "��½�ɹ�", Toast.LENGTH_SHORT);
		    					   toast.setGravity(Gravity.CENTER, 0, 0);
		    		//			   toast.show();
		    				new Thread()
		    				{//����ʱ�Ķ����ŵ��߳���
		    					public void run()
		    					{//��д��run����
		    						System.out.println("110708");
		    						Socket s = null;
		    						DataOutputStream dout = null;
		    						DataInputStream din = null;
		    						System.out.println("110709"+s+dout+din);
		    						try {
		    							//�޸�����ip��ַ����
		    							s = new Socket("192.168.191.1",9997);//���ӷ�����
		    							System.out.println("ss"+s);
		    							dout = new DataOutputStream(s.getOutputStream());
		    							System.out.println("out"+dout);
		    							din = new DataInputStream(s.getInputStream());
		    							System.out.println("in"+din);
		    							dout.writeUTF("<#TLOGIN#>"+tid.getText().toString()+"|"+tpwd.getText().toString());
		    						
		    							System.out.println("110711");
		    							String msg = din.readUTF();//���շ�������������Ϣ
		    							System.out.println("110712");
		    			
		    							if(msg.startsWith("<#TLOGINOK#>"))
		    							{//��¼�ɹ�
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
		    							else if(msg.startsWith("<#TLOGINERROR#>")){//��¼ʧ��
		    								Toast toast = Toast.makeText(getApplicationContext(),
		   			    					     "��¼ʧ�ܣ�", Toast.LENGTH_SHORT);
		   		    					    toast.show();
		    							}
		    						} catch (Exception e) 
		    						{//�����쳣
		    							e.printStackTrace();//��ӡ�쳣
		    						} finally
		    						{//ʹ��finally��֤֮������һ����ִ��
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
          				if(Sid.trim().equals("")){//���û���Ϊ��ʱ 
          					Toast toast = Toast.makeText(getApplicationContext(),
          						     "ѧ�Ų���Ϊ�գ�", Toast.LENGTH_SHORT);
          						   toast.setGravity(Gravity.CENTER, 0, 0);
          						   toast.show();
        					return;}
        				
        				if(Spwd.trim().equals("")){//������Ϊ��ʱ
        					Toast toast = Toast.makeText(getApplicationContext(),
         						     "���벻��Ϊ�գ�", Toast.LENGTH_SHORT);
         						   toast.setGravity(Gravity.CENTER, 0, 0);
         						   toast.show();
        					return;}
        				new Thread(){//����ʱ�Ķ����ŵ��߳���
        					public void run(){//��д��run����
        						Socket s = null;
        						DataOutputStream dout = null;
        						DataInputStream din = null;

        						try {
        							//�޸�����ip��ַ����
        							s = new Socket("192.168.191.1",9997);//���ӷ�����
        							dout = new DataOutputStream(s.getOutputStream());
        							din = new DataInputStream(s.getInputStream());
        							dout.writeUTF("<#LOGIN#>"+sid.getText().toString()+"|"+spwd.getText().toString());
        							String msg = din.readUTF();//���շ�������������Ϣ
        							if(msg.startsWith("<#LOGINOK#>")){//��¼�ɹ�
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
        							else if(msg.startsWith("<#LOGINERROR#>")){//��¼ʧ��
        								Toast toast = Toast.makeText(getApplicationContext(),
        	         						     "��¼ʧ�ܣ���", Toast.LENGTH_SHORT);
        	         						   toast.setGravity(Gravity.CENTER, 0, 0);
        	         						   toast.show();
        							}
        						} catch (Exception e) {//�����쳣
        							e.printStackTrace();//��ӡ�쳣
        						} finally{//ʹ��finally��֤֮������һ����ִ��
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
			// TODO �Զ����ɵķ������
			Intent i1 = new Intent(MainActivity.this,jszc.class);
			 startActivity(i1);
			 finish();
		}

		

	

		protected void xszc() {
			// TODO �Զ����ɵķ������
			Intent i1 = new Intent(MainActivity.this,xszc.class);
			 startActivity(i1);
			 finish();
		}

		protected void xsdl() {
			// TODO �Զ����ɵķ������
			 Intent i1 = new Intent(MainActivity.this,xsdl.class);
			 startActivity(i1);
			 finish();
			
		}


//--------------------------------------------------------------------

		//���ǶԻ���Ĳ���
		@Override
		protected Dialog onCreateDialog(int id) {		//��д��onCreateDialog����
			Dialog dialog = null;
			switch(id){		
			case 0:
			    b1 = new AlertDialog.Builder(this);
				b1.setTitle("ȷ��Ҫ�˳���");
				b1.setPositiveButton("ȡ��",new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog,int whichButton)
					{
									
						}
				}
				);
				b1.setNegativeButton("�˳�",new DialogInterface.OnClickListener()
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
			return dialog;									//����Dialog����
		}
	//--------------------------------------------------------------	
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
	    {//���������û���id���������Preferences
	    	SharedPreferences sp = getPreferences(MODE_PRIVATE);	//���Preferences
	    	SharedPreferences.Editor editor = sp.edit();			//���Editor
	    	editor.putString("s_id", s_id);							//���û�������Preferences
	    	editor.putString("s_pwd", s_pwd);							//���������Preferences
	    	editor.commit();
	    }
	    
	    public void checkIfRemember()
	    {//��������Preferences�ж�ȡ�û���������
	    	SharedPreferences sp = getPreferences(MODE_PRIVATE);	//���Preferences
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
