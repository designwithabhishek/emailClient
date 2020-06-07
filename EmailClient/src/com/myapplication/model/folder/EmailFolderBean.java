package com.myapplication.model.folder;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.myapplication.model.EmailMessengerBean;
import com.myapplication.view.ViewFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class EmailFolderBean<T> extends TreeItem<String>{

	private boolean topElement=false;
	private int unreadMessageCount;
	private String name;
	private String completeName;
	private ObservableList<EmailMessengerBean> data=FXCollections.observableArrayList();
	
	public EmailFolderBean(String value)
	{
		super(value,ViewFactory.default_factory.resolveIcon(value));
		this.name=value;
		this.completeName=value;
		this.data=null;
		this.topElement=true;
		this.setExpanded(true);
	}
	
	public EmailFolderBean(String value,String completeName)
	{
		super(value,ViewFactory.default_factory.resolveIcon(value));
		this.name=value;
		this.completeName=completeName;
		this.setExpanded(true);
	}
	
	private void updateValue()
	{
		if(unreadMessageCount > 0)
			this.setValue((String)(name+"("+unreadMessageCount+")"));
		else
		{
			this.setValue(name);
		}
	}
	
	public void incrementUnreadMessagesCount(int newMessages)
	{
		unreadMessageCount=unreadMessageCount+newMessages;
		updateValue();
	}
	
	public void decrementUnreadMessagesCount()
	{
		unreadMessageCount--;
		updateValue();
	}
	/*
	public void addEmail(EmailMessengerBean message)
	{
		data.add(message);
		if(!message.isRead())
			incrementUnreadMessagesCount(1);
	}
	*/
	
	public void addEmail(int position,Message message)
	{
		try {
			boolean isRead=message.getFlags().contains(Flag.SEEN);
			EmailMessengerBean emailMessengerBean=new EmailMessengerBean(message.getSubject(),message.getFrom()[0].toString(),message.getSize(),isRead,message);
			//data.add(emailMessengerBean);
			
			if(position<0)
				data.add(emailMessengerBean);
			else
				data.add(position,emailMessengerBean);
			
			if(!isRead)
				incrementUnreadMessagesCount(1);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isTopElement()
	{
		return topElement;
	}
	
	public ObservableList<EmailMessengerBean> getData()
	{
		return data;
	}
	
}
