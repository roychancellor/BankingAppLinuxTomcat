package edu.gcu.cst341.model;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import edu.gcu.cst341.controller.Bank;

/**
 * Customer class used to create Customer objects which will create Checking, Saving, and Loan objects
 * NO LONGER NEEDS TO IMPLEMENT COMPARABLE BECAUSE THE APP WILL NO LONGER USE LISTS OF CUSTOMER OBJECTS
 */
public class Customer implements Comparable<Customer> {
	//Class data
	private int custId;
	@NotBlank(message = "Name must be at least one character")
	private String firstName;
	@NotBlank(message = "Name must be at least one character")
	private String lastName;
	private Date dateOpened;
	@NotBlank
	@Size(min=6, message="Username must be at least 6 characters")
	private String username;
	@NotBlank
	@Size(min=8, message="Password must be at least 8 characters")
	private String password;
	@NotBlank
	@Size(max=12, message="Phone number must be xxx-xxx-xxxx")
	@Pattern(regexp="[0-9]{3}-[0-9]{3}-[0-9]{4}", message="Phone number must be xxx-xxx-xxxx")
	private String phoneNumber;
	@NotBlank
	@Email(message = "Must be a valid e-mail format: email@example.com")
	private String emailAddress;
	
	//Customer accounts
	private Checking checking;
	private Saving saving;
	private Loan loan;
	
	/**
	 * No-argument constructor for Customer objects
	 * Makes Checking, Saving, and Loan account objects and adds initial balance
	 * transactions to the transaction list for each account
	 */
	public Customer() {
		//Make Checking, Saving, and Loan accounts (in the future, create a menu to do this manually)
		this.checking = createCheckingAccount();
		this.saving = createSavingAccount();
		this.loan = createLoanAccount();
		
		//Populate transaction list with opening balance for each account
		this.checking.addTransaction(this.checking.getAccountBalance(), "Opening balance");
		this.saving.addTransaction(this.saving.getAccountBalance(), "Opening balance");
		this.loan.addTransaction(this.loan.getAccountBalance(), "Opening balance");
	}
	
	/**
	 * Constructor for Customers requires all three parameters to create a new Customer object
	 * Customer objects are immutable
	 * @param custId the customer identification number
	 * @param lastName the customer last name
	 * @param firstName the customer first name
	 * @param dateOpened the date the customer opened the account
	 */
	public Customer(int custId, String lastName, String firstName, Date dateOpened) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.dateOpened = dateOpened;
		//this.custID = createCustomerID();
		this.custId = custId;
		
		//Make Checking, Saving, and Loan accounts (in the future, create a menu to do this manually)
		this.checking = createCheckingAccount();
		this.saving = createSavingAccount();
		this.loan = createLoanAccount();
		
		//Populate transaction list with opening balance for each account
		this.checking.addTransaction(this.checking.getAccountBalance(), "Opening balance");
		this.saving.addTransaction(this.saving.getAccountBalance(), "Opening balance");
		this.loan.addTransaction(this.loan.getAccountBalance(), "Opening balance");
	}

	//Accessors and mutators

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the dateOpened
	 */
	public Date getDateOpened() {
		return dateOpened;
	}

	/**
	 * @param dateOpened the dateOpened to set
	 */
	public void setDateOpened(Date dateOpened) {
		this.dateOpened = dateOpened;
	}

	/**
	 * @return the custId
	 */
	public int getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(int custId) {
		this.custId = custId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the checking
	 */
	public Checking getChecking() {
		return checking;
	}

	/**
	 * @param checking the checking to set
	 */
	public void setChecking(Checking checking) {
		this.checking = checking;
	}

	/**
	 * @return the saving
	 */
	public Saving getSaving() {
		return saving;
	}

	/**
	 * @param saving the saving to set
	 */
	public void setSaving(Saving saving) {
		this.saving = saving;
	}

	/**
	 * @return the loan
	 */
	public Loan getLoan() {
		return loan;
	}

	/**
	 * @param loan the loan to set
	 */
	public void setLoan(Loan loan) {
		this.loan = loan;
	}

//	/**
//	 *@return customer and account information
//	 */
//	@Override
//	public String toString() {
//		return "\n----------------------------------------------"
//			+ "\nAccount details for " + this.firstName + " " + this.lastName
//			//+ "\ncustomer ID " + this.custID.substring(this.custID.length() - 4, this.custID.length())
//			+ "\ncustomer ID " + this.custId
//			+ "\n----------------------------------------------"
//			+ "\n* Customer since " + this.dateOpened
//			+ balancesToString()
//			+ "\n----------------------------------------------";
//	}
//	
//	/**
//	 * creates a string of all account balances formatted for printing
//	 * @return string
//	 */
//	public String balancesToString() {
//		return "\n* " + this.checking.getAccountNumber() + "\tBalance: " + Bank.money.format(this.checking.getAccountBalance())
//		+ "\n* " + this.saving.getAccountNumber() + "\tBalance: " + Bank.money.format(this.saving.getAccountBalance())
//		+ "\n* " + this.loan.getAccountNumber() + "\tBalance: " + Bank.money.format(this.loan.getAccountBalance());
//	}
//	
	/**
	 * overloaded toString to allow a boolean parameter for controlling whether loan details show or not
	 * @param verbose boolean value to print details or not
	 * @return message string
	 */
	public String toString(boolean verbose) {
		String message = this.toString();
		
		//Concatenate the loan details when verbose is true
		if(verbose) {
			message += "\n   * principal borrowed:\t" + Bank.money.format(-this.loan.getCreditLimit())
			+ "\n   * annual interest rate:\t" + this.loan.getMonthlyInterestRate() * 12 * 100 + "%"
			+ "\n   * monthly payment:\t" + Bank.money.format(this.loan.getMonthlyPaymentAmount())
			+ "\n   * term years:\t" + this.loan.getTermYears()
			+ "\n----------------------------------------------";
		}

		return message;
	}
	
	//Class methods

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", dateOpened=" + dateOpened
				+ ", custId=" + custId + ", username=" + username + ", password=" + password + ", phoneNumber="
				+ phoneNumber + ", emailAddress=" + emailAddress + "]";
	}

	/**
	 * Implementation of compareTo for comparing two Customer objects
	 * Allows sorting by lastName, firstName
	 * @return the value of String compareTo for lastName (if unequal) or firstName if lastNames same
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
		String accountNumber = "CHK" + createAccountNumber();
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
		String accountNumber = "SAV" + createAccountNumber();
		return new Saving(
			accountNumber,
			500,
			Saving.MINIMUM_BALANCE,
			Saving.BELOW_MIN_BALANCE_FEE,
			Saving.ANNUAL_INTEREST_RATE
		);
	}
	
	/**
	 * Creates a savings account
	 * @return Saving account object
	 */
	public Loan createLoanAccount() {
		String accountNumber = "LOA" + createAccountNumber();
		return new Loan(
			accountNumber,
			-5000,
			Loan.LATE_FEE,
			Loan.ANNUAL_INTEREST_RATE,
			10
		);
	}
	
	/**
	 * Creates an account number from the first 9 characters of System.currentTimeMillis
	 * @return string
	 */
	private String createAccountNumber() {
		return ((Long)System.currentTimeMillis()).toString().substring(0, 9);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + custId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (custId != other.custId)
			return false;
		return true;
	}
	
	/**
	 * Creates an account number from the first 9 characters of System.currentTimeMillis + random 1-100
	 * @return string
	 */
//	private String createCustomerID() {
//		return ((Long)(System.currentTimeMillis() + (long)(Math.random() * 100 + 1))).toString().substring(0, 9);
//	}
}