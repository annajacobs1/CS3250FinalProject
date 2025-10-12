import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Page to view/search all catalog items using SearchTablePane
 */
public class CatalogPane extends VBox{
	public CatalogPane() {
		Label catalogSearchLbl = new Label("Search Catalog");
		
		ArrayList<String> options = new ArrayList<String>();
		options.add("Author");
		options.add("Barcode");
		options.add("Call Number");
		options.add("Title");
		
		SearchTablePane<Item> catalogPane = new SearchTablePane<Item>(Main.getItems(),
				options);
		
		getChildren().addAll(catalogSearchLbl, catalogPane);
		
		if(Main.getUser() instanceof Employee) {
			if(((Employee)Main.getUser()).getAccessLevel() == AccessLevel.ADD) {
				Button addBtn = new Button("Add an item");
				// TODO: button click takes you to form to input new item info
				getChildren().add(addBtn);
			}
		}
	}
}
