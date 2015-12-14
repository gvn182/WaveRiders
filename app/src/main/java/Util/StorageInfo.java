package Util;

import android.app.Activity;

public abstract class StorageInfo {
	public static StorageInfoHelper storage;
	public static void getInstance (Activity context){		
		storage = new StorageInfoHelper(context);
		//storage.reset();
	}
	
}
