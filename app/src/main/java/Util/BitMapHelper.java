package Util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitMapHelper {
	public static Bitmap LoadFliped(Bitmap src) {
	    Matrix matrix = new Matrix();
	    matrix.preScale(-1.0f, 1.0f);

	    return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
	}
	public static Bitmap LoadFlipedVertical(Bitmap src) {
	    Matrix matrix = new Matrix();
	    matrix.preScale(1.0f, -1.0f);
	    return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
	}
}
