package Util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class StorageInfoHelper {
	public static final String StartGOD = "StartGodBonus";
	public static final String StartBOAT = "StartBoatBonus";
	public static final String DoubleCoinBonus = "StartDoubleCoinsBonus";
	
	public static final String GoldKEY = "Gold";
	public static final String  RecordKEY = "Record";
	public static final String EquipedItensKEY = "EquipedItens";
	public static final String MyHeadItensKEY = "MyHeadItens";
	public static final String MyPowerUpItensKEY = "MyPowerUpItens";
	public static final String MyPowerUpItensTwoKEY = "MyPowerUpItensTwo";
	public static final String MyBodyItensKEY = "MyBodyItens";
	public static final String MyTrickItensKEY = "MyTrickItens";
	public static final String MyEquipedTricks = "MyEquipedTrickItens";
	public static final String MyBoardItensKEY = "MyBoardItens";
	public static final String MusicStatusKEY = "MusicStatus";
	public static final String RateStatusKEY = "RateStatus";
	public static final String SFXStatusKEY = "SFXStatus";
	public static final String splitDefault = ",";
	public static final String goldDefault = "0";
	public static final String itenDefault = ",0,";
	public static final String defaultTricks = ",1,2,7,8,";
	public static final String equipItensDefault = ",0,0,0,0,0,";
	private static final int headCNT = 1;
	private static final int PowerUpCNT = 2;
	private static final int bodyCNT = 3;
	private static final int boardCNT = 4;
	private static final int PowerUpTwoCNT = 5;
	private static final int AskToRate = 0;
	
	SharedPreferences.Editor editor;
	SharedPreferences settings;
	private Activity context;
	
	
	public StorageInfoHelper (Activity mycontext){
		context = mycontext;
	}
	
		
    protected SharedPreferences getPreferences (){
    		
    	synchronized (StorageInfoHelper.class){
			if (settings != null){
				return settings;
			}else {
				settings = PreferenceManager.getDefaultSharedPreferences(context);
				//settings = context.getSharedPreferences("net.game.Surfing", Context.MODE_PRIVATE);
				return settings; 
			}
		}
    }
    
    private SharedPreferences.Editor getEditor (){
		synchronized (StorageInfoHelper.class){
			if (editor != null){
				return editor;
			}else {
				editor = getPreferences().edit();
				return editor; 
			}
		}
    }
    
    public int CheckRate (){
    	return getPreferences().getInt(RateStatusKEY, AskToRate);
    }
    
    public boolean IsThereStartGod()
    {
    	return getPreferences().getBoolean(StartGOD, false);
    }
    public void setStartGod(boolean GO)
    {
    	getEditor().putBoolean(StartGOD, GO);
    	getEditor().commit();
    }
    
    public boolean IsThereStartBoat()
    {
    	return getPreferences().getBoolean(StartBOAT, false);
    }
    public void setStartBoat(boolean GO)
    {
    	getEditor().putBoolean(StartBOAT, GO);
    	getEditor().commit();
    }
    public boolean IsThere2xCoin()
    {
    	return getPreferences().getBoolean(DoubleCoinBonus, false);
    }
    public void setDoubleCoinsBonuts(boolean GO)
    {
    	getEditor().putBoolean(DoubleCoinBonus, GO);
    	getEditor().commit();
    }
    
    
    
    protected String getStoredFiles(String key){
       // Restore preferences
       return getPreferences().getString(key, new String());
    }

    protected void saveStoredFiles(String key, String value){
      // We need an Editor object to make preference changes.
      // All objects are from android.context.Context
      getEditor().putString(key, value);

      // Commit the edits!
      getEditor().commit();
    }
    
    public boolean isOnStatusMusic (){
    	return getPreferences().getBoolean(MusicStatusKEY, true);
    }
    
    public void setIsOnStatusMusic (boolean val){
    	getEditor().putBoolean(MusicStatusKEY, val);
    	getEditor().commit();
    }
    
    public boolean isOnSFXStatus (){
    	return getPreferences().getBoolean(SFXStatusKEY, true);
    }
    
    public void setIsOnSFXStatus (boolean val){
    	getEditor().putBoolean(SFXStatusKEY, val);
    	getEditor().commit();
    }
	
    public String getMyGold (){
        return getPreferences().getString(GoldKEY, goldDefault);
    }
	
    public String addGold (int value){
         getEditor().putString(GoldKEY, String.valueOf(Long.decode(getMyGold()) + value));
         getEditor().commit();
    	return getMyGold();
    }
    
    public String getMyEquipedItens (){
    	return getPreferences().getString(EquipedItensKEY, equipItensDefault);
    }
    
    public void setMyEquipedItens (String equipedItens){
    	getEditor().putString(EquipedItensKEY, equipedItens);
    	getEditor().commit();
    }
    
    public String getMyEquipedHead (){
    	return getMyEquipedItens().split(splitDefault)[headCNT] + splitDefault;
    }
    
    public void setMyEquipedHead (String head){
    	String myEquipedItens = getMyEquipedItens();
    	String myEquipedPowerUp = myEquipedItens.split(splitDefault)[PowerUpCNT];    	
    	String myEquipedBody = myEquipedItens.split(splitDefault)[bodyCNT];
    	String myEquipedBoard = myEquipedItens.split(splitDefault)[boardCNT];
    	String myEquipedPowerUpTwo = myEquipedItens.split(splitDefault)[PowerUpTwoCNT];
    	myEquipedItens = splitDefault + head + splitDefault + myEquipedPowerUp + splitDefault + myEquipedBody + splitDefault + myEquipedBoard + splitDefault + myEquipedPowerUpTwo + splitDefault;
    	setMyEquipedItens(myEquipedItens);
    }
    
    public String getMyEquipedPowerUp (){
    	return getMyEquipedItens().split(splitDefault)[PowerUpCNT];
    }
    
    public void setMyEquipedPowerUp (String PowerUp){
    	String myEquipedItens = getMyEquipedItens();
    	String myEquipedHead = myEquipedItens.split(splitDefault)[headCNT];    	
    	String myEquipedBody = myEquipedItens.split(splitDefault)[bodyCNT];
    	String myEquipedBoard = myEquipedItens.split(splitDefault)[boardCNT];
    	String myEquipedPowerUpTwo = myEquipedItens.split(splitDefault)[PowerUpTwoCNT];
    	myEquipedItens = splitDefault + myEquipedHead + splitDefault + PowerUp + splitDefault + myEquipedBody + splitDefault + myEquipedBoard + splitDefault + myEquipedPowerUpTwo + splitDefault;
    	setMyEquipedItens(myEquipedItens);
    }
    
    public String getMyEquipedBoard (){
    	return getMyEquipedItens().split(splitDefault)[boardCNT]+ splitDefault;
    }
    
    public void setMyEquipedBoard (String board){
    	String myEquipedItens = getMyEquipedItens();
    	String myEquipedHead = myEquipedItens.split(splitDefault)[headCNT];
    	String myEquipedPowerUp = myEquipedItens.split(splitDefault)[PowerUpCNT];    	
    	String myEquipedBody = myEquipedItens.split(splitDefault)[bodyCNT];
    	String myEquipedPowerUpTwo = myEquipedItens.split(splitDefault)[PowerUpTwoCNT];
    	myEquipedItens = splitDefault + myEquipedHead + splitDefault + myEquipedPowerUp + splitDefault + myEquipedBody + splitDefault + board + splitDefault + myEquipedPowerUpTwo + splitDefault;
    	setMyEquipedItens(myEquipedItens);
    }
    
    public String getMyEquipedBody () {
    	return getMyEquipedItens().split(splitDefault)[bodyCNT]+ splitDefault;
    }
    
    public void setMyEquipedBody (String body){
    	String myEquipedItens = getMyEquipedItens();
    	String myEquipedHead = myEquipedItens.split(splitDefault)[headCNT];
    	String myEquipedPowerUp = myEquipedItens.split(splitDefault)[PowerUpCNT];    	
    	String myEquipedBoard = myEquipedItens.split(splitDefault)[boardCNT];
    	String myEquipedPowerUpTwo = myEquipedItens.split(splitDefault)[PowerUpTwoCNT];
    	myEquipedItens = splitDefault + myEquipedHead + splitDefault + myEquipedPowerUp + splitDefault + body + splitDefault + myEquipedBoard + splitDefault + myEquipedPowerUpTwo + splitDefault;
    	setMyEquipedItens(myEquipedItens);
    }
    
    public String getMyEquipedPowerUpTwo (){
    	return getMyEquipedItens().split(splitDefault)[PowerUpTwoCNT]+ splitDefault;
    }
    
    public void setMyEquipedPowerUpTwo (String PowerUpTwo){
    	String myEquipedItens = getMyEquipedItens();
    	String myEquipedHead = myEquipedItens.split(splitDefault)[headCNT];    	
    	String myEquipedBody = myEquipedItens.split(splitDefault)[bodyCNT];
    	String myEquipedBoard = myEquipedItens.split(splitDefault)[boardCNT];
    	String PowerUp = myEquipedItens.split(splitDefault)[PowerUpCNT];
    	myEquipedItens = splitDefault + myEquipedHead + splitDefault + PowerUp + splitDefault + myEquipedBody + splitDefault + myEquipedBoard + splitDefault + PowerUpTwo + splitDefault;
    	setMyEquipedItens(myEquipedItens);
    }
    
    public String getMyHeads (){
    	return  getPreferences().getString(MyHeadItensKEY, itenDefault);
    }
    
    public void addInMyHeads (String head) {
    	String myHead = getPreferences().getString(MyHeadItensKEY, itenDefault);
    	myHead = myHead + head + splitDefault;
    	getEditor().putString(MyHeadItensKEY, myHead);
    	getEditor().commit();
    }
    
    public String getMyPowerUps (){
    	return getPreferences().getString(MyPowerUpItensKEY, itenDefault);
    }
    
    public void addInMyPowerUps (String PowerUp) {
    	String myPowerUp = getPreferences().getString(MyPowerUpItensKEY, itenDefault);
    	myPowerUp = myPowerUp  + PowerUp + splitDefault;
    	getEditor().putString(MyPowerUpItensKEY, myPowerUp);
    	getEditor().commit();
    }
    
    public String getMyBoards (){
    	return getPreferences().getString(MyBoardItensKEY, itenDefault);
    }
    
    public void addInMyBoard (String board) {
    	String myBoard = getPreferences().getString(MyBoardItensKEY, itenDefault);
    	myBoard = myBoard + board + splitDefault;
    	getEditor().putString(MyBoardItensKEY, myBoard);
    	getEditor().commit();
    }
    
    public String getMyBodys (){
    	return getPreferences().getString(MyBodyItensKEY, itenDefault);
    }
    
    public void addInMybodys (String body) {
    	String myBody = getPreferences().getString(MyBodyItensKEY, itenDefault);
    	myBody = myBody + body + splitDefault;
    	getEditor().putString(MyBodyItensKEY, myBody);
    	getEditor().commit();
    }
    
    public String getMyPowerUpsTwo (){
    	return getPreferences().getString(MyPowerUpItensTwoKEY, itenDefault);
    }
    
    public void addInMyPowerUpsTwo (String PowerUpTwo) {
    	String myPowerUp = getPreferences().getString(MyPowerUpItensTwoKEY, itenDefault);
    	myPowerUp = myPowerUp + PowerUpTwo + splitDefault;
    	getEditor().putString(MyPowerUpItensTwoKEY, myPowerUp);
    	getEditor().commit();
    }

	public void reset() {
		 getEditor().clear();
		 getEditor().commit();	
	}


	public void SetRate(int i) {
    	getEditor().putInt(RateStatusKEY, i);
    	getEditor().commit();
		
	}


	public String getMyEquipedTrick() {
		return getPreferences().getString(MyEquipedTricks, defaultTricks);
	}
	
	public void addInMyTrick(String i)
	{
		String MyTricks = getPreferences().getString(MyTrickItensKEY, defaultTricks);
		MyTricks = MyTricks + i + ",";
		getEditor().putString(MyTrickItensKEY, MyTricks);
		getEditor().commit();
	}

	public void EquipTrick(int Index, String Trick)
	{
		String MyTricks = getPreferences().getString(MyEquipedTricks, defaultTricks);
		String CurrentTrick = MyTricks.split(",")[Index+1]; //String.valueOf(MyTricks.charAt(Index));
		MyTricks = MyTricks.replace(CurrentTrick, Trick);
		getEditor().putString(MyEquipedTricks, MyTricks);
		getEditor().commit();
	}
	public String getMyTricks() {
		return getPreferences().getString(MyTrickItensKEY, defaultTricks);
	}


	public int getMyRecord() {
		return getPreferences().getInt(RecordKEY, 50);
	}
	public void setMyRecord(int Record)
	{
		getEditor().putInt(RecordKEY, Record);
		getEditor().commit();
	}
	public boolean HasPlayed()
	{
		return getPreferences().getBoolean("HasPlayed", false);
	}
	public void setHasPlayed()
	{
		getEditor().putBoolean("HasPlayed", true);
		getEditor().commit();
	}
	
    
}
