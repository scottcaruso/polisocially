package com.scottcaruso.polisocially;

import org.w3c.dom.Document;

import com.scottcaruso.utilities.Connection_Verification;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        zipcodeClick();
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
    
    public void zipcodeClick() {
    	Button zipButton = (Button) findViewById(R.id.button1);
    	zipButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(Main_Screen.this);

				alert.setTitle("Enter Zip Code");
				alert.setMessage("Please enter a 5-digit zip code.");

				// Set an EditText view to get user input 
				final EditText input = new EditText(Main_Screen.this);
				input.setInputType(InputType.TYPE_CLASS_NUMBER);
				alert.setView(input);
				
				alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String zipEntered = input.getText().toString();
						if (zipEntered.length() != 5)
						{
							Log.i("Log","Invalid zip code entry: "+zipEntered);
				    		Toast toast = Toast.makeText(Main_Screen.this, "Please enter a valid 5-digit zip code.", Toast.LENGTH_LONG);
				    		toast.show();
						} else
						{
							Log.i("Log","The number entered was "+zipEntered+".");
						}
					}
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				
			}
		});	
    }
       
}
