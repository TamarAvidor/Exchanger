package com.project.currency

class Coin(inputCurrencyName: String, inputCountryName: String, inputCurrencyRate: Double, inputUnit: Int = 1) {

  private var currencyName: String = inputCurrencyName
  private var currencyRate: Double = inputCurrencyRate
  private var currencyCountryName: String = inputCountryName
  private var currencyUnit: Int = inputUnit

  def setCurrencyName(i_currencyName: String) {
    this.currencyName = i_currencyName
  }

  def getCurrencyName(): String = {
    currencyName
  }

  def setCurrencyRate(i_currencyRate: Double) {
    this.currencyRate = i_currencyRate
  }

  def getCurrencyRate(): Double = {
    currencyRate
  }

  def setCurrencyCountryName(i_countryName: String) {
    this.currencyCountryName = i_countryName
  }

  def getCurrencyCountryName(): String = {
    currencyCountryName
  }

  def setCurrencyUnit(i_currencyUnit: Int) {
    this.currencyUnit = i_currencyUnit
  }

  def getCurrencyUnit(): Int = {
    currencyUnit
  }
}