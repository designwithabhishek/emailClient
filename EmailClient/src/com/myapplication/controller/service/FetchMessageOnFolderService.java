package com.myapplication.controller.service;

import javax.mail.Folder;
import javax.mail.Message;

import com.myapplication.model.folder.EmailFolderBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FetchMessageOnFolderService extends Service<Void> {

	EmailFolderBean<String> emailFolderBean ;
	Folder folder;
	public FetchMessageOnFolderService(EmailFolderBean emailFolderBean, Folder folder) {
		//super();
		this.emailFolderBean = emailFolderBean;
		this.folder = folder;
	}
	@Override
	protected Task<Void> createTask() {
	    return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
			  if(folder.getType()!=Folder.HOLDS_FOLDERS)
			  {
				  folder.open(Folder.READ_WRITE);
			  
			  int folderSize=folder.getMessageCount();
			  for(int i=folderSize;i>0;i--)
			  {
				  Message currentMessage=folder.getMessage(i);
				  emailFolderBean.addEmail(-1,currentMessage);
			  
			  }
			  }
			  return null;
			}
	    	
	    };
	}
	
	
	
}
