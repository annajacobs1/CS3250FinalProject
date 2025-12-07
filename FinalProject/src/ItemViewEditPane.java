import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ItemViewEditPane extends GridPane{
	public ItemViewEditPane(Record record, Item item, RecordViewPane parent) {
		setPadding(new Insets(10));
		setHgap(10);
		setVgap(7);
		
		Button backBtn = new Button("Back");
		GridPane.setConstraints(backBtn, 0, 0);
		backBtn.setOnAction(e -> parent.resetCenter());
		
		
		Label barcodeLbl = new Label("Barcode: ");
		TextField barcodeTxt = new TextField(Long.toString(item.getBarcode()));
		GridPane.setConstraints(barcodeLbl, 0, 1);
		GridPane.setConstraints(barcodeTxt, 1, 1);
		
		Label locationLbl = new Label("Location: ");
		GridPane.setConstraints(locationLbl, 0, 2);
		ComboBox<Location> locationCmb = new ComboBox<>();
		locationCmb.getItems().setAll(Location.values());
		locationCmb.setValue(item.getLocation());
		GridPane.setConstraints(locationCmb, 1, 2);
		
		Label circsLbl = new Label("Circulations: ");
		TextField circsTxt = new TextField(Integer.toString(item.getCirculations()));
		GridPane.setConstraints(circsLbl, 0, 3);
		GridPane.setConstraints(circsTxt, 1, 3);
		
		Label statusLbl = new Label("Status: ");
		GridPane.setConstraints(statusLbl, 0, 4);
		ComboBox<Status> statusCmb = new ComboBox<>();
		statusCmb.getItems().setAll(Status.values());
		statusCmb.setValue(item.getStatus());
		GridPane.setConstraints(statusCmb, 1, 4);
		
		
		Label checkedOutLbl = new Label("Availability: ");
		TextField checkedOutTxt = new TextField();
		GridPane.setConstraints(checkedOutLbl, 0, 4);
		if(item.getStatus() == Status.AVAILABLE) {
			checkedOutTxt.setText("Available");
		} else {
			checkedOutTxt.setText("Not Available");
		}
		GridPane.setConstraints(checkedOutTxt, 1, 4);
		
		DatePicker dueDatePicker = new DatePicker();
		if(item.getDueDate() != null && !item.getDueDate().equals("")) {
			Label dueDateLbl = new Label("Due Date: ");
			LocalDate dueDate = LocalDate.parse(item.getDueDate());
			dueDatePicker.setValue(dueDate);
			dueDatePicker.setConverter(DateConverter.getConverter());
			GridPane.setConstraints(dueDateLbl, 0, 5);
			GridPane.setConstraints(dueDatePicker, 1, 5);
			
			getChildren().addAll(dueDateLbl, dueDatePicker);
		}
		
		Button checkOutBtn = new Button("Check Out Item");
		GridPane.setConstraints(checkOutBtn, 1, 6);
		checkOutBtn.setOnAction(e -> Main.getMainPane().setCenter(new CheckOutPane(item)));
		
		Button holdBtn = new Button("Place Hold on Item");
		GridPane.setConstraints(holdBtn, 2, 6);
		holdBtn.setOnAction(e -> Main.getMainPane().setCenter(new PlaceHoldPane(item)));
		
		getChildren().addAll(backBtn, barcodeLbl, barcodeTxt, locationLbl, locationCmb);
		if(item.getStatus() == Status.AVAILABLE && item.getRecord().isCirculating()) {
			getChildren().add(checkOutBtn);
		}
		if(item.getRecord().isCirculating()) {
			getChildren().add(holdBtn);
		}
		
		
		if(Main.getUser() instanceof Employee) {
			getChildren().addAll(circsLbl,circsTxt,statusLbl,statusCmb);
			
			if(((Employee)Main.getUser()).getAccessLevel() == AccessLevel.VIEW) {
				barcodeTxt.setEditable(false);
				locationCmb.setDisable(true);
				circsTxt.setEditable(false);
				statusCmb.setDisable(true);
				dueDatePicker.setDisable(true);
				
			} else {
				Button saveBtn = new Button("Save Changes");
				GridPane.setConstraints(saveBtn, 4, 6);
				saveBtn.setOnAction(e -> {
					
					item.setBarcode(Long.parseLong(barcodeTxt.getText()));
					item.setLocation(locationCmb.getValue());
					item.setDueDate((dueDatePicker.getValue()).toString());
					item.setCirculations(Integer.parseInt(circsTxt.getText()));
					item.setStatus(statusCmb.getValue());
					// Save changes to db
					Data.updateItem(barcodeTxt.getText(), "location", "'" + locationCmb.getValue().toString() + "'");
					Data.updateItem(barcodeTxt.getText(), "circulations", circsTxt.getText());
					Data.updateItem(barcodeTxt.getText(), "status", "'" + statusCmb.getValue().toString() + "'");
				});
				
				getChildren().add(saveBtn);
			}
		}
		
		if(Main.getUser() instanceof Patron) {
			getChildren().addAll(checkedOutTxt, checkedOutLbl);
			barcodeTxt.setEditable(false);
			locationCmb.setDisable(true);
			checkedOutTxt.setDisable(true);
			dueDatePicker.setDisable(true);
		}
	}
}
