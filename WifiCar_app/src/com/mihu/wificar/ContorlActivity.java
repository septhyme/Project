package com.mihu.wificar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ContorlActivity extends Activity implements OnTouchListener {
	YourSurfaceView mySurfaceView;
	// MySurfaceView newSurfaceView;
	// 视频线程
	// private Thread mThreadvideo = null;
	private ImageButton ForWard;
	private ImageButton BackWard;
	private ImageButton TurnLeft;
	private ImageButton TurnRight;
	private ImageButton btnTrack, btnBizhang;

	private LinearLayout paramLayout;
	private ImageView ivFire, ivFog;
	private TextView tvMileage, tvTemp, tvSpeed, tvHumidity;

	private CheckBox chkSensor, chkGears, chkPwm, chkParam;

	// 舵机模式
	private View gearView;
	private MySeekBar gear1, gear2, gear3;

	// 重力感应
	private boolean isGravity = false;
	// 摇晃速度临界值
	private static final int SPEED_SHRESHOLD = 600;
	// 两次检测的时间间隔
	private static final int UPTATE_INTERVAL_TIME = 200;
	private SensorManager sensorMag;
	private Sensor gravitySensor;
	// 上次检测时间
	private long lastUpdateTime;
	// 保存上一次记录
	float lastX = 0;
	float lastY = 0;
	float lastZ = 0;

	private Context mContext;
	private boolean isConnecting = false;

	private Thread mThreadClient = null;
	private Socket mSocketClient = null;
	static BufferedReader mBufferedReaderServer = null;
	static PrintWriter mPrintWriterServer = null;
	static BufferedReader mBufferedReaderClient = null;
	static PrintWriter mPrintWriterClient = null;
	private String recvMessageClient = "";
	private String recvMessageServer = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;
		findViewById();
		addListner();
		initData();
	}

	/**
	 * 初始化传感器
	 */
	private void initGravitySensor() {
		sensorMag = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		gravitySensor = sensorMag.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

	}

	@Override
	protected void onPause() {
		sensorMag.unregisterListener(sensorLis);
		super.onPause();
	}

	@Override
	protected void onResume() {
		sensorMag.registerListener(sensorLis, gravitySensor,
				SensorManager.SENSOR_DELAY_UI);
		super.onResume();
	}

	float tMax = 1.0f;
	private SensorEventListener sensorLis = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}

		@Override
		public void onSensorChanged(SensorEvent event) {

			if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
				return;
			}
			if (!isGravity) {
				return;
			}
			// 现在检测时间
			long currentUpdateTime = System.currentTimeMillis();
			// 两次检测的时间间隔
			long timeInterval = currentUpdateTime - lastUpdateTime;
			// 判断是否达到了检测时间间隔
			if (timeInterval < UPTATE_INTERVAL_TIME)
				return;
			// 现在的时间变成last时间
			lastUpdateTime = currentUpdateTime;
			// 获取加速度数值，以下三个值为重力分量在设备坐标的分量大小
			float x = event.values[SensorManager.DATA_X];

			float y = event.values[SensorManager.DATA_Y];

			float z = event.values[SensorManager.DATA_Z];

			// Log.e("msg", "x= "+x+" y= "+y);
			// Log.e("msg", "x= "+x+" y= "+y+" z= "+z);

			float absx = Math.abs(x);
			float absy = Math.abs(y);
			float absz = Math.abs(z);

			if (absx > absy && absx > absz) {

				if (x > tMax) { // left
					sendCmd(GlobalData.downCmd);
					Log.e("origen", "turn left");
				} else if (x < -tMax) {// right
					sendCmd(GlobalData.upCmd);
					Log.e("origen", "turn right");
				} else {
					Log.e("origen", "not turn right or turn left");
				}

			} else if (absy > absx && absy > absz) {

				if (y > tMax) {
					sendCmd(GlobalData.rightCmd);
					Log.e("origen", "turn up");
				} else if (y < -tMax) {
					sendCmd(GlobalData.leftCmd);
					Log.e("origen", "turn down");
				}
			} else if (absz > absx && absz > absy) {// 此时考虑停止游戏
				if (z > 0) {
					Log.e("origen", "screen up");
				} else {
					Log.e("origen", "screen down");
				}
			} else {
				Log.e("origen", "unknow action");
			}

			// 获得x,y,z的变化值
			float deltaX = x - lastX;
			float deltaY = y - lastY;
			float deltaZ = z - lastZ;
			// 备份本次坐标
			lastX = x;
			lastY = y;
			lastZ = z;
			// 计算移动速度
			double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
					* deltaZ)
					/ timeInterval * 10000;
			// Log.e("msg", "speed= "+speed);

			// if (speed >= SPEED_SHRESHOLD)//移动速度
			// Toast.makeText(GameGravityActivity.this, "onshake", 200).show();

		}
	};

	private void addListner() {
		// TODO Auto-generated method stub
		// ForWard.setOnClickListener(ForWardClickListener);
		// BackWard.setOnClickListener(BackWardClickListener);
		// TurnLeft.setOnClickListener(TurnLeftClickListener);
		// TurnRight.setOnClickListener(TurnRightClickListener);

		ForWard.setOnTouchListener(this);
		BackWard.setOnTouchListener(this);
		TurnLeft.setOnTouchListener(this);
		TurnRight.setOnTouchListener(this);

		btnTrack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			}
		});
		btnBizhang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			}
		});

		chkSensor.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton compoundButton,
					boolean isChecked) {
				// TODO Auto-generated method stub
				isGravity = isChecked;
				switchToSensor(isChecked);
			}
		});
		chkGears.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton compoundButton,
					boolean isChecked) {
				// TODO Auto-generated method stub
				switchToGears(isChecked);
			}
		});
		chkPwm.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton compoundButton,
					boolean isChecked) {
				// TODO Auto-generated method stub
				switchToFun(isChecked);
			}
		});
		// chkParam.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton compoundButton,
		// boolean isChecked) {
		// // TODO Auto-generated method stub
		// switchToParam(isChecked);
		// }
		// });  
		
		gear1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
			}
		});
		gear2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
			}
		});
		gear3.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
			}
		});
	}

	protected void switchToParam(boolean isChecked) {
		// TODO Auto-generated method stub
		paramLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
	}

	protected void switchToFun(boolean isChecked) {
		// TODO Auto-generated method stub
	//	btnTrack.setVisibility(isChecked ? View.VISIBLE : View.GONE);
	//	btnBizhang.setVisibility(isChecked ? View.VISIBLE : View.GONE);
		btnTrack.setVisibility(isChecked ? View.GONE : View.GONE);
		btnBizhang.setVisibility(isChecked ? View.GONE : View.GONE);
	}

	protected void switchToGears(boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			//gearView.setVisibility(View.VISIBLE);
			gearView.setVisibility(View.INVISIBLE);
		} else {
			gearView.setVisibility(View.INVISIBLE);
		}
	}

	private void sendCmd(String cmd) {
		if (isConnecting && mSocketClient != null) {
			try {
				byte[] buf = GlobalData.HexString2Bytes(cmd);
				OutputStream os;
				os = mSocketClient.getOutputStream();
				os.write(buf);
				os.flush();
				// MySendCommondThread sendCmd = new MySendCommondThread(buf);
				// // mPrintWriterClient.print(cmd);//
				// sendCmd.start();
				// mPrintWriterClient.flush();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(mContext, "发送异常,请检查发送命令!", Toast.LENGTH_SHORT)
						.show();
			}

		} else {
			Toast.makeText(mContext, "没有连接", Toast.LENGTH_SHORT).show();
		}
	}

	private void sendCmd(boolean isPwm, String PWM1, String PWM2, String cmd) {
		if (isConnecting && mSocketClient != null) {
			try {
				if (isPwm) {
					mPrintWriterClient.print(PWM1);// 发送PWM1信息给服务器
					mPrintWriterClient.flush();
					mPrintWriterClient.print(PWM2);// 发送PWM2信息给服务器
					mPrintWriterClient.flush();
					Thread.sleep(600);
				}
				// mPrintWriterClient.print(cmd);// 发送方向给服务器
				// mPrintWriterClient.flush();
				byte[] buf = GlobalData.HexString2Bytes(cmd);
				OutputStream os = mSocketClient.getOutputStream();
				os.write(buf);
				os.flush();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(mContext, "发送异常,请检查连接!", Toast.LENGTH_SHORT)
						.show();
			}

		} else {
			Toast.makeText(mContext, "没有连接", Toast.LENGTH_SHORT).show();
		}
	}

	/** 发送命令线程 */
	class MySendCommondThread extends Thread {
		private byte[] buf;

		public MySendCommondThread(byte[] buf) {
			this.buf = buf;
		}

		public void run() {
			OutputStream os = null;
			try {
				os = mSocketClient.getOutputStream();
				os.write(buf);
				os.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void switchToSensor(boolean isChecked) {
		if (isChecked) {
			ForWard.setVisibility(View.INVISIBLE);
			BackWard.setVisibility(View.INVISIBLE);
			TurnLeft.setVisibility(View.INVISIBLE);
			TurnRight.setVisibility(View.INVISIBLE);
		} else {
			ForWard.setVisibility(View.VISIBLE);
			BackWard.setVisibility(View.VISIBLE);
			TurnLeft.setVisibility(View.VISIBLE);
			TurnRight.setVisibility(View.VISIBLE);
		}
	}

	private void initData() {
		mySurfaceView.GetCameraIP(GlobalData.CameraIp);
		// 视频监听
		// newSurfaceView = (MySurfaceView) findViewById(R.id.videoview);
		// newSurfaceView.GetCameraIP(GlobalData.CameraIp);

		if (isConnecting) {
			isConnecting = false;
			try {
				if (mSocketClient != null) {
					mSocketClient.close();
					mSocketClient = null;

					mPrintWriterClient.close();
					mPrintWriterClient = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mThreadClient.interrupt();
		} else {
			isConnecting = true;
			mThreadClient = new Thread(mRunnable);
			mThreadClient.start();
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
			} else if (msg.what == 1) {
				Toast.makeText(ContorlActivity.this, recvMessageClient, 1000).show();
			} else if (msg.what == 2) {
				// 处理参数显示
				// FF1000XXXXFF-里程,FF1100XXXXFF-速度,FF1200XXXXFF-温度,FF1300XXXXFF-湿度,FF140000XXFF-火焰　亮与灰
			}
		}
	};
	// 线程:监听服务器发来的消息
	private Runnable mRunnable = new Runnable() {
		public void run() {
			try {
				// 连接服务器
				mSocketClient = new Socket(GlobalData.ip, GlobalData.port); // portnum
				// 取得输入、输出流
				mBufferedReaderClient = new BufferedReader(
						new InputStreamReader(mSocketClient.getInputStream()));
				mPrintWriterClient = new PrintWriter(
						mSocketClient.getOutputStream(), true);
				recvMessageClient = "已经连接server!\n";// 消息换行
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
			} catch (Exception e) {
				recvMessageClient = e.toString() + e.getMessage() + "\n";// 消息换行
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}
			char[] buffer = new char[1024];
			int count = 0;
			String temp = "";
			while (isConnecting) {
				try {
					if ((count = mBufferedReaderClient.read(buffer)) > 0) {
						temp += getInfoBuff(buffer, count);// 消息换行
						Log.e("isConnecting:", temp);
					}
					temp = temp.replaceAll("\r|\n", "");
					Log.e("isConnecting finish:", recvMessageClient);
					if (temp.length() == "FF12345678FF".length()) {
						Message msg = new Message();
						msg.what = 2;
						msg.obj = temp;
						mHandler.sendMessage(msg);
						temp = "";
					}
				} catch (Exception e) {
					recvMessageClient = e.getMessage() + "\n";// 消息换行
					Message msg = new Message();
					msg.what = 1;
					mHandler.sendMessage(msg);
				}
			}
		}

	private String getInfoBuff(char[] buffer, int count) {
			// TODO Auto-generated method stub
			char[] temp = new char[count];
			for (int i = 0; i < count; i++) {
				temp[i] = buffer[i];
			}
			return new String(temp);

		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mySurfaceView != null) {
			mySurfaceView.setRun(false);
		}
		// if (newSurfaceView != null) {
		// newSurfaceView.setRun(false);
		// }
		if (isConnecting) {
			isConnecting = false;
			try {
				if (mSocketClient != null) {
					mSocketClient.close();
					mSocketClient = null;

					mPrintWriterClient.close();
					mPrintWriterClient = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mThreadClient.interrupt();
		}
	}

	private String getParam(String aStr) {
		if (aStr == null || aStr.trim().equals("")) {
			aStr = "0";
		}
		return aStr;
	}

	private void findViewById() {
		// TODO Auto-generated method stub
		ForWard = (ImageButton) findViewById(R.id.btnForward);
		BackWard = (ImageButton) findViewById(R.id.btnBack);
		TurnLeft = (ImageButton) findViewById(R.id.btnLeft);
		TurnRight = (ImageButton) findViewById(R.id.btnRight);
		btnTrack = (ImageButton) findViewById(R.id.btnTrack);
		btnBizhang = (ImageButton) findViewById(R.id.btnBizhang);
		mySurfaceView = (YourSurfaceView) findViewById(R.id.videoview);

		paramLayout = (LinearLayout) findViewById(R.id.paramLayou);
		ivFire = (ImageView) findViewById(R.id.ivFire);
		ivFog = (ImageView) findViewById(R.id.ivFog);
		tvMileage = (TextView) findViewById(R.id.tvMileage);
		tvHumidity = (TextView) findViewById(R.id.tvHumidity);
		tvSpeed = (TextView) findViewById(R.id.tvSpeed);
		tvTemp = (TextView) findViewById(R.id.tvTemp);

		chkSensor = (CheckBox) findViewById(R.id.chkSensor);
		chkGears = (CheckBox) findViewById(R.id.chkGears);
		chkPwm = (CheckBox) findViewById(R.id.chkPwm);
		chkParam = (CheckBox) findViewById(R.id.chkParam);

		gearView = findViewById(R.id.gear_layout);
		gear1 = (MySeekBar) findViewById(R.id.gear1);
		gear2 = (MySeekBar) findViewById(R.id.gear2);
		gear3 = (MySeekBar) findViewById(R.id.gear3);
		gear1.setMax(180);
		gear2.setMax(180);
		gear3.setMax(180);
		gear1.setProgress(90);
		gear2.setProgress(90);
		gear3.setProgress(90);

		initGravitySensor();
	}

	private OnClickListener ForWardClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (isConnecting && mSocketClient != null) {
				String PWM1 = "PWM 1 100 50";
				String PWM2 = "PWM 2 100 50";
				sendCmd(false, PWM1, PWM2, GlobalData.upCmd);
			} else {
				Toast.makeText(mContext, "没有连接", Toast.LENGTH_SHORT).show();
			}
		}
	};

	private OnClickListener StopClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (isConnecting && mSocketClient != null) {
				String PWM1 = "PWM 1 100 0";
				String PWM2 = "PWM 2 100 0";
				sendCmd(false, PWM1, PWM2, GlobalData.stopCmd);
			} else {
				Toast.makeText(mContext, "没有连接", Toast.LENGTH_SHORT).show();
			}
		}
	};

	private OnClickListener TurnLeftClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (isConnecting && mSocketClient != null) {
				String PWM1 = "PWM 1 100 0";
				String PWM2 = "PWM 2 100 50";
				sendCmd(false, PWM1, PWM2, GlobalData.leftCmd);
			} else {
				Toast.makeText(mContext, "没有连接", Toast.LENGTH_SHORT).show();
			}
		}
	};

	private OnClickListener TurnRightClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (isConnecting && mSocketClient != null) {
				String PWM1 = "PWM 1 100 50";
				String PWM2 = "PWM 2 100 0";
				sendCmd(false, PWM1, PWM2, GlobalData.rightCmd);
			} else {
				Toast.makeText(mContext, "没有连接", Toast.LENGTH_SHORT).show();
			}
		}
	};

	private OnClickListener BackWardClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (isConnecting && mSocketClient != null) {
				String PWM1 = "PWM 1 100 50";
				String PWM2 = "PWM 2 100 50";
				sendCmd(false, PWM1, PWM2, GlobalData.downCmd);
			} else {
				Toast.makeText(mContext, "没有连接", Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btnForward:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				sendCmd(GlobalData.upCmd);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				sendCmd(GlobalData.stopCmd);
			}
			break;
		case R.id.btnBack:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				sendCmd(GlobalData.downCmd);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				sendCmd(GlobalData.stopCmd);
			}
			break;
		case R.id.btnLeft:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				sendCmd(GlobalData.leftCmd);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				sendCmd(GlobalData.stopCmd);
			}
			break;
		case R.id.btnRight:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				sendCmd(GlobalData.rightCmd);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				sendCmd(GlobalData.stopCmd);
			}
			break;
		default:
			break;
		}
		return false;
	}
}
