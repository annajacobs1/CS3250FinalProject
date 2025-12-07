import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Page to display all employee accounts using SearchTablePane. Will only be visible
 * to employees with "Add" access level.
 */
public class EmployeesPane extends VBox{
	private SearchTablePane<Employee> employeesPane;
	
	public EmployeesPane() {
		Label employeeSearchLbl = new Label("Search Employees");
		employeeSearchLbl.getStyleClass().add("title");
		
		ArrayList<String> options = new ArrayList<String>();
		options.add("First Name");
		options.add("Last Name");
		options.add("Username");
		
		employeesPane = new SearchTablePane<Employee>(Data.getEmployees(),
				options, new SearchEmployees());
		
		ArrayList<String[]> columns = new ArrayList<String[]>();
		String[] firstNameCol = {"First Name", "firstName"};
		String[] lastNameCol = {"Last Name", "lastName"};
		String[] usernameCol = {"Username", "username"};
		String[] accessCol = {"Access Level", "accessLevel"};
		
		columns.add(firstNameCol);
		columns.add(lastNameCol);
		columns.add(usernameCol);
		columns.add(accessCol);
		
		employeesPane.setCols(columns);
		
		Button addBtn = new Button("Add Employee");
		addBtn.setOnAction(e -> {
			Main.getMainPane().setCenter(new EmployeeAddPane());
		});
		getChildren().addAll(employeeSearchLbl, employeesPane, addBtn);
	}
	
	public class SearchEmployees implements SearchHandler{
		public void search(String col, String val) {
			
			ArrayList<Employee> resultArray = new ArrayList<>();
			
			if(!val.equals("")) {
				switch(col) {
				case "First Name":
					resultArray = Data.searchEmployeesByFirstName(val);
					break;
				case "Last Name":
					resultArray = Data.searchEmployeesByLastName(val);
					break;
				case "Username":
					resultArray = Data.searchEmployeesByUsername(val);
					break;
				}
			} else {
				employeesPane.setItems(Data.getEmployees());
			}
			
			employeesPane.setItems(resultArray);
		}
	}
}
