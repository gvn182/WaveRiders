package net.ui;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import net.game.MainActivity;
import net.surf.R;
import Util.StorageInfo;
import Util.StorageInfoHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

public class MainMenu extends Activity implements OnClickListener{
	private boolean doubleBackToExitPressedOnce;
	MediaPlayer mp1;
	boolean isDisplaying = false;
    AdView adView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main_menu);


        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-9480879598480702/4025467071");
        adView.setAdSize(AdSize.BANNER);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainAD);

        // Adicionar o adView a ele.
        layout.addView(adView);

        // Iniciar uma solicitação genérica.
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("13F77AAABEE7FB57CF4FA6BD90138B61").build();
        // Carregar o adView com a solicitação de anúncio.
        adView.loadAd(adRequest);


		mp1 = MediaPlayer.create(this, R.raw.ducksound);

		RelativeLayout myLay = (RelativeLayout) findViewById(R.id.mainLayout);
        myLay.setOnClickListener(this);

		ImageButton btnCredits = (ImageButton) findViewById(R.id.btnCredits);
		btnCredits.setOnClickListener(this);

		ImageButton btnSurfShop = (ImageButton) findViewById(R.id.btnSurfShop);
		btnSurfShop.setOnClickListener(this);

		ImageButton btnSurfNow = (ImageButton) findViewById(R.id.btnSurfNowMenu);
		btnSurfNow.setOnClickListener(this);
		
		ImageButton btnTutorial = (ImageButton) findViewById(R.id.btnTutorial);
		btnTutorial.setOnClickListener(this);

		ImageView imgLogo = (ImageView) findViewById(R.id.imgDuckLogo);
		imgLogo.setOnClickListener(this);

		ImageButton btnShare = (ImageButton) findViewById(R.id.TheShare);
		btnShare.setOnClickListener(this);

		CheckRate();


	}


	@Override
	public void onClick(View btn) {
		if(isDisplaying)
		{
			ImageView creditsImage = (ImageView) findViewById(R.id.aboutSplash);
			creditsImage.setVisibility(View.INVISIBLE);

			ImageView imgDarkBG = (ImageView) findViewById(R.id.imgDarkBG);
			imgDarkBG.setVisibility(View.INVISIBLE);
			isDisplaying = false;
			return;
		}


		switch (btn.getId()) {
		case R.id.btnSurfNowMenu:
		{
			if(StorageInfo.storage.HasPlayed())
			{
				Intent intent = new Intent(MainMenu.this, MainActivity.class);
				MainMenu.this.finish();
				MainMenu.this.startActivity(intent);
			}
			else
			{
				ShowWantTuto();

			}

		}break;
		case R.id.btnSurfShop:
		{
			Intent intent = new Intent(MainMenu.this, net.ui.Shopping.class);
			this.startActivity(intent);

		}	break;
		case R.id.btnTutorial:
		{
			StorageInfo.storage.setHasPlayed();
			Intent intent = new Intent(MainMenu.this, Tutorial.class);
			MainMenu.this.startActivity(intent);

		}	break;
		case R.id.btnCredits: {
			isDisplaying = true;

			ImageView creditsImage = (ImageView) findViewById(R.id.aboutSplash);
			creditsImage.setVisibility(View.VISIBLE);

			ImageView imgDarkBG = (ImageView) findViewById(R.id.imgDarkBG);
			imgDarkBG.setVisibility(View.VISIBLE);


		}break;
		case R.id.imgDuckLogo:
		{
			mp1.start();
		}break;

		case R.id.TheShare:
		{
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I'm playing Wave Riders, check it out: https://play.google.com/store/apps/details?id=net.surf , It's FREE!!");
			startActivity(Intent.createChooser(shareIntent, "Share..."));		

		}break;
		}

	}
	private void ShowWantTuto() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Tutorial").
		setMessage("We STRONGLY recommend you to see the tutorial before playing. \n" +
				"Do you want to see the tutorial?");


		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				StorageInfo.storage.setHasPlayed();
				
			}});


		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				StorageInfo.storage.setHasPlayed();
				Intent intent = new Intent(MainMenu.this, Tutorial.class);
				MainMenu.this.startActivity(intent);
			}});


		AlertDialog dialog = builder.create();
		dialog.show();

	}


	@Override
	protected void onDestroy() {
        adView.destroy();
		mp1.release();
		super.onDestroy();
	}


	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce=false;

			}
		}, 2000);
	}

	private void CheckRate() {

		if(Integer.parseInt(StorageInfo.storage.getMyGold()) >= 1000 && StorageInfo.storage.CheckRate()  == 0)
		{

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Rate us!").
			setMessage("Like our and Free-to-Play Game ? \n" +
					"Support us and take a few seconds to give your rating !");


			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					StorageInfo.storage.SetRate(1);
				}});

			builder.setNeutralButton("Ask me later", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {

				}});

			builder.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					MainMenu.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=net.surf")));
				}});


			AlertDialog dialog = builder.create();
			dialog.show();
		}

	}
	@Override
	protected void onStart()
	{
		super.onStart();
		FlurryAgent.onStartSession(this, "RZ9BQMB7XQVHK6GWT6KF");
	}
	@Override
	protected void onStop()
	{
		super.onStop();		
		FlurryAgent.onEndSession(this);
	}
    @Override
    protected void onResume() {
        adView.resume();
        super.onResume();
    }
    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
    }
}