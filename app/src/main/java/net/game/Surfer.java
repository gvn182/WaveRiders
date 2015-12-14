package net.game;

import java.util.ArrayList;

import net.surf.R;
import Util.BitMapHelper;
import Util.StorageInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

public class Surfer {
	float MaxDown;
	private static float threeXFactor = 2f;
	private static float twoXFactor = 1f;
	private static float oneXFactor = 0.25f;
	private static float oneAndHalfXFactor = 0.5f;
	private static float halfXFactor = 0.15f;
	private static float STARTSPEED = 3.0f;
	private static final float FRAMEDEATH = 100; //millis
	private static float VELOCITYLOST = 0.02f;
	private static float VELOCITYGET = 0.02f;
	private static float Gravity = 0.5f;
	private static final float DefaultTimeToWaitSpeed = 0.65f;
	private static float trickFactor = 2f;
	private static final long FRAMESWIM = 150;
	private float MaxSpeedNoGod = 8f;
	public int Index = 7;
	static final float DefaultTimeToWait = 0.10f;
	private float TimeLoss = 0.05f;
	public float GodTrickSpeedTime = 0.5f;
	float internalSpeed;
	float SpeedUPTrick = 0;
	int SpeedDownTrick = 0;
	boolean isGodMode = false;
	boolean touching = false;
	boolean lastTouch = false;
	RectF Position;
	int HeadID;
	int BodyID;
	int BoardID;
	int i;
	Context _context;
	Bitmap[] Head = new Bitmap[18];
	Bitmap[] Body = new Bitmap[18];
	Bitmap[] Board = new Bitmap[18];
	Bitmap FlingHorizontalLeftToRightTrick;
	Bitmap FlingHorizontalRightToLeftTrick;
	Bitmap FlingVerticalTopToBottomTrick;
	Bitmap FlingVerticalBottomToTopTrick;
	Tail SurferTail;
	Bitmap[] DeathAnimation = new Bitmap[7];
	Bitmap[] SharkDeathAnimation = new Bitmap[4];
	int HeadIndex;
	int BodyIndex;
	int BoardIndex;
	long oldTime = System.currentTimeMillis();

	public float speed = STARTSPEED;
	public boolean trickingGOD = false;
	public boolean dyeing = false;
	public boolean dyeingForShark = false;
	public boolean dead = false;
	int DrawableWidth = 0;
	int DrawableHeight = 0;
	private int CanvasHeight = 0;
	private int CanvasWidth = 0;
	Player _player;
	public boolean doingTrick;
	Bitmap TrickToPerform;
	int CurAnimation = 0;
	public boolean touchingRight;
	public boolean touchingLeft;

	float TimeToWaitIndex = DefaultTimeToWait;
	public float Direction;
	public boolean tricking;
	Level level;
	Aura godAura;
	float Str;
	float Down = 0;
	private float TimeToWaitSpeed = 0;
	private ArrayList<Integer> TrickVector = new ArrayList<Integer>();
	private ArrayList<Integer> RemoveList = new ArrayList<Integer>();
	public boolean turningGOD = false;
	long TurnGodTime = System.currentTimeMillis();
	private float GodTime = 0;
	public int coinsColected;
	public float GodTrickedTime = 0;
	public int EnergyCollected;
	private float oldSpeed;
	private int DeathIndex;
	private boolean visible = true;
	WaterBG water;
	Bitmap[] SwimmingHeadMap = new Bitmap[4];
	Bitmap[] SwimmingBodyMap = new Bitmap[4];
	Bitmap[] SwimmingBoardMap = new Bitmap[4];
	Bitmap[] WordMap = new Bitmap[4];
	int SwinIndex =0;
	boolean hasStarted = false;
	private boolean firstTouch;
	GodMode godMode;
	EndGame endgame;
	RectF dst;
	Rect src;
	RectF dstTrick;
	RectF magneticArea;
	float screenFactor;
	Rect srcTrick;
	RectF BoudingRecTF;
	Paint pt; 
	ArrayList<Integer> Trick = new ArrayList<Integer>();
	RectF BoundingRectBoard;
	int srcLeft;
	long timeAtual;
	private float left;
	private float top;
	Speaker speaker;
	float resultFactor;
	public int shellCount = 0;
	PowerUpEffects PuEffects;
	float scaleWidth;
	float scaleHeight;
	
