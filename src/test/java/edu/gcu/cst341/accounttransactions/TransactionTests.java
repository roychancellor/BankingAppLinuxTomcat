/**
 * Tests new customer functionality
 * 1. New customer information is correctly written to the database
 * 2. Creating a new customer with a username that exists in the database produces an error
 */
package edu.gcu.cst341.accounttransactions;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.gcu.cst341.controller.BankService;
import edu.gcu.cst341.controller.CustomerService;
import edu.gcu.cst341.controller.DataService;
import edu.gcu.cst341.controller.LoginService;
import edu.gcu.cst341.model.Account;
import edu.gcu.cst341.model.Customer;
import edu.gcu.cst341.model.Loan;
import edu.gcu.cst341.model.Transaction;

public class TransactionTests {
	private CustomerService cs;
	private Customer testCust;
	private DataService ds;
	private LoginService ls;
	private BankService bs;
	
	@Before
	public void beforeTest() {
		cs = new CustomerService();
		ds = new DataService();
		testCust = new Customer();
		ls = new LoginService();
		bs = new BankService();
	}

	@Test
	public final void testComputeMonthlyPayment() {
		System.out.println("\nTesting the loan payment calculation");
		Loan testLoan = new Loan("aaaaaaaaa", -5000, 25, 0.08, 10);
		//Check whether the monthly payment returns the correct value within less than half a cent
		//Checking a double requires an allowable delta, so set at 0.004 for this test
	    assertEquals("Monthly payment should be $60.66", 60.66, testLoan.computeMonthlyPayment(), 0.004);
	}

	@Test
	public void stringToDoubleTest() {
		double amount = bs.stringToDouble("100.00");
		assertTrue(amount == 100.00);
		amount = bs.stringToDouble(null);
		assertTrue(amount == -1.0);
		amount = bs.stringToDouble("");
		assertTrue(amount == -1.0);
		amount = bs.stringToDouble("-100.0");
		assertTrue(amount == -100.00);
		amount = bs.stringToDouble("abc");
		assertTrue(amount == -1.0);
		amount = bs.stringToDouble("0");
		assertTrue(amount == 0.00);
		//This is the actual condition when the method is used in LoginController
		assertTrue(amount < 0.01);
	}
	
	@Test
	public void depositTests() {
		//TEST 1A: Can pay less than outstanding balance for cash advance loans
		testCust.getLoan().setAccountBalance(1000.00);
		double payAmount = bs.stringToDouble("500.00");
		boolean validAmount = bs.validateCashAdvancePayment(testCust, payAmount);
		assertTrue(validAmount);
		//TEST 1B: Can't pay more than outstanding balance for cash advance loans
		testCust.getLoan().setAccountBalance(1000.00);
		payAmount = bs.stringToDouble("1000.01");
		validAmount = bs.validateCashAdvancePayment(testCust, payAmount);
		assertFalse(validAmount);
		
		//TEST 2: Transactions are correct (new balance computed and new balance + transaction written to DB)
		//Set the test customer balances:
		makeValidCustomer();
		double initialBalance = 1000.00;
		testCust.getChecking().setAccountBalance(initialBalance);
		testCust.getSaving().setAccountBalance(initialBalance);
		testCust.getLoan().setAccountBalance(-initialBalance);
		
		int custId = cs.createNewCustomer(testCust);
		assertTrue(custId > 0);
		
		//Perform transactions for each of the test customer's accounts
		String depositAmount = "500.00";
		payAmount = bs.stringToDouble(depositAmount);
		bs.doTransaction(testCust, "chk", Account.DEPOSIT, payAmount);
		payAmount = bs.stringToDouble(depositAmount);
		bs.doTransaction(testCust, "sav", Account.DEPOSIT, payAmount);
		payAmount = bs.stringToDouble(depositAmount);
		bs.doTransaction(testCust, "loan", Account.DEPOSIT, payAmount);
		
		//Get the balances back from the database and perform the tests
		boolean gotBalances = ds.dbRetrieveCustomerBalancesById(testCust);
		assertTrue(gotBalances);
		assertTrue(testCust.getChecking().getAccountBalance() == 1500.00);
		assertTrue(testCust.getSaving().getAccountBalance() == 1500.00);
		assertTrue(testCust.getLoan().getAccountBalance() == -500.00);
		List<Transaction> allTransactions = ds.dbRetrieveTransactionsById(testCust.getCustId());
		List<Transaction> chkTrans = bs.transListByAccount(allTransactions, 'C');
		List<Transaction> savTrans = bs.transListByAccount(allTransactions, 'S');
		List<Transaction> loanTrans = bs.transListByAccount(allTransactions, 'L');
		assertTrue(chkTrans.get(chkTrans.size() - 1).getAmount() == payAmount);
		assertTrue(savTrans.get(savTrans.size() - 1).getAmount() == payAmount);
		assertTrue(loanTrans.get(loanTrans.size() - 1).getAmount() == payAmount);
		assertTrue(chkTrans.get(chkTrans.size() - 1).getTransactionType().equals("Deposit"));
		assertTrue(savTrans.get(savTrans.size() - 1).getTransactionType().equals("Deposit"));
		assertTrue(loanTrans.get(loanTrans.size() - 1).getTransactionType().equals("Loan payment"));
		
		//Delete the test customer from the database
		cs.deleteExistingCustomer(testCust);
	}
	
	@Test
	public void withdrawalTests() {
		//TEST 1: Checking overdraft occurs when amount > balance
		
		//TEST 2: Transactions are correct (new balance computed and new balance + transaction written to DB)

		//TEST 3: Unable to exceed available balance for savings and loans
		
	}
	
	@Test
	public void transferTests() {
		//TEST 1: Can't transfer between same account type
		
		
		//TEST 2: Can't violate deposit or withdrawal rules
		
		
		//TEST 3: transactions written to correct accounts
		
	}
	
	@Test
	public void correctBalanceTests() {
		//Perform a series of transactions of known amounts from a new customer
		//and verify the correct balance occurs for each account
	}
	
	/**
	 * Helper method to create a known-good customer object
	 * @return a known-good customer object
	 */
	private void makeValidCustomer() {
		this.testCust.setFirstName("First");
		this.testCust.setLastName("Last");
		this.testCust.setUsername("username");
		this.testCust.setPassword("password");
		this.testCust.setPassCompare("password");
		this.testCust.setEmailAddress("first@last.com");
		this.testCust.setPhoneNumber("123-456-7890");
	}
	
	@After
	public void afterTests() {
		ds.close();
	}
	
}
