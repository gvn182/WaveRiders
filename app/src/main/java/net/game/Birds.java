package net.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.surf.R;
import Util.PatternsHelper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Birds {
	private float timeWaited = 0;
	float waitTime;
	final int range = 1000;
	final int minRange = 500;
	int Index = 0;
	public List<Bird> birds = new ArrayList<Bird>();
	final int maxBirds = 5;
	Context _context;
	int CanvasHeight;
	int CanvasWidth;
	public Bitmap texture;
	public Bitmap BirdHit;
	Level _level;
	Surfer surfer;
	Random r;
	int i;
	int k;
	int PatternHeight;
	int rndNumber;
	int CurHeight;
	int CurWith;
	char character;
	static final String key = "birdspattern";
	boolean playedSound = false;
	public Birds (Context cont,int Height, int Width, Surfer surfer){
		CanvasHeight = Height;
		CanvasWidth = Width;
		_context = cont;
		LoadResources();
		this.surfer = surfer;
		r = new Random();
		startBirds ();
	}
	
	public void startBirds (){
		for (i=0; i<5; i++){
			birds.add(new Bird(_context, CanvasHeight, CanvasWidth, new RectF(-100f,-100f,-100f,100f), texture, BirdHit, surfer));
		}
	}

	private void LoadResources() {
		texture = BitmapFactory.decodeResource( _context.getResources(), R.drawable.bird_flying);
		BirdHit = BitmapFactory.decodeResource( _context.getResources(), R.drawable.bird_hit);
	}

	private void checkTime() {
		if(timeWaited  >= waitTime)
		{
			RandomPattern();
			timeWaited = 0;
		}
		else
		{
			timeWaited+=1f;
		}
	}

	private void RandomPattern() {
		rndNumber = r.nextInt(2) + 1;
		ArrayList<String> linhas =  PatternsHelper.getPattern(key+String.valueOf(rndNumber));
		playedSound = false;
		//PatternHeight = (linhas.size()*texture.getHeight());

		//CurHeight =  (CanvasHeight - PatternHeight)/2;


		for (i=0; i< linhas.size(); i++)
		{
			String linha = linhas.get(i); 
			CurWith = CanvasWidth;
			for(k=0; k<linha.length(); k++)
			{
				character = linha.charAt(k);
				if(character == '0')
				{
					CurWith += texture.getHeight();
				}
				else
				{
					CurWith += texture.getHeight();
					birds.get(Index).Dest.left = CurWith + CanvasWidth;
					birds.get(Index).Dest.right = birds.get(Index).Dest.left + birds.get(Index).BirdMap.getHeight();
					birds.get(Index).Dest.top = CurHeight;
					birds.get(Index).Dest.bottom = CurHeight + birds.get(Index).BirdMap.getHeight();
					birds.get(Index).isGone = false;
					
					//birds.add(new Bird(_context, CanvasHeight, CanvasWidth, pos,texture,BirdHit));					
					
					if(Index < birds.size() - 1)
					{
						Index++;
					}
					else
					{
						Index = 0;

					}
				}
			}
			CurHeight += texture.getHeight();
		}
	}

	public void update() {
		if(timeWaited == 0)
		{
			waitTime = r.nextInt(range) + minRange;
					
		}
		checkTime();

		for (i=0; i< birds.size(); i++)
		{
			Bird bird = birds.get(i); 
			bird.update();
		}
	}

	public void draw(Canvas canvas) {
		for (i=0; i< birds.size(); i++)
		{
			Bird bird = birds.get(i); 
			bird.draw(canvas);
		}
	}

	public void Restart() {
		for (i=0; i< birds.size(); i++)
		{
			Bird bird = birds.get(i);
			//manda pro inferno.
			bird.Dest.left-= 10000;
			bird.Dest.right-= 10000;
		}
		
	}
}
