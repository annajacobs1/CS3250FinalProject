import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Page that a User goes to to check out an item. User enters the item barcode,
 * then searches for an item with that barcode. 
 */
public class CheckOutPane extends VBox{
	private Item item;
	private Patron patron;
	private ItemInfoPane itemPane;
	
	public CheckOutPane(Item selectedItem) {
		// title label
		Label checkOutLbl = new Label("Check Out Item");
					
		// input barcode for desired item
		Label barcodeLbl = new Label("Enter the Barcode:");
		TextField barcodeTxt = new TextField();
		if(selectedItem != null) {
			barcodeTxt.setText(Long.toString(selectedItem.getBarcode()));
		}
		
		// search for item with entered barcode
		Button searchBtn = new Button("Search for Barcode");
		
		// to display a message if barcode search fails
		Label barcodeErrLbl = new Label("");
		
		getChildren().addAll(checkOutLbl, barcodeLbl, barcodeTxt, searchBtn, barcodeErrLbl);
		
		// label to enter patron's card number
		Label patronLbl = new Label("Patron Card Number");
		TextField patronTxt = new TextField();
		
		// to display message if card number search fails
		Label patronErrLbl = new Label("");
		
		// Employees do not check out books for themselves. Check out page
		// is used for employees to check out items on behalf of patrons.
		if(Main.getUser() instanceof Employee) {
			getChildren().addAll(patronLbl, patronTxt, patronErrLbl);
		}
		
		// To add item to patron's checkouts
		Button checkOutBtn = new Button("Check Out Item");
		checkOutBtn.setVisible(false);
		
		// To display when the checkout is successful
		Label successLbl = new Label("");
		
		getChildren().addAll(checkOutBtn, successLbl);
		
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
				
				getChildren().add(itemPane);
				
				if(item.getStatus() == Status.AVAILABLE && item.getRecord().isCirculating()) {
					checkOutBtn.setVisible(true);
				}
				else {
					getChildren().add(new Label("Item is unavailable."));
				}
			} catch(NullPointerException e) {
				barcodeErrLbl.setText("Item not found.");
			} catch(NumberFormatException e) {
				barcodeErrLbl.setText("Barcode must be a number");
			}
		});
		
		checkOutBtn.setOnAction(event -> {
			if(Main.getUser() instanceof Patron) {
				patron = (Patron)Main.getUser();
				if(patron.checkOut(item)) {
					successLbl.setText("Check Out Successful!");
					barcodeTxt.setText("");
					getChildren().removeAll(itemPane, checkOutBtn);
				} else {
					patronErrLbl.setText("Your account is currently suspended. Please contact an employee for help.");
				}
				
			}
			else {
				try {
					int cardNum = Integer.parseInt(patronTxt.getText());
					patron = Data.searchByCardNum(cardNum);
					
					if(patron.checkOut(item)) {
						successLbl.setText("Check Out Successful!");
						barcodeTxt.setText("");
						patronTxt.setText("");
						getChildren().removeAll(itemPane, checkOutBtn);
					} else {
						patronErrLbl.setText("Entered account has a stop");
					}
					
					
				} catch (NullPointerException e) {
					patronErrLbl.setText("No patron found.");
				} catch(NumberFormatException e) {
					patronErrLbl.setText("Please enter a number");
				}
				
			}
		});
	}
}
