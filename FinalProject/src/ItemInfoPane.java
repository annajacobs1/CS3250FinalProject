import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Pane to display information of an item
 */
public class ItemInfoPane extends SplitPane{
	public ItemInfoPane(Item item) {
		VBox itemInfoBox = new VBox();
		StackPane itemImagePane = new StackPane();
		
		Image itemImage = item.getRecord().getImage();
		ImageView itemImageView = new ImageView(itemImage);
		itemImageView.setFitWidth(100);
		itemImageView.setPreserveRatio(true);
		itemImagePane.getChildren().add(itemImageView);
		
		//TODO: Change what fields show based on item type
		// Also make it less ugly and stuff
		Label titleLbl = new Label(item.getRecord().getTitle());
		Label callNoLbl = new Label(item.getRecord().getCallNum());
		Label locationLbl = new Label(item.getLocation().toString());
		Label sectionLbl = new Label(item.getRecord().getSection().toString());
		Label circsLbl = new Label(Integer.toString(item.getCirculations()));
		
		itemInfoBox.getChildren().addAll(titleLbl,callNoLbl,locationLbl,sectionLbl);
		
		// Patrons don't really need to know number of circulations
		if(Main.getUser() instanceof Employee) {
			itemInfoBox.getChildren().add(circsLbl);
		}
		
		getItems().addAll(itemImagePane, itemInfoBox);
	}
}
