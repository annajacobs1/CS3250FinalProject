import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Form to place a hold on an item
 */
public class PlaceHoldPane extends GridPane {
	private Item item;
	private Patron patron;
	private ItemInfoPane itemPane;
	
	public PlaceHoldPane(Item selectedItem) {
		setPadding(new Insets(10));
		setVgap(7);
		setHgap(10);
		
		Label placeholder1 = new Label("");
		GridPane.setConstraints(placeholder1, 3, 1);
		Label placeholder2 = new Label("");
		GridPane.setConstraints(placeholder2, 4, 1);
		getChildren().addAll(placeholder1, placeholder2);
		
		// title label
		Label titleLbl = new Label("Place a Hold on an Item");
		titleLbl.getStyleClass().add("title");
		GridPane.setConstraints(titleLbl, 0, 0);
					
		// input barcode for desired item
		Label barcodeLbl = new Label("Enter the Barcode:");
		TextField barcodeTxt = new TextField();
		if(selectedItem != null) {
			barcodeTxt.setText(Long.toString(selectedItem.getBarcode()));
		}
		GridPane.setConstraints(barcodeLbl, 0, 1);
		GridPane.setConstraints(barcodeTxt, 1, 1);
		
		// search for item with entered barcode
		Button searchBtn = new Button("Search");
		GridPane.setConstraints(searchBtn, 0, 2);
		
		// to display a message if barcode search fails
		Label barcodeErrLbl = new Label("");
		GridPane.setConstraints(barcodeErrLbl, 1, 2);
		
		getChildren().addAll(titleLbl, barcodeLbl, barcodeTxt, searchBtn, barcodeErrLbl);
		
		// label to enter patron's card number
		Label patronLbl = new Label("Patron Card Number");
		TextField patronTxt = new TextField();
		GridPane.setConstraints(patronLbl, 2, 1);
		GridPane.setConstraints(patronTxt, 3, 1);
		
		// to display message if card number search fails
		Label patronErrLbl = new Label("");
		GridPane.setConstraints(patronErrLbl, 1, 10);
		
		// Employees do not place holds on books for themselves. Place page
		// is used for employees to place holds on behalf of patrons.
		if(Main.getUser() instanceof Employee) {
			getChildren().addAll(patronLbl, patronTxt, patronErrLbl);
		}
		
		// To add item to patron's checkouts
		Button placeHoldBtn = new Button("Place Hold");
		placeHoldBtn.setVisible(false);
		GridPane.setConstraints(placeHoldBtn, 0, 10);
		
		// To display when the hold place is successful
		Label successLbl = new Label("");
		GridPane.setConstraints(successLbl, 0, 11);
		
		getChildren().addAll(placeHoldBtn, successLbl);
		
		searchBtn.setOnAction(event -> {
			int barcode;
			successLbl.setText("");
			
			for (int i = 0; i < getChildren().size(); i++) {
				Node node = getChildren().get(i);
				if(node instanceof ItemInfoPane) {
					getChildren().remove(node);
					i--;
				}
			}
			
			try {
				barcode = Integer.parseInt(barcodeTxt.getText());
				item = Data.searchByBarcode(barcode);
				barcodeErrLbl.setText("");
				
				itemPane = new ItemInfoPane(item);
				GridPane.setConstraints(itemPane, 0, 3);
				GridPane.setColumnSpan(itemPane, 7);
				GridPane.setRowSpan(itemPane, 5);
				
				getChildren().add(itemPane);
				
				if(item.getRecord().isCirculating()) {
					placeHoldBtn.setVisible(true);
				}
				else {
					getChildren().add(new Label("Item is non-circulating."));
				}
			} catch(NullPointerException e) {
				barcodeErrLbl.setText("Item not found.");
			} catch(NumberFormatException e) {
				barcodeErrLbl.setText("Barcode must be a number");
			}
		});
		
		placeHoldBtn.setOnAction(event -> {
			if(Main.getUser() instanceof Patron) {
				patron = (Patron)Main.getUser();
				if(patron.checkOut(item)) {
					successLbl.setText("Check Out Successful!");
					barcodeTxt.setText("");
					getChildren().removeAll(itemPane, placeHoldBtn);
				} else {
					patronErrLbl.setText("Your account is currently suspended. Please contact an employee for help.");
				}
				
			}
			else {
				try {
					int cardNum = Integer.parseInt(patronTxt.getText());
					patron = Data.searchByCardNum(cardNum);
					
					if(patron.checkOut(item)) {
						successLbl.setText("Hold placed successfully!");
						barcodeTxt.setText("");
						patronTxt.setText("");
						patronErrLbl.setText("");
						barcodeErrLbl.setText("");
						getChildren().removeAll(itemPane, placeHoldBtn);
					} else {
						patronErrLbl.setText("Entered account has a stop");
					}
					
					
				} catch (NullPointerException e) {
					patronErrLbl.setText("No patron found.");
				} catch(NumberFormatException e) {
					patronErrLbl.setText("Please enter a card number.");
				}
				
			}
		});
	}
}
