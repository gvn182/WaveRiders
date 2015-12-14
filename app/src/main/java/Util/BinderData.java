package Util;

import java.util.HashMap;
import java.util.List;

import net.surf.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BinderData extends BaseAdapter {

	// XML node keys
    static final String KEY_ID = "id";
    static final String KEY_ITEMNAME = "name";
    static final String KEY_ITEMVALUE = "value";
    static final String KEY_ICON = "icon";
    static final String KEY_BUY = "buyequip";
	
	LayoutInflater inflater;
	ImageView thumb_image;
	List<HashMap<String,String>> weatherDataCollection;
	ViewHolder holder;
	public BinderData() {
		// TODO Auto-generated constructor stub
	}
	
	public BinderData(Activity act, List<HashMap<String,String>> map) {
		
		this.weatherDataCollection = map;
		
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	public int getCount() {
		// TODO Auto-generated method stub
//		return idlist.size();
		return weatherDataCollection.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		 
		View vi=convertView;
	    if(convertView==null){
	     
	      vi = inflater.inflate(R.layout.list_row, null);
	      holder = new ViewHolder();
	     
	      holder.ItemImage =(ImageView)vi.findViewById(R.id.list_image); // thumb image
	      holder.ItemName = (TextView)vi.findViewById(R.id.tvItemName); // city name
	      holder.ItemValue = (TextView)vi.findViewById(R.id.tvItemValue); // city weather overview
	      
	      holder.buyEquipButton = (ImageView)vi.findViewById(R.id.ImgBuyEquip); // thumb image
	      vi.setTag(holder);
	    }
	    else{
	    	
	    	holder = (ViewHolder)vi.getTag();
	    }

	      // Setting all values in listview
	      
	      holder.ItemName.setText(weatherDataCollection.get(position).get(KEY_ITEMNAME));
	      holder.ItemValue.setText(weatherDataCollection.get(position).get(KEY_ITEMVALUE));
	      
	      //Setting an image
	      String uri = "drawable/"+ weatherDataCollection.get(position).get(KEY_ICON);
	      int imageResource = vi.getContext().getApplicationContext().getResources().getIdentifier(uri, null, vi.getContext().getApplicationContext().getPackageName());
	      Drawable image = vi.getContext().getResources().getDrawable(imageResource);
	      holder.ItemImage.setImageDrawable(image);
	      
	      
	     
	      uri = "drawable/"+ weatherDataCollection.get(position).get(KEY_BUY);
	      int imageResourceBuy = vi.getContext().getApplicationContext().getResources().getIdentifier(uri, null, vi.getContext().getApplicationContext().getPackageName());
	      Drawable imageBuy = vi.getContext().getResources().getDrawable(imageResourceBuy);
	      holder.buyEquipButton.setImageDrawable(imageBuy);
	      return vi;
	}
	
	/*
	 * 
	 * */
	static class ViewHolder{
		
		TextView ItemName;
		ImageView buyEquipButton;
		TextView ItemValue;
		ImageView ItemImage;
	}
	
}
