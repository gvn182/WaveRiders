package net.game;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Tail {
	private static final long FRAMERATEANIMATION = 100;
	private long oldTime = System.currentTimeMillis();
	int TailHeight;
	int TailWidth;
	Bitmap[] TailMap = new Bitmap[3];
	int TailCode;
	Context _context;
	int Animations;
	int CurAnimation;
	int QtdTails = 0;
	RectF dst;
	Rect Position;
	public Tail(Context context)
	{
		_context = context;
		LoadResources();
		TailHeight = TailMap[0].getHeight();
		TailWidth = TailHeight;
		Animations =2;
	}
	public void update()
	{
		if(System.currentTimeMillis() - oldTime > FRAMERATEANIMATION)
		{
			if(CurAnimation < Animations)
			{
				CurAnimation++;
			}
			else
			{
				CurAnimation = 0;
			}
			oldTime =(System.currentTimeMillis());
		}

	}
	public void draw(Canvas canvas, Surfer Surfman)
	{
		if(!Surfman.tricking && !Surfman.dyeing)
		{
		dst = Surfman.BoundingRectBoard();
		canvas.drawBitmap(TailMap[CurAnimation], dst.left,dst.top,null);
		}
	}
	private void LoadResources() {
		TailMap[0] = BitmapFactory.decodeResource( _context.getResources(), R.drawable.board_ripple_00);
		TailMap[1] = BitmapFactory.decodeResource( _context.getResources(), R.drawable.board_ripple_01);
		TailMap[2] = BitmapFactory.decodeResource( _context.getResources(), R.drawable.board_ripple_02);
		
	}
}
