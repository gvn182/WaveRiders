package Util;

import net.surf.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;

public class CustomDialogCoins extends Dialog {

	public Activity c;
	public Dialog d;
	public ImageButton yes, no;

	public CustomDialogCoins(Activity a) {
		super(a);
		this.c = a;
	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialo_coins);

	
		yes = (ImageButton) findViewById(R.id.btnItemConfirmCoin);
		no = (ImageButton) findViewById(R.id.btnItemCancelCoin);

	}
}
