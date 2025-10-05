import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/*
 * Pane to lay out information of an item
 */
public class ItemInfoPane extends SplitPane{
	public ItemInfoPane(Item item) {
		VBox itemInfoBox = new VBox();
		StackPane itemImagePane = new StackPane();
		
		Image itemImage = item.getImage();
		ImageView itemImageView = new ImageView(itemImage);
		itemImageView.setFitWidth(100);
		itemImageView.setPreserveRatio(true);
		itemImagePane.getChildren().add(itemImageView);
		
		//TODO: Change what fields show based on item type
		Label titleLbl = new Label(item.getTitle());
		Label callNoLbl = new Label(item.getCallNum());
		Label locationLbl = new Label(item.getLocation().toString());
		Label sectionLbl = new Label(item.getSection().toString());
		
		itemInfoBox.getChildren().addAll(titleLbl,callNoLbl,locationLbl,sectionLbl);
		
		getItems().addAll(itemImagePane, itemInfoBox);
	}
}
