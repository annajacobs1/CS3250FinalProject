/**
 * Represent any item that belongs to the library. This is a unique, physical copy of an 
 * item. Item type is dependent on type of record.
 */
public class Item {
	protected long barcode;
	protected Location location;
	protected Record record;
	protected String dueDate = "";
	protected int circulations = 0;
	protected Status status = Status.AVAILABLE;
	
	
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
	public Item(Record record, long barcode, Location location) {
		this.record = record;
		this.barcode = barcode;
		this.location = location;
	}
	
	//-------------GETTERS AND SETTERS---------------------
	
	public long getBarcode() {
		return barcode;
	}
	
	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
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
	
	public Record getRecord() {
		return record;
	}
	
	public void setRecordNum(Record record) {
		this.record = record;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Record Number: " + record.getRecordNum() + "\nBarcode: " + barcode + "\nLocation: " + location;
	}
}
