package com.example.tharu.voicetotext.sms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tharu.voicetotext.R;

public class DraftActivity extends Activity {
	ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		list= (ListView)findViewById(android.R.id.list);
		init();
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				switch(position){
					case 0: newDraft();break;
					case 1: inbox(); break;
				}
			}
		});
	}
	
	public void init(){
		String [] items = new String []{"New Draft","Inbox"};
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(DraftActivity.this, android.R.layout.simple_list_item_1, items);
		list.setAdapter(adapter);
	}
	
	public void newDraft(){
		Intent intent= new Intent(DraftActivity.this,CreateDraftActivity.class);
		startActivity(intent);
	}
	public void inbox(){
		Intent intent= new Intent(DraftActivity.this,DraftInboxActivity.class);
		startActivity(intent);
	}
}