	public Surfer(Level myLevel,Player player,Ocean ocean,WaterBG water, Context context, int Height, int Width, EndGame endgame, Speaker speaker)
	{
		screenFactor = (float) Width / Height;
		level = myLevel;
		_player = player;
		CanvasHeight = Height;
		CanvasWidth = Width;
		_context = context;
		this.water = water;
		this.endgame = endgame;
		HeadIndex = player.Head;
		BodyIndex = player.Body;
		BoardIndex = player.Board;
		 scaleWidth = (CanvasWidth / 800);
		 scaleHeight = (CanvasHeight / 480);
		LoadContent();
		Position = new RectF(CanvasHeight/2,water.FoamTop,0,0);
		DrawableWidth = this.Head[0].getWidth();
		DrawableHeight = this.Head[0].getHeight();
		dst = new RectF();
		src = new Rect();
		dstTrick = new RectF();
		srcTrick = new Rect();
		godAura = new Aura(context, this, level, Height, Width) ;
		PuEffects = new PowerUpEffects(context, this, Height, Width);
		SurferTail = new Tail(context);
		godMode = new GodMode(context, Height, Width,this,level,godAura,speaker);
		BoudingRecTF = new RectF();
		pt = new Paint();
		BoundingRectBoard = new RectF();
		this.speaker = speaker;
		STARTSPEED = 3 * scaleWidth; 
		speed = 3 * scaleWidth; 
		VELOCITYLOST = 0.02f * scaleWidth;
		VELOCITYGET = (PowerUps.isThereThePowerUp(PowerUps.TwoXFasterPowerUp)? PowerUps.speedFatcor : 1f) * 0.02f * scaleWidth;
		MaxSpeedNoGod = 8f * scaleWidth;
		MaxSpeedNoGod *= (PowerUps.isThereThePowerUp(PowerUps.TwoXFasterPowerUp)? PowerUps.speedFatcor : 1f);
		Gravity = 0.1f * scaleHeight;
		Gravity = (PowerUps.isThereThePowerUp(PowerUps.FeatherPowerUp)? PowerUps.gravityFactor : 1f) * 0.5f * scaleHeight;
		trickFactor = 2.3f;
		twoXFactor = 2.3f;
		oneXFactor = 1f;
		oneAndHalfXFactor = 1.4f;
		halfXFactor = 0.5f;
		threeXFactor = 4.3f;
		MaxDown = 50 * scaleHeight;
		RemoveList.add(Integer.valueOf(0));
		RemoveList.add(Integer.valueOf(1));
		RemoveList.add(Integer.valueOf(6));
		RemoveList.add(Integer.valueOf(7));
		RemoveList.add(Integer.valueOf(8));
		RemoveList.add(Integer.valueOf(9));
		RemoveList.add(Integer.valueOf(10));
		RemoveList.add(Integer.valueOf(11));
		RemoveList.add(Integer.valueOf(16));
		RemoveList.add(Integer.valueOf(17));
		RemoveList.add(Integer.valueOf(18));
		RemoveList.add(Integer.valueOf(19));
		RemoveList.add(Integer.valueOf(20));

	}
	public void update()
	{
		if(hasStarted)
		{
			godAura.update();
			if(!trickingGOD && !dyeing && !dyeingForShark && !dead && !tricking && !turningGOD){

				if(GodTime <=0)
					applyPhisics();

				
				
				CheckTurnGod();
				CheckTrick();
				SurferTail.update();

			}
			CheckDeath();
			UpdateAnimations();
			loadMagneticArea();
		}
		else
		{
			AnimateSwim();
			PuEffects.update();	
		}
	}


