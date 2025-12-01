import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Page to view a User account
 */
public class AccountPane extends VBox{
	User user;
	GridPane idPane = new GridPane();
	VBox finesBox = new VBox();
	VBox holdsBox = new VBox();
	VBox checkoutsBox = new VBox();
	
	public AccountPane(User user) {
		this.user = user;
		
		idPane.setPadding(new Insets(10));
		idPane.setHgap(10);
		idPane.setVgap(7);
		
		setFinesBox();
		setCheckoutsBox();
		setHoldsBox();
		
		Label titleLbl = new Label("View Account");
		GridPane.setConstraints(titleLbl, 0, 0);
		
		Label infoSub = new Label("Personal Information");
		GridPane.setConstraints(infoSub, 0, 1);
		
		Label firstNameLbl = new Label("First Name: ");
		TextField firstNameTxt = new TextField(user.getFirstName());
		GridPane.setConstraints(firstNameLbl, 0, 2);
		GridPane.setConstraints(firstNameTxt, 1, 2);
		
		Label lastNameLbl = new Label("Last Name: ");
		TextField lastNameTxt = new TextField(user.getLastName());
		GridPane.setConstraints(lastNameLbl, 2, 2);
		GridPane.setConstraints(lastNameTxt, 3, 2);
		
		Label usernameLbl = new Label("Username: ");
		TextField usernameTxt = new TextField(user.getUsername());
		GridPane.setConstraints(usernameLbl, 0, 3);
		GridPane.setConstraints(usernameTxt, 1, 3);
		
		Label cardNumLbl = new Label("Card Number: ");
		TextField cardNumTxt = new TextField();
		if(user instanceof Patron) {
			cardNumTxt.setText(Integer.toString(((Patron)user).getCardNum()));
		}
		GridPane.setConstraints(cardNumLbl, 2, 3);
		GridPane.setConstraints(cardNumTxt, 3, 3);
		
		Label accessLbl = new Label("Access Level: ");
		ComboBox<AccessLevel> accessCmb = new ComboBox<>();
		accessCmb.getItems().setAll(AccessLevel.values());
		GridPane.setConstraints(cardNumLbl, 2, 3);
		GridPane.setConstraints(cardNumTxt, 3, 3);
		
		Label addressLbl = new Label("Address: ");
		TextField addressTxt = new TextField();
		if(user instanceof Patron) {
			addressTxt.setText(((Patron)user).getAddress());
		}
		GridPane.setConstraints(addressLbl, 0, 4);
		GridPane.setConstraints(addressTxt, 1, 4);
		GridPane.setColumnSpan(addressTxt, 3);
		
		Label emailLbl = new Label("Email: ");
		TextField emailTxt = new TextField();
		if(user instanceof Patron) {
			emailTxt.setText(((Patron)user).getEmail());
		}
		GridPane.setConstraints(emailLbl, 0, 5);
		GridPane.setConstraints(emailTxt, 1, 5);
		
		Label phoneLbl = new Label("Phone: ");
		TextField phoneTxt = new TextField();
		if(user instanceof Patron) {
			phoneTxt.setText(((Patron)user).getPhone());
		}
		GridPane.setConstraints(phoneLbl, 2, 5);
		GridPane.setConstraints(phoneTxt, 3, 5);
		
		Label dateJoinedLbl = new Label("Date Joined: ");
		DatePicker dateJoinedPicker = new DatePicker();
		if(user instanceof Patron) {
			LocalDate dateJoined = LocalDate.parse(((Patron)user).getDateJoined());
			dateJoinedPicker.setValue(dateJoined);
		}
		dateJoinedPicker.setConverter(DateConverter.getConverter());
		GridPane.setConstraints(dateJoinedLbl, 0, 6);
		GridPane.setConstraints(dateJoinedPicker, 1, 6);
		
		Label stopLbl = new Label("Has Stop: ");
		ComboBox<Boolean> stopCmb = new ComboBox<>();
		stopCmb.getItems().setAll(true, false);
		if(user instanceof Patron) {
			stopCmb.setValue(((Patron)user).getHasStop());
		}
		GridPane.setConstraints(stopLbl, 2, 6);
		GridPane.setConstraints(stopCmb, 3, 6);
		
		Label homeLbl = new Label("Home Location: ");
		ComboBox<Location> homeCmb = new ComboBox<>();
		homeCmb.getItems().setAll(Location.values());
		if(user instanceof Patron) {
			homeCmb.setValue(((Patron)user).getHomeLocation());
		}
		GridPane.setConstraints(homeLbl, 0, 7);
		GridPane.setConstraints(homeCmb, 1, 7);
		
		Button saveBtn = new Button("Save Changes");
		GridPane.setConstraints(saveBtn, 0, 8);
		
		Button finesBtn = new Button("View Fines");
		GridPane.setConstraints(finesBtn, 0, 9);
		
		Button holdsBtn = new Button("View Holds");
		GridPane.setConstraints(holdsBtn, 1, 9);
		
		Button checkoutsBtn = new Button("View Checked Out");
		GridPane.setConstraints(checkoutsBtn, 2, 9);
		
		idPane.getChildren().addAll(titleLbl, infoSub, firstNameLbl, firstNameTxt, lastNameLbl,
				lastNameTxt);
		
		// card number should NOT be editable after creation
		cardNumTxt.setEditable(false);
		
		
		if(user instanceof Employee) {
			idPane.getChildren().addAll(accessLbl, accessCmb);
		} else {
			idPane.getChildren().addAll(addressLbl, addressTxt, emailLbl, emailTxt, phoneLbl, phoneTxt,
				dateJoinedLbl, dateJoinedPicker, stopLbl, stopCmb, homeLbl, homeCmb, finesBtn,
				holdsBtn, checkoutsBtn);
		}
		
		stopCmb.setDisable(true);
		dateJoinedPicker.setDisable(true);
		
		if(!(Main.getUser().equals(user)) || (user instanceof Employee && ((Employee) user).getAccessLevel() == AccessLevel.VIEW)) {
			firstNameTxt.setEditable(false);
			lastNameTxt.setEditable(false);
			accessCmb.setDisable(true);
			addressTxt.setEditable(false);
			emailTxt.setEditable(false);
			phoneTxt.setEditable(false);
			homeCmb.setDisable(true);
		} else if(Main.getUser().equals(user)) {
			idPane.getChildren().add(saveBtn);	
		}
		if(Main.getUser() instanceof Employee) {
			Employee employee = (Employee)Main.getUser();
			if(employee.getAccessLevel() == AccessLevel.ADD) {
				// only highest access level can change these fields
				idPane.getChildren().add(saveBtn);
				stopCmb.setDisable(false);
				dateJoinedPicker.setDisable(false);
				firstNameTxt.setEditable(true);
				lastNameTxt.setEditable(true);
				accessCmb.setDisable(false);
				addressTxt.setEditable(true);
				emailTxt.setEditable(true);
				phoneTxt.setEditable(true);
				homeCmb.setDisable(false);
				
			}
		}
		
		getChildren().add(idPane);
		
		finesBtn.setOnAction(e -> {
			if(getChildren().contains(checkoutsBox)) {
				getChildren().remove(checkoutsBox);
			}
			if(getChildren().contains(holdsBox)) {
				getChildren().remove(holdsBox);
			}
			
			getChildren().add(finesBox);
		});
		
		holdsBtn.setOnAction(e -> {
			if(getChildren().contains(checkoutsBox)) {
				getChildren().remove(checkoutsBox);
			}
			if(getChildren().contains(finesBox)) {
				getChildren().remove(finesBox);
			}
			
			getChildren().add(holdsBox);
		});
		
		checkoutsBtn.setOnAction(e -> {
			if(getChildren().contains(holdsBox)) {
				getChildren().remove(holdsBox);
			}
			if(getChildren().contains(finesBox)) {
				getChildren().remove(finesBox);
			}
			
			getChildren().add(checkoutsBox);
		});
		
		saveBtn.setOnAction(e -> {
			user.setFirstName(firstNameTxt.getText());
			user.setLastName(lastNameTxt.getText());
			user.setUsername(usernameTxt.getText());
			if(user instanceof Employee) {
				((Employee) user).setAccessLevel(accessCmb.getValue());
				// TODO: update changes in db
			}
			else if(user instanceof Patron) {
				((Patron)user).setAddress(addressTxt.getText());
				((Patron)user).setCardNum(Integer.parseInt(cardNumTxt.getText()));
				((Patron)user).setDateJoined(dateJoinedPicker.getValue().toString());
				((Patron)user).setEmail(emailTxt.getText());
				((Patron)user).setPhone(phoneTxt.getText());
				((Patron)user).setHasStop(stopCmb.getValue());
				
				// TODO: update changes in db
			}
		});
		
	}
	
