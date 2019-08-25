package edu.gcu.cst341.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.gcu.cst341.customertests.TestCustomerCompareTo;
import edu.gcu.cst341.loantests.TestLoanPaymentCalculation;
import edu.gcu.cst341.menutests.TestCustomerLogin;
import edu.gcu.cst341.menutests.TestViewCustomerLogin;

@RunWith(Suite.class)
@SuiteClasses({ TestCustomerLogin.class, TestCustomerCompareTo.class, TestLoanPaymentCalculation.class, TestViewCustomerLogin.class })
/**
 * Junit Test Suite for the banking app
 */
public class TestSuite {
	
}
