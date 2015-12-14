package net.game;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Overlay {

	Context context;
	private int CanvasHeight;
	private int CanvasWidth;
	RectF DestUp;
	RectF DestDown;
	Bitmap[] buttonDown = new Bitmap[2];
	Bitmap[] buttonUp = new Bitmap[2];
	Surfer surfer;
	int indexDown = 0;
	int indexUp = 0;
	
	public Overlay (Context myContext, int myCanvasHeight, int myCanvasWidth, Surfer _surfer){
		context = myContext;
		surfer = _surfer;
		CanvasHeight = myCanvasHeight;
		CanvasWidth = myCanvasWidth;
		buttonDown[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_godown);
		buttonDown[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_godown_pressed);
		buttonUp[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_goup);
		buttonUp[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_goup_pressed);
		DestDown = new RectF(0, CanvasHeight - buttonUp[0].getHeight(), 0, 0);
		DestUp = new RectF(CanvasWidth - buttonDown[0].getWidth(), CanvasHeight - buttonDown[0].getHeight(), 0, 0);
	}
	
	public void update()
	{
		if (!surfer.touchingLeft)
			indexDown = 0;
		else
			indexDown = 1;
		
		if (!surfer.touchingRight)
			indexUp = 0;
		else
			indexUp = 1;
	}
	
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(buttonDown[indexDown], DestDown.left, DestDown.top,null);
		canvas.drawBitmap(buttonUp[indexUp], DestUp.left, DestUp.top, null);
	}
	

	
}
