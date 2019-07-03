/**
 * Superclass for creating Checking, Saving, and Loan accounts
 * Future versions may declare this class abstract since it will not be instantiated
 */
package edu.gcu.bootcamp.cst135.milestone.model;

import edu.gcu.bootcamp.cst135.milestone.model.iActions;
import edu.gcu.bootcamp.cst135.milestone.controller.Bank;

public abstract class Account implements iActions {
	
	//Class Constants
	//Multipliers for a common doTransaction method
	public static final int DEPOSIT = 1;
	public static final int WITHDRAW = -1;

	//Class data
	private String accountNumber;
	private double accountBalance;
	public static final String AMOUNT_MESSAGE = "Enter dollar amount you would like to ";

	/**
	 * Constructor gets called when making child objects
	 * @param accountNumber random account number
	 * @param accountBalance opening account balance
	 */
	public Account(String accountNumber, double accountBalance) {

		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
	}
	
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
		return accountNumber + "\t$" + accountBalance;
	}

	/**
	 * Implements iAction interface
	 * This method will throw an exception for invalid input and call itself over and over and over until
	 * the user gets it right, at which point it returns the user's value.
	 * The method will also validate that input is positive or zero.
	 * @param message is a string prompt for the user
	 * @return double value representing a dollar amount
	 */
	public double getTransactionValue(String message) {
		double value = 0;
		boolean isValid;
		String invalidMessage = "Invalid input: Enter a positive number such as 123.45\n";
		do {
			isValid = true;
			try {
				System.out.println(message);
				value = Bank.scanner.nextDouble();
				if(value < 0) {
					System.out.println(invalidMessage);
					isValid = false;
				}
			}
			catch(Exception e) {
				System.out.println(invalidMessage);
				isValid = false;
			}
			finally {
				//value is now validated to be a double >= 0
				//so, read the newline token that is remaining
				Bank.scanner.nextLine();				
			}
		} while(!isValid);

		return value;
	}
	
	/**
	 * Leave abstract so the subclasses (Checking, Saving, Loan) can implement
	 * unique to those account types
	 */
	public abstract void doTransaction(final int transType, double amount);
}