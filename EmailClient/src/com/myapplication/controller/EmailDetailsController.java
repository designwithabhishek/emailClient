package com.myapplication.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.myapplication.model.EmailMessengerBean;
import com.myapplication.model.Singleton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

public class EmailDetailsController extends AbstractController implements Initializable {

	public EmailDetailsController(ModelAccess modelAccess) {
		super(modelAccess);
		
	}


	//private Singleton singleton;
	
	@FXML
    private WebView webView;

    @FXML
    private Label Subject;

    @FXML
    private Label Sender;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//singleton=Singleton.getInstance();
		EmailMessengerBean selectedMessage;
		selectedMessage=getModelAccess().getMessage();
		
		Sender.setText("Sender : "+selectedMessage.getSender());
		Subject.setText("Subject : "+selectedMessage.getSubject());
		//webView.getEngine().loadContent(selectedMessage.getContent());
		System.out.println("Initializing");
	}

}
