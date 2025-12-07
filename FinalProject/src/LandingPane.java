import java.util.Random;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Home page that will show immediately after login/when home button is clicked
 */
public class LandingPane extends VBox{
	Random random = new Random();
	public LandingPane() {
		if(Main.getUser() != null) {
			Label welcomeLabel = new Label("Welcome in, " + Main.getUser().getFirstName() + "!");
			welcomeLabel.getStyleClass().add("title");
			
			setSpacing(7);
			
			int randomIndex = random.nextInt(Data.getItems().size());
			Item randomItem = Data.getItems().get(randomIndex);
			
			Label featuredLabel = new Label("Featured Item");
			
			ItemInfoPane featuredInfo = new ItemInfoPane(randomItem);
			
			Button checkOutBtn = new Button("View Featured Item");
			checkOutBtn.setOnAction(e -> {
				Main.getMainPane().setCenter(new RecordViewPane(randomItem.getRecord()));
			});
			
			getChildren().addAll(welcomeLabel, featuredLabel, featuredInfo, checkOutBtn);
		}
	}
}
