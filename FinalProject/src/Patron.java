import java.util.ArrayList;

/**
 * Represent a Patron at the library. Patron can check out/in items, place holds,
 * accumulate fines and pay fines. 
 */
public class Patron extends User{
	private int cardNum;
	private String dateJoined;
	private String address;
	private String email;
	private String phone;
	private ArrayList<Fine> fines = new ArrayList<Fine>();
	private ArrayList<Hold> holds = new ArrayList<Hold>();
	private ArrayList<Item> checkouts = new ArrayList<Item>();
	private boolean hasStop = false;
	
	public Patron(String username, String password, String firstName, String lastName,
		int cardNum, String dateJoined, String address, String email) {
		super(username, password, firstName, lastName);
		this.setCardNum(cardNum);
		this.setDateJoined(dateJoined);
		this.setAddress(address);
		this.setEmail(email);
	}
	
	//----------------GETTERS AND SETTERS---------------------

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public String getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(String dateJoined) {
		this.dateJoined = dateJoined;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		// TODO: validate number is 10 digits
		this.phone = phone;
	}

	public ArrayList<Fine> getFines() {
		return fines;
	}

	public void setFines(ArrayList<Fine> fines) {
		this.fines = fines;
	}

	public ArrayList<Hold> getHolds() {
		return holds;
	}

	public void setHolds(ArrayList<Hold> holds) {
		this.holds = holds;
	}

	public ArrayList<Item> getCheckouts() {
		return checkouts;
	}

	public void setCheckouts(ArrayList<Item> checkouts) {
		this.checkouts = checkouts;
	}

	public boolean isHasStop() {
		return hasStop;
	}

	public void setHasStop(boolean hasStop) {
		this.hasStop = hasStop;
	}
	
	public void setHasStop() {
		// TODO: if fines total > certain amount (undecided), set hasStop = true
		// also if today's date > 2 years from dateJoined hasStop= true 
		// (some sort of renewal system requiring proof of address will need to be 
		// implemented)
	}
	
	public boolean getHasStop() {
		return hasStop;
	}
	
	//------------------------------BEHAVIORS---------------------------
	
	// ability to complete following behaviors should depend on hasStop
	
	public void placeHold(Item item) {
		String today = "";
		// TODO: today should be the current date properly formatted
		Hold hold = new Hold(item, today);
		holds.add(hold);
	}
	
	public void removeHold(Item item) {
		// TODO: search through holds and remove item
	}
	
	/**
	 * 
	 * @param item
	 * @return true if successful checkout
	 */
	public boolean checkOut(Item item) {
		if(!hasStop) {
			checkouts.add(item);
			item.setCheckedOut(true);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void checkIn(Item item) {
		// TODO: find item in checkouts and remove
		item.setCirculations(item.getCirculations() + 1);
	}
	
	public void payFine(Fine fine) {
		// TODO: remove fine from fines
	}
	
}
