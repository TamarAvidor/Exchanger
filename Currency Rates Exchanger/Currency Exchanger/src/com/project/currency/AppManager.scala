package com.project.currency

import javax.swing.JOptionPane
import java.util
import java.util.Date
import java.util.Collection
/*
 * Manages the application.
 * Downloads data, parses the data, and restoring the HashMap in a variable
 * executes money exchange commands
 * responsible for verifying the data is up to date (refreshed successfully)
 */
class AppManager extends IAppManager {
  var fileDownloader: TextFileDownloader = new TextFileDownloader()
  var xmlParser: XMLParser = new XMLParser()

  var sourceCoin: Coin = null
  var destinationCoin: Coin = null
  var amountToExchange: Double = 0

  var downloadedFileDate: Date = null //new Date(0, 0, 1) // 1/1/1900

  var fileDLSucceeded: Boolean = false
  var XMLParseSucceeded: Boolean = false
  var isRefreshSucceeded: Boolean = false

  var currenciesHashMap: util.HashMap[String, Coin] = new util.HashMap[String, Coin]() //HashMap data type  to contain all the coins objects
  var coinsCollection: util.Collection[Coin] = null //Intended to contain all the coins objects
  private var it: util.Iterator[Coin] = null //Intended to iterating over the coins collection

  def setSourceCoin(): Coin = {
    sourceCoin = currenciesHashMap.get(sourceCoin.getCurrencyName) //sourceCoin will now hold the actual Coin object needed as the source exchange
    sourceCoin
  }

  def setDestinationCoin(): Coin = {
    destinationCoin = currenciesHashMap.get(destinationCoin.getCurrencyName) //destinationCoin will now hold the actual Coin object needed as the destination exchange
    destinationCoin
  }

  override def isDataUpToDate(fileDate: Date, timeTriggerInHours: Int): Boolean =
    /**
     * This method will receive a Date which is the file's date, and a time in INT to represent the hours.
     * if the delta between the file's date and the current date (from computer) is larger than the triggered time sent to the method (measured by milliseconds)
     * the method will return false- as the data's date is not updated.
     */
    {
      var isDataUpToDate: Boolean = false
      var computerDate: Date = new Date() //by default getting the computers time

      var deltaTime: Long = computerDate.getTime() - fileDate.getTime()
      //getTime() returns the time in milliseconds since the epoch.
      //Therefore deltaTime holds the time difference between now and the last DataBase update

      if (deltaTime > timeTriggerInHours * 60 * 60 * 1000) { //4h in milliseconds
        isDataUpToDate = false
      } else {
        isDataUpToDate = true
      }
      isDataUpToDate
    }

  def refreshDataAndCheckForSuccess(): Boolean =
    /**
     * This method will return true only if both the the file is up to date & the parsing succeeded.
     * once those two condition are filled, method will then use XMLParser to receive the coins currencies.
     */
    {
      fileDLSucceeded = fileDownloader.updateDataFile()
      if (fileDLSucceeded == true) {
        XMLParseSucceeded = xmlParser.parseXMLFile()
        if (XMLParseSucceeded == true) {
          /*
       * if both the file download and the XML parsing succeeded,
       *  then we will get the currencies hash map that was created in the XMLParser class
       */
          isRefreshSucceeded = true

          downloadedFileDate = new Date()  // getting the computers time stamp, which will be used to mark when was the last file downloaded
          //downloadedFileDate = xmlParser.getDateStamp() // getting the file's inner string date stamp
          currenciesHashMap = xmlParser.getCurrenciesHashMap
          coinsCollection = xmlParser.getCoinsCollection
        } else {
          /*
       * error message dialog for XML not parsing successfully
       */
          JOptionPane.showMessageDialog(null, "parsing failed!\n", "null pointer Exception", JOptionPane.ERROR_MESSAGE)
        }
      } else {
        /*
     * error message dialog for file not downloaded successfully
     */
        JOptionPane.showMessageDialog(null, "There is no internet connection!\n"
          + "Please ensure that you are connected to the internet", "Unknown Host Exception", JOptionPane.ERROR_MESSAGE)
      }
      isRefreshSucceeded
    }

  override def getCurrenciesArray(): util.ArrayList[String] = {
    val coinsArray = new util.ArrayList[String]()
    it = coinsCollection.iterator()
    while (it.hasNext) {
      val iterator = it.next()
      coinsArray.add(iterator.getCurrencyName() + " " + iterator.getCurrencyCountryName)
    }
    coinsArray
  }

  def stringToCoin(coinName: String): Coin =
    /*This method gets a string which represent coin's key in the HashMap and
      returns the relevant coin Object according to it.*/
    {
      currenciesHashMap.get(coinName)
    }

  def getFileDate(): Date = {
    downloadedFileDate
  }
}