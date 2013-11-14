package com.scottcaruso.polisocially;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class Politician_Results extends Activity {
	
	public String[] politicianNames = {"Test","Test 2","Test 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politician_results);
        debugButtonClick();
        
        ArrayAdapter<String> politicianAdapter = createArray(politicianNames);
        ListView listView = (ListView) findViewById(R.id.listOfPols);
        listView.setAdapter(politicianAdapter);
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
    
    public ArrayAdapter<String> createArray(String[] politicians)
    {
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, politicians);
    	return adapter;
    }

}
