package com.scottcaruso.polisocially;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Politician_Details extends Activity {
	
	public static ListView listview;
	public static String polName;
	public static String partyName;
	public static String birthday;
	public static String termEnd;
	public static String govTrackID;
	public static String photoID;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politician_details);
        
        Bundle extras = getIntent().getExtras();
        polName = extras.getString("Name");
        partyName = extras.getString("Party");
        birthday = extras.getString("DOB");
        termEnd = extras.getString("Term End");
        govTrackID = extras.getString("GovTrack ID");
        photoID = extras.getString("Photo ID");
        
        //Set dynamic data views
        TextView name = (TextView) findViewById(R.id.poliName);
        TextView partyView = (TextView) findViewById(R.id.partyvalue);
        TextView birthdayView = (TextView) findViewById(R.id.birthdayvalue);
        TextView termView = (TextView) findViewById(R.id.termvalue);
        TextView govTrackView = (TextView) findViewById(R.id.govtrackvalue);
        ImageView polImage = (ImageView) findViewById(R.id.polImage);
        
        name.setText(polName);
        partyView.setText(partyName);
        birthdayView.setText(birthday);
        termView.setText(termEnd);
        govTrackView.setText(govTrackID);
        int id = Politician_Details.this.getResources().getIdentifier(photoID, "drawable", Politician_Details.this.getPackageName());
        polImage.setImageResource(id);
        
        //Set up action buttons
        Button twitterButton = (Button) findViewById(R.id.buttonTweet);
        twitterButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				launchTwitterIntent();		
			}
		});
        
        Button facebookButton = (Button) findViewById(R.id.buttonFacebook);
        facebookButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				launchFacebookIntent();		
			}
		});
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_politician_details, menu);
        return true;
    }
    
    public void launchTwitterIntent()
    {
    	Intent tweetIntent = new Intent(Intent.ACTION_SEND);
    	tweetIntent.putExtra(Intent.EXTRA_TEXT, "This is a Test.");
    	tweetIntent.setType("text/plain");

    	PackageManager packManager = getPackageManager();
    	List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent,  PackageManager.MATCH_DEFAULT_ONLY);

    	boolean resolved = false;
    	for(ResolveInfo resolveInfo: resolvedInfoList){
    	    if(resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")){
    	        tweetIntent.setClassName(
    	            resolveInfo.activityInfo.packageName, 
    	            resolveInfo.activityInfo.name );
    	        resolved = true;
    	        break;
    	    }
    	}
    	if(resolved){
    	    startActivity(tweetIntent);
    	}else{
    	    Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
    	}
    }
    
    public void launchFacebookIntent()
    {
    	Intent facebookIntent = new Intent(Intent.ACTION_SEND);
    	facebookIntent.putExtra(Intent.EXTRA_TEXT, "This is a Test.");
    	facebookIntent.setType("text/plain");

    	PackageManager packManager = getPackageManager();
    	List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(facebookIntent,  PackageManager.MATCH_DEFAULT_ONLY);

    	boolean resolved = false;
    	for(ResolveInfo resolveInfo: resolvedInfoList){
    	    if(resolveInfo.activityInfo.packageName.startsWith("com.facebook.katana")){
    	    	facebookIntent.setClassName(
    	            resolveInfo.activityInfo.packageName, 
    	            resolveInfo.activityInfo.name );
    	        resolved = true;
    	        break;
    	    }
    	}
    	if(resolved){
    	    startActivity(facebookIntent);
    	}else{
    	    Toast.makeText(this, "Facebook app isn't found", Toast.LENGTH_LONG).show();
    	}
    }
    
    public void launchPhoneIntent()
    {
    	
    }
    
    public void launchWebIntent()
    {
    	
    }
}
