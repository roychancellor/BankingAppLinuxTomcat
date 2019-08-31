package edu.gcu.cst341.application;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.gcu.cst341.controller.DataService;
//import edu.gcu.cst341.database.DbConstants;
import edu.gcu.cst341.model.Account;
import edu.gcu.cst341.model.Customer;
import edu.gcu.cst341.view.Menus;

/**
 * Bank is the primary class for the banking application, housing all methods to process user input
 * The actual account computations occur in the account classes Checking, Saving, and Loan
 */
@Service
public class BankServiceOLD {

	//Class data
	private String bankName;
	private List<Customer> customers = new ArrayList<Customer>();
	private Map<Integer, Integer> custIdToIndex = new HashMap<Integer, Integer>();
	private DataService ds;
	private int custIndex;
	
	//Format for dates and money outputs in all classes
	public static DecimalFormat money = new DecimalFormat();
	public static final String MONEY_FORMAT = "$#,##0.00;($#,##0.00)";

	//Constructor
	public BankServiceOLD() {
		this.bankName = "Roy Bank 4.0";
		
		System.out.println("BankService constructor opening DB connection...");
//		this.db = new DataService(DbConstants.SILENT, DbConstants.LOCAL);
		this.ds = new DataService();
		if(ds.connectToDatabase()) {
//			//Create the list of customers from the customers database
//			customers = db.makeCustomerListFromDatabase();
//			//Sort the customer list
//			Collections.sort(customers);
//			//Make a map of customerId from the database to the index of the customers list
//			//which is necessary after the sort
//			mapIdtoIndex();
			//Set the money format
			money.applyPattern(MONEY_FORMAT);
//			System.out.println("BankService constructor closing DB connection...");
			ds.close();
		}
		else {
			System.out.println("FATAL ERROR: Unable to open database. BANK IS CLOSED FOR BUSINESS!");
		}
	}
	
	//Getters and setters
	
	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the customers
	 */
	public List<Customer> getCustomers() {
		return customers;
	}

	/**
	 * @param customers the customers to set
	 */
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	/**
	 * @return the custIdToIndex
	 */
	public Map<Integer, Integer> getCustIdToIndex() {
		return custIdToIndex;
	}

	/**
	 * @param custIdToIndex the custIdToIndex to set
	 */
	public void setCustIdToIndex(Map<Integer, Integer> custIdToIndex) {
		this.custIdToIndex = custIdToIndex;
	}

	/**
	 * @return the ds
	 */
	public DataService getDb() {
		return ds;
	}

	/**
	 * @param db the db to set
	 */
	public void setDb(DataService ds) {
		this.ds = ds;
	}

	//Class methods

	/**
	 * updates customer first and last name,
	 * re-sorts customer list, and updates the id to index map
	 */
	private void doUpdateCustomer() {
		custIndex = Menus.viewCustomerSelectionMenu(customers);
		if(custIndex != Menus.MENU_EXIT) {
			//Make the user selection into a zero-based index
			custIndex -= 1;
			
			//Get original names
			String origLastName = customers.get(custIndex).getLastName();
			String origFirstName = customers.get(custIndex).getFirstName();
			
			//Set the new names
			customers.get(custIndex).setLastName(Menus.getCustomerName("Enter new LAST name:"));
			customers.get(custIndex).setFirstName(Menus.getCustomerName("Enter new FIRST name:"));
			System.out.println("\nSuccess, " + origFirstName + " " + origLastName
				+ " changed to "
				+ customers.get(custIndex).getFirstName()
				+ " " + customers.get(custIndex).getLastName()
			);
			//Sort the customer list
			Collections.sort(customers);
			//Update the id to index map
//			mapIdtoIndex();
		}
	}
	
	/**
	 * prints a welcome message to the customer
	 */
	private void welcomeCustomer() {
		if(this.custIndex < customers.size())
			System.out.println(customers.get(custIndex).toString(false));
	}
	
	/**
	 * gets the customer's transaction selection
	 */
	public void doCustomerTransactions() {
		//Get a customer logged in
//		int customerIdFromDb = doCustomerLogin();
		int customerIdFromDb = custIndex;
		
		if(customerIdFromDb != Menus.MENU_EXIT) {
			custIndex = this.custIdToIndex.get(customerIdFromDb);
			int option = Menus.MENU_EXIT;
			do {
				option = Menus.viewCustomerActionMenu(this.bankName, customers.get(custIndex));
				
				welcomeCustomer();
	
				processCustomerMenu(option);
			} while(option!= Menus.MENU_EXIT);
		}
	}
		
	/**
	 * Processes the user-selected option from the main menu
	 * After each transaction, calls viewBalances to update the user
	 * @param menuOption the menu option the user selected
	 */
	private void processCustomerMenu(int menuOption) {

		switch(menuOption) {
		case 1:
			customers.get(custIndex).getChecking().doTransaction(
				Account.DEPOSIT,
				customers.get(custIndex).getChecking().getTransactionValue(Account.AMOUNT_MESSAGE + "deposit: ")
			);
			Menus.viewBalances(customers.get(custIndex));
			break;
		case 2:
			customers.get(custIndex).getSaving().doTransaction(
				Account.DEPOSIT,
				customers.get(custIndex).getSaving().getTransactionValue(Account.AMOUNT_MESSAGE + "deposit: ")
			);
			Menus.viewBalances(customers.get(custIndex));
			break;
		case 3:
			customers.get(custIndex).getChecking().doTransaction(
				Account.WITHDRAWAL,
				customers.get(custIndex).getChecking().getTransactionValue(Account.AMOUNT_MESSAGE + "withdraw: ")
			);
			Menus.viewBalances(customers.get(custIndex));
			break;
		case 4:
			customers.get(custIndex).getSaving().doTransaction(
				Account.WITHDRAWAL,
				customers.get(custIndex).getSaving().getTransactionValue(Account.AMOUNT_MESSAGE + "withdraw: ")
			);
			Menus.viewBalances(customers.get(custIndex));
			break;
		case 5:
			System.out.println("\nYour minimum monthly payment is "
				+ money.format(customers.get(custIndex).getLoan().getMonthlyPaymentAmount()));
			customers.get(custIndex).getLoan().doTransaction(
				customers.get(custIndex).getLoan().getTransactionValue(Account.AMOUNT_MESSAGE + "pay on the loan: ")
			);
			Menus.viewBalances(customers.get(custIndex));
			break;
		case 6:
			customers.get(custIndex).getLoan().viewAmortization();
		case 7:
			//Menus.viewBalances(customers.get(custIndex));
			break;
		case 8:
			Menus.viewEndOfMonth(customers.get(custIndex));
			break;  
		case Menus.MENU_EXIT:
			Menus.viewCustomerExit(customers.get(custIndex));
			break;
		default:
			Menus.viewCustomerActionMenu(this.bankName, customers.get(custIndex));
		}
	}	
}