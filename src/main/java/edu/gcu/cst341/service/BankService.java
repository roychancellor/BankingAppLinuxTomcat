package edu.gcu.cst341.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * @return the number of records updated
	 */
	public int doTransaction(Customer cust, String accountType, int transType, double amount) {
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
	public int doCheckingOverdraft(Customer cust) {
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
	 * Executes a transfer between two accounts
	 * @param cust the current logged-in Customer object
	 * @param fromAccount the account type from which funds are taken
	 * @param amount the amount of the transfer
	 * @param toAccount the account type into which funds are put
	 * @return the number of records written to the database
	 */
	public int doTransfer(Customer cust, String fromAccount, double amount, String toAccount) {
		//Do a withdrawal from the FROM account
		int numRec = doTransaction(cust, fromAccount, Account.TRANSFER_W, amount);
		
		if(numRec > 0) {
			//Do a deposit into the TO account
			numRec += doTransaction(cust, toAccount, Account.TRANSFER_D, amount);
		}
		else {
			System.out.println("ERROR: Unable to complete the transfer!!!");
		}
		
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
		if(amount <= Math.abs(cust.getLoan().getAccountBalance())) {
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
		System.out.println("Credit limit: " + loan.getCreditLimit() + ", balance: " + loan.getAccountBalance());
		return Math.abs(loan.getCreditLimit()) - Math.abs(loan.getAccountBalance());
	}
	
	/**
	 * Helper method that separates the complete transaction list for a customer Id
	 * into lists by account type
	 * @param allTransactions the complete list of transactions for a customer Id
	 * @param acctPrefix the account type prefix ('C' = checking, 'S' = savings, 'L' = loan)
	 * @return a list of transactions filtered by account prefix
	 */
	public List<Transaction> transListByAccount(List<Transaction> allTransactions, char acctPrefix) {
		List<Transaction> listByAccount = null;
		char acctType;
		
		if(allTransactions != null) {
			listByAccount = new ArrayList<Transaction>();
			for(Transaction t : allTransactions) {
				acctType = t.getAccountNumber().charAt(0);
				if(acctType == acctPrefix) {
					listByAccount.add(t);
				}
			}
		}
		return listByAccount;
	}
	
	/**
	 * Converts a String value that represents a number into a double, if possible
	 * If not possible, catches the thrown exceptions
	 * @param amount the String amount to convert
	 * @return the double representation of the String if successful or -1 if not possible
	 */
	public double stringToDouble(String amount) {
		double convertedAmount = -1;
		
		try {
			convertedAmount = Double.parseDouble(amount);
		}
		catch(NullPointerException e) {
		}
		catch(NumberFormatException e) {
		}
		catch(Exception e) {
		}
		return convertedAmount;
	}	
}