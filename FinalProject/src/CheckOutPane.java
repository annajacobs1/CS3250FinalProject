import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/*
 * Page that a User goes to to check out an item. User enters the item barcode
 */
public class CheckOutPane extends VBox{
	private Item item;
	private Patron patron;
	
	public CheckOutPane() {
		Label barcodeLbl = new Label("Enter the Barcode:");
		TextField barcodeTxt = new TextField();
		
		Button searchBtn = new Button("Search for Barcode");
		
		getChildren().addAll(barcodeLbl, barcodeTxt, searchBtn);
		

		Label patronLbl = new Label("Patron");
		TextField patronTxt = new TextField();
		
		if(Main.getUser() instanceof Employee) {
			getChildren().addAll(patronLbl, patronTxt);
		}
		
		Button checkOutBtn = new Button("Check Out Item");
		
		searchBtn.setOnAction(event -> {
			int barcode;
			try {
				barcode = Integer.parseInt(barcodeTxt.getText());
				item = Main.searchByBarcode(barcode);
				ItemInfoPane itemPane = new ItemInfoPane(item);
				
				getChildren().addAll(itemPane, checkOutBtn);
			} catch(Error e) {
				// show error label
			}
		});
		
		checkOutBtn.setOnAction(event -> {
			if(Main.getUser() instanceof Patron) {
				patron = (Patron)Main.getUser();
				patron.checkOut(item);
			}
			else {
				try {
					int cardNum = Integer.parseInt(patronTxt.getText());
					item = Main.searchByBarcode(cardNum);
					patron = Main.searchByCardNum(cardNum);
				} catch (Error e) {
					// error label
				}
				
			}
		});
		
		
	}
}
