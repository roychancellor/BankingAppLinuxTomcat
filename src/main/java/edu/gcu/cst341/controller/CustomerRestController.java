package edu.gcu.cst341.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.gcu.cst341.model.Customer;
import edu.gcu.cst341.services.CustomerService;

/**
 * REST controller to get customer information from the customers database table
 * and return a JSON string of basic customer information (name, e-mail, phone, username)
 */
@ComponentScan(basePackages = {"edu.gcu.cst341"})
@RestController
public class CustomerRestController {
	@Autowired
	CustomerService cs;
	
	@RequestMapping(value="/customer", method=RequestMethod.GET)
	public Customer getCustomerInfo(@RequestParam("id") int id) {
		return cs.getCustomerInfoAndBalances(id);
	}
}
