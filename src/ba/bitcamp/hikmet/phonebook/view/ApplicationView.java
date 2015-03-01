package ba.bitcamp.hikmet.phonebook.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ba.bitcamp.hikmet.phonebook.controller.ApplicationController;
import ba.bitcamp.hikmet.phonebook.model.Contact;

/**
 * This is the visual part of the phonebook
 * with the views for: home, add contact, list of contacts
 * Extends the class Main and its JFrame, on which the panels are added
 * and replaced by need
 * 
 */
public class ApplicationView extends Main {

	/*
	 * This panel is for the 'home' view
	 * Contains a welcome message as JLabel, and
	 * buttons: Add Contact, Show Contacts
	 */
	public static void home() {
		JPanel content = new JPanel();
		JLabel greeting = new JLabel("Welcome to BitBook");

		Font grretingFont = new Font("SansSerif", Font.BOLD, 30);
		greeting.setFont(grretingFont);

		content.add(greeting);

		
		JButton showContact = new JButton("Show Contacts"); // anonymous action listener
		showContact.addActionListener(new ActionListener() {
			
			@Override
			/* 
			 * (non-Javadoc)
			 * calling the list() method through the ApplicationController class 
			 */
			public void actionPerformed(ActionEvent e) {
	
				ApplicationController.list(); 
			}
		});
		JButton addContact = new JButton("Add Contact");
		addContact.addActionListener(new ActionListener() {
			
			@Override
			/*
			 * (non-Javadoc)
			 * calling the addContact() method through the ApplicationController class 
			 */
			public void actionPerformed(ActionEvent e) {

				ApplicationController.addContact(); 
			}
		});

		/* adding buttons to the panel */
		content.add(addContact);
		content.add(showContact);
		
		/*Replacing the panel on the frame by calling the method from the parent class*/
		replaceContent(content);

	}
	
	
	
	public static void show(final Contact c){
	
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
//		Font dataFont = new Font("SansSerif", Font.BOLD, 30);
		
		JLabel nameL = new JLabel("Name");
		JLabel nameF = new JLabel(c.getName());
		content.add(nameL);
		content.add(nameF);

		JLabel surnameL = new JLabel("Surname");
		JLabel surnameF = new JLabel(c.getSurname());
		content.add(surnameL);
		content.add(surnameF);

		JLabel numberL = new JLabel("Number ");
		JLabel numberF = new JLabel(c.getNumber());
		content.add(numberL);
		content.add(numberF);

		JButton edit = new JButton("EDIT");
		edit.addActionListener(new ActionListener() {	
			@Override	
			public void actionPerformed(ActionEvent e) {
				ApplicationView.updateContact(c);
			}
		});
		JButton back = new JButton("BACK");
		back.addActionListener(new ActionListener() {		
			
			public void actionPerformed(ActionEvent e) {
				ApplicationController.list();
			}
		});

		JPanel buttons = new JPanel();
		buttons.add(back);
		buttons.add(edit);		
		content.add(buttons);
		
		replaceContent(content); 
		
	}//end of show()

	/*
	 * This panel is for the 'adding contact' view
	 * Contains the names for the input fields as JLabel, 
	 * input fields as JTextField and
	 * buttons: SUBMIT, BACK 
	 */
	public static void addContact() {

		JPanel content = new JPanel();
		content.setLayout(new FlowLayout());
		JLabel nameL = new JLabel("Name    ");
		final JTextField nameF = new JTextField(25);
		content.add(nameL);
		content.add(nameF);

		JLabel surnameL = new JLabel("Surname");
		final JTextField surnameF = new JTextField(25);
		content.add(surnameL);
		content.add(surnameF);

		JLabel numberL = new JLabel("Number ");
		final JTextField numberF = new JTextField(25);
		content.add(numberL);
		content.add(numberF);

		JButton save = new JButton("SUBMIT");
		save.addActionListener(new ActionListener() {
			
			@Override	
			/*
			 * (non-Javadoc)
			 * The values from the input text fields are taken
			 * and sent as parameters to the create() method
			 * from the  ApplicationController class which
			 * saves the new contact 
			 */
			public void actionPerformed(ActionEvent e) {
				String cName = nameF.getText();
				String cSurname = surnameF.getText();
				String cNumber = numberF.getText();
				ApplicationController.create(cName, cSurname, cNumber);
			}
		});
		JButton back = new JButton("BACK");
		back.addActionListener(new ActionListener() {
			
			@Override
			/*
			 * (non-Javadoc)
			 * Calling the home() method through the ApplicationController class
			 * and replacing the view to the home view
			 */
			public void actionPerformed(ActionEvent e) {
				ApplicationController.home();
			}
		});

		JPanel buttons = new JPanel();
		buttons.add(save);
		buttons.add(back);		
		content.add(buttons);
		
		/*Replacing the panel on the frame by calling the method from the parent class*/
		replaceContent(content); 

	}
	
