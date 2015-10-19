package com.example.tharu.voicetotext.sms;

public class MessageData {
	private String id;
	private String thread_id;
	private String name;
	private String phone_number;
	private String data;
	private String type;
	private String time;
	private String date;
	private String state;
	
//	public MessageData(String name, String phone, String type,String date,String time, String data, String state) {
//		this.data=data;
//		this.date=date;
//		this.type=type;
//		this.name=name;
//		this.phone_number=phone;
//		this.time=time;
//		this.state=state;
//	}
	public String getId() {
		return id;
	}
	public String getThread_id() {
		return thread_id;
	}
	public String getData() {
		return data;
	}
	public String getDate() {
		return date;
	}
	public String getName() {
		return name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public String getTime() {
		return time;
	}
	public String getState() {
		return state;
	}
	public String getType() {
		return type;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setThread_id(String thread_id) {
		this.thread_id = thread_id;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " "+data+" "+date+" "+time;
	}
}
