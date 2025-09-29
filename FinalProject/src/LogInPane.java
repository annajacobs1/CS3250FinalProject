import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Log in page to input user name and password
 */
public class LogInPane extends VBox{
	private String password;
	private String username;
	private Button logInBtn = new Button("Log In");
	
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
			Main.validateUser(password, username);
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

	public Button getLogInBtn() {
		return logInBtn;
	}

	public void setLogInBtn(Button logInBtn) {
		this.logInBtn = logInBtn;
	}

	
	
}
