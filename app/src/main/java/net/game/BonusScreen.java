package net.game;

import java.util.Random;

import net.surf.R;
import Util.StorageInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class BonusScreen {

	private static final int STARTGOD = 0;
	private static final int MORESHELLS = 1;
	private static final int ONEHUNDREDCOINS = 2;
	private static final int TWOHUNDREDCOINS = 3;
	private static final int STARTBOAT = 4;
	private static final int DOUBLECOIN = 5;

	private static int MaxPrizes = 5;
	int ItemToGive = -1;
	private int LUCK = 15;
	public static int coinsFactor = 1;
	Random rdn = new Random();
	Context context;

	Bitmap darkScreen;
	Bitmap titleTexture;
	Bitmap sheelBoxQntTexture;
	Bitmap[] shellsTexture = new Bitmap[3];
	Bitmap[] PrizeIcons = new Bitmap[6];

	public RectF sheelBoxQntPos = new RectF();
	public RectF sheelBoxQntPosFinal = new RectF();;
	public RectF[] shellsPos = new RectF[3];
	public RectF[] shellsPosFinal = new RectF[3];	
	public RectF prizePosition = new RectF();
	public RectF prizeTextPosition = new RectF();
	RectF ClickedPosition;

	private int CanvasHeight;
	private int CanvasWidth;
	private int titleHeigth;
	private int titleBGWidth;
	Matrix matrix = new Matrix();
	public int shellQnt = 0;
	Random rnd = new Random();
	private  int bmWidth;
	private  int bmHeight;
	private int y;
	private int x;

	boolean animating = true;
	boolean goingDown = true;
	private boolean goingUp;
	private boolean opening;
	private int MaxY;
	private float MinY;

	private float VelocitY;
	private long oldTime = System.currentTimeMillis();
	private long FRAMEBG = 500;
	private long FRAMEDISPLAY = 1000;
	int IndexChoose = -1;

	int IndexLeft = 0;
	int IndexMiddle = 0;
	int IndexRight = 0;
	private boolean showingItem;
	private long showTime;
	Paint pencil;
	Speaker speaker;
	 float scaleWidth;
	 float scaleHeight;
	public BonusScreen(Context myContext, int myCanvasHeight, int myCanvasWidth, Paint myPencil, Speaker speaker){
		context = myContext;
		CanvasHeight = myCanvasHeight;
		CanvasWidth = myCanvasWidth;
		this.speaker = speaker;
		darkScreen = BitmapFactory.decodeResource(context.getResources(), R.drawable.black_square);
		this.pencil = myPencil;
		ClickedPosition = new RectF();
		scaleWidth = (CanvasWidth / 800);
		scaleHeight = (CanvasHeight / 480);
		initItens();
		initPositions();
		MaxY = (myCanvasHeight/2) + (shellsTexture[0].getHeight() /3);
		VelocitY = 30 * scaleWidth;
		bmWidth = darkScreen.getWidth();
		bmHeight = darkScreen.getHeight();
		

	}

	private void initItens(){
		shellsTexture[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shell0);
		shellsTexture[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shell1);		
		shellsTexture[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shell2);

		titleTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.bonus_title);
		sheelBoxQntTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.bonus_shellbox);
		PrizeIcons[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bonusicon_god);
		PrizeIcons[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bonusicon_3shells);
		PrizeIcons[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bonusicon_100coins);
		PrizeIcons[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bonusicon_200coins);
		PrizeIcons[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bonusicon_boat);
		PrizeIcons[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bonusicon_doublecoins);
	}

	public void initPositions(){
		titleHeigth = CanvasHeight/12;
		titleBGWidth = CanvasWidth/2 - titleTexture.getWidth()/2;

		float left =  CanvasWidth/2 - sheelBoxQntTexture.getWidth()/2;
		float top = titleHeigth+titleTexture.getHeight();
		float right = left + sheelBoxQntTexture.getWidth();
		float bottom = top + sheelBoxQntTexture.getHeight();
		float shellWidthFactor = shellsTexture[0].getWidth()/6;
		sheelBoxQntPos = new RectF( left,top , right, bottom);
		
		left = CanvasWidth/2 - shellsTexture[0].getWidth()/2;
		right = left +  shellsTexture[0].getWidth();
		top = (CanvasHeight/2);
		bottom = top + sheelBoxQntTexture.getHeight();
		shellsPos[1] = new RectF (left,top,right,bottom);
					
		right = shellsPos[1].left - shellWidthFactor;
		left = right - shellsTexture[0].getWidth();
		shellsPos[0] = new RectF (left,top,right,bottom);

		left = shellsPos[1].right + shellWidthFactor;
		right = left +  shellsTexture[0].getWidth();
		shellsPos[2] = new RectF(left,top,right,bottom);

		MinY = shellsPos[0].top;

		shellsPos[0].top -= CanvasHeight;
		shellsPos[1].top -= CanvasHeight;
		shellsPos[2].top -= CanvasHeight;
		sheelBoxQntPos.top -=CanvasHeight;
		titleHeigth -=CanvasHeight;



	}

	public void update()
	{
		if(animating)
		{
			if(goingDown)
				GoDown();

			if(goingUp)
				GoUp();
		}

		if(opening)
			Open();

		if(showingItem)
			Show();

	}
	private void Show() {
		if(System.currentTimeMillis() - showTime > FRAMEDISPLAY)
		{
			showingItem = false;
			Next();
		}

	}

	private void Next() {

		if(shellQnt > 0)
		{
			shellQnt--;
			IndexChoose = -1;	
			IndexLeft = 0;
			IndexMiddle = 0;
			IndexRight = 0;
		}
	}

	private void Open() {
		if(System.currentTimeMillis() - oldTime > FRAMEBG)
		{

			if(IndexChoose == 0)
			{
				if(IndexLeft < 2)
					IndexLeft++;
				else
				{
					opening = false;
				}
			}

			if(IndexChoose == 1)
			{
				if(IndexMiddle < 2)
					IndexMiddle++;
				else
				{
					opening = false;
				}
			}

			if(IndexChoose == 2)
			{
				if(IndexRight < 2)
					IndexRight++;
				else
				{
					opening = false;
				}
			}
			FRAMEBG = 120;
			oldTime = System.currentTimeMillis();

			if(IndexRight == 1 || IndexLeft == 1 || IndexMiddle == 1)
			{
				showingItem = true;
				if(ItemToGive <= MaxPrizes)
					speaker.Luck();
				else 
					speaker.NoLuck();
				
				showTime = System.currentTimeMillis();
			}
		}

	}

	private void GoUp() {

		if(shellsPos[0].top > MinY)
		{
			shellsPos[0].top -= VelocitY;
			shellsPos[1].top -= VelocitY;
			shellsPos[2].top -= VelocitY;
			titleHeigth -= VelocitY;
			sheelBoxQntPos.top -=VelocitY;
		}
		else
		{
			shellsPos[0].bottom = shellsPos[0].top + shellsTexture[0].getHeight();
			shellsPos[1].bottom = shellsPos[0].top + shellsTexture[0].getHeight();
			shellsPos[2].bottom = shellsPos[0].top + shellsTexture[0].getHeight();

			goingUp = false;
			animating = false;
		}

	}

	private void GoDown() {
		if(shellsPos[0].top < MaxY)
		{
			shellsPos[0].top += VelocitY;
			shellsPos[1].top += VelocitY;
			shellsPos[2].top += VelocitY;
			sheelBoxQntPos.top +=VelocitY;
			titleHeigth += VelocitY;
		}
		else
		{
			goingUp = true;
			goingDown = false;
		}
	}
	public void draw(Canvas canvas)
	{
		drawBG(canvas);
		drawItens(canvas);
		drawPrize(canvas);
	}

	private void drawPrize(Canvas canvas) {

		if(ItemToGive <= MaxPrizes && showingItem)
			canvas.drawBitmap(PrizeIcons[ItemToGive], prizePosition.left, prizePosition.top, null);
	}

	public void drawBG (Canvas canvas){


		for (y = 0;y < CanvasHeight;y+=bmHeight){
			for (x = 0;x < CanvasWidth;x+=bmWidth){
				canvas.drawBitmap(darkScreen, x, y, null);
			}
		}
		canvas.drawBitmap(titleTexture, titleBGWidth, titleHeigth, null);


	}

	public void drawItens (Canvas canvas){
		canvas.drawBitmap(sheelBoxQntTexture, sheelBoxQntPos.left,sheelBoxQntPos.top, null);
		canvas.drawText(String.valueOf(shellQnt), sheelBoxQntPos.left + (sheelBoxQntTexture.getWidth() * 0.4f) , sheelBoxQntPos.top + (sheelBoxQntTexture.getHeight()* 0.6f) , pencil);

		matrix.reset();
		matrix.setTranslate(shellsPos[0].left, shellsPos[0].top);
		matrix.postRotate(-15, shellsPos[0].left + shellsTexture[0].getHeight()/2, shellsPos[0].top + shellsTexture[0].getHeight()/2);
		canvas.drawBitmap(shellsTexture[IndexLeft], matrix,null);

		canvas.drawBitmap(shellsTexture[IndexMiddle], shellsPos[1].left,shellsPos[1].top, null);

		matrix.reset();
		matrix.setTranslate(shellsPos[2].left, shellsPos[2].top);
		matrix.postRotate(15, shellsPos[2].left + shellsTexture[2].getHeight()/2, shellsPos[2].top + shellsTexture[2].getHeight()/2);
		canvas.drawBitmap(shellsTexture[IndexRight], matrix,null);

	}

	public void onGameEnded(int CoinsCollected)
	{
		shellQnt = CoinsCollected;
	}

	public void onTouch(MotionEvent event)
	{
		if(IndexChoose != -1) //ja apertou
			return;

		ClickedPosition.left = event.getX();
		ClickedPosition.top = event.getY();
		ClickedPosition.right = event.getX() + 1;
		ClickedPosition.bottom = event.getY() + 1;

		switch (event.getAction())
		{    
		case MotionEvent.ACTION_DOWN:
		{
			if(RectF.intersects(ClickedPosition, shellsPos[0]))
			{
				prizePosition.left = shellsPos[0].centerX() - PrizeIcons[0].getWidth()/2.7f;
				prizePosition.top = shellsPos[0].centerY() + PrizeIcons[0].getWidth()/3.5f;
				opening = true;
				IndexChoose = 0;
				RandomItem();
			}

			if(RectF.intersects(ClickedPosition, shellsPos[1]))
			{
				prizePosition.left = shellsPos[1].centerX() - PrizeIcons[0].getWidth()/1.9f;
				prizePosition.top = shellsPos[1].centerY() + PrizeIcons[0].getWidth()/3;
				opening = true;
				IndexChoose = 1;
				RandomItem();
			}

			if(RectF.intersects(ClickedPosition, shellsPos[2]))
			{
				prizePosition.left = shellsPos[2].centerX() - PrizeIcons[0].getWidth()/1.5f;
				prizePosition.top = shellsPos[2].centerY() + PrizeIcons[0].getWidth()/3;
				opening = true;
				IndexChoose = 2;
				RandomItem();
			}

		}break;
		}
	}

	private void RandomItem() {
		ItemToGive = rdn.nextInt(LUCK); //TODO: verifica se o cara ja nao tem o item
		//TODO: a

		if(ItemToGive <= MaxPrizes)
			switch (ItemToGive)
			{
			case STARTGOD:
			{
				if(StorageInfo.storage.IsThereStartGod())
					RandomItem();
				else 
					StorageInfo.storage.setStartGod(true);
			}break;

			case STARTBOAT:
			{
				if(StorageInfo.storage.IsThereStartBoat())
					RandomItem();
				else 
					StorageInfo.storage.setStartBoat(true);
			}break;
			case DOUBLECOIN:
			{
				if(StorageInfo.storage.IsThere2xCoin())
					RandomItem();
				else 
					StorageInfo.storage.setDoubleCoinsBonuts(true);
			}break;

			case MORESHELLS:
				shellQnt+=3;break;

			case ONEHUNDREDCOINS:
				StorageInfo.storage.addGold(100);
				break;
			case TWOHUNDREDCOINS:
				StorageInfo.storage.addGold(200);
				break;
			}


	}



}
