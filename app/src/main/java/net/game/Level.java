package net.game;
import net.surf.R;
import net.ui.MainMenu;
import net.ui.Shopping;
import Util.HeapLog;
import Util.MoveHelper;
import Util.PatternsHelper;
import Util.StorageInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;


public class Level {
	private static final String FINALM = "M";
	int CanvasWidth;
	int CanvasHeight;
	public Surfer SurfGuy;
	PatternsHelper patterns;
	Coins coins;
	DisplayMetrics metrics;
	Context _context;
	Player player;
	Sky skyBG;
	Ocean ocean;
	Boat boat;
	Birds birds;
	Onda onda;
	GodBar godBar;
	WaterBG waterBG;
	Shark shark;
	Squid squid;
	PauseScreen pauseScreen;
	EndGame endgame;
	Overlay overlay;
	Bitmap CoinHud;
	Typeface CustomFont;
	Paint PtNewFont = new Paint();
	public boolean isGamePaused = false;
	boolean loaded = false;
	Loading loading;
	public float Distance;
	Manobra manobra;
	float translateY = 0;
	SpeedUP speedUP;
	private RectF CoinHudPosition;
	Paint pt;
	int Dist;
	String ScoreText;
	String DistancText;
	Speaker speaker;
	HeapLog heap;
	private int i;
	private Bird sel_bird;
	boolean isCoinMagnetPowerUp = false;
	boolean isBirdRepelentPowerUp = false;
	boolean isSharkRepelentPowerUp = false;
	boolean isTwoXWaveSpeedPowerUp = false;
	boolean isOnBoat = false;
	Shell shell;
	Record record;
	private boolean isWaterRepelentPowerUp = false;
    AdView adView;
    Activity act;
    boolean adLoaded = false;
	public Level(Context context, int Height, int Width, AdView Ads) {
        System.gc();

        act = (Activity)context;
		_context = context;
        adView = Ads;
        adView.setVisibility(View.VISIBLE);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adLoaded = true;
            }
        });
		CanvasWidth = Width;
		CanvasHeight = Height;
		loading = new Loading(context, Height, Width,this);
	}
	public void LoadEveryFuckThing() {		
		speaker = new Speaker(_context);
		StorageInfo.getInstance((Activity) _context);
		loadSoundsInfoByStorage();
		player = new Player(_context);
		pt = new Paint(); 
		metrics = _context.getResources().getDisplayMetrics();
		//heap = new HeapLog();
		patterns = new PatternsHelper (_context);
		skyBG = new Sky(_context, CanvasHeight, CanvasWidth);
		ocean = new Ocean(_context,skyBG,this, CanvasHeight, CanvasWidth);
		record = new Record(_context, CanvasHeight, CanvasWidth, StorageInfo.storage.getMyRecord());
		endgame = new EndGame(_context, CanvasHeight, CanvasWidth,record,speaker, adView);
		waterBG = new WaterBG(_context, ocean, CanvasHeight, CanvasWidth, this);
		SurfGuy = new Surfer(this,player,ocean,waterBG, _context,CanvasHeight,CanvasWidth,endgame,speaker); //change to player items
		manobra = new Manobra(_context, CanvasHeight, CanvasWidth, SurfGuy);
		coins = new Coins(_context,CanvasHeight,CanvasWidth,this,SurfGuy);
		onda = new Onda(_context,SurfGuy,ocean,CanvasHeight,CanvasWidth);
		birds = new Birds(_context, CanvasHeight, CanvasWidth, SurfGuy);
		godBar = new  GodBar(_context, this, SurfGuy, CanvasHeight, CanvasWidth);
		shark = new Shark(_context, CanvasHeight, CanvasWidth, waterBG);
		squid = new Squid(_context, CanvasHeight, CanvasWidth, waterBG,SurfGuy);
		pauseScreen = new PauseScreen(_context, CanvasHeight, CanvasWidth);
		overlay = new Overlay (_context, CanvasHeight, CanvasWidth, SurfGuy);
		CoinHud = BitmapFactory.decodeResource( _context.getResources(), R.drawable.hud_coincounter_bg);
		speedUP = new SpeedUP(_context, CanvasHeight, CanvasWidth, waterBG,SurfGuy);
		boat = new Boat(_context, CanvasWidth, CanvasHeight, this, speaker);
		shell = new Shell(_context, CanvasHeight, CanvasWidth,  SurfGuy);

		CustomFont = Typeface.createFromAsset(_context.getAssets(), "fonts/comix_loud.ttf");
		PtNewFont.setColor(Color.WHITE);
		PtNewFont.setTextSize(12);
		PtNewFont.setTypeface(CustomFont);
		
		switch ((int)metrics.densityDpi)
		{
		case DisplayMetrics.DENSITY_LOW:
			PtNewFont.setTextSize(4);
			break; //LDPI
		case DisplayMetrics.DENSITY_MEDIUM:
			PtNewFont.setTextSize(9);
			
			break;	
		case DisplayMetrics.DENSITY_HIGH:
			PtNewFont.setTextSize(12);

			break;
		case DisplayMetrics.DENSITY_XHIGH:
			PtNewFont.setTextSize(17);

			break;
			
		case DisplayMetrics.DENSITY_XXHIGH:
			PtNewFont.setTextSize(22);
			break;
		}

		isCoinMagnetPowerUp = PowerUps.isThereThePowerUp(PowerUps.CoinMagnetPowerUp);
		isBirdRepelentPowerUp = PowerUps.isThereThePowerUp(PowerUps.BirdRepelentPowerUp);
		isSharkRepelentPowerUp = PowerUps.isThereThePowerUp(PowerUps.SharkRepelentPowerUp);
		isTwoXWaveSpeedPowerUp = PowerUps.isThereThePowerUp(PowerUps.ArrowPowerUp);
		isWaterRepelentPowerUp = PowerUps.isThereThePowerUp(PowerUps.WaterRepelent);
		CoinHudPosition = new  RectF(CanvasWidth - CoinHud.getWidth(), 0 , 0, 0);		
		loaded = true;		
		//Debug.startMethodTracing("myapp");
		speaker.startWave();
	}
	public void onTouch(MotionEvent event) {
		if(loaded)
		{
			if(!SurfGuy.dead)
			{
				if(!isGamePaused && !isOnBoat)
					SurfGuy.onTouch(event);
				else if (!isGamePaused && isOnBoat)
					boat.onTouch(event);
				else
					pauseScreen.onTouch(event);
			}
			else
			{
				endgame.onTouch(event);
			}
		}
	}
	public void Draw(Canvas canvas) {
		if(loaded)
		{
			if(!SurfGuy.trickingGOD && !SurfGuy.turningGOD)
			{
				DrawCamera(canvas);
				drawSky(canvas);
				drawOcean(canvas);
				drawWaterBG(canvas);
				drawObstacles(canvas);
			}
			if(boat.gettingOff)
				boat.draw(canvas);

			if(isOnBoat)
			{
				boat.draw(canvas);
			}
			else
			{
				SurfGuy.draw(canvas);
			}


			godBar.draw(canvas);
			if(!SurfGuy.trickingGOD && !SurfGuy.turningGOD)
			{
				drawWave(canvas);
			}
			DrawScore(canvas);
			if (isGamePaused)
				pauseScreen.draw(canvas);

			manobra.draw(canvas);
			//DrawHeap(canvas);
			overlay.draw(canvas);
			if(SurfGuy.dead){
				endgame.draw(canvas);
			}


		}
		else
		{
			loading.Draw(canvas);

		}
	}

	private void DrawCamera(Canvas canvas) {

		if(!isGamePaused)
		{
			
			canvas.translate(0, translateY);
		}
		else
		{
			
			canvas.translate(0, 0);
		}
			
		
		
	}

	private void drawWave(Canvas canvas) {
		onda.draw(canvas);

	}

	private void drawWaterBG(Canvas canvas) {
		waterBG.Draw(canvas);
	}
	private void drawOcean(Canvas canvas) {
		ocean.Draw(canvas);
	}
	private void drawSky(Canvas canvas) {
		skyBG.Draw(canvas);

	}
	public void update() {

		if(loaded)
		{
			speaker.update();
			
			if(!isGamePaused)
			{
				UpdateCamera();
				if(!SurfGuy.dead)
				{
					skyBG.update();
					ocean.update();
					waterBG.update();
					if(!isOnBoat)
						SurfGuy.update();
					else
					{
						boat.update();
						checkCoinColision();
						checkSquidColision();
						checkSpeedColision();
						checkMagneticAreaCoinColision();
					}

					if(boat.gettingOff)
					{
						//SurfGuy.update();
						boat.update();	
					}

					godBar.update();
					overlay.update();
					manobra.update();
					if(SurfGuy.hasStarted && !SurfGuy.trickingGOD && !SurfGuy.turningGOD)
					{
						Distance+= SurfGuy.speed * 0.01;
						updateObstacles();
						if(!isOnBoat)
							CheckColisions();
						playSounds();
					}
				}
				else
				{
					endgame.update();
					if(endgame.isButtonShopPressed)
					{
						endgame.isButtonShopPressed = false;
						Intent intent = new Intent(_context, Shopping.class);
						((Activity) _context).finish();
						((Activity) _context).startActivity(intent);
					}

					if(endgame.isButtonReplayPressed)
					{

						endgame.isButtonReplayPressed = false;
						loaded = false;
						ResetALL();
					}
				}
			}
			else
			{
				if(pauseScreen.isButtonResumePressed)
				{
					isGamePaused = false;
					pauseScreen.resetStatus();
				}
				if(pauseScreen.isButtonQuitPressed)
				{
					
					isGamePaused = false;
					Intent intent = new Intent(_context, MainMenu.class);
					((Activity) _context).finish();
					((Activity) _context).startActivity(intent);
				}

				if(pauseScreen.buttonMusicCounter == 1)
				{
					if(!speaker.isMusicMuted)
					{
						speaker.MuteMusic();
					}
				}
				else
				{
					if(speaker.isMusicMuted)
					{
						speaker.UnMuteMusic();
					}
				}


				if(pauseScreen.buttonSoundsCounter == 1)
					speaker.MuteSFX();
				else
				{
					speaker.UnMuteSFX();
				}

			}
		}
		else
		{
			loading.update();
		}
	}
	private void UpdateCamera() {
		
		if(SurfGuy.Str > 0)
			translateY+= SurfGuy.Str;
		else if(SurfGuy.Down > 0)
			if(translateY - SurfGuy.Down > -1)
				translateY-= SurfGuy.Down;
			else
				translateY = 0;

		if(!SurfGuy.tricking)
		{
			CoinHudPosition.top = 0;
			translateY = 0;
			
		}
		float destTop = (godBar.BackMap.getHeight() * 0.9f) - translateY;
		float destBottom = destTop + godBar.BackMap.getHeight();
		
		godBar.HudPosition.top = -translateY;
		godBar.bgDest.top =destTop ;
		godBar.bgDest.bottom = destBottom;
		godBar.barDest.top = destTop;
		godBar.barDest.bottom = destBottom;
		
	}
	private void ResetALL() {
		//Debug.stopMethodTracing();

        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(adLoaded)
                adView.setVisibility(View.INVISIBLE);
            }
        });
		SurfGuy.Restart();
		coins.Restart();
		onda.Restart();
		birds.Restart();
		godBar.Restart();
		shark.Restart();
		speedUP.Restart(); 
		endgame.Restart();
		manobra.Restart();
		boat.Restart();
		record.Restart();
		squid.Restart();
		isOnBoat = false;
		Distance = 0;
		loadSoundsInfo();
		loaded = true;
		System.gc();
	}
	private void CheckColisions() {
		if(!SurfGuy.dyeing && !SurfGuy.dead && !SurfGuy.dyeingForShark)
		{
			checkCoinColision();
			checkMagneticAreaCoinColision();
			checkBirdColision();
			checkSharkColision();
			checkSquidColision();
			checkWaveColision();
			checkSpeedColision();
			checkShellColision();
		}
	}

	private void checkShellColision() {
		if(!shell.isColided)
		{
			if(RectF.intersects(shell.BoudingRecTF(), SurfGuy.BoudingRecTF()))
			{
				shell.onColision();
				SurfGuy.shellCount++;

			}
		}


	}
	private void playSounds (){
		playBirdSound ();
	}

	private void playBirdSound (){
		for (i=0;i<birds.birds.size();i++){
			if (!birds.birds.get(i).isGone){
				if(!birds.playedSound)
				{
					birds.playedSound = true;
					speaker.BirdSound();
					return;
				}
			}
		}
	}

	private void checkSpeedColision() {
		if(!speedUP.IsColided)
		{
			if(RectF.intersects(speedUP.BoudingRecTF(), SurfGuy.BoudingRecTF()))
			{
				speedUP.OnColision();
				if(isTwoXWaveSpeedPowerUp)
					SurfGuy.speed *= PowerUps.FasterArrow;	
				else
					SurfGuy.speed *=1.3;

			}
		}

	}
	private void checkWaveColision() {
		if(onda.ConerUPPosition.left >= SurfGuy.Position.left && SurfGuy.Position.top + SurfGuy.DrawableHeight >= waterBG.FoamBottom)
		{
			SurfGuy.dyeing = true;
		}
	}

	private void checkSquidColision() {
		if(RectF.intersects(squid.BoudingRecTF(), SurfGuy.BoudingRecTF()))
		{
			if(!isOnBoat)
			{
				GetOnBoat();
				squid.onColision();
			}
		}
	}

	private void checkSharkColision() {

		if(RectF.intersects(shark.BoudingRecTF(), SurfGuy.BoudingRecTF()))
		{
			shark.onColision();
			SurfGuy.dyeingForShark = true;
		}
	}
	private void checkBirdColision() {
		for (i=0; i< birds.birds.size(); i++)
		{
			sel_bird = birds.birds.get(i);
			if (RectF.intersects(sel_bird.BoudingRecTF(), SurfGuy.BoudingRecTF()) && !SurfGuy.dyeing){
				sel_bird.onColision();
				SurfGuy.dyeing = true;
				return;
			}
		}
	}

	private void checkCoinColision() {
		for (int i=0; i<coins.coins-1; i++)
		{
			if(!coins.collectedList[i] &&  !coins.isGoneList[i])
				if(RectF.intersects(coins.BoudingRecTF(i), SurfGuy.BoudingRecTF()))
				{
					coins.OnCollect(i);
					speaker.CoinCollect();
				}
		}
	}

	private void checkMagneticAreaCoinColision() {
		if (isCoinMagnetPowerUp){
			for (int i=0; i<coins.coins-1; i++)
			{
				if(!coins.collectedList[i] && !coins.isGoneList[i])
					if(RectF.intersects(coins.BoudingRecTF(i), SurfGuy.loadMagneticArea()))
					{
						RectF newPosition = MoveHelper.MoveTowards(coins.BoudingRecTF(i), SurfGuy.BoudingRecTF(), 15f);
						coins.Pos[i].left += newPosition.left;
						coins.Pos[i].top += newPosition.top;
						coins.Pos[i].right += newPosition.right;
						coins.Pos[i].bottom += newPosition.bottom;
					}
			}
		}
	}

	private void drawObstacles(Canvas canvas) {
		coins.draw(canvas);
		birds.draw(canvas);
		shark.draw(canvas);
		squid.draw(canvas);
		speedUP.draw(canvas);
		shell.draw(canvas);
		record.draw(canvas);
	}
	private void updateObstacles() {
		coins.update();
		record.update(Distance, SurfGuy.speed);

		if (!isBirdRepelentPowerUp)
			birds.update();

		if (!isWaterRepelentPowerUp)
			onda.update();

		if (!isSharkRepelentPowerUp)
			shark.update();

		squid.update();
		speedUP.update();
		shell.update();
	}
	private void DrawScore(Canvas canvas) {
		canvas.drawBitmap(CoinHud,CoinHudPosition.left,CoinHudPosition.top-translateY,null);
		ScoreText = String.valueOf(SurfGuy.coinsColected); //String.format("%06d",SurfGuy.coinsColected);
		Dist = (int)Distance;
		DistancText = String.valueOf(Dist);

		DistancText = FormatText(DistancText) + FINALM;
		ScoreText = FormatText(ScoreText) ;

		canvas.drawText(ScoreText, CanvasWidth - CoinHud.getWidth() * 0.7f,(CoinHud.getHeight() * 0.6f) - translateY, PtNewFont);
		canvas.drawText(DistancText, CanvasWidth - CoinHud.getWidth() * 0.7f, (CoinHud.getHeight() + CoinHud.getHeight() * 0.3f) - translateY , PtNewFont);


	}
	private String FormatText(String Text) {
		if(Text.length() == 1)
		{
			Text = "0000" + Text;
		}
		if(Text.length() == 2)
		{
			Text = "000" + Text;
		}
		if(Text.length() == 3)
		{
			Text = "00" + Text;
		}
		if(Text.length() == 4)
		{
			Text = "0" + Text;
		}

		return Text;

	}
	public void onLongPress(MotionEvent arg0) {


	}
	public void onSingleTapUp(MotionEvent arg0) {

	}
	public void onFlingHorizontalLeftToRight(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY) {
		if(loaded)
			SurfGuy.onFlingHorizontalLeftToRight(e1,e2,velocityX,velocityY);
	}
	public void onFlingHorizontalRightToLeft(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY) {
		if(loaded)
			SurfGuy.onFlingHorizontalRightToLeft(e1,e2,velocityX,velocityY);
	}
	public void onFlingVerticalTopToBottom(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY) {
		if(loaded)
			SurfGuy.onFlingVerticalTopToBottom(e1,e2,velocityX,velocityY);
	}
	public void onFlingVerticalBottomToTop(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY) {
		if(loaded)
			SurfGuy.onFlingVerticalBottomToTop(e1,e2,velocityX,velocityY);

	}

	private void loadSoundsInfo (){
		if(pauseScreen.buttonMusicCounter == 1)
		{
			if(!speaker.isMusicMuted)
			{
				speaker.MuteMusic();
			}
		}
		else
		{
			if(speaker.isMusicMuted)
			{
				speaker.UnMuteMusic();
			}
		}


		if(pauseScreen.buttonSoundsCounter == 1)
			speaker.MuteSFX();
		else
		{
			speaker.UnMuteSFX();
		}

	}

	private void loadSoundsInfoByStorage (){
		if(!StorageInfo.storage.isOnStatusMusic())
		{
			if(!speaker.isMusicMuted)
			{
				speaker.MuteMusic();
			}
		}
		else
		{
			if(speaker.isMusicMuted)
			{
				speaker.UnMuteMusic();
			}
		}


		if(!StorageInfo.storage.isOnSFXStatus())
			speaker.MuteSFX();
		else
		{
			speaker.UnMuteSFX();
		}

	}
	public void GetOnBoat() {
		isOnBoat = true;
		boat.Position.top = SurfGuy.Position.top;
		boat.gettingOn = true;

	}

}
