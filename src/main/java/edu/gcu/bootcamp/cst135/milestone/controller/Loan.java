package edu.gcu.bootcamp.cst135.milestone.controller;

import edu.gcu.bootcamp.cst135.milestone.model.Account;

public class Loan extends Account {

	//Class data
	private double monthlyInterestRate;
	private double lateFee;
	private int termYears;
	private double monthlyPaymentAmount;
	private double principal;
	private double amountPaidThisMonth;
	public static final double LATE_FEE = 25.0;
	public static final double ANNUAL_INTEREST_RATE = 0.08;
	
	//Constructor
	public Loan(String accountNumber, double principal, double lateFee, double annualInterestRate, int termYears) {
		//Call the superclass (Account) constructor
		super(accountNumber, principal);
		
		//Unique to Loan objects
		this.lateFee = lateFee;
		
		//MONTHLY interest rate (no setter provided, so interest rate is immutable for Loan objects)
		this.monthlyInterestRate = annualInterestRate / 12;
		
		this.termYears = termYears;
		this.principal = principal;
		this.monthlyPaymentAmount = computeMonthlyPayment();
		this.amountPaidThisMonth = 0;
	}

	/**
	 * @return the monthly monthlyInterestRate
	 */
	public double getMonthlyInterestRate() {
		return monthlyInterestRate;
	}

	/**
	 * @return the lateFee
	 */
	public double getLateFee() {
		return lateFee;
	}

	/**
	 * @param lateFee the lateFee to set
	 */
	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

	/**
	 * @return the termYears
	 */
	public int getTermYears() {
		return termYears;
	}

	/**
	 * @param termYears the termYears to set
	 */
	public void setTermYears(int termYears) {
		this.termYears = termYears;
	}

	/**
	 * @return the principal
	 */
	public double getPrincipal() {
		return principal;
	}

	/**
	 * @return the monthlyPaymentAmount
	 */
	public double getMonthlyPaymentAmount() {
		return monthlyPaymentAmount;
	}

	/**
	 * @return the amountPaidThisMonth
	 */
	public double getAmountPaidThisMonth() {
		return amountPaidThisMonth;
	}

	/**
	 * @param amountPaidThisMonth the amountPaidThisMonth to set
	 */
	public void setAmountPaidThisMonth(double amountPaidThisMonth) {
		this.amountPaidThisMonth = amountPaidThisMonth;
	}

	@Override
	/**
	 * Implements doTransaction that was left abstract in the superclass
	 * unique to Loan accounts (logic for payment greater than outstanding balance)
	 * @param transType a multiplier for withdrawals (-1) or deposits (+1), unused for Loan objects
	 * @param amount the amount of loan payment
	 */
	public void doTransaction(final int transType, double amount) {
		//WITHDRAWAL: Determine if the account will be overdrawn; if so, alert the user and try again
		while(transType == Account.DEPOSIT && amount > Math.abs(getAccountBalance())) {
			System.out.println(
				"\n\t"
				+ amount
				+ " is greater than your outstanding balance of "
				+ Bank.money.format(getAccountBalance())
				+ ". Enter a new value or 0 to void transaction.\n"
			);			
			amount = getTransactionValue(Account.AMOUNT_MESSAGE + "pay: ");
		}
		//Process the transaction
		setAccountBalance(getAccountBalance() + amount);
		setAmountPaidThisMonth(getAmountPaidThisMonth() + amount);
		//Record the transaction
		this.addTransaction(amount, "Loan payment");
	}

	/**
	 * Overloads doTransaction to receive only the amount because the only allowable loan transactions are deposits
	 * @param amount dollar amount of payment on the loan
	 */
	public void doTransaction(double amount) {
			//Loan balances are negative amounts, so ADD the loan payment to make it less negative
			this.doTransaction(Account.DEPOSIT, amount);
	}

	/**
	 * Computes the monthly payment of a loan based on principal, term, and annual interest rate
	 */
	private double computeMonthlyPayment() {
		return (-this.monthlyInterestRate * this.principal / (1 - Math.pow(1 + this.monthlyInterestRate, -this.termYears * 12)));
	}
	
	/**
	 * computes the end of month interest for the loan
	 * @return end of month interest
	 */
	public double doEndOfMonthInterest() {
		return getAccountBalance() * monthlyInterestRate;
	}
	
	@Override
	/**
	 * Implements the method in the iTrans interface
	 */
	public void doEndOfMonth() {
		if (getAccountBalance() < 0) {
			//Interest
			double eomAdder = doEndOfMonthInterest();
			System.out.println("* Loan interest charged: " + String.format("$%(,12.2f", Math.abs(eomAdder)));
			this.addTransaction(eomAdder, "Interest charged");
			
			//Late fee
			if(isFeeRequired()) {
				eomAdder -= getLateFee();
				System.out.println("* Late fee charged: "
					+ String.format("$%(,12.2f", getLateFee())
					+ " (failure to make the minimum payment)\n"
				);
				this.addTransaction(-getLateFee(), "Late fee");
			}
			
			//New balance
			setAccountBalance(getAccountBalance() + eomAdder);
			
			//Reset the amount paid for the month to zero
			setAmountPaidThisMonth(0);
		}
		else {
			System.out.println("\nCongratulations, your loan is now paid off!");
		}
	}
	
	/**
	 * determines if a late fee for non-payment or below minimum payment is required
	 * @return true if fee is required or false if not
	 */
	public boolean isFeeRequired() {
		return amountPaidThisMonth < monthlyPaymentAmount;
	}
}
