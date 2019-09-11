package edu.gcu.cst341.controller;

import org.springframework.stereotype.Service;

import edu.gcu.cst341.model.Customer;

@Service
public class CustomerService {
	/**
	 * Checks for the existence of a username in the credentials database
	 * @param usernameToCheck the username to look up
	 * @return true if the username is found; false if not
	 */
	public boolean userNameExists(String usernameToCheck) {
		DataService ds = new DataService();
		if(ds.dbRetrieveCustomerByUsername(usernameToCheck) > 0) {
			ds.close();
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a new customer in the database by writing customer information
	 * to the customers table, login credentials to the credentials table,
	 * and account information to the customer_accounts table and
	 * customer_transactions table
	 * @param cust the new Customer object
	 * @return the number of records written to the database (5 if successful)
	 */
	public int createNewCustomer(Customer cust) {
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
			
			//Create the new customer's hashed credentials
			createHashedCredentials(cust);
			//Write the hashed credentials to the database
			numRec = ds.dbCreateCustomerCredentials(cust);
			
			if(numRec > 0) {
				//Create the accounts and balances in the customer_accounts table
				numRec = ds.dbCreateCustomerAccounts(cust);
				System.out.println("\nCREATED ACCOUNT NUMBERS AND BALANCES\n");
				if(numRec > 0) {
					//Create opening balance transactions for each account
					numRec = createOpeningBalanceTransactions(dbCustId, cust);
					System.out.println("\nCREATED OPENING BALANCE TRANSACTIONS\n");
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
	 * Creates hashed credentials from the customer's plain-text values
	 * @param cust the new Customer being created
	 */
	private void createHashedCredentials(Customer cust) {
		//Make a salt for the new customer
		cust.setHashedSalt(PasswordService.generateSalt(512).get());
		//Hash the customer's plain-text password with the salt and replace the Customer password
		cust.setPassword(PasswordService.hashPassword(cust.getPassword(), cust.getHashedSalt()).get());
	}
	
	/**
	 * Writes opening balance transactions to the database
	 * @param dbCustId the database-generated customer Id for the new customer being created
	 * @param cust the new Customer object being created
	 * @return the number of records written (should be 3 if successful)
	 */
	private int createOpeningBalanceTransactions(int dbCustId, Customer cust) {
		DataService ds = new DataService();
		int numRec = 0;
		
		//Write the opening balance transactions to the database
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
		
		ds.close();
		
		return numRec;
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
	public boolean validatePasswordsMatch(String password, String passCompare) {
		//Validate the inputs before testing
		if(password == null || passCompare == null) {
			//Should never occur because of bean validation, but check anyway
			return false;
		}
		else if(password.equals("") || passCompare.equals("")) {
			//Should never occur because of bean validation, but check anyway
			return false;
		}
		else if(password.equals(passCompare)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Updates an existing customer in the database by writing customer information
	 * to the customers table and login credentials to the credentials table.
	 * Accounts and transactions are NOT allowed to change by business rule
	 * @param cust the updated Customer object
	 * @return the number of records written to the database
	 */
	public int updateCustomer(Customer cust) {
		int dbCustId = 0;
		
		//Open a connection to the database
		DataService ds = new DataService();
		
		//Update the customer in the customers table
		int numRec = ds.dbUpdateCustomerContactInfo(cust);
		System.out.println("numRec = " + numRec + ", UPDATED CUSTOMER: " + cust.toString() + "\n");
		
		if(numRec > 0) {
			//hash the customer's new credentials (even if they were unchanged)
			createHashedCredentials(cust);
			//Update the credentials in the database
			numRec = ds.dbUpdateCustomerCredentials(cust);
			
			if(numRec == 0) {
				System.err.println("ERROR: Unable to update customer credentials in database!!!");
			}
		}
		else {
			System.err.println("ERROR: Unable to update customer in database!!!");
		}
		
		//Close the database connection
		ds.close();
		
		return dbCustId;
	}
	
	/**
	 * Deletes an existing customer completely from the database
	 * @param cust the existing customer object to delete
	 * @return the number of records deleted (>0 if successful, 0 if not or if cust is null)
	 */
	public int deleteExistingCustomer(Customer cust) {
		int numRec = 0;
		int customerId = cust.getCustId();
		DataService ds = new DataService();
		
		//Verify the customer with the passed-in id exists
		if(ds.dbRetrieveCustomerById(customerId) == null) {
			return numRec;
		}
		else {
			//Delete all transactions
			numRec = ds.dbDeleteCustomerTransactionsById(customerId);
			//Delete all accounts
			numRec += ds.dbDeleteCustomerAccountsById(customerId);
			//Delete all credentials
			numRec += ds.dbDeleteCustomerCredentialsById(customerId);
			//Delete all customer information
			numRec += ds.dbDeleteCustomerById(customerId);
		}
		
		ds.close();
		
		return numRec;
	}
}
