package edu.gcu.cst341.interfaces;

/**
 * Interface of method declarations for logging banking transactions
 */
public interface iTrans {
	/**
	 * Method to add a transaction to the transaction list
	 * @param amount the amount of the transaction
	 * @param transType the type (description) of the transaction
	 */
	public void addTransaction(double amount, String transType);
	/**
	 * Method to do the end of month close for an account
	 */
	public void doEndOfMonth();
	
	/**
	 * Method to determine if a fee is required for the account based on account type
	 * @return true if a fee is required or false otherwise
	 */
	public boolean isFeeRequired();
}
