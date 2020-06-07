package com.myapplication.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.myapplication.controller.service.CreateAndRegisterEmailAccountService;
import com.myapplication.model.folder.EmailFolderBean;
import com.myapplication.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddAccountController extends AbstractController implements Initializable {

	//EmailFolderBean<String> root;
	
	@FXML
    private TextField emailaccount;

    @FXML
    private TextField password;

    @FXML
    void addAccount(ActionEvent event) {

    	getModelAccess().setEmailAddress(emailaccount.getText());
    	getModelAccess().setPassword(password.getText());
    	getModelAccess().setStatus(true);
    	//CreateAndRegisterEmailAccountService emailService=new CreateAndRegisterEmailAccountService(emailaccount.getText(),password.getText(),root,getModelAccess());
		//emailService.start();
    }
	
	public AddAccountController(ModelAccess modelAccess) {
		super(modelAccess);
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
