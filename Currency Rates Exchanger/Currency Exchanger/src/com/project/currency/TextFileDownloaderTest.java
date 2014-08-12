package com.project.currency;

import static org.junit.Assert.*;
import java.io.File;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextFileDownloaderTest {
	private TextFileDownloader textFileDownloaderInstance;
	
	private static Logger logger;
	private static FileAppender appender;

	@Before
	public void setUp() throws Exception {
		textFileDownloaderInstance = new TextFileDownloader();
		
		logger = Logger.getLogger("TextFileDownloaderTestLogger");
		appender = new FileAppender(new SimpleLayout(), "Testing/TestingLog.log"); // Creating new file to have the log prints
		logger.addAppender(appender); // Adding file to get the logs
	}

	@After
	public void tearDown() throws Exception {
			 
		File file = new File("Testing/Data/CurrencyData.xml");
 
    	if(file.delete()){
    		logger.debug(this.toString() + ": " + file.getName() + " is deleted!");
		}else{
			logger.debug(this.toString() + ": " + "Delete operation of " + file.getName() + " has failed.");
		}

	}

	@Test
	public void testUpdateDataFileCore() {
		try
		{
			assertTrue(textFileDownloaderInstance.updateDataFileCore("Testing/Data/CurrencyData.xml"));
			logger.debug(this.toString() + ": TextFileDownloader Test finished succesfully");
		}
		catch (Exception ex)
		{
			logger.error(this.toString() + ": TextFileDownloader Test failed !!" + ex.getMessage());
		}
		
	}

}
