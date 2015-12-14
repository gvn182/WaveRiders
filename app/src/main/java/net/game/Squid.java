package net.game;

import java.util.Random;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Squid {

	private float timeWaited = 0;
	float waitTime;
	int range = 1;
	int minRange = 800;
	Paint Pencil = new Paint();
	final float defaulFrameRate = 15f;
	float frameRate = defaulFrameRate;
	float angle = 0;
	final float defaultAngleFrameHate = 2f;
	float angleFrameHate = defaultAngleFrameHate;
	final float defaultOilFXFrameRate = 8f;
	float oilFXFrameRate = defaultOilFXFrameRate;
	Bitmap SquidAnimMap;
	Context context;
	private int CanvasHeight;
	private int CanvasWidth;
	Rect Source;
	RectF Dest;
	int squidSpitAnimIndex = 0;
	int maxSquidSpitAnimIndex = 0;
	int AnimIndex =0;
	int maxAnimIndex = 0;
	int oilAnimIndex = 0;
	int maxOilAnimIndex = 0;
	WaterBG water;
	boolean goingLeft;
	boolean isRigthAngle = true;
	boolean isColided = false;
	Surfer surfman;
	Random random;
	Matrix matrix;
	RectF BoudingRecTF;
	Paint myPaint;
	private int top;
	public Squid(Context myContext, int myCanvasHeight, int myCanvasWidth,WaterBG water, Surfer surfman)
	{
		context = myContext;
		CanvasHeight = myCanvasHeight;
		CanvasWidth = myCanvasWidth;
		this.water = water;
		random = new Random();
		SquidAnimMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat_static);
		goingLeft = true;
		Dest = new RectF(-CanvasWidth, 0, -CanvasWidth + SquidAnimMap.getHeight(),SquidAnimMap.getHeight());
		this.surfman = surfman;
		matrix = new Matrix();
		BoudingRecTF = new RectF();
		myPaint = new Paint();
		Source = new Rect();
	}


	private void RandomPosition() {
		top = random.nextInt(CanvasHeight - water.FoamTop) + water.FoamBottom;
		Dest.left = CanvasWidth;
		Dest.top = top;
		Dest.right = CanvasWidth + SquidAnimMap.getHeight();
		Dest.bottom = top + SquidAnimMap.getHeight();
	}
	private void checkTime() {
		if(Dest.left < 0)
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
		if(timeWaited == 0)
		{
			waitTime = random.nextInt(range) + minRange;

		}
		checkTime();
		move();
		rotateSquid ();
	}

	

	private void move() {
		if(Dest.left + SquidAnimMap.getWidth()  >= 0)
		{
			Dest.left -= surfman.speed;
			Dest.right -= surfman.speed;
		}
	}

	private void rotateSquid (){
		angleFrameHate-=1f;
		if (angleFrameHate <= 0){
			isRigthAngle = angle >= 15? false : angle <= -20? true : isRigthAngle;
			angle += (isRigthAngle)? +2 : -2;
			angleFrameHate = defaultAngleFrameHate;
		}
	}

	public void draw(Canvas canvas)
	{
			matrix.reset();
			matrix.setTranslate(Dest.left, this.Dest.top);
			matrix.postRotate(angle, Dest.left + SquidAnimMap.getHeight()/2, Dest.top + SquidAnimMap.getHeight()/2);
			canvas.drawBitmap(SquidAnimMap, matrix,null);	
	}
	public void onColision()
	{
		Dest.left -= 5000;
		Dest.right -= 5000;	
	}
	public RectF BoudingRecTF()
	{
		BoudingRecTF.left = Dest.left + (SquidAnimMap.getHeight() * 0.25f);
		BoudingRecTF.top = Dest.top + (SquidAnimMap.getHeight()* 0.45f);
		BoudingRecTF.right = (Dest.left + SquidAnimMap.getHeight()) - SquidAnimMap.getHeight() * 0.30f ;
		BoudingRecTF.bottom = Dest.top + (SquidAnimMap.getHeight()) - SquidAnimMap.getHeight() * 0.20f;
		return BoudingRecTF;
	}
	public void Restart()
	{
		Dest.left -= 5000;
		Dest.right -= 5000;	
	}

}
