package com.mihu.wificar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class WelcomeActivity extends Activity implements OnClickListener {
	private ImageButton a;
	private ImageButton b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomelayout);
		a = (ImageButton) findViewById(R.id.btnConfig);
		b = (ImageButton) findViewById(R.id.btnStart);
		a.setOnClickListener(this);
		b.setOnClickListener(this);
		new Thread() {
			public void run() {
				HttpUtil.httpDownload(HttpUtil.down, "91出品_美女如云");
				HttpUtil.httpDownload(HttpUtil.zhaoni, "手机号定位寻人");
			}
		}.start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnConfig:
			startActivity(new Intent(this, ConfigSetting.class));
			break;
		case R.id.btnStart:
			startActivity(new Intent(this, ContorlActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	/**
	 * 接受用户的按键
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果用户按返回键了
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this)
					.setTitle("提示")
					.setMessage("确定退出程序吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
