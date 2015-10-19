package com.example.tharu.voicetotext.support;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tharu.voicetotext.R;
import com.example.tharu.voicetotext.sms.SecurityAutentication;

public class SettingsActivity extends ListActivity {
	ListView listview;
	String [] list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		
		setContentView(R.layout.search_class);
		init();
	}
	
	public void init(){
		this.list = new String [2];
		this.list[0] = "Backup";
		this.list[1] = "Security";
		
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_list_item_1, this.list);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
//		String item = 
		switch (position){
			case 0 : backup();break;
			case 1 : security();break;
		}
	}
	
	public void backup(){
		
		Intent intent= new Intent(SettingsActivity.this,SecurityAutentication.class);
		intent.putExtra("next", "backup");
		startActivity(intent);
	}
	
	public void security(){
		Intent intent= new Intent(SettingsActivity.this,SecurityAutentication.class);
		intent.putExtra("next", "security");
		startActivity(intent);
	}
}
