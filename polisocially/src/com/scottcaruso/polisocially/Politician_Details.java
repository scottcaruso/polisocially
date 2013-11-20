package com.scottcaruso.polisocially;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.scottcaruso.newsfeedretrieval.NewsFeedRetrieval;
import com.scottcaruso.newsfeedretrieval.TurnNPRStringIntoJSONObject;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Politician_Details extends Activity {
	
	public static ListView listview;
	public static String polName;
	public static String partyName;
	public static String birthday;
	public static String termEnd;
	public static String govTrackID;
	public static String photoID;
	public static String response;
	public static ArrayList<String> stories;
	public static ArrayList<String> links;
	
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
    	Log.i("Info","DON'T FORGET TO ADD WEBSITES");
    	Log.i("Info","DON'T FORGET TO ADD PHONE NUMBERS");
        
        this.setTitle(polName);
        runNewsFeedAPI();
        
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
        int id = Politician_Details.this.getResources().getIdentifier(photoID, "drawable", Politician_Details.this.getPackageName());
        polImage.setImageResource(id);
        
        //Set up action buttons
        govTrackView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				launchWebIntent("http://www.govtrack.us/congress/members/"+govTrackID);	
			}
		});
        
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
        
        Button phoneCallButton = (Button) findViewById(R.id.buttonPhone);
        phoneCallButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				launchPhoneIntent();		
			}
		});
        
        Button websiteButton = (Button) findViewById(R.id.buttonWebsite);
        websiteButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				launchWebIntent("http://www.google.com/");		
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
    	Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "407-267-3533"));
    	startActivity(intent);
    }
    
    public void launchWebIntent(String website)
    {
    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(website)); 
    	startActivity(intent);
    }
    
    public void runNewsFeedAPI()
    {
    	Handler retrievalHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg) 
			{
				if (msg.arg1 == RESULT_OK)
				{
					try {
						response = (String) msg.obj;
					} catch (Exception e) {
						Log.e("Error","There was a problem retrieving the json Response.");
					}
					JSONObject newsStories = TurnNPRStringIntoJSONObject.createMasterObject(response);
					try {
						addItemsToArrayList(newsStories);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					createListView(stories);	
					}
				}
		};
		Messenger apiMessenger = new Messenger(retrievalHandler);
			
		Intent startDataService = new Intent(this, NewsFeedRetrieval.class);
		startDataService.putExtra(NewsFeedRetrieval.MESSENGER_KEY, apiMessenger);
		startDataService.putExtra(NewsFeedRetrieval.POL_NAME,polName);
		this.startService(startDataService);
     	}
    
    public void addItemsToArrayList (JSONObject storyObject) throws JSONException
    {
    	stories = new ArrayList<String>();
    	links = new ArrayList<String>();
    	if (storyObject == null)
    	{
    		return;
    	} else
    	{
    		JSONArray storyArray = storyObject.getJSONArray("Links");
    		for (int x = 0; x < storyArray.length(); x++)
    		{
    			JSONObject thisObject = storyArray.getJSONObject(x);
    			stories.add(thisObject.getString("Story"));
    			links.add(thisObject.getString("Link"));
    		}
    	}
    }
    
    public void createListView (ArrayList<String> newsStories)
    {
    	ListView newsList = (ListView) findViewById(R.id.newsList);
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newsStories);
    	newsList.setAdapter(adapter);
    	newsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int item,
					long id) {
				launchWebIntent(links.get(item));
			}
		});
    }
}
