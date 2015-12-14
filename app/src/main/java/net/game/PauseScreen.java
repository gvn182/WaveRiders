package net.game;

import net.surf.R;
import Util.StorageInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

public class PauseScreen {

	private static final int maxButtonMusicCounter = 3;
	private static final int maxButtonSoundsCounter = 3;
	private static final int maxButtonQuitCounter = 2;
	private static final int maxButtonResumeCounter = 2;
	
	Context context;
	Bitmap darkScreen;
	Bitmap[] buttonMusic = new Bitmap[maxButtonMusicCounter];
	Bitmap[] buttonSounds = new Bitmap[maxButtonSoundsCounter];
	Bitmap[] buttonQuit = new Bitmap[maxButtonQuitCounter];
	Bitmap[] buttonResume = new Bitmap[maxButtonResumeCounter];
	Bitmap pauseBG;
	
	private int CanvasHeight;
	private int CanvasWidth;
	private int pauseBGHeigth;
	private int pauseBGWidth;
	int buttonMusicCounter = 0;
	int buttonSoundsCounter = 0;
	private int buttonQuitCounter = 0;
	private int buttonResumeCounter = 0;
	
	boolean isButtonMusicPressed = false;
	boolean isButtonSoundsPressed = false;
	boolean isButtonQuitPressed = false;
	boolean isButtonResumePressed = false;

	public RectF ResumePosition;
	private RectF QuitPosition;
	private RectF SoundFxPosition;
	private RectF MusicPosition;
	RectF ClickedPosition;
	private  int bmWidth;
	private  int bmHeight;
	private float y;
	private int x;
	
