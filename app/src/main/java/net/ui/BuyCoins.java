package net.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.surf.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Util.BinderData;
import Util.BinderDataCoin;
import Util.StorageInfo;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.vending.billing.IInAppBillingService;

public class BuyCoins extends Activity implements OnClickListener{
	static final String KEY_TAG = "itemdata"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_ICON = "icon";
	static final String KEY_ITEMNAME = "name";
	static final String KEY_ITEMVALUE = "value";
	static final String KEY_BUY = "buyequip";
	String Asset = "";
	String ID = "";
	IInAppBillingService mService;

	ListView list;
	BinderData adapter = null;
	List<HashMap<String,String>> ItemDataCollection;
	private ServiceConnection mServiceConn;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		setContentView(R.layout.buycoins_layout);

		ImageButton btnBack = (ImageButton) findViewById(R.id.BtnBack);
		btnBack.setOnClickListener(this);

		SetCoinsPlayer();
		Asset = "Coin.xml";
		PopulateList();




	}



	private void SetCoinsPlayer() {
		Util.MyTextView imgView = (Util.MyTextView) findViewById(R.id.txtCoins);	   
		imgView.setText(StorageInfo.storage.getMyGold());
	}
	private void PopulateList() {

		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;

			docBuilder = docBuilderFactory.newDocumentBuilder();

			Document doc = docBuilder.parse (getAssets().open(Asset));

			ItemDataCollection = new ArrayList<HashMap<String,String>>();

			// normalize text representation
			doc.getDocumentElement ().normalize ();

			NodeList itemList = doc.getElementsByTagName("itemdata");

			HashMap<String,String> map = null;
			for (int i = 0; i < itemList.getLength(); i++) {

				map = new HashMap<String,String>(); 

				Node firstWeatherNode = itemList.item(i);

				if(firstWeatherNode.getNodeType() == Node.ELEMENT_NODE){

					Element firstWeatherElement = (Element)firstWeatherNode;
					//-------
					NodeList idList = firstWeatherElement.getElementsByTagName(KEY_ID);
					Element firstIdElement = (Element)idList.item(0);
					NodeList textIdList = firstIdElement.getChildNodes();
					//--id
					map.put(KEY_ID, ((Node)textIdList.item(0)).getNodeValue().trim());

					NodeList iconList = firstWeatherElement.getElementsByTagName(KEY_ICON);
					Element firstIconElement = (Element)iconList.item(0);
					NodeList textIconList = firstIconElement.getChildNodes();
					map.put(KEY_ICON, ((Node)textIconList.item(0)).getNodeValue().trim());

					NodeList itemName = firstWeatherElement.getElementsByTagName(KEY_ITEMNAME);
					Element firstItemList = (Element)itemName.item(0);
					NodeList textCityList = firstItemList.getChildNodes();
					//--city
					map.put(KEY_ITEMNAME, ((Node)textCityList.item(0)).getNodeValue().trim());


					//4.-------
					NodeList valueList = firstWeatherElement.getElementsByTagName(KEY_ITEMVALUE);
					Element firstValueElement = (Element)valueList.item(0);
					NodeList textCondList = firstValueElement.getChildNodes();
					//--city
					map.put(KEY_ITEMVALUE, ((Node)textCondList.item(0)).getNodeValue().trim());


					map.put(KEY_BUY, "shop_item_buy");
					//Add to the Arraylist
					ItemDataCollection.add(map);
				}
			} 
			BinderDataCoin bindingData = new BinderDataCoin(this,ItemDataCollection);

			list = (ListView) findViewById(R.id.list_coins);

			Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");

			list.setAdapter(bindingData);

			Log.i("AFTER", "<<------------- After SetAdapter-------------->>");

			// Click event for single list row
			list.setOnItemClickListener(new OnItemClickListener() {


				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {


					ID =  ItemDataCollection.get(position).get(KEY_ID);
					Buy();
					//					String Name =  ItemDataCollection.get(position).get(KEY_ITEMNAME);
					//					String Value =  ItemDataCollection.get(position).get(KEY_ITEMVALUE);
					//					String Icon =  ItemDataCollection.get(position).get(KEY_ICON);
					//					String BuyEquip =  ItemDataCollection.get(position).get(KEY_BUY);

					//Toast.makeText(getApplicationContext(), ID + " " + Name + " " + Value + " " + Icon + " " + BuyEquip, Toast.LENGTH_SHORT).show();






				}


			});


		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View btn) {
		switch (btn.getId()) {
		case R.id.BtnBack:

			Intent intent = new Intent(this, Shopping.class);
			this.startActivity(intent);
			this.finish();
			break;

		}

	}
	private void Buy() {
		Connection();
		bindService(new 
				Intent("com.android.vending.billing.InAppBillingService.BIND"),
				mServiceConn, this.BIND_AUTO_CREATE);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		if (requestCode == 1001) {           
			String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");

			if (resultCode == RESULT_OK) {
				try {
					JSONObject jo = new JSONObject(purchaseData);
					String sku = jo.getString("productId");
					String token = jo.getString("purchaseToken");
					Log.e("sku", sku);
					int respostaConsumo = mService.consumePurchase(3, getPackageName(), token);
					if(respostaConsumo == 0)
					{
						if(sku.equals("coin20"))
						{
							StorageInfo.storage.addGold(Integer.valueOf("20000"));
						}
						else if(sku.equals("coin50"))
						{
							StorageInfo.storage.addGold(Integer.valueOf("50000"));
						}
						else if(sku.equals("coin150"))
						{
							StorageInfo.storage.addGold(Integer.valueOf("150000"));
						}
						else if (sku.equals("coin500"))
						{
							StorageInfo.storage.addGold(Integer.valueOf("500000"));
						}

						SetCoinsPlayer();
					}
				}
				catch (JSONException e) {
					// alert("Failed to parse purchase data.");
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private void Connection() {
		mServiceConn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				mService = null;
			}

			@Override
			public void onServiceConnected(ComponentName name, 
					IBinder service) {
				mService = IInAppBillingService.Stub.asInterface(service);

				try {

					Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),ID, "inapp", "ItemBUY");


					PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

					startIntentSenderForResult(pendingIntent.getIntentSender(),
							1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
							Integer.valueOf(0));

				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SendIntentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
	}



}



	


