/**
 * Customer class used to create Customer objects which will create Checking, Saving, and Loan objects
 */
package edu.gcu.bootcamp.cst135.milestone.model;

import java.util.Date;
import edu.gcu.bootcamp.cst135.milestone.controller.Checking;
import edu.gcu.bootcamp.cst135.milestone.controller.Saving;
import edu.gcu.bootcamp.cst135.milestone.controller.Loan;

public class Customer implements Comparable<Customer> {
	//Class data
	private String firstName;
	private String lastName;
	private Date dateOpened;
	private String custID;
	private Checking checking;
	private Saving saving;
	private Loan loan;
	
	/**
	 * Constructor for Customers requires all three parameters to create a new Customer object
	 * Customer objects are immutable
	 * @param firstName customer first name
	 * @param lastName customer last name
	 * @param dateOpened date the customer opened the account
	 * @param custID unique 9-character string ID for customer
	 */
	public Customer(String firstName, String lastName, Date dateOpened, String custID) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOpened = dateOpened;
		this.custID = custID;
		
		//Make Checking, Saving, and Loan accounts (in the future, create a menu to do this manually)
		this.checking = createCheckingAccount();
		this.saving = createSavingAccount();
		this.loan = createLoanAccount();
	}

	//Accessors (no mutators provided - Customers are immutable objects)
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the dateOpened
	 */
	public Date getDateOpened() {
		return dateOpened;
	}
	
	/**
	 * @return the checking account object
	 */
	public Checking getChecking() {
		return checking;
	}

	/**
	 * @return the saving account object
	 */
	public Saving getSaving() {
		return saving;
	}

	/**
	 * @return the loan account object
	 */
	public Loan getLoan() {
		return loan;
	}

	/**
	 * @return the custID
	 */
	public String getCustomerID() {
		return custID;
	}

	/**
	 *@return lastName, firstName
	 */
	@Override
	public String toString() {
		return "\n-------------------------------------------------------------"
			+ "\nAccount details for " + this.firstName + " " + this.lastName + ", "
			+ "customer ID XXX-XX-" + this.custID.substring(this.custID.length() - 4, this.custID.length())
			+ "\n-------------------------------------------------------------"
			+ "\n* Customer since " + this.dateOpened
			+ "\n* " + this.checking.getAccountNumber() + "\tBalance: " + this.checking.getAccountBalance()
			+ "\n* " + this.saving.getAccountNumber() + "\tBalance: " + this.saving.getAccountBalance()
			+ "\n* " + this.loan.getAccountNumber() + "\tBalance: " + this.loan.getAccountBalance();
	}
	/**
	 * overloaded toString to allow a boolean parameter for controlling whether loan details show or not
	 * @param verbose boolean value to print details or not
	 * @return message string
	 */
	public String toString(boolean verbose) {
		String message = this.toString();
		
		//Add the loan details
		if(verbose) {
			message += "\n   * principal borrowed: " + (-this.loan.getPrincipal())
			+ "\n   * annual interest rate: " + this.loan.getInterestRate() * 12
			+ "\n   * monthly payment: " + this.loan.getMonthlyPayment()
			+ "\n   * term years: " + this.loan.getTermYears();
		}

		return message;
	}
	
	//Class methods

	/**
	 * Implementation of compareTo for comparing two Customer objects
	 * Allows sorting by lastName, firstName
	 */
	public int compareTo(Customer c) {
		int valueLastName = this.lastName.compareTo(c.lastName);
		if(valueLastName == 0) {  //last names same
			return this.firstName.compareTo(c.firstName);
		}
		else {
			return valueLastName;
		}
	}
	
	/**
	 * Creates a checking account
	 * @return Checking account object
	 */
	public Checking createCheckingAccount() {
		String accountNumber = "CHK" + ((Long)System.currentTimeMillis()).toString().substring(0, 9);
		return new Checking(
			accountNumber,
			2500,
			Checking.OVERDRAFT_FEE
		);
	}
	
	/**
	 * Creates a savings account
	 * @return Saving account object
	 */
	public Saving createSavingAccount() {
		String accountNumber = "SAV" + ((Long)System.currentTimeMillis()).toString().substring(0, 9);
		return new Saving(
			accountNumber,
			500,
			Saving.MINIMUM_BALANCE,
			Saving.BELOW_MIN_BALANCE_FEE,
			Saving.INTEREST_RATE
		);
	}
	
	/**
	 * Creates a savings account
	 * @return Saving account object
	 */
	public Loan createLoanAccount() {
		String accountNumber = "LOA" + ((Long)System.currentTimeMillis()).toString().substring(0, 9);
		return new Loan(
			accountNumber,
			-5000,
			Loan.LATE_FEE,
			Loan.INTEREST_RATE,
			10
		);
	}

}