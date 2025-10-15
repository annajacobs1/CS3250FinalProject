import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Page to view/search list of Patrons using SearchTablePane
 */
public class PatronsPane extends VBox{
	public PatronsPane() {
		Label patronSearchLbl = new Label("Search Patrons");
		
		ArrayList<String> options = new ArrayList<String>();
		options.add("Address");
		options.add("Card Number");
		options.add("Email");
		options.add("First Name");
		options.add("Last Name");
		options.add("Username");
		
		SearchTablePane<Patron> patronsPane = new SearchTablePane<Patron>(Data.getPatrons(),
				options);
		
		ArrayList<String[]> columns = new ArrayList<String[]>();
		String[] cardNumCol = {"Card Number", "cardNum"};
		String[] firstNameCol = {"First Name", "firstName"};
		String[] lastNameCol = {"Last Name", "lastName"};
		String[] usernameCol = {"Username", "username"};
		
		columns.add(cardNumCol);
		columns.add(firstNameCol);
		columns.add(lastNameCol);
		columns.add(usernameCol);
		
		patronsPane.setCols(columns);
		
		getChildren().addAll(patronSearchLbl, patronsPane);
		
		if(Main.getUser() instanceof Employee) {
			if(((Employee)Main.getUser()).getAccessLevel() == AccessLevel.ADD) {
				Button addBtn = new Button("Add Patron");
				// TODO: button click takes you to form to input new patron info
				getChildren().add(addBtn);
			}
		}
	}
}
