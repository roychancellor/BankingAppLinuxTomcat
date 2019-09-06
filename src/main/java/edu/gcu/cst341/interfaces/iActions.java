package edu.gcu.cst341.interfaces;

/**
 * Interface containing methods for banking actions
 */
public interface iActions {
	/**
	 * These constants are provided as descriptors for transType in processTransaction
	 * DEPOSIT & WITHDRAWAL should be different values from TRANSFER_D & TRANSFER_W
	 */
	public static final int DEPOSIT = 1;
	public static final int WITHDRAWAL = -1;
	public static final int TRANSFER_D = 2;
	public static final int TRANSFER_W = -2;
	public static final String TRANSFER_NOTE = " (transfer)";
	
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
