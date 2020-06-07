package com.myapplication.model;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import javafx.collections.ObservableList;

public class EmailAccountBean {

	private String emailAddress;
	private String password;
	private Properties properties;
	private Store store;
	private Session session;
	private int login=EmailConstants.LOGIN_STATE_NOT_READY;
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public Store getStore() {
		return store;
	}
	
	public Session getSession() {
		return session;
	}
	 
	public int getLogin() {
		return login;
	}
	
	public String  getPassword()
	{
		return password;
	}
	public Properties getProperties()
	{
		return properties;
	}
	
	public EmailAccountBean(String emailAddress , String password)
	{
		this.emailAddress=emailAddress;
		this.password=password;
		
		properties=new Properties();
		properties.put("mail.store.protocol", "imaps");
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.host","smtp.gmail.com");
		properties.put("mail.smtp.auth", true);
		properties.put("incomingHost","imap.gmail.com");
		properties.put("outgoingHost","smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable",true);
		Authenticator authenticate=new Authenticator(){
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(emailAddress,password);
			}
		};
		
		
		session=Session.getInstance(properties,authenticate);
		
		try{
		 this.store=session.getStore();
		 store.connect(properties.getProperty("incomingHost"),emailAddress,password);
		 System.out.println("EmailAccountBean success");
		 login=EmailConstants.LOGIN_STATE_SUCCEDED;
		}
	    catch(Exception e)
		{
			e.printStackTrace();
			login=EmailConstants.LOGIN_STATE_FAILED_BY_CREDENTIALS;
		}
	}
	/*
	public void addEmailsToData(ObservableList<EmailMessengerBean> data)
	{
		Folder folder;
		try {
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			for(int i=folder.getMessageCount();i>0;i--)
			{
				Message message=folder.getMessage(i);
				EmailMessengerBean messageBean=new EmailMessengerBean(message.getSubject(),message.getFrom()[0].toString(),message.getSize(),"",message.getFlags().contains(Flag.SEEN));
		        System.out.println("Got"+messageBean);
		        data.add(messageBean);
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
	*/
}

