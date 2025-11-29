import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
	private static Connection conn;
	private static Statement stmt;
	
	
	public static void loadConnection() {
		String url = "jdbc:mysql://localhost:3306/library";
		String user = "root";
		String password = "1234";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Execute a query that will return a result set (select queries)
	 * @param query the query to execute
	 * @return set of results from the query
	 */
	public static ResultSet sqlQuery(String query) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	/**
	 * Execute any SQL statement that will not return a result set
	 * (update, delete, insert)
	 * @param query the query to execute
	 */
	public static void sqlUpdate(String query) {
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
