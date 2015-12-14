package net.game;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;

public class Ocean {
	int CanvasHeight;
	int CanvasWidth;
	Context context;
	Bitmap Island1Map;
	Bitmap Island2Map;
	Bitmap People1Map;
	Bitmap SunMAP;
	Bitmap BoatMap;
	RectF Island1Position;
	RectF Island2Position;
	RectF People1Position;
	RectF People2Position;
	RectF SunPosition;
	RectF BoatPosition;
	Rect Source;
	public Rect Dest;
	BitmapDrawable mDrawable;
	Sky sky;
	Level level;
	float Velocity = 0.001f;
	float SurferSpeedRatio = 0.10f;
	Bitmap[] WaterMap =  new Bitmap[3];
	int CurAnimation = 0;
	final float defaulFrameRate = 30f;
	float frameRate = defaulFrameRate;
	private int bmWidth;
	float FxPosition;
	
	public Ocean(Context Mycontext,Sky sky,Level level, int Height, int Width)
	{
		context = Mycontext;
		CanvasHeight = Height;
		CanvasWidth = Width;
		WaterMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_ocean_00);
		WaterMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_ocean_01);
		WaterMap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_ocean_02);
		this.sky = sky;
		Dest = new Rect(0, sky.SkyMap.getHeight(), Width, sky.SkyMap.getHeight() + WaterMap[0].getHeight());
		Island1Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_island_01);
		Island2Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_island_02);
		People1Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.ocean_people_00);
		BoatMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ocean_sailboat_00);
		Island1Position = new RectF(0, (sky.SkyMap.getHeight() - Island1Map.getHeight()) + Island1Map.getHeight() * 0.2f , 0, 0);
		Island2Position = new RectF(CanvasWidth/2 + Island2Map.getWidth() , (sky.SkyMap.getHeight() - Island2Map.getHeight()) + Island2Map.getHeight() * 0.2f, 0, 0);
		People1Position = new RectF(Island1Map.getWidth(), Dest.top, 0, 0);
		People2Position = new RectF(Island1Map.getWidth() +People1Map.getWidth() , Dest.top, 0, 0);
		SunMAP = BitmapFactory.decodeResource(context.getResources(), R.drawable.sun);
		SunPosition = new RectF(CanvasWidth/2  - SunMAP.getWidth()/2, 0, 0, 0);
		BoatPosition = new RectF(People2Position.left, People2Position.top, 0, 0);
		FxPosition = 0;
		bmWidth = WaterMap[CurAnimation].getWidth();
		this.level = level;
	}
	public void Draw(Canvas canvas)
	{
		canvas.drawBitmap(SunMAP, SunPosition.left, SunPosition.top,null);
		
		for (float x = FxPosition, width = Dest.width(); x < width; x += bmWidth) {
			canvas.drawBitmap(WaterMap[CurAnimation], x, sky.SkyMap.getHeight(), null);
		}

		
		canvas.drawBitmap(Island1Map, Island1Position.left,Island1Position.top, null);
		canvas.drawBitmap(Island2Map, Island2Position.left,Island2Position.top, null);
		canvas.drawBitmap(People1Map, People1Position.left,People1Position.top, null);
		canvas.drawBitmap(People1Map, People2Position.left,People2Position.top, null);
		canvas.drawBitmap(BoatMap, BoatPosition.left,BoatPosition.top, null);
		
		
	}
	public void update()
	{
		frameRate -= 1f;
		if(frameRate <= 0)
		{
		if(CurAnimation < WaterMap.length-1)
		CurAnimation++;
		else
			CurAnimation = 0;
		frameRate = defaulFrameRate;
		}
		
		if(Island1Position.left + Island1Map.getWidth() <= 0)
			Island1Position.left = CanvasWidth;

		if(Island2Position.left + Island2Map.getWidth() <= 0)
			Island2Position.left = CanvasWidth;
		
		if(People1Position.left + People1Map.getWidth() <= 0)
			People1Position.left = CanvasWidth;

		if(People2Position.left + People1Map.getWidth() <= 0)
			People2Position.left = CanvasWidth;
		
		if(BoatPosition.left + BoatMap.getWidth() <= 0)
			BoatPosition.left = CanvasWidth;

		Island1Position.left -= Velocity + level.SurfGuy.speed * SurferSpeedRatio ;
		Island2Position.left -= Velocity + level.SurfGuy.speed * SurferSpeedRatio ;
		
		People1Position.left -= Velocity + level.SurfGuy.speed * SurferSpeedRatio ;
		People2Position.left -= Velocity + level.SurfGuy.speed * SurferSpeedRatio ;
		
		BoatPosition.left -= Velocity + level.SurfGuy.speed * SurferSpeedRatio ;
		
		if(FxPosition <= -CanvasWidth/2)
			FxPosition = -Velocity + level.SurfGuy.speed * SurferSpeedRatio ;
		else
		FxPosition -= Velocity + level.SurfGuy.speed * SurferSpeedRatio;
	}
}
