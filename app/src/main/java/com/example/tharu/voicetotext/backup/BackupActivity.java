package com.example.tharu.voicetotext.backup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tharu.voicetotext.sms.FileOperations;
import com.example.tharu.voicetotext.R;

public class BackupActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.backup);
		
		final EditText username = (EditText)findViewById(R.id.editText1);
		final EditText password = (EditText)findViewById(R.id.editText2);
		final EditText confirmPassword = (EditText)findViewById(R.id.editText3);
		final TextView notification = (TextView)findViewById(R.id.textView4);
		final ImageButton ok= (ImageButton)findViewById(R.id.imageButton1);
		
		
//		final String pw= password.getText().toString();
//		final String confirmpw = confirmPassword.getText().toString();
//		String auto="false";
//		if(automatic.isChecked()){
//			auto = "true";
//		}
//		final String backupSchedule = auto;
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(username.getText().toString() !=null){										
					if( password.getText().toString() != null){
						if(confirmPassword.getText().toString()!= null){							
							if(password.getText().toString().equals(confirmPassword.getText().toString())){
////								settings.setAddress(username.getText().toString());
////								settings.setPassword(confirmPassword.getText().toString());
////								settings.setAutomatic(automatic.isChecked());
//								
								String data =username.getText().toString()+","+confirmPassword.getText().toString();
								Intent intent= new Intent(BackupActivity.this, FileOperations.class);
								intent.putExtra("filename", "backup.txt");
								intent.putExtra("data", data);
								intent.putExtra("operation", "write");
								startActivity(intent);
								finish();
////								username.setText("");
////								password.setText("");
////								confirmPassword.setText("");
////								notification.setText("");
							}
							else{
								notification.setVisibility(View.VISIBLE);
								notification.setText("Password is not match");
							}
						}
						else{
							notification.setVisibility(View.VISIBLE);
							notification.setText("please retype password");
						}
					}
					else{
						notification.setVisibility(View.VISIBLE);
						notification.setText("Password is Empty");						
					}
				}
				else{
					notification.setVisibility(View.VISIBLE);
					notification.setText("User Name is Empty");
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
	
//	
}
