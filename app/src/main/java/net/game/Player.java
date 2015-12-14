package net.game;

import Util.StorageInfo;
import android.content.Context;

public class Player {
	public int Head,Body,Board,Tail;
	public int FlingHorizontalLeftToRight;
	public int FlingHorizontalRightToLeft;
	public int FlingVerticalTopToBottom;
	public int FlingVerticalBottomToTop;
	public int FlingVerticalBottomToTopCoin;
	public int FlingHorizontalLeftToRightCoin;
	public int FlingHorizontalRightToLeftCoin;
	public int FlingVerticalTopToBottomCoin;
	String EquipedTricks;
	Context context;
	public Player(Context context)
	{
		this.context = context;
		EquipedTricks = StorageInfo.storage.getMyEquipedTrick();
		Head = Integer.decode(StorageInfo.storage.getMyEquipedHead().replace(",",""));
		Body = Integer.decode(StorageInfo.storage.getMyEquipedBody().replace(",",""));
		Board = Integer.decode(StorageInfo.storage.getMyEquipedBoard().replace(",",""));
		Tail = 0;
		FlingHorizontalLeftToRight =  getAndroidDrawable("surfergod_" + EquipedTricks.split(",")[1]);
		FlingHorizontalLeftToRightCoin = 10;
		FlingHorizontalRightToLeft = getAndroidDrawable("surfergod_" + EquipedTricks.split(",")[2]);
		FlingHorizontalRightToLeftCoin = 15;
		FlingVerticalTopToBottom = getAndroidDrawable("surfergod_" + EquipedTricks.split(",")[3]);
		FlingVerticalTopToBottomCoin = 20;
		FlingVerticalBottomToTop = getAndroidDrawable("surfergod_" + EquipedTricks.split(",")[4]);
		FlingVerticalBottomToTopCoin = 25;
	}
	private int getAndroidDrawable(String pDrawableName){
		int resourceId=context.getResources().getIdentifier(pDrawableName, "drawable", context.getPackageName());
		if(resourceId==0){
			return -1;
		} else {
			return resourceId;
		}
	}
}
