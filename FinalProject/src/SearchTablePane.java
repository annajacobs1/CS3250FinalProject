import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * Generic class to display a table of all items in an array list.
 * Includes a search bar with a drop down menu to choose which field to
 * search in.
 * @param <T>
 */
public class SearchTablePane<T> extends VBox{
	private ComboBox<String> searchFieldMenu = new ComboBox<String>();
	private TableView<T> table;
	
	public SearchTablePane(ArrayList<T> itemsToDisplay, ArrayList<String> options) {
		FlowPane searchPane = new FlowPane();
		
		table = new TableView<T>();
		
		setFieldOptions(options);
		TextField searchTxt = new TextField();
		Button searchBtn = new Button("Search");
		searchPane.getChildren().addAll(searchFieldMenu, searchTxt, searchBtn);
		
		ObservableList<T> listToDisplay = FXCollections.observableList(itemsToDisplay);
		
		table.setItems(listToDisplay);
		
		getChildren().addAll(searchPane, table);
		
		
	}
	
	private void setFieldOptions(ArrayList<String> options) {
		for(String option : options) {
			searchFieldMenu.getItems().add(option);
		}
	}
	
	// TODO: another setCols for just strings
	
	public void setCols(String imageName, ArrayList<String> columnNames) {
		
		TableColumn<T, Image> coverCol = new TableColumn<>(imageName);
		coverCol.setCellValueFactory(new PropertyValueFactory<T, Image>("image"));
		this.table.getColumns().add(coverCol);
		
		for (String name : columnNames) {
			TableColumn<T, String> callNumCol = new TableColumn<T, String>(name);
			callNumCol.setCellValueFactory(new PropertyValueFactory<T, String>("callNum"));
			this.table.getColumns().add(callNumCol);
		}
		
//		TableColumn<T, String> callNumCol = new TableColumn<T, String>("Call Number");
//		callNumCol.setCellValueFactory(new PropertyValueFactory<T, String>("callNum"));
//		
//		TableColumn<T, String> titleCol = new TableColumn<T, String>("Title");
//		callNumCol.setCellValueFactory(new PropertyValueFactory<T, String>("title"));
//		
//		table.getColumns().setAll(coverCol, callNumCol, titleCol);
		
		//TODO: Try to set up columns to automatically populate. Figure out
		// 		how to get fields of T. Also each row should be clickable and set screen
		//		to ItemInfoPane (probably won't be in this function...)
	}
}
