package com.project.currency

import reflect.io._

import java.net._
import scala.xml._
import scala.io._ //Files
import java.io.{ IOException }

/*
 * This Class goal is to download a text source from a URL address,
 * and save it as an XML file.
 */
class TextFileDownloader {
  private val sourceUrl = new URL("http://www.boi.org.il/currency.xml")

   def updateDataFile(): Boolean = {
    var fileDLSucceeded = updateDataFileCore("Data/CurrencyData.xml")
    if(fileDLSucceeded == true)
    {
      Program.logger.debug(this.toString() + ": XML File downladed ");
    }
    else
    {
      Program.logger.error(this.toString + ":ERROR_MESSAGE:There is no internet connection!"
            + "Please ensure that you are connected to the internet ")
    }
    fileDLSucceeded
  }
  
  def updateDataFileCore(pathStr: String): Boolean = {
    var fileDLSucceeded = true
    try {
      val connection = sourceUrl.openConnection()
      var xmlContent: scala.xml.Elem = null
      xmlContent = XML.load(connection.getInputStream())
      var tempFile = File(pathStr) //overwriting last file by default
      tempFile.writeAll(xmlContent.toString())

    } catch {
      case ex: IOException =>
        {
          fileDLSucceeded = false //!!! If the exception was thrown this parameter change to false
        }
    }
    fileDLSucceeded
  }
}