package com.scottcaruso.polisocially;

import com.scottcaruso.utilities.Connection_Verification;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Main_Screen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__screen);
        boolean connection = connectionExists();
        //The connection logic below is simply for testing purposes. It will be hooked up when the required classes are built.
        if (connection)
        {
        	Log.i("Info","We are connected.");
    		Toast toast = Toast.makeText(this, "There is a connection to the network!", Toast.LENGTH_LONG);
    		toast.show();
        } else
        {
    		Log.i("Info","No internet connection found.");
    		Toast toast = Toast.makeText(this, "There is no connection to the internet available. Please try again later.", Toast.LENGTH_LONG);
    		toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main__screen, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	this.createAlert();
    	return true;
    }
    
    
    public void createAlert() {
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.about_app);
		AlertDialog alertDialog = alert.create();

		alertDialog.show();  
    }
    
    public boolean connectionExists() {
    	Boolean connected = Connection_Verification.areWeConnected(this);
    	return connected;
    }
       
}
