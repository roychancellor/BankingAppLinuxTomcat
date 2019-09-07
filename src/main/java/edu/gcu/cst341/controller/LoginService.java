package edu.gcu.cst341.controller;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
	/**
	 * logs in a customer by checking user-entered credential against the database
	 * @return the customer id if successful and Menus.MENU_EXIT if unsuccessful after 3 tries
	 */
	protected int validateCredentials(String username, String password) {
		int customerId = 0;
		//Query the credentials database for the customer credentials
		System.out.println("validateCredentials opening a DB connection");
		DataService ds = new DataService();
		if(ds.isConnectedToDb()) {
			//Retrieve the salt from the database
			String salt = ds.dbRetrieveSaltKey(1);
			//Hash the plain-text password with the salt
			String hashedPassToSearch = PasswordService.hashPassword(password, salt).get();
			customerId = ds.dbRetrieveCustomerByLoginCredentials(username, salt, hashedPassToSearch);
			System.out.println("validateCredentials closing a DB connection");
			ds.close();
		}
		return customerId;
	}
	/**
	 * logs in a customer by checking user-entered credential against the database
	 * @return the customer id if successful and Menus.MENU_EXIT if unsuccessful after 3 tries
	 */
//	protected int validateCredentials(String username, String password) {
//		int customerId = 0;
//		//Query the credentials database for the customer credentials
//		System.out.println("validateCredentials opening a DB connection");
//		DataService ds = new DataService();
//		if(ds.isConnectedToDb()) {
//			customerId = ds.checkLoginCredentials(username, "salt", password);
//			System.out.println("validateCredentials closing a DB connection");
//			ds.close();
//		}
//		return customerId;
//	}
	
	/**
	 * Converts a String value that represents a number into a double, if possible
	 * If not possible, catches the thrown exceptions
	 * @param amount the String amount to convert
	 * @return the double representation of the String if successful or -1 if not possible
	 */
	protected double stringToDouble(String amount) {
		double convertedAmount = -1;
		
		try {
			convertedAmount = Double.parseDouble(amount);
		}
		catch(NullPointerException e) {
		}
		catch(NumberFormatException e) {
		}
		catch(Exception e) {
		}
		return convertedAmount;
	}	
}
