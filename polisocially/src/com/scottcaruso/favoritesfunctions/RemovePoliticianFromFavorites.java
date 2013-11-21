package com.scottcaruso.favoritesfunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class RemovePoliticianFromFavorites {
	
	public static void removePoliticianFromFavorites(Context context, String photoID)
	{
		SharedPreferences prefs = context.getSharedPreferences("com.scottcaruso.politicalconnectness", Context.MODE_PRIVATE);
		String saved = prefs.getString("Politicians", "None");
		try {
			JSONObject savedPols = new JSONObject(saved);
			JSONArray polArray = savedPols.getJSONArray("Politicians");
			for (int x = 0; x < polArray.length(); x++)
			{
				JSONObject thisPol = polArray.getJSONObject(x);
				JSONArray newArray = new JSONArray();
				String thisID = thisPol.getString("Photo ID");
				String thisPolName = thisPol.getString("Name");
				if (!thisID.equals(photoID))
				{
					newArray.put(thisPol);
				} else {
					Toast toast = Toast.makeText(context, thisPolName+" was successfully removed from Favorites.", Toast.LENGTH_SHORT);
					toast.show();
				}
				savedPols = new JSONObject();
				savedPols.put("Politicians", newArray);
				String newSave = savedPols.toString();
				prefs.edit().putString("Politicians", newSave).commit();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
