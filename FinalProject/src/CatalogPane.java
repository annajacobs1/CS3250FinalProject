import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
		
		catalogPane = new SearchTablePane<Record>(Data.getRecords(),
				options);
		
		getChildren().addAll(catalogSearchLbl, catalogPane);
		
		ArrayList<String[]> columns = new ArrayList<String[]>();
		String[] imageCol = {"Image", "image"};
		String[] callNumCol = {"Call Number", "callNum"};
		String[] titleCol = {"Title", "title"};
		String[] sectionCol = {"Section", "section"};
		
		columns.add(imageCol);
		columns.add(callNumCol);
		columns.add(titleCol);
		columns.add(sectionCol);
		
		catalogPane.setCols("Image", columns);
		
		if(Main.getUser() instanceof Employee) {
			if(((Employee)Main.getUser()).getAccessLevel() == AccessLevel.ADD) {
				Button addBtn = new Button("Add a record");
				addBtn.setOnAction(e -> {
					Main.getMainPane().setCenter(new RecordAddPane());
				});
				getChildren().add(addBtn);
			}
		}
	}
}
