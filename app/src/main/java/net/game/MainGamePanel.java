package net.game;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.ads.AdView;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements
OnGestureListener,SurfaceHolder.Callback {

	public MainThread thread;
	Level level;
	Context _context;
	private GestureDetector gDetector;
	public String Fps = "";
AdView adView;
	public MainGamePanel(Context context, AdView Ads) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);


		// create droid and load bitmap
		_context =  context;
		gDetector = new GestureDetector(context,this);
        this.adView = Ads;

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	public void newThread()
	{
		thread = new MainThread(getHolder(), this);
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	
	
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		if(level == null)
		{
			level = new Level(_context,this.getHeight(),this.getWidth(),adView);
			
		}
		newThread();
	
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

		if(level != null)
		{
			if(level.speaker != null)
				
			level.speaker.Release();
			level.isGamePaused = true;
			level = null;	
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		level.onTouch(event);
		gDetector.onTouchEvent(event);
		return true;
	}

	public void render(Canvas canvas) {

		canvas.drawColor(Color.BLACK);
		level.Draw(canvas);
		//canvas.drawText(Fps, 150, 50, pt);
	}
	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {
		if(level != null)
		level.update();
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		velocityX = Math.abs(velocityX);
		velocityY = Math.abs(velocityY);

		if(velocityX > velocityY)
		{
			if(e1.getX() < e2.getX())
				level.onFlingHorizontalLeftToRight(e1, e2, velocityX, velocityX);
			else
				level.onFlingHorizontalRightToLeft(e1, e2, velocityX, velocityX);

		}
		else
		{
			if(e1.getY() > e2.getY())
				level.onFlingVerticalBottomToTop(e1, e2, velocityX, velocityX);

			else
				level.onFlingVerticalTopToBottom(e1, e2, velocityX, velocityX);
		}

		return true;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		level.onLongPress(arg0);

	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		level.onSingleTapUp(arg0);
		return true;
	}





}
