package com.myapplication;

import com.myapplication.model.EmailAccountBean;
import com.myapplication.model.EmailMessengerBean;
import com.myapplication.view.ViewFactory;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//Pane pane=FXMLLoader.load(getClass().getResource("MainLayout.fxml"));
		   //final EmailAccountBean emailAccountBean=new EmailAccountBean("designwithabhishek1996@gmail.com","qwer0192");
	       //ObservableList<EmailMessengerBean> data=FXCollections.observableArrayList();
	       //emailAccountBean.addEmailsToData(data);
		//Scene scene=new Scene(pane);
		ViewFactory viewFactory=ViewFactory.default_factory;
		Scene scene=viewFactory.getMainScene();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	

}