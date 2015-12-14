package net.game;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;

public class Sky {
	int CanvasHeight;
	int CanvasWidth;
	Context context;
	Bitmap SkyMap;
	Bitmap Cloud1;
	Bitmap Cloud2;
	Bitmap Cloud3;
	Bitmap Sky1To2Map;
	Bitmap Sky2To3Map;
	Bitmap Sky2Map;
	Bitmap Sky3To4Map;
	BitmapDrawable Sky2Drawable;
	Bitmap Sky3Map;
	BitmapDrawable Sky3Drawable;
	Bitmap MoonMap;
	Bitmap Planet1Map;
	Bitmap Planet2Map;
	Bitmap SaturnMap;
	Bitmap Satellite1Map;
	Bitmap Satellite2Map;
	Bitmap GalaxyMap;
	Bitmap Baloon1Map;
	Bitmap Baloon2Map;
	RectF Cloud1Position;
	RectF Cloud2Position;
	RectF Cloud3Position;
	Rect Dest;
	Rect Source;
	float Velocity = 0.1f;
	Rect Sky1To2Position;
	Rect Sky2Position;
	Rect Sky2To3Position;
	Rect Sky3Position;
	Rect Sky3To4Position;
	private Rect Sky1To2Source;
	private Rect Sky2To3Source;
	private Rect Sky3To4Source;
	public Sky(Context Mycontext, int Height, int Width)
	{
		context = Mycontext;
		CanvasHeight = Height;
		CanvasWidth = Width;
		Sky2To3Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky2to3);
		Sky1To2Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky1to2);
		Sky2Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky2);
		SkyMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sky);
		Sky3Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky3);
		Sky3To4Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky3to4);
		Source = new Rect(0, 0, SkyMap.getWidth(), SkyMap.getHeight());
		Dest = new Rect(0, -CanvasHeight, Width, CanvasHeight + SkyMap.getHeight());
		Cloud1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_clouds_01);
		Cloud1Position = new RectF(0, 0, 0, 0);
		Cloud2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_clouds_02);
		Cloud2Position = new RectF(Width/2 - Cloud2.getWidth(), 0, 0, 0);
		Cloud3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_clouds_02);
		
		MoonMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky_moon);
		Planet1Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky_planet1);
		Planet2Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky_planet2);
		SaturnMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky_saturn);
		Satellite1Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky_satellite1); 
		Satellite2Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky_satellite2);
		GalaxyMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky_galaxy);
		Baloon1Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky_baloon1);
		Baloon2Map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sky_baloon2);
		Cloud3Position = new RectF(Width-Cloud3.getWidth(), 0, 0, 0);
		Velocity = (float)Width / 8000;
		
		Sky1To2Position = new Rect(0,-CanvasHeight -Sky1To2Map.getHeight(),CanvasWidth,(-CanvasHeight -Sky1To2Map.getHeight()) + Sky1To2Map.getHeight());
		Sky2Position = new Rect(0,-CanvasHeight*2,CanvasWidth,Sky1To2Position.top); 
		Sky2To3Position = new Rect(0,Sky2Position.top  -Sky2To3Map.getHeight(),CanvasWidth,(Sky2Position.top  -Sky2To3Map.getHeight()) + Sky2To3Map.getHeight());
		Sky3Position =   new Rect(0, -CanvasHeight*7, CanvasWidth,Sky2To3Position.top);
		Sky3To4Position =   new Rect(0, Sky3Position.top -Sky3To4Map.getHeight(), CanvasWidth,(Sky3Position.top  -Sky3To4Map.getHeight()) + Sky3To4Map.getHeight());
		
		Sky2Drawable = new BitmapDrawable(context.getResources(),Sky2Map);
        Sky2Drawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
        Sky2Drawable.setBounds(Sky2Position);
		
        Sky1To2Source = new Rect(0,0,Sky1To2Map.getWidth(),Sky1To2Map.getHeight());
        
        Sky2To3Source = new Rect(0,0,Sky2To3Map.getWidth(),Sky2To3Map.getHeight());
        
        Sky3To4Source = new Rect(0,0,Sky3To4Map.getWidth(),Sky3To4Map.getHeight());
        
        Sky3Drawable = new BitmapDrawable(context.getResources(),Sky3Map);
        Sky3Drawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
        Sky3Drawable.setBounds(Sky3Position);
		
	}
	public void Draw(Canvas canvas)
	{
		canvas.drawBitmap(Sky3To4Map, Sky3To4Source,Sky3To4Position, null);
		Sky3Drawable.draw(canvas);
		canvas.drawBitmap(Sky2To3Map, Sky2To3Source,Sky2To3Position, null);
		Sky2Drawable.draw(canvas);
		canvas.drawBitmap(Sky1To2Map, Sky1To2Source, Sky1To2Position, null);
		canvas.drawBitmap(SkyMap,Source,Dest, null);
		canvas.drawBitmap(Cloud1, Cloud1Position.left,Cloud1Position.top, null);
		canvas.drawBitmap(Cloud2, Cloud2Position.left,Cloud2Position.top, null);
		canvas.drawBitmap(Cloud3, Cloud3Position.left,Cloud3Position.top, null);
		canvas.drawBitmap(MoonMap, CanvasWidth*0.1f,Sky2To3Position.top, null);
		canvas.drawBitmap(Satellite1Map, CanvasWidth*0.4f,Sky2To3Position.top, null);
		canvas.drawBitmap(Satellite2Map, CanvasWidth*0.6f,Sky2To3Position.top, null);
		canvas.drawBitmap(Planet1Map, CanvasWidth*0.6f,-CanvasHeight*3, null);
		canvas.drawBitmap(Planet2Map, CanvasWidth*0.8f,-CanvasHeight*4, null);
		canvas.drawBitmap(SaturnMap, CanvasWidth*0.4f,-CanvasHeight*5.8f, null);
		canvas.drawBitmap(GalaxyMap, 0,-CanvasHeight*6.8f, null);
		canvas.drawBitmap(Baloon1Map, CanvasWidth * 0.2f, Sky1To2Position.top + (Sky1To2Position.top/2), null);
		canvas.drawBitmap(Baloon2Map, CanvasWidth * 0.6f,Sky1To2Position.top + (Sky1To2Position.top/3), null);

	}
	public void update()
	{
		
		
		if(Cloud1Position.left + Cloud1.getWidth() <= 0)
			Cloud1Position.left = CanvasWidth;
		
		if(Cloud2Position.left + Cloud2.getWidth() <= 0)
			Cloud2Position.left = CanvasWidth;
		
		if(Cloud3Position.left + Cloud3.getWidth() <= 0)
			Cloud3Position.left = CanvasWidth;
		
		Cloud1Position.left -= Velocity;
		Cloud2Position.left -= Velocity;
		Cloud3Position.left -= Velocity;
	}
}
