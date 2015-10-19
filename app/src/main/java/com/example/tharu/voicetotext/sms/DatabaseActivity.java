package com.example.tharu.voicetotext.sms;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Hashtable;
import java.util.ArrayList;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DatabaseActivity extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "MessageData";
   public static final String CONTACTS_SECURITY_TABLE = "security";
   public static final String CONTACTS_BACKUP_TABLE = "backup";
   public static final String CONTACTS_COLUMN_NAME = "username";
   public static final String CONTACTS_COLUMN_EMAILPASSWORD = "emailpassword";
   public static final String CONTACTS_COLUMN_STATE = "state";
   public static final String CONTACTS_COLUMN_PASSWORD = "password";
   public static final String CONTACTS_COLUMN_PIN = "pin";
   //private HashMap hp;

   public DatabaseActivity(Context context)
   {
      super(context, DATABASE_NAME , null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      db.execSQL("CREATE TABLE "+CONTACTS_BACKUP_TABLE+"("+CONTACTS_COLUMN_NAME+" text primary key, "+CONTACTS_COLUMN_EMAILPASSWORD+" text )");
      db.execSQL("CREATE TABLE "+CONTACTS_SECURITY_TABLE+"("+CONTACTS_COLUMN_PASSWORD+" text primary key, "+CONTACTS_COLUMN_PIN+" text , "+CONTACTS_COLUMN_STATE+" text )");
      Log.d("DatabaseActivity", "creating done");
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS "+CONTACTS_BACKUP_TABLE);
      db.execSQL("DROP TABLE IF EXISTS "+CONTACTS_SECURITY_TABLE);
      onCreate(db);
      Log.d("DatabaseActivity", "upgrading");
   }
   public boolean insertBackupData  (String username, String password,boolean automatic)
   {
	   try{
//		   deleteBackup();
		   SQLiteDatabase db = this.getWritableDatabase();
		   ContentValues contentValues = new ContentValues();

		   contentValues.put(CONTACTS_COLUMN_NAME, username);
		   contentValues.put(CONTACTS_COLUMN_EMAILPASSWORD, password);
		   contentValues.put(CONTACTS_COLUMN_STATE, automatic);
		   db.insert(CONTACTS_BACKUP_TABLE, null, contentValues);
		      
		   this.close();
		   Log.d("DatabaseActivity", "insert work "+username);		   
	   }
	   catch(SQLException s){
		   Log.d("DatabaseActivity", "insert does not work");
	   }      
      return true;
   } 
   public boolean insertSecurityData  (String password, String pin)
   {
	   try{
		   String [] pw = getSecurityData();
		   
		   SQLiteDatabase db = this.getWritableDatabase();
		   ContentValues contentValues = new ContentValues();

		   contentValues.put(CONTACTS_COLUMN_PASSWORD, password);
		   contentValues.put(CONTACTS_COLUMN_PIN, pin);		   
		   
//		   Log.d("Database insert Activity", "insert work "+password);
		 //  db.insert(CONTACTS_SECURITY_TABLE, null, contentValues);
//		   db.delete(CONTACTS_SECURITY_TABLE, CONTACTS_COLUMN_PASSWORD+" = ?", new String[] { pw[0] });    
		   Log.d("Database insert Activity", "delete "+pw[0]);
		   db.insert(CONTACTS_SECURITY_TABLE, null, contentValues);
		   Log.d("Database insert Activity", "insert work "+password);
		   this.close();		   
	   }
	   catch(SQLException s){
		   Log.d("DatabaseActivity", "insert does not work");
	   }      
      return true;
   } 
   public String [] getBackupData(){
      SQLiteDatabase db = this.getReadableDatabase();
      String [] columns = new String []{ CONTACTS_COLUMN_NAME, CONTACTS_COLUMN_EMAILPASSWORD};
      Cursor res = db.query(CONTACTS_BACKUP_TABLE, columns, null, null, null, null, null);
      
      String [] data= new String [3]; 
      int userName = res.getColumnIndex(CONTACTS_COLUMN_NAME);
      int password = res.getColumnIndex(CONTACTS_COLUMN_EMAILPASSWORD);
      int state = res.getColumnIndex(CONTACTS_COLUMN_STATE);
      if(res.moveToFirst()){
    	  data[0]= res.getString(userName);
    	  data[1]= res.getString(password);
    	  data[2]= res.getString(state);
      }
      
      db.close();
      return data;
   }
   
   public String [] getSecurityData(){
	      SQLiteDatabase db = this.getReadableDatabase();
	      String [] columns = new String []{ CONTACTS_COLUMN_PASSWORD, CONTACTS_COLUMN_PIN};
	      Cursor res = db.query(CONTACTS_SECURITY_TABLE, columns, null, null, null, null, null);
	      String [] data= new String [2];
	      int password = res.getColumnIndex(CONTACTS_COLUMN_PASSWORD);
	      int pin = res.getColumnIndex(CONTACTS_COLUMN_PIN);
	      
	      if(res.moveToFirst()){
	    	  data[0]= res.getString(password);
	    	  data[1]= res.getString(pin);
	      }
	      
	      Log.d("DatabaseActivity", "get data "+data[0]);
	      
	      db.close();
	      return data;
	   }
   
   
   public boolean updateData (String password, String pin)
   {
	  String [] pass = getSecurityData();
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put(CONTACTS_COLUMN_PASSWORD, password);
      contentValues.put(CONTACTS_COLUMN_PIN, pin);
      db.update(CONTACTS_SECURITY_TABLE, contentValues, "username = ? ", new String[] {pass[0]} );
      return true;
   }

   public boolean deleteBackup ()
   {
	   try{
		   SQLiteDatabase db = this.getWritableDatabase();
      //return db.delete("messages","name = ? ",new String[] {name});
//		   db.delete("messages", "date_time = "+time, null /*new String[]{time}*/);
		   db.delete(CONTACTS_BACKUP_TABLE, CONTACTS_COLUMN_NAME+" = ?", null/*new String[] { String.valueOf(book.getId()) }*/);
		   return true;
	   } catch (SQLException e) {
		   return false;
	   }
   }
   
   public boolean deleteSecurity(String id){
	   try{
		   SQLiteDatabase db = this.getWritableDatabase();
		   db.delete(CONTACTS_SECURITY_TABLE, CONTACTS_COLUMN_PASSWORD+" = ?", new String[] { id });		   
		   Log.d("DatabaseActivity", "delete");
		   db.close();
		   return true;
	   } catch (SQLException e) {
		   return false;
	   }
   }
}