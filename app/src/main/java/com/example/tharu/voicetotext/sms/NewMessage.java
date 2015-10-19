package com.example.tharu.voicetotext.sms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tharu.voicetotext.support.AlertDialogManager;
import com.example.tharu.voicetotext.support.ConnectionDetector;
import com.example.tharu.voicetotext.R;
import com.example.tharu.voicetotext.backup.SendMailActivity;

public class NewMessage extends Activity {
	
	private static final int REQUEST_CODE = 100;
	//private String contactNumber;
	private String message,type; 
	private EditText editTxt;
	private ConnectionDetector cd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_message);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent= getIntent();
		message= intent.getStringExtra("message");
		
		editTxt = (EditText)findViewById(R.id.editText1);
		
		editTxt.setText(message);
		ImageButton start_button = (ImageButton)findViewById(R.id.imageButton1);											        // initialize "Start" button in second window
		start_button.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {																        // when touch "Start" button do voice recognition
				Intent intent=new Intent(NewMessage.this,VoiceRecognition.class);
		    	startActivityForResult(intent,REQUEST_CODE);
			
			}
		});
		
		ImageButton done_button = (ImageButton)findViewById(R.id.imageButton2);
		done_button.setOnClickListener(new View.OnClickListener() {											        // when user touch "Send" button  
			
			@Override
			public void onClick(View v) {
				Intent intent= new Intent(NewMessage.this, SMSActivity.class);
				intent.putExtra("message", editTxt.getText().toString());
				intent.putExtra("sms", type);
				startActivity(intent);
				editTxt.setText("");
			}
		});
		
		ImageButton cloud_button = (ImageButton)findViewById(R.id.imageButton4);
		cloud_button.setOnClickListener(new View.OnClickListener() {											        // when user touch "Send" button  
			
			@Override
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
				 // Alert Dialog Manager
			    AlertDialogManager alert = new AlertDialogManager();

		        // Check if Internet present
		        if (!cd.isConnectingToInternet()) {
		            // Internet Connection is not present
		            alert.showAlertDialog(NewMessage.this, "Internet Connection Error","Please connect to working Internet connection", false);
		            // stop executing code by return
		            return;
		        }
		        else{		        
					Intent intent=new Intent(NewMessage.this,SendMailActivity.class);
					intent.putExtra("message", editTxt.getText().toString());		
					startActivity(intent);
					
					editTxt.setText("");
		        }
			}
		});
		
		ImageButton close_button = (ImageButton)findViewById(R.id.imageButton3);
		close_button.setOnClickListener(new View.OnClickListener() {											        // when user touch "Send" button  
			
			@Override
			public void onClick(View v) {
		    	finish();
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {

		super.onActivityResult(arg0, arg1, arg2);
		if(arg0 == REQUEST_CODE && arg1 == RESULT_OK ){
			String text= arg2.getStringExtra("text");
			
//			if(text.equalsIgnoreCase("send")){
//				
//			}
////			else if(text.equalsIgnoreCase("facebook")){
//				cd = new ConnectionDetector(getApplicationContext());
//				 
//		        // Check if Internet present
//		        if (cd.isConnectingToInternet()) {
//		        	Intent intent=new Intent(NewMessage.this,TwitterActivity.class);
//					startActivity(intent);
//		            return;
//		        }
//		        else{
//					Intent intent = new Intent(NewMessage.this, MessageSend.class);
//					intent.putExtra("contactnumber", "32665");
//					intent.putExtra("message", message);
//					startActivity(intent);
//		        }
//			}
//			else if(text.equalsIgnoreCase("twitter")){
//				cd = new ConnectionDetector(getApplicationContext());
//				 
//		        // Check if Internet present
//		        if (cd.isConnectingToInternet()) {
//		        	Intent intent=new Intent(NewMessage.this,TwitterActivity.class);
//		        	intent.putExtra("message", editTxt.getText().toString());
//					startActivity(intent);
//		        }
//		        else{
//					Intent intent = new Intent(NewMessage.this, MessageSend.class);
//					intent.putExtra("contactnumber", "40404");
//					intent.putExtra("message", editTxt.getText().toString());
//					startActivity(intent);
//		        }
//			}
//			else if(text.contains("up")){
				
//			}
//			else if(text.contains("sa") || text.contains("ou")){
//				if(editTxt.getText().toString()!=null){
//					ContentValues values = new ContentValues();
//					values.put("body", editTxt.getText().toString());
//					getContentResolver().insert(Uri.parse("content://sms/draft"), values);
//					Toast.makeText(this, "save as a draft", Toast.LENGTH_SHORT).show();
//				}
//				finish();
//			}
//			else{
//				editTxt.setText(editTxt.getText().toString()+text);
				editTxt.append(text);
				message=editTxt.getText().toString();				
//				Toast retry= Toast.makeText(this, "Please retry...", Toast.LENGTH_LONG);
//				retry.show();
//			}
		}
	}
	
}
