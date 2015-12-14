package net.game;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.ads.AdRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */


	MainGamePanel panel;
    AdView adView;
	public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-9480879598480702/4025467071");
        adView.setAdSize(AdSize.SMART_BANNER);

        RelativeLayout rl = new RelativeLayout(this);
        RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        lay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().addTestDevice("13F77AAABEE7FB57CF4FA6BD90138B61").build();
        panel = new MainGamePanel(this,adView);
        rl.addView(panel);
        rl.addView(adView, lay);

        // Carregar o adView com a solicitação de anúncio.
        adView.loadAd(adRequest);
        setContentView(rl);

		//setContentView(panel);
	}
	@Override
	public void onBackPressed() {

		if(panel.level!= null)
			if(!panel.level.SurfGuy.dead)
				panel.level.isGamePaused = !panel.level.isGamePaused;
			
				
		return;
	}

	@Override
	public void onPause() {
		super.onPause();  // Always call the superclass method first
        adView.pause();
		if(panel.level!= null)
		{
			panel.thread.setRunning(false);

			if(panel.level.SurfGuy != null)
			{
				if(!panel.level.SurfGuy.dead)
				{
					panel.level.isGamePaused = true;
				}
				else
				{
					panel.level.isGamePaused = false;
				}
			}

			if(panel.level.speaker != null)
			{
				panel.level.speaker.StopMusic();
				panel.level.speaker.stopWave();
			}

		}
	}

	@Override
	public void onResume() {
		super.onResume();  // Always call the superclass method first
        adView.resume();
		if(panel.level!= null)
		{
			panel.newThread();
			return;
		}
	}
	@Override
    protected void onDestroy() {
		super.onDestroy();
        adView.destroy();
		if(panel.level!= null)
			if(panel.level.speaker != null)
			{
				panel.level.speaker.Release();
			}
		
		
        
    }





}