package net.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.game.MainActivity;
import net.surf.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Util.BinderData;
import Util.CustomDialogClass;
import Util.CustomDialogCoins;
import Util.StorageInfo;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class Shopping extends Activity implements OnClickListener{
	// XML node keys
	static final String KEY_TAG = "itemdata"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_ITEMNAME = "name";
	static final String KEY_ITEMVALUE = "value";
	static final String KEY_ICON = "icon";
	static final String KEY_BUY = "buyequip";
	static final String KEY_DIRECTION = "direction";
	public static final String bodyXml = "Body.xml";
	public static final String boardXml = "Board.xml";
	public static final String headXml = "Head.xml";
	public static final String powerUpXml = "PowerUp.xml";
	public static final String setXML = "Set.xml";
	public static final String GodXML = "GodTrick.xml";
	String Asset = "";
	// List items 
	ListView list;
	BinderData adapter = null;
	List<HashMap<String,String>> ItemDataCollection;
	StorageInfo Storage; 
	CustomDialogClass cdd;
	CustomDialogCoins cddshop;

	Shopping shop = this;
    AdView adView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



		setContentView(R.layout.shooping_layout);

        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-9480879598480702/4025467071");
        adView.setAdSize(AdSize.BANNER);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.shoppingAD);

        // Adicionar o adView a ele.
        layout.addView(adView);

        // Iniciar uma solicitação genérica.
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("13F77AAABEE7FB57CF4FA6BD90138B61").build();
        // Carregar o adView com a solicitação de anúncio.
        adView.loadAd(adRequest);

		ImageButton btnHead = (ImageButton) findViewById(R.id.BtnHead);
		btnHead.setOnClickListener(this);

		ImageButton btnBody = (ImageButton) findViewById(R.id.BtnBody);
		btnBody.setOnClickListener(this);

		ImageButton btnBoard = (ImageButton) findViewById(R.id.BtnBoard);
		btnBoard.setOnClickListener(this);

		ImageButton btnTrick = (ImageButton) findViewById(R.id.BtnTrick);
		btnTrick.setOnClickListener(this);

		ImageButton btnSurfNow = (ImageButton) findViewById(R.id.BtnSurfNow);
		btnSurfNow.setOnClickListener(this);

		ImageButton btnBuyCoins = (ImageButton) findViewById(R.id.btnBuyCoins);
		btnBuyCoins.setOnClickListener(this);

		ImageButton btnBuyPowerUp = (ImageButton) findViewById(R.id.BtnPowerUp);
		btnBuyPowerUp.setOnClickListener(this);

		ImageButton btnSets = (ImageButton) findViewById(R.id.BtnSet);
		btnSets.setOnClickListener(this);


		cdd=new CustomDialogClass(Shopping.this);
		cddshop = new CustomDialogCoins(Shopping.this);
		Asset = headXml;
		StorageInfo.getInstance(this);
		PopulateList();
		SetCoinsPlayer();
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

					//2.-------
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


					//6.-------
					NodeList iconList = firstWeatherElement.getElementsByTagName(KEY_ICON);
					Element firstIconElement = (Element)iconList.item(0);
					NodeList textIconList = firstIconElement.getChildNodes();
					//--city
					map.put(KEY_ICON, ((Node)textIconList.item(0)).getNodeValue().trim());

					String id = map.get(KEY_ID);

					map.put(KEY_BUY, getKeyBuyType(id));

					
						NodeList valueDirection = firstWeatherElement.getElementsByTagName(KEY_DIRECTION);
						Element firstDirectionElement = (Element)valueDirection.item(0);
						NodeList textValue = firstDirectionElement.getChildNodes();
						map.put(KEY_DIRECTION,((Node)textValue.item(0)).getNodeValue().trim() );

					
					//Add to the Arraylist
					ItemDataCollection.add(map);
				}
			} 
			BinderData bindingData = new BinderData(this,ItemDataCollection);

			list = (ListView) findViewById(R.id.list);

			Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");

			list.setAdapter(bindingData);

			Log.i("AFTER", "<<------------- After SetAdapter-------------->>");

			// Click event for single list row
			list.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {


					String ID =  ItemDataCollection.get(position).get(KEY_ID).replace(",","");
					String Name =  ItemDataCollection.get(position).get(KEY_ITEMNAME);
					String Value =  ItemDataCollection.get(position).get(KEY_ITEMVALUE);
					String Icon =  ItemDataCollection.get(position).get(KEY_ICON);
					String BuyEquip =  ItemDataCollection.get(position).get(KEY_BUY);
					String Index = ItemDataCollection.get(position).get(KEY_DIRECTION);

					
					if(BuyEquip.equals("shop_item_buy"))
					{
						if(Integer.parseInt(StorageInfo.storage.getMyGold()) >= Integer.parseInt(Value))
						{

							cdd.image = Icon;
							cdd.item_name = Name;
							cdd.item_value = Value;
							cdd.Index = Integer.valueOf(Index);
							cdd.ID = ID;
							cdd.show();
							cdd.SetContent();
							cdd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

							cdd.yes.setOnClickListener(shop);
							cdd.no.setOnClickListener(shop);

						}
						else
						{
							cddshop.show();
							cddshop.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

							cddshop.yes.setOnClickListener(shop);
							cddshop.no.setOnClickListener(shop);
						}
					}
					else if (BuyEquip.equals("shop_item_equip"))
					{
						equipItem(ID, Integer.parseInt(Index));
						PopulateList();
					}
				}
			});


		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View btn) {
		switch (btn.getId()) {
		case R.id.BtnHead:
			Asset="Head.xml";PopulateList();
			break;

		case R.id.BtnBody:
			Asset="Body.xml";PopulateList();
			break;

		case R.id.BtnBoard:
			Asset="Board.xml";PopulateList();
			break;

		case R.id.BtnTrick:
			Asset="GodTrick.xml";PopulateList();
			break;

		case R.id.BtnPowerUp:
			Asset="PowerUp.xml";PopulateList();
			break;

		case R.id.BtnSet:
			Asset="Set.xml";PopulateList();
			break;


		case R.id.BtnSurfNow:
			Intent intent = new Intent(Shopping.this, MainActivity.class);
			Shopping.this.finish();
			Shopping.this.startActivity(intent);

			break;
		case R.id.btnBuyCoins:
			Intent intentcoins = new Intent(Shopping.this, BuyCoins.class);
			Shopping.this.finish();
			Shopping.this.startActivity(intentcoins);


			break;

		case R.id.btnItemConfirm:
			if(Asset.equals("Head.xml") || Asset.equals("Body.xml") || Asset.equals("Board.xml") )
			{
				if(Integer.parseInt(StorageInfo.storage.getMyEquipedHead().replace(",", "")) >= 100 ) //podia ser qualquer um
				{
					StorageInfo.storage.setMyEquipedBoard("0");
					StorageInfo.storage.setMyEquipedHead("0");
					StorageInfo.storage.setMyEquipedBody("0");
				}
			}
			addNewItem(cdd.ID);		 
			StorageInfo.storage.addGold(-Integer.decode(cdd.item_value));
			PopulateList();
			SetCoinsPlayer();
			cdd.dismiss();
			break;

		case R.id.btnItemCancel:
			PopulateList();
			cdd.dismiss();
			break;

		case R.id.btnItemConfirmCoin:
			Intent intn = new Intent(Shopping.this, BuyCoins.class);
			Shopping.this.finish();
			Shopping.this.startActivity(intn);

			break;

		case R.id.btnItemCancelCoin:
			cddshop.dismiss();
			break;

		}
	}

	public void addNewItem (String newItem){	


		switch (AssetType.getAssetType(Asset)) {
		case Body:
			StorageInfo.storage.addInMybodys(newItem);
			StorageInfo.storage.setMyEquipedBody(newItem);
			break;
		case Board:
			StorageInfo.storage.addInMyBoard(newItem);
			StorageInfo.storage.setMyEquipedBoard(newItem);
			break;
		case Head:
			StorageInfo.storage.addInMyHeads(newItem);
			StorageInfo.storage.setMyEquipedHead(newItem);
			break;
		case PowerUp:
			StorageInfo.storage.addInMyPowerUps(newItem);
			StorageInfo.storage.setMyEquipedPowerUp(newItem);
			break;

		case GodTrick:
			StorageInfo.storage.addInMyTrick(newItem);
			StorageInfo.storage.EquipTrick(cdd.Index,newItem);
			break;

		case Set:
			StorageInfo.storage.addInMyHeads(newItem);
			StorageInfo.storage.addInMyBoard(newItem);
			StorageInfo.storage.addInMybodys(newItem);
			StorageInfo.storage.setMyEquipedHead(newItem);
			StorageInfo.storage.setMyEquipedBody(newItem);
			StorageInfo.storage.setMyEquipedBoard(newItem);
			break;

		}
	}
	public void equipItem (String newItem, int Index){

		if(Asset.equals("Head.xml") || Asset.equals("Body.xml") || Asset.equals("Board.xml") )
		{
			if(Integer.parseInt(StorageInfo.storage.getMyEquipedHead().replace(",", "")) >= 100 ) //podia ser qualquer um
			{
				
				StorageInfo.storage.setMyEquipedBoard("0");
				StorageInfo.storage.setMyEquipedHead("0");
				StorageInfo.storage.setMyEquipedBody("0");
			}
		}

		switch (AssetType.getAssetType(Asset)) {
		case Body:
			StorageInfo.storage.setMyEquipedBody(newItem);
			break;
		case Board:
			StorageInfo.storage.setMyEquipedBoard(newItem);
			break;
		case Head:
			StorageInfo.storage.setMyEquipedHead(newItem);
			break;
		case PowerUp:
			StorageInfo.storage.setMyEquipedPowerUp(newItem);
			break;
		case GodTrick:
			StorageInfo.storage.EquipTrick(Index, newItem);
			break;
		case Set:
			StorageInfo.storage.setMyEquipedBody(newItem);
			StorageInfo.storage.setMyEquipedBoard(newItem);
			StorageInfo.storage.setMyEquipedHead(newItem);
			break;
		}
	}

	public String getKeyBuyType (String id){
		String keyBuy = "shop_item_buy";
		switch (AssetType.getAssetType(Asset)) {
		case Body:
			if (StorageInfo.storage.getMyEquipedBody().equals(id + ",")){
				keyBuy = "shop_btn_equipped";
			}else if (StorageInfo.storage.getMyBodys().contains( "," + id+ ",")){
				keyBuy = "shop_item_equip";
			}
			break;
		case Board:
			if (StorageInfo.storage.getMyEquipedBoard() .equals (id+ ",")){
				keyBuy = "shop_btn_equipped";
			}else if (StorageInfo.storage.getMyBoards().contains("," +id+ ",")){
				keyBuy = "shop_item_equip";
			}
			break;
		case Head:
			if (StorageInfo.storage.getMyEquipedHead() .equals (id + ",")){
				keyBuy = "shop_btn_equipped";
			}else if (StorageInfo.storage.getMyHeads().contains("," +id + ",")){
				keyBuy = "shop_item_equip";
			}
			break;
		case GodTrick:
			if (StorageInfo.storage.getMyEquipedTrick().contains("," + id + ",")){
				keyBuy = "shop_btn_equipped";
			}else if (StorageInfo.storage.getMyTricks().contains("," + id + ",")){
				keyBuy = "shop_item_equip";
			}
			break;
		case PowerUp:
			if (StorageInfo.storage.getMyEquipedPowerUp() .equals (id)){
				keyBuy = "shop_btn_equipped";
			}else if (StorageInfo.storage.getMyPowerUps().contains("," +id + ",")){
				keyBuy = "shop_item_equip";
			}
			break;
		case Set:
			if (StorageInfo.storage.getMyEquipedHead() .equals (id + ",")){
				keyBuy = "shop_btn_equipped";
			}else if (StorageInfo.storage.getMyHeads().contains("," +id + ",")){
				keyBuy = "shop_item_equip";
			}
			break;

		}
		return keyBuy;
	}

	public enum AssetType {
		Body(bodyXml),
		Board(boardXml),
		Head(headXml),
		GodTrick(GodXML),
		PowerUp(powerUpXml),
		Set(setXML);
		private String xmlType;
		private AssetType (String _type){
			xmlType = _type;
		}

		public static AssetType getAssetType (String type){
			if (bodyXml.equals(type)){
				return Body;
			}else if (boardXml.equals(type)){
				return Board;
			}else if (headXml.equals(type)) {
				return Head;
			}else if (powerUpXml.equals(type)){
				return PowerUp;
			}
			else if (GodXML.equals(type)){
				return GodTrick;
			}
			else if (setXML.equals(type)){
				return Set;
			}
			else {
				return null;
			}
		}

	}
    @Override
    protected void onResume() {
        adView.resume();
        super.onResume();
    }
    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }

}


