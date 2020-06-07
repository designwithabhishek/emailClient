package com.myapplication;

import javax.mail.Message;

import com.myapplication.model.EmailAccountBean;
import com.myapplication.model.EmailMessengerBean;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class test   {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       final EmailAccountBean emailAccountBean=new EmailAccountBean("designwithabhishek1996@gmail.com","qwer0192");
       ObservableList<EmailMessengerBean> data=FXCollections.observableArrayList();
       //emailAccountBean.addEmailsToData(data);
       
	}

}
