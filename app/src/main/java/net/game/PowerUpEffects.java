package net.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;


public class PowerUpEffects {

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
	Bitmap[] PowerUpEffectsMap;
	Rect Position;
	Rect Dest;
	Surfer surfista;
	int CurAnimation = 0;
	public PowerUpEffects(Context Mycontext, Surfer mySurfer, int Height, int Width)
	{
		context = Mycontext;
		CanvasHeight = Height;
		CanvasWidth = Width;
		preparePowerUpEffectsMap();
		if (PowerUpEffectsMap != null && PowerUpEffectsMap.length >0){
			AuraHeight = PowerUpEffectsMap[0].getHeight();
			AuraWidth = PowerUpEffectsMap[0].getHeight();
		}
		Position = new Rect();
		surfista = mySurfer;
	}

	private void preparePowerUpEffectsMap(){
		int [] textures = PowerUps.getPowerUpEffectsTextures();
		if (textures!= null && textures.length>0){
			PowerUpEffectsMap = new Bitmap[textures.length];
			for (int i=0;i<textures.length;i++){
				PowerUpEffectsMap[i] = BitmapFactory.decodeResource(context.getResources(), textures[i]);
			}
		}		
	}

	public void update()
	{
		if(System.currentTimeMillis() - oldTime > FRAMERATEANIMATION)
		{

			CurAnimation++;
			oldTime = (System.currentTimeMillis());
		}
		if(PowerUpEffectsMap != null && PowerUpEffectsMap.length >0 &&  CurAnimation >= PowerUpEffectsMap.length)
			CurAnimation = 0;
		
	}
	public void draw(Canvas canvas)
	{
		if (PowerUpEffectsMap != null && PowerUpEffectsMap.length >0){
			left = surfista.Position.left - Position.left; //(TailHeight * i);
			top = surfista.Position.top;
			right = surfista.Position.left -(Position.left) +AuraHeight;
			bottom = surfista.Position.top + AuraHeight;
			RectF dst = new RectF(left, top,right ,bottom );
			canvas.drawBitmap(PowerUpEffectsMap[CurAnimation], dst.left, dst.top,null);
		}

	}
}
