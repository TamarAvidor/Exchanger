package com.project.currency;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppManagerTest {
	private AppManager appManager;
	
	private static Logger logger;
	private static FileAppender appender;

	@Before
	public void setUp() throws Exception {
		appManager = new AppManager();
		
		logger = Logger.getLogger("AppManagerTestLogger");
		appender = new FileAppender(new SimpleLayout(), "Testing/TestingLog.log"); // Creating new file to have the log prints
		logger.addAppender(appender); // Adding file to get the logs
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsDataUpToDate() {
		Boolean boolT, boolF;
		Date now = new Date();
		Long deltaTime = now.getTime() - 2* 60 * 60 * 1000; //Subtracting 2 hours from nows long representation (2h in milliseconds)
		
		boolT = appManager.isDataUpToDate(now, 1);
		boolF = appManager.isDataUpToDate(new Date(deltaTime), 1);	
		
		try
		{
			assertTrue((boolT == true)&&(boolF == false));
			logger.debug(this.toString() + ": AppManager Test finished succesfully");
		}
		catch (Exception ex)
		{
			logger.error(this.toString() + ": AppManager Test failed !!" + ex.getMessage());
		}
	}

}
