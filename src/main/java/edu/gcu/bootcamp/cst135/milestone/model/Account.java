/**
 * Superclass for creating Checking, Saving, and Loan accounts
 * Future versions may declare this class abstract since it will not be instantiated
 */
package edu.gcu.bootcamp.cst135.milestone.model;

public class Account {

	//Class data
	private String accountNumber;
	private double accountBalance;

	//Getters and setters
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return accountNumber + " $" + accountBalance;
	}

	/**
	 * Constructor gets called when making child objects
	 * @param accountNumber the random account number
	 * @param accountBalance the opening account balance
	 */
	public Account(String accountNumber, double accountBalance) {

		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
	}	
}