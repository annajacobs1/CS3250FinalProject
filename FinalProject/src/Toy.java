/**
 * Represent a toy item
 */
public class Toy extends Item{
	private int pieces;
	
	/**
	 * Constructor for Toy
	 * 
	 * @param recordNum
	 * @param barcode
	 * @param title
	 * @param callNum
	 * @param location
	 * @param section
	 * @param pieces
	 */
	public Toy(String recordNum, int barcode, String title, String callNum, 
			Location location, Section section, int pieces) {
		super(recordNum, barcode, title, callNum, location, section);
		this.setPieces(pieces);
	}

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
}
