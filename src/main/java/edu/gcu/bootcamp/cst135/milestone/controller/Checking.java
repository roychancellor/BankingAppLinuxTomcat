/**
 * Child class of Account that creates Checking account objects
 */
package edu.gcu.bootcamp.cst135.milestone.controller;

import edu.gcu.bootcamp.cst135.milestone.model.Account;

public class Checking extends Account {

	//Unique child class data and getters/setters
	private double overdraftFee;
	//Account fees and minimum balances
	public static final double OVERDRAFT_FEE = 25.0;
	
	public double getOverdraft() {
		return overdraftFee;
	}

	public void setOverdraft(double overdraft) {
		this.overdraftFee = overdraft;
	}

	/**
	 * Constructor for Checking objects. There is no default constructor...must use this one
	 * @param accountNumber account number
	 * @param accountBalance opening balance
	 * @param overdraftFee amount of overdraftFee fee assessed every time the user withdraws more than available
	 */
	public Checking(String accountNumber, double accountBalance, double overdraftFee) {
		//Call the superclass (Account) constructor
		super(accountNumber, accountBalance);
		//Unique to Checking objects
		this.overdraftFee = overdraftFee;
	}
	
	/**
	 * Implements processTransaction that was left abstract in the superclass
	 * unique to Checking accounts (logic for overdraftFee fee)
	 * @param transType a multiplier for withdrawals (-1) or deposits (+1)
	 * @param amount the amount to withdraw or deposit
	 */
	public void doTransaction(int transType, double amount) {
		double feeAmount = 0;
		//WITHDRAWAL: Determine if the account will be overdrawn; if so, alert the user and give choice to exit
		if(transType == Account.WITHDRAWAL && amount > getAccountBalance()) {
			System.out.println("A $" + getOverdraft() + " overdraftFee fee will be assessed if you continue. Continue Y or N?");
			//If the user chooses to continue, assess the overdraftFee fee; if not, return to the checking withdrawal screen
			if(Bank.scanner.nextLine().toLowerCase().equals("y")) {
				feeAmount = getOverdraft();
			}
			else {
				doTransaction(Account.WITHDRAWAL, getTransactionValue(Account.AMOUNT_MESSAGE + "withdraw: "));
			}
		}
		
		//Process the transaction
		setAccountBalance(getAccountBalance() + transType * (amount + feeAmount));
	}
}