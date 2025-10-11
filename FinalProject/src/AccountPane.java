import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Page to allow a Patron to view their account
 */
public class AccountPane extends Pane{
	public AccountPane() {
		Label testLabel = new Label("THIS IS THE ACCOUNT PANE");
		
		getChildren().add(testLabel);
	}
}
