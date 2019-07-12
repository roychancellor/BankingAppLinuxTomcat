/**
 * 
 */
package edu.gcu.bootcamp.cst135.milestone.model;

/**
 * method declarations for logging banking transactions
 * @author roy
 *
 */
public interface iTrans {
	public void addTransaction(double amount, String transType);
	public void displayTransactions();
	public void doEndOfMonth();
	public boolean isFeeRequired();
}
