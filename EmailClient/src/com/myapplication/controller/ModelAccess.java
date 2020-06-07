package com.myapplication.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Folder;

import com.myapplication.model.EmailAccountBean;
import com.myapplication.model.EmailMessengerBean;
import com.myapplication.model.folder.EmailFolderBean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelAccess {

	private String emailAddress;
	private String password;
	private boolean status;
	private EmailMessengerBean message;
	Map<String,EmailAccountBean> emailAccounts=new HashMap<String,EmailAccountBean>();
	
	private ObservableList<String> emailAccountsNames=FXCollections.observableArrayList();
	
	public ObservableList<String> getEmailAccountNames()
	{
		return emailAccountsNames;
	}
	
	public EmailAccountBean getEmailAccountByName(String name)
	{
		return emailAccounts.get(name);
	}
	
	public void addAccount(EmailAccountBean account)
	{
		emailAccounts.put(account.getEmailAddress(), account);
		emailAccountsNames.add(account.getEmailAddress());
	}
	public EmailMessengerBean getMessage()
	{
		return message;
	}
	
	public void setMessage(EmailMessengerBean message)
	{
		this.message=message;
	}
	
    public EmailFolderBean<String> getSelectedFolder() {
		return selectedFolder;
	}

	public void setSelectedFolder(EmailFolderBean<String> selectedFolder) {
		this.selectedFolder = selectedFolder;
	}

	private EmailFolderBean<String> selectedFolder;
	
	private List<Folder> folderList=new ArrayList<Folder>();
	
	public  List<Folder> getFoldersList()
	{
      return folderList;
	}
	
	public void  addFolder(Folder folder)
	{
		folderList.add(folder);
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
