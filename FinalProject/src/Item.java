/**
 * Represent any item that belongs to the library. Items cannot just be Items,
 * they must be an instance of Book, AVItem, Periodical, or Toy
 */
public abstract class Item extends Record{
	protected int barcode;
	protected Location location;
	protected Section section;
	private boolean checkedOut = false;
	private String dueDate;
	private int circulations;
	
	/**
	 * Constructor for Item
	 * 
	 * @param recordNum
	 * @param barcode
	 * @param title
	 * @param callNum
	 * @param location
	 * @param section
	 * @param circulating
	 */
	public Item(String recordNum, int barcode, String title, String callNum, 
			Location location, Section section) {
		super(recordNum, title, callNum, section);
		this.recordNum = recordNum;
		this.barcode = barcode;
		this.title = title;
		this.callNum = callNum;
		this.location = location;
		this.section = section;
	}
	
	//-------------GETTERS AND SETTERS---------------------
	
	public int getBarcode() {
		return barcode;
	}
	
	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
		if(checkedOut) {
			setDueDate();
		}
	}

	public String getDueDate() {
		return dueDate;
	}

	// Due date should automatically be set when item is checked out
	private void setDueDate() {
		// TODO validate date format, calculate due date
	}

	public int getCirculations() {
		return circulations;
	}

	public void setCirculations(int circulations) {
		this.circulations = circulations;
	}
}
