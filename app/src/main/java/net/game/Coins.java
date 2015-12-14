package net.game;

import java.util.ArrayList;
import java.util.Random;

import net.surf.R;
import Util.PatternsHelper;
import Util.StorageInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Coins {

	private float timeWaited = 0;
	float waitTime = 25f;
	int coins = 100;
	int allaa = 0;
	int Index = 0;
	Context _context;
	int CanvasHeight;
	int CanvasWidth;
	public Bitmap[] texture = new Bitmap[3];
	RectF[] Pos; 
	boolean[] isGoneList;
	boolean[] collectedList;
	Level _level;
	Surfer surfer;
	Random r;
	int i;
	int k;
	int CurWith;
	char character;
	private int theRandom;
	boolean isTwoXCoinPowerUp = false;
	private static final int FRAMERATEANIMATION = 120;
	private long oldTime;
	RectF BoudingRecTF;
	public Coins(Context cont,int Height, int Width, Level level,Surfer mySurfer)
	{
		CanvasHeight = Height;
		CanvasWidth = Width;
		_context = cont;
		
		_level = level;
		surfer = mySurfer;
		isTwoXCoinPowerUp = PowerUps.isThereThePowerUp(PowerUps.TwoXCoinPowerUp);
		BoudingRecTF = new RectF(0,0,0,0);
		if(!isTwoXCoinPowerUp)
			isTwoXCoinPowerUp = StorageInfo.storage.IsThere2xCoin();
		
		if(StorageInfo.storage.IsThere2xCoin())
			StorageInfo.storage.setDoubleCoinsBonuts(false);

		LoadResources();
		StartCoins();
		r = new Random();
		RandomPattern();
	}
	private void StartCoins() {
		isGoneList = new boolean[coins];
		collectedList = new boolean[coins];
		Pos = new RectF[coins];
		for (i=0; i< coins-1; i++)
		{
			isGoneList[i] = true;
			collectedList[i] = false;
			Pos[i] = new RectF(-100,0,0,0);
		}

	}
	private void LoadResources() {
		if(isTwoXCoinPowerUp)
		{
			texture[0] = BitmapFactory.decodeResource( _context.getResources(), R.drawable.darkcoin0);
			texture[1] = BitmapFactory.decodeResource( _context.getResources(), R.drawable.darkcoin1);
			texture[2] = BitmapFactory.decodeResource( _context.getResources(), R.drawable.darkcoin2);
		}
		else
		{
			texture[0] = BitmapFactory.decodeResource( _context.getResources(), R.drawable.coin_00);
			texture[1] = BitmapFactory.decodeResource( _context.getResources(), R.drawable.coin_01);
			texture[2] = BitmapFactory.decodeResource( _context.getResources(), R.drawable.coin_02);
		}
	}
	private void checkTime() {
		if(timeWaited >= waitTime)
		{
			r = new Random();
			RandomPattern();
			timeWaited = 0;
		}
		else
		{
			timeWaited+=0.1f;
		}
	}
	private void RandomPattern() {
		theRandom = r.nextInt(9) + 1;
		String key = "coinpattern" + String.valueOf(theRandom);
		ArrayList<String> linhas =  PatternsHelper.getPattern(key);

		int PatternHeight = (linhas.size()*texture[0].getHeight());
		int CurHeight =  (CanvasHeight - PatternHeight)/2;


		for (i=0; i< linhas.size(); i++)
		{
			String linha = linhas.get(i); 
			CurWith = CanvasWidth;
			for(k=0; k<linha.length()-1; k++)
			{
				character = linha.charAt(k);
				if(character == '0')
				{
					CurWith += texture[0].getWidth();
				}
				else
				{
					CurWith += texture[0].getWidth();
					if (Pos[Index].left <= 0){
						Pos[Index].left = CurWith;
						Pos[Index].top = CurHeight;
						isGoneList[Index] = false;
						collectedList[Index] = false;
					}
					if(Index < coins-2)
					{
						Index++;
					}
					else
					{
						Index = 0;

					}
				}
			}
			CurHeight += texture[0].getHeight();
		}
		Index=0;
	}
	public void update() {
		checkTime();

		for (i=0; i< coins-1; i++)
		{
			if(!isGoneList[i])
			{
				if(!collectedList[i])
				{
					Pos[i].left -= surfer.speed;
				}
				else
				{
					isGoneList[i] = true;
				}
				
				if(Pos[i].left + texture[0].getWidth() < 0)
					isGoneList[i] = true;
				
				if(collectedList[i])
				{
					surfer.coinsColected+=(isTwoXCoinPowerUp? PowerUps.coinsFactor : 1);
					collectedList[i] = false;
					collectedList[i] = true;
				}
			}
		}
		
		if(System.currentTimeMillis() - oldTime > FRAMERATEANIMATION)
		{
			if(allaa == 2)
				allaa = 0;
			else allaa++;
			
			oldTime = (System.currentTimeMillis());
		}
		
	}
	public void draw(Canvas canvas) {

		for (i=0; i< coins-1; i++) {
			canvas.drawBitmap(texture[allaa], Pos[i].left, Pos[i].top, null);
		}
		
	}
	public void Restart() {
		StartCoins();
		if(!isTwoXCoinPowerUp)
		{
			isTwoXCoinPowerUp = StorageInfo.storage.IsThere2xCoin();

			if(StorageInfo.storage.IsThere2xCoin())
			StorageInfo.storage.setDoubleCoinsBonuts(false);
		}
			
	}
	
	public void OnCollect(int pos) {
		collectedList[pos] = true;
		Pos[pos].left = -3000;
	}

	public RectF BoudingRecTF(int pos)
	{
		BoudingRecTF.left = (Pos[pos].left);
		BoudingRecTF.top = Pos[pos].top;
		BoudingRecTF.right = (Pos[pos].left + texture[0].getWidth());
		BoudingRecTF.bottom = (Pos[pos].top + texture[0].getHeight());
		return BoudingRecTF;

	}
	
}
