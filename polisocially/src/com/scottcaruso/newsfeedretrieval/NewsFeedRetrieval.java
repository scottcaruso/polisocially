package com.scottcaruso.newsfeedretrieval;

import com.scottcaruso.sunlightlabsretrievalclasses.RetrieveDataFromSunlightLabs;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class NewsFeedRetrieval extends IntentService {
	
	public static final String MESSENGER_KEY = "messenger";
	public static final String POL_NAME = "politicianname";

	public NewsFeedRetrieval() {
		super("NewsFeedRetrieval");
	}

	@Override
	protected void onHandleIntent(Intent intent) 
	{		
		Log.i("onHandleIntent","Service started");
		
		Bundle extras = intent.getExtras();
		Messenger messenger = (Messenger) extras.get(MESSENGER_KEY);
		String politicianname = (String) extras.get(POL_NAME);
		//Get a string back from NPR using a custom Class housed in a different Java file.

		String newsResponse = RetrieveDataFromNPR.retrieveData("http://api.npr.org/query?fields=title,titles&searchTerm="+politicianname+"&dateType=story&output=JSON&searchType=mainText&apiKey=MDEyNjM3MTM0MDEzODQ5MjY0NDE5ZTRkNg001");
		//Pass the string back so that it can be parsed into JSON by the DisplayActivity
		
		Message message = Message.obtain();
		message.arg1 = Activity.RESULT_OK; //Tell the MainActivity that the service worked.
		message.obj = newsResponse; //Store the JSON Object to the Main Activity so it can do its magic.
		
		try 
		{
			messenger.send(message);
		} catch (RemoteException e) {
			Log.e("messenger","There was a problem sending the message.");
		}
	}

}
