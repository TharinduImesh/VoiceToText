package com.example.tharu.voicetotext.sms;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.tharu.voicetotext.R;

public class SearchMessage extends Activity{
	private static final int REQUEST_CODE = 100;
	ArrayList<MessageData> smsList;
	int position;
	static ListView list;
	static String [] array;
	ArrayList<String> numbers,names;
	Button backup,delete;
	ImageButton cancel;
	boolean isDelete;
	ProgressDialog pDialog;
	/*---------------------------------------*/
	ArrayList<MessageData> data,sent,inbox;
	ArrayList<String> thread_ids,temp;
	ContentResolver contentResolver;
	String[] projection;
	int l=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_class);
		smsList = new ArrayList<MessageData>();
		data = new ArrayList<MessageData>();
		thread_ids = new ArrayList<String>();
		sent = new ArrayList<MessageData>();
		inbox = new ArrayList<MessageData>();
		contentResolver = getContentResolver();
		numbers = new ArrayList<String>();
		names = new ArrayList<String>();
		projection = new String[]{"_id", "thread_id", "address", "body","date"};
		
		list = (ListView) findViewById(android.R.id.list);		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				
				if(!isDelete){
					SearchMessage.this.position= position;
					read();
				}
				
			}
		});
		
		search();
		
		
		delete = (Button)findViewById(R.id.button1);
		delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent=new Intent(SearchMessage.this,DeleteMessage.class);
				SparseBooleanArray checked = list.getCheckedItemPositions();
		        for (int i = 0; i < checked.size(); i++) {
		            // Item position in adapter
		            int position = checked.keyAt(i);
		            if (checked.valueAt(i)){
		            	Intent intent=new Intent(SearchMessage.this,DeleteMessage.class);
			    		intent.putExtra("thread_id", smsList.get(position).getThread_id());
			    		intent.putExtra("message", smsList.get(position).getData());
			    		startActivity(intent);
		            }
		        }
		        finish();
			}
		});
		
		cancel = (ImageButton)findViewById(R.id.imageButton1);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SearchMessage.array=getArray(smsList);
				ArrayAdapter<String> adapter =new ArrayAdapter<String>(SearchMessage.this,android.R.layout.simple_list_item_1, SearchMessage.array);		
				list.setAdapter(adapter);	
				SearchMessage.this.isDelete=false;
				delete.setVisibility(View.INVISIBLE);
				cancel.setVisibility(View.INVISIBLE);
			}
		});
	}
	
