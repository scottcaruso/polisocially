
package com.scottcaruso.dataretrievalclasses;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

//This Service Class talks to GovTrack and returns a string with relevant data about the zip code the user entered.

public class DataRetrievalService extends IntentService{

	public static final String MESSENGER_KEY = "messenger";
	public static final String ZIP_KEY = "URL"; //This key is for the user's entered Zip Code. It is appended to the URL below to make the call to the GovTrack API.
	public static final String LON_KEY = "LON";
	public static final String LAT_KEY = "LAT";
	
	public DataRetrievalService() 
	{
		super("DataRetrievalService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) 
	{		
		Log.i("onHandleIntent","Service started");
		
		Bundle extras = intent.getExtras();
		Messenger messenger = (Messenger) extras.get(MESSENGER_KEY);
		String lonString = (String) extras.get(LON_KEY);
		String latString = (String) extras.get(LAT_KEY);
		//Get a string back from SunLight labs using a custom Class housed in a different Java file.
		String dataResponse = RetrieveDataFromSunlightLabs.retrieveData("http://congress.api.sunlightfoundation.com/legislators/locate?latitude="+latString+"&longitude="+lonString+"&apikey=eab4e1dfef1e467b8a25ed1eab0f7544");
		//Log.i("Info","Response received based on "+dataResponse);
		//Pass the string back so that it can be parsed into JSON by the DisplayActivity
		
		Message message = Message.obtain();
		message.arg1 = Activity.RESULT_OK; //Tell the MainActivity that the service worked.
		message.obj = dataResponse; //Store the JSON Object to the Main Activity so it can do its magic.
		
		try 
		{
			messenger.send(message);
		} catch (RemoteException e) {
			Log.e("messenger","There was a problem sending the message.");
		}
	}
}
