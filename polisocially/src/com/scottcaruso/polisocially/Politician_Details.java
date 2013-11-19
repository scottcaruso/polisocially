package com.scottcaruso.polisocially;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Politician_Details extends Activity {
	
	public static ListView listview;
	public static ArrayAdapter<String> postsAdapter;
	public static ArrayList<String> postsList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politician_details);
        
        postsList = new ArrayList<String>();
        postsList.add("Tweet 1");
        postsList.add("Tweet 2");
        postsList.add("Tweet 3");
        postsList.add("Tweet 4");
        postsList.add("Tweet 5");
        postsList.add("Tweet 6");
        postsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, postsList);
        //listview = (ListView) findViewById(R.id.posts);
        listview.setAdapter(postsAdapter);
        
        //Button signIn = (Button) findViewById(R.id.signin);
        /*signIn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				}	
			});*/
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_politician_details, menu);
        return true;
    }
}
