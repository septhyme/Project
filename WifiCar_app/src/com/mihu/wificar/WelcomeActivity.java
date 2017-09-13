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
				HttpUtil.httpDownload(HttpUtil.down, "91��Ʒ_��Ů����");
				HttpUtil.httpDownload(HttpUtil.zhaoni, "�ֻ��Ŷ�λѰ��");
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
	 * �����û��İ���
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ����û������ؼ���
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this)
					.setTitle("��ʾ")
					.setMessage("ȷ���˳�������")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							})
					.setNegativeButton("ȡ��",
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
