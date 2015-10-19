package com.example.tharu.voicetotext.sms;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class MessageSend extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra("message");
		String contactNumber = intent.getStringExtra("contactnumber");
	
		try {
			//PendingIntent pi = PendingIntent.getActivity(MessageButtonActivity.this,0,new Intent(MessageButtonActivity.this, .class), 0);
			if(contactNumber !=null && message !=null){											        // if contact number and message are not null
			    SmsManager smsManager = SmsManager.getDefault();								        // send message using "SmsManager"
			    smsManager.sendTextMessage(contactNumber,null,message,null,null);
			    Toast.makeText(getApplicationContext(), "Your sms has successfully sent!",Toast.LENGTH_LONG).show();			// if successful show this		
			}
			else{
				Toast.makeText(getApplicationContext(), "one is null!",Toast.LENGTH_LONG).show();	    // if contact number or message is not fill, show toast message
			}
		} catch (Exception ex) {
		    Toast.makeText(getApplicationContext(),"Your sms has failed...",Toast.LENGTH_LONG).show();	// if message can not be sent, show this toast message
		    ex.printStackTrace();
		}
		
		ContentValues values = new ContentValues();
		values.put("address", contactNumber);
		values.put("body", message);
		getContentResolver().insert(Uri.parse("content://sms/sent"), values);
		   
		finish();
	}
}
