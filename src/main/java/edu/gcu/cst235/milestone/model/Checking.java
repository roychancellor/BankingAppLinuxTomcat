package edu.gcu.cst235.milestone.model;

import edu.gcu.cst235.milestone.controller.Bank;
import edu.gcu.cst235.milestone.view.Menus;

/**
 * Child class of Account that creates Checking account objects
 */
public class Checking extends Account {

	//Unique child class data and getters/setters
	private double overdraftFee;
	//Account fees and minimum balances
	public static final double OVERDRAFT_FEE = 25.0;
	
	public double getOverdraftFee() {
		return overdraftFee;
	}

	public void setOverdraftFee(double overdraftFee) {
		this.overdraftFee = overdraftFee;
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
			System.out.println("An overdraft fee of " 
				+ Bank.money.format(getOverdraftFee())
				+ " will be assessed if you continue. Continue Y or N?");
			//If the user chooses to continue, assess the overdraftFee fee; if not, return to the checking withdrawal screen
			if(Menus.scan.nextLine().toLowerCase().equals("y")) {
				feeAmount = getOverdraftFee();
				this.addTransaction(feeAmount, "Overdraft fee");
			}
			else {  //the user chose not to continue with the overdraft fee, so ask for a new amount to withdraw
				doTransaction(Account.WITHDRAWAL, getTransactionValue(Account.AMOUNT_MESSAGE + "withdraw: "));
			}
		}
		
		//Once validated, process the transaction
		setAccountBalance(getAccountBalance() + transType * (amount + feeAmount));
		//Record the transaction
		if(transType == Account.WITHDRAWAL)
			this.addTransaction(-amount, "Withdrawal");
		if(transType == Account.DEPOSIT)
			this.addTransaction(amount, "Deposit");
	}
	
	/**
	 * Implements the iTrans interface: doEndOfMonth method
	 */
	public void doEndOfMonth() {
		//Future functionality for Checking objects
	}
	
	/**
	 * Implements the iTrans interface: checkLateFee method
	 */
	public boolean isFeeRequired() {
		return false;
	}

}