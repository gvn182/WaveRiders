package Util;

import net.surf.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CustomDialogClass extends Dialog {

	public Activity c;
	public Dialog d;
	public ImageButton yes, no;
	public String item_name;
	public String image;
	public String item_value;
	public String ID;
	public int Index = 0;
	public CustomDialogClass(Activity a) {
		super(a);
		this.c = a;
	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog);


	

	}
	public void SetContent()
	{
		ImageView imgItem = (ImageView) findViewById(R.id.imgConfirmItem);
		String uri = "drawable/"+ image;
		int imageResource = c.getApplicationContext().getResources().getIdentifier(uri, null, c.getApplicationContext().getPackageName());
		imgItem.setImageResource(imageResource);
		
		MyTextView ItemText = (MyTextView) findViewById(R.id.txtItemNameItem);
		ItemText.setText(item_name);

		MyTextView ItemValue = (MyTextView) findViewById(R.id.txtItemValueItem);
		ItemValue.setText(item_value);
	
		yes = (ImageButton) findViewById(R.id.btnItemConfirm);
		no = (ImageButton) findViewById(R.id.btnItemCancel);
	}
}
