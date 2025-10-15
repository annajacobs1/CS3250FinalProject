import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Log in page to input user name and password.
 */
public class LogInPane extends VBox{
	private String password;
	private String username;
	
	public LogInPane() {
		super(10);
		
		setPrefWidth(100);
		setPrefHeight(150);
		
		Label titleLbl = new Label("Log In");
		Label usernameLbl = new Label("Username");
		// Text field for user to enter their user name
		TextField usernameTxt = new TextField();
		
		Label passwordLbl = new Label("Password");
		// Text field for user to enter their password
		PasswordField passwordTxt = new PasswordField();
		
		// Button to click once credentials are inputted.
		// Sets username and password members to the input
		Button logInBtn = new Button("Log In");
		
		getChildren().addAll(titleLbl, usernameLbl, usernameTxt, passwordLbl, passwordTxt, logInBtn);
		
		logInBtn.setOnAction(event -> {
			password = passwordTxt.getText();
			username = usernameTxt.getText();
			validateUser(password, username);
		});
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	//-------------------------LOG IN CONTROLS-----------------------------
	
	/**
	 * Given a user name and password, find correct user and set user for the session.
	 * Log in to the application by setting the scene root to mainPane.
	 */
	public static void validateUser(String password, String username) {
		User foundUser = null;
		for(User user : Data.getPatrons()) {
			if(user.getPassword().equals(password)
			&& user.getUsername().equals(username)
			) {
				foundUser = user;
			}
		}
		for(User user : Data.getEmployees()) {
			if(user.getPassword().equals(password)
			&& user.getUsername().equals(username)
			) {
				foundUser = user;
			}
		}
		if(foundUser != null) {
			Main.setUser(foundUser);
			// create new MainPane with every login
			MainPane mainPane = new MainPane();
			Main.setMainPane(mainPane);
			Main.getScene().setRoot(mainPane);
		}
		
	}
	
	/**
	 * Set root to log in page, set user to null, initialize new MainPane
	 */
	public static void logOut() {
		Main.getScene().setRoot(new LogInPane());
		Main.setUser(null);
		
		Main.setMainPane(new MainPane());
	}
}
