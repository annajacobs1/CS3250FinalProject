import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * Page accessible to employees where you can view various lists
 */
public class ReportsPane extends VBox{
	public ReportsPane() {
		Label titleLbl = new Label("Reports");
		
		Label frequentSubtitleLbl = new Label("Frequent lists");
		
		// Holds
		FlowPane holdsPane = new FlowPane();
		Label holdsLbl = new Label("Holds");
		Button holdsBtn = new Button("Get list");
		
		holdsPane.getChildren().addAll(holdsLbl, holdsBtn);
		
		//
		
		
		Label generateSubtitleLbl = new Label("Generate a Custom List");
		
		// Basically a query builder
		
		getChildren().addAll(titleLbl, frequentSubtitleLbl, holdsPane, generateSubtitleLbl);
	}
}
