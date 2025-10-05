import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/*
 * Page accessible to employees where you can view various lists
 */
public class ReportsPane extends Pane{
	public ReportsPane() {
		Label testLabel = new Label("THIS IS THE REPORTS PANE");
		
		getChildren().add(testLabel);
	}
}