	private void AnimateSwim() {

		if(System.currentTimeMillis() - oldTime > FRAMESWIM)
		{

			switch (SwinIndex)
			{
			case 0: SwinIndex++; break;
			case 1: SwinIndex++; break;
			case 2: {

				if(!firstTouch)
					SwinIndex = 0;
				else
					SwinIndex++;
				;break;
			}
			case 3:
				hasStarted = true;break;
			}
			oldTime =(System.currentTimeMillis());
		}

	}
	private void CheckDirection() {
		if(touchingLeft || touchingRight)
		{
			if(TimeToWaitIndex > 0)
			TimeToWaitIndex -= TimeLoss;
		}
		if(!touchingLeft && !touchingRight)
			TimeToWaitSpeed += 0.1f;
		else
			TimeToWaitSpeed = 0;

		if(TimeToWaitIndex <= 0)
		{
			if(touchingLeft)
			{
				if(Index > 0)
				{
					Index--;
				}
				TimeToWaitIndex = DefaultTimeToWait;
			}

			if(touchingRight)
			{
				if(Index < Head.length/2-1)
				{
					Index++;
				}
				TimeToWaitIndex = DefaultTimeToWait;
			}
		}

	}
	private void UpdateAnimations()
	{
		UpdateGod();
		PuEffects.update();
		if(tricking)
		{
			UpdateTrickNoGod();
			CheckDirectionTrick();
		}
		if(dyeing)
		{
			UpdateDeath();

		}
		if(dyeingForShark)
		{
			UpdateSharkDeath();
		}

	}
	private void UpdateSharkDeath() {

		if(System.currentTimeMillis() - oldTime > FRAMEDEATH)
		{

			switch (DeathIndex)
			{
			case 0: visible=false; DeathIndex++; speaker.SharkBite(); break;
			case 1: DeathIndex++; break;
			case 2: DeathIndex++; break;
			case 3: dead = true; endgame.onGameEnded(coinsColected,(int)level.Distance,shellCount );break;

			}
			oldTime = (System.currentTimeMillis());
		}
	}
	private void UpdateGod() {
		if(turningGOD)
		{
			godMode.updateTurnGod();
		}
		if(trickingGOD)
		{
			godMode.UpdateStandGod();
		}
		if(godMode.doingTrick)
		{
			godMode.updateCurrentTrick();
		}
	}
	private void CheckDirectionTrick() {

		if(touchingLeft || touchingRight)
			TimeToWaitIndex -= TimeLoss;

		if(TimeToWaitIndex <= 0)
		{
			if(touchingLeft)
			{
				if(Index > 0)
				{
					Index--;
				}
				else Index = 17;

				TrickVector.add(Integer.valueOf(Index));
				TimeToWaitIndex = DefaultTimeToWait;
			}else 
				if(touchingRight)
				{
					if(Index < Head.length-1)
					{
						Index++;
					}
					else Index = 0;

					TrickVector.add(Integer.valueOf(Index));
					TimeToWaitIndex = DefaultTimeToWait;
				}
		}
	}
	private void UpdateDeath() {

		if(System.currentTimeMillis() - oldTime > FRAMEDEATH)
		{

			switch (DeathIndex)
			{
			case 0: DeathIndex++; speaker.DeathSplash(); break;
			case 1: DeathIndex++; visible=false; break;
			case 2: DeathIndex++; break;
			case 3: DeathIndex++; dead = true; endgame.onGameEnded(coinsColected,(int)level.Distance,shellCount );

			break;
			case 4: DeathIndex++; break;
			case 5: DeathIndex++; break;
			case 6: DeathIndex = 4; break;
			default:DeathIndex = 0;break;
			}
			oldTime =(System.currentTimeMillis());
		}

	}
	void Restart() {
		this.Position.top = water.FoamBottom;
		Str = 0;
		dead = false;
		trickingGOD = false;
		dyeing = false;
		doingTrick = false;
		CurAnimation = 0;
		Index = 8;
		speed= STARTSPEED;
		isGodMode = false;
		visible = true;
		turningGOD = false;
		DeathIndex = 0;
		dyeing = false;
		dyeingForShark = false;
		turningGOD = false;
		hasStarted = false;
		firstTouch = false;
		SwinIndex = 0;
		coinsColected = 0;
		EnergyCollected = 0;
		shellCount = 0;

	}
	private void CheckDeath() {
		if(Position.top + DrawableHeight/2 >= CanvasHeight)
			dyeing = true;

		if(!trickingGOD && doingTrick)
			dyeing = true;

	}
	private void UpdateTrickNoGod() {

		if (Str > 0f)
		{
			Str -= Gravity;
			Position.top -= Str;
			
		}
		else
		{
			
			if(Down <= MaxDown)
			{
			Down+=Gravity;
			}
			Position.top += Down ;
		}

		if(Position.top > water.FoamBottom && Str <=0)
		{
			tricking = false;
			Down = 0;
			Str = 0;
			if(Index == 7 )
			{
				if(oldSpeed-twoXFactor > STARTSPEED)
					speed = oldSpeed-oneXFactor;
			}
			else if(Index == 6)
			{
				if(oldSpeed-threeXFactor > STARTSPEED)
					speed = oldSpeed-twoXFactor;
			}
			else if(Index == 5)
				speed = STARTSPEED;

			else if(Index != 8 && Index != 9)
			{
				dyeing = true;
				speed = STARTSPEED;	
			}
			else
			{
				Index = 8;
				speed = oldSpeed;
			}

			CheckPointsFromTricks();


		}

	}
	private void CheckPointsFromTricks() {
		int TrickCount =0;
		TrickVector.removeAll(RemoveList);

		while(TrickVector.size() > 0)
		{
			boolean is180 = CheckNormal();
			if(is180){
				TrickCount++;
			}
			is180 = CheckInverse();

			if(is180){
				TrickCount++;
			}
			if(TrickCount > 0)
			{
				EnergyCollected += TrickCount * 2;
				level.manobra.DisplayMessageHead(String.valueOf(TrickCount * 180));
				 speaker.NormalTrick();
			}
		}

		
	}

