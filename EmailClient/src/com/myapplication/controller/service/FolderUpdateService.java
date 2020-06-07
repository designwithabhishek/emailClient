package com.myapplication.controller.service;

import java.util.List;

import javax.mail.Folder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FolderUpdateService extends Service<Void> {

	private List<Folder> foldersList;

	public FolderUpdateService(List<Folder> foldersList) {
		super();
		this.foldersList = foldersList;
	}

	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				
				for(;;)
				{
					Thread.sleep(1000);
					if (FetchFolderService.noServiceActive()) {
						for (Folder folder : foldersList) {
							if (folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen())
								folder.getMessageCount();
						} 
					}
				}
			}
			
		};
	}
	
}
