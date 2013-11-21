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

//This class includes two functins for favorties retrieval - one that tells the application
//that a specific politician is already saved, so it doesn't try to do it again, and one
//that simply returns the list of politicians from storage.

public class RetrievePoliticians {
	
	public static Boolean isPoliticianSaved(Context context, String govTrackID)
	{
		SharedPreferences prefs = context.getSharedPreferences("com.scottcaruso.politicalconnectness", Context.MODE_PRIVATE);
		String saved = prefs.getString("Politicians", "None");
		if (saved.equals("None"))
		{
			return false;
		} else
		{
			try {
				JSONObject savedPols = new JSONObject(saved);
				JSONArray polArray = savedPols.getJSONArray("Politicians");
				for (int x = 0; x < polArray.length(); x++)
				{
					JSONObject thisPol = polArray.getJSONObject(x);
					String thisGovTrack = thisPol.getString("GovTrack ID");
					if (thisGovTrack.equals(govTrackID))
					{
						return true;
					}
				}
				return false;
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}	
	}
	
	public static String retrieveFavorites(Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences("com.scottcaruso.politicalconnectness", Context.MODE_PRIVATE);
		String saved = prefs.getString("Politicians", "None");
		return saved;
	}

}
