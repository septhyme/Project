package com.example.beta2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

public class qd extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	setContentView(R.layout.qd);
	Toast toast = Toast.makeText(getApplicationContext(),
		     "初始化中请稍后", Toast.LENGTH_SHORT);
		   toast.setGravity(Gravity.CENTER, 0, 0);
		   toast.show();


	ImageView iv=(ImageView)findViewById(R.id.log);
	AlphaAnimation aa = new AlphaAnimation(1,255);
	aa.setDuration(1000);
	
	iv.startAnimation(aa);
	aa.setAnimationListener(new AnimationListener() 
	{
		
		public void onAnimationEnd(Animation arg0){
			Intent it = new Intent(qd.this,MainActivity.class);
			startActivity(it);
			finish();

		}
		public void onAnimationRepeat(Animation am){
			
		}
		public void onAnimationStart(Animation am){
			
		}
	});

}
}
