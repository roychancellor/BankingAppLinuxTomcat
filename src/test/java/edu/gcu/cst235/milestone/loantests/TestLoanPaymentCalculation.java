package edu.gcu.cst235.milestone.loantests;

import static org.junit.Assert.*;
import org.junit.Test;
import edu.gcu.cst235.milestone.model.Loan;

/**
 * JUnit test that verifies loan payment calculation is correct
 */
public class TestLoanPaymentCalculation {

	@Test
	public final void testComputeMonthlyPayment() {
		System.out.println("\nTesting the loan payment calculation");
		Loan testLoan = new Loan("aaaaaaaaa", -5000, 25, 0.08, 10);
		//Check whether the monthly payment returns the correct value within less than half a cent
		//Checking a double requires an allowable delta, so set at 0.004 for this test
	    assertEquals("Monthly payment should be $60.66", 60.66, testLoan.computeMonthlyPayment(), 0.004);
	}

}
