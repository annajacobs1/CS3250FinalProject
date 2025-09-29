import javafx.scene.layout.BorderPane;

/**
 * BorderPane to set up the main layout of the page once you log in
 */
public class MainPane extends BorderPane{
	
	public MainPane(User user) {
		NavPane navPane = new NavPane(user);
		// put a navigation bar at the top of the page
		setTop(navPane);
		
		setCenter(navPane.getCurrentPage());
	}
}
