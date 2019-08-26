package edu.gcu.cst341.spring;

import org.springframework.stereotype.Service;

import edu.gcu.cst341.model.DataSource;
import edu.gcu.cst341.view.Menus;

@Service
public class LoginService {
	/**
	 * logs in a customer by checking user-entered credential against the database
	 * @return the customer id if successful and Menus.MENU_EXIT if unsuccessful after 3 tries
	 */
	protected int validateCredentials(String username, String password) {
		int customerId = Menus.MENU_EXIT;
		//Query the credentials database for the customer credentials
		DataSource ds = new DataSource(false, false);
		customerId = ds.checkLoginCredentials(username, "salt", password);
		ds.close();
		return customerId;
	}
}
