 package com.example.tharu.voicetotext.sms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tharu.voicetotext.R;
import com.example.tharu.voicetotext.backup.BackupActivity;
import com.example.tharu.voicetotext.twitter.TwitterActivity;

 public class SecurityAutentication extends Activity {
	private static final int REQUEST_CODE = 100;
	EditText key;
	String data;
	String next;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent i= getIntent();
		next = i.getStringExtra("next");
		Intent intent=new Intent(SecurityAutentication.this,FileOperations.class);
		intent.putExtra("filename", "security.txt");
		intent.putExtra("operation", "read");
		startActivityForResult(intent, REQUEST_CODE);
		
	}
	
	public boolean passswordChecking(String entered,String owned){
		if(entered.equals(owned)){
			return true;
		}
		else{
			return false;
		}		
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0 == REQUEST_CODE && arg1 == RESULT_OK ){
			String text= arg2.getStringExtra("data");
			if(text!=null){
				data = text;
				nextProcess1();
			}
			else{				
				data =null;
				nextProcess2();
			}
			
		}
	}
	
	public void nextProcess1(){
		if(data!=null){
			setContentView(R.layout.activity_password_autentication);
			key = (EditText)findViewById(R.id.editText1);
			ImageButton ok= (ImageButton)findViewById(R.id.imageButton1);
			ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(passswordChecking(key.getEditableText().toString(),data) ){
						if(next.equalsIgnoreCase("search")){
							Intent intent=new Intent(SecurityAutentication.this,SearchMessage.class);    	
					    	startActivity(intent);
						}
						else if(next.equalsIgnoreCase("twitter")){
							       
								Intent intent=new Intent(SecurityAutentication.this,TwitterActivity.class);
								startActivity(intent);
						}

						else if(next.equalsIgnoreCase("security")){
							Intent intent=new Intent(SecurityAutentication.this,SecurityOptions.class);
							startActivity(intent);
						}
						else if(next.equalsIgnoreCase("backup")){
							Intent intent= new Intent(SecurityAutentication.this,BackupActivity.class);
							startActivity(intent);
						}
					}
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
	
	public void nextProcess2(){
		if(next.equalsIgnoreCase("search")){
			Intent intent=new Intent(SecurityAutentication.this,SearchMessage.class);    	
	    	startActivity(intent);
	    	finish();
		}
		else if(next.equalsIgnoreCase("twitter")){
				        
				Intent intent=new Intent(SecurityAutentication.this,TwitterActivity.class);		
				startActivity(intent);
				finish();
		}
		else if(next.equalsIgnoreCase("security")){
			Intent intent=new Intent(SecurityAutentication.this,SecurityOptions.class);
			startActivity(intent);
			finish();
		}
		else if(next.equalsIgnoreCase("backup")){
			Intent intent= new Intent(SecurityAutentication.this,BackupActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
