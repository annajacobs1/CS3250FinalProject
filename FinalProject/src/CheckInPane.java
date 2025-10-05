import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/*
 * Page for a User to check in an item
 */
public class CheckInPane extends Pane{
	public CheckInPane() {
		Label testLabel = new Label("THIS IS THE CHECK IN PANE");
		
		getChildren().add(testLabel);
	}
}
