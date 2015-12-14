package net.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Bird {
	
	float Velocity = 3f;
	float yVelocity = 0f;
	final float defaulFrameRate = 5f;
	float frameRate = defaulFrameRate;
	boolean isDowing = false;
	boolean isColided = false;
	Bitmap BirdMap;
	Bitmap BirdHit;
	Context context;
	private int CanvasHeight;
	Rect Source;
	RectF Dest;
	int AnimIndex =0;
	int maxAnimIndex = 0;
	boolean isGone = false;
	Surfer surfer;
	RectF BoudingRecTF;
	Paint myPaint = new Paint();

	public Bird(Context myContext, int myCanvasHeight, int myCanvasWidth, RectF pos, Bitmap _BirdMap, Bitmap _BirdHit, Surfer mysurfer)
	{
		context = myContext;
		CanvasHeight = myCanvasHeight;
		BirdMap = _BirdMap;
		Source = new Rect(AnimIndex * BirdMap.getHeight(), 0,AnimIndex*BirdMap.getHeight() + BirdMap.getHeight() , (BirdMap.getHeight()));
		maxAnimIndex = BirdMap.getWidth()/BirdMap.getHeight();
		float left = pos.left;
		float top = pos.top;
		float right = pos.left +BirdMap.getHeight();
		float bottom = pos.top + BirdMap.getHeight();
		Dest = new RectF(left, top,right ,bottom );
		BirdHit = _BirdHit;
		surfer = mysurfer;
		BoudingRecTF = new RectF(0,0,0,0);
		Velocity = (float)myCanvasWidth / 260;
	}
	
	public void update()
	{
		if (!isGone) {
			frameRate -= 1f;

			Source.left = AnimIndex * BirdMap.getHeight();
			Source.top = 0;
			Source.right = AnimIndex*BirdMap.getHeight() + BirdMap.getHeight();
			Source.bottom = BirdMap.getHeight();
			if (!isColided && frameRate <= 0){
				AnimIndex = (AnimIndex >= maxAnimIndex-1)? 0 : AnimIndex+1;
				frameRate = defaulFrameRate;
			}
			if (!isColided){
				Dest.left -= surfer.speed + Velocity;
				Dest.right -= surfer.speed + Velocity;
			}
			
		}
		if ((Dest.left + BirdMap.getHeight()) <= 0 || Dest.top > CanvasHeight )
			isGone = true;
	}
	public void draw(Canvas canvas)
	{
		if (!isGone)
			canvas.drawBitmap(BirdMap,Source,Dest,null);
	}

	public void onColision() {
		if (!isGone){
			isColided = true;
			yVelocity = 3f;
			AnimIndex = 0;
			BirdMap = BirdHit;
		}
	}
	public RectF BoudingRecTF()
	{
		BoudingRecTF.left = Dest.left + (BirdMap.getHeight() * 0.25f);
		BoudingRecTF.top = Dest.top + (BirdMap.getHeight()* 0.45f);
		BoudingRecTF.right = (Dest.left + BirdMap.getHeight()) - BirdMap.getHeight() * 0.30f ;
		BoudingRecTF.bottom = Dest.top + (BirdMap.getHeight()) - BirdMap.getHeight() * 0.20f;
		return BoudingRecTF;
	}
//	private void DrawBounding(Canvas canvas) {
//		myPaint.setColor(Color.rgb(0, 0, 0));
//		myPaint.setStrokeWidth(10);
//		canvas.drawRect(BoudingRecTF(), myPaint);
//
//	}
	
}
