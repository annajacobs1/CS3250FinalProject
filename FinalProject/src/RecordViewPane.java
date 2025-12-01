import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * View information about a Record
 */
public class RecordViewPane extends BorderPane{
	private Record record;
	private GridPane recordData;
	private VBox holdsData;
	private VBox itemPane;
	private TableView<Item> itemTable;
	
	
	public RecordViewPane(Record record) {
		this.record = record;
		recordData = new GridPane();
		recordData.setPadding(new Insets(10));
		recordData.setHgap(10);
		recordData.setVgap(7);
		
		holdsData = new VBox();
		
		itemPane = new VBox();
		itemTable = new TableView<>();
		Label itemTitle = new Label("Items");
		itemPane.getChildren().addAll(itemTitle, itemTable);
		
		setRecordData();
		setHoldsData();
		setItemTable();
		
		setTop(recordData);
		setLeft(holdsData);
		setCenter(itemPane);
	}
	
	
	/**
	 * Set information for top pane to view the fields of the record itself
	 */
	private void setRecordData() {
		Label recordNumberLbl = new Label("Record Number: ");
		TextField recordNumberTxt = new TextField(record.getRecordNum());
		GridPane.setConstraints(recordNumberLbl, 0, 0);
		GridPane.setConstraints(recordNumberTxt, 1, 0);
		
		Label titleLbl = new Label("Title: ");
		TextField titleTxt = new TextField(record.getTitle());
		GridPane.setConstraints(titleLbl, 0, 1);
		GridPane.setConstraints(titleTxt, 1, 1);
		
		Label callNumLbl = new Label("Call Number: ");
		TextField callNumTxt = new TextField(record.getCallNum());
		GridPane.setConstraints(callNumLbl, 0, 2);
		GridPane.setConstraints(callNumTxt, 1, 2);
		
		Label circulatingLbl = new Label("Circulating: ");
		TextField circulatingTxt = new TextField();
		if(record.isCirculating()) {
			circulatingTxt.setText("Yes");
		} else {
			circulatingTxt.setText("No");
		}
		GridPane.setConstraints(circulatingLbl, 0, 3);
		GridPane.setConstraints(circulatingTxt, 1, 3);
		
		Label sectionLbl = new Label("Section: ");
		GridPane.setConstraints(sectionLbl, 0, 4);
		ComboBox<Section> sectionCmb = new ComboBox<>();
		sectionCmb.getItems().setAll(Section.values());
		sectionCmb.setValue(record.getSection());
		GridPane.setConstraints(sectionCmb, 1, 4);
		
		ImageView coverView = new ImageView(record.getImage());
		coverView.setFitWidth(100);
		coverView.setPreserveRatio(true);
		GridPane.setConstraints(coverView, 5, 0);
		GridPane.setRowSpan(coverView, 5);
		
		recordData.getChildren().addAll(titleLbl,titleTxt,callNumLbl,callNumTxt,
				circulatingLbl, circulatingTxt, sectionLbl,sectionCmb, coverView);
		
		if(Main.getUser() instanceof Employee) {
			recordData.getChildren().addAll(recordNumberLbl, recordNumberTxt);
			
			if(((Employee)Main.getUser()).getAccessLevel() == AccessLevel.VIEW) {
				recordNumberTxt.setEditable(false);
				titleTxt.setEditable(false);
				callNumTxt.setEditable(false);
				circulatingTxt.setEditable(false);
				sectionCmb.setDisable(true);
			} else {
				Button saveBtn = new Button("Save Changes");
				GridPane.setConstraints(saveBtn, 5, 7);
				
				Button addBtn = new Button("Add an Item");
				GridPane.setConstraints(addBtn, 5, 6);
				addBtn.setOnAction( e -> {
					setCenter(new ItemAddPane(record, this));
				});
				
				recordData.getChildren().addAll(saveBtn, addBtn);
			}
		}
		
		if(Main.getUser() instanceof Patron) {
			recordNumberTxt.setEditable(false);
			titleTxt.setEditable(false);
			callNumTxt.setEditable(false);
			circulatingTxt.setEditable(false);
			sectionCmb.setDisable(true);
		}
		
		
		if(record instanceof BookRecord) {
			Label authorFirstLbl = new Label("Author First Name: ");
			TextField authorFirstTxt = new TextField(((BookRecord)record).getAuthorFirstName());
			GridPane.setConstraints(authorFirstLbl, 3, 0);
			GridPane.setConstraints(authorFirstTxt, 4, 0);
			
			Label authorLastLbl = new Label("Author Last Name: ");
			TextField authorLastTxt = new TextField(((BookRecord)record).getAuthorLastName());
			GridPane.setConstraints(authorLastLbl, 3, 1);
			GridPane.setConstraints(authorLastTxt, 4, 1);
			
			Label publicationDateLbl = new Label("Publication Date: ");
			LocalDate publicationDate = LocalDate.parse(((BookRecord)record).getPublicationDate());
			DatePicker publicationDatePicker = new DatePicker(publicationDate);
			publicationDatePicker.setConverter(DateConverter.getConverter());
			GridPane.setConstraints(publicationDateLbl, 3, 2);
			GridPane.setConstraints(publicationDatePicker, 4, 2);
			
			Label editionLbl = new Label("Edition: ");
			TextField editionTxt = new TextField(((BookRecord)record).getEdition());
			GridPane.setConstraints(editionLbl, 3, 3);
			GridPane.setConstraints(editionTxt, 4, 3);
			
			Label isbnLbl = new Label("ISBN: ");
			TextField isbnTxt = new TextField(Long.toString(((BookRecord)record).getIsbn()));
			GridPane.setConstraints(isbnLbl, 3, 4);
			GridPane.setConstraints(isbnTxt, 4, 4);
			
			Label genreLbl = new Label("Genre: ");
			TextField genreTxt = new TextField(((BookRecord)record).getGenre());
			GridPane.setConstraints(genreLbl, 3, 5);
			GridPane.setConstraints(genreTxt, 4, 5);
			
			recordData.getChildren().addAll(authorFirstLbl, authorFirstTxt, authorLastLbl, authorLastTxt,
					publicationDateLbl, publicationDatePicker, editionLbl, editionTxt, isbnLbl, isbnTxt,
					genreLbl, genreTxt);
			
			if(Main.getUser() instanceof Employee) {
				if(((Employee)Main.getUser()).getAccessLevel() == AccessLevel.VIEW) {
					authorFirstTxt.setEditable(false);
					authorLastTxt.setEditable(false);
					publicationDatePicker.setDisable(true);
					editionTxt.setEditable(false);
					isbnTxt.setEditable(false);
					genreTxt.setEditable(false);
				}
			}
			
			if(Main.getUser() instanceof Patron) {
				authorFirstTxt.setEditable(false);
				authorLastTxt.setEditable(false);
				publicationDatePicker.setEditable(false);
				editionTxt.setEditable(false);
				isbnTxt.setEditable(false);
				genreTxt.setEditable(false);
			}
		}
		
		if(record instanceof AvItemRecord) {
			Label publicationYearLbl = new Label("Publication Year: ");
			TextField publicationYearTxt = new TextField(Integer.toString(((AvItemRecord)record).getPublicationYear()));
			GridPane.setConstraints(publicationYearLbl, 3, 0);
			GridPane.setConstraints(publicationYearTxt, 4, 0);
			
			Label discCountLbl = new Label("Disc Count: ");
			TextField discCountTxt = new TextField(Integer.toString(((AvItemRecord)record).getDiscCount()));
			GridPane.setConstraints(discCountLbl, 3, 1);
			GridPane.setConstraints(discCountTxt, 4, 1);
			
			Label avTypeLbl = new Label("AV Type: ");
			TextField avTypeTxt = new TextField(((AvItemRecord)record).getType().toString());
			GridPane.setConstraints(avTypeLbl, 3, 2);
			GridPane.setConstraints(avTypeTxt, 4, 2);
			
			Label volumeLbl = new Label("Volume: ");
			TextField volumeTxt = new TextField(Integer.toString(((AvItemRecord)record).getVolume()));
			GridPane.setConstraints(volumeLbl, 3, 3);
			GridPane.setConstraints(volumeTxt, 4, 3);
			
			recordData.getChildren().addAll(publicationYearLbl, publicationYearTxt, discCountLbl, discCountTxt, 
					avTypeLbl, avTypeTxt, volumeLbl, volumeTxt);
			
			if(Main.getUser() instanceof Employee) {
				if(((Employee)Main.getUser()).getAccessLevel() == AccessLevel.VIEW) {
					publicationYearTxt.setEditable(false);
					discCountTxt.setEditable(false);
					avTypeTxt.setEditable(false);
					volumeTxt.setEditable(false);
				}
			}
			
			if(Main.getUser() instanceof Patron) {
				publicationYearTxt.setEditable(false);
				discCountTxt.setEditable(false);
				avTypeTxt.setEditable(false);
				volumeTxt.setEditable(false);
			}
		}
		
		if(record instanceof PeriodicalRecord) {
			Label editionLbl = new Label("Edition: ");
			TextField editionTxt = new TextField(((PeriodicalRecord)record).getEdition());
			GridPane.setConstraints(editionLbl, 3, 0);
			GridPane.setConstraints(editionTxt, 4, 0);
			
			Label volumeLbl = new Label("Volume: ");
			TextField volumeTxt = new TextField(Integer.toString(((PeriodicalRecord)record).getVolume()));
			GridPane.setConstraints(volumeLbl, 3, 1);
			GridPane.setConstraints(volumeTxt, 4, 1);
			
			Label publicationDateLbl = new Label("Publication Date: ");
			LocalDate publicationDate = LocalDate.parse(((PeriodicalRecord)record).getPublicationDate());
			DatePicker publicationDatePicker = new DatePicker(publicationDate);
			publicationDatePicker.setConverter(DateConverter.getConverter());
			GridPane.setConstraints(publicationDateLbl, 3, 2);
			GridPane.setConstraints(publicationDatePicker, 4, 2);
			
			recordData.getChildren().addAll(editionLbl, editionTxt, volumeLbl, volumeTxt, 
					publicationDateLbl, publicationDatePicker);
			
			if(Main.getUser() instanceof Employee) {
				if(((Employee)Main.getUser()).getAccessLevel() == AccessLevel.VIEW) {
					editionTxt.setEditable(false);
					volumeTxt.setEditable(false);
					publicationDatePicker.setDisable(true);
				}
			}
			
			if(Main.getUser() instanceof Patron) {
				editionTxt.setEditable(false);
				volumeTxt.setEditable(false);
				publicationDatePicker.setDisable(true);
			}
		}
		
		if(record instanceof ToyRecord) {
			Label piecesLbl = new Label("Pieces: ");
			TextField piecesTxt = new TextField(Integer.toString(((ToyRecord)record).getPieces()));
			GridPane.setConstraints(piecesLbl, 3, 0);
			GridPane.setConstraints(piecesTxt, 4, 0);
			
			recordData.getChildren().addAll(piecesLbl, piecesTxt);
			
			if(Main.getUser() instanceof Employee) {
				if(((Employee)Main.getUser()).getAccessLevel() == AccessLevel.VIEW) {
					piecesTxt.setEditable(false);
				}
			}
			
			if(Main.getUser() instanceof Patron) {
				piecesTxt.setEditable(false);
			}
		}
	}
	
