package com.scottcaruso.polisocially;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.AuthProvider;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindspiker.mstwitter.MSTwitter;
import com.mindspiker.mstwitter.MSTwitter.MSTwitterResultReceiver;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.AuthorizationConfiguration;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
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
        listview = (ListView) findViewById(R.id.posts);
        listview.setAdapter(postsAdapter);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_politician_details, menu);
        return true;
    }
   
}
