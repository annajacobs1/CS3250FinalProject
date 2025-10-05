import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * BorderPane to set up the main layout of the page once you log in
 */
public class MainPane extends BorderPane{
	private NavPane navPane = new NavPane(this);
	
	public MainPane() {
		// put a navigation bar at the top of the page
		// NavPane also takes care of setting Center
		setTop(navPane);
	}
	
	public void setCenterPane(Pane currentPage) {
		this.setCenter(currentPage);
	}
}
