import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Page to display all employee accounts using SearchTablePane. Will only be visible
 * to employees with "Add" access level.
 */
public class EmployeesPane extends VBox{
	public EmployeesPane() {
		Label employeeSearchLbl = new Label("Search Employees");
		
		ArrayList<String> options = new ArrayList<String>();
		options.add("First Name");
		options.add("Last Name");
		options.add("Username");
		
		SearchTablePane<Employee> employeesPane = new SearchTablePane<Employee>(Data.getEmployees(),
				options);
		
		// TODO: this button takes you to a form to input new employee information
		Button addBtn = new Button("Add Employee");
		getChildren().addAll(employeeSearchLbl, employeesPane, addBtn);
	}
}
