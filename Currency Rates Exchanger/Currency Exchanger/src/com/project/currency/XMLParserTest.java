package com.project.currency;

import java.io.File;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class XMLParserTest {
	private XMLParser XMLParserer;
	private boolean XMLParsingSucceeded;
	
	private static Logger logger;
	private static FileAppender appender;
	
	@Before
	public void setUp() throws Exception {
		TextFileDownloader textFileDownloader = new TextFileDownloader();
		textFileDownloader.updateDataFileCore("Testing/Data/CurrencyData.xml");
		XMLParserer = new XMLParser();
		
		logger = Logger.getLogger("XMLParsererTestLogger");
		appender = new FileAppender(new SimpleLayout(), "Testing/TestingLog.log"); // Creating new file to have the log prints
		logger.addAppender(appender); // Adding file to get the logs
	}

	@After
	public void tearDown() throws Exception {
		File file = new File("Testing/Data/CurrencyData.xml");
		file.delete();
	}

	@Test
	public void testParseXMLFile() {
		XMLParsingSucceeded = XMLParserer.parseXMLFileCore("Testing/Data/CurrencyData.xml");
		
		try
		{
			assert(XMLParsingSucceeded == true);
			logger.debug(this.toString() + ": XMLParser finished succesfully");
		}
		catch (Exception ex)
		{
			logger.error(this.toString() + ": XMLParser Test failed !!" + ex.getMessage());
		}	
	}
}
