package edu.gcu.cst235.milestone.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.gcu.cst235.milestone.customertests.TestCustomerCompareTo;
import edu.gcu.cst235.milestone.loantests.TestLoanPaymentCalculation;
import edu.gcu.cst235.milestone.menutests.TestCustomerLogin;
import edu.gcu.cst235.milestone.menutests.TestViewCustomerLogin;

@RunWith(Suite.class)
@SuiteClasses({ TestCustomerLogin.class, TestCustomerCompareTo.class, TestLoanPaymentCalculation.class, TestViewCustomerLogin.class })
/**
 * Junit Test Suite for the banking app
 */
public class TestSuite {
	
}
