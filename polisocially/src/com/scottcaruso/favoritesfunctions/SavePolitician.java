package com.scottcaruso.favoritesfunctions;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

public class SavePolitician {
	
	public static void savePoliticians(Context context, String jsonString)
	{
		SharedPreferences prefs = context.getSharedPreferences("com.scottcaruso.politicalconnectness", Context.MODE_PRIVATE);
		prefs.edit().putString("Politicians", jsonString);
	}
	
	public static String convertJsonToString(JSONObject masterObject)
	{
		
		return null;
	}

}
