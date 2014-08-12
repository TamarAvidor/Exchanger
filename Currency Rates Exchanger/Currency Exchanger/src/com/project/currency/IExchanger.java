package com.project.currency;

/**
 * The IExchanger interface is responsible to declare the method to calculate the different coins rates.
 */
public interface IExchanger {
	/**
	 * Iterating the HashMap and returns ArrayList of Strings that will be shown at the GUI. 
	 * Each element in the ArrayList includes the coin name, country name and rate of currency.
	 * 
	 * @param sourceCoin					the rate of the source coin to be converted.
	 * @param currencyAmount				the amount to convert from source to destination coin.
	 * @param destinationCoin				the rate of the destination coin that was converted.
	 * @return								the value of the destination coin after calculations.
	 */
	double moneyExchange(double sourceCoin, double currencyAmount,
			double destinationCoin);
}