	private void setFinesBox() {
		Label finesLbl = new Label("Fines");
		
		TableView<Fine> fineTable = new TableView<>();
		
		ArrayList<TableColumn<Fine, ?>> cols = new ArrayList<>();
		
		TableColumn<Fine, Double> amountCol = new TableColumn<>("Amount");
		amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
		cols.add(amountCol);
		
		TableColumn<Fine, String> dateCol = new TableColumn<>("Date Began");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("dateBegan"));
		cols.add(dateCol);
		
		TableColumn<Fine, Item> itemCol = new TableColumn<>("Item");
		itemCol.setCellValueFactory(new PropertyValueFactory<>("item"));
		cols.add(itemCol);
		
		fineTable.getColumns().setAll(cols);
		
		if(user instanceof Patron) {
			ObservableList<Fine> listToDisplay = FXCollections.observableList(((Patron)user).getFines());
			fineTable.setItems(listToDisplay);
		}
		
		finesBox.getChildren().addAll(finesLbl, fineTable);
	}
	
	private void setHoldsBox() {
		Label holdsLbl = new Label("Holds");
		
		TableView<Hold> fineTable = new TableView<>();
		
		ArrayList<TableColumn<Hold, ?>> cols = new ArrayList<>();
		
		TableColumn<Hold, Item> itemCol = new TableColumn<>("Item");
		itemCol.setCellValueFactory(new PropertyValueFactory<>("item"));
		cols.add(itemCol);
		
		TableColumn<Hold, String> datePlacedCol = new TableColumn<>("Date Placed");
		itemCol.setCellValueFactory(new PropertyValueFactory<>("datePlaced"));
		cols.add(datePlacedCol);
		
		TableColumn<Hold, String> dateExpiresCol = new TableColumn<>("Date Expires");
		itemCol.setCellValueFactory(new PropertyValueFactory<>("dateExpires"));
		cols.add(dateExpiresCol);
		
		TableColumn<Hold, Location> locationCol = new TableColumn<>("Pickup Location");
		itemCol.setCellValueFactory(new PropertyValueFactory<>("pickUpLocation"));
		cols.add(locationCol);
		
		fineTable.getColumns().setAll(cols);
		
		if(user instanceof Patron) {
			ObservableList<Hold> listToDisplay = FXCollections.observableList(((Patron)user).getHolds());
			fineTable.setItems(listToDisplay);
		}
		
		holdsBox.getChildren().addAll(holdsLbl, fineTable);
	}
	
	private void setCheckoutsBox() {
		Label checkoutsLbl = new Label("Checked Out Items");
		
		TableView<Item> checkoutsTable = new TableView<>();
		
		ArrayList<TableColumn<Item, ?>> cols = new ArrayList<>();
		
		TableColumn<Item, Record> recordCol = new TableColumn<>("Record");
		recordCol.setCellValueFactory(new PropertyValueFactory<>("record"));
		cols.add(recordCol);
		
		TableColumn<Item, Location> locationCol = new TableColumn<>("Location");
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		cols.add(locationCol);

		
		checkoutsTable.getColumns().setAll(cols);
		
		if(user instanceof Patron) {
			ObservableList<Item> listToDisplay = FXCollections.observableList(((Patron)user).getCheckouts());
			checkoutsTable.setItems(listToDisplay);
		}
		
		checkoutsBox.getChildren().addAll(checkoutsLbl, checkoutsTable);
	}
}
