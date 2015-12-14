package net.game;
import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class GodBar {

	Level level;
	Surfer surfer;
	int CanvasHeight;
	int CanvasWidth;
	Bitmap BarMAP;
	Bitmap BackMap;
	Bitmap HudMap;
	RectF HudPosition;
	int CurBarSize;
	int MaxBarSize;
	Rect bgSource;
	RectF bgDest;
	RectF barDest;
	public GodBar(Context _context, Level level, Surfer surfer, int CanvasH, int CanvasW)
	{
		this.level = level;
		this.surfer = surfer;
		this.CanvasHeight = CanvasH;
		this.CanvasWidth = CanvasW;
		BarMAP = BitmapFactory.decodeResource( _context.getResources(), R.drawable.hud_surfmeter_bar);
		HudMap = BitmapFactory.decodeResource( _context.getResources(), R.drawable.hud_surfmeter_fg);
		BackMap = BitmapFactory.decodeResource( _context.getResources(), R.drawable.hud_surfmeter_bg);
		
		HudPosition = new RectF(0,0,0,0);
		
		
		int srcLeft = 0;
		int srcTop = 0;
		int srcRight = BackMap.getWidth();
		int srcBottom = BackMap.getHeight();

		float destLeft = HudMap.getWidth() * 0.2f;
		float destTop = (BackMap.getHeight() * 0.9f);
		float destRight = HudMap.getWidth() - BackMap.getWidth();
		float destBottom = destTop + BackMap.getHeight();
		
		bgSource = new Rect(srcLeft, srcTop, srcRight, srcBottom);
		bgDest = new RectF(destLeft,destTop,destRight,destBottom);
		barDest = new RectF(destLeft,destTop,0f,destBottom);
		
		MaxBarSize = (int)bgDest.right;
	}
	public void update()
	{
		
		if(!surfer.isGodMode)
		CurBarSize = surfer.EnergyCollected;
	
		if(CurBarSize >= MaxBarSize && !surfer.isGodMode)
			surfer.isGodMode = true;
		
		if(CurBarSize >  MaxBarSize)
			CurBarSize = MaxBarSize;
		
		if(surfer.trickingGOD)
			CurBarSize = (int)surfer.GodTrickedTime;
		
		barDest.right = CurBarSize;
	}
	public void draw(Canvas canvas)
	{
		drawHud(canvas);

	}
	private void drawHud(Canvas canvas) {
		canvas.drawBitmap(BackMap, bgSource,bgDest,null);
		canvas.drawBitmap(BarMAP, bgSource,barDest, null);
		canvas.drawBitmap(HudMap, HudPosition.left,HudPosition.top, null);
	
	}
	public void Restart() {
		CurBarSize = 0;
		
	}
}
