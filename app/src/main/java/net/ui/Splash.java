package net.ui;

import net.surf.R;
import Util.StorageInfo;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends Activity {
	ImageView imgDuck;
	ImageView imgFluir;
	 public void onCreate(Bundle savedInstanceState) {	 
		 super.onCreate(savedInstanceState);
		 	requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        
	        setContentView(R.layout.splash);
	        
	        imgDuck = (ImageView) findViewById(R.id.imgDuck);
	        imgFluir = (ImageView) findViewById(R.id.imgFluir);
	        StorageInfo.getInstance(this);
	        FadeInDuck();	        
	        
	 }
	void FadeInDuck()
	{
		Animation FadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
			    
			    FadeInAnimation.setAnimationListener(new AnimationListener() {
		            public void onAnimationStart(Animation animation) {}
		            public void onAnimationRepeat(Animation animation) {}
		            public void onAnimationEnd(Animation animation) {
		                FadeOutDuck();
		            }
		        });   
			    imgDuck.startAnimation(FadeInAnimation);
	}
	void FadeOutDuck()
	{
		Animation FadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
			    
			FadeOutAnimation.setAnimationListener(new AnimationListener() {
		            public void onAnimationStart(Animation animation) {}
		            public void onAnimationRepeat(Animation animation) {}
		            public void onAnimationEnd(Animation animation) {
		            	imgDuck.setVisibility(View.GONE);
                        Intent intent = new Intent(Splash.this, MainMenu.class);
                        Splash.this.startActivity(intent);
                        Splash.this.finish();
		            }
		        });   
			    imgDuck.startAnimation(FadeOutAnimation);
	}
}