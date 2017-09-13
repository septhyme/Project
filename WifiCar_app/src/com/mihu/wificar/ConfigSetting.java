package com.mihu.wificar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigSetting extends Activity {
	private EditText editVideoIP, ipEdit, upEdit, downEdit, leftEdit,
			rightEdit, portEdit;
	private Button btnControlOK, btnControlCancel;

	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configforcontrol);
		sp = getSharedPreferences("config", 0);
		editVideoIP = (EditText) findViewById(R.id.editVideoIP);
		ipEdit = (EditText) findViewById(R.id.editControlIP);
		upEdit = (EditText) findViewById(R.id.editUp);
		downEdit = (EditText) findViewById(R.id.editDown);
		leftEdit = (EditText) findViewById(R.id.editLeft);
		rightEdit = (EditText) findViewById(R.id.editRight);
		portEdit = (EditText) findViewById(R.id.editControlPort);
		btnControlOK = (Button) findViewById(R.id.btnControlOK);
		btnControlCancel = (Button) findViewById(R.id.btnControlCancel);
		initData();
		btnControlOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GlobalData.CameraIp = editVideoIP.getText().toString();
				GlobalData.ip = ipEdit.getText().toString();
				GlobalData.port = Integer.valueOf(portEdit.getText().toString()
						.trim());
				GlobalData.upCmd = upEdit.getText().toString();
				GlobalData.downCmd = downEdit.getText().toString();
				GlobalData.leftCmd = leftEdit.getText().toString();
				GlobalData.rightCmd = rightEdit.getText().toString();
				setData();
				Toast.makeText(ConfigSetting.this, "±£´æ³É¹¦", 1000).show();
				finish();
			}
		});
		btnControlCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	protected void setData() {
		// TODO Auto-generated method stub
		Editor editor = sp.edit();
		editor.putString("CameraIp", GlobalData.CameraIp);
		editor.putInt("port", GlobalData.port);
		editor.putString("upCmd", GlobalData.upCmd);
		editor.putString("downCmd", GlobalData.downCmd);
		editor.putString("leftCmd", GlobalData.leftCmd);
		editor.putString("rightCmd", GlobalData.rightCmd);
		editor.commit();
	}

	private void initData() {
		// TODO Auto-generated method stub
		GlobalData.CameraIp = sp.getString("CameraIp", GlobalData.CameraIp);
		GlobalData.ip = sp.getString("ip", GlobalData.ip);
		GlobalData.port = sp.getInt("port", GlobalData.port);
		GlobalData.upCmd = sp.getString("upCmd", GlobalData.upCmd);
		GlobalData.downCmd = sp.getString("downCmd", GlobalData.downCmd);
		GlobalData.leftCmd = sp.getString("leftCmd", GlobalData.leftCmd);
		GlobalData.rightCmd = sp.getString("rightCmd", GlobalData.rightCmd);
		editVideoIP.setText(GlobalData.CameraIp);
		ipEdit.setText(GlobalData.ip);
		upEdit.setText(GlobalData.upCmd);
		downEdit.setText(GlobalData.downCmd);
		leftEdit.setText(GlobalData.leftCmd);
		rightEdit.setText(GlobalData.rightCmd);
		portEdit.setText(GlobalData.port + "");
	}
}
