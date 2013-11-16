package com.scottcaruso.listadapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
 
public class ImageLoader {
 
    //final int stub_id = R.drawable.no_image;
    public void DisplayImage(Bitmap bitmap, ImageView imageView)
    {
    	//This is where the DisplayImage will be.
    }
 
    private Bitmap getBitmap(String name)
    {
    	//Will get the bitmap from the file directory
    	return null;
    }
}
