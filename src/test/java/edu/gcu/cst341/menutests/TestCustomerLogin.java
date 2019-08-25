package edu.gcu.cst341.menutests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.gcu.cst341.controller.Bank;
import edu.gcu.cst341.view.Menus;

/**
 * JUnit test that verifies the customer login method is correct
 */
public class TestCustomerLogin {

	/**
	 * Tests the customer login method
	 */
	@Test
	public final void testTestDoCustomerLogin() {
		System.out.println("\nTesting the doCustomerLogin method...");
		Bank testBank = new Bank("Test Bank");
		
		//TEST 1: User elects to cancel --> returns Menus.MENU_EXIT
		System.out.println("TEST 1: user selects 0 to exit");
		assertEquals(Menus.MENU_EXIT, testBank.testDoCustomerLogin());
		//TEST 2: User elects to log in with username tw, password hash --> returns customerId = 1
		System.out.println("\nTEST 2: user logs in as tw with password hash");
		assertEquals(1, testBank.testDoCustomerLogin());
		//TEST 3: User elects to log in with username roychance600, password abc123 --> returns customerId = 10
		System.out.println("\nTEST 3: user logs in with roychance600 and password abc123");
		assertEquals(10, testBank.testDoCustomerLogin());
		//TEST 4: User elects to log in incorrectly 3 times --> returns Menus.MENU_EXIT
		System.out.println("\nTEST 4: user fails to enter correct credentials 3 times");
		assertEquals(Menus.MENU_EXIT, testBank.testDoCustomerLogin());
	}
}
