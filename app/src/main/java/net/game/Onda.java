package net.game;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Onda {
	float defaulFrameRate = 10f;
	float frameRate = defaulFrameRate;
	int CanvasHeight;
	int CanvasWidth;
	Context context;
	Bitmap[] ConerUPMap = new Bitmap[3];
	RectF ConerUPPosition;
	Bitmap[] SideMap = new Bitmap[3];
	RectF SidePosition;
	Bitmap[] CornerBotMap = new Bitmap[3];
	RectF CornerBotPosition;
	Bitmap[] TopRepeatMap = new Bitmap[3];
	RectF TopRepeatPosition;
	Bitmap[] MiddleRepeatMap = new Bitmap[3];
	RectF MiddleRepeatPosition;
	Bitmap[] BottomRepeatMap = new Bitmap[3];
	RectF BottomRepeatPosition;
	Rect Source;
	Rect Dest;
	private float OndaSpeed = 4.5f;
	Surfer surfer;
	int AnimIndex = 0;
	private int bmWidth;
	public Onda(Context Mycontext,Surfer mySurfer,Ocean waterBG,int Height, int Width)
	{
		context = Mycontext;
		CanvasHeight = Height;
		CanvasWidth = Width;
		surfer = mySurfer;
		ConerUPMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_corner_up_00);
		ConerUPMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_corner_up_01);
		ConerUPMap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_corner_up_02);
		SideMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_side_00);
		SideMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_side_01);
		SideMap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_side_02);
		CornerBotMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_corner_bot_00);
		CornerBotMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_corner_bot_01);
		CornerBotMap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_corner_bot_02);
		TopRepeatMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_top_00);
		TopRepeatMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_top_01);
		TopRepeatMap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_top_02);
		MiddleRepeatMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_00);
		MiddleRepeatMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_01);
		MiddleRepeatMap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_02);
		BottomRepeatMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_bottom_00);
		BottomRepeatMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_bottom_01);
		BottomRepeatMap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wavetile_bottom_02);
		
		ConerUPPosition = new RectF(-CanvasWidth,waterBG.Dest.bottom - waterBG.Dest.bottom * 0.10f ,0,(waterBG.Dest.bottom - waterBG.Dest.bottom * 0.10f) + ConerUPMap[AnimIndex].getHeight());
		SidePosition = new RectF(ConerUPPosition.left,ConerUPPosition.bottom,0,ConerUPPosition.bottom + SideMap[AnimIndex].getHeight());
		CornerBotPosition = new RectF(SidePosition.left,SidePosition.bottom,0,0);
		TopRepeatPosition = new RectF(CornerBotPosition.left, 0, 0, 0);
		MiddleRepeatPosition = new RectF(TopRepeatPosition.left, ConerUPPosition.bottom, 0, ConerUPPosition.bottom + MiddleRepeatMap[AnimIndex].getHeight());
		BottomRepeatPosition = new RectF(MiddleRepeatPosition.left, MiddleRepeatPosition.bottom, 0, 0);
		OndaSpeed = (float)Width / 175;
	}
	public void update()
	{
		frameRate-=1f;
		if(frameRate < 0)
		{
			frameRate = defaulFrameRate;
			
			if(AnimIndex < ConerUPMap.length-1)
				AnimIndex++;
			else
				AnimIndex = 0;
		}
		
		if(surfer.hasStarted && ConerUPPosition.left + ConerUPMap[0].getWidth() <= CanvasWidth - surfer.DrawableWidth )
		{
		ConerUPPosition.left +=OndaSpeed - surfer.speed;
		SidePosition.left += OndaSpeed - surfer.speed;
		CornerBotPosition.left += OndaSpeed - surfer.speed;
		TopRepeatPosition.left += OndaSpeed - surfer.speed;
		MiddleRepeatPosition.left += OndaSpeed - surfer.speed;
		BottomRepeatPosition.left += OndaSpeed - surfer.speed;
		}
		
		
	}
	public void draw(Canvas canvas)
	{
		if(ConerUPPosition.left + ConerUPMap[0].getWidth() >= 0) {


            RepeatTop(canvas);
            RepeatMiddle(canvas);
            RepeatBottom(canvas);

            canvas.drawBitmap(ConerUPMap[AnimIndex], ConerUPPosition.left, ConerUPPosition.top, null);
            canvas.drawBitmap(SideMap[AnimIndex], SidePosition.left, SidePosition.top, null);
            canvas.drawBitmap(CornerBotMap[AnimIndex], CornerBotPosition.left, CornerBotPosition.top, null);
        }
	}
	private void RepeatBottom(Canvas canvas) {
		 bmWidth = BottomRepeatMap[AnimIndex].getWidth();
		for (float x = -CanvasWidth, width = 0; x < width; x += bmWidth) {
			canvas.drawBitmap(BottomRepeatMap[AnimIndex], BottomRepeatPosition.left + x, BottomRepeatPosition.top, null);
		}
		
	}
	private void RepeatTop(Canvas canvas) {
		bmWidth = TopRepeatMap[AnimIndex].getWidth();
		for (float x = -CanvasWidth, width = 0; x < width; x += bmWidth) {
			canvas.drawBitmap(TopRepeatMap[AnimIndex], TopRepeatPosition.left + x, ConerUPPosition.top, null);
		}
	}
	private void RepeatMiddle(Canvas canvas) {
		bmWidth = MiddleRepeatMap[AnimIndex].getWidth();
		for (float x = -CanvasWidth, width = 0; x < width; x += bmWidth) {
			canvas.drawBitmap(MiddleRepeatMap[AnimIndex], MiddleRepeatPosition.left + x, SidePosition.top, null);
		}
	}
	public void Restart() {
		
		ConerUPPosition.left = -CanvasWidth; 
		ConerUPPosition.left =-CanvasWidth; 
		SidePosition.left = -CanvasWidth; 
		CornerBotPosition.left = -CanvasWidth; 
		TopRepeatPosition.left = -CanvasWidth; 
		MiddleRepeatPosition.left = -CanvasWidth; 
		BottomRepeatPosition.left = -CanvasWidth; 
		
	}
}

