import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Control the view and flow of the application
 */
public class Main extends Application{
	private static User user;
	private static ArrayList<User> users = new ArrayList<User>();
	private static ArrayList<Item> items = new ArrayList<Item>();
	private static Scene scene;
	private static MainPane mainPane;
	

	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// add user for testing
		users.add(new User("ajacobs", "12345", "Anna", "Jacobs"));
		
		LogInPane logInPane = new LogInPane();
		scene = new Scene(logInPane, 750, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// instantiate main layout page
		// pass in user because certain elements will/will not show up based on
		// user type/access level
		mainPane = new MainPane(user);
		
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public void setUsers() {
		// TODO: File I/O for user list (So it persists past runtime)
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public void setItems() {
		// TODO: File I/O for item list (So it persists past runtime)
	}
	
	public static void validateUser(String password, String username) {
		for(User user : users) {
			if(user.getPassword().equals(password)
			&& user.getUsername().equals(username)
			) {
				Main.user = user;
				scene.setRoot(mainPane);
			}
		}
	}
}