	public static void updateContact(final Contact current) {

		JPanel content = new JPanel();
		content.setLayout(new FlowLayout());
		JLabel nameL = new JLabel("Name: ");
		final JTextField nameF = new JTextField(current.getName(), 25);
		content.add(nameL);
		content.add(nameF);

		JLabel surnameL = new JLabel("Surname");
		final JTextField surnameF = new JTextField(current.getSurname(), 25);
		content.add(surnameL);
		content.add(surnameF);

		JLabel numberL = new JLabel("Number ");
		final JTextField numberF = new JTextField(current.getNumber(), 25);
		content.add(numberL);
		content.add(numberF);

		JButton update = new JButton("Save Update");
		update.addActionListener(new ActionListener() {
			
			@Override	
			public void actionPerformed(ActionEvent e) {
				String cName = nameF.getText();
				String cSurname = surnameF.getText();
				String cNumber = numberF.getText();
				current.setName(cName);
				current.setSurname(cSurname);
				current.setNumber(cNumber);
				ApplicationController.update(current.getId());
				
			}
		});
		JButton back = new JButton("BACK");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationController.show(current.getId());
			}
		});
		
		
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null,  "Are you sure?", "Delete?", JOptionPane.YES_NO_OPTION);
				if (response == JOptionPane.YES_OPTION){
					ApplicationController.delete(current.getId());
				} else { 
					return;			
				}
				
				
			}
		});

		JPanel buttons = new JPanel();
		buttons.add(back);		
		buttons.add(update);
		buttons.add(delete);
		content.add(buttons);
		
		/*Replacing the panel on the frame by calling the method from the parent class*/
		replaceContent(content); 

	}

	/*
	 * This panel is for the 'list of contacts' view
	 * Contains two buttons: back, add contact
	 * and the list of contacts represented on buttons in an vertical order
	 */
	public static void list(Contact[] all) {
		
		int buttonHeight = 50;
		
		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(ApplicationView.windowWidth - 70, all.length * (buttonHeight + 20)));
		
		JButton back = new JButton("Back"); 
		back.addActionListener(new ActionListener() {
			
			@Override
			/* 
			 * Redirecting to 'home' view by calling the home() 
			 * method through the ApplicationController class
			 */
			public void actionPerformed(ActionEvent e) {
				ApplicationController.home();
			}
		});
		JButton addContact = new JButton("Add Contact");
		addContact.addActionListener(new ActionListener() {
			
			@Override
			/* 
			 * Redirecting to 'add contact' view by calling the addContact() 
			 * method through the ApplicationController class
			 */
			public void actionPerformed(ActionEvent e) {
				ApplicationController.addContact();
			}
		});
		
		/* adding buttons to panel of the list view */
		content.add(back);
		content.add(addContact);
	
		/* if there aren't any contacts a info message is displayed */
		if (all.length < 1){
			JLabel message = new JLabel("Contact list empty");
			Font messageFont = new Font("SansSerif", Font.BOLD, 30);
			message.setFont(messageFont);
			content.add(message);
		}

		/*
		 * Creates a button for each contact in the list
		 * sets the label and name for the button
		 * connects an action listener
		 * and adds the button to the content panel
		 */
		for (int i = 0; i < all.length; i++) {
			JButton current = new JButton(all[i].getDisplayName());	
			current.setName(Integer.toString(all[i].getId()) );
			current.setPreferredSize(new Dimension( ApplicationView.windowWidth - 75, buttonHeight ));
			
			current.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO redirect to contact
					JButton clicked = (JButton)(e.getSource());
					int id = Integer.parseInt(clicked.getName());
					ApplicationController.show(id);
					
					System.out.println("Korisnik: " + id);
					
				}
			});
			content.add(current);
		}
		
		/* scrolling enabled by initializing a JScrollPane
		 * and adding the content panel to it
		 */
		JScrollPane sp = new JScrollPane(content);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				
		replaceContent(sp);
		
	}

}