	private boolean CheckInverse() {
		int Index;
		boolean is180 = true;
		Index = TrickVector.indexOf(2);
		if(Index == -1)
			is180 = false;
		else
			TrickVector.remove(Index);
		Index = TrickVector.indexOf(3);
		if(Index == -1)
			is180 = false;
		else
			TrickVector.remove(Index);
		Index = TrickVector.indexOf(4);
		if(Index == -1)
			is180 = false;
		else
			TrickVector.remove(Index);
		Index = TrickVector.indexOf(5);
		if(Index == -1)
			is180 = false;
		else
			TrickVector.remove(Index);

		return is180;
	}
	private boolean CheckNormal() {
		int Index;
		boolean is180 = true;
		Index = TrickVector.indexOf(15);
		if(Index == -1)
			is180 = false;
		else
			TrickVector.remove(Index);
		Index = TrickVector.indexOf(14);
		if(Index == -1)
			is180 = false;
		else
			TrickVector.remove(Index);
		Index = TrickVector.indexOf(13);
		if(Index == -1)
			is180 = false;
		else
			TrickVector.remove(Index);
		Index = TrickVector.indexOf(12);
		if(Index == -1)
			is180 = false;
		else
			TrickVector.remove(Index);

		return is180;
	}
	
	private void CheckTurnGod() {

		if(Position.top + DrawableHeight/2 < water.FoamBottom && isGodMode)
		{
			turningGOD = true;
		}
	}
	private void CheckTrick() {
		if(Position.top + DrawableHeight/2 < water.FoamBottom && !tricking && !turningGOD)
		{
			tricking = true;
			oldSpeed = speed;
			speed = 0;

			internalSpeed =  (oldSpeed);
			if(internalSpeed < 0)
				internalSpeed  = internalSpeed * -1;

			Str = (internalSpeed * trickFactor);

		}
	}

