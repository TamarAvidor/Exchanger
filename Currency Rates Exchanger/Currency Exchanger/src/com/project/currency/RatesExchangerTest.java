package com.project.currency;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RatesExchangerTest {
	private RatesExchanger ratesExchanger;
	
	private static Logger logger;
	private static FileAppender appender;
	
	@Before
	public void setUp() throws Exception {
		ratesExchanger = new RatesExchanger();
		
		logger = Logger.getLogger("RatesExchangerTestLogger");
		appender = new FileAppender(new SimpleLayout(), "Testing/TestingLog.log"); // Creating new file to have the log prints
		logger.addAppender(appender); // Adding file to get the logs
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMoneyExchange() {
		double result = ratesExchanger.moneyExchange(20, 3, 5);
		
		try
		{
			assert(result == 12);
			//assertEquals(12, (ratesExchanger.moneyExchange(20, 3, 5))); //deprecated method.
	        logger.debug(this.toString() + ": RatesExchanger Test finished succesfully");
		}
		catch (Exception ex)
		{
			logger.error(this.toString() + ": RatesExchanger Test failed !!" + ex.getMessage());
		}
	}

}
