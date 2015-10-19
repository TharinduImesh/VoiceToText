package com.example.tharu.voicetotext.sms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tharu.voicetotext.R;

public class SendDraftActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_draft);
		
		Intent intent= getIntent();
		final EditText text= (EditText)findViewById(R.id.editText1);
		text.setText(intent.getStringExtra("message"));
		
		ImageButton send=(ImageButton)findViewById(R.id.imageButton3);
		send.setVisibility(View.VISIBLE);
		send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent= new Intent(SendDraftActivity.this,SMSActivity.class);
				intent.putExtra("message",text.getText().toString() );
				startActivity(intent);
				finish();
			}
		});
		
		ImageButton close=(ImageButton)findViewById(R.id.imageButton2);
		close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
}
