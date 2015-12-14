package net.game;

import java.util.Calendar;

import net.surf.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;


public class GodMode {
	
	private static final long FRAMETRANSFORMATION = 150;
	private static final long FRAMECLOUDS = 100;
	private static final float CloudVelocity = 7f;
	private static final long FRAMEAURA = 200;
	private static final long TURNSIDEFRAME = 40;
	private long TurnGodTime = System.currentTimeMillis();
	Aura aura;
	Bitmap[] LeftBottomCloud = new Bitmap[6];
	Bitmap[] LeftTopCloud = new Bitmap[6];
	Bitmap[] RightBottomCloud = new Bitmap[6];
	Bitmap[] RightTopCloud = new Bitmap[6];
	Bitmap[] SunMap = new Bitmap[5];
	Bitmap[] FlatClouds = new Bitmap[3];
	Bitmap[] TransforMap = new Bitmap[7];
	Bitmap[] FxAura = new Bitmap[4];
	int CloudFrameWidth;
	int CloudFrameHeight;
	int AnimCloudIndex = 0;
	int AnimSunIndex = 0;
	Context context;
	private long oldTime = System.currentTimeMillis();
	int CanvasWidth;
	int CanvasHeight;
	Rect FlatCloudPosition;
	private int AnimGodFrame =0;
	Surfer surfer;
	Rect TransformPosition;
	public Bitmap TrickToPerform;
	public boolean doingTrick = false;
	public long timeTrick = System.currentTimeMillis();
	public float GodTrickedTime = 0;
	public float GodTrickSpeedTime = 1.5f;
	private int CurAnimation = -1;
	int Animations =0;
	int CurAnimationAura = 0;
	private long AuraTime = System.currentTimeMillis();
	boolean isGoingUp = true;
	Level level;
	private boolean isTransforming = false;
	private boolean turningSide = false;
	private Calendar TurnSideTime = Calendar.getInstance();
	int TurnSideIndex = 0;
	private int repeat;
	private Bitmap StartTrickMap;
	RectF dst;
	Rect src;
	private int TrickWidth;
	private int TrickHeight;
	private int x;
	private int width;
	private int srcLeft;
	private int srcTop;
	private int srcRight;
	private int srcBottom;
	private int destLeft;
	private int destTop;
	private int destRight;
	private int destBottom;
	public String TheTrick;
	Speaker speaker;
	
	float Velocity;
	public GodMode(Context context,int CanvasHeight, int CanvasWidth,Surfer surfer, Level level, Aura aura, Speaker speaker)
	{
		this.context = context;
		this.CanvasHeight = CanvasHeight;
		this.CanvasWidth = CanvasWidth;
		float scaleWidth = (CanvasWidth / 800);
		Velocity = 5f * scaleWidth;
		LoadResources();
		this.speaker = speaker;
		CloudFrameWidth = LeftBottomCloud[0].getWidth();
		CloudFrameHeight = LeftBottomCloud[0].getHeight();
		FlatCloudPosition = new Rect(0,0,0,0);
		TransformPosition = new Rect(CanvasWidth/2-TransforMap[0].getWidth()/2, CanvasHeight, 0, 0);

		this.surfer = surfer;
		this.level = level;
		this.aura = aura;
		
		dst = new RectF(0,0,0,0);
		src = new Rect(0,0,0,0);
	}


