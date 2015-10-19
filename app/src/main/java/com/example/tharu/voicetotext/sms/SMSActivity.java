package com.example.tharu.voicetotext.sms;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tharu.voicetotext.R;

public class SMSActivity extends Activity {
	private static final int REQUEST_CODE = 100;
	EditText editTxt;
	private String [] contactdata;
	private String contactNumber;
	private String message;
	private AutoCompleteTextView textView;
	ArrayList<String> contactList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		contacts();
		Intent intent= getIntent();
		message= intent.getStringExtra("message");
//		type= intent.getStringExtra("type");		
//		Toast t= Toast.makeText(this, message, Toast.LENGTH_LONG);
//		t.show();
		editTxt = (EditText) findViewById(R.id.editText1);
		editTxt.setText(message);
		
		
		textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);	
		textView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.test_list_item, contactList));
		
		textView.setText(intent.getStringExtra("contact"));
		ImageButton searchContactButton= (ImageButton)findViewById(R.id.imageButton1);
		searchContactButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent= new Intent(SMSActivity.this,SearchContactNumber.class);
				intent.putExtra("input", "list");
				startActivityForResult(intent,REQUEST_CODE);
			}
		});
		ImageButton iButton= (ImageButton) findViewById(R.id.imageButton2);
		iButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SMSActivity.this,VoiceRecognition.class);
		    	startActivityForResult(intent,REQUEST_CODE);
		//		getContactList("Tharindu");
			//	editTxt.setText("12345667");
			}
		});
		//editTxt.setText(contactNumber);
		
		ImageButton send_button = (ImageButton)findViewById(R.id.imageButton3);
		send_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {				

				String data= ((EditText) findViewById(R.id.autoCompleteTextView1)).getText().toString();
				
				if(textView.getText().toString()!=null){
					contactdata= data.split("[\n]");				
					if(contactdata.length >2){
						for(int j=1;j<contactdata.length;j+=2){
							Intent i=new Intent(SMSActivity.this,MessageSend.class);
							i.putExtra("message", editTxt.getText().toString());
							i.putExtra("contactnumber", contactdata[j]);
							startActivity(i);
						}
						finish();
					}
					else if(contactdata.length == 2){
						contactNumber = contactdata[1];					
						Intent i=new Intent(SMSActivity.this,MessageSend.class);
						i.putExtra("message",  editTxt.getText().toString());						
						i.putExtra("contactnumber", contactNumber);
				    	startActivity(i);
				    	finish();
					}
					else{
						Intent i=new Intent(SMSActivity.this,MessageSend.class);
						i.putExtra("message", editTxt.getText().toString());						
						i.putExtra("contactnumber", contactdata[0]);
				    	startActivity(i);
				    	finish();
					}
				}
			}
		});
		
		ImageButton cancel_button = (ImageButton)findViewById(R.id.imageButton4);
		cancel_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {		    	
		    	finish();
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
		
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0 == REQUEST_CODE && arg1 == RESULT_OK ){
			String name= arg2.getStringExtra("name");
			String text= arg2.getStringExtra("numbers");
			
			if(name!=null){
				textView.setText(getContact(name));
			}
			
			if(text!=null){
				textView.setText(text);
			}
		}
	}
	
	public void contacts(){
		Cursor c = getContentResolver().query(   ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);
		contactList=new ArrayList<String>();
		while (c.moveToNext()) {
			String name =c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	        String phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	        contactList.add(name+"\n"+phoneNumber);
		}
	}
	
	public String getContact(String name){
			String contact=name;
			
			Cursor c = getContentResolver().query(   ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);
			while (c.moveToNext()) {
				String original =c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				if(name.equalsIgnoreCase(original)){
					contact = contact.concat("\n"+c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
				}
			}
		   	if(contact.equals(name)){
		   		return null;
		   	}
		   	else{
		   		return contact;
		   	}
	}
}

