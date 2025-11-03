/**
 * Represent a Book Item
 */
public class Book extends Item{
	private String author;
	private String publicationDate;
	private String edition;
	private long isbn; // ISBN's are 13 digits long so an int would not work
	private String genre;
	
	//--------------------CONSTRUCTORS----------------------------
	public Book(Record record, int barcode, Location location, String author, 
			String publicationDate, String edition, long isbn) {
		super(record, barcode, location);
		this.setAuthor(author);
		this.setPublicationDate(publicationDate);
		this.setEdition(edition);
		this.setIsbn(isbn);
	}
	
	public Book(Record record, int barcode, Location location, String author, 
			String publicationDate, String edition, long isbn, String genre) {
		super(record, barcode, location);
		this.setAuthor(author);
		this.setPublicationDate(publicationDate);
		this.setEdition(edition);
		this.setIsbn(isbn);
		this.setGenre(genre);
	}
	
	//-------------GETTERS AND SETTERS---------------------

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
		// TODO Logic to set callNum for new item
		
	}
}
