/**
 * Represent a fine a Patron may have on an overdue/damaged item
 */
public class Fine {
	private double amount = 0.25; // default to $0.25
	private String dateBegan;
	private Item item;
	
	public Fine(String dateBegan, Item item) {
		this.setDateBegan(dateBegan);
		this.setItem(item);
	}
	
	public Fine(String dateBegan, Item item, double amount) {
		this(dateBegan, item);
		this.amount = amount;
	}
	
	//-------------GETTERS AND SETTERS---------------------

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDateBegan() {
		return dateBegan;
	}

	public void setDateBegan(String dateBegan) {
		this.dateBegan = dateBegan;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	//------------------BEHAVIORS---------------------------
	
	public void incrementAmount() {
		//TODO: figure out how to increment fine per day.
		// increment amount should be based on item type
		// ex. AV items should have higher rate per day
		// Also possibly cap at $4.00 for books (like Weber
		// County does)
	}
}