//	@Override
//	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(arg0, arg1, arg2);
//		if(arg0 == REQUEST_CODE && arg1 == RESULT_OK ){
//			String text= arg2.getStringExtra("text");
//			Toast t= Toast.makeText(this, text, Toast.LENGTH_SHORT);
//			t.show();
//			
//			if(text.contains("send")){
//				forward();
//			}
//			else if(text.contains("i")){
//				edit();
//			}
//			else if(text.contains("up")){
//				backup();
//			}
//			else if(text.contains("e")){
//				delete();
//				finish();
//			}
//			else if(text.contains("l")){
//				reply();
//			}
//			else if (text.contains("o")){
//				search();
//			}	
//			else{				
//				searchByName(text);
//			}
//		}
//	}
	
	public void search(){
	AsyncTask<Void, Void, Void> search_task= new AsyncTask<Void, Void, Void>(){

		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchMessage.this);
            pDialog.setMessage("Searching.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
		
		@Override
		protected Void doInBackground(Void... params) {
			newProcess();
			SearchMessage.array=getArray(smsList);
			return null;
		}
		
		protected void onPostExecute(Void result) {
            // dismiss the dialog after getting all products
			if(pDialog!=null){
				pDialog.dismiss();				
				display(SearchMessage.array);
			}
           
        }
	};
	
	search_task.execute((Void[])null);
	}
	
	public void searchByName(String name){
		AsyncTask<String, Void, String> search_task= new AsyncTask<String, Void, String>(){

			@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(SearchMessage.this);
	            pDialog.setMessage("Searching.....");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	        }
			
			@Override
			protected String doInBackground(String... params) {
				getThread();
				getSentBox();	
				getInbox();
				temp=getArrayByName(smsList, params);
				return null;
			}
			
			protected void onPostExecute(String result) {
	            // dismiss the dialog after getting all products
				if(pDialog!=null){
					pDialog.dismiss();				
					display2(temp);
				}
	           
	        }
		};
		
		search_task.execute(name);
		}
	public void getThread(){
		Uri uri = Uri.parse("content://mms-sms/conversations");
		Cursor query = contentResolver.query(uri, projection, null, null, null);
		if (query.moveToFirst()) {
		    do {
		        String string = query.getString(query.getColumnIndex("body")).concat(query.getString(query.getColumnIndex("thread_id")));
		        if ("application/vnd.wap.multipart.related".equals(string)) {
		            // it's MMS
		        } else {
		        	thread_ids.add(query.getString(query.getColumnIndex("thread_id")));
		        	numbers.add(getName(query.getString(query.getColumnIndex("address"))));
		        }
		    } while (query.moveToNext());
		    query.close();
		}		
	}
	
	public void getSentBox(){
		Uri uri = Uri.parse("content://sms/sent");
        Cursor query = contentResolver.query(uri, projection, null, null, null);
        for(int i=0; i< thread_ids.size();i++){
	    	if (query.moveToFirst()) {
	    	    do {
	       		   	if(thread_ids.get(i).equals(query.getString(query.getColumnIndex("thread_id")))){
	       		   		MessageData ms =new MessageData();
	        		    ms.setThread_id(query.getString(query.getColumnIndex("thread_id")));
	        		    ms.setId(query.getString(query.getColumnIndex("_id")));
	        		    ms.setData(query.getString(query.getColumnIndex("body")));
	        		    ms.setDate(query.getString(query.getColumnIndex("date")));
	        		    ms.setPhone_number(query.getString(query.getColumnIndex("address")));
	        		    ms.setName(numbers.get(i));
	        		    ms.setState("sent");
	        		    sent.add(ms);
	        	    }
	       		} while (query.moveToNext());	        		    
	        }
        }
        query.close();
	}
	
	public void getInbox(){
		Uri uri = Uri.parse("content://sms/inbox");
        Cursor query = contentResolver.query(uri, projection, null, null, null);
        for(int i=0; i< thread_ids.size();i++){
	    	if (query.moveToFirst()) {
	    	    do {
	    	    	if(thread_ids.get(i).equals(query.getString(query.getColumnIndex("thread_id")))){
	    	    		MessageData ms =new MessageData();
	        		    ms.setThread_id(query.getString(query.getColumnIndex("thread_id")));
	        		    ms.setId(query.getString(query.getColumnIndex("_id")));
	        		    ms.setData(query.getString(query.getColumnIndex("body")));
	        		    ms.setDate(query.getString(query.getColumnIndex("date")));
	        		    ms.setPhone_number(query.getString(query.getColumnIndex("address")));
	        		    ms.setName(numbers.get(i));
//	        		    ms.setName(getName(query.getString(query.getColumnIndex("address"))));
	        		    ms.setState("received");
	        		    inbox.add(ms);
	        		}
	        	} while (query.moveToNext());	        		    
	        }
        }
        query.close();
	}
//	public Cursor [] getCursors(){
//		Cursor [] c= new Cursor[2];
//		Uri uriSms = Uri.parse("content://sms/sent");
//		Cursor cursor = this.getContentResolver().query(uriSms,new String[] { "_id", "address", "date", "body","type", "read" }, null, null,null);
//		c[0] = cursor;
//		
//		uriSms = Uri.parse("content://sms/inbox");
//		cursor = this.getContentResolver().query(uriSms,new String[] { "_id", "address", "date", "body","type", "read" }, null, null,null);
//		c[1] = cursor;
//		return c;
//	}
//	
	
