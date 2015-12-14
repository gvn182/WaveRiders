package net.game;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Loading {
	int CanvasHeight;
	int CanvasWidth;
	Context context;
	Bitmap softNameMap;
	Bitmap hardNameMap;
	Bitmap wavePaternMap;
	int SoftPositionLeft;
	RectF HardPosition;
	long oldTime = System.currentTimeMillis();
	int TIMETOWAIT = 2000;
	Level level;
	private boolean run = true;
	int bmWidth;
	int bmHeight;
	int y;
	int x;
	private int height;
	private int width;
	public Loading(Context Mycontext,int Height, int Width, Level level)
	{
		context = Mycontext;
		CanvasHeight = Height;
		CanvasWidth = Width;
		this.level = level;
		softNameMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.loading_bg_soft);
		hardNameMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.loading_bg);
		wavePaternMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.loading_wave_pattern);
		SoftPositionLeft = (int)(-softNameMap.getWidth()* 0.5f);
		HardPosition = new RectF(Width/2 - hardNameMap.getWidth()/2,Height/2 - hardNameMap.getHeight()/2,0,0);
		bmWidth = softNameMap.getWidth();
		bmHeight = softNameMap.getHeight();
	}
	public void Draw(Canvas canvas)
	{
		canvas.drawARGB(255, 68, 98, 187);
		bmWidth = softNameMap.getWidth();
		bmHeight = softNameMap.getHeight();

		for (y = 0, height = CanvasHeight ; y < height; y += bmHeight) 
			for (x = SoftPositionLeft, width = CanvasWidth; x < width; x += bmWidth) {
				canvas.drawBitmap(softNameMap, x, y, null);
			}
		canvas.drawBitmap(hardNameMap, HardPosition.left,HardPosition.top, null);
		
		final int bmWidth2 = wavePaternMap.getWidth();
		for (x = 0, width = CanvasWidth; x < width; x += bmWidth2) {
			canvas.drawBitmap(wavePaternMap, x, CanvasHeight-wavePaternMap.getHeight(), null);
		}

	}
	public void update()
	{
		if(run)
		{
			if(System.currentTimeMillis() - oldTime > TIMETOWAIT)
			{
				oldTime=(System.currentTimeMillis());
				level.LoadEveryFuckThing();
				run = false;
			}
		}
	}
}
