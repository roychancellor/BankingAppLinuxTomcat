package edu.gcu.cst341.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.gcu.cst341.interfaces.iBankActions;
import edu.gcu.cst341.interfaces.iTrans;

/**
 * Superclass for creating Checking, Saving, and Loan accounts
 * Future versions may declare this class abstract since it will not be instantiated
 */
public abstract class Account implements iBankActions, iTrans {
	
	//Class data
	private String accountNumber;
	private double accountBalance;
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

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the accountBalance
	 */
	public double getAccountBalance() {
		return accountBalance;
	}

	/**
	 * @param accountBalance the accountBalance to set
	 */
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	/**
	 * @return the transList
	 */
	public List<Transaction> getTransList() {
		return transList;
	}

	/**
	 * @param transList the transList to set
	 */
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
		return "Account [accountNumber=" + accountNumber + ", accountBalance=" + accountBalance + ", transList="
				+ transList + ", lastTrans=" + lastTrans + "]";
	}

	/**
	 * Leave abstract so the subclasses (Checking, Saving, Loan) can implement
	 * unique to those account types
	 */
	public abstract void doTransaction(final int transType, double amount);

	/**
	 * Implements the iTrans interface: addTransaction method
	 * Adds a transaction to the list of transactions and sets the lastTrans field
	 * @param amount is the dollar amount of the transaction
	 * @param transType is a description of the type of transaction
	 */
	public void addTransaction(double amount, String transType) {
		this.transList.add(new Transaction(new Date(), this.accountNumber, amount, transType));
		setLastTrans(new Transaction(new Date(), this.accountNumber, amount, transType));
	}
}