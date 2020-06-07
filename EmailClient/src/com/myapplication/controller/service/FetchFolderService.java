package com.myapplication.controller.service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;

import com.myapplication.controller.ModelAccess;
import com.myapplication.model.EmailAccountBean;
import com.myapplication.model.folder.EmailFolderBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FetchFolderService extends Service<Void> {

	private EmailFolderBean<String> folderRoot ;
	private EmailAccountBean emailAccountBean;
	private ModelAccess modelAccess;
	private static int NUMBER_OF_FETCH_FOLDERS_ACTIVE=0;
	
	public FetchFolderService(EmailFolderBean<String> folderRoot,EmailAccountBean emailAccountBean,ModelAccess modelAccess)
	{
		this.folderRoot=folderRoot;
		this.emailAccountBean=emailAccountBean;
		this.modelAccess=modelAccess;
		this.setOnSucceeded(e->{
			NUMBER_OF_FETCH_FOLDERS_ACTIVE--;
		});
	}
	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>() {
			
			@Override
			protected Void call() throws Exception {
				NUMBER_OF_FETCH_FOLDERS_ACTIVE++;
				Folder []folders=emailAccountBean.getStore().getDefaultFolder().list();
				for(Folder folder:folders)
				{
					System.out.println(folder.getName());
					modelAccess.addFolder(folder);
					EmailFolderBean<String> item=new EmailFolderBean<String>(folder.getName(),folder.getFullName());
					folderRoot.getChildren().add(item);
					item.setExpanded(true);
					addMessageListenerToFolder(folder,item);
					FetchMessageOnFolderService fetchMessage=new FetchMessageOnFolderService(item,folder);
					fetchMessage.start();
					Folder []subFolders=folder.list();
					for(Folder subfolder:subFolders)
					{
						System.out.println(subfolder);
						modelAccess.addFolder(subfolder);
						EmailFolderBean<String> subItem=new EmailFolderBean<String>(subfolder.getName(),subfolder.getFullName());
                        item.getChildren().add(subItem);
                        subItem.setExpanded(true);
                        addMessageListenerToFolder(subfolder,subItem);
                        FetchMessageOnFolderService fetchMessage2=new FetchMessageOnFolderService(subItem,subfolder);
    					fetchMessage2.start();
					}
				}
				return null;
			}
			
		};
	}
	
	private void addMessageListenerToFolder(Folder folder,EmailFolderBean<String> item)
	{
		folder.addMessageCountListener(new MessageCountAdapter()
				{
			     @Override
			     public void messagesAdded(MessageCountEvent e)
			     {
			    	 for(int i=0;i<e.getMessages().length;i++)
			    	 {
			    		 Message currentMessage;
						try {
							currentMessage = folder.getMessage(folder.getMessageCount()-i);
							item.addEmail(0,currentMessage);
						} catch (MessagingException e1) {
							// TODO Auto-generated catch block
							
							e1.printStackTrace();
						}    		 
			    	 }
			     }
				});
	}
	
	public static boolean noServiceActive()
	{
		return NUMBER_OF_FETCH_FOLDERS_ACTIVE==0;
	}
}
