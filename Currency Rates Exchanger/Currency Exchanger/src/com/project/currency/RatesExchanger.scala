package com.project.currency
/*
 * This Class goal is to calculate the rate from the source to destination coin
 */
class RatesExchanger extends IExchanger {
  override def moneyExchange(sourceCoin: Double, sourceAmount: Double, destinationCoin: Double): Double =
    /*This method gets two strings that represent coins and sum to exchange
      and returns their value after the convert. */
    {
      val calculationToReturn = ((sourceCoin * sourceAmount) / destinationCoin)
      calculationToReturn
    }
}