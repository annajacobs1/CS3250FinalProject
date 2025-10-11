/**
 * Represent a DVD or CD item
 */
public class AvItem extends Item{
	private int publicationYear;
	private int discCount = 1;
	private int volume;
	
	//--------------CONSTRUCTORS----------------------------
	public AvItem(String recordNum, int barcode, String title, String callNum, 
			Location location, Section section, int publicationYear) {
		super(recordNum, barcode, title, callNum, location, section);
		this.setPublicationYear(publicationYear);
	}
	
	public AvItem(String recordNum, int barcode, String title, String callNum, 
			Location location, Section section, int publicationYear, int discCount,
			int volume) {
		super(recordNum, barcode, title, callNum, location, section);
		this.setDiscCount(discCount);
		this.setVolume(volume);
	}
	
	public AvItem(String recordNum, int barcode, String title, String callNum, 
			Location location, Section section, int publicationYear, int discCount) 
	{
		super(recordNum, barcode, title, callNum, location, section);
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
	
	
}
