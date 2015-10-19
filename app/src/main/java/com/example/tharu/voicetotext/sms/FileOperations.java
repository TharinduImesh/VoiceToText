package com.example.tharu.voicetotext.sms;

/**
* @author THARINDU
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class FileOperations extends Activity {
    /** Called when the activity is first created. */

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Intent i=getIntent();
	        String file = i.getStringExtra("filename");							// get file name from another activity
	        String operation= i.getStringExtra("operation");					// get read or write command from another activity
	        String data = i.getStringExtra("data");								// get data to write
	        
	        if(operation.equalsIgnoreCase("write")){							// if command is write
	        	createFile(file,data);											// create file and write data
	        	finish();														// finish this activity
	        }
	        else{
			        String result= readFile(file);								// read data from given file
			        Intent send_intent= new Intent();
					send_intent.putExtra("data", result);						// send read data 
					setResult(RESULT_OK,send_intent);	        	
		        	finish();													// finish this activity
	        }
	    }

	    private void createFile(String filename,String text){					// create file

	    	FileOutputStream fos=null;
	    	try {
				fos=openFileOutput(filename, MODE_PRIVATE);						// create and open file
				fos.write(text.getBytes());										// write data
				if(text.equals("-1")){
					Toast.makeText(getApplicationContext(), "Password removed", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getApplicationContext(), "Saving succesfully", Toast.LENGTH_SHORT).show();
				}
				
			} catch (FileNotFoundException e) {
				 Log.e("CreateFile", e.getLocalizedMessage());
			}
			catch (IOException e) {
				 Log.e("CreateFile", e.getLocalizedMessage());
			}

			finally{
				if(fos!=null){
					try {
						// drain the stream
						fos.flush();	
						fos.close();											// close the file
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	    }

	    private String readFile(String file){									// read file
	    	String result="";
	    	FileInputStream fis;

	    	try {
				fis=openFileInput(file);										// open file
				byte[] reader=new byte[fis.available()];						// read
				while (fis.read(reader)!=-1) {

				}
			    result= new String(reader);
			    if(fis!=null){
			    	fis.close();												// close file
			    }
			}
	    	catch (FileNotFoundException e) {
				e.printStackTrace();
				result="-1";
			}
			catch (IOException e) {
				Log.e("Read File", e.getLocalizedMessage());
			}
	    	
	    	if(result.equalsIgnoreCase("-1")){
	    		return null;
	    	}
	    	else{
	    		return result;
	    	}
	    }

	    public boolean delete(String filename){                  // use when password is wanted to remove
	    	File f= new File(filename);
	    	return f.delete();
	    }
	}
