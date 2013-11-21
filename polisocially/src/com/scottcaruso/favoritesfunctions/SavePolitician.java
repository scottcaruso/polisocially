/* Political Connectness
 * by Scott Caruso
 * APD2 1311 Term
 */
package com.scottcaruso.favoritesfunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

public class SavePolitician {
	
	//This class includes functions related to saving politician data to the preferences
	//file. There is a master save function, that wraps up a function that converts a
	//JSON object to a string, and a second function that saves that string to the
	//storage with the other strings.
	
	public static void doSave(Context context, JSONObject saveObject)
	{
		String singleString = convertJsonToString(saveObject);
		savePoliticians(context, singleString);
	}
	
	public static void savePoliticians(Context context, String jsonString)
	{
		SharedPreferences prefs = context.getSharedPreferences("com.scottcaruso.politicalconnectness", Context.MODE_PRIVATE);
		String saved = prefs.getString("Politicians", "None");
		JSONObject masterObject;
		JSONArray masterArray;
		try {
			masterObject = new JSONObject(saved);
			masterArray = masterObject.getJSONArray("Politicians");
			JSONObject newObject = new JSONObject(jsonString);
			masterArray.put(newObject);
			masterObject = new JSONObject();
			masterObject.put("Politicians",masterArray);
			String newSave = masterObject.toString();
			prefs.edit().putString("Politicians", newSave).commit();
		} catch (JSONException e) {
			JSONObject thisObject;
			try {
				thisObject = new JSONObject(jsonString);
				masterArray = new JSONArray();
				masterArray.put(thisObject);
				masterObject = new JSONObject();
				masterObject.put("Politicians", masterArray);
				String firstSave = masterObject.toString();
				prefs.edit().putString("Politicians", firstSave).commit();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static String convertJsonToString(JSONObject saveObject)
	{
		String converted = saveObject.toString();
		return converted;
	}

}
