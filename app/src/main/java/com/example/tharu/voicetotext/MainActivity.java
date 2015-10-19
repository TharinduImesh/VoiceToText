package com.example.tharu.voicetotext;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.tharu.voicetotext.sms.NewMessage;
import com.example.tharu.voicetotext.sms.SearchMessage;
import com.example.tharu.voicetotext.support.AlertDialogManager;
import com.example.tharu.voicetotext.support.ConnectionDetector;
import com.example.tharu.voicetotext.support.SettingsActivity;
import com.example.tharu.voicetotext.twitter.TwitterActivity;

public class MainActivity extends Activity {
	private ConnectionDetector cd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
    	
		
		ImageButton message=(ImageButton)findViewById(R.id.imageButton1);
		message.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,NewMessage.class);
				intent.putExtra("message", "");
		    	startActivity(intent);				
			}
		});
	
		ImageButton search=(ImageButton)findViewById(R.id.imageButton2);
		search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,SearchMessage.class);
		    	startActivity(intent);	
			}
		});
		
		ImageButton twitter=(ImageButton)findViewById(R.id.imageButton3);
		twitter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
				 // Alert Dialog Manager
			    AlertDialogManager alert = new AlertDialogManager();

		        // Check if Internet present
		        if (!cd.isConnectingToInternet()) {
		            // Internet Connection is not present
		            alert.showAlertDialog(MainActivity.this, "Internet Connection Error","Please connect to working Internet connection", false);
		            // stop executing code by return
		            return;
		        }
		        else{		        
					Intent intent=new Intent(MainActivity.this,TwitterActivity.class);
					startActivity(intent);
		        }			
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent=new Intent(MainActivity.this,SettingsActivity.class);
	    	startActivity(intent);
			return true;
		}
//		if (id == R.id.action_draft) {
//			Intent intent=new Intent(MainActivity.this,DraftActivity.class); 
//	    	startActivity(intent);
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}

}
