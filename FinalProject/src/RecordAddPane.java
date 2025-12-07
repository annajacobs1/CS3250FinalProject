import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class RecordAddPane extends HBox{
	String recordType = "";
	
	public RecordAddPane() {
		
		GridPane leftPane = new GridPane();
		leftPane.setPadding(new Insets(10));
		leftPane.setHgap(10);
		leftPane.setVgap(7);
		
		Label recordNumberLbl = new Label("Record Number: ");
		TextField recordNumberTxt = new TextField();
		GridPane.setConstraints(recordNumberLbl, 0, 0);
		GridPane.setConstraints(recordNumberTxt, 1, 0);
		
		Label titleLbl = new Label("Title: ");
		TextField titleTxt = new TextField();
		GridPane.setConstraints(titleLbl, 0, 1);
		GridPane.setConstraints(titleTxt, 1, 1);
		
		Label callNumLbl = new Label("Call Number: ");
		TextField callNumTxt = new TextField();
		GridPane.setConstraints(callNumLbl, 0, 2);
		GridPane.setConstraints(callNumTxt, 1, 2);
		
		Label circulatingLbl = new Label("Circulating: ");
		ComboBox<Boolean> circulatingCmb = new ComboBox<>();
		circulatingCmb.getItems().setAll(true, false);
		GridPane.setConstraints(circulatingLbl, 0, 3);
		GridPane.setConstraints(circulatingCmb, 1, 3);
		
		Label sectionLbl = new Label("Section: ");
		GridPane.setConstraints(sectionLbl, 0, 4);
		ComboBox<Section> sectionCmb = new ComboBox<>();
		sectionCmb.getItems().setAll(Section.values());
		GridPane.setConstraints(sectionCmb, 1, 4);
		
		// TODO: file upload to set cover image
		
		Label typeLbl = new Label("Record Type: ");
		GridPane.setConstraints(typeLbl, 0, 5);
		ComboBox<String> typeCmb = new ComboBox<String>();
		typeCmb.getItems().addAll("Book", "AV Item", "Periodical", "Toy");
		GridPane.setConstraints(typeCmb, 1, 5);
		
		
		Button saveBtn = new Button("Save");
		GridPane.setConstraints(saveBtn, 1, 7);
		
		
		leftPane.getChildren().addAll(recordNumberLbl, recordNumberTxt, titleLbl, titleTxt, 
				callNumLbl, callNumTxt, circulatingLbl, circulatingCmb, sectionLbl,
				sectionCmb, typeLbl, typeCmb, saveBtn);
		
		getChildren().add(leftPane);
		
		GridPane bookPane = new GridPane();
		bookPane.setPadding(new Insets(10));
		bookPane.setHgap(10);
		bookPane.setVgap(7);
		Label authorFirstLbl = new Label("Author First Name: ");
		TextField authorFirstTxt = new TextField();
		GridPane.setConstraints(authorFirstLbl, 0, 0);
		GridPane.setConstraints(authorFirstTxt, 1, 0);
		
		Label authorLastLbl = new Label("Author Last Name: ");
		TextField authorLastTxt = new TextField();
		GridPane.setConstraints(authorLastLbl, 0, 1);
		GridPane.setConstraints(authorLastTxt, 1, 1);
		
		Label publicationDateLbl = new Label("Publication Date: ");
		DatePicker publicationDatePicker = new DatePicker();
		publicationDatePicker.setConverter(DateConverter.getConverter());
		GridPane.setConstraints(publicationDateLbl, 0, 2);
		GridPane.setConstraints(publicationDatePicker, 1, 2);
		
		Label editionLbl = new Label("Edition: ");
		TextField editionTxt = new TextField();
		GridPane.setConstraints(editionLbl, 0, 3);
		GridPane.setConstraints(editionTxt, 1, 3);
		
		Label isbnLbl = new Label("ISBN: ");
		TextField isbnTxt = new TextField();
		GridPane.setConstraints(isbnLbl, 0, 4);
		GridPane.setConstraints(isbnTxt, 1, 4);
		
		Label genreLbl = new Label("Genre: ");
		TextField genreTxt = new TextField();
		GridPane.setConstraints(genreLbl, 0, 5);
		GridPane.setConstraints(genreTxt, 1, 5);
		
		bookPane.getChildren().addAll(authorFirstLbl, authorFirstTxt, authorLastLbl, authorLastTxt,
				publicationDateLbl, publicationDatePicker, editionLbl, editionTxt, isbnLbl, isbnTxt,
				genreLbl, genreTxt);
			
		
		GridPane avPane = new GridPane();
		avPane.setPadding(new Insets(10));
		avPane.setHgap(10);
		avPane.setVgap(7);
		Label publicationYearLbl = new Label("Publication Year: ");
		TextField publicationYearTxt = new TextField();
		GridPane.setConstraints(publicationYearLbl, 0, 0);
		GridPane.setConstraints(publicationYearTxt, 1, 0);
		
		Label discCountLbl = new Label("Disc Count: ");
		TextField discCountTxt = new TextField();
		GridPane.setConstraints(discCountLbl, 0, 1);
		GridPane.setConstraints(discCountTxt, 1, 1);
		
		Label avTypeLbl = new Label("AV Type: ");
		TextField avTypeTxt = new TextField();
		GridPane.setConstraints(avTypeLbl, 0, 2);
		GridPane.setConstraints(avTypeTxt, 1, 2);
		
		Label volumeLbl = new Label("Volume: ");
		TextField volumeTxt = new TextField();
		GridPane.setConstraints(volumeLbl, 0, 3);
		GridPane.setConstraints(volumeTxt, 1, 3);
		
		avPane.getChildren().addAll(publicationYearLbl, publicationYearTxt, discCountLbl, discCountTxt, 
				avTypeLbl, avTypeTxt, volumeLbl, volumeTxt);
			
		
		
		GridPane periodicalPane = new GridPane();
		periodicalPane.setPadding(new Insets(10));
		periodicalPane.setHgap(10);
		periodicalPane.setVgap(7);
		
		Label perEditionLbl = new Label("Edition: ");
		TextField perEditionTxt = new TextField();
		GridPane.setConstraints(perEditionLbl, 0, 0);
		GridPane.setConstraints(perEditionTxt, 1, 0);
		
		Label perVolumeLbl = new Label("Volume: ");
		TextField perVolumeTxt = new TextField();
		GridPane.setConstraints(perVolumeLbl, 0, 1);
		GridPane.setConstraints(perVolumeTxt, 1, 1);
		
		Label perPublicationDateLbl = new Label("Publication Date: ");
		DatePicker perPublicationDatePicker = new DatePicker();
		publicationDatePicker.setConverter(DateConverter.getConverter());
		GridPane.setConstraints(perPublicationDateLbl, 0, 2);
		GridPane.setConstraints(perPublicationDatePicker, 1, 2);
		
		periodicalPane.getChildren().addAll(perEditionLbl, perEditionTxt, perVolumeLbl, perVolumeTxt, 
				perPublicationDateLbl, perPublicationDatePicker);
			
		
		GridPane toyPane = new GridPane();
		toyPane.setPadding(new Insets(10));
		toyPane.setHgap(10);
		toyPane.setVgap(7);
		
		Label piecesLbl = new Label("Pieces: ");
		TextField piecesTxt = new TextField();
		GridPane.setConstraints(piecesLbl, 0, 0);
		GridPane.setConstraints(piecesTxt, 1, 0);
		
		toyPane.getChildren().addAll(piecesLbl, piecesTxt);
	
		
			
		typeCmb.setOnAction(e -> {
			if(getChildren().contains(toyPane))
				getChildren().remove(toyPane);
			if(getChildren().contains(periodicalPane))
				getChildren().remove(periodicalPane);
			if(getChildren().contains(bookPane))
				getChildren().remove(bookPane);
			if(getChildren().contains(avPane))
				getChildren().remove(avPane);
			
			recordType = typeCmb.getValue();
			
			switch (recordType) {
			case "Book":
				getChildren().add(bookPane);
				break;
			case "AV Item":
				getChildren().add(avPane);
				break;
			case "Periodical":
				getChildren().add(periodicalPane);
				break;
			case "Toy":
				getChildren().add(toyPane);
				break;
			}
		});
		
		saveBtn.setOnAction(e -> {
			String recordNum = recordNumberTxt.getText();
			String title = titleTxt.getText();
			String callNum = callNumTxt.getText();
			Section section = sectionCmb.getValue();
			boolean circulating = circulatingCmb.getValue();
			
			switch(recordType) {
			case "Book":
				String authorFirstName = authorFirstTxt.getText();
				String authorLastName = authorLastTxt.getText();
				String publicationDate = publicationDatePicker.getValue().toString();
				String edition = editionTxt.getText();
				long isbn = Long.parseLong(isbnTxt.getText());
				String genre = genreTxt.getText();
				
				BookRecord bookRecord = new BookRecord(recordNum, title, callNum, authorLastName,
						authorFirstName, section, publicationDate, edition, isbn, genre);
				bookRecord.setCirculating(circulating);
				
				Data.addRecord(bookRecord);
				break;
			case "AV Item":
				int publicationYear = Integer.parseInt(publicationYearTxt.getText());
				int discCount = Integer.parseInt(discCountTxt.getText());
				AvItemRecord.AVType avType = AvItemRecord.AVType.valueOf(avTypeTxt.getText());
				int volume = Integer.parseInt(volumeTxt.getText());
				
				AvItemRecord avRecord = new AvItemRecord(recordNum, title, callNum, section,
						publicationYear, discCount, volume, avType);
				
				Data.addRecord(avRecord);
				break;
			case "Periodical":
				String perEdition = perEditionTxt.getText();
				int perVolume = Integer.parseInt(perVolumeTxt.getText());
				String perPublicationDate = perPublicationDatePicker.getValue().toString();
				
				PeriodicalRecord periodicalRecord = new PeriodicalRecord(recordNum, title, callNum, 
						section, perEdition, perPublicationDate, perVolume);
				
				Data.addRecord(periodicalRecord);
				break;
			case "Toy":
				int pieces = Integer.parseInt(piecesTxt.getText());
				
				ToyRecord toyRecord = new ToyRecord(recordNum, title, callNum, section, pieces);
				
				Data.addRecord(toyRecord);
				break;
			}
			Main.getMainPane().setCenter(new CatalogPane());
		});
	}
}
