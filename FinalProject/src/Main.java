import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Control the view and flow of the application.
 */
public class Main extends Application{
	// Members are static because there will only ever be one Main
	private static User user = null;
	private static Scene scene;
	private static MainPane mainPane;
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Data.loadData();
		LogInPane logInPane = new LogInPane();
		
		scene = new Scene(logInPane, 750, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

//---------------------GETTERS AND SETTERS------------------------------------
	
	/*
	 * Create getters and setters so that other panes have access to change
	 * root or user if necessary (mainly for LogInPane)
	 */
	
	public static User getUser() {
		return user;
	}
	
	public static void setUser(User user) {
		Main.user = user;
	}
	
	public static MainPane getMainPane() {
		return mainPane;
	}

	public static void setMainPane(MainPane mainPane) {
		Main.mainPane = mainPane;
	}
	
	public static Scene getScene() {
		return scene;
	}
	
	public static void setScene(Scene scene) {
		Main.scene = scene;
	}
	
}
