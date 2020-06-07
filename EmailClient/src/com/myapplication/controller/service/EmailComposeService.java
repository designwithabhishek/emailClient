package com.myapplication.controller.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.myapplication.model.EmailAccountBean;
import com.myapplication.model.EmailConstants;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class EmailComposeService extends Service<Integer> {

	private int result;
	private String subject;
	private String receptient;
	private EmailAccountBean emailAccountbean;
	private String content;
	private List<File> attachments=new ArrayList<File>();
	
	public EmailComposeService(String subject, String receptient, EmailAccountBean emailAccountbean, String content,
			List<File> attachments) {
		super();
		this.subject = subject;
		this.receptient = receptient;
		this.emailAccountbean = emailAccountbean;
		this.content = content;
		this.attachments = attachments;
	}

	@Override
	protected Task<Integer> createTask() {
		// TODO Auto-generated method stub
		
		return new Task<Integer>()
				{

					@Override
					protected Integer call() throws Exception {
						try {
							Session session=emailAccountbean.getSession();
							MimeMessage message=new MimeMessage(session);
							message.setFrom(emailAccountbean.getEmailAddress());
							message.addRecipients(Message.RecipientType.TO, receptient);
							message.setSubject(subject);
							
							Multipart multipart=new MimeMultipart();
							BodyPart messageBodyPart=new MimeBodyPart();
							messageBodyPart.setContent(content,"text/html");
							multipart.addBodyPart(messageBodyPart);
							
							if(attachments.size()>0)
							{
								for(File file:attachments)
								{
								 MimeBodyPart attach=new MimeBodyPart();
								 DataSource source=new FileDataSource(file.getAbsolutePath());
								 attach.setDataHandler(new DataHandler(source));
								 attach.setFileName(file.getName());
								 multipart.addBodyPart(attach);
								}
							}
							
							message.setContent(multipart);
							
							Transport transport=session.getTransport();
							transport.connect(emailAccountbean.getProperties().getProperty("outgoingHost"),emailAccountbean.getEmailAddress(), emailAccountbean.getPassword());
							
							transport.sendMessage(message, message.getAllRecipients());
							transport.close();
							
							result=EmailConstants.MESSAGE_SENT_OK;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							result=EmailConstants.MESSAGE_SENT_ERROR;
							e.printStackTrace();
						}
						return result;
					}
			
				};
	}

}