//	public ArrayList<MessageData> getMessages(Cursor [] cursor,String name){
//
//		smsList = new ArrayList<MessageData>();
//		for (int i=0;i<2;i++){
//			if (cursor [i] != null) {			
//				cursor[i].moveToFirst();
//				if (cursor[i].getCount() > 0) {					
//					do {
//						MessageData message = new MessageData();
//						if(!name.equalsIgnoreCase("all")){
//							message.setPhone_number(cursor[i].getString(cursor[i].getColumnIndex("address")));							
//							if(message.getPhone_number().equalsIgnoreCase(getcontactNumber(name))){
//								message.setData(cursor[i].getString(cursor[i].getColumnIndex("body")));
//								message.setDate(cursor[i].getString(cursor[i].getColumnIndex("date")));
//								message.setId(cursor[i].getString(cursor[i].getColumnIndex("_id")));								
//								message.setType(cursor[i].getString(cursor[i].getColumnIndex("type")));
//								message.setName(name);
//								smsList.add(message);
//							}
//							
//							Toast ti= Toast.makeText(this, message.getPhone_number(), Toast.LENGTH_SHORT);
//							ti.show();
//						}
//						else{
//							message.setPhone_number(cursor[i].getString(cursor[i].getColumnIndex("address")));
//							message.setName(getName(message.getPhone_number()));
//							message.setData(cursor[i].getString(cursor[i].getColumnIndex("body")));
//							message.setDate(cursor[i].getString(cursor[i].getColumnIndex("date")));
//							message.setId(cursor[i].getString(cursor[i].getColumnIndex("_id")));
//							message.setType(cursor[i].getString(cursor[i].getColumnIndex("type")));
//							smsList.add(message);
//						}
//					} while (cursor[i].moveToNext());
//				}
//			}
//			cursor[i].close();
//		}
//		return smsList;
//	}
	@SuppressLint("SimpleDateFormat")
	public String[] getArray(ArrayList<MessageData> data){
		
		String [] array = new String [data.size()];
		for(int i=0;i<data.size();i++){
			String word = data.get(i).getData();
			if(word.length()> 30){
				word= word.substring(0, 30);
				if(data.get(i).getName()!= null){
					array[i]=data.get(i).getName()+"\n"+data.get(i).getState()+"\n"+word+"........";
				}
				else{
					array[i]=data.get(i).getPhone_number()+"\n"+data.get(i).getState()+"\n"+word+"........";
				}
			}
			else{
				if(data.get(i).getName()!=null){
					array[i]=data.get(i).getName()+"\n"+data.get(i).getState()+"\n"+word;
				}
				else{
					array[i]=data.get(i).getPhone_number()+"\n"+data.get(i).getState()+"\n"+word;
				}
				
			}
		}
		return array;
	}
	
	@SuppressLint("SimpleDateFormat")
	public ArrayList<String> getArrayByName(ArrayList<MessageData> data,String... name){
		
		ArrayList<String> array = new ArrayList<String>();
		for(int i=0;i<data.size();i++){
			if(data.get(i).getName().equals(name[0])){
				String word = data.get(i).getData();
				if(word.length()> 30){
					word= word.substring(0, 30);
				}
				array.add(data.get(i).getPhone_number()+"\n"+data.get(i).getState()+"\n"+word+"........");
			}
		}
		return array;
	}
	public void display(String [] data){
		ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, data);		
		list.setAdapter(adapter);
	}
	
	public void display2(ArrayList<String> d){
		ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, d);		
		list.setAdapter(adapter);
	}
//	public String getcontactNumber (String contactName) {
//		String phoneNumber="";
//		Cursor phoneCursor = getContentResolver().query(   ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);
//		while (phoneCursor.moveToNext()) {
//			String name =phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//			if(name.equalsIgnoreCase(contactName)){
//				phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//			}
//		}
//		return phoneNumber;
//	}
	
	public String getName(String contactNumber){
		
		if(contactNumber.contains("+")){
			contactNumber = contactNumber.replace("+94", "0");
		}
		String name="";
		Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);
		while (c.moveToNext()) {
			String number =c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			if(number.equalsIgnoreCase(contactNumber)){
				name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			}	   	
		}
		if(name == null){
			return contactNumber;
		}
		else{
			return name;
		}
	}
	
//	public void forward(){
//		Intent intent=new Intent(SearchMessage.this,SMSActivity.class);
//		intent.putExtra("message", smsList.get(this.position).getData());
//		startActivity(intent);
//	}
//	
//	public void edit(){
//		Intent intent=new Intent(SearchMessage.this,NewMessage.class);
//		intent.putExtra("message", smsList.get(this.position).getData());
//		startActivity(intent);
//	}
//	
	public void read(){
		Intent intent=new Intent(SearchMessage.this,ReadActivity.class);
		intent.putExtra("name", smsList.get(this.position).getName());
		intent.putExtra("data", smsList.get(this.position).getData());
		intent.putExtra("number", smsList.get(this.position).getPhone_number());
		intent.putExtra("date", smsList.get(this.position).getDate());
		intent.putExtra("thread_id", smsList.get(this.position).getThread_id());
		intent.putExtra("position", this.position);
		startActivityForResult(intent, REQUEST_CODE);
//		finish();
	}
	
