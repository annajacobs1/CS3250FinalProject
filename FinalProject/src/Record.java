import javafx.scene.image.Image;

/**
 * Represent a record. A record contains information about a certain item.
 * It does not represent a specific item (which physically exists at a location
 * and has a barcode), but is instead a way to group copies of the same item 
 * together.
 */
public class Record {
	protected String recordNum;
	protected String title;
	protected String callNum;
	protected Image image = new Image("images/default_cover.jpg");
	protected boolean circulating = true;
	protected Section section;
	protected RecordType recordType;
	protected Object typeData;
	
	public enum RecordType {
		BOOK,
		TOY,
		AV_ITEM,
		PERIODICAL
	}
	
	
	class BookData {
		
	}
	
	/**
	 * Constructor for Record
	 * 
	 * @param recordNum
	 * @param title
	 * @param callNum
	 * @param circulating
	 * @param section
	 */
	public Record(String recordNum, String title, String callNum,
			Section section, Object typeData) {
		this.recordNum = recordNum;
		this.title = title;
		this.callNum = callNum;
		this.section = section;
		setTypeData(typeData);
	}


	//-------------GETTERS AND SETTERS---------------------
	
	
	public String getRecordNum() {
		return this.recordNum;
	}
	
	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
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
	
	public boolean isCirculating() {
		return circulating;
	}

	public void setCirculating(boolean circulating) {
		this.circulating = circulating;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return image;
	}

	public Section getSection() {
		return section;
	}
	
	public void setSection(Section section) {
		this.section = section;
	}
	
	public void setRecordType(RecordType recordType) {
		
	}
	
	private void setTypeData(Object typeData) {
		if(typeData instanceof BookData) {
			recordType = RecordType.BOOK;
		}
		
	}
	
}