	private void LoadContent() {
		for(i=0; i<9; i++)
		{

			HeadID =  getAndroidDrawable("head" + 
					String.valueOf(HeadIndex) + String.valueOf(i));

			BodyID =  getAndroidDrawable("body" + 
					String.valueOf(BodyIndex) + String.valueOf(i));

			BoardID = getAndroidDrawable("board" + 
					String.valueOf(BoardIndex) + String.valueOf(i));

			Head[i] = BitmapFactory.decodeResource( _context.getResources(), HeadID);
			Body[i] = BitmapFactory.decodeResource( _context.getResources(), BodyID);
			Board[i] = BitmapFactory.decodeResource( _context.getResources(), BoardID);
		}

		for(i=0; i<4; i++)
		{

			HeadID =  getAndroidDrawable("head_idlestart_" + 
					String.valueOf(HeadIndex) + String.valueOf(i));

			BodyID =  getAndroidDrawable("body_idlestart_" + 
					String.valueOf(BodyIndex) + String.valueOf(i));

			BoardID = getAndroidDrawable("board_idlestart_" + 
					String.valueOf(BoardIndex) + String.valueOf(i));

			SwimmingHeadMap[i] = BitmapFactory.decodeResource( _context.getResources(), HeadID);
			SwimmingBodyMap[i] = BitmapFactory.decodeResource( _context.getResources(), BodyID);
			SwimmingBoardMap[i] = BitmapFactory.decodeResource( _context.getResources(), BoardID);
		}

		Board[9] = BitMapHelper.LoadFliped(Board[8]);
		Board[10] = BitMapHelper.LoadFliped(Board[7]);
		Board[11] = BitMapHelper.LoadFliped(Board[6]);
		Board[12] = BitMapHelper.LoadFliped(Board[5]);
		Board[13] = BitMapHelper.LoadFliped(Board[4]);
		Board[14] = BitMapHelper.LoadFliped(Board[3]);
		Board[15] = BitMapHelper.LoadFliped(Board[2]);
		Board[16] = BitMapHelper.LoadFliped(Board[1]);
		Board[17] = BitMapHelper.LoadFliped(Board[0]);

		Body[9] = BitMapHelper.LoadFliped(Body[8]);
		Body[10] = BitMapHelper.LoadFliped(Body[7]);
		Body[11] = BitMapHelper.LoadFliped(Body[6]);
		Body[12] = BitMapHelper.LoadFliped(Body[5]);
		Body[13] = BitMapHelper.LoadFliped(Body[4]);
		Body[14] = BitMapHelper.LoadFliped(Body[3]);
		Body[15] = BitMapHelper.LoadFliped(Body[2]);
		Body[16] = BitMapHelper.LoadFliped(Body[1]);
		Body[17] = BitMapHelper.LoadFliped(Body[0]);

		Head[9] = BitMapHelper.LoadFliped(Head[8]);
		Head[10] = BitMapHelper.LoadFliped(Head[7]);
		Head[11] = BitMapHelper.LoadFliped(Head[6]);
		Head[12] = BitMapHelper.LoadFliped(Head[5]);
		Head[13] = BitMapHelper.LoadFliped(Head[4]);
		Head[14] = BitMapHelper.LoadFliped(Head[3]);
		Head[15] = BitMapHelper.LoadFliped(Head[2]);
		Head[16] = BitMapHelper.LoadFliped(Head[1]);
		Head[17] = BitMapHelper.LoadFliped(Head[0]);




		FlingHorizontalLeftToRightTrick = BitmapFactory.decodeResource( _context.getResources(),_player.FlingHorizontalLeftToRight);
		FlingHorizontalRightToLeftTrick = BitmapFactory.decodeResource( _context.getResources(),_player.FlingHorizontalRightToLeft);
		FlingVerticalTopToBottomTrick = BitmapFactory.decodeResource( _context.getResources(),_player.FlingVerticalTopToBottom);
		FlingVerticalBottomToTopTrick = BitmapFactory.decodeResource( _context.getResources(),_player.FlingVerticalBottomToTop);

		DeathAnimation[0] = BitmapFactory.decodeResource(_context.getResources(), R.drawable.death_00);
		DeathAnimation[1] = BitmapFactory.decodeResource(_context.getResources(), R.drawable.death_01);
		DeathAnimation[2] = BitmapFactory.decodeResource(_context.getResources(), R.drawable.death_02);
		DeathAnimation[3] = BitmapFactory.decodeResource(_context.getResources(), R.drawable.death_03);
		DeathAnimation[4] = BitmapFactory.decodeResource(_context.getResources(), R.drawable.death_04);
		DeathAnimation[5] = BitmapFactory.decodeResource(_context.getResources(), R.drawable.death_05);
		DeathAnimation[6] = BitmapFactory.decodeResource(_context.getResources(), R.drawable.death_06);

		WordMap[0] = BitmapFactory.decodeResource( _context.getResources(),R.drawable.pressanywhere_00);
		WordMap[1] = BitmapFactory.decodeResource( _context.getResources(),R.drawable.pressanywhere_01);
		WordMap[2] = BitmapFactory.decodeResource( _context.getResources(),R.drawable.pressanywhere_02);
		WordMap[3] = BitmapFactory.decodeResource( _context.getResources(),R.drawable.pressanywhere_03);
		SharkDeathAnimation [0] = BitmapFactory.decodeResource( _context.getResources(),R.drawable.disappear_00);
		SharkDeathAnimation [1] = BitmapFactory.decodeResource( _context.getResources(),R.drawable.disappear_01);
		SharkDeathAnimation [2] = BitmapFactory.decodeResource( _context.getResources(),R.drawable.disappear_02);
		SharkDeathAnimation [3] = BitmapFactory.decodeResource( _context.getResources(),R.drawable.disappear_03);




	}
	public void applyPhisics() {
		CheckDirection();
		CheckDirectionSpeed();
		CheckSpeedTap();
		moveSurfer();

	}

