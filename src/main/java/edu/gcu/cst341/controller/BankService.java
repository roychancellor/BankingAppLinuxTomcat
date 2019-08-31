package edu.gcu.cst341.controller;

import java.text.DecimalFormat;

import org.springframework.stereotype.Service;

import edu.gcu.cst341.model.Customer;

/**
 * Bank is the primary class for the banking application, housing all methods to process user input
 * The actual account computations occur in the account classes Checking, Saving, and Loan
 */
@Service
public class BankService {

	//Format for dates and money outputs in all classes
	public static DecimalFormat money = new DecimalFormat();
	public static final String MONEY_FORMAT = "$#,##0.00;($#,##0.00)";

	//Class methods
	private void setMoneyFormat() {
		money.applyPattern(MONEY_FORMAT);
	}
	
	public void executeTransaction(Customer customer, String accountType, int transType, double amount) {
		//Do the deposit and update the dashboard values
		DataService ds = new DataService();
		boolean dbSuccess = false;
		switch(accountType) {
			case "chk":
				//Update the model
				customer.getChecking().doTransaction(transType, amount);
				//Write to the database
				dbSuccess = ds.dbUpdateBalanceAndTransaction(customer.getCustId(), customer.getChecking());
				if(!dbSuccess) {
					System.err.println("ERROR!!! Unable to write transaction to DB");
				}
				break;
			case "sav":
				//Update the model
				customer.getSaving().doTransaction(transType, amount);
				//Write to the database
				dbSuccess = ds.dbUpdateBalanceAndTransaction(customer.getCustId(), customer.getSaving());
				if(!dbSuccess) {
					System.err.println("ERROR!!! Unable to write transaction to DB");
				}
				break;
			case "loan":
				//Update the model
				customer.getLoan().doTransaction(transType, amount);
				//Write to the database
				dbSuccess = ds.dbUpdateBalanceAndTransaction(customer.getCustId(), customer.getLoan());
				if(!dbSuccess) {
					System.err.println("ERROR!!! Unable to write transaction to DB");
				}
				break;
			default:
		}
		ds.close();
		System.out.println("/deposit-bank POST: the database result is " + dbSuccess);
	}
	
}