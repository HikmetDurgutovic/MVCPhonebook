package ba.bitcamp.hikmet.phonebook.controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import ba.bitcamp.hikmet.phonebook.model.Application;
import ba.bitcamp.hikmet.phonebook.model.Contact;
import ba.bitcamp.hikmet.phonebook.view.ApplicationView;
import ba.bitcamp.hikmet.phonebook.view.Main;

/**
 * This class starts the methods from Application and Main classes (starts the
 * application)
 *
 */
public class ApplicationController {

	public static void home() {

		ApplicationView.home();
	}

	public static void addContact() {
		ApplicationView.addContact();
	}

	public static void create(String name, String surname, String number) {
		Contact newContact = new Contact(name, surname, number);

		/*
		 * Pops out a information message window if the 'save contact' procedure
		 * is done or not
		 */

		if (newContact.save() == true) {
			
			show(newContact.getId());
		} else {
			JOptionPane.showMessageDialog(null, "There is an invalid input","Error saving Contact", JOptionPane.WARNING_MESSAGE);
		}
	}

	public static void show(int id) {
		Contact current = Contact.find(id);
		ApplicationView.show(current);
	}

	public static void list() {
		Contact[] all = Contact.all();
		ApplicationView.list(all);
	}
	
	public static void update(int id) {
		Contact current = Contact.find(id);
		ApplicationView.updateContact(current);		
	}
	
	public static void update(Contact c) {
		c.update();
		ApplicationView.show(c);
	}
	
	public static void delete(int id) {
		Contact.delete(id);
		list();
	}
	

	public static void main(String[] args) {

		try {
			Application.init("phonebook"); // starts the connection with the
											// database
		} catch (SQLException e) {
			System.err.println("Application.int " + e.getMessage());
			System.exit(1);
		}

		Main.init(); // opens main window
		home(); // sets the contentPane to main window

	}

	



}
