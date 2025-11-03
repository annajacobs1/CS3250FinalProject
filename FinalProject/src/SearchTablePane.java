import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
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
	private ObservableList<T> listToDisplay;
	
	public SearchTablePane(ArrayList<T> itemsToDisplay, ArrayList<String> options) {
		FlowPane searchPane = new FlowPane();
		
		table = new TableView<T>();
		
		setFieldOptions(options);
		TextField searchTxt = new TextField();
		Button searchBtn = new Button("Search");
		searchPane.getChildren().addAll(searchFieldMenu, searchTxt, searchBtn);
		
		listToDisplay = FXCollections.observableList(itemsToDisplay);
		table.setItems(listToDisplay);
		
		table.setRowFactory(table -> {
			TableRow<T> row = new TableRow<T>() {
				@Override
				protected void updateItem(T item, boolean empty) {
					super.updateItem(item, empty);
				}
			};
			
			row.setOnMouseClicked(e -> {
				// open ItemInfoPane for 
			});
			
			return row;
		});
		
		getChildren().addAll(searchPane, table);
	}
	
	private void setFieldOptions(ArrayList<String> options) {
		for(String option : options) {
			searchFieldMenu.getItems().add(option);
		}
	}
	
	public void setCols(String imageName, ArrayList<String[]> columnNames) {
		
		TableColumn<T, Image> coverCol = new TableColumn<>(imageName);
		coverCol.setCellValueFactory(new PropertyValueFactory<T, Image>("image"));
		
		ArrayList<TableColumn<T, ?>> cols = new ArrayList<TableColumn<T, ?>>();
		cols.add(coverCol);
		
		for(int i = 1; i < columnNames.size(); i++) {
			String[] col = columnNames.get(i);
			TableColumn<T, String> newCol = new TableColumn<T, String>(col[0]);
			newCol.setCellValueFactory(new PropertyValueFactory<T, String>(col[1]));
			cols.add(newCol);
		}
		
		this.table.getColumns().setAll(cols);
	}
	
	public void setCols(ArrayList<String[]> columnNames) {
		ArrayList<TableColumn<T, ?>> cols = new ArrayList<TableColumn<T, ?>>();
		for(int i = 0; i < columnNames.size(); i++) {
			String[] col = columnNames.get(i);
			TableColumn<T, String> newCol = new TableColumn<T, String>(col[0]);
			newCol.setCellValueFactory(new PropertyValueFactory<T, String>(col[1]));
			cols.add(newCol);
		}
		
		this.table.getColumns().setAll(cols);
	}
}
