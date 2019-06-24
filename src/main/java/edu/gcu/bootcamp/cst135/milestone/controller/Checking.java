package edu.gcu.bootcamp.cst135.milestone.controller;

import edu.gcu.bootcamp.cst135.milestone.model.Account;

public class Checking extends Account {

	private double overdraft;
	
	public double getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(double overdraft) {
		this.overdraft = overdraft;
	}

	public Checking(String accountNumber, double accountBalance, double overdraft) {
		super(accountNumber, accountBalance);
		this.overdraft = overdraft;
	}
}