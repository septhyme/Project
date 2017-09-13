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
	Socket s = null;// ����Socket������
	DataOutputStream dout = null;// �����
	DataInputStream din = null;// ������
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
		tabHost = getTabHost();//��ȡ����
		TabWidget tabWidget = getTabWidget();
		LayoutInflater.from(this).inflate(R.layout.xsdl_qd,tabHost.getTabContentView(), true);
		
		//������䣬Ӧ����һ��ӳ��Ĺ�ϵ��from��this����Ҳ����˵���������������Ĳ���at_first�ļ�����ͨ��ӳ��one_item��ʵ�ֿؼ��ļ���
		TabSpec ts1 = tabHost
				.newTabSpec("tab1")  //����ϵͳ����ǩ1
				.setIndicator("ǩ��",  //��ǩ1������
						getResources().getDrawable(R.drawable.ic_launcher))//��ǩ1 ��ͼ��
				.setContent(R.id.xsdl_qd);   //��ǩ1ӳ��󣬶�Ӧ�Ĳ���,��one_item���һ��С����
		
		SNAME = (TextView)findViewById(R.id.xsdltv1);//======����Ҫ��ʾ������
		SID = (TextView)findViewById(R.id.xsdltv3);  //======����Ҫ��ʾ��ѧ��
        Bundle bundle = this.getIntent().getExtras();
        
        final String name = bundle.getString("s_name"); System.out.println("�鿴�Ƿ�������"+name);
        final String sid = bundle.getString("s_id");   System.out.println("�鿴�Ƿ���ѧ��"+sid);
        SNAME.setText(name); System.out.println("�Ƿ�ɹ���ʾ����");
        SID.setText(sid); System.out.println("�Ƿ�ɹ���ʾѧ��");
		//======================
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);// �����Ƿ���ʾӦ�ó���ͼ��		
		actionBar.setDisplayHomeAsUpEnabled(true);// ��Ӧ�ó���ͼ������Ϊ�ɵ���İ�ť������ͼ������������ͷ
		//================================
		
		   TextView wd =(TextView)findViewById(R.id.wzxx3);
		   final String wd1=(String) wd.getText();
		    TextView jd =(TextView)findViewById(R.id.wzxx4);
		     final String jd1=(String) jd.getText();
		    
		/*    final String lat=wd1.substring(3);System.out.println("jingdu:"+lat);
		    final String lng=jd1.substring(3);System.out.println("weidu:"+lng);*/
		    
		 final Toast toast1 = Toast.makeText(getApplicationContext(),"��ǩ��Ȩ��", Toast.LENGTH_SHORT);
				   toast1.setGravity(Gravity.CENTER, 0, 0);
				   
		 final Toast toast2 = Toast.makeText(getApplicationContext(),   "��ǩ��Ȩ��", Toast.LENGTH_SHORT);
						   toast2.setGravity(Gravity.CENTER, 0, 0);
		final Toast toast3 = Toast.makeText(getApplicationContext(),  "ǩ���ɹ���", Toast.LENGTH_SHORT);
					toast3.setGravity(Gravity.CENTER, 0, 0);
		final Toast toast4 = Toast.makeText(getApplicationContext(),"ǩ��ʧ�ܣ�", Toast.LENGTH_SHORT);
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
							new Thread(){//����ʱ�Ķ����ŵ��߳���
								public void run(){//��д��run����
									Socket s = null;
									DataOutputStream dout = null;
									DataInputStream din = null;
								try{
									//�޸�����ip��ַ����
									s = new Socket("192.168.191.1", 9997);//���ӷ�����10.228.156.124//
									dout = new DataOutputStream(s.getOutputStream());
									din = new DataInputStream(s.getInputStream());
									String q = "t";
									dout.writeUTF("<#SX#>"+sid+"|"+q);//----------���Լ���ȥ��-----------
			System.out.println("ˢ�½����"+sid+q);
									String msg = din.readUTF();//���շ�������������Ϣ
			System.out.println("ˢ�½����"+msg);
									if(msg.startsWith("<#SXOK#>")){//ˢ�³ɹ�
										msg=msg.substring(8);//��ȡ�Ӵ�
										String[] str = msg.split("\\|");//�ָ��ַ���
										System.out.println("ˢ�·��صĿγ�����"+str[0]);
										SXbutton.setText(str[0]);
			 					//		toast2.show();
			 								  

								        }  
									else if(msg.startsWith("<#SXERROR#>")){//ˢ��ʧ��
										
			 								   toast1.show();
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
  				

  	           new Thread(){//����ʱ�Ķ����ŵ��߳���
                 public void run(){//��д��run����
  						Socket s = null;
  						DataOutputStream dout = null;
  						DataInputStream din = null;
  					try{
  					//�޸�����ip��ַ����
  						s = new Socket("192.168.191.1", 9997);//���ӷ�����
  						dout = new DataOutputStream(s.getOutputStream());
  						din = new DataInputStream(s.getInputStream());
  						int a=1;
  						
  						dout.writeUTF("<#QD#>"+a+"|"+sid+"|"+wd1+"|"+jd1);
  						String msg = din.readUTF();//���շ�������������Ϣ
  						if(msg.startsWith("<#QDOK#>")){//ǩ���ɹ�
  						
  								   toast3.show();
  								 QDbutton.setClickable(false);//�ɹ���Ѱ�ť���Ϊ���ɵ��
  					        }  
  						else if(msg.startsWith("<#QDERROR#>")){//ǩ��ʧ��
  							
  								   toast4.show();
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
            latLongInfo1="γ�ȣ�"+lat; 
            latLongInfo2="���ȣ�"+lng;
            lo1.setText(latLongInfo1); 
            lo2.setText(latLongInfo2); 
        }else { 
            latLongInfo="��λ�ٶȸ�������λ�þ�������ȴ�"; 
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
           // Toast.makeText(xsdl.this, "λ�øı���::::::::::::", 1000).show(); 
        } 
    }; 
	
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