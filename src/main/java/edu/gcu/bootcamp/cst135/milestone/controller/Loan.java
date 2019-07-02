package edu.gcu.bootcamp.cst135.milestone.controller;

import edu.gcu.bootcamp.cst135.milestone.model.Account;

public class Loan extends Account {

	//Class data
	private double interestRate;
	private double lateFee;
	public static final double LATE_FEE = 25.0;
	public static final double INTEREST_RATE = 0.08;
	
	//Constructor
	public Loan(String accountNumber, double principal, double lateFee, double interestRate) {
		//Call the superclass (Account) constructor
		super(accountNumber, principal);
		
		//Unique to Loan objects
		this.lateFee = lateFee;
		
		//MONTHLY interest rate (no setter provided, so interest rate is immutable for Loan objects
		this.interestRate = interestRate / 12;
	}

	/**
	 * @return the interestRate
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * @return the lateFee
	 */
	public double getLateFee() {
		return lateFee;
	}

	/**
	 * @param lateFee the lateFee to set
	 */
	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

	@Override
	/**
	 * @param transType a value that is -1 for withdrawals and +1 for deposits
	 * @param amount the dollar amount of the transaction
	 */
	public void doTransaction(int transType, double amount) {
		//Can ONLY make loan payments ("deposits")
		if(transType == Account.DEPOSIT) {
			//Loan balances are negative amounts, so ADD the loan payment to make it less negative
			setAccountBalance(getAccountBalance() + amount);
		}
	}

}
