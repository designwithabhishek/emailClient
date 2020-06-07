package com.myapplication.controller.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import com.myapplication.model.EmailMessengerBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

public class MessageRendererService extends Service<Void>  {

	private EmailMessengerBean messageToRender;
	private WebEngine messageRendererEngine;
	private StringBuffer sb=new StringBuffer();
	
	private void renderMessage()
	{
		sb.setLength(0);
		messageToRender.clearAttachment();
		Message message=messageToRender.getMessageRefrence();
		try
		{
			String messageType=message.getContentType();
			if(messageType.contains("TEXT/HTML")||messageType.contains("TEXT/PLAIN")||messageType.contains("text"))
			{
				sb.append(message.getContent().toString());
			}
			else if(messageType.contains("multipart"))
			{
				Multipart mp=(Multipart)message.getContent();
			    for(int i=mp.getCount()-1;i>=0;i--)
			    {
			    	BodyPart bp=mp.getBodyPart(i);
			    	String contentType=bp.getContentType();
			    	if(contentType.contains("TEXT/HTML")||contentType.contains("TEXT/PLAIN")||contentType.contains("mixed")||contentType.contains("text"))
			    	{
			           if(sb.length()==0)
			           {
			        	   sb.append(bp.getContent().toString());
			           }
			           
			    	}else if(contentType.toLowerCase().contains("application")||contentType.toLowerCase().contains("image")||contentType.toLowerCase().contains("audio"))
			    	{
			    		MimeBodyPart mbp=(MimeBodyPart)bp;		
			            messageToRender.addAttachment(mbp);
			    	}
			    	else if(bp.getContentType().contains("multipart"))
			    	{
			    		Multipart mp2=(Multipart)bp.getContent();
			    		for(int j=mp2.getCount()-1;j>=0;j--)
			    		{
			    			BodyPart bp2=mp.getBodyPart(j);
					    	if(bp2.getContentType().contains("TEXT/HTML")||bp2.getContentType().contains("TEXT/PLAIN"))
					    	{
					    		sb.append(bp.getContent().toString());
					    	}
			    		}
			    	}
			    }
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public MessageRendererService(WebEngine messageRendererEngine) {
		//super();
		this.messageRendererEngine = messageRendererEngine;

	}

	public void setMessageToRender(EmailMessengerBean messageToRender)
	{
		this.messageToRender=messageToRender;
		this.setOnSucceeded(e->{
			showMessage();
		});
	}
	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
			   renderMessage();
			   
			   return null;
			}
			
		};
	}

	private void showMessage()
	{
		messageRendererEngine.loadContent(sb.toString());
	}

	
}
