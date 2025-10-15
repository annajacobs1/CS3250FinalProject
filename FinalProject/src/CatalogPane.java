import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Page to view/search all catalog items using SearchTablePane
 */
public class CatalogPane extends VBox{
	SearchTablePane<Record> catalogPane;
	
	public CatalogPane() {
		Label catalogSearchLbl = new Label("Search Catalog");
		
		ArrayList<String> options = new ArrayList<String>();
		options.add("Author");
		options.add("Barcode");
		options.add("Call Number");
		if(Main.getUser() instanceof Employee) {
			options.add("Record Number");
		}
		options.add("Title");
		
		// This table should actually only show all the records, not individual items.
		// Individual items only show up when you click a record.
		
		catalogPane = new SearchTablePane<Record>(Data.getRecords(),
				options);
		
		getChildren().addAll(catalogSearchLbl, catalogPane);
		
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("Call Number");
		columns.add("Next");
		
		catalogPane.setCols("Image", columns);
		
		if(Main.getUser() instanceof Employee) {
			if(((Employee)Main.getUser()).getAccessLevel() == AccessLevel.ADD) {
				Button addBtn = new Button("Add an item");
				// TODO: button click takes you to form to input new item info
				getChildren().add(addBtn);
			}
		}
	}
	
	private void setCols() {
//		TableColumn<Record, Image> coverCol = new TableColumn<Record, Image>("Cover");
//		coverCol.setCellValueFactory(new PropertyValueFactory<Record, Image>("image"));
//		
//		TableColumn<Record, String> callNumCol = new TableColumn<Record, String>("Call Number");
//		callNumCol.setCellValueFactory(new PropertyValueFactory<Record, String>("callNum"));
//		
//		TableColumn<Record, String> titleCol = new TableColumn<Record, String>("Title");
//		callNumCol.setCellValueFactory(new PropertyValueFactory<Record, String>("title"));
//		
//		catalogPane.table.getColumns().setAll(coverCol, callNumCol, titleCol);
	}
}
