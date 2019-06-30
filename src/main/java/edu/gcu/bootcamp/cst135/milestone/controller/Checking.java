/**
 * Child class of Account that creates Checking account objects
 */
package edu.gcu.bootcamp.cst135.milestone.controller;

import edu.gcu.bootcamp.cst135.milestone.model.Account;

public class Checking extends Account {

	//Unique child class data and getters/setters
	private double overdraft;
	
	public double getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(double overdraft) {
		this.overdraft = overdraft;
	}

	/**
	 * Constructor for Checking objects. There is no default constructor...must use this one
	 * @param accountNumber account number
	 * @param accountBalance opening balance
	 * @param overdraft amount of overdraft fee assessed every time the user withdraws more than available
	 */
	public Checking(String accountNumber, double accountBalance, double overdraft) {
		//Call the superclass (Account) constructor
		super(accountNumber, accountBalance);
		//Unique to Checking objects
		this.overdraft = overdraft;
	}
}