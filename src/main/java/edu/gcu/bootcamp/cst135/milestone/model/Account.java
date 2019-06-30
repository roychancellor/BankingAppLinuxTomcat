/**
 * Superclass for creating Checking, Saving, and Loan accounts
 * Future versions may declare this class abstract since it will not be instantiated
 */
package edu.gcu.bootcamp.cst135.milestone.model;
import edu.gcu.bootcamp.cst135.milestone.model.iActions;

public abstract class Account implements iActions {

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
	
	/**
	 * Implements iAction interface
	 * @param message
	 * @return double value representing a dollar amount
	 */
	public double getTransactionValue(String message) {
		System.out.println("Int getTransactionValue");
		return 1;
		//This method will throw an exception for invalid input and call itself over and over and over until
		//the user gets it right, at which point it returns the user's value
		//Validate that input is positive or zero
	}
	
	/**
	 * Leave abstract so the subclasses (Checking, Saving, Loan) can implement
	 * unique to those account types
	 */
	public abstract void processTransaction(final int transType, double amount);
}