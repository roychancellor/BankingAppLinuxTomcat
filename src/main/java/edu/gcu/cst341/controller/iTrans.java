package edu.gcu.cst341.controller;

/**
 * Interface of method declarations for logging banking transactions
 */
public interface iTrans {
	public void addTransaction(double amount, String transType);
	public void displayTransactions();
	public void doEndOfMonth();
	public boolean isFeeRequired();
}
