package edu.gcu.cst341.controller;

import org.springframework.stereotype.Service;

import edu.gcu.cst341.model.Customer;

@Service
public class CustomerService {
	
	/**
	 * Retrieves all customer information and account balances from the database
	 * @param custId the customerId to lookup in the database
	 * @return a Customer object with populated data fields
	 */
	protected Customer getCustomerInfoAndBalances(int custId) {
		Customer customer;
		
		//Open a connection to the database
		DataService ds = new DataService();

		//Get the customer information
		customer = ds.dbRetrieveCustomerById(custId);
		
		//Get the customer account balances
		if(customer != null) {
			ds.dbRetrieveCustomerBalancesById(customer);
		}
		
		//Close the database connection
		ds.close();
		
		return customer;
	}
}
