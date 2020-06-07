package com.myapplication.view;

import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import com.myapplication.controller.AbstractController;
import com.myapplication.controller.AddAccountController;
import com.myapplication.controller.ComposeMessageController;
import com.myapplication.controller.EmailDetailsController;
import com.myapplication.controller.MainController;
import com.myapplication.controller.ModelAccess;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ViewFactory {

	private static boolean flag=false;
	public static ViewFactory default_factory=new ViewFactory();
	private final String DEFAULT_CSS="styles.css";
	private final String MAINLAYOUT_PATH="MainLayout.fxml";
	private final String EMAILLAYOUT_PATH="EmailDetailsLayout.fxml";
	private final String COMPOSELAYOUT_PATH="ComposeMessageLayoout.fxml";
	private final String ADDACCOUNTLAYOUT_PATH="addAccount.fxml";
	private ModelAccess modelAccess=new ModelAccess();
	private EmailDetailsController emailDetailsController;
	private MainController mainController;
	
	
	
	public Scene getMainScene() throws OperationNotSupportedException
	{
		if (!flag) {
			mainController = new MainController(modelAccess);
			flag = true;
			return initializeScene(MAINLAYOUT_PATH, mainController);
		}
		else
		{
			throw new OperationNotSupportedException("Main has been initialized earlier");
		}
	}
	
	public Scene getEmailDetailsScene()
	{
		emailDetailsController=new EmailDetailsController(modelAccess);
		return initializeScene(EMAILLAYOUT_PATH,emailDetailsController);
	}
	
	public Scene getComposeMessageScene()
	{
		AbstractController composeMessageController=new ComposeMessageController(modelAccess);
		return initializeScene(COMPOSELAYOUT_PATH,composeMessageController);
	}
	
	public Scene getAddAccountScene()
	{
		AbstractController addAccountController=new AddAccountController(modelAccess);
		return initializeScene(ADDACCOUNTLAYOUT_PATH,addAccountController);
	}
	public Node resolveIcon(String treeItemValue)
	{
		String lowerCaseTreeItemValue= treeItemValue.toLowerCase();
		ImageView returnIcon;
		try
		{
			if(lowerCaseTreeItemValue.contains("inbox"))
				returnIcon=new ImageView(new Image(getClass().getResourceAsStream("image/inbox.png")));
			else if(lowerCaseTreeItemValue.contains("sent"))
				returnIcon=new ImageView(new Image(getClass().getResourceAsStream("image/sent2.png")));
			else if(lowerCaseTreeItemValue.contains("spam"))
				returnIcon=new ImageView(new Image(getClass().getResourceAsStream("image/spam.png")));
			else if(lowerCaseTreeItemValue.contains("@"))
				returnIcon=new ImageView(new Image(getClass().getResourceAsStream("image/email.png")));
			else
				returnIcon=new ImageView(new Image(getClass().getResourceAsStream("image/folder.png")));
		}
		catch(NullPointerException e)
		{
		 System.out.println("Invalid Path");
		 e.printStackTrace();
		 returnIcon=new ImageView();
		}
		returnIcon.setFitHeight(16);
		returnIcon.setFitWidth(16);
		return returnIcon;
	}
	
	public Scene initializeScene(String fxml,AbstractController abstractController)
	{
		FXMLLoader loader;
		Parent parent;
		Scene scene;
		try {
			loader=new FXMLLoader(getClass().getResource(fxml));
			loader.setController(abstractController);
			parent=loader.load();
		} catch (IOException e) {
			return null;
		}
		
		scene=new Scene(parent);
		scene.getStylesheets().add(getClass().getResource(DEFAULT_CSS).toExternalForm());
		return scene;
	
	}
	

}
