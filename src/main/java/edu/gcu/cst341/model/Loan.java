package edu.gcu.cst341.model;

/**
 * Class that inherits Account fields and behaviors and creates Loan object data and behaviors
 *
 */
public class Loan extends Account {

	//Class data
	private double monthlyInterestRate;
	private double lateFee;
	private int termYears;
	private double monthlyPaymentAmount;
	private double creditLimit;
	private double amountPaidThisMonth;
	private double interestPaidThisMonth;
	public static final double LATE_FEE = 25.0;
	public static final double ANNUAL_INTEREST_RATE = 0.08;
	public static final double DEFAULT_CREDIT_LIMIT = -5000.00;
	
	//Constructor
	public Loan(String accountNumber, double creditLimit, double lateFee, double annualInterestRate, int termYears) {
		//Call the superclass (Account) constructor with a ($1,000) balance
		super(accountNumber, -1000);
		
		//Unique to Loan objects
		this.lateFee = lateFee;
		
		//MONTHLY interest rate (no setter provided, so interest rate is immutable for Loan objects)
		this.monthlyInterestRate = annualInterestRate / 12;
		
		this.termYears = termYears;
		this.creditLimit = creditLimit;
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
	 * @return the credit limit
	 */
	public double getCreditLimit() {
		return creditLimit;
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

	/**
	 * @return the interestPaidThisMonth
	 */
	public double getInterestPaidThisMonth() {
		return interestPaidThisMonth;
	}

	/**
	 * @param interestPaidThisMonth the interestPaidThisMonth to set
	 */
	public void setInterestPaidThisMonth(double interestPaidThisMonth) {
		this.interestPaidThisMonth = interestPaidThisMonth;
	}

	@Override
	/**
	 * Implements doTransaction that was left abstract in the superclass
	 * unique to Loan accounts (logic for payment greater than outstanding balance)
	 * @param transType a multiplier for withdrawals (-1) or deposits (+1), unused for Loan objects
	 * @param amount the amount of loan payment
	 */
	public void doTransaction(final int transType, double amount) {
		//DEPOSITS are payments TO the loan balance, so only allow a deposit when there is a balance
		if(transType == Account.DEPOSIT && amount > Math.abs(getAccountBalance())) {
			System.out.println("\nLOAN DEPOSIT: Paying more than outstanding balance");
		}
		//WITHDRAWALS are cash advances FROM the loan balance, so only allow a withdrawal
		//up to the difference between the current balance and the available credit (principal)
		//Process the transaction
		String transMessage = "";
		if(transType == Account.WITHDRAWAL || transType == Account.TRANSFER_W) {
			//Withdrawals are negative, so change the sign of the amount
			amount = -amount;
			transMessage = "Cash advance" + (transType == Account.TRANSFER_W ? Account.TRANSFER_NOTE : "");
		}
		if(transType == Account.DEPOSIT || transType == Account.TRANSFER_D) {
			transMessage = "Loan payment" + (transType == Account.TRANSFER_D ? Account.TRANSFER_NOTE : "");
			setAmountPaidThisMonth(getAmountPaidThisMonth() + amount);
			System.out.println("Loan.doTransaction: paid this month = " + getAmountPaidThisMonth());
		}
		//Update the account balance
		setAccountBalance(getAccountBalance() + amount);
		//Record the transaction
		this.addTransaction(amount, transMessage);
	}

	/**
	 * Computes the monthly payment of a loan based on principal, term, and annual interest rate
	 * @return the monthly payment amount for compounded interest
	 */
	public double computeMonthlyPayment() {
		return (-this.monthlyInterestRate * this.creditLimit / (1 - Math.pow(1 + this.monthlyInterestRate, -this.termYears * 12)));
	}
	
	/**
	 * computes the end of month interest for the loan
	 * @return end of month interest
	 */
	public double doEndOfMonthInterest() {
		return getAccountBalance() * monthlyInterestRate;
	}
	
	/**
	 * Implements the method in the iTrans interface
	 */
	public void doEndOfMonth() {
		if(getAccountBalance() < 0) {
			//Interest
			double eomAdder = doEndOfMonthInterest();
			this.setInterestPaidThisMonth(eomAdder);
			this.addTransaction(eomAdder, "Interest charged");
			
			//Late fee
			if(isFeeRequired()) {
				eomAdder -= getLateFee();
				System.out.println("* Late fee charged: "
					+ " (failure to make the minimum payment)\n"
				);
				this.addTransaction(-getLateFee(), "Late fee");
			}
			
			//New balance
			setAccountBalance(getAccountBalance() + eomAdder);
			
			//The calling method needs to set the amount paid this month to zero
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
		if(Math.abs(this.getAccountBalance()) < 0.01) {
			return false;
		}
		return amountPaidThisMonth < monthlyPaymentAmount;
	}
	
	/**
	 * prints an amortization table for this loan
	 */
	public void viewAmortization() {
		double balance = this.creditLimit;
		double interestPaid = 0;
		int paymentNumber = 1;
		double totalInterestPaid = 0;
		double totalPrincipalPaid = 0;
		
		System.out.println("\nNumber\tInterest\tPrincipal\tBalance");
//		Menus.printHeaderLine(55);
		System.out.printf("0\t--------\t--------\t$%(,12.2f\n", this.creditLimit);
		
		while(balance < 0) {
			interestPaid = -balance * this.monthlyInterestRate;
			totalInterestPaid += interestPaid;
			totalPrincipalPaid += (this.monthlyPaymentAmount - interestPaid);
			balance = balance - interestPaid + this.monthlyPaymentAmount;
			System.out.printf("%d\t$%(,9.2f\t$%(,7.2f\t$%(,12.2f\n", paymentNumber, -interestPaid, this.monthlyPaymentAmount - interestPaid, balance);
			paymentNumber++;
		}
		
//		Menus.printHeaderLine(55);
		System.out.printf("TOTALS:\t$%(,9.2f\t$%(,9.2f\n", totalInterestPaid, totalPrincipalPaid);
//		Menus.printHeaderLine(55);
		System.out.println("\n");
	}
}
