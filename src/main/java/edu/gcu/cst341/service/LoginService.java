package edu.gcu.cst341.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
	/**
	 * logs in a customer by checking user-entered credential against the database
	 * @return the customer id if successful, -1 if the passwords do not match, or 0 if username not found
	 */
	public int validateCredentials(String username, String plainTextPassword) {
		int customerId = 0;
		//Query the credentials database for the customer credentials
		System.out.println("validateCredentials opening a DB connection");
		DataService ds = new DataService();
		if(ds.isConnectedToDb()) {
			//Look up the customer by username; if found, continue validating credentials
			customerId = ds.dbRetrieveCustomerByUsername(username);
			
			//If username found, continue validating credentials
			if(customerId > 0) {
				//Retrieve the hashed credentials from the credentials table in the database
				//element 0 is the hashed salt and element 1 is the hashed password
				String[] hashedCredentials = ds.dbRetrieveCustomerHashedCredentials(customerId);
				
				//Check if the passwords DO NOT match
				if(!PasswordService.verifyPassword(plainTextPassword, hashedCredentials[0], hashedCredentials[1])) {
					customerId = -1;
				}
			}
			else {
				System.out.println("\nCustomer with username " + username + " DOES NOT EXIST");
			}
			System.out.println("validateCredentials closing a DB connection");
			ds.close();
		}
		else {
			System.out.println("validateCredentials: FAILED TO CONNECT TO DATABASE");
		}
		
		return customerId;
	}	
}
