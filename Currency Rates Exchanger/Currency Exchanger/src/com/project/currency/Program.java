package com.project.currency;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

/**
 * The Program class is responsible to run the main program.
 * 
 * @author Tamar
 *
 */
public class Program {
	public static Logger logger;
	public static FileAppender appender;
	
	/**
	 * Main will create the logger object for handling the logger messages.
	 * The inner run method will create the GUI object and call the start method on it.
	 * 
	 * @param args					unused parameter.
	 */
	public static void main(String[] args)
	// Running the GUI in addition thread separately from the event dispatch thread
	{
		logger = Logger.getLogger("Application");

		try {
			appender = new FileAppender(new SimpleLayout(), "SystemLog.log"); // Creating new file to have the log prints
			logger.addAppender(appender); // Adding file to get the logs
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					GUI ob = new GUI();
					ob.start();
				}
			});
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Cannot create the log file",
					"Error Message", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Can't write to the specific file", "Error Message",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
