package net.game;

import java.util.Random;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Shark {
	private float timeWaited = 0;
	float waitTime;
	int range = 1000;
	int minRange = 500;
	int defaultCountStalk = 3;
	int countStalk = 0;
	Paint Pencil = new Paint();
	float Velocity = 3f;
	final float defaulFrameRate = 5f;
	float frameRate = defaulFrameRate;
	Bitmap SharkAnimMap;
	Bitmap SharkAnimMapFliped;
	Bitmap[] RippleMap = new Bitmap[3];
	Context context;
	private int CanvasHeight;
	private int CanvasWidth;
	Rect Source;
	RectF Dest;
	int AnimIndex =0;
	int maxAnimIndex = 0;
	WaterBG water;
	RectF RipplePosition;
	boolean goingLeft;
	boolean done;
	private int Index = 0;
	long oldTime = System.currentTimeMillis();
	private static final float FRAMERIPPLE = 150; //millis
	Random random;
	RectF BoudingRecTF;
	private int top;
	private int left;
	private int right;
	private int bottom;
	public Shark(Context myContext, int myCanvasHeight, int myCanvasWidth,WaterBG water)
	{
		context = myContext;
		CanvasHeight = myCanvasHeight;
		CanvasWidth = myCanvasWidth;
		this.water = water;
		SharkAnimMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shark_attack);
		SharkAnimMapFliped = Util.BitMapHelper.LoadFliped(SharkAnimMap);
		RippleMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.death_04);
		RippleMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.death_05);
		RippleMap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.death_06);
		Dest = new RectF(-CanvasWidth, 0, -CanvasWidth + SharkAnimMap.getHeight(), SharkAnimMap.getHeight());
		RipplePosition = new RectF(Dest.left, Dest.top, 0, 0);
		maxAnimIndex = SharkAnimMap.getWidth() / SharkAnimMap.getHeight();
		maxAnimIndex--;
		goingLeft = true;
		BoudingRecTF = new RectF();
		random = new Random();
		Source = new Rect();
		Velocity = (float)myCanvasWidth / 260;

	}
	private void RandomPosition() {
		top = random.nextInt(CanvasHeight - water.FoamTop) + water.FoamTop;
		Dest = new RectF(CanvasWidth, top, CanvasWidth + SharkAnimMap.getHeight(), top + SharkAnimMap.getHeight());
		countStalk = defaultCountStalk;

	}
	private void checkTime() {
		if(Dest.left < 0 && countStalk == 0)
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
	public void update()
	{
		if(timeWaited == 0 )
		{
			waitTime = random.nextInt(range) + minRange;

		}
		updateRippleIndex();
		checkTime();
		move();
		changeDirection();
		changeAnimation();



	}
	private void updateRippleIndex() {
		if(System.currentTimeMillis() - oldTime  > FRAMERIPPLE)
		{

			switch (Index)
			{
			case 0: Index++; break;
			case 1: Index++; break;
			case 2: Index=0; break;
			}
			oldTime = (System.currentTimeMillis());
		}

	}
	private void changeDirection() {
		if(countStalk > 0)
		{
			if(Dest.left + SharkAnimMap.getHeight() < 0)
			{
				goingLeft = false;
				countStalk--;

			}
			if(Dest.left >= CanvasWidth)
			{

				goingLeft = true;
				countStalk--;

			}
		}

	}
	private void changeAnimation() {
		if(AnimIndex > 0)
		{
			frameRate-=1f;

			if(frameRate <=0 && AnimIndex < maxAnimIndex)
			{
				AnimIndex++;
				frameRate = defaulFrameRate;	
			}

		}

		if(goingLeft)
		{
			left = ((SharkAnimMap.getWidth() -  SharkAnimMap.getHeight()) -(SharkAnimMap.getHeight() * AnimIndex));
			top = 0;
			right = left + SharkAnimMap.getHeight();
			bottom = top + SharkAnimMap.getHeight();
			Source.left = left;
			Source.top = top;
			Source.right = right;
			Source.bottom = bottom;
		}
		else
		{
			goingLeft = false;
			left = SharkAnimMap.getHeight() * AnimIndex;
			top = 0;
			right = left + SharkAnimMap.getHeight();
			bottom = top + SharkAnimMap.getHeight();
			Source.left = left;
			Source.top = top;
			Source.right = right;
			Source.bottom = bottom;	
		}
	}
	private void move() {
		if(AnimIndex == 0 && countStalk > 0)
		{
			if(goingLeft)
			{
				Dest.left -= Velocity;
				Dest.right -= Velocity;
				RipplePosition.left -= Velocity;
			}
			else
			{
				Dest.left += Velocity;
				Dest.right += Velocity;
				RipplePosition.left += Velocity;
			}
		}
		if(countStalk == 0)
		{
			Dest.left = -CanvasWidth;
			Dest.right = -CanvasWidth + SharkAnimMap.getHeight();
		}
	}
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(RippleMap[Index], Dest.left, Dest.top,null);

		if(goingLeft)
			canvas.drawBitmap(SharkAnimMapFliped,Source,Dest,null);
		else
			canvas.drawBitmap(SharkAnimMap,Source,Dest,null);

		//DrawBounding(canvas);

	}
	public void onColision()
	{
		if(AnimIndex == 0)
			AnimIndex++;
	}
	public RectF BoudingRecTF()
	{
		BoudingRecTF.left = Dest.left + (SharkAnimMap.getHeight() * 0.25f);
		BoudingRecTF.top = Dest.top + (SharkAnimMap.getHeight()* 0.45f);
		BoudingRecTF.right = (Dest.left + SharkAnimMap.getHeight()) - SharkAnimMap.getHeight() * 0.30f ;
		BoudingRecTF.bottom = Dest.top + (SharkAnimMap.getHeight()) - SharkAnimMap.getHeight() * 0.20f;
		return BoudingRecTF;
	}
	public void Restart() {
		countStalk = 0;
		Dest.left = -CanvasWidth;
		Dest.right = -CanvasWidth + SharkAnimMap.getHeight();


	}
}
