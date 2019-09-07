package edu.gcu.cst341.controller;

import org.springframework.stereotype.Service;

import edu.gcu.cst341.model.Customer;

@Service
public class CustomerService {
	
	/**
	 * Creates a new customer in the database by writing customer information
	 * to the customers table, login credentials to the credentials table,
	 * and account information to the customer_accounts table and
	 * customer_transactions table
	 * @param cust the new Customer object
	 * @return the number of records written to the database (5 if successful)
	 */
	protected int createNewCustomer(Customer cust) {
		int dbCustId = 0;
		
		//Open a connection to the database
		DataService ds = new DataService();
		
		//Create the customer in the customers table
		dbCustId = ds.dbCreateCustomer(cust.getLastName(), cust.getFirstName(), cust.getEmailAddress(), cust.getPhoneNumber());
		System.out.println("dbCustId = " + dbCustId + ", CREATED CUSTOMER: " + cust.toString() + "\n");
		
		int numRec = 0;
		if(dbCustId > 0) {
			//Set the customerId for the Customer object
			cust.setCustId(dbCustId);
			
			//Retrieve the salt from the database
			String salt = ds.dbRetrieveSaltKey(1);
			//Hash the plain-text password with the salt
			String hashedPass = PasswordService.hashPassword(cust.getPassword(), salt).get();
			//Replace the plain-text password in the Customer object with the hashed password
			cust.setPassword(hashedPass);
			//Create the credentials in credentials table
			numRec = ds.dbCreateCustomerCredentials(dbCustId, cust.getUsername(), salt, hashedPass);
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
	
	/**
	 * Simple helper method to verify a new customer entered the same password twice
	 * @param password the first password entered
	 * @param passCompare the second password entered
	 * @return true if the passwords match; false if not
	 */
	protected boolean validatePasswordsMatch(String password, String passCompare) {
		if(password.equals(passCompare)) {
			return true;
		}
		return false;
	}
}
