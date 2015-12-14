package net.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.DisplayMetrics;




public class Manobra {
	int TIMETODISPLAY = 2000;
	Context context;
	int CanvasHeight;
	int CanvasWidth;
	Surfer surfer;
	Paint pencil;
	long oldTime = System.currentTimeMillis();
	Typeface CustomFont;
	RectF Position;
	private boolean displayMessage = false;
	String Text;
	float Velocity = 1f;
	DisplayMetrics metrics;
	public Manobra(Context context, int Height, int Width,Surfer surfer)
	{
		this.context = context;
		this.CanvasHeight = Height;
		this.CanvasWidth = Width;
		this.surfer = surfer;
		Position = new RectF();
		metrics = context.getResources().getDisplayMetrics();
		LoadResources();
		
	}
	private void LoadResources() {
		CustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/comix_loud.ttf");
		pencil = new Paint();
		pencil.setColor(Color.RED);
		pencil.setTypeface(CustomFont);
		switch ((int)metrics.densityDpi)
		{
		case DisplayMetrics.DENSITY_LOW:
			pencil.setTextSize(6);
			
			break; //LDPI
		case DisplayMetrics.DENSITY_MEDIUM:
			pencil.setTextSize(11);
			break;	
		case DisplayMetrics.DENSITY_HIGH:
			pencil.setTextSize(17);
			
			break;
		case DisplayMetrics.DENSITY_XHIGH:
			pencil.setTextSize(21);
			
			break;
		case DisplayMetrics.DENSITY_XXHIGH:
			pencil.setTextSize(27);
			break;
		
		
		}
		
		
	}
	public void update()
	{
		if(displayMessage)
		{
			if(System.currentTimeMillis() - oldTime  > TIMETODISPLAY)
			{
				Text = "";
				displayMessage = false;
				Position.top = -100;
			}
			else
			{
				Position.top -= Velocity;
			}
		}
	}
	public void draw(Canvas canvas)
	{
		if(displayMessage)
		{
			canvas.drawText(Text, Position.left, Position.top, pencil);
		}
	}
	public void DisplayMessageHead(String message)
	{
		Position.bottom = 0;
		Position.right = 0;
		Position.top = surfer.Position.top;
		Position.left = surfer.Position.left + surfer.DrawableWidth/2;
		displayMessage = true;
		Text = message;
		oldTime =(System.currentTimeMillis());
	}
	public void Restart() {
		displayMessage = false;
		
	}
	
}
