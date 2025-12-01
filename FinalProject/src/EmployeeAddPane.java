import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EmployeeAddPane extends GridPane{
	public EmployeeAddPane() {
		setPadding(new Insets(10));
		setHgap(10);
		setVgap(7);
		
		Label titleLbl = new Label("Add an Employee");
		GridPane.setConstraints(titleLbl, 0, 0);
		
		Label firstNameLbl = new Label("First Name: ");
		TextField firstNameTxt = new TextField();
		GridPane.setConstraints(firstNameLbl, 0, 2);
		GridPane.setConstraints(firstNameTxt, 1, 2);
		
		Label lastNameLbl = new Label("Last Name: ");
		TextField lastNameTxt = new TextField();
		GridPane.setConstraints(lastNameLbl, 2, 2);
		GridPane.setConstraints(lastNameTxt, 3, 2);
		
		Label usernameLbl = new Label("Username: ");
		TextField usernameTxt = new TextField();
		GridPane.setConstraints(usernameLbl, 0, 3);
		GridPane.setConstraints(usernameTxt, 1, 3);
		
		Label passwordLbl = new Label("Password: ");
		TextField passwordTxt = new TextField();
		GridPane.setConstraints(passwordLbl, 2, 3);
		GridPane.setConstraints(passwordTxt, 3, 3);
		
		Label accessLbl = new Label("Access Level: ");
		ComboBox<AccessLevel> accessCmb = new ComboBox<>();
		accessCmb.getItems().setAll(AccessLevel.values());
		GridPane.setConstraints(accessLbl, 0, 4);
		GridPane.setConstraints(accessCmb, 1, 4);
		
		Button saveBtn = new Button("Save");
		GridPane.setConstraints(saveBtn, 0, 5);
		
		
		getChildren().addAll(titleLbl, firstNameLbl, firstNameTxt, lastNameLbl, lastNameTxt, saveBtn, 
				usernameLbl, usernameTxt, passwordLbl, passwordTxt, accessCmb, accessLbl);
		
		saveBtn.setOnAction(e -> {
			String username = usernameTxt.getText();
			String password = passwordTxt.getText();
			String firstName = firstNameTxt.getText();
			String lastName = lastNameTxt.getText();
			AccessLevel accessLevel = accessCmb.getValue();
			
			Employee employee = new Employee(username, password, firstName, lastName,
					accessLevel);
			
			Data.addEmployee(employee);
			
			Main.getMainPane().setCenter(new EmployeesPane());
		});
	}
}
