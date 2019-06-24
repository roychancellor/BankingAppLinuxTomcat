package edu.gcu.bootcamp.cst135.milestone.controller;

import edu.gcu.bootcamp.cst135.milestone.model.Account;

public class Saving extends Account {

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

	public Saving(String accountNum, double balance, double minBalance, double serviceFee , double interest) {
		
		super(accountNum, balance);
		this.minBalance = minBalance;
		this.serviceFee = serviceFee;
		this.interest = interest/12;		
	}
}