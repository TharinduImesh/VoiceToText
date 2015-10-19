package com.example.tharu.voicetotext.sms;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class DeleteMessage extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent= getIntent();
		String message= intent.getStringExtra("message");
		String thread_id= intent.getStringExtra("thread_id");
		delete(thread_id,message);
		Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
		finish();
	}
	

	public void delete(String thread_id,String data){
		boolean check=false;
		String[] projection = new String[]{"_id", "thread_id", "address", "body","date"};
		Uri uriSms = Uri.parse("content://sms/sent");
		Cursor c = getContentResolver().query(uriSms, projection,null,null,null); 
		if (c.moveToFirst()) {
		    do {
		    	
				String id = c.getString(c.getColumnIndex("_id"));
				String thread = c.getString(c.getColumnIndex("thread_id"));//get the thread_id
				String body = c.getString(c.getColumnIndex("body"));
				if(thread_id.equalsIgnoreCase(thread) && data.equals(body)){
					getContentResolver().delete(Uri.parse("content://sms/"+id),null,null);
					check=true;
					break;
				}				
		    } while (c.moveToNext());
		    c.close();
		}
		
		if(!check){
			uriSms = Uri.parse("content://sms/inbox");
			c = getContentResolver().query(uriSms, projection,null,null,null); 
			if (c.moveToFirst()) {
			    do {
			    	
					String id = c.getString(c.getColumnIndex("_id"));
					String thread = c.getString(c.getColumnIndex("thread_id"));//get the thread_id
					String body = c.getString(c.getColumnIndex("body"));
					if(thread_id.equalsIgnoreCase(thread) && data.equals(body)){
						getContentResolver().delete(Uri.parse("content://sms/"+id),null,null);
						break;
					}				
			    } while (c.moveToNext());
			    c.close();
			}
		}
	}
}
