import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

/**
 * Main navigation bar set up using a FlowPane
 */
public class NavPane extends FlowPane {
	// Access to current session user to manage which elements appear
	User user = Main.getUser();
	// NavPane must be aware of parent MainPane to be able to setCenter
	private MainPane mainPane;
	
	public NavPane(MainPane mainPane) {
		Image logo = new Image("images/explore_library.png");
		ImageView logoView = new ImageView(logo);
		logoView.setFitWidth(70);
		logoView.setPreserveRatio(true);
		getChildren().add(logoView);
		
		this.mainPane = mainPane;
		this.setHgap(5);
		
		// Button to navigate to landing page
		Button homeBtn = new Button("Home");
		getChildren().add(homeBtn);
		// Default currentPage is a home page
		mainPane.setCenterPane(new LandingPane());
		
		// Button to navigate to page that lists all items
		Button catalogBtn = new Button("Explore the Catalog");
		getChildren().add(catalogBtn);
		
		// Button to navigate to page that lists all patrons
		// (should only be visible to Users of type Employee
		Button patronsBtn = new Button("View Patrons");
		if(user instanceof Employee) {
			getChildren().add(patronsBtn);
		}
		
		// Button to navigate to page that lists all employees
		// Only visible to Employees with Add permissions
		Button employeesBtn = new Button("View Employees");
		if(user instanceof Employee) {
			if(((Employee) user).getAccessLevel() == AccessLevel.ADD) {
				getChildren().add(employeesBtn);
			}
		}
		
		// Button to navigate to page to check out an item
		Button checkOutBtn = new Button("Check Out");
		getChildren().add(checkOutBtn);
		
		// Button to navigate to a page to check in an item
		Button checkInBtn = new Button("Check In");
		getChildren().add(checkInBtn);
		
		// Button to navigate to Reports page.
		// This page allow users to  view various lists (Holds, overdue items, 
		// patrons with expired cards, etc.). This page should only be visible to Employees
		Button reportsBtn = new Button("Reports");
		if(user instanceof Employee) {
			getChildren().add(reportsBtn);
		}
		
		// Button to view your account information
		// Probably only necessary for Patron users
		Button accountBtn = new Button("Account");
		if(user instanceof Patron) {
			getChildren().add(accountBtn);
		}
		
		
		// Button to log out of account and take you to log in page
		Button logOutBtn = new Button("Log Out");
		getChildren().add(logOutBtn);
		
		
		// BUTTON CLICK EVENTS
		homeBtn.setOnAction(event -> {
			mainPane.setCenterPane(new LandingPane());
		});
		
		logOutBtn.setOnAction(event -> {
			LogInPane.logOut();
		});
		
		patronsBtn.setOnAction(event -> {
			mainPane.setCenterPane(new PatronsPane());
		});
		
		employeesBtn.setOnAction(event -> {
			mainPane.setCenterPane(new EmployeesPane());
		});
		
		catalogBtn.setOnAction(event -> {
			mainPane.setCenterPane(new CatalogPane());
		});
		
		checkInBtn.setOnAction(event -> {
			mainPane.setCenterPane(new CheckInPane());
		});
		
		checkOutBtn.setOnAction(event -> {
			mainPane.setCenterPane(new CheckOutPane(null));
		});
		
		reportsBtn.setOnAction(event -> {
			mainPane.setCenterPane(new ReportsPane());
		});
		
		accountBtn.setOnAction(event -> {
			mainPane.setCenterPane(new AccountPane(Main.getUser()));
		});
	}

	//---------------------------GETTERS AND SETTERS---------------------------
	public MainPane getMainPane() {
		return mainPane;
	}

	public void setMainPane(MainPane mainPane) {
		this.mainPane = mainPane;
	}
}