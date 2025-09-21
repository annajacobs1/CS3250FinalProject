/**
 * Represent a periodical item (like magazine and newspaper)
 */
public class Periodical extends Item{
	private String edition;
	private int volume;
	private String publicationDate;
	
	public Periodical(String recordNum, int barcode, String title, String callNum, 
			Location location, Section section, String edition, String publicationDate) {
		super(recordNum, barcode, title, callNum, location, section);
		this.setEdition(edition);
		this.setPublicationDate(publicationDate);
	}
	
	public Periodical(String recordNum, int barcode, String title, String callNum, 
			Location location, Section section, String edition, String publicationDate,
			int volume) {
		this(recordNum, barcode, title, callNum, location, section, edition, publicationDate);
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
}
