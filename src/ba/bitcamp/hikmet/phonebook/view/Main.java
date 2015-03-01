package ba.bitcamp.hikmet.phonebook.view;

import java.awt.Container;
import javax.swing.JFrame;
/**
 * This creates the main window on which the content will apply
 *
 */
public class Main {

	private static JFrame window = null;
	protected static int windowWidth = 400;
	protected static int windowHeight = 500;

	public static void init() {
		window = new JFrame("BitBook");
		window.setSize(windowWidth, windowHeight);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);

	}

	protected static void setVisible() {
		window.validate();
		window.repaint();
		window.setVisible(true);
	}

	/**
	 * This method replaces the content in the window with the new current content
	 * @param JPanel as parameter (Container)
	 */
	protected static void replaceContent(Container panel) {
		window.setContentPane(panel);
		setVisible();
	}

}