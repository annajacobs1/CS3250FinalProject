/**
 * Represent a DVD or CD item
 */
public class AvItem extends Item{
	private int publicationYear;
	private int discCount = 1;
	private int volume;
	
	//--------------CONSTRUCTORS----------------------------
	public AvItem(Record record, int barcode, Location location, Section section,
			int publicationYear) {
		super(record, barcode, location);
		this.setPublicationYear(publicationYear);
	}
	
	public AvItem(Record record, int barcode, Location location, Section section,
			int publicationYear, int discCount, int volume) {
		super(record, barcode, location);
		this.setDiscCount(discCount);
		this.setVolume(volume);
	}
	
	public AvItem(Record record, int barcode, Location location, Section section,
			int publicationYear, int discCount) 
	{
		super(record, barcode, location);
		this.setDiscCount(discCount);
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

	//-------------OVERRIDE---------------------
	@Override
	protected void setCallNum() {
		// TODO Logic to set callNum for new item
		
	}
	
	
}
