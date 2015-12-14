package net.game;

import net.surf.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

@SuppressLint("UseSparseArrays")
public class Speaker {
	private static final long FRAMERATEANIMATION = 100;
	SoundPool soundPool;
	int[] soundPoolMap = new int[9];
	Context context;
	AudioManager audioManager;
	float curVolume;
	float maxVolume;
	float leftVolume;
	float rightVolume;
	int priority;
	int no_loop;
	float normal_playback_rate;
	boolean IsSFXMuted = false;
	boolean isMusicMuted = false;
	MediaPlayer themePlay;
	MediaPlayer wavePlay;
	private long oldTime;
	Integer aff;
	public Speaker(Context context)
	{
		this.context = context;
		LoadContent();
	}
	private void LoadContent() {
		audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		((Activity) context).setVolumeControlStream(AudioManager.STREAM_MUSIC);
		soundPool = new SoundPool(20, AudioManager.STREAM_RING, 100);
		
		curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		leftVolume = (curVolume/maxVolume);
		rightVolume = (curVolume/maxVolume);
		
		soundPoolMap[0] = soundPool.load(context, R.raw.coin_collect,1);
		aff = soundPool.load(context, R.raw.coin_collect,1);
		soundPoolMap[1] = soundPool.load(context, R.raw.cheer1,1);
		soundPoolMap[2] = soundPool.load(context, R.raw.sound_bird,1);
		soundPoolMap[3] = soundPool.load(context, R.raw.sound_shark,1);
		soundPoolMap[4] = soundPool.load(context, R.raw.sound_splash,1);
		soundPoolMap[5] = soundPool.load(context, R.raw.sound_swoosh,1);
		soundPoolMap[6] = soundPool.load(context, R.raw.noluck,1);
		soundPoolMap[7] = soundPool.load(context, R.raw.luck,1);
		soundPoolMap[8] = soundPool.load(context, R.raw.start_boat,1);
		
		
		priority = 1;
		no_loop = 0;
		normal_playback_rate = 1f;


		themePlay = MediaPlayer.create(this.context, R.raw.theme);
		themePlay.setLooping(true);
		themePlay.start();
		
		wavePlay = MediaPlayer.create(this.context, R.raw.wave_sound);
		wavePlay.setLooping(true);
		wavePlay.setVolume(leftVolume/5, rightVolume/5);
		wavePlay.start();
	}
	public void startWave (){
		wavePlay.start();
	}
	public void stopWave (){
		wavePlay.pause();
	}
	public void StartEngine (){
		PlaySound((Integer)soundPoolMap[8]);
	}
	public void NoLuck()
	{
		PlaySound((Integer)soundPoolMap[6]);
	}
	public void Luck()
	{
		PlaySound((Integer)soundPoolMap[7]);
	}
	public void GodTrickStart()
	{
		PlaySound((Integer)soundPoolMap[5]);
	}
	public void DeathSplash()
	{
		PlaySound((Integer)soundPoolMap[4]);
	}
	public void SharkBite()
	{
		PlaySound((Integer)soundPoolMap[3]);
	}
	public void CoinCollect()
	{
		PlaySound((Integer)soundPoolMap[0]);
	}
	public void NormalTrick()
	{
		PlaySound((Integer)soundPoolMap[1]);
	}
	public void BirdSound()
	{
		PlaySound((Integer)soundPoolMap[2]);
	}
	private void PlaySound(Integer ID) {

		if(!IsSFXMuted)
		{
			curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			leftVolume = (curVolume/maxVolume);
			rightVolume = (curVolume/maxVolume);
			soundPool.play(ID, leftVolume, rightVolume, priority, no_loop, normal_playback_rate);
		}

	}
	public void MuteSFX()
	{
		IsSFXMuted = true;
		wavePlay.pause();
	}
	public void UnMuteSFX()
	{
		IsSFXMuted = false;		
		wavePlay.start();
	}
	public void MuteMusic()
	{
		isMusicMuted = true;
		themePlay.pause();
	}
	public void UnMuteMusic()
	{
		themePlay.start();		
		isMusicMuted = false;
	}
	public void Release() {
		themePlay.release();
		wavePlay.release();
	}
	public void StopMusic()
	{
		themePlay.pause();
		wavePlay.pause();
	}
	public void update()
	{
		
		if(System.currentTimeMillis() - oldTime > FRAMERATEANIMATION)
		{
			if(!IsSFXMuted)
			{
			soundPool.play(aff, 0, 0, priority, no_loop, normal_playback_rate);
			}
			oldTime = (System.currentTimeMillis());
		}
		
	}

}
