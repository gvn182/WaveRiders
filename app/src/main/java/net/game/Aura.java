package net.game;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;


public class Aura {

	private static final long FRAMERATEANIMATION = 300;
	private long oldTime = System.currentTimeMillis();
	int AuraHeight;
	int AuraWidth;
	int CanvasHeight;
	int CanvasWidth;
	float left;
	float top;
	float bottom;
	float right;
	Context context;
	Bitmap AuraMap;
	Rect Position;
	Rect Dest;
	Surfer surfista;
	int CurAnimation = 0;
	Level level;
	Rect src;
	public Aura(Context Mycontext,Surfer mySurfer,Level myLevel, int Height, int Width)
	{
		context = Mycontext;
		level = myLevel;
		CanvasHeight = Height;
		CanvasWidth = Width;
		AuraMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fx_aura_small);
		AuraHeight = AuraMap.getHeight();
		AuraWidth = AuraMap.getHeight();
		Position = new Rect();
		surfista = mySurfer;
		src = new Rect(AuraWidth * CurAnimation,0,AuraWidth * CurAnimation + AuraWidth,AuraHeight);
	}
	public void update()
	{
		if(surfista.isGodMode)
		{
			if(CurAnimation == 4)
				CurAnimation = 0;
			
			if(System.currentTimeMillis() - oldTime > FRAMERATEANIMATION)
			{
				
				CurAnimation++;
				oldTime = (System.currentTimeMillis());
			}
		}
	}
	public void draw(Canvas canvas)
	{
		if(surfista.isGodMode)
		{
			src.bottom = AuraHeight;
			src.top = 0;
			src.left = AuraWidth * CurAnimation;
			src.right = AuraWidth * CurAnimation + AuraWidth;
			left = surfista.Position.left - Position.left; //(TailHeight * i);
			top = surfista.Position.top;
			right = surfista.Position.left -(Position.left) +AuraHeight;
			bottom = surfista.Position.top + AuraHeight;
			RectF dst = new RectF(left, top,right ,bottom );
			canvas.drawBitmap(AuraMap, src, dst,null);
		}
	}
	public void drawforturning(Canvas canvas, Rect TurnPosition)
	{
			src.bottom = AuraHeight;
			src.top = 0;
			src.left = AuraWidth * CurAnimation;
			src.right = AuraWidth * CurAnimation + AuraWidth;
			left = TurnPosition.left - Position.left; //(TailHeight * i);
			right = TurnPosition.left -(Position.left) + AuraHeight;
			top = TurnPosition.top;
			bottom = TurnPosition.top + AuraHeight;
			RectF dst = new RectF(left, top,right ,bottom );
			canvas.drawBitmap(AuraMap, src, dst,null);
		
	}
}
