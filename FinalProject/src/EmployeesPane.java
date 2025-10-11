import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * 
 */
public class EmployeesPane extends Pane{
	public EmployeesPane() {
		Label testLabel = new Label("THIS IS THE EMPLOYEES PANE");
		
		getChildren().add(testLabel);
	}
}
