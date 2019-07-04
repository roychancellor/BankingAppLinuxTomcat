package edu.gcu.bootcamp.cst135.milestone.controller;

import edu.gcu.bootcamp.cst135.milestone.model.Account;

public class Loan extends Account {

	//Class data
	private double interestRate;
	private double lateFee;
	private int termYears;
	private double monthlyPayment;
	private double principal;
	public static final double LATE_FEE = 25.0;
	public static final double INTEREST_RATE = 0.08;
	
	//Constructor
	public Loan(String accountNumber, double principal, double lateFee, double annualInterestRate, int termYears) {
		//Call the superclass (Account) constructor
		super(accountNumber, principal);
		
		//Unique to Loan objects
		this.lateFee = lateFee;
		
		//MONTHLY interest rate (no setter provided, so interest rate is immutable for Loan objects
		this.interestRate = annualInterestRate / 12;
		
		this.termYears = termYears;
		this.principal = principal;
		this.monthlyPayment = computeMonthlyPayment();
	}

	/**
	 * @return the monthly interestRate
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

	/**
	 * @return the termYears
	 */
	public int getTermYears() {
		return termYears;
	}

	/**
	 * @param termYears the termYears to set
	 */
	public void setTermYears(int termYears) {
		this.termYears = termYears;
	}

	/**
	 * @return the principal
	 */
	public double getPrincipal() {
		return principal;
	}

	/**
	 * @return the monthlyPayment
	 */
	public double getMonthlyPayment() {
		return monthlyPayment;
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
	/**
	 * Overloads doTransaction to receive only the amount because the only loan transactions are deposits
	 * @param amount dollar amount of payment on the loan
	 */
	public void doTransaction(double amount) {
			//Loan balances are negative amounts, so ADD the loan payment to make it less negative
			this.doTransaction(Account.DEPOSIT, amount);
	}

	/**
	 * Computes the monthly payment of a loan based on principal, term, and annual interest rate
	 */
	private double computeMonthlyPayment() {
		return (-this.interestRate * this.principal / (1 - Math.pow(1 + this.interestRate, -this.termYears * 12)));
	}
}
