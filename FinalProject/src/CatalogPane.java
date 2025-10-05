import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/*
 * Page to view/search all catalog items
 */
public class CatalogPane extends Pane{
	public CatalogPane() {
		Label testLabel = new Label("THIS IS THE CATALOG PANE");
		
		getChildren().add(testLabel);
	}
}
