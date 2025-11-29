import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Page for a User to check in an item
 * 
 * At this point I just copied the code from CheckOutPane. I'll possibly want to
 * make another pane so this code isn't repeated.
 */
public class CheckInPane extends VBox{
	private Item item;
		private Patron patron;
		private ItemInfoPane itemPane;
		
		public CheckInPane() {
			// title label
			Label checkInLbl = new Label("Check In Item");
			
			// input barcode for desired item
			Label barcodeLbl = new Label("Enter the Barcode:");
			TextField barcodeTxt = new TextField();
			
			// search for item with entered barcode
			Button searchBtn = new Button("Search for Barcode");
			
			// to display a message if barcode search fails
			Label barcodeErrLbl = new Label("");
			
			getChildren().addAll(checkInLbl, barcodeLbl, barcodeTxt, searchBtn, barcodeErrLbl);
			
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
			Button checkInBtn = new Button("Check In Item");
			checkInBtn.setVisible(false);
			
			// To display when the checkout is successful
			Label successLbl = new Label("");
			
			getChildren().addAll(checkInBtn, successLbl);
			
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
					
					if(item.getStatus() == Status.CHECKED_OUT) {
						checkInBtn.setVisible(true);
					}
					else {
						getChildren().add(new Label("Item is not checked out, "
								+ "so it cannot be checked in."));
					}
				} catch(NullPointerException e) {
					barcodeErrLbl.setText("Item not found.");
				} catch(NumberFormatException e) {
					barcodeErrLbl.setText("Barcode must be a number");
				}
			});
			
			checkInBtn.setOnAction(event -> {
				if(Main.getUser() instanceof Patron) {
					patron = (Patron)Main.getUser();
					if(patron.findItemInCheckouts(item)) {
						patron.checkIn(item);
						successLbl.setText("Check in successful!");
						barcodeTxt.setText("");
						patronTxt.setText("");
						getChildren().removeAll(itemPane, checkInBtn);
					} else {
						patronErrLbl.setText("You do not currently have this item checked out.");
					}
					
				}
				else {
					try {
						int cardNum = Integer.parseInt(patronTxt.getText());
						patron = Data.searchByCardNum(cardNum);
						
						if(patron.checkOut(item)) {
							successLbl.setText("Check In Successful!");
							barcodeTxt.setText("");
							patronTxt.setText("");
							getChildren().removeAll(itemPane, checkInBtn);
						} else {
							patronErrLbl.setText("Item not in Patron checkouts.");
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
