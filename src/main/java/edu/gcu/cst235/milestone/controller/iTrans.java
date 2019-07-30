/**
 * 
 */
package edu.gcu.cst235.milestone.controller;

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
