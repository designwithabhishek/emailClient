package com.myapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import com.myapplication.controller.service.CreateAndRegisterEmailAccountService;
import com.myapplication.controller.service.FolderUpdateService;
import com.myapplication.controller.service.MessageRendererService;
import com.myapplication.controller.service.SaveAttachmentService;
import com.myapplication.model.EmailAccountBean;
import com.myapplication.model.EmailMessengerBean;
import com.myapplication.model.Singleton;
import com.myapplication.model.folder.EmailFolderBean;
import com.myapplication.model.table.RowFactory;
import com.myapplication.view.ViewFactory;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MainController extends AbstractController implements Initializable{

	    
	    
        public MainController(ModelAccess modelAccess) {
		super(modelAccess);
	}
        EmailFolderBean<String> root; 
		//private Singleton singleton;    
	
        @FXML
        private Label downattachlabel;

        @FXML
        private ProgressBar downattachprogress;
        
        @FXML
        private Button readcontrol;
        
	    @FXML
	    private TreeView<String> emailtreeview;
	    
	    private MenuItem showDetails=new MenuItem("showDetails");
	    @FXML
        private TableView<EmailMessengerBean> emailBox;
      
	    @FXML
	    private TableColumn<EmailMessengerBean, String> subjectCol;
	
	    @FXML
	    private TableColumn<EmailMessengerBean, String> senderCol;
	
	    @FXML
	    private TableColumn<EmailMessengerBean, String> sizeCol;

	    @FXML
        private WebView messageRenderer;
	    
	    private MessageRendererService messageRendererService;
	    private SaveAttachmentService saveAttachmentService;


	    @FXML
	    private Button compose;
	    
	   @FXML
	    private Button downattachbutton;

	    @FXML
	    void downloadingattachment(ActionEvent event) {
         System.out.println("downloadingattachment");
	    	EmailMessengerBean message=emailBox.getSelectionModel().getSelectedItem();
	    	System.out.println(message.getAttachmentList().toString());
         if(message!=null&&message.hasAttachment())
          {
           saveAttachmentService.setMessageToDownload(message);
           saveAttachmentService.restart();
          }
	    }

	    @FXML
	    void composeAction(ActionEvent event) {
         Scene scene=ViewFactory.default_factory.getComposeMessageScene();
         Stage stage=new Stage();
         stage.setScene(scene);
         stage.show();
	    }
	    
	    @FXML
	    private Button addaccountbtn;

	    @FXML
	    void addaccountaction(ActionEvent event) {
	    	//AddAccountController addAccountController=new AddAccountController(getModelAccess(),root);
	    	Scene scene=ViewFactory.default_factory.getAddAccountScene();
	    	Stage stage=new Stage();
	    	stage.setScene(scene);
	    	stage.show();
	    	Thread mythread=new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(!getModelAccess().isStatus())
					{
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(getModelAccess().isStatus());
			    	{
			    	 CreateAndRegisterEmailAccountService emailService=new CreateAndRegisterEmailAccountService(getModelAccess().getEmailAddress(),getModelAccess().getPassword(),root,getModelAccess());
					 emailService.start();
					 getModelAccess().setStatus(false);
			    	}		
				}
	    	});
	    	mythread.start();
	    }
        /*
	    @FXML
	    void changeReadStatus(ActionEvent event) {
           EmailMessengerBean item=getModelAccess().getMessage();
           if(item!=null)
           {
        	   boolean value = item.isRead();
        	   item.setRead(!value);
        	   EmailFolderBean<String> selectedFolder=getModelAccess().getSelectedFolder();
        	   if(selectedFolder!=null)
        	   {
        		   if(value)
        		   {
        			   selectedFolder.incrementUnreadMessagesCount(1);
        		   }
        		   else
        		   {
        			   selectedFolder.decrementUnreadMessagesCount();
        		   }
        	   }
           }
           
	    }
	    */
	/*final ObservableList<EmailMessengerBean> data=FXCollections.observableArrayList(
			new EmailMessengerBean("hello from abhi","abhianil96@gmail.com",66666),
			new EmailMessengerBean("hello from Mommy","abhianil96@gmail.com",665466),
			new EmailMessengerBean("hello from kushaal","abhianil96@gmail.com",96666),
			new EmailMessengerBean("hello from abhi","abhianil96@gmail.com",26366)
			);    
	  */  
	@Override
    public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//messageRenderer.getEngine().loadContent("<html>lets dothis</html>");
		downattachlabel.setVisible(false);
		downattachprogress.setVisible(false);
		messageRendererService=new MessageRendererService(messageRenderer.getEngine());
		saveAttachmentService=new SaveAttachmentService(downattachprogress,downattachlabel);
		
		downattachprogress.progressProperty().bind(saveAttachmentService.progressProperty());
		ViewFactory viewFactory=ViewFactory.default_factory;
		emailBox.setRowFactory(e->new RowFactory<>());
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessengerBean, String>("subject"));
		senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessengerBean, String>("sender"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessengerBean, String>("size"));
		
		sizeCol.setComparator(new Comparator<String>()
		{
			Integer i1 , i2;
			public int compare(String s1,String s2)
			{
			 i1=EmailMessengerBean.formattedValues.get(s1);
			 i2=EmailMessengerBean.formattedValues.get(s2);
			 return i1.compareTo(i2);	
			}
		});
		/*
		emailtreeview.setRoot(root);
		root.setValue("example@gmail.com");
		root.setGraphic(viewFactory.resolveIcon(root.getValue()));
		TreeItem<String> inbox=new TreeItem<String>("Inbox",viewFactory.resolveIcon("Inbox"));
		TreeItem<String> sent=new TreeItem<String>("Sent",viewFactory.resolveIcon("Sent"));
		TreeItem<String> spam=new TreeItem<String>("Spam",viewFactory.resolveIcon("Spam"));
		TreeItem<String> trash=new TreeItem<String>("Trash",viewFactory.resolveIcon("Trash"));
		root.getChildren().addAll(inbox,sent,spam,trash);
		root.setExpanded(true);
		*/
		
		root=new EmailFolderBean<String>("");
		emailtreeview.setRoot(root);
		emailtreeview.setShowRoot(true);
		
		FolderUpdateService folderUpdateService=new FolderUpdateService(getModelAccess().getFoldersList());
		folderUpdateService.start();
		//specify your username and password
		//CreateAndRegisterEmailAccountService emailService=new CreateAndRegisterEmailAccountService("designwithabhishek1996@gmail.com","qwer0192",root,getModelAccess());
		//emailService.start();
		/*
		CreateAndRegisterEmailAccountService emailService2=new CreateAndRegisterEmailAccountService("designwithabhishek1996@gmail.com","qwer0192",root,getModelAccess());
		emailService2.start();
		*/
		emailBox.setContextMenu(new ContextMenu(showDetails));
		emailtreeview.setOnMouseClicked(e->{
			EmailFolderBean<String> item=(EmailFolderBean<String>)emailtreeview.getSelectionModel().getSelectedItem();
			if(item!=null&&!item.isTopElement())
			emailBox.setItems(item.getData());
			getModelAccess().setSelectedFolder(item);
		});
		
		emailBox.setOnMouseClicked(e->{
			EmailMessengerBean message=emailBox.getSelectionModel().getSelectedItem();
			if(message!=null)
			{
				//messageRenderer.getEngine().loadContent(message.getContent());
				getModelAccess().setMessage(message);
				messageRendererService.setMessageToRender(message);
				messageRendererService.restart();
				//Platform.runLater(messageRendererService); 
				//singleton.setMessage(message);
			}
		});
		
		showDetails.setOnAction(e->{
			
			Scene scene=viewFactory.getEmailDetailsScene();
		   
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		});
		
		//singleton=Singleton.getInstance();
	}
	
	
}
