/**
 * Represent a Hold on an Item
 */
public class Hold {
	private Item item;
	private String datePlaced;
	private String dateExpires;
	
	public Hold(Item item, String datePlaced) {
		this.setItem(item);
		this.setDatePlaced(datePlaced);
		setDateExpires();
	}
	
	public Hold(Item item, String datePlaced, String dateExpires) {
		this(item, datePlaced);
		this.setDateExpires(dateExpires);
	}
	
	
	//-------------GETTERS AND SETTERS---------------------
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getDatePlaced() {
		return datePlaced;
	}

	public void setDatePlaced(String datePlaced) {
		this.datePlaced = datePlaced;
	}

	public String getDateExpires() {
		return dateExpires;
	}

	public void setDateExpires(String dateExpires) {
		this.dateExpires = dateExpires;
	}
	
	public void setDateExpires() {
		// Overload setDateExpires to automatically set to 2 weeks
	}
	
}
