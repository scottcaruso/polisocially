package com.scottcaruso.polisocially;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.AuthProvider;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.AuthorizationConfiguration;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;

public class Politician_Details extends Activity {
	
	public static AccessToken accessToken;
	public static RequestToken requestToken;
	public static String redirect_url;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politician_details);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        
        try {
			twitterAuth();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_politician_details, menu);
        return true;
    }

    public static void twitterAuth() throws Exception{    	
        Twitter twitter = TwitterFactory.getSingleton();
		redirect_url = "";
		try 
		{
			requestToken = twitter.getOAuthRequestToken();
			redirect_url = requestToken.getAuthorizationURL();
			Log.i("Info",requestToken.toString());
		} catch (TwitterException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
