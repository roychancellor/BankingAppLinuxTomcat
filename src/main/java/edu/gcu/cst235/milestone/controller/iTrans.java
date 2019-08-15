package edu.gcu.cst235.milestone.controller;

/**
 * Interfac of method declarations for logging banking transactions
 */
public interface iTrans {
	public void addTransaction(double amount, String transType);
	public void displayTransactions();
	public void doEndOfMonth();
	public boolean isFeeRequired();
}
