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

import edu.gcu.cst341.model.Account;
import edu.gcu.cst341.model.Customer;
import edu.gcu.cst341.model.Loan;
import edu.gcu.cst341.model.Transaction;
import edu.gcu.cst341.services.BankService;
import edu.gcu.cst341.services.CustomerService;
import edu.gcu.cst341.services.DataService;

public class TransactionTests {
	private CustomerService cs;
	private Customer testCust;
	private DataService ds;
	private BankService bs;
	
	@Before
	public void beforeTest() {
		cs = new CustomerService();
		ds = new DataService();
		testCust = new Customer();
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
		bs.doTransaction(testCust, "sav", Account.DEPOSIT, payAmount);
		bs.doTransaction(testCust, "loan", Account.DEPOSIT, payAmount);
		
		//Get the balances back from the database and perform the tests
		boolean gotBalances = ds.dbRetrieveCustomerBalancesById(testCust);
		assertTrue(gotBalances);
		assertTrue(testCust.getChecking().getAccountBalance() == initialBalance + payAmount);
		assertTrue(testCust.getSaving().getAccountBalance() == initialBalance + payAmount);
		assertTrue(testCust.getLoan().getAccountBalance() == -initialBalance + payAmount);
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
		double initialBalance = 1000.00;
		String validWithdraw = "500.00";
		String invalidWithdraw = "1000.01";
		
		//TEST 1A: Valid withdrawal from checking
		testCust.getChecking().setAccountBalance(initialBalance);
		double withdrawAmount = bs.stringToDouble(validWithdraw);
		boolean validAmount = bs.validateWithdrawal(testCust, "chk", withdrawAmount);
		assertTrue(validAmount);
		testCust.getChecking().doTransaction(Account.WITHDRAWAL, withdrawAmount);
		assertTrue(testCust.getChecking().getAccountBalance() == initialBalance - withdrawAmount);

		//TEST 1B: Overdraft checking
		testCust.getChecking().setAccountBalance(initialBalance);
		withdrawAmount = bs.stringToDouble(invalidWithdraw);
		validAmount = bs.validateWithdrawal(testCust, "chk", withdrawAmount);
		assertFalse(validAmount);
		testCust.getChecking().doTransaction(Account.WITHDRAWAL, withdrawAmount);
		assertTrue(testCust.getChecking().getAccountBalance() ==
			initialBalance - withdrawAmount - testCust.getChecking().getOverdraftFee());
		
		//TEST 2A: Transactions are correct (new balance computed and new balance + transaction written to DB)
		//Set the test customer balances:
		makeValidCustomer();
		testCust.getChecking().setAccountBalance(initialBalance);
		testCust.getSaving().setAccountBalance(initialBalance);
		testCust.getLoan().setAccountBalance(-initialBalance);
		
		int custId = cs.createNewCustomer(testCust);
		assertTrue(custId > 0);
		
		//Perform transactions for each of the test customer's accounts
		withdrawAmount = bs.stringToDouble(validWithdraw);
		bs.doTransaction(testCust, "chk", Account.WITHDRAWAL, withdrawAmount);
		bs.doTransaction(testCust, "sav", Account.WITHDRAWAL, withdrawAmount);
		bs.doTransaction(testCust, "loan", Account.WITHDRAWAL, withdrawAmount);
		
		//Get the balances back from the database and perform the tests
		boolean gotBalances = ds.dbRetrieveCustomerBalancesById(testCust);
		assertTrue(gotBalances);
		assertTrue(testCust.getChecking().getAccountBalance() == initialBalance - withdrawAmount);
		assertTrue(testCust.getSaving().getAccountBalance() == initialBalance - withdrawAmount);
		assertTrue(testCust.getLoan().getAccountBalance() == -initialBalance - withdrawAmount);
		List<Transaction> allTransactions = ds.dbRetrieveTransactionsById(testCust.getCustId());
		List<Transaction> chkTrans = bs.transListByAccount(allTransactions, 'C');
		List<Transaction> savTrans = bs.transListByAccount(allTransactions, 'S');
		List<Transaction> loanTrans = bs.transListByAccount(allTransactions, 'L');
		assertTrue(chkTrans.get(chkTrans.size() - 1).getAmount() == -withdrawAmount);
		assertTrue(savTrans.get(savTrans.size() - 1).getAmount() == -withdrawAmount);
		assertTrue(loanTrans.get(loanTrans.size() - 1).getAmount() == -withdrawAmount);
		assertTrue(chkTrans.get(chkTrans.size() - 1).getTransactionType().equals("Withdrawal"));
		assertTrue(savTrans.get(savTrans.size() - 1).getTransactionType().equals("Withdrawal"));
		assertTrue(loanTrans.get(loanTrans.size() - 1).getTransactionType().equals("Cash advance"));

		//TEST 2B: Checking overdraft is correct in database
		testCust.getChecking().setAccountBalance(initialBalance);
		withdrawAmount = bs.stringToDouble(invalidWithdraw);
		bs.doTransaction(testCust, "chk", Account.WITHDRAWAL, withdrawAmount);
		bs.doCheckingOverdraft(testCust);
		gotBalances = ds.dbRetrieveCustomerBalancesById(testCust);
		assertTrue(gotBalances);
		System.err.println("testing checking overdraft: " + testCust.getChecking().getAccountBalance());
		System.err.println(initialBalance - withdrawAmount - testCust.getChecking().getOverdraftFee());
		assertEquals("balance within 0.004", initialBalance - withdrawAmount - testCust.getChecking().getOverdraftFee(),
			testCust.getChecking().getAccountBalance(), 0.004);
		allTransactions = ds.dbRetrieveTransactionsById(testCust.getCustId());
		chkTrans = bs.transListByAccount(allTransactions, 'C');
		assertEquals(testCust.getChecking().getOverdraftFee(), chkTrans.get(chkTrans.size() - 1).getAmount(), 0.004);
		assertEquals(-withdrawAmount, chkTrans.get(chkTrans.size() - 2).getAmount(), 0.004);
		assertTrue(chkTrans.get(chkTrans.size() - 2).getTransactionType().equals("Withdrawal"));
		assertTrue(chkTrans.get(chkTrans.size() - 1).getTransactionType().equals("Overdraft fee"));

		//TEST 3: Unable to exceed available balance for savings and loans
		testCust.getSaving().setAccountBalance(initialBalance);
		testCust.getLoan().setAccountBalance(0);
		withdrawAmount = bs.stringToDouble(invalidWithdraw);
		validAmount = bs.validateWithdrawal(testCust, "sav", testCust.getSaving().getAccountBalance() + 0.01);
		assertFalse(validAmount);
		validAmount = bs.validateWithdrawal(testCust, "loan", -testCust.getLoan().getCreditLimit() + 0.01);
		assertFalse(validAmount);
		
		//Delete the test customer from the database
		cs.deleteExistingCustomer(testCust);
	}
	
