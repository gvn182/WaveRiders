package net.game;

import java.util.Random;

import net.surf.R;
import Util.StorageInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

public class Record {

	final float defaulFrameRate = 15f;
	float frameRate = defaulFrameRate;
	Bitmap Texture;
	Context context;
	private int CanvasHeight;
	private int CanvasWidth;
	int AnimIndex =0;
	int maxAnimIndex = 2;
	WaterBG water;
	boolean isColided = false;
	RectF Position;
	Surfer surfman;
	Random random;
	Matrix matrix;
	RectF BoudingRecTF;
	int top;
	public boolean newRecord = false;
	boolean Showing = false;
	int LastRecord;
	public Record(Context myContext, int myCanvasHeight, int myCanvasWidth, int LastRecord)
	{
		context = myContext;
		CanvasHeight = myCanvasHeight;
		CanvasWidth = myCanvasWidth;
		random = new Random();
		Texture = BitmapFactory.decodeResource(context.getResources(), R.drawable.fx_newrecord);
		Position = new RectF(-CanvasWidth, CanvasHeight - CanvasHeight/2, 0,0);
		this.LastRecord  = LastRecord;
	}



	public void update(float SurferDistance, float surferSpeed)
	{
		if(SurferDistance >=  LastRecord && !Showing)
		{
			Showing = true;
			newRecord = true;
			Position.left = CanvasWidth + Texture.getWidth();
		}

		if(Showing && Position.left + Texture.getWidth()  >= 0)
		{
			Position.left -= surferSpeed;
		}
	}
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(Texture, Position.left,Position.top,null);
	}
	public void Restart()
	{
		LastRecord = StorageInfo.storage.getMyRecord();
		newRecord = false;
		Showing = false;
		Position = new RectF(-CanvasWidth, CanvasHeight - CanvasHeight/3, 0,0);
	}



	public void SetNewRecord(int Distance) {
		if(newRecord)
			StorageInfo.storage.setMyRecord(Distance);
		
	}
}
