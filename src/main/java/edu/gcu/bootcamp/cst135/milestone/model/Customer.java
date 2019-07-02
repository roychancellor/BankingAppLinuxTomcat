/**
 * Customer class used to create Customer objects which will create Checking, Saving, and Loan objects
 */
package edu.gcu.bootcamp.cst135.milestone.model;

import java.util.Date;
import edu.gcu.bootcamp.cst135.milestone.controller.Checking;
import edu.gcu.bootcamp.cst135.milestone.controller.Saving;
import edu.gcu.bootcamp.cst135.milestone.controller.Loan;

public class Customer {
	//Class data
	private String firstName;
	private String lastName;
	private Date dateOpened;
	
	/**
	 * Constructor for Customers requires all three parameters to create a new Customer object
	 * Customer objects are immutable
	 * @param firstName customer first name
	 * @param lastName customer last name
	 * @param dateOpened date the customer opened the account
	 */
	public Customer(String firstName, String lastName, Date dateOpened) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOpened = dateOpened;
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
	 *@return lastName, firstName
	 */
	@Override
	public String toString() {
		return "Customer last, first, dateOpened: "
			+ this.lastName
			+ ", " + this.firstName
			+ ", " + this.dateOpened;
	}
	
	//Class methods

	/**
	 * Creates a checking account
	 * @return Checking account object
	 */
	public Checking createCheckingAccount() {
		String accountNumber = "C" + ((Long)System.currentTimeMillis()).toString().substring(0, 9);
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
		String accountNumber = "S" + ((Long)System.currentTimeMillis()).toString().substring(0, 9);
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
		String accountNumber = "L" + ((Long)System.currentTimeMillis()).toString().substring(0, 9);
		return new Loan(
			accountNumber,
			-5000,
			Loan.LATE_FEE,
			Loan.INTEREST_RATE
		);
	}

}