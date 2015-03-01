package ba.bitcamp.hikmet.phonebook.model;

import java.sql.*;
/**
 * This is for establishing the connection with the database
 * Includes methods for insert to and search from the database
 *
 */
public class Application {
	
	
	protected static Connection db; /* connection is necessary for working with the database */

	/*
	 *  initializing the connection with the database 
	 */
	public static void init(String databaseName) throws SQLException {
		db = DriverManager.getConnection("jdbc:sqlite:"+databaseName+".db");
	}

	/**
	 * This method is for finding data from a certain contact from the database.
	 * Makes a query to the database and returns all the data for the specified id and table as ResultSet
	 * @param id of the contact as int
	 * @param name of the table as String
	 * @return ResultSet if the query succeeds, or null if an error occurs, and a System error message is written in the standard output
	 */
	protected static ResultSet find(int id, String tableName) {
		try {
			Statement stmt = db.createStatement();  // Statement is needed for accessing the database, it's from the external JDBC library 
			String sql = String.format("SELECT * FROM %s WHERE id = '%d' ;",
					tableName, id);
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Saves data to database
	 * @param tableName to save to as String
	 * @param values to Save as String
	 * @return true if the save is finished successfully or false if there is an error
	 * If error occurs, a rollback is done and the database stays untouched
	 */
	protected static int save(String tableName, String values) {

		Statement stmt = null;
		try {
			stmt = db.createStatement();
			String sql = String.format("INSERT INTO %s VALUES %s;", tableName, values );
			stmt.execute("begin;");
			stmt.execute(sql);
			stmt.execute("commit;");
			sql = String.format( "SELECT max(id) as last_id FROM %s;", tableName);
			ResultSet res = stmt.executeQuery(sql);
			res.next();	
			return res.getInt(1);
		} catch (SQLException e) {
			if (stmt != null) {
				try {
					stmt.execute("rollback;");
				} catch (SQLException e1) {
					System.err.println(e.getMessage());
					return -1;
				}
			}
			System.err.println(e.getMessage());
			return -1;
		}
		
	}
	/**
	 * Returns data as ResultSet from the specified columns and table if the query succeeds,
	 * else it catches the error, writes out a System error message and returns null.
	 * @param name of the table as String
	 * @param name of the column as String
	 * @return ResultSet or null
	 */
	protected static ResultSet all(String tableName, String columnNames){
		try {
			Statement stmt = db.createStatement();
			String sql = String.format("SELECT %s FROM %s;", columnNames, tableName);
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
		
	}

	protected static void update(String tableName, int id, String values) {
		Statement stmt = null;
		try {
			stmt = db.createStatement();
			String sql = String.format("UPDATE %s SET %s WHERE id = '%d';", tableName, id);
			stmt.execute(sql);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		
	}

	public static void delete(String tableName, int id) {
		try {
			Statement stmt = db.createStatement();
			String sql = String.format("DELETE FROM %s WHERE id = '%d';", tableName, id);
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	
	
	
}
