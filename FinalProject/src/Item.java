/**
 * Represent any item that belongs to the library. This is a unique, physical copy of an 
 * item. Item type is dependent on type of record.
 */
public class Item {
	protected int barcode;
	protected Location location;
	// preferably this would hold a record, but for file i/o to work
	// Item has to be a POJO
	protected String recordNum;
	protected boolean checkedOut = false;
	protected String dueDate = "";
	protected int circulations = 0;
	
	
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
	public Item(String recordNum, int barcode, Location location) {
		this.recordNum = recordNum;
		this.barcode = barcode;
		this.location = location;
	}
	
	public Item() {};
	
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
	
	public String getRecordNum() {
		return recordNum;
	}
	
	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}
	
	@Override
	public String toString() {
		return "Record Number: " + recordNum + "\nBarcode: " + barcode + "\nLocation: " + location;
	}
}
