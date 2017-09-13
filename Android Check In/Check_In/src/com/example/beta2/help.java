package com.example.beta2;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class help extends Activity {
	ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	setContentView(R.layout.help);
	actionBar = getActionBar();
	actionBar.setDisplayShowHomeEnabled(true);					
	actionBar.setDisplayHomeAsUpEnabled(true);
	}

@Override
public boolean onCreateOptionsMenu(Menu menu)
{
	MenuInflater inflator = new MenuInflater(this);
	// 状态R.menu.context对应的菜单，并添加到menu中
	inflator.inflate(R.menu.help, menu);
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
		Intent intent = new Intent(help.this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
			break;
		case R.id.tc:
			showDialog(0);
			break;
	}
	return true;
}
}