/* Political Connectness
 * by Scott Caruso
 * APD2 1311 Term
 */
package com.scottcaruso.listadapter;

import java.util.ArrayList;

import com.scottcaruso.polisocially.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//This CustomAdapter is used for feeding data to the Politician Results screen. It 
//includes politician names, political parties, and images. It also handles advancing to 
//the Details screen.

public class CustomAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<String> thesePols;
    private ArrayList<String> theseParties;
    private ArrayList<String> thesePics;
    private static LayoutInflater inflater = null;
 
    public CustomAdapter (Activity a, ArrayList<String> pols, ArrayList<String> parties, ArrayList<String> photoIDs) {
        activity = a;
        thesePols = pols;
        theseParties = parties;
        thesePics = photoIDs;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return thesePols.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row,null);
 
        TextView name = (TextView)vi.findViewById(R.id.polName); // politician name
        TextView party = (TextView)vi.findViewById(R.id.party); // pol. party name
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        String thisPol = thesePols.get(position);
        String thisParty = theseParties.get(position);
        String thisImage = thesePics.get(position);
        // Setting all values in listview
        name.setText(thisPol);
        party.setText(thisParty);
        int id = activity.getResources().getIdentifier(thisImage, "drawable", activity.getPackageName());
        thumb_image.setImageResource(id);
        return vi;
    }
}