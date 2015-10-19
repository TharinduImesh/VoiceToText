package com.example.tharu.voicetotext.sms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tharu.voicetotext.R;

public class SecurityOptions extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_option);
		
		Button set=(Button)findViewById(R.id.button1);
		set.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SecurityOptions.this, SecurityLayoutActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		Button remove= (Button)findViewById(R.id.button2);
		remove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent= new Intent(SecurityOptions.this,FileOperations.class);
				intent.putExtra("data","-1");
				intent.putExtra("operation", "write");
				intent.putExtra("filename", "security.txt");
				startActivity(intent);
				finish();
			}
		});
	}
}
