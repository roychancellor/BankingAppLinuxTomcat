/**
 * Unit tests for form inputs (login, amounts, new customer)
 */
package edu.gcu.cst341.formvalidation;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import edu.gcu.cst341.model.Customer;
import edu.gcu.cst341.services.CustomerService;
import edu.gcu.cst341.viewforms.AmountForm;
import edu.gcu.cst341.viewforms.LoginForm;

/**
 * Tests the form field validation for creating a new customer,
 * logging in an existing customer, and amounts for transactions
 */
public class FormValidationTests {
	private Validator validator;
	private Set<ConstraintViolation<LoginForm>> loginViolations;
	private Set<ConstraintViolation<AmountForm>> amountViolations;
	private Set<ConstraintViolation<Customer>> custViolations;
	
	@Before
	public void init() {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		this.validator = vf.getValidator();
	}
	
	@Test
	public void testLoginForm() {
		//Requires both fields to be not null and not blank
		LoginForm lf = new LoginForm();
		
		//TEST 1: Both valid. assert TRUE that violations is empty
		lf.setUsername("username");
		lf.setPassword("password");
		this.loginViolations = this.validator.validate(lf);
		assertTrue("Valid username, valid password: violations should be empty", loginViolations.isEmpty());
		
		//TEST 2: Both invalid. assert FALSE that violations is empty
		lf.setUsername("");
		lf.setPassword("");
		this.loginViolations.clear();
		this.loginViolations = this.validator.validate(lf);
		System.out.println(loginViolations.toString());
		System.out.println(loginViolations.isEmpty());
		assertFalse("INVALID username, INVALID password: violations should NOT be empty", loginViolations.isEmpty());
		
		//TEST 3: Username invalid, password valid. assert FALSE that violations is empty
		lf.setUsername("");
		lf.setPassword("password");
		this.loginViolations.clear();
		this.loginViolations = this.validator.validate(lf);
		System.out.println(loginViolations.toString());
		System.out.println(loginViolations.isEmpty());
		assertFalse("INVALID username, valid password: violations should NOT be empty", loginViolations.isEmpty());		
		
		//TEST 4: Username valid, password invalid. assert FALSE that violations is empty
		lf.setUsername("username");
		lf.setPassword("");
		this.loginViolations.clear();
		this.loginViolations = this.validator.validate(lf);
		System.out.println(loginViolations.toString());
		System.out.println(loginViolations.isEmpty());
		assertFalse("valid username, INVALID password: violations should NOT be empty", loginViolations.isEmpty());		
	}
	
	@Test
	public void testAmountForm() {
		//Requires amount field to be not blank
		AmountForm af = new AmountForm();
		
		//TEST 1: Valid amount string: assert TRUE that violations is empty
		af.setAmount("123.45");
		this.amountViolations = this.validator.validate(af);
		assertTrue("Valid amount: violations should be empty", amountViolations.isEmpty());
				
		//TEST 2: INVALID amount string: assert FALSE that violations is empty
		af.setAmount("");
		this.amountViolations.clear();
		this.amountViolations = this.validator.validate(af);
		assertFalse("INVALID amount: violations should NOT be empty", amountViolations.isEmpty());
	}
	
	@Test
	public void testNewCustomerForm() {
		//Requires amount field to be not blank
		Customer c = new Customer();
		
		//TEST 1: Valid customer: assert TRUE that violations is empty
		resetValidCustomer(c);
		this.custViolations = this.validator.validate(c);
		assertTrue("Valid customer: violations should be empty", custViolations.isEmpty());				

		//TEST 2: Invalid customer: assert FALSE that violations is empty
		c.setFirstName("");
		this.custViolations.clear();
		this.custViolations = this.validator.validate(c);
		assertFalse("INVALID customer: violations should NOT be empty", custViolations.isEmpty());				
		//TEST 3: Invalid customer: assert FALSE that violations is empty
		resetValidCustomer(c);
		c.setLastName("");
		this.custViolations.clear();
		this.custViolations = this.validator.validate(c);
		assertFalse("INVALID customer: violations should NOT be empty", custViolations.isEmpty());				
		//TEST 4: Invalid customer: assert FALSE that violations is empty
		resetValidCustomer(c);
		c.setUsername("12345");
		this.custViolations.clear();
		this.custViolations = this.validator.validate(c);
		assertFalse("INVALID customer: violations should NOT be empty", custViolations.isEmpty());				
		//TEST 5: Invalid customer: assert FALSE that violations is empty
		resetValidCustomer(c);
		c.setPassword("1234567");
		this.custViolations.clear();
		this.custViolations = this.validator.validate(c);
		assertFalse("INVALID customer: violations should NOT be empty", custViolations.isEmpty());				
		//TEST 6: Invalid customer: assert FALSE that violations is empty
		resetValidCustomer(c);
		c.setPassCompare("1234567");
		this.custViolations.clear();
		this.custViolations = this.validator.validate(c);
		assertFalse("INVALID customer: violations should NOT be empty", custViolations.isEmpty());				
		//TEST 7A: Invalid customer: assert FALSE that violations is empty
		resetValidCustomer(c);
		c.setEmailAddress("e.com");
		this.custViolations.clear();
		this.custViolations = this.validator.validate(c);
		assertFalse("INVALID customer: violations should NOT be empty", custViolations.isEmpty());				
		//TEST 7B: Invalid customer: assert FALSE that violations is empty
		resetValidCustomer(c);
		c.setEmailAddress("e@");
		this.custViolations.clear();
		this.custViolations = this.validator.validate(c);
		assertFalse("INVALID customer: violations should NOT be empty", custViolations.isEmpty());				
		//TEST 8A: Invalid customer: assert FALSE that violations is empty
		resetValidCustomer(c);
		c.setPhoneNumber("123456789");
		this.custViolations.clear();
		this.custViolations = this.validator.validate(c);
		assertFalse("INVALID customer: violations should NOT be empty", custViolations.isEmpty());				
		//TEST 8B: Invalid customer: assert FALSE that violations is empty
		resetValidCustomer(c);
		c.setPhoneNumber("1234567890123");
		this.custViolations.clear();
		this.custViolations = this.validator.validate(c);
		assertFalse("INVALID customer: violations should NOT be empty", custViolations.isEmpty());				
		//TEST 8C: Invalid customer: assert FALSE that violations is empty
		resetValidCustomer(c);
		c.setPhoneNumber("(123)456-7890");
		this.custViolations.clear();
		this.custViolations = this.validator.validate(c);
		assertFalse("INVALID customer: violations should NOT be empty", custViolations.isEmpty());				
	}
	
	/**
	 * Helper method for customer form test
	 * @param c Customer object
	 */
	private void resetValidCustomer(Customer c) {
		c.setFirstName("F");
		c.setLastName("L");
		c.setUsername("123456");
		c.setPassword("12345678");
		c.setPassCompare("12345678");
		c.setEmailAddress("e@e.com");
		c.setPhoneNumber("123-456-7890");
	}
	
	@Test
	public void testPasswordsMatch() {
		CustomerService cs = new CustomerService();
		assertTrue(cs.validatePasswordsMatch("pass", "pass"));
		assertFalse(cs.validatePasswordsMatch("pass1", "pass2"));
		assertFalse(cs.validatePasswordsMatch("", ""));
		assertFalse(cs.validatePasswordsMatch(null, ""));
		assertFalse(cs.validatePasswordsMatch("", null));
		assertFalse(cs.validatePasswordsMatch(null, null));
		assertFalse(cs.validatePasswordsMatch(null, "pass2"));
		assertFalse(cs.validatePasswordsMatch("pass1", null));
	}
	
}
