package com.scottcaruso.polisocially;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.AuthProvider;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.AuthorizationConfiguration;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;

public class Politician_Details extends Activity {
	
	public static AccessToken accessToken;
	public static AccessToken accessTokenSecret;
	public static RequestToken requestToken;
	public static String redirect_url;
	public static Twitter twitter;
	public static ConfigurationBuilder cb;
	public static TwitterFactory tf;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politician_details);
        

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_politician_details, menu);
        return true;
    }

}
