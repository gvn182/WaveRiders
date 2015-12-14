package Util;

import android.graphics.RectF;

public final class MoveHelper {
	 public final static RectF MoveTowards(RectF position, RectF target, float speed)
     {
		 double direction = (float)(Math.atan2(target.top - position.top, target.left - position.left) * 180 / Math.PI);

         RectF move = new RectF(0, 0,0,0);

         move.left = (float)Math.cos(direction * Math.PI / 180) * speed;
         move.top = (float)Math.sin(direction * Math.PI / 180) * speed;

         return move;
     }
	 
	 public final static Float deltaDistance (RectF position1, RectF position2){
		 Float result = Math.abs(position1.left - position2.left); 
		 return result;
	 }
	 
}
