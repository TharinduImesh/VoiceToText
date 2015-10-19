package com.example.tharu.voicetotext.backup;
/**
*
* @author THARINDU
*/

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import android.util.Log;

public class GMail {		
	
	final String emailPort = "587";														// gmail's smtp port
	final String smtpAuth = "true";
	final String starttls = "true";
	final String emailHost = "smtp.gmail.com";
	 

	String fromEmail;
	String fromPassword;
	List<String> toEmailList;
	String emailSubject;
	String emailBody;

	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;

	public GMail() {

	}
																					// override the constructor
	public GMail(String fromEmail, String fromPassword,List<String> toEmailList, String emailSubject, String emailBody) {
		this.fromEmail = fromEmail;													// initialize email address
		this.fromPassword = fromPassword;											// initialize email password
		this.toEmailList = toEmailList;												// initialize destination email address
		this.emailSubject = emailSubject;											// initialize email subject
		this.emailBody = emailBody;													// initialize email body

		emailProperties = System.getProperties();									// initialize protocal's details
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", smtpAuth);
		emailProperties.put("mail.smtp.starttls.enable", starttls);
		Log.i("GMail", "Mail server properties set.");
	}

	public MimeMessage createEmailMessage() throws AddressException,MessagingException, UnsupportedEncodingException {

		mailSession = Session.getDefaultInstance(emailProperties, null);			// use mail sessions to create new mail
		emailMessage = new MimeMessage(mailSession);

		emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));			// set sender address and receiver address.
		for (String toEmail : toEmailList) {
			Log.i("GMail","toEmail: "+toEmail);
			emailMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
		}

		emailMessage.setSubject(emailSubject);										// set email subject
		emailMessage.setContent(emailBody, "text/html");							// for a html email
		// emailMessage.setText(emailBody);// for a text email
		Log.i("GMail", "Email Message created.");
		return emailMessage;
	}

	public void sendEmail() throws AddressException, MessagingException {			// send email 

		Transport transport = mailSession.getTransport("smtp");						// set protocal
		transport.connect(emailHost, fromEmail, fromPassword);						// connect with Gmail
		Log.i("GMail","allrecipients: "+emailMessage.getAllRecipients());
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		Log.i("GMail", "Email sent successfully.");
	}

}
