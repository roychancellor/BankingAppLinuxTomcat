/**
 * Child class of Account that creates Saving account objects
 */
package edu.gcu.bootcamp.cst135.milestone.controller;

import edu.gcu.bootcamp.cst135.milestone.model.Account;

public class Saving extends Account {

	//Unique child class data and getters/setters
	private double minBalance;
	private double interest;
	private double serviceFee;
	
	public double getMinBalance() {
		return minBalance;
	}

	public void setMinBalance(double minBalance) {
		this.minBalance = minBalance;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest/12;
	}

	public double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

	/**
	 * Constructor for Saving objects. There is no default constructor...must use this one	 * 
	 * @param accountNum the account number
	 * @param balance the beginning balance
	 * @param minBalance minimum balance required to not get a monthly service fee
	 * @param serviceFee service fee amount (assessed if the balance drops below the minimum)
	 * @param interest annual interest rate
	 */
	public Saving(String accountNum, double balance, double minBalance, double serviceFee , double interest) {
		//Call the superclass (Account) constructor
		super(accountNum, balance);
		
		//Unique to Saving objects
		this.minBalance = minBalance;
		this.serviceFee = serviceFee;
		
		//MONTHLY interest rate
		this.interest = interest / 12;		
	}
	
	/**
	 * Implements processTransaction that was left abstract in the superclass
	 * unique to Saving accounts (logic for withdraw > available balance)
	 * @param transType a multiplier for withdrawals (-1) or deposits (+1)
	 * @param amount the amount to withdraw or deposit
	 */
	public void doTransaction(final int transType, double amount) {
		//WITHDRAWAL: Determine if the account will be overdrawn; if so, alert the user and try again
		while(transType == Account.WITHDRAWAL && amount > getAccountBalance()) {
			System.out.println(
				"\n\t"
				+ amount
				+ " is greater than your balance of "
				+ getAccountBalance()
				+ ". Enter a new value or 0 to void transaction.\n"
			);			
			amount = getTransactionValue(Account.AMOUNT_MESSAGE + "withdraw: ");
		}
		//Process the transaction
		setAccountBalance(getAccountBalance() + transType * amount);
	}
}