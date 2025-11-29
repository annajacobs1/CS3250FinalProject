/**
 * Represent a periodical record (like magazine and newspaper)
 */
public class PeriodicalRecord extends Record{
	private String edition;
	private int volume;
	private String publicationDate;
	
	public PeriodicalRecord(String recordNum, String title, String callNum, Section section,
			String edition, String publicationDate) {
		super(recordNum, title, section, callNum);
		this.setEdition(edition);
		this.setPublicationDate(publicationDate);
	}
	
	public PeriodicalRecord(String recordNum, String title, String callNum, Section section, 
			String edition, String publicationDate, int volume) {
		super(recordNum, title, section, callNum);
		this.edition = edition;
		this.publicationDate = publicationDate;
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