	@Test
	public void transferTests() {
		double initialBalance = 1000.00;
		double transferAmount = 500.00;
		makeValidCustomer();
		testCust.getChecking().setAccountBalance(initialBalance);
		testCust.getSaving().setAccountBalance(initialBalance);
		testCust.getLoan().setAccountBalance(-initialBalance);
		//TEST 1: Can't transfer between same account type
		String fromAccount = "chk";
		String toAccount = "sav";
		assertFalse(fromAccount.equals(toAccount));
		fromAccount = "chk";
		toAccount = "loan";
		assertFalse(fromAccount.equals(toAccount));
		fromAccount = "sav";
		toAccount = "chk";
		assertFalse(fromAccount.equals(toAccount));
		fromAccount = "sav";
		toAccount = "loan";
		assertFalse(fromAccount.equals(toAccount));
		fromAccount = "loan";
		toAccount = "chk";
		assertFalse(fromAccount.equals(toAccount));
		fromAccount = "loan";
		toAccount = "sav";
		assertFalse(fromAccount.equals(toAccount));
		fromAccount = "chk";
		toAccount = "chk";
		assertTrue(fromAccount.equals(toAccount));
		fromAccount = "sav";
		toAccount = "sav";
		assertTrue(fromAccount.equals(toAccount));
		fromAccount = "loan";
		toAccount = "loan";
		assertTrue(fromAccount.equals(toAccount));

		//TEST 2: Can't violate deposit or withdrawal rules
		//Check for a valid WITHDRAWAL amount before executing the transfer
		int custId = cs.createNewCustomer(testCust);
		assertTrue(custId > 0);
		testCust.getChecking().setAccountBalance(initialBalance);
		testCust.getSaving().setAccountBalance(initialBalance);
		testCust.getLoan().setAccountBalance(-initialBalance);

		//CHK --> SAV
		fromAccount = "chk";
		toAccount = "sav";
		boolean toAmountValid = true;
		boolean fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		assertTrue(fromAmountValid && toAmountValid);
		transferAmount = testCust.getChecking().getAccountBalance() + 0.01;
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		assertFalse(fromAmountValid && toAmountValid);
		
		//CHK --> LOAN
		transferAmount = 500.00;
		toAccount = "loan";
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		toAmountValid = bs.validateCashAdvancePayment(testCust, transferAmount);
		assertTrue(fromAmountValid && toAmountValid);
		transferAmount = testCust.getChecking().getAccountBalance() + 0.01;
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		assertFalse(fromAmountValid && toAmountValid);
		transferAmount = 500.0;
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		toAmountValid = bs.validateCashAdvancePayment(testCust, 5000.01);
		assertFalse(fromAmountValid && toAmountValid);
		
		//SAV --> CHK
		fromAccount = "sav";
		toAccount = "chk";
		toAmountValid = true;
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		assertTrue(fromAmountValid && toAmountValid);
		transferAmount = testCust.getSaving().getAccountBalance() + 0.01;
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		assertFalse(fromAmountValid && toAmountValid);
		
		//SAV --> LOAN
		transferAmount = 500.00;
		toAccount = "loan";
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		toAmountValid = bs.validateCashAdvancePayment(testCust, transferAmount);
		assertTrue(fromAmountValid && toAmountValid);
		transferAmount = testCust.getSaving().getAccountBalance() + 0.01;
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		assertFalse(fromAmountValid && toAmountValid);
		transferAmount = 500.0;
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		toAmountValid = bs.validateCashAdvancePayment(testCust, 5000.01);
		assertFalse(fromAmountValid && toAmountValid);
		
		//LOAN --> CHK
		fromAccount = "loan";
		toAccount = "chk";
		toAmountValid = true;
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		assertTrue(fromAmountValid && toAmountValid);
		transferAmount = -(testCust.getLoan().getCreditLimit() - 0.01);
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		assertFalse(fromAmountValid && toAmountValid);
		
		//LOAN --> SAV
		transferAmount = 500.00;
		toAccount = "sav";
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		assertTrue(fromAmountValid && toAmountValid);
		transferAmount = -(testCust.getLoan().getCreditLimit() - 0.01);
		fromAmountValid = bs.validateWithdrawal(testCust, fromAccount, transferAmount);
		assertFalse(fromAmountValid && toAmountValid);
		//Delete the test customer from the database
		cs.deleteExistingCustomer(testCust);
		
		//TEST 3: transactions written to correct accounts
		makeValidCustomer();
		custId = cs.createNewCustomer(testCust);
		assertTrue(custId > 0);
		testCust.getChecking().setAccountBalance(initialBalance);
		testCust.getSaving().setAccountBalance(initialBalance);
		testCust.getLoan().setAccountBalance(-initialBalance);

		//CHK --> SAV
		transferAmount = 500.00;
		fromAccount = "chk";
		toAccount = "sav";
		int numRec = bs.doTransfer(testCust, fromAccount, transferAmount, toAccount);
		assertTrue(numRec > 0);
		//Get the balances back from the database and perform the tests
		boolean gotBalances = ds.dbRetrieveCustomerBalancesById(testCust);
		assertTrue(gotBalances);
		assertEquals(initialBalance - transferAmount, testCust.getChecking().getAccountBalance(), 0.004);
		assertEquals(initialBalance + transferAmount, testCust.getSaving().getAccountBalance(), 0.004);
		List<Transaction> allTransactions = ds.dbRetrieveTransactionsById(testCust.getCustId());
		List<Transaction> chkTrans = bs.transListByAccount(allTransactions, 'C');
		List<Transaction> savTrans = bs.transListByAccount(allTransactions, 'S');
		assertEquals(-transferAmount, chkTrans.get(chkTrans.size() - 1).getAmount(), 0.004);
		assertEquals(transferAmount, savTrans.get(savTrans.size() - 1).getAmount(), 0.004);
		assertTrue(chkTrans.get(chkTrans.size() - 1).getTransactionType().equals("Withdrawal (transfer)"));
		assertTrue(savTrans.get(savTrans.size() - 1).getTransactionType().equals("Deposit (transfer)"));

		//Delete the test customer from the database
		cs.deleteExistingCustomer(testCust);
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
