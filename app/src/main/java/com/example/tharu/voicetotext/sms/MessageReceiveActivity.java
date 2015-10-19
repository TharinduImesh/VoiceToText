package com.example.tharu.voicetotext.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MessageReceiveActivity extends BroadcastReceiver {
																																// Get the object of SmsManager
	final SmsManager sms = SmsManager.getDefault();
	@Override
	public void onReceive(Context context, Intent intent) {																		// when message is received 
		final Bundle bundle = intent.getExtras();																				// Retrieves a map of extended data from the intent.
		 
		try {		     
		    if (bundle != null) {		         
		        final Object[] pdusObj = (Object[]) bundle.get("pdus");
		         
		        for (int i = 0; i < pdusObj.length; i++) {
		             
		            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
		            String phoneNumber = currentMessage.getDisplayOriginatingAddress();											// find sender's number
		             
		            String senderNum = phoneNumber;
		            String message = currentMessage.getDisplayMessageBody();													// get message body
		 
		            Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);									// Show alert
		            int duration = Toast.LENGTH_LONG;
		            Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
		            toast.show();
					
		        } 
		      } 																												// bundle is null
		 
		} catch (Exception e) {																									// handle exceptions
		    Log.e("SmsReceiver", "Exception smsReceiver" +e);
		     
		}
		
	}

}

