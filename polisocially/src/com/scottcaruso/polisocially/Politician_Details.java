package com.scottcaruso.polisocially;

import java.util.ArrayList;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Politician_Details extends Activity {
	
	public class Constants {

		public static final String CONSUMER_KEY = "W5f9ce3TCVVdFi97yHVoQg";
		public static final String CONSUMER_SECRET= "0JKuxFLPfWfu2lLv0veIm2OHogmrNuSPlcHWgEAtSD4";

		public static final String REQUEST_URL = "http://api.twitter.com/oauth/request_token";
		public static final String ACCESS_URL = "http://api.twitter.com/oauth/access_token";
		public static final String AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";

		final public static String CALLBACK_SCHEME = "x-latify-oauth-twitter";
		final public static String CALLBACK_URL = CALLBACK_SCHEME + "://callback";

		}
	
	public static ListView listview;
	public static ArrayAdapter<String> postsAdapter;
	public static ArrayList<String> postsList;
	private SharedPreferences prefs;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politician_details);
        
        postsList = new ArrayList<String>();
        postsList.add("Tweet 1");
        postsList.add("Tweet 2");
        postsList.add("Tweet 3");
        postsList.add("Tweet 4");
        postsList.add("Tweet 5");
        postsList.add("Tweet 6");
        postsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, postsList);
        listview = (ListView) findViewById(R.id.posts);
        listview.setAdapter(postsAdapter);
        
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Button signIn = (Button) findViewById(R.id.signin);
        signIn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), PrepareRequestTokenActivity.class);
				//i.putExtra("tweet_msg",getTweetMsg());
				startActivity(i);
				}	
			});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_politician_details, menu);
        return true;
    }
    
    //Launch webview for Twitter signin
    public class WebViewLaunchActivity extends Activity {
    	String url;
    	
    	@SuppressLint("SetJavaScriptEnabled")
		@Override
    	public void onCreate(Bundle savedInstanceState) {
    		super.onCreate(savedInstanceState);
    		Bundle extras = getIntent().getExtras();
    		if (extras!=null){
    			url = extras.getString("url");
    		}
    		
    		WebView webview = new WebView(this);
    		webview.getSettings().setJavaScriptEnabled(true);
    		webview.setVisibility(View.VISIBLE);
    		setContentView(webview);

    		/* WebViewClient must be set BEFORE calling loadUrl! */
    		webview.setWebViewClient(new WebViewClient() {

    			@Override
    			public void onPageStarted(WebView view, String url,Bitmap bitmap) {
    			Log.i("info","onPageStarted : " + url);
    			}
    			
    			@Override
    			public void onPageFinished(WebView view, String url) {
    				//sview.setVisibility(View.INVISIBLE);

    				if (url.startsWith(Constants.CALLBACK_URL)) {
    					try {

    						if (url.indexOf("oauth_token=")!=-1) {
    							view.setVisibility(View.INVISIBLE);
    							goBack(url);

    						} else if (url.indexOf("error=")!=-1) {
    							view.setVisibility(View.INVISIBLE);

    						}

    					} catch (Exception e) {
    						e.printStackTrace();
    					}
    				}
    			}
    		});
    		webview.loadUrl(url);

    	}

    	private void goBack(String url){
    		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    		final Uri uri = Uri.parse(url);
    		if (uri != null && uri.getScheme().equals(Constants.CALLBACK_SCHEME)) {
    			new RetrieveAccessTokenTask(this,PrepareRequestTokenActivity.getConsumer(),PrepareRequestTokenActivity.getProvider(),prefs).execute(uri);
    			finish();
    		}
    	}
    	public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

	    	private Context	context;
	    	private OAuthProvider provider;
	    	private OAuthConsumer consumer;
	    	private SharedPreferences prefs;

	    	public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,OAuthProvider provider, SharedPreferences prefs) {
	    		this.context = context;
	    		this.consumer = consumer;
	    		this.provider = provider;
	    		this.prefs=prefs;
	    	}
    	
	    	/**
	    	* Retrieve the oauth_verifier, and store the oauth and oauth_token_secret
	    	* for future API calls.
	    	*/
	    	protected Void doInBackground(Uri...params) {
	    		final Uri uri = params[0];
	    		final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

	    		try {
	    			provider.retrieveAccessToken(consumer, oauth_verifier);

			    	final Editor edit = prefs.edit();
			    	edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
			    	edit.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
			    	edit.commit();

			    	String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
			    	String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
			
			    	consumer.setTokenWithSecret(token, secret);
			    	context.startActivity(new Intent(context,Politician_Details.class));
			
			    	Log.i("info",edit.toString());
			    	Log.i("info",prefs.toString());
			    	executeAfterAccessTokenRetrieval();

	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}

	    		return null;
	    	}

	    	private void executeAfterAccessTokenRetrieval() {
	    		String msg = getIntent().getExtras().getString("tweet_msg");
	    		try {
	    			//TwitterUtils.sendTweet(prefs, "This is a tweet");
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    	}
    	}
    }
    
    public static class PrepareRequestTokenActivity extends Activity{
    	final String TAG = getClass().getName();
        
        private static OAuthConsumer consumer; 
        private static OAuthProvider provider;
        
         @Override
         public void onCreate(Bundle savedInstanceState) {
        	 super.onCreate(savedInstanceState);
        	 try {
        		 PrepareRequestTokenActivity.consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
        		 PrepareRequestTokenActivity.provider = new CommonsHttpOAuthProvider(Constants.REQUEST_URL,Constants.ACCESS_URL,Constants.AUTHORIZE_URL);
            } catch (Exception e) {
                 Log.e(TAG, "Error creating consumer / provider",e);
            }

            Log.i(TAG, "Starting task to retrieve request token.");
            new OAuthRequestTokenTask(this,consumer,provider).execute();
            }

            public static OAuthProvider getProvider() {
			// TODO Auto-generated method stub
			return provider;
		}

			public static OAuthConsumer getConsumer() {
            
			return consumer;
            }

			/**
             * Called when the OAuthRequestTokenTask finishes (user has authorized the request token).
             * The callback URL will be intercepted here.
             */
            @Override
            public void onNewIntent(Intent intent) {
                    super.onNewIntent(intent); 
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    final Uri uri = intent.getData();
                    if (uri != null && uri.getScheme().equals(Constants.CALLBACK_SCHEME)) {
                        Log.i(TAG, "Callback received : " + uri);
                        Log.i(TAG, "Retrieving Access Token");
                        new RetrieveAccessTokenTask(this,consumer,provider,prefs).execute(uri);
                        finish();       
                    }
            }
            
            public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

                    private Context context;
                    private OAuthProvider provider;
                    private OAuthConsumer consumer;
                    private SharedPreferences prefs;
                    
                    public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,OAuthProvider provider, SharedPreferences prefs) {
                            this.context = context;
                            this.consumer = consumer;
                            this.provider = provider;
                            this.prefs=prefs;
                    }


                    /**
                     * Retrieve the oauth_verifier, and store the oauth and oauth_token_secret 
                     * for future API calls.
                     */
                    @Override
                    protected Void doInBackground(Uri...params) {
                            final Uri uri = params[0];
                            final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

                            try {
                                provider.retrieveAccessToken(consumer, oauth_verifier);

                                final Editor edit = prefs.edit();
                                edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
                                edit.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
                                edit.commit();
                                
                                String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
                                String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
                                
                                consumer.setTokenWithSecret(token, secret);
                                //context.startActivity(new Intent(context,SocialActivity.class));

                                executeAfterAccessTokenRetrieval();
                                
                                Log.i(TAG, "OAuth - Access Token Retrieved");
                                    
                            } catch (Exception e) {
                                    Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
                            }

                            return null;
                    }


                    private void executeAfterAccessTokenRetrieval() {
                        String msg = getIntent().getExtras().getString("tweet_msg");
                        try {
                        	//TwitterUtils.sendTweet(prefs, msg);
                        	Log.i("info",msg);
                        } catch (Exception e) {
                        	Log.e(TAG, "OAuth - Error sending to Twitter", e);
                        }
                    }
            }       
    }
    
    public static class OAuthRequestTokenTask extends AsyncTask<Void, Void, Void> {

        final String TAG = getClass().getName();
        private Context _context;
        private OAuthProvider _provider;
        private OAuthConsumer _consumer;

        /**
         * 
         * We pass the OAuth consumer and provider.
         * 
         * @param       context
         *                      Required to be able to start the intent to launch the browser.
         * @param       provider
         *                      The OAuthProvider object
         * @param       consumer
         *                      The OAuthConsumer object
         */
        public OAuthRequestTokenTask(Context context, OAuthConsumer consumer, OAuthProvider provider) {
                _context = context;
                _consumer = consumer;
                _provider = provider;
        }

        /**
         * 
         * Retrieve the OAuth Request Token and present a browser to the user to authorize the token.
         * 
         */
        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String url = _provider.retrieveRequestToken(_consumer, Constants.CALLBACK_URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND);
                _context.startActivity(intent);
            } catch (Exception ex) {
            	
            }
            return null;
        }
    }
  
}
