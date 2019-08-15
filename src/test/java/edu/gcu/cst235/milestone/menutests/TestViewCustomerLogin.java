package edu.gcu.cst235.milestone.menutests;

import static org.junit.Assert.*;
import org.junit.Test;
import edu.gcu.cst235.milestone.view.Menus;

/**
 * Tests whether the class returns the user-entered login credentials correctly
 */
public class TestViewCustomerLogin {

	/**
	 * Test method for {@link edu.gcu.cst235.milestone.view.Menus#viewCustomerLogin()}.
	 */
	@Test
	public final void testViewCustomerLogin() {
		System.out.println("\nTesting tht the viewCustomerLogin method returns what the user typed...");
		String[] userCredentials = Menus.viewCustomerLogin();
		System.out.println("TEST 1: Verify method returns what the user typed in");
		System.out.println("Username should equal \"username\"");
		assertEquals("username", userCredentials[0]);
		System.out.println("Password should equal \"password\"");
		assertEquals("password", userCredentials[1]);
		System.out.println("\nTEST 2: Verify method returns null when user enters 0 to exit");
		System.out.println("Credentials should equal null");
		String[] nullCreds = Menus.viewCustomerLogin();
		assertNull(null, nullCreds);
	}
}
