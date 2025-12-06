import java.util.ArrayList;

import javafx.scene.image.Image;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
		catch (Exception ex) {
		    System.err.println("Failed loading record");
		    ex.printStackTrace();
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
		setHolds();
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
		System.out.println(imagePath);
		
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
		
		LocalDate currentDate = LocalDate.now();
		LocalDate dateExpires = currentDate.plus(2, ChronoUnit.WEEKS);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDateExpires = dateExpires.format(formatter);
		
		hold.setDateExpires(formattedDateExpires);
		
		SQLConnection.sqlUpdate("INSERT INTO hold VALUES('" + patron.getUsername() + "'," +
				hold.getItem().getBarcode() + ",'" + currentDate + "','" +
				formattedDateExpires + "')");
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
	
	/**
	 * Add a checkout to the checkout table. set item status to checked out in item table
	 * @param patron patron who is making the checkout
	 * @param item the item being checked out
	 */
	public static void addCheckout(Patron patron, Item item) {
		LocalDate currentDate = LocalDate.now();
		LocalDate dueDate = currentDate.plus(2, ChronoUnit.WEEKS);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDueDate = dueDate.format(formatter);
		
		item.setDueDate(formattedDueDate);
		
		SQLConnection.sqlUpdate("UPDATE item SET status = 'CHECKED_OUT' WHERE barcode = " +
				item.getBarcode());
		
		SQLConnection.sqlUpdate("INSERT INTO checkout VALUES('" + patron.getUsername() + "'," 
				+ item.getBarcode() + ",'" + formattedDueDate + "')");
	}
	
	/**
	 * delete a checkout from checkout table. set item status to available.
	 * @param item the item being checked in
	 */
	public static void removeCheckout(Item item) {
		SQLConnection.sqlUpdate("UPDATE item SET status = 'AVAILABLE' WHERE barcode = " + item.getBarcode());
		SQLConnection.sqlUpdate("UPDATE item SET circulations = circulations + 1 WHERE barcode = "
				+ item.getBarcode());
		
		SQLConnection.sqlUpdate("DELETE FROM checkout WHERE barcode = " + item.getBarcode());
	}
	
	/**
	 * delete a fine
	 * @param fine fine to be deleted
	 */
	public static void removeFine(Fine fine) {
		SQLConnection.sqlUpdate("DELETE FROM fine WHERE amount = " + fine.getAmount() + " AND item = " +
				fine.getItem().getBarcode() + " AND date_began = '" + fine.getDateBegan() + "'");
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
	 * Given a patron username, iterates over patrons array and returns patron with matching
	 * username or null if not found
	 */
	public static Patron searchPatronByUsername(String username) {
		Patron patronFound = null;
		for(Patron patron : patrons) {
			if(patron.getUsername().equals(username)) {
				patronFound = patron;
				break;
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
			if(record.getRecordNum().toLowerCase().equals(recordNum.toLowerCase())) {
				recordFound = record;
				break;
			}
		}
		return recordFound;
	}
	
	/**
	 * Given an item, returns ArrayList of all holds on the item
	 * @param item
	 * @return
	 */
	public static ArrayList<Hold> searchHoldsByItem(Item item) {
		ArrayList<Hold> holdsFound = new ArrayList<Hold>();
		for(Hold hold : holds) {
			if(hold.getItem().equals(item)) {
				holdsFound.add(hold);
			}
		}
		return holdsFound;
	}
	
	/**
	 * Given a record, returns ArrayList of all holds on the item
	 * @param item
	 * @return
	 */
	public static ArrayList<Hold> searchHoldsByRecord(Record record) {
		ArrayList<Hold> holdsFound = new ArrayList<Hold>();
		for(Hold hold : holds) {
			if(hold.getItem().getRecord().equals(record)) {
				holdsFound.add(hold);
			}
		}
		return holdsFound;
	}
	
	/**
	 * Given a record, returns ArrayList of all Items of that record
	 * @param record
	 * @return
	 */
	public static ArrayList<Item> searchItemsByRecord(Record record) {
		ArrayList<Item> itemsFound = new ArrayList<Item>();
		for(Item item : items) {
			if(item.getRecord().equals(record)) {
				itemsFound.add(item);
			}
		}
		return itemsFound;
	}
	
	/**
	 * Given a value for a column, return an array list of matching records
	 */
	public static ArrayList<Record> searchBooksByColumnValue(String col, String val) {
		ResultSet booksRs = SQLConnection.sqlQuery("SELECT * FROM book_record LEFT JOIN record "
				+ "ON book_record.record_number = record.record_number WHERE " + col + " = " + val);
		
		ArrayList<Record> records = new ArrayList<>();
		try {
			while(booksRs.next()) {
				String recordNumber = booksRs.getString("record_number");
				String title = booksRs.getString("title");
				String callNumber = booksRs.getString("call_number");
				String imagePath = booksRs.getString("image_path");
				Image image;
				try {
					image = new Image(imagePath);
				} catch(Exception e) {
					image = new Image("images/default_cover.jpg");
				}
				boolean circulating = booksRs.getBoolean("circulating");
				String sectionStr = booksRs.getString("section");
				Section section = Section.valueOf(sectionStr);
				
				String authorFirstName = booksRs.getString("author_first_name");
				String authorLastName = booksRs.getString("author_last_name");
				String publicationDate = booksRs.getDate("publication_date").toString();
				String edition = booksRs.getString("edition");
				long isbn = booksRs.getLong("isbn");
				String genre = booksRs.getString("genre");
				
				BookRecord bookRecord = new BookRecord(recordNumber, title, callNumber, authorLastName, 
						authorFirstName, section, publicationDate, edition, isbn, genre);
				bookRecord.setCirculating(circulating);
				bookRecord.setImage(image);
				
				records.add(bookRecord);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return records;
	}
	
	/**
	 * Given a call number, find all records with matching callnum and return in ArrayList
	 */
	public static ArrayList<Record> searchRecordsByCallNum(String callNum) {
		String callNumLower = callNum.toLowerCase();
		
		ArrayList<Record> resultRecords = new ArrayList<>();
		
		for(Record record : records) {
			if(record.getCallNum().toLowerCase().equals(callNumLower)) {
				resultRecords.add(record);
			}
		}
		
		return resultRecords;
	}
	
	/**
	 * Given an ISBN, return all records with matching ISBN
	 */
	public static ArrayList<Record> searchRecordsByISBN(long isbn){
		ArrayList<Record> resultRecords = new ArrayList<>();
		
		for(Record record : records) {
			if(record instanceof BookRecord) {
				if(((BookRecord)record).getIsbn() == isbn) {
					resultRecords.add(record);
				}
			}
			
		}
		
		return resultRecords;
	}
	
	/**
	 * Given a title, search for records with matching title
	 * TODO: improve so that search title does not have to exactly match results
	 * 		 for them to show up.
	 */
	public static ArrayList<Record> searchRecordsByTitle(String title) {
		String lowerTitle = title.toLowerCase();
		
		ArrayList<Record> resultRecords = new ArrayList<>();
		
		for(Record record : records) {
			if(record.getTitle().toLowerCase().equals(lowerTitle)) {
				resultRecords.add(record);
			}
		}
		
		return resultRecords;
	}
	
	/**
	 * Return list of all records of a given section
	 */
	public static ArrayList<Record> searchRecordsBySection(String section) {
		String sectionLower = section.toLowerCase();
		
		Section searchSection;
		
		ArrayList<Record> resultRecords = new ArrayList<>();
		
		if(sectionLower.equals("juvenile fiction") || sectionLower.equals("jf") || sectionLower.equals("juvenile_fiction")) {
			searchSection = Section.JUVENILE_FICTION;
		} else if(sectionLower.equals("juvenile non fiction") || sectionLower.equals("jnf") || sectionLower.equals("juvenile non-fiction")
				|| sectionLower.equals("juvenile nf") || sectionLower.equals("juvenile_non_fiction")) {
			searchSection = Section.JUVENILE_NON_FICTION;
		} else if(sectionLower.equals("juvenile picture") || sectionLower.equals("jp") || sectionLower.equals("juvenile_picture")
				|| sectionLower.equals("picture")) {
			searchSection = Section.JUVENILE_PICTURE;
		} else if(sectionLower.equals("juvenile dvd") || sectionLower.equals("jdvd") || sectionLower.equals("juvenile_dvd")
				|| sectionLower.equals("j dvd")) {
			searchSection = Section.JUVENILE_PICTURE;
		} else if(sectionLower.equals("juvenile cd") || sectionLower.equals("jcd") || sectionLower.equals("juvenile_cd")
				|| sectionLower.equals("j cd")) {
			searchSection = Section.JUVENILE_CD;
		} else if(sectionLower.equals("fiction") || sectionLower.equals("adult fiction") || sectionLower.equals("f")) {
			searchSection = Section.FICTION;
		} else if(sectionLower.equals("non fiction") || sectionLower.equals("nonfiction") || sectionLower.equals("non-fiction")
				|| sectionLower.equals("adult non fiction") || sectionLower.equals("adult nonfiction") || sectionLower.equals("adult non-fiction")
				|| sectionLower.equals("non_fiction")) {
			searchSection = Section.NON_FICTION;
		} else if(sectionLower.equals("high_school_fiction") || sectionLower.equals("hsf") || sectionLower.equals("hs f") 
				|| sectionLower.equals("hs fiction") || sectionLower.equals("high school fiction")) {
			searchSection = Section.HIGH_SCHOOL_FICTION;
		} else if(sectionLower.equals("high_school_non_fiction") || sectionLower.equals("high_school_nonfiction") || sectionLower.equals("hsnf")
				|| sectionLower.equals("hs nf") || sectionLower.equals("high school nonfiction") || sectionLower.equals("high school non-fiction")
				|| sectionLower.equals("high school nf") || sectionLower.equals("high school non fiction")) {
			searchSection = Section.HIGH_SCHOOL_NON_FICTION;
		} else if(sectionLower.equals("young_person_fiction") || sectionLower.equals("ypf") || sectionLower.equals("yp f") 
				|| sectionLower.equals("yp fiction") || sectionLower.equals("young person fiction")) {
			searchSection = Section.YOUNG_PERSON_FICTION;
		} else if(sectionLower.equals("young_person_non_fiction") || sectionLower.equals("young_person_nonfiction") || sectionLower.equals("ypnf")
				|| sectionLower.equals("yp nf") || sectionLower.equals("young person nonfiction") || sectionLower.equals("young person non-fiction")
				|| sectionLower.equals("young person nf") || sectionLower.equals("young person non fiction")) {
			searchSection = Section.YOUNG_PERSON_NON_FICTION;
		} else if(sectionLower.equals("dvd") || sectionLower.equals("adult dvd") || sectionLower.equals("adult_dvd")) {
			searchSection = Section.DVD;
		} else if(sectionLower.equals("cd") || sectionLower.equals("adult cd") || sectionLower.equals("adult_cd")) {
			searchSection = Section.CD;
		} else if(sectionLower.equals("periodical") || sectionLower.equals("per")) {
			searchSection = Section.PERIODICAL;
		} else if(sectionLower.equals("spanish_non_fiction") || sectionLower.equals("spanish_nonfiction") || sectionLower.equals("spanf")
				|| sectionLower.equals("spa nf") || sectionLower.equals("spanish nonfiction") || sectionLower.equals("spanish non-fiction")
				|| sectionLower.equals("spanish nf") || sectionLower.equals("spanish non fiction") || sectionLower.equals("spanish nf")) {
			searchSection = Section.SPANISH_NON_FICTION;
		} else if(sectionLower.equals("spanish fiction") || sectionLower.equals("spanish_fiction") || sectionLower.equals("spaf")
				|| sectionLower.equals("spa f") || sectionLower.equals("spanish f")) {
			searchSection = Section.SPANISH_FICTION;
		} else if(sectionLower.equals("TOY")) {
			searchSection = Section.TOY;
		} else {
			return resultRecords;
		}
		
		for(Record record : records) {
			if(record.getSection() == searchSection) {
				resultRecords.add(record);
			}
		}
		
		return resultRecords;
	}
	
	/**
	 * Return array list of all employees with matching first name
	 */
	public static ArrayList<Employee> searchEmployeesByFirstName(String firstName) {
		String name = firstName.toLowerCase();
		
		ArrayList<Employee> resultEmployees = new ArrayList<>();
		
		for(Employee employee : employees) {
			if(employee.getFirstName().toLowerCase().equals(name)) {
				resultEmployees.add(employee);
			}
		}
		
		return resultEmployees;
	}
	
	/**
	 * Return array list of all employees with matching last name
	 */
	public static ArrayList<Employee> searchEmployeesByLastName(String lastName) {
		String name = lastName.toLowerCase();
		
		ArrayList<Employee> resultEmployees = new ArrayList<>();
		
		for(Employee employee : employees) {
			if(employee.getLastName().toLowerCase().equals(name)) {
				resultEmployees.add(employee);
			}
		}
		
		return resultEmployees;
	}
	
	/**
	 * Return array list of all employees with matching username
	 */
	public static ArrayList<Employee> searchEmployeesByUsername(String username) {
		String name = username.toLowerCase();
		
		ArrayList<Employee> resultEmployees = new ArrayList<>();
		
		for(Employee employee : employees) {
			if(employee.getUsername().toLowerCase().equals(name)) {
				resultEmployees.add(employee);
			}
		}
		
		return resultEmployees;
	}
	
	/**
	 * Return array list of all patrons with matching first name
	 */
	public static ArrayList<Patron> searchPatronsByFirstName(String firstName) {
		String name = firstName.toLowerCase();
		
		ArrayList<Patron> resultPatrons = new ArrayList<>();
		
		for(Patron patron : patrons) {
			if(patron.getFirstName().toLowerCase().equals(name)) {
				resultPatrons.add(patron);
			}
		}
		
		return resultPatrons;
	}
	
	/**
	 * Return array list of all patrons with matching last name
	 */
	public static ArrayList<Patron> searchPatronsByLastName(String lastName) {
		String name = lastName.toLowerCase();
		
		ArrayList<Patron> resultPatrons = new ArrayList<>();
		
		for(Patron patron : patrons) {
			if(patron.getLastName().toLowerCase().equals(name)) {
				resultPatrons.add(patron);
			}
		}
		
		return resultPatrons;
	}
	
	/**
	 * Return array list of all patrons with matching username
	 */
	public static ArrayList<Patron> searchPatronsByUsername(String username) {
		String name = username.toLowerCase();
		
		ArrayList<Patron> resultPatrons = new ArrayList<>();
		
		for(Patron patron : patrons) {
			if(patron.getUsername().toLowerCase().equals(name)) {
				resultPatrons.add(patron);
			}
		}
		
		return resultPatrons;
	}
	
	/**
	 * Return array list of all patrons with matching email
	 */
	public static ArrayList<Patron> searchPatronsByEmail(String searchEmail) {
		String email = searchEmail.toLowerCase();
		
		ArrayList<Patron> resultPatrons = new ArrayList<>();
		
		for(Patron patron : patrons) {
			if(patron.getEmail().toLowerCase().equals(email)) {
				resultPatrons.add(patron);
			}
		}
		
		return resultPatrons;
	}

	
}
