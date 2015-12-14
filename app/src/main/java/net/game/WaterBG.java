package net.game;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;



public class WaterBG {
	private static final long FRAMEBG = 200;
	private static final float veloConst = 1.7f;
	int CanvasHeight;
	int CanvasWidth;
	Context context;
	Bitmap[] WaterMap = new Bitmap[5];
	Bitmap[] FoamMapBitmap = new Bitmap[3];
	Rect Source;
	public int FoamBottom;
	public int FoamTop;
	Ocean ocean;
	int CurAnimation = 0;
	int FxCurAnimation = 0;
	float defaulFrameRate = 17f;
	float frameRate = defaulFrameRate;
	Level level;
	private long oldTime = System.currentTimeMillis();
	RectF FxPosition;
	int bmWidthWater;
	int bmHeightWater;
	int bmWidth;
	private RectF FxFoamPosition;
	public WaterBG(Context Mycontext,Ocean ocean, int Height, int Width, Level level)
	{
		context = Mycontext;
		CanvasHeight = Height;
		CanvasWidth = Width;
		WaterMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_water_00);
		WaterMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_water_01);
		WaterMap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_water_02);
		WaterMap[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_water_03);
		FoamMapBitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_foam_00);
		FoamMapBitmap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_foam_01);
		FoamMapBitmap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_foam_02);
		
		this.ocean = ocean;
		this.level = level;
		FoamBottom = (ocean.Dest.bottom + (FoamMapBitmap[0].getHeight()/2));
		FoamTop = ocean.Dest.bottom;
		FxPosition = new RectF(0f,0f,0f,0f);
		FxFoamPosition = new  RectF(0f,0f,0f,0f);
		bmWidthWater = WaterMap[FxCurAnimation].getWidth();
		bmHeightWater = WaterMap[FxCurAnimation].getHeight();
		bmWidth = FoamMapBitmap[CurAnimation].getWidth();
	}
	public void update()
	{
		
		frameRate-=1f;
		if(frameRate < 0)
		{
			frameRate = defaulFrameRate;
			
			if(CurAnimation < FoamMapBitmap.length-1)
				CurAnimation++;
			else
				CurAnimation = 0;
		}
		updateFXAnimation();
	}
	public void updateFXAnimation()
	{
		
		if(System.currentTimeMillis() - oldTime > FRAMEBG)
		{
			
			if(FxCurAnimation < 3)
			FxCurAnimation++;
			else FxCurAnimation = 0;
			
			oldTime =(System.currentTimeMillis());
		}
		if(FxPosition.left <= -bmWidthWater)
			FxPosition.left = 0;
		
		FxPosition.left -= veloConst*level.SurfGuy.speed;
		FxFoamPosition.left = FxPosition.left;
		
	}
	public void Draw(Canvas canvas)
	{
		DrawBG(canvas);
		DrawFoam(canvas);
	}
	private void DrawBG(Canvas canvas) {
		
		for (int y = ocean.Dest.bottom, height = CanvasHeight ; y < height; y += bmHeightWater) {
			for (float x = FxPosition.left, width = CanvasWidth; x < width; x += bmWidthWater) {
				canvas.drawBitmap(WaterMap[FxCurAnimation], x, y, null);
			}
		}
	}
	private void DrawFoam(Canvas canvas) {
		for (float x = FxFoamPosition.left, width = CanvasWidth; x < width; x += bmWidth) {
			canvas.drawBitmap(FoamMapBitmap[CurAnimation], x, ocean.Dest.bottom , null);
		}
	}
}