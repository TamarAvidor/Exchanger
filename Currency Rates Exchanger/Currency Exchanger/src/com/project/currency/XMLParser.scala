package com.project.currency

import scala.collection.mutable._
import scala.io._
import java.util
import scala.xml._
import java.util.Date

/*
 * This Class goal is to parse the XML data into a HashMap
 * Get the relevant date of the data and process it to a string type
 */
class XMLParser {
  var parsingSucceeded = true
  var currenciesMap = new util.HashMap[String, Coin]() //HashMap data type to contain all the coins objects
  var coinsCollection: util.Collection[Coin] = null //Intended to contain all the coins objects
  var coinIterator: Iterator[Coin] = null //Intended to iterating over the coins collection
  var XMLDataElement: scala.xml.Elem = null

  def loadXMLFile(destinationDir: String): Unit = {
    val stringData = scala.io.Source.fromFile(destinationDir).mkString
    //maybe check if a file exists
    XMLDataElement = XML.loadString(stringData)
  }

  def parseXMLFile(): Boolean =
    /*This method will handle the XML parsing to HashMap collection
   * and will return true if operation succeeded 
   * */
    {
      parsingSucceeded = parseXMLFileCore("Data/CurrencyData.xml")
      
      if(parsingSucceeded == true)
      {
        Program.logger.debug(this.toString() + ": XML File was parsed ");
      }
      else
      {
        parsingSucceeded = false //!!! If the exception was thrown this parameter change to false
        Program.logger.error(this.toString + ":ERROR_MESSAGE: null pointer exception, XML file could not be parsed")
      }
      
      parsingSucceeded
   }
  
  def parseXMLFileCore(pathStr: String): Boolean =
    /*This method will handle the XML parsing to HashMap collection
   * and will return true if operation succeeded 
   * */
    {
      loadXMLFile(pathStr)

      try {
        for (ob <- (XMLDataElement \\ "CURRENCY")) {
          val newCoin = new Coin((ob \\ "NAME").text, (ob \\ ("COUNTRY")).text, ((ob \\ "RATE").text).toDouble, ((ob \\ "UNIT").text).toInt)
          currenciesMap.put((ob \\ "NAME").text + " " + (ob \\ ("COUNTRY")).text, newCoin)
        }

        currenciesMap.put("NIS " + "Israel", new Coin("NIS", "Israel", 1.0))
        //addition of a specific currency(NIS-Israel- not appearing in the XML)
        coinsCollection = currenciesMap.values()
        parsingSucceeded

      } catch {
        case ex: NullPointerException =>
          {
            parsingSucceeded = false //!!! If the exception was thrown this parameter change to false
            parsingSucceeded
          }
      }
      parsingSucceeded
   }

  def getDateStamp(): Date = {
    var ob: scala.xml.NodeSeq = XMLDataElement \\ "LAST_UPDATE"
    var obToString = ob.toString()
    XMLTagStringToDate(obToString)
  }

  def XMLTagStringToDate(XMLTagString: String): Date = {
    var XMLTagDataString: String = XMLTagString

    XMLTagDataString = XMLTagDataString.dropRight(14) //drops 14 last chars
    XMLTagDataString = XMLTagDataString.drop(13) //drops 13 first chars

    var tempString: String = XMLTagDataString.dropRight(6)
    var year: Int = tempString.toInt
    tempString = XMLTagDataString.drop(8)
    var day: Int = tempString.toInt

    XMLTagDataString = XMLTagDataString.dropRight(3)
    tempString = XMLTagDataString.drop(5)
    var month: Int = tempString.toInt

    var tagDate: Date = new Date(year - 1900, month - 1, day)

    tagDate
  }

  def getCoinsCollection: util.Collection[Coin] =
    /*This method returns the coin collection */
    {
      coinsCollection
    }

  def getCurrenciesHashMap: util.HashMap[String, Coin] =
    /*This method returns the coins HashMap */
    {
      currenciesMap
    }
}