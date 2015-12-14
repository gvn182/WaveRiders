package net.game;

import java.util.Random;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

public class Shell {

	private float timeWaited = 0;
	float waitTime;
	int range = 500;
	int minRange = 1000;
	Paint Pencil = new Paint();
	final float defaulFrameRate = 15f;
	float frameRate = defaulFrameRate;
	Bitmap[] Texture = new Bitmap[3];
	Context context;
	private int CanvasHeight;
	private int CanvasWidth;
	int AnimIndex =0;
	int maxAnimIndex = 2;
	WaterBG water;
	boolean isColided = false;
	RectF Position;
	Surfer surfman;
	Random random;
	Matrix matrix;
	RectF BoudingRecTF;
	int top;
	private static final long FRAMERATEANIMATION = 100;
	private long oldTime = System.currentTimeMillis();
	public Shell(Context myContext, int myCanvasHeight, int myCanvasWidth, Surfer surfman)
	{
		context = myContext;
		CanvasHeight = myCanvasHeight;
		CanvasWidth = myCanvasWidth;
		random = new Random();
		Texture[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.tokenshell0);
		Texture[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.tokenshell1);
		Texture[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.tokenshell2);
		Position = new RectF(-CanvasWidth, 0, -CanvasWidth + Texture[0].getHeight(),Texture[0].getHeight());
		this.surfman = surfman;
		BoudingRecTF = new RectF();
	}


	private void RandomPosition() {
		top = random.nextInt(CanvasHeight - Texture[0].getHeight() );
		Position.left = CanvasWidth;
		Position.top = top;
		Position.right = CanvasWidth + Texture[0].getHeight();
		Position.bottom = top + Texture[0].getHeight();

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
	public void update()
	{
		if(timeWaited == 0)
		{
			waitTime = random.nextInt(range) + minRange;
		}
		checkTime();
		move();
		animate ();
	}

	

	private void animate() {
		if(System.currentTimeMillis() - oldTime > FRAMERATEANIMATION)
		{
			if(AnimIndex <maxAnimIndex)
			{
				AnimIndex++;
			}
			else
			{
				AnimIndex = 0;
			}
			oldTime =(System.currentTimeMillis());
		}

		
	}
	private void move() {
		if(Position.left + Texture[0].getWidth()  >= 0)
		{
			Position.left -= surfman.speed;
			Position.right -= surfman.speed;
		}
	}

	
	public void draw(Canvas canvas)
	{
			canvas.drawBitmap(Texture[AnimIndex], Position.left,Position.top,null);
	}
	public void onColision()
	{
		Position.left -= 5000;
		Position.right -= 5000;	
	
	}
	public RectF BoudingRecTF()
	{
		BoudingRecTF.left = Position.left + (Texture[0].getHeight() * 0.25f);
		BoudingRecTF.top = Position.top + (Texture[0].getHeight()* 0.45f);
		BoudingRecTF.right = (Position.left + Texture[0].getHeight()) - Texture[0].getHeight() * 0.30f ;
		BoudingRecTF.bottom = Position.top + (Texture[0].getHeight()) - Texture[0].getHeight() * 0.20f;
		return BoudingRecTF;
	}

}
