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
import android.widget.Toast;

public class RemovePoliticianFromFavorites {
	
	//This class is for the removal of a politician from the Favorites list. It removes
	//the JSONObject from the Array, and then rebuilds the array.
	
	public static void removePoliticianFromFavorites(Context context, String photoID)
	{
		SharedPreferences prefs = context.getSharedPreferences("com.scottcaruso.politicalconnectness", Context.MODE_PRIVATE);
		String saved = prefs.getString("Politicians", "None");
		try {
			JSONObject savedPols = new JSONObject(saved);
			JSONArray polArray = savedPols.getJSONArray("Politicians");
			JSONArray newArray = new JSONArray();
			for (int x = 0; x < polArray.length(); x++)
			{
				JSONObject thisPol = polArray.getJSONObject(x);
				String thisID = thisPol.getString("Photo ID");
				String thisPolName = thisPol.getString("Name");
				if (!thisID.equals(photoID))
				{
					newArray.put(thisPol);
				} else {
					Toast toast = Toast.makeText(context, thisPolName+" was successfully removed from Favorites.", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
			savedPols = new JSONObject();
			savedPols.put("Politicians", newArray);
			String newSave = savedPols.toString();
			prefs.edit().putString("Politicians", newSave).commit();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
