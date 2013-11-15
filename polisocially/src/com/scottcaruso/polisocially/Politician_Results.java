package com.scottcaruso.polisocially;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.scottcaruso.dataretrievalclasses.TurnStringIntoJSONObject;
import com.scottcaruso.twittertests.Twitter_Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class Politician_Results extends Activity {
	
	public String[] politicianNames;
	public String polData;
	public ArrayList<String> polsList;
	public String location;
	public String tempLocation;
	public ArrayList<String> multipleReps;
	public ArrayList<String> arrayOfDistricts;
	public Spinner hiddenButton;
	public JSONObject thesePols;
	public ListView listview;
	public ArrayAdapter<String> politicianAdapter;

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
        
        thesePols = TurnStringIntoJSONObject.createMasterObject(polData);
        try {
        	polsList = new ArrayList<String>();
        	arrayOfDistricts = new ArrayList<String>();
        	multipleReps = new ArrayList<String>();
			JSONArray polArray = thesePols.getJSONArray("Politicians");
			int arrayLength = polArray.length();
			if (arrayLength <= 3)
			{
				for (int x = 0; x < arrayLength; x++)
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
							location = polState+"-"+polDistrict;
						}
					} catch (Exception e) {
						//Do nothing and move on.
					}
				}
			} else {
				for (int x = 0; x < arrayLength; x++)
				{
					Log.i("Info","Multiple representatives found!");
					JSONObject polObjectTwo = polArray.getJSONObject(x);
					String polTitle = "None";
					try {
						polTitle = polObjectTwo.getString("Title");
						if (polTitle.equals("Rep"))
						{
							String polDistrict = polObjectTwo.getString("District");
							String polState = polObjectTwo.getString("State");
							String district = polState+"-"+polDistrict;
							arrayOfDistricts.add(district);
						}
					} catch (Exception e) {
						//Do nothing and move on.
					}
				}
				for (int x = 0; x < arrayLength; x++)
				{
					JSONObject polObjectThree = polArray.getJSONObject(x);
					String polName = polObjectThree.getString("Name");
					String polTitle = "None";
					try {
						polTitle = polObjectThree.getString("Title");
						if (polTitle.equals("Rep"))
						{
							String polDistrict = polObjectThree.getString("District");
							String polState = polObjectThree.getString("State");
							String district = polState+"-"+polDistrict;
							multipleReps.add(polName);
							for (int y=0; y < arrayOfDistricts.size(); y++)
							{
								if (district.equals(arrayOfDistricts.get(arrayOfDistricts.size()-1)))
								{
									polsList.add(polName);
									break;
								}
							}
						} else
						{
							polsList.add(polName);
						}
					} catch (Exception e) {
						//Do nothing and move on.
					}
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
        
        politicianAdapter = createArray(polsList);
        listview = (ListView) findViewById(R.id.listOfPols);
        listview.setAdapter(politicianAdapter);
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int item,
					long id) {
				Log.i("Info","The index of the clicked item is "+item);	
		    	Intent nextActivity = new Intent(Politician_Results.this,Twitter_Activity.class);
				Activity currentActivity = (Activity) Politician_Results.this;
				currentActivity.startActivityForResult(nextActivity, 0);
			}
		});
        
        this.setTitle(location);
        
        Boolean needToShow = shouldButtonBeShown(multipleReps.size());
        showHiddenButton(needToShow);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_politician_results, menu);
        return true;
    }
    
    /*public void debugButtonClick()
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
    }*/
    
    public ArrayAdapter<String> createArray(ArrayList<String> polsList2)
    {
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, polsList2);
    	return adapter;
    }
    
    public Boolean shouldButtonBeShown(int listSize)
    {
    	if (listSize > 1)
    	{
    		return true;
    	} else
    	{
    		return false;
    	}
    }
    
    public void showHiddenButton(Boolean shouldItBeShown)
    {
		hiddenButton = (Spinner) findViewById(R.id.district);
    	if (shouldItBeShown == true)
    	{
    		createSpinner();
    		hiddenButton.setVisibility(Spinner.VISIBLE);
    	} else {
    		hiddenButton.setVisibility(Spinner.INVISIBLE);
    	}
    	
    }
    
    public void createSpinner()
    {
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayOfDistricts);		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		hiddenButton.setAdapter(adapter);
		//Adapter that listens for which spinner item has been clicked.
		hiddenButton.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int loc, long id) {
				politicianAdapter.clear();
				String district = adapter.getItemAtPosition(loc).toString();
				try {
					JSONArray polArray = thesePols.getJSONArray("Politicians");
					int arrayLength = polArray.length();
					for (int x = 0; x < arrayLength; x++)
					{
						JSONObject polObjectFour = polArray.getJSONObject(x);
						String polName = polObjectFour.getString("Name");
						String polTitle = "None";
						String polDistrict = "None";
						String polState = "None";
						polTitle = polObjectFour.getString("Title");
						polDistrict = polObjectFour.getString("District");
						polState = polObjectFour.getString("State");
						tempLocation = polState+"-"+polDistrict;
						if (polTitle.equals("Rep")&&tempLocation.equals(district))
						{
							polsList.add(polName);
						} else if (!polTitle.equals("Rep"))
						{
							polsList.add(polName);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			//TODO: Need to debug why the title isn't changing with the Spinner clicks.
			Politician_Results.this.setTitle(tempLocation);
		    int size = polsList.size();
		    polsList.add(size, "President Barack Obama");
		    polsList.add(size+1, "Vice President Joe Biden");
			politicianAdapter.notifyDataSetChanged();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//DO NOTHING
			}
		});
    }
    
}
