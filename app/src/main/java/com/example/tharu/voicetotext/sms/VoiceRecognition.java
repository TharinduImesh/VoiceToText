package com.example.tharu.voicetotext.sms;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.EditText;
import android.widget.Toast;

public class VoiceRecognition extends Activity {
	protected static final int RESULT_SPEECH = 1;
//	private EditText editTxt;	
	private String result_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);							// use "SPEECH RECOGNIZE" api 
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");								// Informs the recognizer that which speech model should be preferred when performing ACTION_RECOGNIZE_SPEECH. 
																										// It is adapted to input messages in English.
		try {
			startActivityForResult(intent, RESULT_SPEECH);												// do voice recognition
			//editTxt.setText("");											
		} catch (ActivityNotFoundException a) {
			Toast toast = Toast.makeText(getApplicationContext(),"Ops! Your device doesn't support Speech to Text",Toast.LENGTH_SHORT);				// if device does not support to voice recognition display taost message 
			toast.show();
		}
		
//		Intent send_intent= new Intent();
//		//intent.putExtra("text", "new message");
//		send_intent.putExtra("text", result_text);
//		setResult(RESULT_OK,intent);
//		finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {														// set result text from voice recognition in to space that is allocated to type message 
				ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				//editTxt.setText(text.get(0));
				result_text= text.get(0).toString();
				Intent send_intent= new Intent();
				//intent.putExtra("text", "new message");
				send_intent.putExtra("text", result_text);
				setResult(RESULT_OK,send_intent);
				finish();
			}
			break;
		}

		}
	}
}
