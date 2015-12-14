package net.game;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class Boat {

	Context context;
	int CanvasWidth;
	int CanvasHeight;
	Surfer surfer;
	float MaxSpeed;
	Bitmap[] BoatMap = new Bitmap[3];
	Bitmap[] HopOnMap = new Bitmap[4];
	Bitmap[] BoatSplash = new Bitmap[3];
	private int CurAnimation;
	private long BoatAnimation;
	private long BoatAnimationTrick = 120;
	boolean gettingOn = false;
	boolean running;
	boolean gettingOff;
	boolean off;
	boolean isPlaying = false;
	private int CurHopeAnimation = 0;
	RectF Position;
	Paint transparentpainthack = new Paint();
	int Alpha = 100;
	Level level;
	private Speaker speaker;
	float TheSpeed = 0;
	private boolean touchingLeft;
	private boolean touchingRight;
	private float TimeToWaitIndex;
	float Velocity;
	public Boat(Context context,int CanvasWidth, int CanvasHeight, Level level, Speaker speaker)
	{
		this.CanvasWidth = CanvasWidth;
		this.CanvasHeight = CanvasHeight;
		float scaleWidth = (CanvasWidth / 800);
		this.context = context;
		this.level = level;
		this.speaker = speaker;
		this.surfer = level.SurfGuy;
		Position = new RectF(surfer.Position.left, surfer.Position.top, 0, 0);
		LoadResources();
		MaxSpeed = 8 * scaleWidth;
		Velocity = 0.1f * scaleWidth;
		
	}
	private void LoadResources() {
		BoatMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat_0);
		BoatMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat_1);
		BoatMap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat_2);
						
		BoatSplash[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.fx_boatsplash_0);
		BoatSplash[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.fx_boatsplash_1);
		BoatSplash[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.fx_boatsplash_2);

		HopOnMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.fx_hopon_0);
		HopOnMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.fx_hopon_1);
		HopOnMap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.fx_hopon_2);
		HopOnMap[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.fx_hopon_3);
	}
	public void update()
	{
		if(gettingOn)
		{
			if (!isPlaying){
				speaker.StartEngine();
				isPlaying = true;
			}
			
			UpdateGetOnAnimation();			
		}

		if(running)
		{
			if (touchingLeft){
				if (Position.top >= level.waterBG.FoamBottom){
					Position.top--;
					surfer.Position.top--;
				}
			}
			if(touchingRight){
				if (CanvasHeight >= Position.top + BoatMap[0].getHeight()){
					Position.top++;
					surfer.Position.top++;
				}
			}
				
			if(TheSpeed <= MaxSpeed)
			{
				surfer.speed += Velocity;
				TheSpeed += Velocity;
			}
			else 
			{
				running = false;
				level.isOnBoat = false;
				gettingOff = true;
				surfer.touchingLeft = false;
				surfer.touchingRight = false;
				surfer.Index = 4;
				TheSpeed = 0;
			}

			if(BoatAnimationTrick >= 0)
				BoatAnimationTrick -= 1f;
				
			UpdateAnimation();
		}
		
		if(gettingOff)
		{
			Alpha -= 1;
			transparentpainthack.setAlpha(Alpha);
			Position.left += 1f;
			Position.top += 1f;
			isPlaying = false;
			if(Alpha <= 0)
			{
				gettingOff = false;
				CurHopeAnimation = 0;
				CurAnimation = 0;
				BoatAnimationTrick = 120;
				Alpha = 200;
				Position.left = surfer.Position.left;
				transparentpainthack.setAlpha(Alpha);
			}
		}

	}
	private void UpdateGetOnAnimation() {
		if(System.currentTimeMillis() - BoatAnimation >= BoatAnimationTrick)
		{
			if(CurHopeAnimation < 3)
			{
				CurHopeAnimation++;
			}
			else
			{
				running = true;
				gettingOn = false;

			}
			BoatAnimation = (System.currentTimeMillis());
		}

	}
	private void UpdateAnimation() {
		if(System.currentTimeMillis() - BoatAnimation >= BoatAnimationTrick)
		{
			if(CurAnimation < 2)
			{
				CurAnimation++;
			}
			else
			{
				CurAnimation =0;
			}
			BoatAnimation = (System.currentTimeMillis());
		}
	}
	public void draw(Canvas canvas)
	{
	
		
		canvas.drawBitmap(BoatSplash[CurAnimation], Position.left - HopOnMap[0].getWidth() ,Position.top, transparentpainthack);
		canvas.drawBitmap(BoatMap[CurAnimation], Position.left,Position.top, transparentpainthack);
		if(!gettingOff)
		canvas.drawBitmap(surfer.Head[4], Position.left,Position.top, null);
		
		if(gettingOn)
			canvas.drawBitmap(HopOnMap[CurHopeAnimation], surfer.Position.left,surfer.Position.top, null);
		
	}
	public void onTouch(MotionEvent event) {
		touchingLeft = false;
		touchingRight = false;
		int PointerID = event.getPointerId(event.getPointerCount()-1);
		int PointerIndex = event.findPointerIndex(PointerID);
		if(PointerIndex != 0)
		{
			switch (event.getActionMasked())
			{    
			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_POINTER_DOWN:
			{
				if (event.getX(PointerIndex) >= CanvasWidth/2)
					touchingRight = true;
				else
					touchingLeft = true;
			}break; 

			case MotionEvent.ACTION_POINTER_UP:
			{
				if(event.getActionIndex() == PointerIndex)
				{

					TimeToWaitIndex = Surfer.DefaultTimeToWait;
				}

			}break;
			}
		}
		else
		{
			switch (event.getAction())
			{
			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_DOWN:
			{
				if (event.getX() >= CanvasWidth/2)
					touchingRight = true;
				else
					touchingLeft = true;
			}break;

			case MotionEvent.ACTION_POINTER_UP:
			{

				TimeToWaitIndex = Surfer.DefaultTimeToWait;

			}break;
			}
		}
	}
	public void Restart()
	{
		gettingOn = false;
		running = false;
		gettingOff = false;
		CurHopeAnimation = 0;
		CurAnimation = 0;
		BoatAnimationTrick = 120;
		Alpha = 200;
		Position.left = surfer.Position.left;
		transparentpainthack.setAlpha(Alpha);
	}
	public void Start()
	{

	}
}