	private void CheckSpeedTap() {

		if(TimeToWaitSpeed >= DefaultTimeToWaitSpeed)
		{
			if(speed > STARTSPEED)
				speed -= VELOCITYLOST;
		}
		else
		{
			if(speed < MaxSpeedNoGod)
				speed += VELOCITYGET;

		}
	}
	private void moveSurfer() {
		
			Position.top += (Direction * speed) ;
		
		
	}
	private void CheckDirectionSpeed() {
		switch (Index)
		{
		case 8: Direction = twoXFactor;break; //Going down maxx
		case 7: Direction = oneAndHalfXFactor;break; //Going diagonal down
		case 6: Direction = oneXFactor;break; //Going diagonal down less
		case 5: Direction = halfXFactor;break; //Going diagonal down less less
		case 4: Direction = 0f;break; //Going diagonal not moving
		case 3: Direction = -halfXFactor;break; //Going diagonal up
		case 2: Direction = -oneXFactor;break; //Going diagonal up more
		case 1: Direction = -oneAndHalfXFactor;break; //Going diagonal up more more
		case 0: Direction = -twoXFactor;break; //Going top maxx
		}
	}
	public void onTouch(MotionEvent event) {

		if(!firstTouch)
		{
			firstTouch = true;
			isGodMode = StorageInfo.storage.IsThereStartGod();
			if(StorageInfo.storage.IsThereStartBoat())
			{
				StorageInfo.storage.setStartBoat(false);
				level.GetOnBoat();
				hasStarted = true;
			}
			
			if(isGodMode)
				StorageInfo.storage.setStartGod(false);
			
		}
		touchingLeft = false;
		touchingRight = false;
		int PointerID = event.getPointerId(event.getPointerCount()-1);
		int PointerIndex = event.findPointerIndex(PointerID);
		if(PointerIndex != 0)
		{
			switch (event.getActionMasked())
			{    
			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_POINTER_DOWN:
			{
				if (event.getX(PointerIndex) >= CanvasWidth/2)
					touchingRight = true;
				else
					touchingLeft = true;
			}break; 

			case MotionEvent.ACTION_POINTER_UP:
			{
				if(event.getActionIndex() == PointerIndex)
				{

					TimeToWaitIndex = DefaultTimeToWait;
				}

			}break;
			}
		}
		else
		{
			switch (event.getAction())
			{
			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_DOWN:
			{
				if (event.getX() >= CanvasWidth/2)
					touchingRight = true;
				else
					touchingLeft = true;
			}break;

			case MotionEvent.ACTION_POINTER_UP:
			{

				TimeToWaitIndex = DefaultTimeToWait;

			}break;
			}
		}
	}
	private void DrawSurfer(Canvas canvas) {
		canvas.drawBitmap(Board[Index],Position.left,Position.top,null);
		canvas.drawBitmap(Body[Index],Position.left,Position.top,null);
		canvas.drawBitmap(Head[Index],Position.left,Position.top,null);
	}
	public void draw(Canvas canvas) {

		if(!hasStarted)
		{
			PuEffects.draw(canvas);
			DrawSwin(canvas);
		}
		else if(turningGOD)
		{
			godMode.drawTurning(canvas);
		}
		else if(trickingGOD && isGodMode)
		{
			godMode.drawTrickGod(canvas);
			//drawTrickGod(canvas);
		}
		else
		{
			if(visible)
			{
				PuEffects.draw(canvas);
				DrawTail(canvas);
				godAura.draw(canvas);
				DrawSurfer(canvas);
				
			}
		}
		if(dyeing)
		{
			drawDying(canvas);
		}
		if(dyeingForShark)
		{
			drawDyingShark(canvas);

		}
		//DrawSpeed(canvas);
		//DrawBounding(canvas);

	}
	private void drawDyingShark(Canvas canvas) {
		canvas.drawBitmap(SharkDeathAnimation[DeathIndex],Position.left,Position.top,null);
	}
	private void DrawSwin(Canvas canvas) {
		canvas.drawBitmap(SwimmingBoardMap[SwinIndex],Position.left,Position.top,null);
		canvas.drawBitmap(SwimmingBodyMap[SwinIndex],Position.left,Position.top,null);
		canvas.drawBitmap(SwimmingHeadMap[SwinIndex],Position.left,Position.top,null);

		canvas.drawBitmap(WordMap[SwinIndex],(CanvasWidth/2) - WordMap[SwinIndex].getWidth()/2,CanvasHeight/2 + WordMap[SwinIndex].getHeight() ,null);
	}
	private void drawDying(Canvas canvas) {
		canvas.drawBitmap(DeathAnimation[DeathIndex],Position.left,Position.top,null);

	}

