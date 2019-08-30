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
			customerId = ds.checkLoginCredentials(username, "salt", password);
			System.out.println("validateCredentials closing a DB connection");
			ds.close();
		}
		return customerId;
	}
}