	private void setHoldsData() {
		ArrayList<Hold> holds = Data.searchHoldsByRecord(record);
		
		ListView<Hold> holdsList = new ListView<>();
		
		for(Hold hold : holds) {
			holdsList.getItems().add(hold);
		}
		
		Label holdsTitleLbl = new Label("Holds");
		
		holdsData.getChildren().addAll(holdsTitleLbl, holdsList);
	}
	
	private void setItemTable() {
		ArrayList<TableColumn<Item, ?>> cols = new ArrayList<>();
		
		TableColumn<Item, Long> barcodeCol = new TableColumn<>("Barcode");
		barcodeCol.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		cols.add(barcodeCol);
		
		TableColumn<Item, Location> locationCol = new TableColumn<>("Location");
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		cols.add(locationCol);
		
		itemTable.getColumns().setAll(cols);
		
		ArrayList<Item> items = Data.searchItemsByRecord(record);
		
		ObservableList<Item> listToDisplay = FXCollections.observableList(items);
		itemTable.setItems(listToDisplay);
		
		itemTable.setRowFactory(table -> {
			TableRow<Item> row = new TableRow<>() {
				@Override
				protected void updateItem(Item item, boolean empty) {
					super.updateItem(item, empty);
				}
			};
			
			row.setOnMouseClicked(e -> {
				if(e.getClickCount() == 2 && (!row.isEmpty())) {
					Item rowData = row.getItem();
					
					this.setCenter(new ItemViewEditPane(null, rowData, this));
				}
			});
			
			return row;
		});
	}
	
	public void resetCenter() {
		setCenter(itemPane);
		setItemTable();
	}
}
