/* Political Connectness
 * by Scott Caruso
 * APD2 1311 Term
 */
package com.scottcaruso.polisocially;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.scottcaruso.favoritesfunctions.RetrievePoliticians;
import com.scottcaruso.listadapter.CustomFavoritesAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Favorites_Activity extends Activity {
	
	public static CustomFavoritesAdapter adapter;
	public static ArrayList<String> polsList;
	public static ArrayList<String> partyList;
	public static ArrayList<String> statesList;
	public static ArrayList<String> photoIDList;
	public static ArrayList<String> birthdayList;
	public static ArrayList<String> termList;
	public static ArrayList<String> govTrackList;
	public static ListView listview;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
    this.setTitle("Favorites");
    getFavoritesAndParse();
    
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	this.createAlert();
    	return true;
    }
    
    public void createAlert() {
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.about_app);
		AlertDialog alertDialog = alert.create();

		alertDialog.show();  
    }
    
    public void getFavoritesAndParse()
    {
    	String favorites = RetrievePoliticians.retrieveFavorites(Favorites_Activity.this);
    	JSONObject savedPols;
    	polsList = new ArrayList<String>();
    	partyList = new ArrayList<String>();
    	statesList = new ArrayList<String>();
    	photoIDList = new ArrayList<String>();
    	birthdayList = new ArrayList<String>();
    	termList = new ArrayList<String>();
    	govTrackList = new ArrayList<String>();
		try {
			savedPols = new JSONObject(favorites);
			JSONArray polArray = savedPols.getJSONArray("Politicians");
			for (int x = 0; x < polArray.length(); x++)
			{
				JSONObject thisPol = polArray.getJSONObject(x);
				polsList.add(thisPol.getString("Name"));
				partyList.add(thisPol.getString("Party"));
				statesList.add(thisPol.getString("State"));
				photoIDList.add(thisPol.getString("Photo ID"));
				birthdayList.add(thisPol.getString("DOB"));
				termList.add(thisPol.getString("Term End"));
				govTrackList.add(thisPol.getString("GovTrack ID"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    adapter = new CustomFavoritesAdapter(Favorites_Activity.this,polsList,partyList,statesList,photoIDList);
	    listview = (ListView) findViewById(R.id.faves_listview);
	    listview.setAdapter(adapter);
	    listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int item,
					long id) {
				Log.i("Info","The index of the clicked item is "+item);	
		    	Intent nextActivity = new Intent(Favorites_Activity.this,Politician_Details.class);
		    	nextActivity.putExtra("Name",polsList.get(item));
		    	nextActivity.putExtra("Party",partyList.get(item));
		    	nextActivity.putExtra("DOB",birthdayList.get(item));
		    	nextActivity.putExtra("Term End", termList.get(item));
		    	nextActivity.putExtra("GovTrack ID", govTrackList.get(item));
		    	nextActivity.putExtra("Photo ID", photoIDList.get(item));
				Activity currentActivity = (Activity) Favorites_Activity.this;
				currentActivity.startActivityForResult(nextActivity, 0);
			}
		});
    }
}
