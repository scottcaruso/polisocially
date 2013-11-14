package com.scottcaruso.polisocially;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.scottcaruso.dataretrievalclasses.TurnStringIntoJSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class Politician_Results extends Activity {
	
	public String[] politicianNames;
	public String polData;
	public ArrayList<String> polsList;
	public String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
        	polData= null;
            } else {
            polData = extras.getString("Response");
        }
        Log.i("Info",polData);
        
        JSONObject thesePols = TurnStringIntoJSONObject.createMasterObject(polData);
        try {
        	polsList = new ArrayList<String>();
			JSONArray polArray = thesePols.getJSONArray("Politicians");
			for (int x = 0; x < polArray.length(); x++)
			{
				JSONObject polObjectOne = polArray.getJSONObject(x);
				String polName = polObjectOne.getString("Name");
				polsList.add(x, polName);
				String polTitle = "None";
				try {
					polTitle = polObjectOne.getString("Title");
					if (polTitle.equals("Rep"))
					{
						String polDistrict = polObjectOne.getString("District");
						String polState = polObjectOne.getString("State");
						Log.i("Info",polDistrict);
						Log.i("Info",polState);
						location = polState+"-"+polDistrict;
					}
				} catch (Exception e) {
					//Do nothing and move on.
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int size = polsList.size();
        polsList.add(size, "President Barack Obama");
        polsList.add(size+1, "Vice President Joe Biden");
   
        setContentView(R.layout.activity_politician_results);
        debugButtonClick();
        
        ArrayAdapter<String> politicianAdapter = createArray(polsList);
        ListView listView = (ListView) findViewById(R.id.listOfPols);
        listView.setAdapter(politicianAdapter);
        this.setTitle(location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_politician_results, menu);
        return true;
    }
    
    public void debugButtonClick()
    {
    	Button debugButton = (Button) findViewById(R.id.button1);
    	debugButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		    	Intent nextActivity = new Intent(Politician_Results.this,Politician_Details.class);
				Activity currentActivity = (Activity) Politician_Results.this;
				currentActivity.startActivityForResult(nextActivity, 0);
			}
		});
    }
    
    public ArrayAdapter<String> createArray(ArrayList<String> polsList2)
    {
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, polsList2);
    	return adapter;
    }
    
}
