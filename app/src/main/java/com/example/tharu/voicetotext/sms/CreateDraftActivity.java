package com.example.tharu.voicetotext.sms;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tharu.voicetotext.R;

public class CreateDraftActivity extends Activity {
	EditText text;
	ContentResolver contentResolver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_draft);
		contentResolver = getContentResolver();
		
		text = (EditText)findViewById(R.id.editText1);
		ImageButton save= (ImageButton)findViewById(R.id.imageButton1);
		save.setVisibility(View.VISIBLE);
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri drafts= Uri.parse("content://sms/draft");
				ContentValues values = new ContentValues();
				values.put("body", text.getText().toString());
				values.put("type",3);
				values.put("address","+1234567890");
				long date = System.currentTimeMillis()-60000; //just to test
				values.put("date", date - date % 1000);
				values.put("read", 1);
				contentResolver.insert(drafts, values);	
				
				finish();
			}
		});
		
		ImageButton cancel= (ImageButton)findViewById(R.id.imageButton2);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
}
