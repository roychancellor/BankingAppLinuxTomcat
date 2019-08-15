package edu.gcu.cst235.milestone.controller;

/**
 * Interface containing methods for banking actions
 */
public interface iActions {
	/**
	 * DEPOSIT and WITHDRAW are provided as descriptors for transType in processTransaction
	 * and can be used as multipliers depending on the transaction type
	 */
	public static final int DEPOSIT = 1;
	public static final int WITHDRAWAL = -1;
	
	/**
	 * interface method for getting a double value from a Scanner
	 * @param message is a string prompt for the user
	 * @return double value representing the transaction amount
	 */
	public abstract double getTransactionValue(String message);
	/**
	 * interface method for processing a transaction as a deposit (or loan payment) or withdrawal
	 * @param transType multiplier so doTransaction can process deposits and withdrawals (-1 for withdrawal or +1 for deposit)
	 * @param amount the amount of the attempted transaction
	 */
	public abstract void doTransaction(final int transType, double amount);
}