	public PauseScreen (Context myContext, int myCanvasHeight, int myCanvasWidth){
		context = myContext;
		CanvasHeight = myCanvasHeight;
		CanvasWidth = myCanvasWidth;
		darkScreen = BitmapFactory.decodeResource(context.getResources(), R.drawable.black_square);
		initButtons ();
		pauseBG = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_bg);
		ClickedPosition = new RectF();
		initPositions();
	}
	
	private void initPositions() {
		
		if (StorageInfo.storage.isOnSFXStatus()){
			buttonSoundsCounter = 0;
			isButtonSoundsPressed = false;
		}else {
			buttonSoundsCounter = 1;
			isButtonSoundsPressed = true;
		}
		
		if (StorageInfo.storage.isOnStatusMusic()){
			buttonMusicCounter = 0;
			isButtonMusicPressed = false;
		}else {
			buttonMusicCounter = 1;
			isButtonMusicPressed = true;
		}
		
		pauseBGHeigth = CanvasHeight/2 - pauseBG.getHeight()/2;
		pauseBGWidth = CanvasWidth/2 - pauseBG.getWidth()/2;
		
		float left =pauseBGWidth;
		float top = pauseBGHeigth*2.5f;
		float right = left + buttonResume[buttonResumeCounter].getWidth();
		float bottom = top + buttonResume[buttonResumeCounter].getHeight();
		ResumePosition = new RectF( left,top , right, bottom);
		
		left = right + buttonQuit[buttonQuitCounter].getWidth();
		right = left + buttonQuit[buttonQuitCounter].getWidth();
		QuitPosition = new RectF (left,top,right,bottom);
		
		left = ResumePosition.right - buttonSounds[buttonSoundsCounter].getWidth();
		top = ResumePosition.bottom + buttonSounds[buttonSoundsCounter].getHeight()/3;
		right = left + buttonSounds[buttonSoundsCounter].getWidth();
		bottom = top + buttonSounds[buttonSoundsCounter].getHeight();
		SoundFxPosition = new RectF (left,top,right,bottom);
		
		left= right + buttonMusic[buttonMusicCounter].getHeight();
		right = left + buttonMusic[buttonMusicCounter].getWidth();
		bottom = top + buttonMusic[buttonMusicCounter].getHeight();
		MusicPosition = new RectF(left,top,right,bottom);
	}

	private void initButtons (){
		buttonMusic[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_music);
		buttonMusic[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_music_mute);
		
		buttonSounds[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_sounds);
		buttonSounds[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_sounds_mute);
		
		buttonResume[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_resume);
		buttonResume[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_resume_pressed);
		
		buttonQuit[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_quit);
		buttonQuit[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_quit_pressed);		
	}
	
	public void update()
	{
		
	}
	
	public void draw(Canvas canvas)
	{
		drawBG(canvas);
		drawButtons(canvas);
	}
	
	public void drawButtons (Canvas canvas){
		canvas.drawBitmap(buttonResume[buttonResumeCounter], ResumePosition.left,ResumePosition.top, null);
		canvas.drawBitmap(buttonQuit[buttonQuitCounter], QuitPosition.left,QuitPosition.top, null);
		canvas.drawBitmap(buttonSounds[buttonSoundsCounter], SoundFxPosition.left,SoundFxPosition.top, null);
		canvas.drawBitmap(buttonMusic[buttonMusicCounter], MusicPosition.left,MusicPosition.top, null);
	}
	
	public void drawBG (Canvas canvas){
		bmWidth = darkScreen.getWidth();
		bmHeight = darkScreen.getHeight();

		for (y = 0;y < CanvasHeight;y+=bmHeight){
			for (x = 0;x < CanvasWidth;x+=bmWidth){
				canvas.drawBitmap(darkScreen, x, y, null);
			}
		}
		canvas.drawBitmap(pauseBG, pauseBGWidth, pauseBGHeigth, null);
	}
	public void onTouch(MotionEvent event)
	{
		ClickedPosition.left = event.getX();
		ClickedPosition.top = event.getY();
		ClickedPosition.right = event.getX();
		ClickedPosition.bottom = event.getY() + 1;
		
		switch (event.getAction())
		{    
		case MotionEvent.ACTION_DOWN:
		{
			if(RectF.intersects(ClickedPosition, ResumePosition))
				buttonResumeCounter++;
			
			if(RectF.intersects(ClickedPosition, QuitPosition))
				buttonQuitCounter++;
			
			if(RectF.intersects(ClickedPosition, MusicPosition))
			if(buttonMusicCounter == 0){
				buttonMusicCounter++;
				StorageInfo.storage.setIsOnStatusMusic(false);
			}else {
				buttonMusicCounter--;
				StorageInfo.storage.setIsOnStatusMusic(true);
			}			
			
			if(RectF.intersects(ClickedPosition, SoundFxPosition))
				if(buttonSoundsCounter == 0){
					buttonSoundsCounter++;
					StorageInfo.storage.setIsOnSFXStatus(false);
				}
				else {
					buttonSoundsCounter--;
					StorageInfo.storage.setIsOnSFXStatus(true);
				}
			
		}break;

		case MotionEvent.ACTION_UP:
		{
			
			if(RectF.intersects(ClickedPosition, ResumePosition))
				if(buttonResumeCounter  == 1)
				{
					isButtonResumePressed = true;
					
				}
			
			if(RectF.intersects(ClickedPosition, QuitPosition))
				if(buttonQuitCounter == 1)
				{
					isButtonQuitPressed = true;
					
				}
			
			if(RectF.intersects(ClickedPosition, MusicPosition))
				if(buttonMusicCounter == 1)
				{
					isButtonSoundsPressed = true;	
				}

			
			if(RectF.intersects(ClickedPosition, SoundFxPosition))
				if(buttonSoundsCounter == 1)
				{
					isButtonSoundsPressed = true;
				}
			
			buttonQuitCounter = 0;
			buttonResumeCounter = 0;
		}break;
		}
	}

	public void resetStatus() {

		isButtonMusicPressed = !StorageInfo.storage.isOnStatusMusic();
		isButtonSoundsPressed = !StorageInfo.storage.isOnSFXStatus();
		isButtonQuitPressed = false;
		isButtonResumePressed = false;
	}

	public void Restart() {

		
	}
	
}
