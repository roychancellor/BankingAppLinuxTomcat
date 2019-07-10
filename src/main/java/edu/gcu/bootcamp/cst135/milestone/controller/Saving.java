/**
 * Child class of Account that creates Saving account objects
 */
package edu.gcu.bootcamp.cst135.milestone.controller;

import edu.gcu.bootcamp.cst135.milestone.model.Account;

public class Saving extends Account {

	//Unique child class data and getters/setters
	private double minBalance;
	private double interestRate;
	private double serviceFee;
	public static final double MINIMUM_BALANCE = 200.0;
	public static final double ANNUAL_INTEREST_RATE = 0.06;
	public static final double BELOW_MIN_BALANCE_FEE = 40.0;
	
	/**
	 * Constructor for Saving objects. There is no default constructor...must use this one	 * 
	 * @param accountNum the account number
	 * @param balance the beginning balance
	 * @param minBalance minimum balance required to not get a monthly service fee
	 * @param serviceFee service fee amount (assessed if the balance drops below the minimum)
	 * @param interestRate annual interestRate rate
	 */
	public Saving(String accountNum, double balance, double minBalance, double serviceFee , double interestRate) {
		//Call the superclass (Account) constructor
		super(accountNum, balance);
		
		//Unique to Saving objects
		this.minBalance = minBalance;
		this.serviceFee = serviceFee;
		
		//MONTHLY interestRate rate
		this.interestRate = interestRate / 12;		
	}

	//Getters and setters for fields unique to Saving class
	public double getMinBalance() {
		return minBalance;
	}

	public void setMinBalance(double minBalance) {
		this.minBalance = minBalance;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate / 12;
	}

	public double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

	/**
	 * Implements processTransaction that was left abstract in the superclass
	 * unique to Saving accounts (logic for withdraw greater than available balance)
	 * @param transType a multiplier for withdrawals (-1) or deposits (+1)
	 * @param amount the amount to withdraw or deposit
	 */
	public void doTransaction(final int transType, double amount) {
		//WITHDRAWAL: Determine if the account will be overdrawn; if so, alert the user and try again
		while(transType == Account.WITHDRAWAL && amount > getAccountBalance()) {
			System.out.println(
				"\n\t"
				+ Bank.money.format(amount)
				+ " is greater than your balance of "
				+ Bank.money.format(getAccountBalance())
				+ ". Enter a new value or 0 to void transaction.\n"
			);			
			amount = getTransactionValue(Account.AMOUNT_MESSAGE + "withdraw: ");
		}
		//Process the transaction
		setAccountBalance(getAccountBalance() + transType * amount);
	}
}