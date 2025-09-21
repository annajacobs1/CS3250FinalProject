/**
 * Represent an employee at the library
 */
public class Employee extends User{
	private AccessLevel accessLevel = AccessLevel.VIEW;
	
	public Employee(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
	}
	
	public Employee(String username, String password, String firstName, String lastName,
			AccessLevel accessLevel) {
		super(username, password, firstName, lastName);
		this.setAccessLevel(accessLevel);
	}

	public AccessLevel getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(AccessLevel accessLevel) {
		this.accessLevel = accessLevel;
	}
	
}