//	public void backup(){
//		Intent intent=new Intent(SearchMessage.this,SendMailActivity.class);
//		intent.putExtra("message", smsList.get(position).getData()+"\n"+smsList.get(position).getPhone_number());		
//		startActivity(intent);
//	}
//
//	public void delete(){
//		Log.d("sea", smsList.get(this.position).getData());
//		Intent intent=new Intent(SearchMessage.this,DeleteMessage.class);
//		intent.putExtra("number", smsList.get(this.position).getPhone_number());
//		intent.putExtra("message", smsList.get(this.position).getData());
//		intent.putExtra("id", smsList.get(this.position).getId());
//		startActivity(intent);
//	}
	
	public void reply(){
		String contact = smsList.get(this.position).getName()+"\n"+smsList.get(this.position).getPhone_number();
		Intent intent=new Intent(SearchMessage.this,SMSActivity.class);
		intent.putExtra("contact", contact);
		startActivity(intent);
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
		SearchMessage.array=getArray(smsList);		
		ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, SearchMessage.array);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		list.setAdapter(adapter);	
		this.isDelete=true;
		delete.setVisibility(View.VISIBLE);
		cancel.setVisibility(View.VISIBLE);
	}
	
	
	public void newProcess(){
		
		projection = new String[]{"_id", "thread_id", "address", "body","date"};
		
		Uri uri = Uri.parse("content://mms-sms/conversations");
		Cursor query = contentResolver.query(uri, projection, null, null, null);
		if (query.moveToFirst()) {
		    do {
		        String string = query.getString(query.getColumnIndex("body")).concat(query.getString(query.getColumnIndex("thread_id")));
		        if ("application/vnd.wap.multipart.related".equals(string)) {
		            // it's MMS
		        } else {
		        	thread_ids.add(query.getString(query.getColumnIndex("thread_id")));
		        	numbers.add(query.getString(query.getColumnIndex("address")));
		        }
		    } while (query.moveToNext());
		    query.close();
		}
		
		for(int i=0;i<numbers.size();i++){
			names.add(getName(numbers.get(i)));
		}
		
		for(int i=thread_ids.size()-1; i>0 ;i--){
			
			sent = new ArrayList<MessageData>();
			inbox = new ArrayList<MessageData>();
			uri = Uri.parse("content://sms/sent");
        	query = contentResolver.query(uri, projection, null, null, null);
        		
	        		if (query.moveToFirst()) {
	        		    do {
	        		    	if(thread_ids.get(i).equals(query.getString(query.getColumnIndex("thread_id")))){
//	        		        	String string = "sent"+query.getString(query.getColumnIndex("thread_id"))+" "+query.getString(query.getColumnIndex("_id"))+" "+query.getString(query.getColumnIndex("body"))+" "+query.getString(query.getColumnIndex("date"));
//	        		        	String string = query.getString(query.getColumnIndex("thread_id"));	  
	        		        	MessageData ms =new MessageData();
	        		        	ms.setThread_id(query.getString(query.getColumnIndex("thread_id")));
	        		        	ms.setId(query.getString(query.getColumnIndex("_id")));
	        		        	ms.setData(query.getString(query.getColumnIndex("body")));
	        		        	ms.setDate(query.getString(query.getColumnIndex("date")));
	        		        	ms.setPhone_number(query.getString(query.getColumnIndex("address")));
	        		        	ms.setName(names.get(i));
//	        		        	ms.setPhone_number(numbers.get(i));
	        		        	ms.setState("sent");
	        		        	sent.add(ms);
	        		        }
	        		    } while (query.moveToNext());
	        		    
	        		}
        		query.close();
        		
        		
        		uri = Uri.parse("content://sms/inbox");
        		query = contentResolver.query(uri, projection, null, null, null);
	        		if (query.moveToFirst()) {
	        		    do {
	        		    	if(thread_ids.get(i).equals(query.getString(query.getColumnIndex("thread_id")))){
//	        		        	String string = "received"+query.getString(query.getColumnIndex("thread_id"))+" "+query.getString(query.getColumnIndex("_id"))+" "+query.getString(query.getColumnIndex("body"))+" "+query.getString(query.getColumnIndex("date"));
//	        		        	String string = query.getString(query.getColumnIndex("thread_id"));	  
	        		    		MessageData ms =new MessageData();
	        		        	ms.setThread_id(query.getString(query.getColumnIndex("thread_id")));
	        		        	ms.setId(query.getString(query.getColumnIndex("_id")));
	        		        	ms.setData(query.getString(query.getColumnIndex("body")));
	        		        	ms.setDate(query.getString(query.getColumnIndex("date")));
	        		        	ms.setPhone_number(query.getString(query.getColumnIndex("address")));
	        		        	ms.setName(names.get(i));
//	        		        	ms.setPhone_number(numbers.get(i));
	        		        	ms.setState("received");
	        		        	inbox.add(ms);
	        		        }
	        		    } while (query.moveToNext());
	        		    
	        		}
        		query.close();
        		
        		if(sent.size()>inbox.size()){
        			l=sent.size();
        		}
        		else{
        			l=inbox.size();
        		}
        		
        		for(int m=0,j=0,k=0; m<l;m++){
        			if(j== sent.size()){
        				while(k< inbox.size()){
        					smsList.add(inbox.get(k));
        					k++;
        				}
        				break;
        			}
        			else if(k== inbox.size()){
        				while(j< sent.size()){
        					smsList.add(sent.get(j));
        					j++;
        				}
        				break;
        			}
        			else {
        				long s = Long.parseLong(sent.get(j).getDate());
        				long in = Long.parseLong(inbox.get(k).getDate());
        				if(j<sent.size()){			
        					if(s> in){
        						smsList.add(sent.get(j));
        						j++;
        					}
        					else{
        						smsList.add(inbox.get(k));
        						k++;					
        					}
        				}
        			}
        		}
        		
        		sent=null;
        		inbox=null;
		}
	}

	
}
