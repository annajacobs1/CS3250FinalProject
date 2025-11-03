/**
 * Represent a periodical item (like magazine and newspaper)
 */
public class Periodical extends Item{
	private String edition;
	private int volume;
	private String publicationDate;
	
	public Periodical(Record record, int barcode, Location location, Section section, 
			String edition, String publicationDate) {
		super(record, barcode, location);
		this.setEdition(edition);
		this.setPublicationDate(publicationDate);
	}
	
	public Periodical(Record record, int barcode, Location location, Section section, 
			String edition, String publicationDate, int volume) {
		this(record, barcode, location, section, edition, publicationDate);
		this.setVolume(volume);
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	//-------------OVERRIDE---------------------
	@Override
	protected void setCallNum() {
		// TODO Logic to set callNum for new item
		
	}
}
