package Util;

import android.os.Debug;

public class HeapLog {

	
	private static double allocated;
	private static double available;
	private static double free;
	public HeapLog()
	{
		
	}
	
	
	public String logMemory() {
         allocated = Debug.getNativeHeapAllocatedSize()/(1048576);
         available = Debug.getNativeHeapSize()/1048576.0;
         free = Debug.getNativeHeapFreeSize()/1048576.0;

        return  ("memory allocated: " + Runtime.getRuntime().totalMemory()/1048576 + "MB of " + Runtime.getRuntime().maxMemory()/1048576+ "MB (" + Runtime.getRuntime().freeMemory()/1048576 + "MB free)");
        
//        Log.d("tag", "debug. =================================");
//        Log.d("tag", "debug.heap native: allocated " + df.format(allocated) + "MB of " + df.format(available) + "MB (" + df.format(free) + "MB free)");
//        Log.d("tag", "debug.memory: allocated: " + df.format(new Double(Runtime.getRuntime().totalMemory()/1048576)) + "MB of " + df.format(new Double(Runtime.getRuntime().maxMemory()/1048576))+ "MB (" + df.format(new Double(Runtime.getRuntime().freeMemory()/1048576)) +"MB free)");
    }

	public String logNativeHeap() {
		allocated = Debug.getNativeHeapAllocatedSize()/(1048576);
        available = Debug.getNativeHeapSize()/1048576.0;
        free = Debug.getNativeHeapFreeSize()/1048576.0;
      

        return  ("heap allocated " +allocated + "MB of " +available + "MB (" + free + "MB free)");
        
	}
	
}
