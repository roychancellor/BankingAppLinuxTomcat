package edu.gcu.bootcamp.cst135.milestone.model;

public interface iActions {
	/**
	 * DEPOSIT and WITHDRAW are provided as descriptors for transType in processTransaction
	 * and can be used as multipliers depending on the transaction type
	 */
	public static final int DEPOSIT = 1;
	public static final int WITHDRAWAL = -1;
	
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
	public abstract void doTransaction(final int transType, double amount);
}
