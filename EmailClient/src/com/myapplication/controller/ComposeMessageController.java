package com.myapplication.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.myapplication.controller.service.EmailComposeService;
import com.myapplication.model.EmailConstants;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

public class ComposeMessageController extends AbstractController implements Initializable  {

	
	private List<File> attachments=new ArrayList<File>();
	@FXML
    private Label attachLabel;

    @FXML
    private TextField receptient;

    @FXML
    private TextField subject;

    @FXML
    private ChoiceBox<String> senderChoice;

    @FXML
    private Label errorLabel;
    
    @FXML
    private HTMLEditor composearea;

    @FXML
    void attachFiles(ActionEvent event) {
      
    	FileChooser fileChooser=new FileChooser();
    	File selectedFile=fileChooser.showOpenDialog(null);
    	if(selectedFile!=null)
    		{
    		attachments.add(selectedFile);
    	    attachLabel.setText(attachLabel.getText()+selectedFile.getName()+";");
    		}
    }
    @FXML
    void sendMessage(ActionEvent event) {
      EmailComposeService sendmail=new EmailComposeService(subject.getText(),
    		  receptient.getText(),
    		  getModelAccess().getEmailAccountByName(senderChoice.getValue()),
    		  composearea.getHtmlText(),
    		  attachments);
      sendmail.restart();
      
      sendmail.setOnSucceeded(e->{
    	  if(sendmail.getValue() == EmailConstants.MESSAGE_SENT_OK)
    	  {
    		errorLabel.setText("Message sent successfully");  
    	  }
    	  else
    	  {
    		  errorLabel.setText("Message sending error!!");
    	  }
      });
    }

	
	public ComposeMessageController(ModelAccess modelAccess) {
		super(modelAccess);
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	 senderChoice.setItems(getModelAccess().getEmailAccountNames());	
	 senderChoice.setValue(getModelAccess().getEmailAccountNames().get(0));
	}

}
