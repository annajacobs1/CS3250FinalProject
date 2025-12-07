import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Page to view/search list of Patrons using SearchTablePane
 */
public class PatronsPane extends VBox{
	private SearchTablePane<Patron> patronsPane;
	
	public PatronsPane() {
		
		Label patronSearchLbl = new Label("Search Patrons");
		patronSearchLbl.getStyleClass().add("title");
		
		ArrayList<String> options = new ArrayList<String>();
		options.add("Card Number");
		options.add("Email");
		options.add("First Name");
		options.add("Last Name");
		options.add("Username");
		
		patronsPane = new SearchTablePane<Patron>(Data.getPatrons(),
				options, new SearchPatrons());
		
		ArrayList<String[]> columns = new ArrayList<String[]>();
		String[] cardNumCol = {"Card Number", "cardNum"};
		String[] firstNameCol = {"First Name", "firstName"};
		String[] lastNameCol = {"Last Name", "lastName"};
		String[] usernameCol = {"Username", "username"};
		String[] emailCol = {"Email", "email"};
		
		columns.add(cardNumCol);
		columns.add(firstNameCol);
		columns.add(lastNameCol);
		columns.add(usernameCol);
		columns.add(emailCol);
		
		patronsPane.setCols(columns);
		
		getChildren().addAll(patronSearchLbl, patronsPane);
		
		if(Main.getUser() instanceof Employee) {
			if(((Employee)Main.getUser()).getAccessLevel() == AccessLevel.ADD) {
				Button addBtn = new Button("Add Patron");
				addBtn.setOnAction(e -> {
					Main.getMainPane().setCenter(new PatronAddPane());
				});
				getChildren().add(addBtn);
			}
		}
	}
	
	public class SearchPatrons implements SearchHandler{
		public void search(String col, String val) {
			
			ArrayList<Patron> resultArray = new ArrayList<>();
			
			if(!val.equals("")) {
				switch(col) {
				case "Card Number":
					try {
						int cardNumber = Integer.parseInt(val);
						Patron resultVal = Data.searchByCardNum(cardNumber);
						resultArray.add(resultVal);
					} catch(NumberFormatException e) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Card Number search must contain only digits");
						alert.show();
					}
					break;
				case "Email":
					resultArray = Data.searchPatronsByEmail(val);
					break;
				case "First Name":
					resultArray = Data.searchPatronsByFirstName(val);
					break;
				case "Last Name":
					resultArray = Data.searchPatronsByLastName(val);
					break;
				case "Username":
					resultArray = Data.searchPatronsByUsername(val);
					break;
				}
			} else {
				patronsPane.setItems(Data.getPatrons());
			}
			
			
			patronsPane.setItems(resultArray);
		}
	}
}
