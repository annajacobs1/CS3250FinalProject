import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Page with a form to add a new Patron
 */
public class PatronAddPane extends GridPane{
	public PatronAddPane() {
		setPadding(new Insets(10));
		setHgap(10);
		setVgap(7);
		
		Label titleLbl = new Label("Add a Patron");
		titleLbl.getStyleClass().add("title");
		GridPane.setConstraints(titleLbl, 0, 0);
		
		Label cardNumLbl = new Label("Card Number: ");
		TextField cardNumTxt = new TextField();
		GridPane.setConstraints(cardNumLbl, 0, 1);
		GridPane.setConstraints(cardNumTxt, 1, 1);
		GridPane.setColumnSpan(cardNumTxt, 3);
		
		Label firstNameLbl = new Label("First Name: ");
		TextField firstNameTxt = new TextField();
		GridPane.setConstraints(firstNameLbl, 0, 2);
		GridPane.setConstraints(firstNameTxt, 1, 2);
		
		Label lastNameLbl = new Label("Last Name: ");
		TextField lastNameTxt = new TextField();
		GridPane.setConstraints(lastNameLbl, 2, 2);
		GridPane.setConstraints(lastNameTxt, 3, 2);
		
		Label usernameLbl = new Label("Username: ");
		TextField usernameTxt = new TextField();
		GridPane.setConstraints(usernameLbl, 0, 3);
		GridPane.setConstraints(usernameTxt, 1, 3);
		
		Label passwordLbl = new Label("Password: ");
		TextField passwordTxt = new TextField();
		GridPane.setConstraints(passwordLbl, 2, 3);
		GridPane.setConstraints(passwordTxt, 3, 3);
		
		Label addressLbl = new Label("Address: ");
		TextField addressTxt = new TextField();
		GridPane.setConstraints(addressLbl, 0, 4);
		GridPane.setConstraints(addressTxt, 1, 4);
		GridPane.setColumnSpan(addressTxt, 3);
		
		Label emailLbl = new Label("Email: ");
		TextField emailTxt = new TextField();
		GridPane.setConstraints(emailLbl, 0, 5);
		GridPane.setConstraints(emailTxt, 1, 5);
		
		Label phoneLbl = new Label("Phone: ");
		TextField phoneTxt = new TextField();
		GridPane.setConstraints(phoneLbl, 2, 5);
		GridPane.setConstraints(phoneTxt, 3, 5);
		
		Label dateJoinedLbl = new Label("Date Joined: ");
		DatePicker dateJoinedPicker = new DatePicker(LocalDate.now());
		dateJoinedPicker.setConverter(DateConverter.getConverter());
		GridPane.setConstraints(dateJoinedLbl, 0, 6);
		GridPane.setConstraints(dateJoinedPicker, 1, 6);
		
		// Patron may be created with a stop if address still needs validation
		Label stopLbl = new Label("Has Stop: ");
		ComboBox<Boolean> stopCmb = new ComboBox<>();
		stopCmb.getItems().setAll(true, false);
		GridPane.setConstraints(stopLbl, 2, 6);
		GridPane.setConstraints(stopCmb, 3, 6);
		
		Label homeLbl = new Label("Home Location: ");
		ComboBox<Location> homeCmb = new ComboBox<>();
		homeCmb.getItems().setAll(Location.values());
		GridPane.setConstraints(homeLbl, 0, 7);
		GridPane.setConstraints(homeCmb, 1, 7);
		
		Button saveBtn = new Button("Save");
		GridPane.setConstraints(saveBtn, 0, 8);
		
		
		getChildren().addAll(titleLbl, firstNameLbl, firstNameTxt, lastNameLbl, addressLbl, 
				addressTxt, emailLbl, emailTxt, phoneLbl, phoneTxt, dateJoinedLbl, dateJoinedPicker, 
				stopLbl, stopCmb, homeLbl, homeCmb, lastNameTxt, saveBtn, cardNumLbl, cardNumTxt,
				usernameLbl, usernameTxt, passwordLbl, passwordTxt);
		
		saveBtn.setOnAction(e -> {
			String username = usernameTxt.getText();
			String password = passwordTxt.getText();
			String firstName = firstNameTxt.getText();
			String lastName = lastNameTxt.getText();
			int cardNum = Integer.parseInt(cardNumTxt.getText());
			String dateJoined = dateJoinedPicker.getValue().toString();
			String address = addressTxt.getText();
			String email = emailTxt.getText();
			String phone = phoneTxt.getText();
			Location homeLocation = homeCmb.getValue();
			
			Patron patron = new Patron(username, password, firstName, lastName, cardNum,
					dateJoined, address, email);
			patron.setPhone(phone);
			patron.setHomeLocation(homeLocation);
			
			Data.addPatron(patron);
			
			Main.getMainPane().setCenter(new PatronsPane());
		});
	}
}
