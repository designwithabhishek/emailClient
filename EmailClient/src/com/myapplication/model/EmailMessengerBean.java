package com.myapplication.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.internet.MimeBodyPart;

import com.myapplication.model.table.AbstractTableItem;

import javafx.beans.property.SimpleStringProperty;

public class EmailMessengerBean extends AbstractTableItem {

	@Override
	public String toString() {
		return "EmailMessengerBean [sender=" + sender + ", subject=" + subject + ", size=" + size + ", content="
				+ "]";
	}

	public static Map<String,Integer> formattedValues=new HashMap<String,Integer>(); 
	private SimpleStringProperty sender;
	private SimpleStringProperty subject;
	private SimpleStringProperty size;
	private Message message;
	private List<MimeBodyPart> attachmentList=new ArrayList(); 
	private StringBuffer attachmentName=new StringBuffer();
	
	
	public EmailMessengerBean(String subject, String sender, int size,boolean isRead,Message message) {
		// TODO Auto-generated constructor stub
		super(isRead);
		this.sender=new SimpleStringProperty(sender);
		this.subject=new SimpleStringProperty(subject);
		this.size=new SimpleStringProperty(formatSize(size));
        this.message=message; 		
	}

	public String getSender()
	{
		return sender.get();
	}
	
	public String getSubject()
	{
		return subject.get();
	}
	
	public String getSize()
	{
		return size.get();
	}
	
	
	
	private String formatSize(int size)
	{
		String returnValue="";
		if(size<=0)
			returnValue="0";
		else if(size<1024)
		{
			returnValue=size+"B";
		}
		else if(size<1048576)
		{
			returnValue=(size/1024)+"kB";
		}
		else
		{
			returnValue=(size/1048576)+"MB";
		}
		formattedValues.put(returnValue,size);
			return returnValue;
	}
	
	public Message getMessageRefrence()
	{
		return message;
	}
	
	public String getAttachmentName() {
		return attachmentName.toString();
	}

	public List<MimeBodyPart> getAttachmentList() {
		return attachmentList;
	}

    public void addAttachment(MimeBodyPart mp)
    {
    	attachmentList.add(mp);
    	try {
    		attachmentName.append(mp.getFileName()+";");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
	
    public boolean hasAttachment()
    {
    	return attachmentList.size()>0;
    }
    
    public void clearAttachment()
    {
    	attachmentList.clear();
    	attachmentName.setLength(0);
    }
}
