package com.example.tharu.voicetotext.sms;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.tharu.voicetotext.R;

public class SearchContactNumber extends Activity {
	ArrayList<String> contactList;
	ListView list;
	ImageButton ok,cancel;
	boolean check=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		list = (ListView)findViewById(android.R.id.list);
		ok = (ImageButton)findViewById(R.id.imageButton1);
		cancel = (ImageButton)findViewById(R.id.imageButton2);
		Intent intent = getIntent();
		String input= intent.getStringExtra("input");
		Cursor cursor=getContactList();
		if(input.equalsIgnoreCase("list")){
			display(cursor);
		}
		else{
			Toast t=Toast.makeText(this,input, Toast.LENGTH_LONG);
			t.show();
			getNumber(input, cursor);
		}
		
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent send_intent=new Intent();
				SparseBooleanArray checked = list.getCheckedItemPositions();
				String data="";
		        for (int i = 0; i < checked.size(); i++) {
		            // Item position in adapter
		            int position = checked.keyAt(i);
		            if (checked.valueAt(i)){
		                data= data.concat(contactList.get(position)+"\n");			    		
		            }
		        }
		        send_intent.putExtra("numbers", data);
		        setResult(RESULT_OK,send_intent);
		        finish();
			}
		});
		
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ArrayAdapter<String> adapter =new ArrayAdapter<String>(SearchContactNumber.this,android.R.layout.simple_list_item_1, contactList);		
				list.setAdapter(adapter);	
				ok.setVisibility(View.INVISIBLE);
				cancel.setVisibility(View.INVISIBLE);
				SearchContactNumber.this.check=false;
			}
		});
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(!check){
					String contactData = contactList.get(position);
					Intent intent=new Intent();
			   	    intent.putExtra("numbers", contactData);
			   	    setResult(RESULT_OK, intent);
			   	    finish();
				}				
			}
		});
	}
	public Cursor getContactList(){
		Cursor phoneCursor = getContentResolver().query(   ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);
	   	Toast t= Toast.makeText(this, "done", Toast.LENGTH_LONG);
	   	t.show();
	   	return phoneCursor; 
    }

	public void display(Cursor c){
		//ArrayList<String> 
		contactList=new ArrayList<String>();
		while (c.moveToNext()) {
			String name =c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	        String phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	        contactList.add(name+"\n"+phoneNumber);
		}
		
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,contactList);
		list.setAdapter(adapter);
	}

	public void getNumber(String contactName, Cursor c){
		String phoneNumber="";
		while (c.moveToNext()) {
			String name =c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			if(name.equalsIgnoreCase(contactName)){
				phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			}
	        
			Toast t= Toast.makeText(this, phoneNumber, Toast.LENGTH_LONG);
	   	    t.show();
	   	    Intent intent=new Intent();
	   	    intent.putExtra("number", phoneNumber);
	   	    setResult(RESULT_OK, intent);
	   	    finish();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.item1) {
			selectActivity();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void selectActivity(){
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,contactList);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		list.setAdapter(adapter);	
		this.check = true;
		ok.setVisibility(View.VISIBLE);
		cancel.setVisibility(View.VISIBLE);
	}
	
}
