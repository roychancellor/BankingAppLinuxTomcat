/**
 * 
 */
package edu.gcu.cst341.reportstests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.gcu.cst341.model.Account;
import edu.gcu.cst341.model.Customer;
import edu.gcu.cst341.services.BankService;
import edu.gcu.cst341.services.CustomerService;
import edu.gcu.cst341.services.DataService;
import edu.gcu.cst341.services.LoginService;

/**
 * Performs a series of transactions and validates the final balance
 */
public class ReportsTests {
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
	public void finalBalancesTest() {
		makeValidCustomer();
		int custId = cs.createNewCustomer(testCust);
		assertTrue(custId > 0);
		double openChkBal = testCust.getChecking().getAccountBalance();
		double openSavBal = testCust.getSaving().getAccountBalance();
		double openLoanBal = testCust.getLoan().getAccountBalance();

		//CHECKING TRANSACTIONS
		bs.doTransaction(testCust, "chk", Account.DEPOSIT, 111.11);
		bs.doTransaction(testCust, "chk", Account.WITHDRAWAL, 222.22);
		bs.doTransaction(testCust, "chk", Account.DEPOSIT, 333.33);
		bs.doTransfer(testCust, "chk", 444.44, "sav");
		bs.doTransfer(testCust, "chk", 555.55, "loan");
		bs.doTransaction(testCust, "chk", Account.WITHDRAWAL, 6666.66);
		bs.doTransaction(testCust, "chk", Account.DEPOSIT, 7777.77);
		double endChkBal = openChkBal + 111.11 - 222.22 + 333.33 - 444.44 - 555.55 - 6666.66 - 25 + 7777.77 + 444.44 + 444.44;
		//3697.22
		//SAVING TRANSACTIONS
		bs.doTransaction(testCust, "sav", Account.DEPOSIT, 1111.11);
		bs.doTransaction(testCust, "sav", Account.WITHDRAWAL, 222.22);
		bs.doTransaction(testCust, "sav", Account.DEPOSIT, 3333.33);
		bs.doTransfer(testCust, "sav", 444.44, "chk");
		bs.doTransfer(testCust, "sav", 555.55, "loan");
		System.err.println("Open savings balance = " + openSavBal);
		double endSavBal = openSavBal + 1111.11 - 222.22 + 3333.33 - 444.44 - 555.55 + 444.44 + 555.55;
		//4722.22
		//LOAN TRANSACTIONS
		bs.doTransaction(testCust, "loan", Account.DEPOSIT, 111.11);
		bs.doTransaction(testCust, "loan", Account.WITHDRAWAL, 2222.22);
		bs.doTransaction(testCust, "loan", Account.DEPOSIT, 333.33);
		bs.doTransfer(testCust, "loan", 444.44, "chk");
		bs.doTransfer(testCust, "loan", 555.55, "sav");
		double endLoanBal = openLoanBal + 111.11 - 2222.22 + 333.33 - 444.44 - 555.55 + 555.55 + 555.55;
		//-2666.67
		
		//Check the Customer object balances against the truth
		assertEquals(endChkBal, testCust.getChecking().getAccountBalance(), 0.004);
		assertEquals(endSavBal, testCust.getSaving().getAccountBalance(), 0.004);
		assertEquals(endLoanBal, testCust.getLoan().getAccountBalance(), 0.004);
		
		//Check the database balances against the truth
		boolean gotBalances = ds.dbRetrieveCustomerBalancesById(testCust);
		assertTrue(gotBalances);
		assertEquals(endChkBal, testCust.getChecking().getAccountBalance(), 0.004);
		assertEquals(endSavBal, testCust.getSaving().getAccountBalance(), 0.004);
		assertEquals(endLoanBal, testCust.getLoan().getAccountBalance(), 0.004);		
		
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
