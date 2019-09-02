package edu.gcu.cst341.controller;

import org.springframework.stereotype.Service;

import edu.gcu.cst341.model.Customer;

@Service
public class CustomerService {
	
	protected int createNewCustomer(Customer cust) {
		int dbCustId = 0;
		
		//Open a connection to the database
		DataService ds = new DataService();
		
		//Create the customer in the customers table
		dbCustId = ds.dbCreateCustomer(cust.getLastName(), cust.getFirstName(), cust.getEmailAddress(), cust.getPhoneNumber());
		System.out.println("dbCustId = " + dbCustId + ", CREATED CUSTOMER: " + cust.toString() + "\n");
		
		if(dbCustId > 0) {
			//Set the customerId for the Customer object
			cust.setCustId(dbCustId);
			
			//Create the credentials in credentials table
			int numRec = 0;
			numRec = ds.dbCreateCustomerCredentials(dbCustId, cust.getUsername(), "salt", cust.getPassword());
			System.out.println("\nCREATED CREDENTIALS\n");
			
			if(numRec > 0) {
				//Create the accounts and balances in the customer_accounts table
				//Create opening balance transactions for each account
				numRec = ds.dbCreateCustomerAccounts(cust);
				System.out.println("\nCREATED ACCOUNT NUMBERS AND BALANCES\n");
				if(numRec > 0) {
					numRec = 0;
					numRec += ds.dbCreateCustomerTransactions(
						dbCustId,
						cust.getChecking().getAccountNumber(),
						cust.getChecking().getAccountBalance());
					numRec += ds.dbCreateCustomerTransactions(
						dbCustId,
						cust.getSaving().getAccountNumber(),
						cust.getSaving().getAccountBalance());
					numRec += ds.dbCreateCustomerTransactions(
						dbCustId,
						cust.getLoan().getAccountNumber(),
						cust.getLoan().getAccountBalance());
					if(numRec != 3) {
						System.out.println("ERROR: Unable to write opening balance transactions");
					}
				}
				else {
					System.out.println("ERROR: Unable to write customer accounts to database");
				}
			}
			else {
				System.out.println("ERROR: Unable to write new customer credentials to database");
			}
		}
		else {
			System.out.println("ERROR: Unable to write new customer to database!!!");
		}
		
		//Close the database connection
		ds.close();
		
		return dbCustId;
	}
	
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
