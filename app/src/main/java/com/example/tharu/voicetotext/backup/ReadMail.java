package com.example.tharu.voicetotext.backup;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

	public class ReadMail extends Activity {
	   static Message [] messages;
	   
	   @Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			 String host = "pop.gmail.com";// change accordingly
		      String mailStoreType = "pop3";
		      String username = "imeshpunchihewa@gmail.com";// change accordingly
		      String password = "1124Imesh1991#";// change accordingly

		      check(host, mailStoreType, username, password);
		}
	   public void check(String host, String storeType, String user,String password) 
	   {
	      try {

	      //create properties field
	      Properties properties = new Properties();

	      properties.put("mail.pop3.host", host);
	      properties.put("mail.pop3.port", "995");
	      properties.put("mail.pop3.starttls.enable", "true");
	      Session emailSession = Session.getDefaultInstance(properties);
	      Toast.makeText(ReadMail.this, "connect", Toast.LENGTH_LONG).show();
	      //create the POP3 store object and connect with the pop server
	      Store store = emailSession.getStore("pop3s");

	      store.connect(host, user, password);
	      
	      //create the folder object and open it
//	      Folder emailFolder = store.getFolder("INBOX");
//	      emailFolder.open(Folder.READ_ONLY);
//
//	      // retrieve the messages from the folder in an array and print it
//	      messages = emailFolder.getMessages();
//	      System.out.println("messages.length---" + messages.length);
//
//	      for (int i = 0, n = messages.length; i < n; i++) {
//	         Message message = messages[i];
//	         System.out.println("---------------------------------");	         
//	         System.out.println("Email Number " + (i + 1));
//	         System.out.println("Subject: " + message.getSubject());
//	         System.out.println("From: " + message.getFrom()[0]);
//	         System.out.println("Text: " + message.getContent().toString());

//	         Toast.makeText(ReadMail.this, "Subject: " + message.getSubject(), Toast.LENGTH_LONG).show();
//	      }

	      //close the store and folder objects
//	      emailFolder.close(false);
//	      store.close();

	      } catch (NoSuchProviderException e) {
	         e.printStackTrace();
	      } catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }

	
}

