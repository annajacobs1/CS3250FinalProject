import java.util.ArrayList;

import javafx.scene.image.Image;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	private static ArrayList<Hold> holds = new ArrayList<Hold>();

	
	//----------------GETTERS AND SETTERS-----------------------
	
	/**
	 * Query database to get all items rows, convert each to Item object, and
	 * add to items ArrayList
	 */
	private static void setItems() {
		try {
			ResultSet itemsRs = SQLConnection.sqlQuery("SELECT * FROM item");
			while(itemsRs.next()) {
				long barcode = itemsRs.getLong("barcode");
				String locationStr = itemsRs.getString("location");
				Location location = Location.valueOf(locationStr);
				String recordNumber = itemsRs.getString("record_number");
				Record record = searchByRecordNum(recordNumber);
				int circulations = itemsRs.getInt("circulations");
				String statusStr = itemsRs.getString("status");
				Status status = Status.valueOf(statusStr);
				
				Item item = new Item(record, barcode, location);
				item.setStatus(status);
				item.setCirculations(circulations);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Item> getItems(){
		return items;
	}

	/**
	 * Query database to get patrons. Convert each result into Patron object. Add each
	 * to patrons list
	 */
	private static void setPatrons() {
		try {
			ResultSet patronsRs = SQLConnection.sqlQuery("SELECT * FROM patron");
			while(patronsRs.next()) {
				String username = patronsRs.getString("username");
				String password = patronsRs.getString("password");
				String firstName = patronsRs.getString("first_name");
				String lastName = patronsRs.getString("last_name");
				int cardNum = patronsRs.getInt("card_number");
				String dateJoined = patronsRs.getDate("date_joined").toString();
				String address = patronsRs.getString("address");
				String email = patronsRs.getString("email");
				String phone = patronsRs.getString("phone");
				boolean hasStop = patronsRs.getBoolean("has_stop");
				String homeLocationStr = patronsRs.getString("home_location");
				Location homeLocation = Location.valueOf(homeLocationStr);
				
				Patron patron = new Patron(username, password, firstName, lastName, cardNum, 
						dateJoined, address, email);
				patron.setHasStop(hasStop);
				patron.setPhone(phone);
				patron.setHomeLocation(homeLocation);
				
				patrons.add(patron);
			}
			
			for(Patron patron : patrons) {
				// get the fines for the current patron
				ResultSet finesRs = SQLConnection.sqlQuery("SELECT * FROM "
						+ "fine WHERE patron = '" + patron.getUsername() + "'");
				ArrayList<Fine> fines = new ArrayList<Fine>();
				while(finesRs.next()) {
					double amount = finesRs.getDouble("amount");
					String dateBegan = finesRs.getDate("date_began").toString();
					long barcode = finesRs.getLong("item");
					Item item = searchByBarcode(barcode);
					
					Fine fine = new Fine(dateBegan, item, amount);
					fines.add(fine);
				}
				
				// get the holds for the current patron
				ResultSet holdsRs = SQLConnection.sqlQuery("SELECT * FROM "
						+ "hold WHERE patron = '" + patron.getUsername() + "'");
				ArrayList<Hold> patronHolds = new ArrayList<Hold>();
				while(holdsRs.next()) {
					long barcode = holdsRs.getLong("item");
					Item item = searchByBarcode(barcode);
					String datePlaced = holdsRs.getDate("date_placed").toString();
					String dateExpires = holdsRs.getDate("date_expires").toString();
					
					Hold hold = new Hold(item, datePlaced, dateExpires);
					patronHolds.add(hold);
				}
				patron.setFines(fines);
				patron.setHolds(patronHolds);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Patron> getPatrons() {
		return patrons;
	}
	
	public static ArrayList<Employee> getEmployees() {
		return employees;
	}
	
	/**
	 * Query the database to get employee rows. Convert each to Employee object and 
	 * add to employee list
	 */
	private static void setEmployees() {
		try {
			ResultSet employeesRs = SQLConnection.sqlQuery("SELECT * FROM employee");
			while(employeesRs.next()) {
				String username = employeesRs.getString("username");
				String password = employeesRs.getString("password");
				String firstName = employeesRs.getString("first_name");
				String lastName = employeesRs.getString("last_name");
				String accessLevelStr = employeesRs.getString("access_level");
				AccessLevel accessLevel = AccessLevel.valueOf(accessLevelStr);
				
				Employee employee = new Employee(username, password, firstName,
						lastName, accessLevel);
				
				employees.add(employee);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Record> getRecords() {
		return records;
	}

	/**
	 * query the database to get records, convert into objects, add to records ArrayList
	 */
	private static void setRecords() {
		try {
			
			// Get the book records and convert to BookRecord type, add to records list
			ResultSet bookRecordsRs = SQLConnection.sqlQuery("SELECT * FROM book_record LEFT JOIN "
					+ "record ON book_record.record_number = record.record_number");
			while(bookRecordsRs.next()) {
				String recordNumber = bookRecordsRs.getString("record_number");
				String title = bookRecordsRs.getString("title");
				String callNumber = bookRecordsRs.getString("call_number");
				String imagePath = bookRecordsRs.getString("image_path");
				Image image;
				try {
					image = new Image(imagePath);
				} catch(Exception e) {
					image = new Image("images/default_cover.jpg");
				}
				boolean circulating = bookRecordsRs.getBoolean("circulating");
				String sectionStr = bookRecordsRs.getString("section");
				Section section = Section.valueOf(sectionStr);
				
				String authorFirstName = bookRecordsRs.getString("author_first_name");
				String authorLastName = bookRecordsRs.getString("author_last_name");
				String publicationDate = bookRecordsRs.getDate("publication_date").toString();
				String edition = bookRecordsRs.getString("edition");
				long isbn = bookRecordsRs.getLong("isbn");
				String genre = bookRecordsRs.getString("genre");
				
				BookRecord bookRecord = new BookRecord(recordNumber, title, callNumber, authorLastName, 
						authorFirstName, section, publicationDate, edition, isbn, genre);
				bookRecord.setCirculating(circulating);
				bookRecord.setImage(image);
				records.add(bookRecord);
			}
			
			// Get AV records and convert to AvItemRecord type, add to records list
			ResultSet avRecordsRs = SQLConnection.sqlQuery("SELECT * FROM av_record LEFT JOIN "
					+ "record on record.record_number = av_record.record_number");
			while(avRecordsRs.next()) {
				String recordNumber = avRecordsRs.getString("record_number");
				String title = avRecordsRs.getString("title");
				String callNumber = avRecordsRs.getString("call_number");
				String imagePath = avRecordsRs.getString("image_path");
				Image image;
				try {
					image = new Image(imagePath);
				} catch(Exception e) {
					image = new Image("images/default_cover.jpg");
				}
				boolean circulating = avRecordsRs.getBoolean("circulating");
				String sectionStr = avRecordsRs.getString("section");
				Section section = Section.valueOf(sectionStr);
				
				int publicationYear = avRecordsRs.getInt("publication_year");
				int discCount = avRecordsRs.getInt("disc_count");
				String avTypeStr = avRecordsRs.getString("av_type");
				AvItemRecord.AVType avType = AvItemRecord.AVType.valueOf(avTypeStr);
				int volume = avRecordsRs.getInt("volume");
				
				AvItemRecord avRecord = new AvItemRecord(recordNumber, title, callNumber, section,
						publicationYear, discCount, volume, avType);
				avRecord.circulating = circulating;
				avRecord.setImage(image);
				records.add(avRecord);
			}
			
			// Get periodical records and convert to PeriodicalRecord type, add to records list
			ResultSet perRecordsRs = SQLConnection.sqlQuery("SELECT * FROM periodical_record LEFT JOIN "
					+ "record on record.record_number = periodical_record.record_number");
			while(perRecordsRs.next()) {
				String recordNumber = perRecordsRs.getString("record_number");
				String title = perRecordsRs.getString("title");
				String callNumber = perRecordsRs.getString("call_number");
				String imagePath = perRecordsRs.getString("image_path");
				Image image;
				try {
					image = new Image(imagePath);
				} catch(Exception e) {
					image = new Image("images/default_cover.jpg");
				}
				boolean circulating = perRecordsRs.getBoolean("circulating");
				String sectionStr = perRecordsRs.getString("section");
				Section section = Section.valueOf(sectionStr);
				
				String edition = perRecordsRs.getString("edition");
				int volume = perRecordsRs.getInt("volume");
				String publicationDate = perRecordsRs.getDate("publication_date").toString();

				
				PeriodicalRecord perRecord = new PeriodicalRecord(recordNumber, title, callNumber, section,
						edition, publicationDate, volume);
				perRecord.circulating = circulating;
				perRecord.setImage(image);
				records.add(perRecord);
			}
			
			// Get toy records and convert to ToyRecord type, add to records list
			ResultSet toyRecordsRs = SQLConnection.sqlQuery("SELECT * FROM toy_record LEFT JOIN "
					+ "record on record.record_number = toy_record.record_number");
			while(toyRecordsRs.next()) {
				String recordNumber = toyRecordsRs.getString("record_number");
				String title = toyRecordsRs.getString("title");
				String callNumber = toyRecordsRs.getString("call_number");
				String imagePath = toyRecordsRs.getString("image_path");
				Image image;
				try {
					image = new Image(imagePath);
				} catch(Exception e) {
					image = new Image("images/default_cover.jpg");
				}
				boolean circulating = toyRecordsRs.getBoolean("circulating");
				String sectionStr = toyRecordsRs.getString("section");
				Section section = Section.valueOf(sectionStr);
				
				int pieces = toyRecordsRs.getInt("pieces");

				ToyRecord toyRecord = new ToyRecord(recordNumber, title, callNumber, section,
						pieces);
				toyRecord.circulating = circulating;
				toyRecord.setImage(image);
				records.add(toyRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Hold> getHolds() {
		return holds;
	}

	/**
	 * Query the database to get the holds rows. Convert each to Hold object
	 * and add to holds list.
	 */
	public static void setHolds() {
		try {
			ResultSet holdsRs = SQLConnection.sqlQuery("SELECT * FROM hold");
			while(holdsRs.next()) {
				long barcode = holdsRs.getLong("item");
				Item item = searchByBarcode(barcode);
				String datePlaced = holdsRs.getDate("date_placed").toString();
				String dateExpires = holdsRs.getDate("date_expires").toString();
				
				Hold hold = new Hold(item, datePlaced, dateExpires);
				holds.add(hold);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//----------------------ADD/DELETE DATA-----------------------------
	
	/**
	 * Establish database connection and call all methods to load data from
	 * database into array lists
	 */
	public static void loadData() {
		SQLConnection.loadConnection();
		setRecords();
		setItems();
		setPatrons();
		setEmployees();
		
	}
	
	/**
	 * add an item to the items list and insert the item into the database
	 * @param item the item to add to the list and database
	 */
	public static void addItem(Item item) {
		items.add(item);

		SQLConnection.sqlUpdate("INSERT INTO item VALUES(" + item.getBarcode() + ",'" + 
				item.getLocation() + "','" + item.getRecord().getRecordNum()
				+ "'," + item.getCirculations() + ",'" + item.getStatus() + "')");
	}
	
	/**
	 * remove item from the items list and delete the item from the database
	 * @param item the item to delete from the list and database
	 */
	public static void removeItem(Item item) {
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).equals(item)) {
				items.remove(i--);
				break;
			}
		}
		
		SQLConnection.sqlUpdate("DELETE FROM item WHERE barcode = " + item.getBarcode());
	}
	
	/**
	 * add patron to patrons list and insert into database
	 * @param patron the patron to add to the list and database
	 */
	public static void addPatron(Patron patron) {
		patrons.add(patron);
		
		String hasStop;
		if(patron.getHasStop()) {
			hasStop = "TRUE";
		} else {
			hasStop = "FALSE";
		}
		
		SQLConnection.sqlUpdate("INSERT INTO patron VALUES('" + patron.getUsername() + "','" + 
				patron.getPassword() + "','" + patron.getFirstName() + "','" + patron.getLastName()
				+ "'," + patron.getCardNum() + ",'" + patron.getDateJoined() + "','" + 
				patron.getAddress() + "','" + patron.getEmail() + "','" + patron.getPhone() + "'," +
				hasStop + ",'" + patron.getHomeLocation() + "')");
	}
	
	/**
	 * delete a patron from the patrons list and from the database
	 * @param patron the patron to be removed from the list and database
	 */
	public static void removePatron(Patron patron) {
		for(int i = 0; i < patrons.size(); i++) {
			if(patrons.get(i).equals(patron)) {
				patrons.remove(i--);
				break;
			}
		}
		
		SQLConnection.sqlUpdate("DELETE FROM patron WHERE username = '" + patron.getUsername() + "'");
	}
	
	/**
	 * Add an employee to the employees list and employee database table
	 * @param employee the employee to be added to list and database
	 */
	public static void addEmployee(Employee employee) {
		employees.add(employee);
		
		SQLConnection.sqlUpdate("INSERT INTO employee VALUES('" + employee.getUsername() +
				"','" + employee.getPassword() + "','" + employee.getFirstName() + "','" +
				employee.getLastName() + "','" + employee.getAccessLevel() + "')");
	}
	
	/**
	 * Remove an employee from the employees list and employee database table
	 * @param employee the employee to be deleted
	 */
	public static void removeEmployee(Employee employee) {
		for(int i = 0; i < employees.size(); i++) {
			if(employees.get(i).equals(employee)) {
				employees.remove(i--);
				break;
			}
		}

		SQLConnection.sqlQuery("DELETE FROM employee WHERE username = '" + 
				employee.getUsername() + "'");
	}
	
	/**
	 * Add a record to the records list and record database table
	 * @param record the record to be added
	 */
	public static void addRecord(Record record) {
		records.add(record);
		
		String imagePath = record.getImage().getUrl();
		
		String circulating;
		if(record.isCirculating()) {
			circulating = "TRUE";
		} else {
			circulating = "FALSE";
		}
		
		SQLConnection.sqlUpdate("INSERT INTO record VALUES('" + record.getRecordNum() +
				"','" + record.getTitle() + "','" + record.getCallNum() + "','" + 
				imagePath + "'," + circulating + ",'" + record.getSection() + "')");
		
		if(record instanceof BookRecord) {
			addBookRecord((BookRecord)record);
		}
		else if(record instanceof AvItemRecord) {
			addAvRecord((AvItemRecord)record);
		}
		else if(record instanceof PeriodicalRecord) {
			addPeriodicalRecord((PeriodicalRecord)record);
		} 
		else {
			addToyRecord((ToyRecord)record);
		}
	}
	
	/**
	 * insert a BookRecord into book_record table
	 * @param record the record to be added
	 */
	private static void addBookRecord(BookRecord record) {
		SQLConnection.sqlUpdate("INSERT INTO book_record VALUES('" + record.getRecordNum() + 
				"','" + record.getAuthorFirstName() + "','" + record.getAuthorLastName() +
				"','" + record.getPublicationDate() + "','" + record.getEdition() + "'," +
				record.getIsbn() + ",'" + record.getGenre() + "')");
	}
	
	/**
	 * insert an AvItemRecord into av_record table
	 * @param record the record to be added
	 */
	private static void addAvRecord(AvItemRecord record) {
		SQLConnection.sqlUpdate("INSERT INTO av_record VALUES('" + record.getRecordNum() +
				"'," + record.getPublicationYear() + "," + record.getDiscCount() + ",'" + 
				record.getType() + "'," + record.getVolume() + ")");
	}
	
	/**
	 * insert a PeriodicalRecord into periodical_record table
	 * @param record the record to be added
	 */
	private static void addPeriodicalRecord(PeriodicalRecord record) {
		SQLConnection.sqlUpdate("INSERT INTO periodical_record VALUES('" + record.getRecordNum() +
				"','" + record.getEdition() + "'," + record.getVolume() + ",'" + 
				record.getPublicationDate() + "')");
	}
	
	/**
	 * insert a ToyRecord into toy_record table 
	 * @param record the record to be added
	 */
	private static void addToyRecord(ToyRecord record) {
		SQLConnection.sqlUpdate("INSERT INTO toy_record VALUES('" + record.getRecordNum() + 
				"'," + record.getPieces() + ")");
		
	}
	
	/**
	 * Remove a record from the records list and delete from record table
	 * as well as corresponding record type table. Removes any items from 
	 * the record
	 * @param record the record to be deleted
	 */
	public static void removeRecord(Record record) {
		// remove record from records ArrayList
		for(int i = 0; i < records.size(); i++) {
			if(records.get(i).equals(record)) {
				records.remove(i--);
				break;
			}
		}
		
		// remove any items from the record from the items ArrayList
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).getRecord().equals(record)) {
				items.remove(i--);
			}
		}
		
		// Delete items of the record from item table in database
		SQLConnection.sqlUpdate("DELETE FROM item WHERE record_number = '" + 
				record.getRecordNum() + "'");
		
		// Delete from type record table depending on record type
		if(record instanceof BookRecord) {
			SQLConnection.sqlUpdate("DELETE FROM book_record WHERE record_number = '"
					+ record.getRecordNum() + "'");
		} else if(record instanceof AvItemRecord) {
			SQLConnection.sqlUpdate("DELETE FROM av_record WHERE record_number = '"
					+ record.getRecordNum() + "'");
		} else if(record instanceof PeriodicalRecord) {
			SQLConnection.sqlUpdate("DELETE FROM periodical_record WHERE record_number = '"
					+ record.getRecordNum() + "'");
		} else {
			SQLConnection.sqlUpdate("DELETE FROM toy_record WHERE record_number = '"
					+ record.getRecordNum() + "'");
		}
		
		// then finally delete from record table
		SQLConnection.sqlUpdate("DELETE FROM record WHERE record_number = '" +
				record.getRecordNum() + "'");
	}
	
	/**
	 * Add a hold to holds list and insert hold into hold table
	 * @param hold the hold to be added
	 */
	public static void addHold(Hold hold, Patron patron) {
		holds.add(hold);
		
		SQLConnection.sqlUpdate("INSERT INTO hold VALUES('" + patron.getUsername() + "'," +
				hold.getItem().getBarcode() + ",'" + hold.getDatePlaced() + "','" +
				hold.getDateExpires() + "')");
	}
	
	public static void removeHold(Hold hold, Patron patron) {
		for(int i = 0; i < holds.size(); i++) {
			if(holds.get(i).equals(hold)) {
				holds.remove(i--);
			}
		}
		
		SQLConnection.sqlUpdate("DELETE FROM hold WHERE patron = '" + patron.getUsername() +
				"' AND item = " + hold.getItem().getBarcode() + " AND date_placed = '" +
				hold.getDatePlaced() + "'");
	}
	
	//----------------------SEARCH FOR DATA-----------------------------
	
	/**
	 * Given a barcode, iterates over items array and returns item with matching
	 * barcode or null if not found
	 */
	public static Item searchByBarcode(long barcode) {
		Item itemFound = null;
		for(Item item : items) {
			if(item.getBarcode() == barcode) {
				itemFound = item;
				break;
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
	
	/**
	 * Given a record number, iterates over records array and returns record with matching
	 * record number or null if not found
	 */
	public static Record searchByRecordNum(String recordNum) {
		Record recordFound = null;
		for(Record record : records) {
			if(record.getRecordNum() == recordNum) {
				recordFound = record;
			}
		}
		return recordFound;
	}
}
