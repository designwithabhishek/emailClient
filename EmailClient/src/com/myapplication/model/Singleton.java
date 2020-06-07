package com.myapplication.model;

public class Singleton {

	private static Singleton instance=new Singleton(); 
	private Singleton() {}
	public static Singleton getInstance()
	{
		return instance;
	}
	private EmailMessengerBean message;
	public EmailMessengerBean getMessage() {
		return message;
	}
	public void setMessage(EmailMessengerBean message) {
		this.message = message;
	}

	
	
}
