package com.example.tharu.voicetotext.backup;

public class EmailSettings {
	private String address;
	private String password;
	private boolean automatic;
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPassword(String password) {
		this.password = password;
	}
 	public String getAddress() {
		return address;//"imeshpunchihewa@gmail.com";//address;
	}
 	public String getPassword() {
		return password;//"1124Imesh1991#";//
	}
 	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}
 	public boolean getAutomatic(){
 		return automatic;
 	}
 	
}
