import java.util.ArrayList;

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
		//-----------------DELETE LATER: TESTING----------------------------
		// add users for testing
		Data.addEmployee(new Employee("ajacobs", "12345", "Anna", "Jacobs"));
		Employee admin = new Employee("jane", "1234", "Jane", "Doe");
		admin.setAccessLevel(AccessLevel.ADD);
		Data.addEmployee(admin);
		
		Data.addPatron(new Patron("bob", "123", "Bob", "Jones", 987654321, "2023/02/01",
				"123 E 456 S Cool Rd, Ogden, UT, 84401", "bob@example.com"));
		
		// add items for testing
		// Two copies of the same book
		Data.addItem(new Book("b123456", 123456789, "Title", "F Jones", Location.MAIN, Section.ADULT_FICTION, "John Jones",
				"2020/01/01", "First", 123456789123L, "Action"));
		Data.addItem(new Book("b123456", 246810121, "Title", "F Jones", Location.NORTH, Section.ADULT_FICTION, "John Jones",
				"2020/01/01", "First", 123456789123L, "Action"));
		
		// Another two copies of the same book
		Data.addItem(new Book("b654321", 987654321, "Another Title", "781.66 F12345 2000", Location.NORTH, Section.NON_FICTION,
				"Jane Farr", "2000/06/07", "Second", 987654321098L));
		Data.addItem(new Book("b654321", 109876543, "Another Title", "781.66 F12345 2000", Location.EAST, Section.NON_FICTION,
				"Jane Farr", "2000/06/07", "Second", 987654321098L));
		//---------------------------------------------------------------------
		
		
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
