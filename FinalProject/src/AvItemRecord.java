/**
 * Represent a DVD or CD record.
 */
public class AvItemRecord extends Record{
	private int publicationYear;
	private int discCount = 1;
	private AVType type;
	private int volume;
	
	public enum AVType {
		CD,
		DVD
	}
	
	//--------------CONSTRUCTORS----------------------------
	public AvItemRecord(String recordNum, String title, Section section, int publicationYear, 
			AVType type) {
		super(recordNum, title, section);
		this.publicationYear = publicationYear;
		this.setType(type);
		setCallNum();
	}
	
	public AvItemRecord(String recordNum, String title, Section section, int publicationYear, 
			int discCount, int volume, AVType type) {
		super(recordNum, title, section);
		this.discCount = discCount;
		this.volume = volume;
		this.setType(type);
		setCallNum();
		
	}
	
	public AvItemRecord(String recordNum, String title, Section section, int publicationYear, 
			int discCount) 
	{
		super(recordNum, title, section);
		this.discCount = discCount;
	}
	
	//-------------GETTERS AND SETTERS---------------------

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public int getDiscCount() {
		return discCount;
	}

	public void setDiscCount(int discCount) {
		this.discCount = discCount;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public AVType getType() {
		return type;
	}

	public void setType(AVType type) {
		this.type = type;
	}
	

	//-------------OVERRIDE---------------------
	@Override
	protected void setCallNum() {
		// TODO Logic to set callNum for new item
		
	}


	
}
