import java.util.ArrayList;

/**
 * Hold ArrayLists for items, patrons, employees, and records. Handle
 * file I/O to get data, add data, remove data. Handle search for data
 * 
 */
public class Data {
	private static ArrayList<Item> items = new ArrayList<Item>();
	private static ArrayList<Patron> patrons = new ArrayList<Patron>();
	private static ArrayList<Employee> employees = new ArrayList<Employee>();
	private static ArrayList<Record> records = new ArrayList<Record>();
	
	public Data() {
		setItems();
		setPatrons();
		setEmployees();
		setRecords();
	}
	
	//----------------GETTERS AND SETTERS-----------------------
	
	private static void setItems() {
		// TODO: File I/O for item list (So it persists past runtime)
	}
	
	public static ArrayList<Item> getItems(){
		return items;
	}

	public static ArrayList<Patron> getPatrons() {
		return patrons;
	}

	private static void setPatrons() {
		// TODO: File I/O for patron list (So it persists past runtime)
	}
	
	public static ArrayList<Employee> getEmployees() {
		return employees;
	}
	
	private static void setEmployees() {
		// TODO: File I/O for employee list (So it persists past runtime)
	}
	
	public static ArrayList<Record> getRecords() {
		return records;
	}

	// Automatically set records by iterating through items
	private static void setRecords() {
		boolean recordExists;
		for(Item item : items) {
			recordExists = false;
			for(Record record : records) {
				if(item.getRecordNum().equals(record.getRecordNum())) {
					recordExists = true;
					break;
				}
			}
			if(!recordExists) {
				records.add(item);
			}
		}
	}
	
	//----------------------ADD/DELETE DATA-----------------------------
	
	public static void addItem(Item item) {
		items.add(item);
		//TODO: add item to file
	}
	
	public static void removeItem(Item item) {
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).equals(item)) {
				items.remove(i--);
			}
		}
		//TODO: remove item from file
	}
	
	public static void addPatron(Patron patron) {
		patrons.add(patron);
		//TODO: add patron to file
	}
	
	public static void removePatron(Patron patron) {
		for(int i = 0; i < patrons.size(); i++) {
			if(patrons.get(i).equals(patron)) {
				patrons.remove(i--);
			}
		}
		//TODO: remove patron from file
	}
	
	public static void addEmployee(Employee employee) {
		employees.add(employee);
		//TODO: add employee to file
	}
	
	public static void removeEmployee(Employee employee) {
		for(int i = 0; i < employees.size(); i++) {
			if(employees.get(i).equals(employee)) {
				employees.remove(i--);
			}
		}
		//TODO: remove employee from file
	}
	
	public static void addRecord(Record record) {
		records.add(record);
		//TODO: add record to file
	}
	
	public static void removeRecord(Record record) {
		for(int i = 0; i < records.size(); i++) {
			if(records.get(i).equals(record)) {
				records.remove(i--);
			}
		}
		//TODO: remove record from file
	}
	
	//----------------------SEARCH FOR DATA-----------------------------
	
	/**
	 * Given a barcode, iterates over items array and returns item with matching
	 * barcode or null if not found
	 */
	public static Item searchByBarcode(int barcode) {
		Item itemFound = null;
		for(Item item : items) {
			if(item.getBarcode() == barcode) {
				itemFound = item;
			}
		}
		return itemFound;
	}
	
	/**
	 * Given a card number, iterates over patrons array and returns patron with matching
	 * card number or null if not found
	 */
	public static Patron searchByCardNum(int cardNum) {
		Patron patronFound = null;
		for(Patron patron : patrons) {
			if(patron.getCardNum() == cardNum) {
				patronFound = patron;
			}
		}
		return patronFound;
	}
}
