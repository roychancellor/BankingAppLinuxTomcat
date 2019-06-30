package edu.gcu.bootcamp.cst135.milestone.model;

public interface iActions {
	/**
	 * interface method for getting a double value from a Scanner
	 * @param message
	 * @return
	 */
	public abstract double getTransactionValue(String message);
	/**
	 * interface method for processing a transaction as a deposit (or loan payment) or withdrawal
	 * @param transType
	 * @param amount
	 */
	public abstract void processTransaction(final int transType, double amount);
}
