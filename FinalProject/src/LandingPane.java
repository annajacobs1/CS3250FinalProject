import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Home page that will show immediately after login/when home button is clicked
 */
public class LandingPane extends GridPane{
	public LandingPane() {
		if(Main.getUser() != null) {
			Label welcomeLabel = new Label("Welcome in, " + Main.getUser().getFirstName());
			add(welcomeLabel, 0, 0);
		}
	}
}
