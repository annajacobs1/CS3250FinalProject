import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Page accessible to employees where you can view various lists
 */
public class ReportsPane extends VBox{
	VBox finesBox = new VBox();
	VBox holdsBox = new VBox();
	VBox circsBox = new VBox();
	
	
	public ReportsPane() {
		Label titleLbl = new Label("Reports");
		titleLbl.getStyleClass().add("title");
		GridPane.setConstraints(titleLbl, 0, 0);
		
		GridPane reportsList = new GridPane();
		reportsList.setHgap(10);
		reportsList.setVgap(7);
		reportsList.setPadding(new Insets(10));
		
		Button holdsBtn = new Button("Holds");
		GridPane.setConstraints(holdsBtn, 1, 0);
		
		Button finesBtn = new Button("Fines");
		GridPane.setConstraints(finesBtn, 1, 1);
		
		
		Button highCircsBtn = new Button("High Circulations");
		GridPane.setConstraints(highCircsBtn, 1, 2);
		
		reportsList.getChildren().addAll(titleLbl, holdsBtn, finesBtn, highCircsBtn);
		
		getChildren().add(reportsList);
		
		setHoldsTable();
		setFinesTable();
		setCircsTable();
		
		finesBtn.setOnAction(e -> {
			if(getChildren().contains(circsBox)) {
				getChildren().remove(circsBox);
			}
			if(getChildren().contains(holdsBox)) {
				getChildren().remove(holdsBox);
			}
			
			getChildren().add(finesBox);
		});
		
		holdsBtn.setOnAction(e -> {
			if(getChildren().contains(circsBox)) {
				getChildren().remove(circsBox);
			}
			if(getChildren().contains(finesBox)) {
				getChildren().remove(finesBox);
			}
			
			getChildren().add(holdsBox);
		});
		
		highCircsBtn.setOnAction(e -> {
			if(getChildren().contains(holdsBox)) {
				getChildren().remove(holdsBox);
			}
			if(getChildren().contains(finesBox)) {
				getChildren().remove(finesBox);
			}
			
			getChildren().add(circsBox);
		});
		
	}
	
	
	public void setHoldsTable() {
		Label holdsLbl = new Label("Holds");
		holdsLbl.getStyleClass().add("title");
		
		TableView<Hold> holdTable = new TableView<>();
		
		ArrayList<TableColumn<Hold, ?>> cols = new ArrayList<>();
		
		TableColumn<Hold, Item> itemCol = new TableColumn<>("Item");
		itemCol.setCellValueFactory(new PropertyValueFactory<>("item"));
		cols.add(itemCol);
		
		TableColumn<Hold, String> datePlacedCol = new TableColumn<>("Date Placed");
		datePlacedCol.setCellValueFactory(new PropertyValueFactory<>("datePlaced"));
		cols.add(datePlacedCol);
		
		TableColumn<Hold, String> dateExpiresCol = new TableColumn<>("Date Expires");
		dateExpiresCol.setCellValueFactory(new PropertyValueFactory<>("dateExpires"));
		cols.add(dateExpiresCol);
		
		TableColumn<Hold, Location> locationCol = new TableColumn<>("Pickup Location");
		locationCol.setCellValueFactory(new PropertyValueFactory<>("pickUpLocation"));
		cols.add(locationCol);
		
		holdTable.getColumns().setAll(cols);
		
		ObservableList<Hold> listToDisplay = FXCollections.observableList(Data.getHolds());
		holdTable.setItems(listToDisplay);
		
		holdsBox.getChildren().addAll(holdsLbl, holdTable);
	}
	
	private void setFinesTable() {
		Label finesLbl = new Label("Fines");
		finesLbl.getStyleClass().add("title");
		
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
		

		ObservableList<Fine> listToDisplay = FXCollections.observableList(Data.getFines());
		fineTable.setItems(listToDisplay);
		
		
		finesBox.getChildren().addAll(finesLbl, fineTable);
	}
	
	private void setCircsTable() {
		Label circsLbl = new Label("High Circulation Items");
		circsLbl.getStyleClass().add("title");
		
		TableView<Item> circsTable = new TableView<>();
		
		ArrayList<TableColumn<Item, ?>> cols = new ArrayList<>();
		
		TableColumn<Item, Long> barcodeCol = new TableColumn<>("Barcode");
		barcodeCol.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		cols.add(barcodeCol);
		
		TableColumn<Item, Location> locationCol = new TableColumn<>("Location");
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		cols.add(locationCol);
		
		TableColumn<Item, Integer> circsCol = new TableColumn<>("Circulations");
		circsCol.setCellValueFactory(new PropertyValueFactory<>("circulations"));
		cols.add(circsCol);

		
		circsTable.getColumns().setAll(cols);

		ObservableList<Item> listToDisplay = FXCollections.observableList(Data.searchByHighCircs(25));
		circsTable.setItems(listToDisplay);
		
		circsBox.getChildren().addAll(circsLbl, circsTable);
	}
}
