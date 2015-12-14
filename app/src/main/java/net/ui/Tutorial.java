package net.ui;

import com.flurry.android.FlurryAgent;

import net.game.MainActivity;
import net.surf.R;
import Util.StorageInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Tutorial extends Activity implements OnClickListener{
	int currentTuto = 0;
	Drawable[] TutorialMAP = new Drawable[7];
	RelativeLayout layout;
	ImageButton btnPrevious;
	ImageButton btnNext;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		setContentView(R.layout.tutorial);
		layout = (RelativeLayout) findViewById(R.id.tutoLayout);
		
		LoadTutors();
		btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
		btnPrevious.setOnClickListener(this);

		btnNext = (ImageButton) findViewById(R.id.btnNext);
		btnNext.setOnClickListener(this);
		ChangeBackground();
	}


	private void LoadTutors() {
		TutorialMAP[0] = getResources().getDrawable(R.drawable.tutorial_bg_01);
		TutorialMAP[1] = getResources().getDrawable(R.drawable.tutorial_bg_02);
		TutorialMAP[2] = getResources().getDrawable(R.drawable.tutorial_bg_03);
		TutorialMAP[3] = getResources().getDrawable(R.drawable.tutorial_bg_04);
		TutorialMAP[4] = getResources().getDrawable(R.drawable.tutorial_bg_05);
		TutorialMAP[5] = getResources().getDrawable(R.drawable.tutorial_bg_06);

	}

	void ChangeBackground()
	{
		if(currentTuto == 0)
			btnPrevious.setEnabled(false);
		else
			btnPrevious.setEnabled(true);
		
		layout.setBackgroundDrawable(TutorialMAP[currentTuto]);
	}
	@Override
	public void onClick(View btn) {

		switch (btn.getId()) {
		case R.id.btnPrevious:
		{
			if(currentTuto > 0)
			{
				currentTuto--;
				ChangeBackground();
			}
		}break;
		case R.id.btnNext:
		{
			if(currentTuto < 5)
			{
				currentTuto++;
				ChangeBackground();

			}
			else
			{
				Intent intent = new Intent(Tutorial.this, MainMenu.class);
				Tutorial.this.finish();
				Tutorial.this.startActivity(intent);	
			
			}


		}	break;
		}
	}
}