	private void LoadResources() {

		LeftBottomCloud[0] = BitmapFactory.decodeResource( context.getResources(), R.drawable.cornerclouds_00);
		LeftBottomCloud[1] = BitmapFactory.decodeResource( context.getResources(), R.drawable.cornerclouds_01);
		LeftBottomCloud[2] = BitmapFactory.decodeResource( context.getResources(), R.drawable.cornerclouds_02);
		LeftBottomCloud[3] = BitmapFactory.decodeResource( context.getResources(), R.drawable.cornerclouds_03);
		LeftBottomCloud[4] = BitmapFactory.decodeResource( context.getResources(), R.drawable.cornerclouds_04);
		LeftBottomCloud[5] = BitmapFactory.decodeResource( context.getResources(), R.drawable.cornerclouds_05);

		RightBottomCloud[0] = Util.BitMapHelper.LoadFliped(LeftBottomCloud[0]);
		RightBottomCloud[1] = Util.BitMapHelper.LoadFliped(LeftBottomCloud[1]);
		RightBottomCloud[2] = Util.BitMapHelper.LoadFliped(LeftBottomCloud[2]);
		RightBottomCloud[3] = Util.BitMapHelper.LoadFliped(LeftBottomCloud[3]);
		RightBottomCloud[4] = Util.BitMapHelper.LoadFliped(LeftBottomCloud[4]);
		RightBottomCloud[5] = Util.BitMapHelper.LoadFliped(LeftBottomCloud[5]);

		LeftTopCloud[0] = Util.BitMapHelper.LoadFlipedVertical(LeftBottomCloud[0]);
		LeftTopCloud[1] = Util.BitMapHelper.LoadFlipedVertical(LeftBottomCloud[1]);
		LeftTopCloud[2] = Util.BitMapHelper.LoadFlipedVertical(LeftBottomCloud[2]);
		LeftTopCloud[3] = Util.BitMapHelper.LoadFlipedVertical(LeftBottomCloud[3]);
		LeftTopCloud[4] = Util.BitMapHelper.LoadFlipedVertical(LeftBottomCloud[4]);
		LeftTopCloud[5] = Util.BitMapHelper.LoadFlipedVertical(LeftBottomCloud[5]);

		RightTopCloud[0] = Util.BitMapHelper.LoadFlipedVertical(RightBottomCloud[0]);
		RightTopCloud[1] = Util.BitMapHelper.LoadFlipedVertical(RightBottomCloud[1]);
		RightTopCloud[2] = Util.BitMapHelper.LoadFlipedVertical(RightBottomCloud[2]);
		RightTopCloud[3] = Util.BitMapHelper.LoadFlipedVertical(RightBottomCloud[3]);
		RightTopCloud[4] = Util.BitMapHelper.LoadFlipedVertical(RightBottomCloud[4]);
		RightTopCloud[5] = Util.BitMapHelper.LoadFlipedVertical(RightBottomCloud[5]);


		SunMap[0] = BitmapFactory.decodeResource( context.getResources(), R.drawable.sun_big_00);
		SunMap[1] = BitmapFactory.decodeResource( context.getResources(), R.drawable.sun_big_01);
		SunMap[2] = BitmapFactory.decodeResource( context.getResources(), R.drawable.sun_big_02);
		SunMap[3] = BitmapFactory.decodeResource( context.getResources(), R.drawable.sun_big_03);
		SunMap[4] = BitmapFactory.decodeResource( context.getResources(), R.drawable.sun_big_04);


		FlatClouds[0] =BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_clouds_01);
		FlatClouds[1] =BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_clouds_02);
		FlatClouds[2] =BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_clouds_03);

		TransforMap[0]  =BitmapFactory.decodeResource( context.getResources(), R.drawable.surfergod_transform_00);
		TransforMap[1]  =BitmapFactory.decodeResource( context.getResources(), R.drawable.surfergod_transform_01);
		TransforMap[2]  =BitmapFactory.decodeResource( context.getResources(), R.drawable.surfergod_transform_02);
		TransforMap[3]  =BitmapFactory.decodeResource( context.getResources(), R.drawable.surfergod_transform_03);
		TransforMap[4]  =BitmapFactory.decodeResource( context.getResources(), R.drawable.surfergod_appear_00);
		TransforMap[5]  =BitmapFactory.decodeResource( context.getResources(), R.drawable.surfergod_appear_01);
		TransforMap[6]  =BitmapFactory.decodeResource( context.getResources(), R.drawable.surfergod_appear_02);

		FxAura[0] = BitmapFactory.decodeResource( context.getResources(), R.drawable.fx_aura_00);
		FxAura[1] = BitmapFactory.decodeResource( context.getResources(), R.drawable.fx_aura_01);
		FxAura[2] = BitmapFactory.decodeResource( context.getResources(), R.drawable.fx_aura_02);
		FxAura[3] = BitmapFactory.decodeResource( context.getResources(), R.drawable.fx_aura_03);

		StartTrickMap =BitmapFactory.decodeResource( context.getResources(), R.drawable.surfergod_start_trick); 
	}
	public void updateCurrentTrick()
	{
		if(System.currentTimeMillis() - timeTrick >= GameConfig.TrickFrameRate)
		{
			TrickWidth = TrickToPerform.getWidth();
			TrickHeight = TrickToPerform.getHeight();
			Animations = TrickWidth / TrickHeight;		
			
			if(CurAnimation < Animations-1)
			{
				AnimSunIndex=0;
				CurAnimation++;
			}
			else
			{
				OnTrickComplete();
			}

			timeTrick = (System.currentTimeMillis());
		}
	}
	private void OnTrickComplete() {
		
		if(Animations > 0 && Animations <= 2)
		{
			AnimSunIndex = 1;
		}
		if(Animations >= 3 && Animations <= 4)
		{
			AnimSunIndex = 2;
		}
		if(Animations >= 4 && Animations <= 5)
		{
			AnimSunIndex = 3;
		}
		if(Animations >=5)
		{
			AnimSunIndex = 4;
		}
		CurAnimation = -1;
		doingTrick = false;
		if(TheTrick.equals("LeftToRight"))
		{
		surfer.coinsColected += level.player.FlingHorizontalLeftToRightCoin;
		}
		else if(TheTrick.equals("RightToLeft"))
		{
		surfer.coinsColected += level.player.FlingHorizontalRightToLeftCoin;
		}
		else if(TheTrick.equals("TopToBottom"))
		{
		surfer.coinsColected += level.player.FlingVerticalTopToBottomCoin;
		}
		else
		{
			surfer.coinsColected += level.player.FlingVerticalBottomToTopCoin;
		}
			
		
	}
	private void updateClouds() {
		if(System.currentTimeMillis() - oldTime > FRAMECLOUDS)
		{
			if(AnimCloudIndex < 5)
				AnimCloudIndex++;
			else
				AnimCloudIndex = 0;

			oldTime =(System.currentTimeMillis());
		}

	}

	private void drawAura(Canvas canvas)
	{
		canvas.drawBitmap(FxAura[CurAnimationAura], TransformPosition.left,TransformPosition.top,null);

	}
	
	private void drawClouds(Canvas canvas) {
		canvas.drawBitmap(LeftBottomCloud[AnimCloudIndex], 0,CanvasHeight-CloudFrameHeight, null);
		canvas.drawBitmap(LeftTopCloud[AnimCloudIndex], 0,0, null);
		canvas.drawBitmap(RightBottomCloud[AnimCloudIndex], CanvasWidth-CloudFrameWidth,CanvasHeight-CloudFrameHeight, null);
		canvas.drawBitmap(RightTopCloud[AnimCloudIndex], CanvasWidth-CloudFrameWidth,0, null);
	}
	private void drawFlatClouds(Canvas canvas)
	{
		int bmWidth = FlatClouds[0].getWidth();
		for (x = -300, width = CanvasWidth; x < width; x += bmWidth) {
			canvas.drawBitmap(FlatClouds[1], x, FlatCloudPosition.top , null);
		}
		bmWidth = FlatClouds[1].getWidth();
		for (x = -170, width = CanvasWidth; x < width; x += bmWidth) {
			canvas.drawBitmap(FlatClouds[2], x, FlatCloudPosition.top + FlatClouds[0].getHeight()*2, null);
		}

		bmWidth = FlatClouds[2].getWidth();
		for (x = -50, width = CanvasWidth; x < width; x += bmWidth) {
			canvas.drawBitmap(FlatClouds[0], x, FlatCloudPosition.top + FlatClouds[1].getHeight()*4, null);
		}

		bmWidth = FlatClouds[1].getWidth();
		for (x = 50, width = CanvasWidth; x < width; x += bmWidth) {
			canvas.drawBitmap(FlatClouds[1], x, FlatCloudPosition.top + FlatClouds[1].getHeight()*6, null);
		}

		bmWidth = FlatClouds[1].getWidth();
		for (x = 80, width = CanvasWidth; x < width; x += bmWidth) {
			canvas.drawBitmap(FlatClouds[2], x, FlatCloudPosition.top + FlatClouds[1].getHeight()*8, null);
		}
		bmWidth = FlatClouds[1].getWidth();
		for (x = 80, width = CanvasWidth; x < width; x += bmWidth) {
			canvas.drawBitmap(FlatClouds[0], x, FlatCloudPosition.top + FlatClouds[1].getHeight()*10, null);
		}
	}
	private void drawSun(Canvas canvas)
	{
		canvas.drawBitmap(SunMap[AnimSunIndex], SunMap[AnimSunIndex].getWidth(),CanvasHeight/2-SunMap[AnimSunIndex].getHeight()/2, null);
	}
	public void drawTurning(Canvas canvas) {
		drawEnviroment(canvas);
		
		if(isTransforming)
		{
			drawAura(canvas);
			canvas.drawBitmap(TransforMap[AnimGodFrame], TransformPosition.left, TransformPosition.top, null);
		}
		else
		{
			aura.drawforturning(canvas,TransformPosition);
			canvas.drawBitmap(surfer.Board[TurnSideIndex], TransformPosition.left, TransformPosition.top, null);
			canvas.drawBitmap(surfer.Body[TurnSideIndex], TransformPosition.left, TransformPosition.top, null);
			canvas.drawBitmap(surfer.Head[TurnSideIndex], TransformPosition.left, TransformPosition.top, null);
		}
	}
	public void updateTurnGod() {
			oldTime = (System.currentTimeMillis());

		if(isGoingUp)
		{
			updateGoingUp();
		
		}
		if(turningSide)
		{
			updateTurnSide();
		}

		if(isTransforming )
		{
			updateTransformation();
		}

		UpdateEnvironment();

		if(!surfer.turningGOD)
		{
			surfer.trickingGOD = true;
			surfer.EnergyCollected = 0;
		}
	}


	private void updateTurnSide() {
		aura.update();
		if(System.currentTimeMillis() - TurnSideTime .getTimeInMillis() > TURNSIDEFRAME)
		{
			if(TurnSideIndex < 7)
				TurnSideIndex++;
			else
			{
				TurnSideIndex= 0;
				turningSide = false;
				isTransforming = true;
			}
			TurnSideTime.setTimeInMillis(System.currentTimeMillis());
		}

	}


	private void updateGoingUp() {
		aura.update();
		int FinalPosition = CanvasHeight/2-TransforMap[0].getHeight()/2;
		
		if(TransformPosition.top > FinalPosition)
			TransformPosition.top -= Velocity ;
		else
		{
			isGoingUp = false;
			turningSide = true;
		}



	}


	private void UpdateEnvironment() {
		updateClouds();
		updateFlatClouds();
		updateAura();
	}


	private void updateAura() {
		if(System.currentTimeMillis() - AuraTime > FRAMEAURA)
		{
			if(CurAnimationAura < 3)
				CurAnimationAura++;
			else
				CurAnimationAura = 0;

			AuraTime =(System.currentTimeMillis());
		}

	}


	private void updateTransformation() {
		if(System.currentTimeMillis() - TurnGodTime > FRAMETRANSFORMATION)
		{

			switch (AnimGodFrame )
			{
			case 0: {
				AnimGodFrame++;
			}
			break;
			case 1: {
				AnimGodFrame++;
			}
			break;
			case 2: {
				AnimGodFrame++;
			}
			break;
			case 3: {
				if(repeat > 2)
				{
					AnimGodFrame++;
				}
				else
				{
					repeat++;
					AnimGodFrame = 0;
				}

			}
			break;
			case 4: {
				AnimGodFrame++;
			}
			break;
			case 5: AnimGodFrame++;break;
			case 6:
			{
				surfer.turningGOD = false;
				surfer.trickingGOD = true;
				surfer.GodTrickedTime = level.godBar.MaxBarSize;
				surfer.EnergyCollected = 0;
			}

			break;

			}
			TurnGodTime=(System.currentTimeMillis());
		}
	}

	private void updateFlatClouds() {
		if(FlatCloudPosition.top*6 < CanvasHeight)
			FlatCloudPosition.top += CloudVelocity;
		else 
			FlatCloudPosition.top = 0;

	}


	public void drawTrickGod(Canvas canvas) {
		drawEnviroment(canvas);
		drawAura(canvas);
		if(!doingTrick)
			canvas.drawBitmap(TransforMap[AnimGodFrame], TransformPosition.left, TransformPosition.top, null);
		else 
			drawCurrentTrick(canvas);
	}


	private void drawEnviroment(Canvas canvas) {
		canvas.drawARGB(255, 172, 228, 242);
		drawClouds(canvas);
		drawFlatClouds(canvas);
		drawSun(canvas);

	}
	private void drawCurrentTrick(Canvas canvas) {
		
		
		if(CurAnimation < 0)
		{
			canvas.drawBitmap(StartTrickMap, TransformPosition.left, TransformPosition.top, null);
			speaker.GodTrickStart();
		}
		else
		{
			TrickWidth = TrickToPerform.getWidth();
			TrickHeight = TrickToPerform.getHeight();
			TrickWidth = TrickHeight;
			srcLeft = TrickWidth * CurAnimation ;
			srcTop = 0;
			srcRight = (TrickWidth * CurAnimation) + TrickWidth;
			srcBottom = TrickHeight;

			destLeft = TransformPosition.left;
			destTop = TransformPosition.top;
			destRight = destLeft + TrickHeight;
			destBottom = destTop + TrickHeight;
		    dst.left = destLeft;
		    dst.top = destTop;
		    dst.right = destRight;
		    dst.bottom = destBottom;
			src.left = srcLeft;
			src.top = srcTop;
			src.right = srcRight;
			src.bottom = srcBottom;
			canvas.drawBitmap(TrickToPerform, src, dst,null);
		}

	}
	public void UpdateStandGod() {

		UpdateEnvironment();
		if(surfer.GodTrickedTime > 0)
		{
			surfer.GodTrickedTime -= surfer.GodTrickSpeedTime;
		}
		else
		{
			TransformPosition.top = CanvasHeight;
			AnimGodFrame = 0;
			surfer.isGodMode = false;
			surfer.trickingGOD = false;
			surfer.Position.top = -surfer.DrawableHeight;
			surfer.Index = 8;
			surfer.tricking = true;
			surfer.Str = -1;
			turningSide = false;
			isTransforming = false;
			isGoingUp = true;
			repeat = 0;


		}		
	}
}
