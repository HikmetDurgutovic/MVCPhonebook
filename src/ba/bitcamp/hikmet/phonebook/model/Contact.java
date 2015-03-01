package ba.bitcamp.hikmet.phonebook.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This makes a Contact as object Extends the Application class Includes basic
 * properties as: id, name, surname, number
 * 
 */
public class Contact extends Application {

	private int id;
	private String name, surname, number;
	private final static String tableName = "contacts";

	/* empty constructor */
	public Contact() {

		this.id = -1;
		this.name = "unknown";
		this.surname = "unknown";
		this.number = "";

	}

	/*
	 * ovaj konstruktor smo napravili kad smo razivjali opciju za prikaz svih
	 * kontakata
	 */
	public Contact(int id, String name, String surname) {

		this.id = id;
		this.name = name;
		this.surname = surname;
		this.number = "";
	}

	/* constructor without the id property */
	public Contact(String name, String surname, String number) {

		this.id = -1;
		this.name = name;
		this.surname = surname;
		this.number = number;
	}

	/* constructor with all properties */
	public Contact(int id, String name, String surname, String number) {

		this.id = id;
		this.name = name;
		this.surname = surname;
		this.number = number;
	}

	/**
	 * Method serves to find a certain contact and return it as a Contact object
	 * with all of its properties. This method calls the method find() from the
	 * parent class and sends the given id as parameter and the name of the
	 * table declared in this class.
	 * 
	 * @param id
	 *            of the contact in the database as integer, and name of the
	 *            table
	 * @return Contact or null if an error occurs and a system error message is
	 *         written
	 */
	public static Contact find(int id) {
		ResultSet res = Application.find(id, tableName);
		try {
			int cid = res.getInt("id");
			String cName = res.getString("name");
			String cSurname = res.getString("surname");
			String cNumber = res.getString("number");
			return new Contact(cid, cName, cSurname, cNumber);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Method saves this Contact to the database by calling the save() method
	 * from the parent class
	 * 
	 * @return true if successful or false if an error occurs in the parent
	 *         method
	 */
	public boolean save() {
		String values = null;
		if (this.id != -1) {
			values = String.format("(%d, '%s', '%s', '%s')", this.id, this.name, this.surname, this.number);
		} else {
			values = String.format("(?, '%s', '%s', '%s')", this.name, this.surname, this.number);
		}

		int id = Application.save(tableName, values);
		if (id == -1) {
			return false;
		} else {
			this.id = id;
			return true;
		}
	}

	/**
	 * Converts all the contacts from the database into an array of Contacts
	 * (objects)
	 * 
	 * @return array Contact[] with properties: id, name, surname, or empty
	 *         array Contact[0] if there aren't any contacts
	 */
	/*
	 * Inside the method, first thing, an ResultSet of all contacts is made by
	 * calling the parent class method all(). Then, all of the contacts in the
	 * ResultSet are added to a LinkedList<Contact> Finally, an Contact[] array
	 * is made with the size of the LinkedList, and the list is converted to
	 * that array.
	 */
	public static Contact[] all() {
		ResultSet res = Application.all(tableName, "id, name, surname");
		if (res == null) {
			return new Contact[0];
		}
		LinkedList<Contact> cl = new LinkedList<Contact>();
		try {
			while (res.next()) {
				int cId = res.getInt("id");
				String cName = res.getString("name");
				String cSurname = res.getString("surname");
				cl.add(new Contact(cId, cName, cSurname));
			}
			Contact[] all = new Contact[cl.size()];
			cl.toArray(all);
			return all;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return new Contact[0];
		}

	}
	//UPDATE tablename SET kolona WHERE id = 'id'
	public void update(){
		String sql = String.format("name = '%s', surname = '$s', number '%s'", this.name, this.surname, this.number);
		Application.update(tableName, this.id, sql);
	}
	
	public static void delete(int id) {
		Application.delete(tableName, id);
		
	}

	/* getters and setters */

	public String getDisplayName() {
		return this.name + " " + this.surname;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}



}