import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * Pane to display information of an item
 */
public class ItemInfoPane extends SplitPane{
	public ItemInfoPane(Item item) {
		GridPane itemInfoBox = new GridPane();
		itemInfoBox.setPadding(new Insets(10));
		itemInfoBox.setHgap(10);
		itemInfoBox.setVgap(7);
		
		StackPane itemImagePane = new StackPane();
		
		Record itemRecord = Data.searchByRecordNum(item.getRecord().getRecordNum());
		
		Image itemImage = itemRecord.getImage();
		ImageView itemImageView = new ImageView(itemImage);
		itemImageView.setFitWidth(100);
		itemImageView.setPreserveRatio(true);
		itemImagePane.getChildren().add(itemImageView);
		
		Label titleLbl = new Label("Title: ");
		GridPane.setConstraints(titleLbl, 0, 0);
		Label titleContent = new Label(itemRecord.getTitle());
		GridPane.setConstraints(titleContent, 1, 0);
		
		
		Label callNoLbl = new Label("Call Number: ");
		GridPane.setConstraints(callNoLbl, 0, 1);
		Label callNoContent = new Label(itemRecord.getCallNum());
		GridPane.setConstraints(callNoContent, 1, 1);
		
		
		Label locationLbl = new Label("Location: ");
		GridPane.setConstraints(locationLbl, 0, 2);
		Label locationContent = new Label(item.getLocation().toString());
		GridPane.setConstraints(locationContent, 1, 2);
		
		
		Label sectionLbl = new Label("Section: ");
		GridPane.setConstraints(sectionLbl, 0, 3);
		Label sectionContent = new Label(itemRecord.getSection().toString());
		GridPane.setConstraints(sectionContent, 1, 3);
		
		
		Label circsLbl = new Label("Circulations: ");
		GridPane.setConstraints(circsLbl, 0, 4);
		Label circsContent = new Label(Integer.toString(item.getCirculations()));
		GridPane.setConstraints(circsContent, 1, 4);
		
		
		itemInfoBox.getChildren().addAll(titleLbl, titleContent, callNoLbl, callNoContent, 
				locationLbl, locationContent, sectionLbl, sectionContent);
		
		// Patrons don't really need to know number of circulations
		if(Main.getUser() instanceof Employee) {
			itemInfoBox.getChildren().addAll(circsLbl, circsContent);
		}
		
		getItems().addAll(itemImagePane, itemInfoBox);
	}
}
