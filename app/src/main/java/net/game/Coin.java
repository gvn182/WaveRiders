package net.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Coin {

	private static final int FRAMERATEANIMATION = 120;
	public Bitmap[] texture = new Bitmap[3];
	Context _context;
	RectF Position;
	public boolean gone;
	public boolean collected;
	private Surfer surfer;
	private int CurAnimation = 0;
	private long oldTime = System.currentTimeMillis();
	RectF BoudingRecTF;
	public Coin(Context cont, int Height, int Width,RectF pos,Bitmap[] texture,Surfer mySurfer)
	{
		_context = cont;
		this.texture = texture;
		Position = pos;
		surfer = mySurfer;
		BoudingRecTF = new RectF(0,0,0,0);
	}
	public void update() {
		Move();
		ChangeAnimation();
		//CheckDetroy();
	}
	private void ChangeAnimation() {
		if(System.currentTimeMillis() - oldTime > FRAMERATEANIMATION)
		{
			if(CurAnimation == 2)
				CurAnimation = 0;
			else CurAnimation++;
			
			oldTime = (System.currentTimeMillis());
		}
	}
	private void Move() {
		if(!collected)
		{
			Position.left -= surfer.speed;
		}
		else
		{
			gone = true;
		}
		
		if(Position.left + texture[0].getWidth() < 0)
			gone = true;
	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(texture[CurAnimation],Position.left,Position.top,null);
		//DrawBounding(canvas);
	}
	public void OnCollect() {
		collected = true;
	}
	public RectF BoudingRecTF()
	{
		BoudingRecTF.left = (int)(Position.left);
		BoudingRecTF.top = (int)Position.top;
		BoudingRecTF.right = (int)(Position.left + texture[0].getWidth());
		BoudingRecTF.bottom = (int)(Position.top + texture[0].getHeight());
		return BoudingRecTF;

	}
	
}
