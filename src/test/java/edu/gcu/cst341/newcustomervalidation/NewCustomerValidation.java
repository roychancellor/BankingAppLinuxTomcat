/**
 * Tests new customer functionality
 * 1. New customer information is correctly written to the database
 * 2. Creating a new customer with a username that exists in the database produces an error
 */
package edu.gcu.cst341.newcustomervalidation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.gcu.cst341.controller.CustomerService;
import edu.gcu.cst341.controller.DataService;
import edu.gcu.cst341.model.Customer;

public class NewCustomerValidation {
	private CustomerService cs;
	private Customer testCust;
	private DataService ds;
	
	@Before
	public void beforeTest() {
		cs = new CustomerService();
		ds = new DataService();
		testCust = new Customer();
	}

	@Test
	public void testCheckForExistingUsername() {
		
		//TEST 1A: Non-existing username --> returns false
		assertFalse(cs.userNameExists("non-existing username"));
		//TEST 1B: Non-existing username --> returns false
		assertFalse(cs.userNameExists(""));
		//TEST 1C: Non-existing username --> returns false
		assertFalse(cs.userNameExists(null));
		//TEST 2: Existing username --> returns true
		assertTrue(cs.userNameExists("roychance"));
	}
	
	@Test
	public void testNewCustomerIntoDatabase() {
		//Four things need to happen when user hits Submit:
		//1. Customer identification written to customers table
		//2. Customer login credentials written to credentials table
		//3. Customer account numbers and opening balances written to customer_accounts table
		//4. Opening balance transactions written to customer_transactions table
		
		//Make a new customer and populate all fields with VALID information
		//(form validation tests occur in separate package )
		makeValidCustomer();
		System.out.println("Test customer:\n" + testCust.toString());
		
		//Perform the method to create a test customer in the database
		//and get the new customerId
		int dbCustId = cs.createNewCustomer(testCust);
		System.out.println("Result customer ID = " + dbCustId);

		//Retrieve information about the customer from the database
		//and test it against the truth
		Customer resultCust = ds.dbRetrieveCustomerById(dbCustId);
		System.out.println("Result customer:\n" + resultCust.toString());
		assertTrue(resultCust.getFirstName().equals(testCust.getFirstName()));
		
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
