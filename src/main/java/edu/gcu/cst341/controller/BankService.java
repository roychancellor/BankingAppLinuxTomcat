package edu.gcu.cst341.controller;

import java.text.DecimalFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import edu.gcu.cst341.model.Account;
import edu.gcu.cst341.model.Customer;
import edu.gcu.cst341.model.Loan;
import edu.gcu.cst341.model.Transaction;

/**
 * BankService provides methods and business logic for transactions
 * The actual account computations occur in the account classes Checking, Saving, and Loan
 */
@Service
public class BankService {

	//Format for dates and money outputs in all classes
	private DecimalFormat money = new DecimalFormat();
	private static final String MONEY_FORMAT = "$#,##0.00;($#,##0.00)";

	//Constructor
	public BankService() {
		money.applyPattern(MONEY_FORMAT);
	}
	
	//Getters and setters
	/**
	 * @return the money
	 */
	public DecimalFormat getMoney() {
		return money;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(DecimalFormat money) {
		this.money = money;
	}

	//Class methods
	
	/**
	 * Executes a deposit or withdrawal from any of the three account types
	 * (Checking, Saving, or Loan)
	 * @param cust the current Customer object
	 * @param accountType the type of account for the transaction ("chk, "sav", or "loan")
	 * @param transType the type of transaction (+1 = deposit, -1 = withdrawal, 0 = transfer)
	 * @param amount the dollar amount of the transaction
	 */
	public int executeTransaction(Customer cust, String accountType, int transType, double amount) {
		//Get the account based on accountType (chk, sav, or loan)
		Account account = null;
		switch(accountType) {
		case "chk":
			account = cust.getChecking();
			break;
		case "sav":
			account = cust.getSaving();
			break;
		case "loan":
			account = cust.getLoan();
			break;
		}

		//Do the transaction
		DataService ds = new DataService();
		int numRec = 0;
		
		//Update the current Customer model object
		//Transact based on the account type (Polymorphism)
		account.doTransaction(transType, amount);
		System.out.println("\t***executeTransaction: after doTransaction,\n" + account.getClass() + "\nbalance = "
			+ account.getAccountBalance());
		
		//Update the database: account balances
		numRec = ds.dbUpdateAccountBalances(cust.getCustId(), account);
		//Update the database: transactions
		numRec += ds.dbAddTransaction(cust.getCustId(), account.getLastTrans());
		if(numRec == 0) {
			System.err.println("ERROR!!! Unable to write transaction to DB");
		}

		ds.close();
		System.out.println("executeTransaction: SUCCESS, " + numRec + " records written");
		
		return numRec;
	}
	
	/**
	 * Writes an overdraft transaction to the database
	 * @param cust the current Customer object
	 * @return the number of records written (1 if successful, 0 if not)
	 */
	public int executeCheckingOverdraft(Customer cust) {
		//If an overdraft occurred, write the overdraft transaction
		DataService ds = new DataService();
		int numRec = ds.dbAddTransaction(
			cust.getCustId(),
			new Transaction(new Date(),
			cust.getChecking().getAccountNumber(),
			cust.getChecking().getOverdraftFee(),
			"Overdraft fee")
		);
		
		ds.close();
		return numRec;
	}
	
	/**
	 * Validates a withdrawal amount against the balance of record from the database by account type
	 * @param cust the current Customer object
	 * @param accountType the type of account for the transaction ("chk, "sav", or "loan")
	 * @param amount the requested withdrawal amount
	 * @return true if the amount requested is valid for the account or false if not
	 */
	public boolean validateWithdrawal(Customer cust, String accountType, double amount) {
		boolean validAmount = false;
		
		//Get the account balances of record from the database
		DataService ds = new DataService();
		boolean dbSuccess = ds.dbRetrieveCustomerBalancesById(cust);
		ds.close();
		
		//Validate the requested withdrawal amount against the current balance
		if(dbSuccess) {
			switch(accountType) {
				case "chk":
					if(amount <= cust.getChecking().getAccountBalance()) {
						validAmount = true;
					}
					break;
				case "sav":
					if(amount <= cust.getSaving().getAccountBalance()) {
						validAmount = true;
					}
					break;
				case "loan":
					//Validate the business rule that cash advances (withdrawals)
					//must be less that the difference between the maximum balance (principal)
					//and the outstanding balance
					if(amount <= computeLoanAvailable(cust.getLoan())) {
						validAmount = true;
					}
					break;
			}
		}
		else {
			System.out.println("ERROR: Unable to retrieve balances in BankService.validateWithdrawal");
		}
		
		return validAmount;
	}
	
	/**
	 * Validates the business rule that cash advance payments (deposits)
	 * must be less than the outstanding balance
	 * @param cust the current Customer
	 * @param amount the requested amount of payment to the cash advance
	 * @return true if the rule is satisfied, false otherwise
	 */
	public boolean validateCashAdvancePayment(Customer cust, double amount) {
		boolean validAmount = false;
		
		//Validate the business rule that cash advance payments (deposits)
		//must be less than the outstanding balance
		if(amount < Math.abs(cust.getLoan().getAccountBalance())) {
			validAmount = true;
		}
		return validAmount;
	}
	
	/**
	 * Helper method to compute how much credit is available on a Loan object
	 * @param loan the Loan object for computing available credit
	 * @return the amount of credit available
	 */
	public double computeLoanAvailable(Loan loan) {
		return Math.abs(loan.getPrincipal()) - Math.abs(loan.getAccountBalance());
	}
}