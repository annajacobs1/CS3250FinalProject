import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * Generic class to display a table of all items in an array list.
 * Includes a search bar with a drop down menu to choose which field to
 * search in.
 * @param <T>
 */
public class SearchTablePane<T extends Object> extends VBox{
	ComboBox<String> searchFieldMenu = new ComboBox<String>();
	
	public SearchTablePane(ArrayList<T> itemsToDisplay, ArrayList<String> options) {
		FlowPane searchPane = new FlowPane();
		
		setFieldOptions(options);
		TextField searchTxt = new TextField();
		searchPane.getChildren().addAll(searchFieldMenu, searchTxt);
		
		TableView<T> table = new TableView<T>();
		
		
		ObservableList<T> listToDisplay = FXCollections.observableList(itemsToDisplay);
		
		table.setItems(listToDisplay);
		
		getChildren().addAll(searchPane, table);
		
		
	}
	
	private void setFieldOptions(ArrayList<String> options) {
		for(String option : options) {
			searchFieldMenu.getItems().add(option);
		}
	}
	
	private void setCols() {
		//TODO: Try to set up columns to automatically populate. Figure out
		// 		how to get fields of T. Also each row should be clickable and set screen
		//		to ItemInfoPane (probably won't be in this function...)
	}
}
