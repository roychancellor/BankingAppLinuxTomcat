package edu.gcu.cst235.milestone.customertests;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Test;
import edu.gcu.cst235.milestone.model.Customer;

/**
 * JUnit test that verifies the compareTo method for Customer objects is functional
 */
public class TestCustomerCompareTo {

	/**
	 * Tests the compareTo method in the Customer class to ensure it returns the correct
	 * sign of the number for sorting Customer objects when calling Collections.sort
	 */
	@Test
	public final void testCompareTo() {
		System.out.println("\nTesting the compareTo method for sorting Customer objects");
		
		Customer cControl = new Customer(1,"MMM", "MMM", new Date());
		Customer cEqual = new Customer(1,"MMM", "MMM", new Date());
		Customer cLastGT = new Customer(1,"MMM", "ZZZ", new Date());
		Customer cLastLT = new Customer(1,"MMM", "AAA", new Date());
		Customer cLastEqFirstGT = new Customer(1,"ZZZ", "MMM", new Date());
		Customer cLastEqFirstLT = new Customer(1,"AAA", "MMM", new Date());
		Customer cBothGT = new Customer(1,"ZZZ", "ZZZ", new Date());
		Customer cBothLT = new Customer(1,"AAA", "AAA", new Date());
		
		assertEquals("Equal names", 0, cControl.compareTo(cEqual));
		assertEquals("Last greater, first equal", -13, cControl.compareTo(cLastGT));
		assertEquals("Last less, first equal", 12, cControl.compareTo(cLastLT));
		assertEquals("Last equal, first greater", -13, cControl.compareTo(cLastEqFirstGT));
		assertEquals("Last equal, first less", 12, cControl.compareTo(cLastEqFirstLT));
		assertEquals("Both greater", -13, cControl.compareTo(cBothGT));
		assertEquals("Both less", 12, cControl.compareTo(cBothLT));
	}

}
