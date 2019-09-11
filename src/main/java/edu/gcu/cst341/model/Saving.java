package edu.gcu.cst341.model;

/**
 * Child class of Account that creates Saving account objects
 */
public class Saving extends Account {

	//Unique child class data and getters/setters
	private double minBalance;
	private double interestRate;
	private double serviceFee;
	public static final double MINIMUM_BALANCE = 200.0;
	public static final double ANNUAL_INTEREST_RATE = 0.06;
	public static final double BELOW_MIN_BALANCE_FEE = 40.0;
	
	/**
	 * Constructor for Saving objects. There is no default constructor...must use this one	 * 
	 * @param accountNum the account number
	 * @param balance the beginning balance
	 * @param minBalance minimum balance required to not get a monthly service fee
	 * @param serviceFee service fee amount (assessed if the balance drops below the minimum)
	 * @param interestRate annual interestRate rate
	 */
	public Saving(String accountNum, double balance, double minBalance, double serviceFee , double interestRate) {
		//Call the superclass (Account) constructor
		super(accountNum, balance);
		
		//Unique to Saving objects
		this.minBalance = minBalance;
		this.serviceFee = serviceFee;
		
		//MONTHLY interestRate rate
		this.interestRate = interestRate / 12;		
	}

	//Getters and setters for fields unique to Saving class
	/**
	 * get the minimum balance
	 * @return the minimum balance
	 */
	public double getMinBalance() {
		return minBalance;
	}

	/**
	 * set the minimum balance
	 * @param minBalance the minimum balance to set
	 */
	public void setMinBalance(double minBalance) {
		this.minBalance = minBalance;
	}

	/**
	 * get the interest rate
	 * @return the interest rate
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * set the interest rate (MONTHLY)
	 * @param interestRate the monthly interest rate from the annual interest rate
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate / 12;
	}

	/**
	 * gets the service fee charged when the balance drops below the minimum
	 * @return the service fee
	 */
	public double getServiceFee() {
		return serviceFee;
	}

	/**
	 * sets the service fee
	 * @param serviceFee the service fee when the balance drops below the minimum
	 */
	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

	/**
	 * Implements processTransaction that was left abstract in the superclass
	 * unique to Saving accounts (logic for withdraw greater than available balance)
	 * @param transType a multiplier for withdrawals (-1) or deposits (+1)
	 * @param amount the amount to withdraw or deposit
	 */
	public void doTransaction(final int transType, double amount) {
		//Record the transaction
		if(transType == Account.WITHDRAWAL || transType == Account.TRANSFER_W) {
			//Withdrawals are negative, so change the sign of the amount
			amount = -amount;
			this.addTransaction(amount, "Withdrawal" + (transType == Account.TRANSFER_W ? Account.TRANSFER_NOTE : ""));
		}
		if(transType == Account.DEPOSIT || transType == Account.TRANSFER_D) {
			this.addTransaction(amount, "Deposit" + (transType == Account.TRANSFER_D ? Account.TRANSFER_NOTE : ""));
		}
		//Update the account balance
		setAccountBalance(getAccountBalance() + amount);
		System.out.println("\t\t***doTransaction AFTER setAccountBalance: " + getAccountBalance());
	}
	
	/**
	 * Implements the iTrans interface method
	 */
	public void doEndOfMonth() {
		//Service fee (deduct before computing interest)
		if(isFeeRequired()) {
//			System.out.println("* Service fee charged: "
//				+ Bank.money.format(getServiceFee())
//				+ "(savings below minimum balance)");

			setAccountBalance(getAccountBalance() - getServiceFee());
			this.addTransaction(-getServiceFee(), "Service fee");
		}
		//Interest on any positive balance (interest compounded monthly)
		if (getAccountBalance() > 0) {
			double interestEarned = getAccountBalance() * getInterestRate();
			
			setAccountBalance(getAccountBalance() + interestEarned);
//			System.out.println("* Savings interest earned: " + Bank.money.format(interestEarned));
			this.addTransaction(interestEarned, "Interest earned");
		}
	}
	
	/**
	 * Implements the iTrans interface: checkLateFee method
	 * @return true if fee is requires (balance below minimum) or false if not
	 */
	public boolean isFeeRequired() {
		return getAccountBalance() < getMinBalance();
	}
}