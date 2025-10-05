import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/*
 * Page to view/search list of Patrons
 */
public class PatronsPane extends Pane{
	private ArrayList<Patron> patrons = Main.getPatrons();
	
	public PatronsPane() {
		Label testLabel = new Label("THIS IS THE PATRONS PANE");
		
		getChildren().add(testLabel);
	}
}
