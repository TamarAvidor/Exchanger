package com.project.currency;

import java.util.*;

/**
 * The IAppManager interface is responsible for managing the entire application's logical part. Storing, refreshing an handling the data.
 */
public interface IAppManager {
	/**
	 * Checking whether the data is up to date by a given date time as an argument, and checking the delta with a given fixed time.
	 * 
	 * @param fileDate					the reference date- the date of the current downloaded file.
	 * @param timeTriggerInHours		the chosen time amount considered to be out of date.
	 * @return							an answer that indicates whether the chosen amount of time has passed.
	 */
	boolean isDataUpToDate(Date fileDate, int timeTriggerInHours);
	/**
	 * Downloads data and parsing it.
	 * Checking for whole operation success (two condition: file download and XML parsing).
	 * 
	 * @return							an answer that indicates whether the data was refreshed successfully. 
	 */
	boolean refreshDataAndCheckForSuccess();
	/**
	 * Iterating the HashMap and returns ArrayList of Strings that will be shown at the GUI.
	 * Each element in the ArrayList includes the country name and currency name
	 * 
	 * @return 							an ArrayList of Strings storing the currency and country names.
	 */
	ArrayList<String> getCurrenciesArray();
	/**
	 * Receiving a string which represent a coin's key in the HashMap and returns the relevant coin Object according to it.
	 * 
	 * @param coinName					a string representing the coin's name to convert to Coin type.				
	 * @return							a specific Coin Object.
	 */
	Coin stringToCoin(String coinName);
	/**
	 * Retrieves the file's date as a Date Object.
	 * 
	 * @return							the file's date as a Date Object.
	 */
	Date getFileDate();
}