package com.scottcaruso.polisocially;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_politician_details, menu);
        return true;
    }
}
