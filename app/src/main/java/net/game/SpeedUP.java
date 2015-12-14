package net.game;

import java.util.Random;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class SpeedUP {

	int range = 1000;
	int minRange = 500;
	int FRAMERATE = 120;
	int CanvasHeight;
	int CanvasWidth;
	Bitmap[] Texture = new Bitmap[3];
	long oldTime = System.currentTimeMillis();
	Context context;
	private int CurAnimation = 0;
	RectF Position;
	private int timeWaited = 0;
	private int waitTime;
	WaterBG water;
	int Velocity = 1;
	Surfer surfer;
	boolean IsColided = false;
	RectF BoudingRecTF;
	private Random r;
	private int top;
	public SpeedUP(Context context, int Height, int Width,WaterBG water, Surfer surfer)
	{
		this.context = context;
		CanvasHeight = Height;
		CanvasWidth = Width;
		LoadResources();
		Position = new RectF(-CanvasWidth +Texture[0].getWidth() ,250,0,0);
		BoudingRecTF = new RectF(); 
		this.water = water;
		this.surfer = surfer;
	}
	private void LoadResources() {
		if(PowerUps.isThereThePowerUp(PowerUps.ArrowPowerUp))
		{
		Texture[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.speedup_double_00);
		Texture[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.speedup_double_01);
		Texture[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.speedup_double_02);
		}
		else
		{
			Texture[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.speedup_00);
			Texture[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.speedup_01);
			Texture[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.speedup_02);
		}
		
	}
	public void update()
	{
		if(timeWaited == 0 )
		{
			r = new Random();
			waitTime = r.nextInt(range) + minRange;

		}
	move();
	UpdateAnimation();	
	checkTime();
	}
	private void move() {
		Position.left -= surfer.speed;
	}
	private void checkTime() {
		if(Position.left < 0)
		{
			if(timeWaited  >= waitTime)
			{
				RandomPosition();
				timeWaited = 0;
			}
			else
			{
				timeWaited+=1f;
			}
		}
	}
	private void RandomPosition() {
		top = r.nextInt(CanvasHeight - water.FoamTop) + water.FoamTop;
		Position.left = CanvasWidth;
		Position.top = top;
		Position.right = 0;
		Position.bottom = 0;
		IsColided  = false;
	}
	private void UpdateAnimation() {
		if(System.currentTimeMillis() - oldTime  > FRAMERATE)
		{
			if(CurAnimation == 2)
				CurAnimation  = 0;
			else 
				CurAnimation++;
			
			oldTime =(System.currentTimeMillis());
		}
		
	}
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(Texture[CurAnimation], Position.left,Position.top, null);
	}
	public void OnColision()
	{
		IsColided = true;	
		Position.left = -5000;
	}
	public RectF BoudingRecTF()
	{
		BoudingRecTF.left = Position.left;
		BoudingRecTF.top = Position.top;
		BoudingRecTF.right = Position.left + Texture[0].getWidth();
		BoudingRecTF.bottom = Position.top +Texture[0].getHeight();
		return BoudingRecTF;
	}
	public void Restart() {

		Position.left = -CanvasWidth;
		
	}

}
