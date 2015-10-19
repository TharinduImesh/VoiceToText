package com.example.tharu.voicetotext.backup;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tharu.voicetotext.R;
import com.example.tharu.voicetotext.sms.FileOperations;

public class SendMailActivity extends Activity {
	private static final int REQUEST_CODE = 100;
	String compte_email, compte_motdepasse;
	String to, Sujet, Message;
	EditText editCompteEmail, editCompteMotdepasse;
	EditText editTo, editSujet, editMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_mail);
//		final Button send = (Button) this.findViewById(R.id.envoi);
//		send.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
				// TODO Auto-generated method stub
		Intent intent= getIntent();
		Message = intent.getStringExtra("message");                              // get the sms from other activity
		Intent file= new Intent(SendMailActivity.this, FileOperations.class);
		file.putExtra("operation", "read");
		file.putExtra("filename", "backup.txt");
		startActivityForResult(file, REQUEST_CODE);
		
		TextView text= (TextView)findViewById(R.id.textView1);						
		text.setText("Mail Successfully Sent");
		
		ImageButton cancel= (ImageButton)findViewById(R.id.imageButton1);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
//				Intent intent= getIntent();
				
//				editCompteEmail = (EditText) findViewById(R.id.compte_mail);
//				editCompteMotdepasse = (EditText) findViewById(R.id.compte_motpasse);
//				editTo = (EditText) findViewById(R.id.to);
//				editSujet = (EditText) findViewById(R.id.object);
//				editMsg = (EditText) findViewById(R.id.Msg);

//				compte_email = settings.getAddress();//editCompteEmail.getText().toString();
//				compte_motdepasse =settings.getPassword();//editCompteMotdepasse.getText().toString();
//				to = settings.getAddress();//editTo.getText().toString();
//				List<String> toEmailList = Arrays.asList(to.split("\\s*,\\s*"));
//
//				Sujet = "sms";//editSujet.getText().toString();
//				Message = intent.getStringExtra("message");//editMsg.getText().toString();
//
//				new SendMailTask(SendMailActivity.this).execute(compte_email,compte_motdepasse, toEmailList, Sujet, Message);
				
//				editCompteEmail.setText("");
//				editCompteMotdepasse.setText("");
//				editTo.setText("");
//				editSujet.setText("");
//				editMsg.setText("");

//			}
//		});

	}
	
	public void process(String address, String password){
		
		compte_email = address;//editCompteEmail.getText().toString();
		compte_motdepasse =password;//editCompteMotdepasse.getText().toString();
		to = address;//editTo.getText().toString();
		List<String> toEmailList = Arrays.asList(to.split("\\s*,\\s*"));

		Sujet = "sms";//editSujet.getText().toString();
	//	Message = message;//editMsg.getText().toString();

		new SendMailTask(SendMailActivity.this).execute(compte_email,compte_motdepasse, toEmailList, Sujet, Message);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0 == REQUEST_CODE && arg1 == RESULT_OK ){
			String text= arg2.getStringExtra("data");
			if(text!=null){
				String [] data = text.split("[,]");
				process(data[0], data[1]);
			}
		}
	}
}

