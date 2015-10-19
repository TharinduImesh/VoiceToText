package com.example.tharu.voicetotext.sms;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.tharu.voicetotext.R;

public class DraftInboxActivity extends Activity {
	ListView list;
	Button delete;
	ImageButton cancel;
	static ArrayList<String> drafts;
	boolean isSelected = false;;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_class);
		list= (ListView)findViewById(android.R.id.list);
		DraftInboxActivity.drafts=init();
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(!isSelected){
					String data = DraftInboxActivity.drafts.get(position);
					Intent intent= new Intent(DraftInboxActivity.this,SendDraftActivity.class);
					intent.putExtra("message",data );
					startActivity(intent);
				}
			}
		});
		
		delete = (Button)findViewById(R.id.button1);
		delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri uriSms = Uri.parse("content://sms/draft"); 
                Cursor c = getContentResolver().query(uriSms, new String[] { "_id", "thread_id", "address","person", "date", "body" }, null, null, null); 
				SparseBooleanArray checked = list.getCheckedItemPositions();
				
		        for (int i = 0; i < checked.size(); i++) {
		            if (checked.valueAt(i)){
		            	try { 			                
			                int position = checked.keyAt(i);
			                if (c != null && c.moveToFirst()) { 
			                    do { 
			                        long id = c.getLong(0); 
			                        if(DraftInboxActivity.drafts.get(position).equals(c.getString(c.getColumnIndex("body")))){
			                        	getContentResolver().delete(Uri.parse("content://sms/" + id), null, null);
//			                        	Log.d("found", DraftInboxActivity.drafts.get(position)+" "+c.getString(c.getColumnIndex("body")));
			                        	Log.d("found", "delete");
			                        	break;
			                        }
			                        Log.d("not found", DraftInboxActivity.drafts.get(position)+" "+c.getString(c.getColumnIndex("body")));
			                    } while (c.moveToNext()); 
			                } 
		            } catch (Exception e) { 

		            } 
		            }
		        }
		        finish();
			}			
		});
		
		cancel = (ImageButton)findViewById(R.id.imageButton1);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ArrayAdapter<String> adapter =new ArrayAdapter<String>(DraftInboxActivity.this,android.R.layout.simple_list_item_1, drafts);		
				list.setAdapter(adapter);	
				delete.setVisibility(View.INVISIBLE);
				cancel.setVisibility(View.INVISIBLE);
				DraftInboxActivity.this.isSelected=false;
			}
		});
	}
	
	public ArrayList<String>  init(){
		
		ArrayList<String> drafts =new ArrayList<String>();
		Uri uriSms = Uri.parse("content://sms/draft");
		Cursor cursor = this.getContentResolver().query(uriSms,new String[] { "_id", "address", "date", "body","type", "read" }, null, null,null);
		
		if (cursor != null) {			
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {					
				do {
//					if(cursor.getString(cursor.getColumnIndex("address"))!=null){
//						drafts.add(cursor.getString(cursor.getColumnIndex("body"))+"\n"+cursor.getString(cursor.getColumnIndex("address")));
//					}
//					else{
						drafts.add(cursor.getString(cursor.getColumnIndex("body")));
//					}
				} while (cursor.moveToNext());
			}
		}
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(DraftInboxActivity.this, android.R.layout.simple_list_item_1, drafts);
		list.setAdapter(adapter);
		return drafts;
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
			isSelected=true;
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void selectActivity(){		
		ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,DraftInboxActivity.drafts );
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		list.setAdapter(adapter);	
		delete.setVisibility(View.VISIBLE);
		cancel.setVisibility(View.VISIBLE);
	}
	
}
