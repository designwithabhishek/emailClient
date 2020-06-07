package com.myapplication.model.table;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableRow;

public class RowFactory<T extends AbstractTableItem> extends TableRow<T>
{
	
	private final SimpleBooleanProperty bold=new SimpleBooleanProperty();
	private T currentItem =null;
	
	public RowFactory()
	{
		super();
		
		bold.addListener((ObservableValue<? extends Boolean> observable,Boolean oldvalue,Boolean newValue)->{
			if(currentItem!=null&& currentItem==getItem())
			{
				updateItem(getItem(),isEmpty());
			}
		});
		
		itemProperty().addListener((ObservableValue<? extends T> observable,T oldvalue,T newValue)->{
			//bold.unbind();
			if(newValue!=null)
			{
				bold.unbind();
				bold.bind(newValue.getReadProperty());
				currentItem=newValue;
			}
			
		});
	}
 @Override
 final protected void updateItem(T item,boolean empty)
 {
	 super.updateItem(item,empty);
	 if(item!=null&&!item.isRead())
	 {
		 setStyle("-fx-font-weight:bold");
	 }
	 else
		 setStyle("");
 }
}
