package com.myapplication.controller.service;

import javax.mail.internet.MimeBodyPart;

import com.myapplication.model.EmailMessengerBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class SaveAttachmentService extends Service<Void> {

	private String LOCATION_DOWNLOAD=System.getProperty("user.home")+"/Downloads/";
	private EmailMessengerBean messageToDownload;
	private ProgressBar progressBar;
	private Label label;
		
	public SaveAttachmentService(ProgressBar progressBar, Label label) {
		this.progressBar = progressBar;
		this.label = label;
		this.setOnRunning(e->{
			showVisuals(true);
		});
		this.setOnSucceeded(e->{
			showVisuals(false);
		});
	}


	public void setMessageToDownload(EmailMessengerBean messageToDownload) {
		this.messageToDownload = messageToDownload;
	}


	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				System.out.println(messageToDownload.getAttachmentList().toString());
				for(MimeBodyPart mbp:messageToDownload.getAttachmentList())
				{
					updateProgress(messageToDownload.getAttachmentList().indexOf(mbp),messageToDownload.getAttachmentList().size());
					mbp.saveFile(LOCATION_DOWNLOAD+mbp.getFileName());
					
				}
				return null;
			}
			
		};
	}
    
	public void showVisuals(boolean show)
	{
		progressBar.setVisible(show);
		label.setVisible(show);
	}
}
