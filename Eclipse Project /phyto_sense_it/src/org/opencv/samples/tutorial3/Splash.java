package org.opencv.samples.tutorial3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
	
	private final int SPLASH_DISPLAY_LENGTH = 5000;
	
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.splashscreen);
		
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run(){
				Intent mainIntent = new Intent(Splash.this, HomeActivity.class);
				Splash.this.startActivity(mainIntent);
				Splash.this.finish();
			}
		}, SPLASH_DISPLAY_LENGTH);
	}
	
}
