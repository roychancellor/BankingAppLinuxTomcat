package edu.gcu.bootcamp.cst135.milestone.model;

import java.util.Date;

import edu.gcu.bootcamp.cst135.milestone.controller.Bank;

public class Transaction {
	//Class data
	private Date transactionDate;
	private String accountNumber;
	private double amount;
	private String transactionType;
	private static final String DELIM = "\t";
	
	//Constructors
	public Transaction() {
		
	}
	
	/**
	 * @param transactionDate
	 * @param accountNumber
	 * @param amount
	 * @param transactionType
	 */
	public Transaction(Date transactionDate, String accountNumber, double amount, String transactionType) {
		this.transactionDate = transactionDate;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.transactionType = transactionType;
	}

	//Accessors and mutators
	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountType() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountType(String accountType) {
		this.accountNumber = accountType;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String toString() {
		return Bank.dateFormat.format(this.transactionDate)
			+ DELIM + this.accountNumber
			+ DELIM + String.format("$%(,12.2f", this.amount)
			+ DELIM + this.transactionType; 
	}	
}
