import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ItemAddPane extends GridPane{
	public ItemAddPane(Record record, RecordViewPane parent) {
		setPadding(new Insets(10));
		setHgap(10);
		setVgap(7);
		
		Button backBtn = new Button("Back");
		GridPane.setConstraints(backBtn, 0, 0);
		backBtn.setOnAction(e -> parent.resetCenter());
		
		
		Label barcodeLbl = new Label("Barcode: ");
		TextField barcodeTxt = new TextField();
		GridPane.setConstraints(barcodeLbl, 0, 1);
		GridPane.setConstraints(barcodeTxt, 1, 1);
		
		Label locationLbl = new Label("Location: ");
		GridPane.setConstraints(locationLbl, 0, 2);
		ComboBox<Location> locationCmb = new ComboBox<>();
		locationCmb.getItems().setAll(Location.values());
		GridPane.setConstraints(locationCmb, 1, 2);
		
		Label circsLbl = new Label("Circulations: ");
		TextField circsTxt = new TextField();
		GridPane.setConstraints(circsLbl, 0, 3);
		GridPane.setConstraints(circsTxt, 1, 3);
		
		Label statusLbl = new Label("Status: ");
		GridPane.setConstraints(statusLbl, 0, 4);
		ComboBox<Status> statusCmb = new ComboBox<>();
		statusCmb.getItems().setAll(Status.values());
		GridPane.setConstraints(statusCmb, 1, 4);
		
		
		DatePicker dueDatePicker = new DatePicker();
		Label dueDateLbl = new Label("Due Date: ");
		dueDatePicker.setConverter(DateConverter.getConverter());
		GridPane.setConstraints(dueDateLbl, 0, 5);
		GridPane.setConstraints(dueDatePicker, 1, 5);
		
		getChildren().addAll(dueDateLbl, dueDatePicker, backBtn, barcodeLbl, barcodeTxt, 
				locationLbl, locationCmb, circsLbl, circsTxt, statusLbl, statusCmb);
		

		Button saveBtn = new Button("Save Changes");
		GridPane.setConstraints(saveBtn, 4, 6);
		saveBtn.setOnAction(e -> {
			long barcode = Long.parseLong(barcodeTxt.getText());
			Location location = locationCmb.getValue();
			String dueDate = "";
			if(dueDatePicker.getValue() != null) {
				dueDate = (dueDatePicker.getValue()).toString();
			}
			int circs = Integer.parseInt(circsTxt.getText());
			Status status = statusCmb.getValue();
			
			Item newItem = new Item(record, barcode, location);
			newItem.setDueDate(dueDate);
			newItem.setCirculations(circs);
			newItem.setStatus(status);
			
			Data.addItem(newItem);
			
			parent.resetCenter();
		});
		
		getChildren().add(saveBtn);
	}
		
}

