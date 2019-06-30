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
		this.interest = interest/12;		
	}
	
	/**
	 * Implements processTransaction that was left abstract in the superclass
	 * unique to Saving accounts
	 */
	public void processTransaction(final int transType, double amount) {
		System.out.println("in processTransaction");
	}
}