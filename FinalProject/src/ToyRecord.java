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
	public ToyRecord(String recordNum, String title, Section section, int pieces) {
		super(recordNum, title, section);
		this.pieces = pieces;
		setCallNum();
	}

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	
	//-------------OVERRIDE---------------------
	@Override
	protected void setCallNum() {
		// TODO Logic to set callNum for new item
		
	}
}
