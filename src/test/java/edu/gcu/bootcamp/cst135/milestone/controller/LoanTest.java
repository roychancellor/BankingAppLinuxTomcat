package edu.gcu.bootcamp.cst135.milestone.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoanTest {

	@Test
	public final void testComputeMonthlyPayment() {
		Loan tester = new Loan("aaaaaaaaa", -5000, 25, 0.08, 10);
		//Check whether the monthly payment returns the correct value within less than half a cent
		//Checking a double requires an allowable delta, so set at 0.004 for this test
	    assertEquals("Monthly payment should be $60.66", 60.66, tester.computeMonthlyPayment(), 0.004);
	}

}
