package Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;


public class PatternsHelper {

	private final String defaulNmePatern = "pattern";
	private final String[] patternsTypes = {"birds", "coin"};
	private static HashMap<String, ArrayList<String>> patternsMap;
	private Context context;
	
			
	public PatternsHelper (Context applicationContext){
		context = applicationContext;
		populateMap();
	}

	public void populateMap() {
		patternsMap = new HashMap<String, ArrayList<String>>();
		for (int i = 0; i<patternsTypes.length;i++){
			for (int j = 1, id = 1; id > 0; j++) {
				String pRawName = patternsTypes[i] + defaulNmePatern + String.valueOf(j);
				id = context.getResources().getIdentifier(pRawName, "raw", context.getPackageName());
				if (id <= 0)
					continue;
				
				patternsMap.put(pRawName, PatternsHelper.getPattern(id, context));
			}
		}
	}
	
	public static ArrayList<String> getPattern (String key){
		return patternsMap.get(key);
	}

	public static final ArrayList<String> getPattern(int ResID,Context cont)
	{
		ArrayList<String> linhas = new ArrayList<String>();

		InputStream inputStream = cont.getResources().openRawResource(ResID);

		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		String line;

		try {
			while (( line = buffreader.readLine()) != null) {
				linhas.add(line);
				
			}
		} catch (IOException e) {
			return null;
		}
		return linhas;
	}
}
