package com.example.tharu.voicetotext.sms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tharu.voicetotext.sms.DatabaseActivity;

public class DatabaseHandlerActivity extends Activity {
	
	private String email_password;
	private String userName;
	private boolean state;
	private String pin;
	private String password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent= getIntent();
		userName = intent.getStringExtra("username");
		email_password=intent.getStringExtra("epw");
		password=intent.getStringExtra("spw");
		pin = intent.getStringExtra("pin");
		state = intent.getBooleanExtra("automatic",false);
		DatabaseActivity database=new DatabaseActivity(this);
		
			if(userName != null){
				database.insertBackupData(userName, email_password, state);
				finish();
			}
			else if(password.equalsIgnoreCase("remove") && pin.equalsIgnoreCase("remove")){	
				Toast.makeText(this, "yes1", Toast.LENGTH_LONG).show();
				database.insertSecurityData("remove", "remove");
				finish();
			}
			else{
				Toast.makeText(this, password+" "+pin, Toast.LENGTH_LONG).show();
				database.insertSecurityData(password, pin);
				finish();
			}
	}
}