	private void DrawTail(Canvas canvas) {
		SurferTail.draw(canvas,this);

	}
	private int getAndroidDrawable(String pDrawableName){
		int resourceId=_context.getResources().getIdentifier(pDrawableName, "drawable", _context.getPackageName());
		if(resourceId==0){
			return -1;
		} else {
			return resourceId;
		}
	}
	public RectF BoudingRecTF()
	{
		BoudingRecTF.left = Position.left + (DrawableHeight * 0.30f);
		BoudingRecTF.top = Position.top + (DrawableHeight* 0.10f);
		BoudingRecTF.right = (Position.left + DrawableHeight) - DrawableHeight * 0.30f ;
		BoudingRecTF.bottom = Position.top + (DrawableHeight) - DrawableHeight * 0.15f;
		return BoudingRecTF;
	}
	public RectF BoundingRectBoard()
	{
		left = 0;
		top = 0;
		if(Index == 0)
		{
			left = Position.left + DrawableWidth * 0.37f;
			top = Position.top + (DrawableHeight * 0.95f);
		}
		if(Index == 1)
		{
			left = Position.left + DrawableWidth * 0.30f;
			top = Position.top + (DrawableHeight * 0.95f);
		}
		if(Index == 2)
		{
			left = Position.left + DrawableWidth * 0.13f;
			top = Position.top + (DrawableHeight * 0.84f);
		}
		if(Index == 3)
		{
			left = Position.left;
			top = Position.top + (DrawableHeight * 0.80f);
		}
		if(Index == 4)
		{
			left = Position.left;
			top = Position.top + (DrawableHeight * 0.65f);
		}
		if(Index == 5)
		{
			left = Position.left;
			top = Position.top + (DrawableHeight * 0.45f);
		}
		if(Index == 6)
		{
			left = Position.left +( DrawableWidth * 0.1f);
			top = Position.top + (DrawableHeight * 0.3f);
		}
		if(Index == 7)
		{
			left = Position.left +( DrawableWidth * 0.25f);
			top = Position.top + (DrawableHeight * 0.2f);
		}
		BoundingRectBoard.left = left;
		BoundingRectBoard.top = top;
		return BoundingRectBoard;

	}


	public void onFlingHorizontalLeftToRight(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY) {

		if(!doingTrick && trickingGOD && isGodMode)
		{
			godMode.TrickToPerform = FlingHorizontalLeftToRightTrick;
			godMode.TheTrick = "LeftToRight";
			godMode.doingTrick = true;
			godMode.timeTrick =(System.currentTimeMillis());
		}

	}
	public void onFlingHorizontalRightToLeft(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY) {
		if(!godMode.doingTrick && trickingGOD && isGodMode)
		{
			godMode.TrickToPerform = FlingHorizontalRightToLeftTrick;
			godMode.TheTrick = "RightToLeft";
			godMode.doingTrick = true;
			godMode.timeTrick =(System.currentTimeMillis());
		}

	}
	public void onFlingVerticalTopToBottom(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY) {
		if(!godMode.doingTrick && trickingGOD && isGodMode)
		{
			godMode.TrickToPerform = FlingVerticalTopToBottomTrick;
			godMode.TheTrick = "TopToBottom";
			godMode.doingTrick = true;
			godMode.timeTrick =(System.currentTimeMillis());
		}

	}
	public void onFlingVerticalBottomToTop(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY) {
		if(!godMode.doingTrick && trickingGOD && isGodMode)
		{
			godMode.TrickToPerform = FlingVerticalBottomToTopTrick;
			godMode.TheTrick = "BottomToTop";
			godMode.doingTrick = true;
			godMode.timeTrick = (System.currentTimeMillis());
		}
	}
	public RectF loadMagneticArea (){
		magneticArea = BoudingRecTF();
		resultFactor = PowerUps.magneticFactor*screenFactor;
		magneticArea.left-=resultFactor;
		magneticArea.right+=resultFactor;
		magneticArea.top-=resultFactor;
		magneticArea.bottom+=resultFactor;
		return magneticArea;
	}
}
