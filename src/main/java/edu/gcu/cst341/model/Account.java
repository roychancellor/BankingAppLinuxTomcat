package edu.gcu.cst341.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.gcu.cst341.controller.Bank;
import edu.gcu.cst341.interfaces.iActions;
import edu.gcu.cst341.interfaces.iTrans;
import edu.gcu.cst341.view.Menus;

/**
 * Superclass for creating Checking, Saving, and Loan accounts
 * Future versions may declare this class abstract since it will not be instantiated
 */
public abstract class Account implements iActions, iTrans {
	
	//Class data
	private String accountNumber;
	private double accountBalance;
	public static final String AMOUNT_MESSAGE = "Enter dollar amount you would like to ";
	private List<Transaction> transList = new ArrayList<Transaction>();
	private Transaction lastTrans;

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

	public List<Transaction> getTransList() {
		return transList;
	}

	public void setTransList(List<Transaction> transList) {
		this.transList = transList;
	}

	/**
	 * @return the lastTrans
	 */
	public Transaction getLastTrans() {
		return lastTrans;
	}

	/**
	 * @param lastTrans the lastTrans to set
	 */
	public void setLastTrans(Transaction lastTrans) {
		this.lastTrans = lastTrans;
	}

	@Override
	public String toString() {
		return accountNumber + "\t" + Bank.money.format(accountBalance);
	}

	/**
	 * Implements iActions interface
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
				value = Menus.scan.nextDouble();
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
				Menus.scan.nextLine();				
			}
		} while(!isValid);

		return value;
	}
	
	/**
	 * Leave abstract so the subclasses (Checking, Saving, Loan) can implement
	 * unique to those account types
	 */
	public abstract void doTransaction(final int transType, double amount);

	/**
	 * Implements the iTrans interface: addTransaction method
	 * @param amount is the dollar amount of the transaction
	 * @param transType is a description of the type of transaction
	 */
	public void addTransaction(double amount, String transType) {
		this.transList.add(new Transaction(new Date(), this.accountNumber, amount, transType));
		this.lastTrans = new Transaction(new Date(), this.accountNumber, amount, transType);
	}

	/**
	 * Implements the iTrans interface: displayTransaction method
	 */
	public void displayTransactions() {
		for(Transaction t : transList) {
			System.out.println(t.toString());
		}
		Menus.printHeaderLine(75);
		System.out.println("\t\t\tEnd balance:\t" + String.format("$%(,12.2f", getAccountBalance()) + "\n");
	}
}