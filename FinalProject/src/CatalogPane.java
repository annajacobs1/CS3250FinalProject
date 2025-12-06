import java.util.ArrayList;

import javafx.scene.control.Alert;
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
		options.add("Author Last Name");
		options.add("Call Number");
		options.add("ISBN");
		if(Main.getUser() instanceof Employee) {
			options.add("Record Number");
		}
		options.add("Section");
		options.add("Title");
		
		catalogPane = new SearchTablePane<Record>(Data.getRecords(),
				options, new SearchRecords());
		
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
	
	public class SearchRecords implements SearchHandler{
		public void search(String col, String val) {
			
			ArrayList<Record> resultArray = new ArrayList<>();
			
			if(!val.equals("")) {
				switch(col) {
				case "Author Last Name":
					resultArray = Data.searchBooksByColumnValue("author_last_name", "'" + val + "'");
					break;
				case "Call Number":
					resultArray = Data.searchRecordsByCallNum(val);
					break;
				case "Record Number":
					Record resultVal = Data.searchByRecordNum(val);
					resultArray.add(resultVal);
					break;
				case "ISBN":
					try {
						long isbn = Long.parseLong(val);
						resultArray = Data.searchRecordsByISBN(isbn);
					} catch(NumberFormatException e) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("ISBN search must contain only digits");
						alert.show();
					}
					break;
				case "Title":
					resultArray = Data.searchRecordsByTitle(val);
					break;
				case "Section":
					resultArray = Data.searchRecordsBySection(val);
					break;
				}
			} else {
				catalogPane.setItems(Data.getRecords());
			}
			
			catalogPane.setItems(resultArray);
		}
	}
	
}
