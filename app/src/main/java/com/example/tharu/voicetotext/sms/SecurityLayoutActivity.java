package com.example.tharu.voicetotext.sms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tharu.voicetotext.R;

public class SecurityLayoutActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.security);
		 
			
		final EditText password = (EditText)findViewById(R.id.editText1);
		final EditText confim = (EditText)findViewById(R.id.editText2);
		final ImageButton ok= (ImageButton)findViewById(R.id.imageButton1);
		final TextView error= (TextView)findViewById(R.id.textView3);
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(password.getText().toString().equals(confim.getText().toString())){
					final String data = password.getText().toString();
					
					Intent intent= new Intent(SecurityLayoutActivity.this,FileOperations.class);
					intent.putExtra("data", data);
					intent.putExtra("operation", "write");
					intent.putExtra("filename", "security.txt");
					startActivity(intent);
					finish();
//					Toast.makeText(SecurityLayoutActivity.this, details.getPassword(), Toast.LENGTH_SHORT).show();
//					password.setText("");
//					pin.setText("");	
					
				}
				else{
					error.setText("Password is not matching....");
				}
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
