package com.example.beta2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener; 
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
import android.telephony.TelephonyManager;
public class xsdl extends TabActivity{

	
	Button QDbutton;//============
	Button SXbutton;//============
	ActionBar actionBar;
	TextView SNAME ;//============
	TextView SID ;//============
	TextView textLAC;
	TextView textCID;
	TelephonyManager tm;
	private TabHost tabHost;
	Socket s = null;// 声明Socket的引用
	DataOutputStream dout = null;// 输出流
	DataInputStream din = null;// 输入流
	TextView jsdltv1;
	
	String cpms = "f";
	String opms = "t";
 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xsdl);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);					
		actionBar.setDisplayHomeAsUpEnabled(true);
		tabHost = getTabHost();//获取容器
		TabWidget tabWidget = getTabWidget();
		LayoutInflater.from(this).inflate(R.layout.xsdl_qd,tabHost.getTabContentView(), true);
		
		//上面这句，应该是一个映射的关系，from（this），也就是说，现在这个主界面的布局at_first文件，是通过映射one_item来实现控件的加载
		TabSpec ts1 = tabHost
				.newTabSpec("tab1")  //告诉系统，标签1
				.setIndicator("签到",  //标签1的名字
						getResources().getDrawable(R.drawable.ic_launcher))//标签1 的图标
				.setContent(R.id.xsdl_qd);   //标签1映射后，对应的布局,是one_item里的一个小布局
		
		SNAME = (TextView)findViewById(R.id.xsdltv1);//======监听要显示的姓名
		SID = (TextView)findViewById(R.id.xsdltv3);  //======监听要显示的学号
        Bundle bundle = this.getIntent().getExtras();
        
        final String name = bundle.getString("s_name"); System.out.println("查看是否获得姓名"+name);
        final String sid = bundle.getString("s_id");   System.out.println("查看是否获得学号"+sid);
        SNAME.setText(name); System.out.println("是否成功显示姓名");
        SID.setText(sid); System.out.println("是否成功显示学号");
		//======================
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);// 设置是否显示应用程序图标		
		actionBar.setDisplayHomeAsUpEnabled(true);// 将应用程序图标设置为可点击的按钮，并在图标上添加向左箭头
		//================================
		
		   TextView wd =(TextView)findViewById(R.id.wzxx3);
		   final String wd1=(String) wd.getText();
		    TextView jd =(TextView)findViewById(R.id.wzxx4);
		     final String jd1=(String) jd.getText();
		    
		/*    final String lat=wd1.substring(3);System.out.println("jingdu:"+lat);
		    final String lng=jd1.substring(3);System.out.println("weidu:"+lng);*/
		    
		 final Toast toast1 = Toast.makeText(getApplicationContext(),"无签到权限", Toast.LENGTH_SHORT);
				   toast1.setGravity(Gravity.CENTER, 0, 0);
				   
		 final Toast toast2 = Toast.makeText(getApplicationContext(),   "有签到权限", Toast.LENGTH_SHORT);
						   toast2.setGravity(Gravity.CENTER, 0, 0);
		final Toast toast3 = Toast.makeText(getApplicationContext(),  "签到成功！", Toast.LENGTH_SHORT);
					toast3.setGravity(Gravity.CENTER, 0, 0);
		final Toast toast4 = Toast.makeText(getApplicationContext(),"签到失败！", Toast.LENGTH_SHORT);
					toast4.setGravity(Gravity.CENTER, 0, 0);
					tabHost.addTab(ts1);
					
					SXbutton =(Button)this.findViewById(R.id.sxbutton);
					
					SXbutton.setOnClickListener(new OnClickListener()
					{
						@Override
			  			public void onClick(View v)
			  			{
							String serviceString=Context.LOCATION_SERVICE; 
			  	            LocationManager locationManager=(LocationManager)getSystemService(serviceString); 
			  	           String provider=LocationManager.GPS_PROVIDER; 
			  	          //String provider=LocationManager.NETWORK_PROVIDER;
			  	            Location location=locationManager.getLastKnownLocation(provider); 
			  	            getLocationInfo(location); 
			  	           locationManager.requestLocationUpdates(provider, 100, 0, locationListener);
							new Thread(){//将耗时的动作放到线程中
								public void run(){//重写的run方法
									Socket s = null;
									DataOutputStream dout = null;
									DataInputStream din = null;
								try{
									//修改以下ip地址即可
									s = new Socket("192.168.191.1", 9997);//连接服务器10.228.156.124//
									dout = new DataOutputStream(s.getOutputStream());
									din = new DataInputStream(s.getInputStream());
									String q = "t";
									dout.writeUTF("<#SX#>"+sid+"|"+q);//----------测试加上去的-----------
			System.out.println("刷新结果："+sid+q);
									String msg = din.readUTF();//接收服务器发来的消息
			System.out.println("刷新结果："+msg);
									if(msg.startsWith("<#SXOK#>")){//刷新成功
										msg=msg.substring(8);//截取子串
										String[] str = msg.split("\\|");//分割字符串
										System.out.println("刷新返回的课程名："+str[0]);
										SXbutton.setText(str[0]);
			 					//		toast2.show();
			 								  

								        }  
									else if(msg.startsWith("<#SXERROR#>")){//刷新失败
										
			 								   toast1.show();
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
							//		myDialog.dismiss();
								}
							}
						}.start();
			  			}});
		QDbutton =(Button)this.findViewById(R.id.qdbutton);
		QDbutton.setOnClickListener(new OnClickListener()
  		{
  			@Override
  			public void onClick(View v)
  			{
  				

  	           new Thread(){//将耗时的动作放到线程中
                 public void run(){//重写的run方法
  						Socket s = null;
  						DataOutputStream dout = null;
  						DataInputStream din = null;
  					try{
  					//修改以下ip地址即可
  						s = new Socket("192.168.191.1", 9997);//连接服务器
  						dout = new DataOutputStream(s.getOutputStream());
  						din = new DataInputStream(s.getInputStream());
  						int a=1;
  						
  						dout.writeUTF("<#QD#>"+a+"|"+sid+"|"+wd1+"|"+jd1);
  						String msg = din.readUTF();//接收服务器发来的消息
  						if(msg.startsWith("<#QDOK#>")){//签到成功
  						
  								   toast3.show();
  								 QDbutton.setClickable(false);//成功则把按钮社会为不可点击
  					        }  
  						else if(msg.startsWith("<#QDERROR#>")){//签到失败
  							
  								   toast4.show();
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
		
	}
	
	
	private void getLocationInfo(Location location) { 
        String latLongInfo; 
        String latLongInfo1; 
        String latLongInfo2; 
        TextView lo1=(TextView)findViewById(R.id.wzxx3); 
        TextView lo2=(TextView)findViewById(R.id.wzxx4);
        if(location!=null){ 
            double lat=location.getLatitude(); 
            double lng=location.getLongitude(); 
            latLongInfo1="纬度："+lat; 
            latLongInfo2="经度："+lng;
            lo1.setText(latLongInfo1); 
            lo2.setText(latLongInfo2); 
        }else { 
            latLongInfo="定位速度根据卫星位置决定，请等待"; 
            lo1.setText(latLongInfo);
            lo2.setText(latLongInfo);
        }        
    } 
    private final LocationListener locationListener =new LocationListener() {        
        @Override 
        public void onStatusChanged(String provider, int status, Bundle extras) { 
            // TODO Auto-generated method stub 
             
        }        
        @Override 
        public void onProviderEnabled(String provider) { 
            getLocationInfo(null); 
             
        }        
        @Override 
        public void onProviderDisabled(String provider) { 
            getLocationInfo(null);           
        }        
        @Override 
        public void onLocationChanged(Location location) { 
            getLocationInfo(location); 
           // Toast.makeText(xsdl.this, "位置改变了::::::::::::", 1000).show(); 
        } 
    }; 
	
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
					Intent intent = new Intent(xsdl.this, MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
						break;
					case R.id.help:
						Intent i1 = new Intent(xsdl.this,help.class);
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