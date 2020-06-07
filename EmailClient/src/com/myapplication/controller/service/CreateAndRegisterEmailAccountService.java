package com.myapplication.controller.service;

import com.myapplication.controller.ModelAccess;
import com.myapplication.model.EmailAccountBean;
import com.myapplication.model.EmailConstants;
import com.myapplication.model.folder.EmailFolderBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CreateAndRegisterEmailAccountService extends Service<Integer>  {

	private String emailPassword;
	private String emailAddress;
	private ModelAccess modelAccess;
	public CreateAndRegisterEmailAccountService(String emailAddress,String emailPassword,
			EmailFolderBean<String> folderRoot,ModelAccess modelAccess) {
		super();
		this.emailPassword = emailPassword;
		this.emailAddress = emailAddress;
		this.folderRoot = folderRoot;
		this.modelAccess=modelAccess;
	}

	private EmailFolderBean<String> folderRoot ;
	
	@Override
	protected Task createTask() {
		// TODO Auto-generated method stub
		return new Task<Integer>() {

			@Override
			protected Integer call() throws Exception {
				System.out.println(emailAddress+emailPassword);
				EmailAccountBean emailAccountBean=new EmailAccountBean(emailAddress,emailPassword);
				if(emailAccountBean.getLogin()==EmailConstants.LOGIN_STATE_SUCCEDED);
					{
						modelAccess.addAccount(emailAccountBean);
						EmailFolderBean<String> emailFolderBean =new EmailFolderBean<String>(emailAddress);
				        folderRoot.getChildren().add(emailFolderBean);
				        FetchFolderService fetchFolderService=new FetchFolderService(emailFolderBean,emailAccountBean,modelAccess);
				        fetchFolderService.start();
					}
					return emailAccountBean.getLogin();
			}
			
		};
	}

}
