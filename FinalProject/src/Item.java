import java.awt.Image;

/**
 * Represent any item that belongs to the library.
 */
public class Item {
	protected String recordNum;
	protected int barcode;
	protected String title;
	protected String callNum;
	protected Location location;
	protected Section section;
	protected Image image;	// TODO: add default blank image
	private boolean circulating = true;
	private boolean checkedOut = false;
	private String dueDate;
	
	public Item(String recordNum, int barcode, String title, String callNum, 
			Location location, Section section) {
		this.recordNum = recordNum;
		this.barcode = barcode;
		this.title = title;
		this.callNum = callNum;
		this.location = location;
		this.section = section;
	}
	
	//-------------GETTERS AND SETTERS---------------------
	
	public String getRecordNum() {
		return this.recordNum;
	}
	
	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}
	
	public int getBarcode() {
		return barcode;
	}
	
	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCallNum() {
		return callNum;
	}
	
	public void setCallNum(String callNum) {
		// TODO: callNum should be dependent on section
		this.callNum = callNum;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Section getSection() {
		return section;
	}
	
	public void setSection(Section section) {
		this.section = section;
	}

	public boolean isCirculating() {
		return circulating;
	}

	public void setCirculating(boolean circulating) {
		this.circulating = circulating;
	}

	public boolean isCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		// TODO validate date format
		this.dueDate = dueDate;
	}
}
