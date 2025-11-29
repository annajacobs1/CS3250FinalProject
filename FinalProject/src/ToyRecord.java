/**
 * Represent a toy record
 */
public class ToyRecord extends Record{
	private int pieces;
	
	/**
	 * Constructor for ToyRecord
	 * 
	 * @param recordNum
	 * @param barcode
	 * @param title
	 * @param callNum
	 * @param location
	 * @param section
	 * @param pieces
	 */
	public ToyRecord(String recordNum, String title, String callNum, Section section, int pieces) {
		super(recordNum, title, section, callNum);
		this.pieces = pieces;
	}

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
}
