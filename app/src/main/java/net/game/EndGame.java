package net.game;
import net.surf.R;
import Util.StorageInfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.ads.AdView;


public class EndGame {

	private Bitmap YouSurfedMap;
	private Bitmap CollectedMap;
	private Bitmap[] SurfAgaindMap = new Bitmap[2];
	private Bitmap[] SurfShopMap = new Bitmap[2];
	Bitmap darkScreen;
	Typeface CustomFont;
	Paint pencil;
	Paint pencilYourMoney;
	private RectF YouSurfedPosition;
	private RectF CollectedPosition;
	private RectF SurfShopPosition;
	private RectF SurfAgainPosition;
	private RectF DistanceTextPosition;
	private RectF CollectedTextPosition;
	private RectF PlayerMoneyTextPosition;
	RectF ClickedPosition;
	private String coinsCollected;
	private String distance;
	BonusScreen bonus;

	private int ShopIndex = 0;
	private int AgainIndex = 0;
	Context context;
	int CanvasHeight;
	int CanvasWidth;
	public boolean isButtonShopPressed;
	public boolean isButtonReplayPressed;
	int bmWidth;
	int bmHeight;
	int y;
	int x;
	DisplayMetrics metrics;
	private String myGold;
	Record record;
	Speaker speaker;
    AdView ad;
    Activity act;
	public EndGame (Context context, int Height, int Width,Record record, Speaker speaker, AdView ads)
	{
		this.context = context;
        ad = ads;
        act = (Activity)context;
		this.CanvasHeight = Height;
		this.CanvasWidth = Width;
		this.record = record;
		this.speaker = speaker;
		metrics = context.getResources().getDisplayMetrics();
		LoadContent();
		LoadPositions();
		bonus = new BonusScreen(context, Height, Width,pencil,speaker);
		bmWidth = darkScreen.getWidth();
		bmHeight = darkScreen.getHeight();
	}
	private void LoadPositions() {
		YouSurfedPosition = new RectF(YouSurfedMap.getWidth() * 0.2f, CanvasHeight/2 - YouSurfedMap.getWidth()/2 , YouSurfedMap.getWidth() * 0.2f + YouSurfedMap.getWidth() , (CanvasHeight/2 - YouSurfedMap.getWidth()/2) + YouSurfedMap.getHeight());
		CollectedPosition = new RectF(CanvasWidth - (CollectedMap.getWidth() + CollectedMap.getWidth()/2),CollectedMap.getHeight(),0,0);
		SurfShopPosition = new RectF(CanvasWidth- (SurfShopMap[0].getWidth() + (SurfShopMap[0].getWidth() * 0.2f)),CollectedPosition.top  * 2.2f, (CanvasWidth- (SurfShopMap[0].getWidth() + (SurfShopMap[0].getWidth() * 0.2f))) + SurfShopMap[0].getWidth(),(CollectedPosition.top  * 2.2f) + SurfShopMap[0].getHeight());
		SurfAgainPosition = new RectF(CanvasWidth - (CollectedMap.getWidth() + CollectedMap.getWidth()/2),SurfShopPosition.bottom + SurfAgaindMap[0].getHeight() * 0.2f , (CanvasWidth - (CollectedMap.getWidth() + CollectedMap.getWidth()/2) + CollectedMap.getWidth()),(CanvasHeight - (SurfAgaindMap[0].getHeight()*1.7f) + SurfAgaindMap[0].getHeight()));
		DistanceTextPosition = new RectF(YouSurfedPosition.left * 2f,(YouSurfedPosition.top + YouSurfedMap.getHeight()) - (YouSurfedMap.getHeight()/10),0,0);
		CollectedTextPosition = new RectF(CollectedPosition.left * 1.1f,CollectedPosition.top * 1.7f,0,0);
		PlayerMoneyTextPosition = new RectF(SurfShopPosition.left + SurfShopMap[0].getWidth()/2,SurfShopPosition.top * 1.73f,0,0);
		ClickedPosition = new RectF();

		CustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/comix_loud.ttf");
		pencilYourMoney = new Paint();
		pencilYourMoney.setColor(Color.WHITE);
		pencilYourMoney.setTypeface(CustomFont);
		pencil = new Paint();
		pencil.setColor(Color.WHITE);
		pencil.setTypeface(CustomFont);
		switch ((int)metrics.densityDpi)
		{
		case DisplayMetrics.DENSITY_LOW:
			pencilYourMoney.setTextSize(4);
			pencil.setTextSize(8);
			break; //LDPI
		case DisplayMetrics.DENSITY_MEDIUM:
			pencilYourMoney.setTextSize(9);
			pencil.setTextSize(13);
			
			break;	
		case DisplayMetrics.DENSITY_HIGH:
			pencilYourMoney.setTextSize(12);
			pencil.setTextSize(18);

			break;
		case DisplayMetrics.DENSITY_XHIGH:
			pencilYourMoney.setTextSize(17);
			pencil.setTextSize(22);

			break;
			
		case DisplayMetrics.DENSITY_XXHIGH:
			pencilYourMoney.setTextSize(22);
			pencil.setTextSize(27);

			break;


		}
	}
	private void LoadContent() {
		darkScreen = BitmapFactory.decodeResource(context.getResources(), R.drawable.black_square);
		YouSurfedMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.endgame_distance);
		CollectedMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.endgame_collected);
		SurfAgaindMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.endgame_surfagain);
		SurfAgaindMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.endgame_surfagain_pressed);
		SurfShopMap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.endgame_surfshop);
		SurfShopMap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.endgame_surfshop_pressed);
	}
	public void update()
	{
		bonus.update();
	}
	public void draw(Canvas canvas)
	{
		if (bonus.shellQnt > 0){
			bonus.draw(canvas);
		}else {
			drawScreenDark(canvas);
			canvas.drawBitmap(YouSurfedMap, YouSurfedPosition.left,YouSurfedPosition.top,null);
			canvas.drawBitmap(CollectedMap, CollectedPosition.left,CollectedPosition.top,null);
			canvas.drawBitmap(SurfShopMap[ShopIndex], SurfShopPosition.left,SurfShopPosition.top,null);
			canvas.drawBitmap(SurfAgaindMap[AgainIndex], SurfAgainPosition.left,SurfAgainPosition.top,null);
			canvas.drawText(distance, DistanceTextPosition.left,DistanceTextPosition.top,pencil);
			canvas.drawText(coinsCollected, CollectedTextPosition.left,CollectedTextPosition.top,pencil);
			canvas.drawText(myGold, PlayerMoneyTextPosition.left,PlayerMoneyTextPosition.top,pencilYourMoney);
		}
	}
	private void drawScreenDark(Canvas canvas) {
		bmWidth = darkScreen.getWidth();
		bmHeight = darkScreen.getHeight();

		for (y = 0;y < CanvasHeight;y+=bmHeight){
			for (x = 0;x < CanvasWidth;x+=bmWidth){
				canvas.drawBitmap(darkScreen, x, y, null);
			}
		}		
	}
	public void onTouch(MotionEvent event)
	{
		if (bonus.shellQnt > 0){
			bonus.onTouch(event);
			return;
		}
		
		ClickedPosition.left = event.getX();
		ClickedPosition.top = event.getY();
		ClickedPosition.right = event.getX();
		ClickedPosition.bottom = event.getY() + 1;

		switch (event.getAction())
		{    
		case MotionEvent.ACTION_DOWN:
		{
			if(RectF.intersects(ClickedPosition, SurfAgainPosition))
				AgainIndex++;

			if(RectF.intersects(ClickedPosition, SurfShopPosition))
				ShopIndex++;


		}break;

		case MotionEvent.ACTION_UP:
		{

			if(RectF.intersects(ClickedPosition, SurfAgainPosition))
				if(AgainIndex  == 1)
				{
					if(!isButtonReplayPressed) {


                        isButtonReplayPressed = true;

                    }

				}

			if(RectF.intersects(ClickedPosition, SurfShopPosition))
				if(ShopIndex == 1)
				{
					if(!isButtonShopPressed)
						isButtonShopPressed = true;

				}
			if(!isButtonReplayPressed)
				AgainIndex = 0;

			if(!isButtonShopPressed)
				ShopIndex = 0;

		}break;
		}
	}
	public void onGameEnded(int CoinsCollected, int distance, int ShellsCollected)
	{
		this.coinsCollected = String.valueOf(CoinsCollected);
		this.distance = String.valueOf(distance) + "M";
		this.myGold = StorageInfo.storage.addGold(CoinsCollected);
		bonus.onGameEnded(ShellsCollected);
		record.SetNewRecord(distance);


        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ad.setVisibility(View.VISIBLE);
            }
        });


	}
	public void Restart() {
		ShopIndex = 0;
		AgainIndex = 0;
		isButtonShopPressed = false;
		isButtonReplayPressed =false;
		bonus.initPositions();
		bonus.animating = true;
		bonus.goingDown = true;
		
	}
}
