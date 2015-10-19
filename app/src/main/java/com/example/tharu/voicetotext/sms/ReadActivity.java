package com.example.tharu.voicetotext.sms;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tharu.voicetotext.support.AlertDialogManager;
import com.example.tharu.voicetotext.support.ConnectionDetector;
import com.example.tharu.voicetotext.R;
import com.example.tharu.voicetotext.backup.SendMailActivity;

public class ReadActivity extends Activity {
	private ConnectionDetector cd;
	int position;
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read);
		
		Intent i= getIntent();
		final String name = i.getStringExtra("name");
		final String number = i.getStringExtra("number");
		final String message = i.getStringExtra("data");
		position = i.getIntExtra("position", -1);
		String date = i.getStringExtra("date");
		
		Date time = new Date(Long.parseLong(date));
		String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(time);
		
		@SuppressWarnings("deprecation")
		int hour= time.getHours();
		@SuppressWarnings("deprecation")
		int minute= time.getMinutes();
		String quater= "";
		if(hour <12){
			quater = "AM";
		}
		else{
			quater= "PM";
		}
		String formattedTime = (hour+":")+(minute+" ")+quater;
		TextView text= (TextView)findViewById(R.id.textView1);
		text.setText(name);
		
		text= (TextView)findViewById(R.id.textView2);
		text.setText(number);
		
		text= (TextView)findViewById(R.id.textView3);
		text.setText(message);
		
		text= (TextView)findViewById(R.id.textView4);
		text.setText("Date : "+formattedDate+"\nTime : "+formattedTime);
		
		ImageButton reply= (ImageButton)findViewById(R.id.imageButton1);
		reply.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ReadActivity.this,SMSActivity.class);
//				intent.putExtra("message", message);
				intent.putExtra("contact", name+"\n"+number);
				startActivity(intent);
			}
		});
		
		ImageButton forward= (ImageButton)findViewById(R.id.imageButton2);
		forward.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ReadActivity.this,SMSActivity.class);
				intent.putExtra("message", message);
				startActivity(intent);
				finish();
			}
		});
		
		ImageButton cloud= (ImageButton)findViewById(R.id.imageButton3);
		cloud.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				cd = new ConnectionDetector(getApplicationContext());
				 // Alert Dialog Manager
			    AlertDialogManager alert = new AlertDialogManager();

		        // Check if Internet present
		        if (!cd.isConnectingToInternet()) {
		            // Internet Connection is not present
		            alert.showAlertDialog(ReadActivity.this, "Internet Connection Error","Please connect to working Internet connection", false);
		            // stop executing code by return
		            return;
		        }
		        else{		        
		        	Intent intent=new Intent(ReadActivity.this,SendMailActivity.class);
					intent.putExtra("message", message+"\n"+number);		
					startActivity(intent);
		        }	
				
				finish();
			}
		});		
		
	}	
	
}
