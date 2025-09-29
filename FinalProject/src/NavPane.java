import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

/**
 * Main navigation bar set up using a FlowPane
 */
public class NavPane extends FlowPane {
	// on button clicks, current page will be changed. MainPane
	// can access this member to set center
	private Pane currentPage;
	
	public NavPane(User user) {
		// Button to navigate to landing page
		Button homeBtn = new Button("Home");
		getChildren().add(homeBtn);
		
		// Button to navigate to page that lists all items (TO BE CREATED)
		Button catalogBtn = new Button("Explore the Catalog");
		getChildren().add(catalogBtn);
		
		// Button to navigate to page that lists all patrons (TO BE CREATED)
		// (should only be visible to Users of type Employee
		if(user instanceof Employee) {
			Button patronsBtn = new Button("View Patrons");
			getChildren().add(patronsBtn);
		}
		
		// Button to navigate to page to check out an item (TO BE CREATED)
		Button checkOutBtn = new Button("Check Out");
		getChildren().add(checkOutBtn);
		
		// Button to navigate to a page to check in an item (TO BE CREATED)
		Button checkInBtn = new Button("Check In");
		getChildren().add(checkInBtn);
		
		// Button to navigate to Reports page (TO BE CREATED).
		// This page allow users to  view various lists (Holds, overdue items, 
		// patrons with expired cards, etc.). This page should only be visible to Employees
		if(user instanceof Employee) {
			Button reportsBtn = new Button("Reports");
			getChildren().add(reportsBtn);
		}
		
		// Button to view your account information (TO BE CREATED)
		// Probably only necessary for Patron users
		Button accountBtn = new Button("Account");
		getChildren().add(accountBtn);
		
		// Button to log out of account and take you to log in page
		Button logOutBtn = new Button("Log Out");
		getChildren().add(logOutBtn);
		
		homeBtn.setOnAction(event -> {
			currentPage = new LandingPane();
		});
		
		logOutBtn.setOnAction(event -> {
			currentPage = new LogInPane();
		});
	}

	public Pane getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Pane currentPage) {
		this.currentPage = currentPage;
	}
}
