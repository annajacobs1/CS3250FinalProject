import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Control the view and flow of the application. Contains universal array lists
 * to keep track of users, items, and patrons
 */
public class Main extends Application{
	// Members are static because there will only ever be one Main
	private static User user = null;
	private static ArrayList<User> users = new ArrayList<User>();
	private static ArrayList<Item> items = new ArrayList<Item>();
	private static ArrayList<Patron> patrons = new ArrayList<Patron>();
	private static Scene scene;
	private static MainPane mainPane;
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// add users for testing
		users.add(new Employee("ajacobs", "12345", "Anna", "Jacobs"));
		users.add(new Patron("bob", "123", "Bob", "Jones", 987654321, "2023/02/01",
				"123 E 456 S Cool Rd, Ogden, UT, 84401", "bob@example.com"));
		
		// add items for testing
		items.add(new Book("b123456", 123456789, "Title", "F Jones", Location.MAIN, Section.ADULT_FICTION, "John Jones",
				"2020/01/01", "First", 123456789123L, "Action"));
		items.add(new Book("b654321", 987654321, "Another Title", "781.66 F12345 2000", Location.NORTH, Section.NON_FICTION,
				"Jane Farr", "2000/06/07", "Second", 987654321098L));
		
		setItems();
		setUsers();
		setPatrons();
		
		LogInPane logInPane = new LogInPane();
		scene = new Scene(logInPane, 750, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

//---------------------GETTERS AND SETTERS------------------------------------
	
	public static ArrayList<User> getUsers(){
		return users;
	}
	
	public void setUsers() {
		// TODO: File I/O for user list (So it persists past runtime)
	}
	
	public static User getUser() {
		return user;
	}
	
	public void setItems() {
		// TODO: File I/O for item list (So it persists past runtime)
	}
	
	public static ArrayList<Item> getItems(){
		return items;
	}

	public static ArrayList<Patron> getPatrons() {
		return patrons;
	}

	public static void setPatrons() {
		for(User user : users) {
			if(user instanceof Patron) {
				patrons.add((Patron)user);
			}
		}
	}
	
//------------------------------LOGIC METHODS------------------------------------
	
	/**
	 * Given a user name and password, find correct user and set user for the session.
	 * Log in to the application by setting the scene root to mainPane.
	 */
	public static void validateUser(String password, String username) {
		for(User user : users) {
			if(user.getPassword().equals(password)
			&& user.getUsername().equals(username)
			) {
				Main.user = user;
				// create new MainPane with every login
				mainPane = new MainPane();
				scene.setRoot(mainPane);
			}
		}
	}
	
	/**
	 * Set root to log in page, set user to null, initialize new MainPane
	 */
	public static void logOut() {
		scene.setRoot(new LogInPane());
		user = null;
		
		mainPane = new MainPane();
	}
	
	/**
	 * Given a barcode, iterates over items array and returns item with matching
	 * barcode or null if not found
	 */
	public static Item searchByBarcode(int barcode) {
		Item itemFound = null;
		for(Item item : items) {
			if(item.getBarcode() == barcode) {
				itemFound = item;
			}
		}
		return itemFound;
	}
	
	/**
	 * Given a card number, iterates over patrons array and returns patron with matching
	 * card number or null if not found
	 */
	public static Patron searchByCardNum(int cardNum) {
		Patron patronFound = null;
		for(Patron patron : patrons) {
			if(patron.getCardNum() == cardNum) {
				patronFound = patron;
			}
		}
		return patronFound;
	}
}
