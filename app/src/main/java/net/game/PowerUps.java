package net.game;

import java.util.HashMap;

import net.surf.R;
import Util.StorageInfo;
import android.annotation.SuppressLint;

@SuppressLint("UseSparseArrays")
public final class PowerUps {
	public static final int NoPowerUp = 0;
	public static final int CoinMagnetPowerUp = 1;
	public static final int FeatherPowerUp = 2;
	public static final int TwoXCoinPowerUp = 3;
	public static final int TwoXFasterPowerUp = 4;
	public static final int BirdRepelentPowerUp = 5;
	public static final int SharkRepelentPowerUp = 6;
	public static final int WaterRepelent = 7;
	public static final int ArrowPowerUp = 8;
		
	public static final int coinsFactor = 2;
	public static final float speedFatcor = 2f;
	public static final float gravityFactor = 0.9f;
	public static final float magneticFactor = 50f;
	public static final float FasterArrow = 1.7f;
	
	private static int[] bitMapFeatherList = {R.drawable.fx_feather0, R.drawable.fx_feather1, R.drawable.fx_feather2};
	private static int[] bitMapMagneticList = {R.drawable.fx_magnet_0, R.drawable.fx_magnet_1, R.drawable.fx_magnet_2};
	private static int[] bitMap2xSpeedList = {R.drawable.fx_aurablue_0, R.drawable.fx_aurablue_1, R.drawable.fx_aurablue_2,R.drawable.fx_aurablue_3};
	private static HashMap<Integer, int[]> PowerUpEffectsMap = getPowerUpEffectsMap ();
	
	private static HashMap<Integer, int[]> getPowerUpEffectsMap (){
		HashMap<Integer, int[]> map = new  HashMap<Integer, int[]>();
		map.put(FeatherPowerUp, bitMapFeatherList);
		map.put(CoinMagnetPowerUp, bitMapMagneticList);
		map.put(TwoXFasterPowerUp, bitMap2xSpeedList);
		return map;
	}
	
	public static boolean isThereThePowerUp (int powerUp){
		return StorageInfo.storage.getMyEquipedPowerUp().equals(String.valueOf(powerUp));
	}
	
	public static int[] getPowerUpEffectsTextures (){
		int[] maps =  PowerUpEffectsMap.get(Integer.decode(StorageInfo.storage.getMyEquipedPowerUp()));
		return maps;
	}
}
