
/**
 * Represent a record for a Book item.
 */
public class BookRecord extends Record{
	private String authorLastName, authorFirstName;
	private String publicationDate;
	private String edition;
	private long isbn; // ISBN's are 13 digits long so an int would not work
	private String genre;
	
	//--------------------CONSTRUCTORS----------------------------
	public BookRecord(String recordNum, String title, String authorLastName, String authorFirstName, 
			Section section, String publicationDate, String edition, long isbn) {
		super(recordNum, title, section);
		this.authorLastName = authorLastName;
		this.authorFirstName = authorFirstName;
		this.publicationDate = publicationDate;
		this.edition = edition;
		this.isbn = isbn;
		setCallNum();
	}
	
	public BookRecord(String recordNum, String title, String authorLastName, String authorFirstName,
			Section section, int barcode, String publicationDate, String edition, long isbn, 
			String genre) {
		super(recordNum, title, section);
		this.authorLastName = authorLastName;
		this.authorFirstName = authorFirstName;
		this.publicationDate = publicationDate;
		this.edition = edition;
		this.isbn = isbn;
		this.genre = genre;
		setCallNum();
	}
	
	//-------------GETTERS AND SETTERS---------------------

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}
	
	public String getAuthorLastName() {
		return authorLastName;
	}

	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		// TODO: validate date input
		this.publicationDate = publicationDate;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	//-------------OVERRIDE---------------------
	@Override
	protected void setCallNum() {
		if(this.section == Section.NON_FICTION) {
			
		}
		this.callNum = "";
		
	}
}
