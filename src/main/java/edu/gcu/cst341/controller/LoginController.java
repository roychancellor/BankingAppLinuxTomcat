package edu.gcu.cst341.controller;

//MAKE SURE THE POM IS NOT IN TEST MODE
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.gcu.cst341.model.Account;
import edu.gcu.cst341.model.Checking;
import edu.gcu.cst341.model.Customer;
import edu.gcu.cst341.model.Loan;
import edu.gcu.cst341.model.Transaction;
import edu.gcu.cst341.viewforms.AmountForm;
import edu.gcu.cst341.viewforms.LoginForm;
import edu.gcu.cst341.viewforms.PasswordForm;

/**
 * The MAIN CONTROLLER for the bank application
 * Controls all HTTP GET and POST requests
 * Keeps a Customer object in the HTTP session until customer logs out
 * All return statements are the names of Java Server Page (jsp) files
 */
@Controller
@Scope("session")
//Give access to the customer object throughout the session
@SessionAttributes("customer")
public class LoginController {
	//Allows Spring to take over control of making these objects
	@Autowired
	LoginService LoginService;
	@Autowired
	CustomerService CustomerService;
	@Autowired
	BankService BankService;
	
	//Spring will make these objects when needed and will keep customer in the session
	//until the session is completed
	/**
	 * Customer object that is kept in session until the customer logs out
	 * @return a new Customer object
	 */
	@Valid @ModelAttribute("customer")
	public Customer customer() {
		return new Customer();
	}
	
	/**
	 * Creates a LoginForm object as needed
	 * @return a new LoginForm object
	 */
	@Valid @ModelAttribute("loginform")
	public LoginForm loginform() {
		return new LoginForm();
	}
	
	/**
	 * Creates a new PasswordForm object as needed
	 * @return a new PasswordForm object
	 */
	@Valid @ModelAttribute("passwordform")
	public PasswordForm passwordform() {
		return new PasswordForm();
	}
	
	/**
	 * HTTP GET request handler for /newcustomer to direct to the new customer jsp
	 * @param map the current ModelMap
	 * @return a string for the customer-new jsp
	 */
	@RequestMapping(value = "/newcustomer", method = RequestMethod.GET)
	public String showNewCustomerScreen(ModelMap map) {
		return "customer-new";
	}
	
	/**
	 * HTTP POST request handler for /newcustomer to respond to the new customer form POST
	 * @param customer the Customer object currently in session representing the new customer
	 * @param result the BindingResult from the validation of the form fields of Customer
	 * @param map the current ModelMap
	 * @return the jsp to return based on the logic of the method (if all is well, redirect to login)
	 */
	@RequestMapping(value="/newcustomer", method = RequestMethod.POST)
	public String processNewCustomer(
		@Valid @ModelAttribute("customer") Customer customer,
		BindingResult result,
		ModelMap map) {
		
		String jspToReturn = "customer-confirm";
		
		//If the BindingResult object has errors, return to the new customer page
		if(result.hasErrors()) {
			System.err.println("\n/newcustomer POST: BindingResult has errors; returning to new customer page");
			jspToReturn = "customer-new";
		}
		else {
			//Put the Customer object in the ModelMap
			System.out.println("/newcustomer POST: customer =\n" + customer.toString());
			map.addAttribute("customer", customer);
			System.out.println("/newcustomer POST: customer from map.get =\n"
				+ map.get("customer").toString());
			System.out.println("/newcustomer POST: password =" + customer.getPassword());
			System.out.println("/newcustomer POST: passCompare =" + customer.getPassCompare());
			
			//Validate the passwords match before proceeding
			if(!CustomerService.validatePasswordsMatch(customer.getPassword(), customer.getPassCompare())) {
				System.err.println("/newcustomer POST: PASSWORDS DO NOT MATCH");
				//Populate the model map and return to the new customer form
				map.addAttribute("passmatcherror", "Passwords do not match, please re-enter");
				map.addAttribute("password", "");
				map.addAttribute("passCompare", "");
				jspToReturn = "customer-new";
			}
		}
		System.out.println("/newcustomer POST: Redirecting to " + jspToReturn);
		return jspToReturn;
	}
	
	/**
	 * HTTP POST handler for new customer confirmation
	 * @param customer the new Customer object
	 * @param map the current ModelMap
	 * @return the jsp to return (redirect to login if all is well)
	 */
	@RequestMapping(value = "/confirmcustomer", method = RequestMethod.POST)
	public String confirmcustomerScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToReturn = "customer-success";
		System.out.println("\n/confirmcustomer POST: Back from confirmcustomer.jsp:");
		System.out.println("/confirmcustomer POST: customer =\n" + customer.toString());
		
		//Check for an existing user with the same username
		if(CustomerService.userNameExists(customer.getUsername())) {
			System.err.println("/confirmcustomer POST: the username "
				+ customer.getUsername() + " already exists\nGoing back to newcustomer.jsp");
			map.put("customer", customer);
			map.addAttribute("errorMessage", "ERROR: A customer with username "
				+ customer.getUsername() + " already exists. Choose another username and re-submit");
			jspToReturn = "customer-new";
		}
		else {
			//Username is valid, so create the new customer object in the database
			int custId = CustomerService.createNewCustomer(customer);
			if(custId > 0) {
				System.out.println("/confirmcustomer POST: created new customer:\n" + customer.toString());
			}
		}
		
		return jspToReturn;
	}
	
	/**
	 * HTTP GET handler for the main login page for existing customers
	 * @param map the ModelMap
	 * @return the login.jsp page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginScreen(ModelMap map) {
		return "login";
	}
	
	/**
	 * HTTP POST handler for the main login page
	 * @param customer the Customer object of the customer logging in
	 * @param loginform the LoginForm object for validation checking
	 * @param br the BindingResult object for errors in the LoginForm object
	 * @param map the ModelMap
	 * @return the jsp page (customer dashboard if successfully logged in)
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String processLoginScreen(
		@ModelAttribute("customer") Customer customer,
		@Valid @ModelAttribute("loginform") LoginForm loginform,
		BindingResult br,
		ModelMap map) {
		
		//Get hte user name and oassword from the LoginForm object
		String username = loginform.getUsername();
		String password = loginform.getPassword();
		
		String pageToReturn = "dashboard";
		int custId = 0;
		System.out.println("/login POST: About to validate credentials with the database...");
		custId = LoginService.validateCredentials(username, password);
		System.out.println("/login POST: DONE validating credentials with the database...");
		
		//If the credentials matched the database, custId should be > 0
		if(custId > 0) {
			System.out.println("/login POST: About to get the customer information from the database...");
			
			//Get the customer object data from the database
			customer = CustomerService.getCustomerInfoAndBalances(custId);
			
			System.out.println("/login POST: After hitting the DB, the customer object is:\n"
				+ customer.toString());
				
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
		}
		else {
			map.addAttribute("errorMessage", "Invalid login credentials, try again");
			pageToReturn = "login";
		}
		return pageToReturn;
	}
	
	/**
	 * HTTP GET handler for a customer logout. Sets all model and session attributes to null
	 * @param customer the Customer object currently in session
	 * @param session the current HTTP session
	 * @param map the ModelMap
	 * @return a redirecrt to the login page
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String showLogoutScreen(
		@ModelAttribute("customer") Customer customer,
		HttpSession session,
		ModelMap map) {
		//Reset the model map to a logged-out state
		map.put("username", null);
		map.put("customerid", 0);
		map.put("customer", null);
		map.put("password", null);
		map.put("passCompare", null);
		
		//Remove session attributes from the session
		session.removeAttribute("customer");
		
		return "redirect:login";
	}
	
	/**
	 * HTTP GET handler for the customer dashboard
	 * @param customer the current Customer object
	 * @param map the ModelMap
	 * @return the jsp file name (dashboard if customer is logged in)
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String showDashboardScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "dashboard";
		
		//Verify there is a logged-in customer
		System.out.println("/dashboard GET: about do check custId != 0:\n" + customer.toString());
		if(customer.getCustId() != 0) {
			System.out.println("/dashboard GET: AFTER check custId != 0:\n" + customer.toString());
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	/**
	 * HTTP GET handler for deposit transactions
	 * @param customer the current logged-in Customer object
	 * @param map the ModelMap
	 * @return the jsp file name (deposit-bank if customer logged in)
	 */
	@RequestMapping(value = "/deposit-bank", method = RequestMethod.GET)
	public String showDepositScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "deposit-bank";
		
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			System.out.println("/deposit-bank GET: customer =\n" + customer.toString());
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
			//Add a new AmountForm object for validating the amount the customer enters
			map.addAttribute("amount", new AmountForm());
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	/**
	 * HTTP POST handler for processing the deposit form
	 * @param customer the current logged-in Customer object
	 * @param accountType the account type from the radio button ("chk", "sav", or "loan")
	 * @param amountStr a String representation of the deposit amount
	 * that will be converted to double if possible
	 * @param br the BindingResult object for the AmountForm object
	 * @param map the ModelMap
	 * @return the jsp file name to go to depending on the result
	 */
	@RequestMapping(value = "/deposit-bank", method = RequestMethod.POST)
	public String processDeposit(
		@ModelAttribute("customer") Customer customer,
		@RequestParam("account") String accountType,
		@Valid @ModelAttribute("amount") AmountForm amountStr,
		BindingResult br,
		ModelMap map) {
		
		String jspToAccess = "login";
		
		System.out.println("/deposit-bank POST: amount = " + amountStr);
		System.out.println("/deposit-bank POST: br.hasErrors is: " + br.hasErrors() + " " + br.toString());
		double amount;
		//If the form had errors or if the form input could not be converted to a number,
		//go back to the form so the customer can make corrections
        if (br.hasErrors() || (amount = BankService.stringToDouble(amountStr.getAmount())) < 0.01) {
        	map.addAttribute("errormessage", "Amount must be at least $0.01");
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
            jspToAccess = "deposit-bank";
        }
		else {
			//Verify there is a logged-in customer
			if(customer.getCustId() != 0) {
				jspToAccess = "dashboard";
				
				//If account type is loan, validate the payment amount before doing the deposit
				//If amount is invalid, show the error page
				if(accountType.equals("loan")
					&& !BankService.validateCashAdvancePayment(customer, amount)) {
					//Populate the information needed for the error page
					populatePaymentErrorModel(customer.getLoan().getAccountBalance(), amount, map);
					map.addAttribute("geturl", "/deposit-bank");
					jspToAccess = "loan-payment-bank-error";
				}
				else {
					//Do the deposit and update the dashboard values
					BankService.doTransaction(customer, accountType, Account.DEPOSIT, amount);
				}
				
				//Update all dashboard parameters
				updateDashboardModel(customer, map);
			}
			else {
				jspToAccess = "redirect:login";
			}
		}
		return jspToAccess;
	}	

	/**
	 * Helper method that populates the parameters necessary for showing a withdrawal
	 * error page for any of the three account types
	 * @param loanBalance the account class with the error (Checking, Saving, Loan)
	 * @param amount the requested withdrawal amount
	 * @param map the ModelMap for the current session
	 */
	private void populatePaymentErrorModel(double loanBalance, double amount, ModelMap map) {
		//Requested withdrawal amount
		map.addAttribute("reqamount", amount);
		//Current account balance
		map.addAttribute("balance", loanBalance);
	}
	
	/**
	 * HTTP GET handler for making a withdrawal
	 * @param customer the current logged-in Customer object
	 * @param map the ModelMap
	 * @return the jsp file name to access (withdraw-bank if customer is logged in)
	 */
	@RequestMapping(value = "/withdraw-bank", method = RequestMethod.GET)
	public String showWithdrawScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "withdraw-bank";
		
		System.out.println("\n/withdraw-bank GET: ready to populate map...");
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
			//AmountForm object for getting the amount from the user
			map.addAttribute("amount", new AmountForm());
		}
		else {
			jspToAccess = "login";
		}
		System.out.println("/withdraw-bank GET: LEAVING to go to JSP page " + jspToAccess);
		return jspToAccess;
	}
	
	/**
	 * HTTP POST request handler for withdrawals
	 * @param customer the current logged-in Customer object
	 * @param accountType the account type ("chk", "sav", or "loan")
	 * @param amountStr the String representation of the withdrawal amount
	 * that will be converted to double, if possible
	 * @param br the BindingResult object for the AmountForm
	 * @param map the ModelMap
	 * @return the jsp to access (dashboard if the transaction is valid)
	 */
	@RequestMapping(value = "/withdraw-bank", method = RequestMethod.POST)
	public String processWithdrawal(
		@ModelAttribute("customer") Customer customer,
		@RequestParam("account") String accountType,
		@Valid @ModelAttribute("amount") AmountForm amountStr,
		BindingResult br,
		ModelMap map) {
		
		String jspToAccess = "login";
		
		System.out.println("/withdraw-bank POST: amount = " + amountStr);
		System.out.println("/withdraw-bank POST: br.hasErrors is: " + br.hasErrors() + " " + br.toString());
		//If the form had errors or if the form input could not be converted to a number,
		//go back to the form so the customer can make corrections
        double amount;
		if (br.hasErrors() || (amount = BankService.stringToDouble(amountStr.getAmount())) < 0.01) {
        	map.addAttribute("errormessage", "Amount must be at least $0.01");
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
            jspToAccess = "withdraw-bank";
        }
		else {
			//Verify there is a logged-in customer
			if(customer.getCustId() != 0) {
				jspToAccess = "dashboard";
				System.out.println("/withdraw-bank: Before executing the transaction, amount = " + amount);
				
				//Check for a valid withdrawal amount before executing the transaction
				boolean validAmount = BankService.validateWithdrawal(customer, accountType, amount);
				
				if(validAmount) {
					//Do the withdrawal and update the dashboard values
					BankService.doTransaction(customer, accountType, Account.WITHDRAWAL, amount);
	
					System.out.println("/withdraw-bank POST: after transaction, balances are:\n"
						+ "checking:" + customer.getChecking().getAccountBalance()
						+ "saving:" + customer.getSaving().getAccountBalance()
						+ "loan:" + customer.getLoan().getAccountBalance());
	
					//Update all dashboard parameters
					updateDashboardModel(customer, map);
				}
				else {
					if(accountType.equals("chk")) {
						//Populate the information needed for the error page
						populateWithdrawalErrorModel(customer.getChecking(), amount, map);
						jspToAccess = "withdraw-bank-error-checking";
					}
					else if(accountType.equals("sav")) {
						//Populate the information needed for the error page
						populateWithdrawalErrorModel(customer.getSaving(), amount, map);
						jspToAccess = "withdraw-bank-error-saving";
					}
					else if(accountType.equals("loan")) {
						//Populate the information needed for the error page
						populateWithdrawalErrorModel(customer.getLoan(), amount, map);
						jspToAccess = "withdraw-bank-error-loan";
					}
					
					//Show error page and proceed based on user selection
					System.out.println("/withdraw-bank POST: About to leave to " + jspToAccess + "\n"
						+ "amount = " + amount);
				}
			}
			else {
				jspToAccess = "redirect:login";
			}
		}
		return jspToAccess;
	}

	/**
	 * Helper method that populates the parameters necessary for showing a withdrawal
	 * error page for any of the three account types
	 * @param account the account class with the error (Checking, Saving, Loan)
	 * @param amount the requested withdrawal amount
	 * @param map the ModelMap for the current session
	 */
	private void populateWithdrawalErrorModel(Account account, double amount, ModelMap map) {
		//URL to return to after error
		map.addAttribute("urlwithdrawal", "/withdraw-bank");
		//Requested withdrawal amount
		map.addAttribute("reqamount", amount);
		//Current account balance
		map.addAttribute("balance", account.getAccountBalance());
		
		//Other model parameters based on account type
		//(note: Saving accounts only need the above three)
		if(account instanceof Checking) {
			map.addAttribute("overdraft", ((Checking)account).getOverdraftFee());
		}
		if(account instanceof Loan) {
			map.addAttribute("balance", BankService.computeLoanAvailable((Loan)account));
		}
	}
	
	/**
	 * HTTP POST handler for withdrawal errors
	 * @param customer the current logged-in Customer object
	 * @param amount the amount of the transaction
	 * @param map the ModelMap
	 * @return the jsp to access (dashboard if successful)
	 */
	@RequestMapping(value = "/withdraw-bank-error-checking", method = RequestMethod.POST)
	public String processWithdrawalCheckingOverdraft(
		@ModelAttribute("customer") Customer customer,
		@RequestParam("amount") double amount,
		ModelMap map) {
		
		String jspToAccess = "dashboard";
		
		System.out.println("/withdraw-bank-error-checking POST: CHOICE = PROCEED");
		System.out.println("/withdraw-bank-error-checking POST: customer = " + customer.toString());
		System.out.println("/withdraw-bank-error-checking POST: amount = " + amount);
		
		//Update balance in Checking object and write transactions in database
		BankService.doTransaction(customer, "chk", Account.WITHDRAWAL, amount);
		BankService.doCheckingOverdraft(customer);

		System.out.println("/withdraw-bank-error-checking POST: after transaction, balances are:\n"
			+ "checking:" + customer.getChecking().getAccountBalance()
			+ "saving:" + customer.getSaving().getAccountBalance()
			+ "loan:" + customer.getLoan().getAccountBalance());

		//Update all dashboard parameters
		updateDashboardModel(customer, map);
		
		//Return to the dashboard
		return jspToAccess;
	}
	
	/**
	 * HTTP GET handler for transfers between accounts
	 * @param customer the current logged-in Customer object
	 * @param map the ModelMap
	 * @return the jsp to access (transfer-bank if customer is logged in)
	 */
	@RequestMapping(value = "/transfer-bank", method = RequestMethod.GET)
	public String showTransferScreen(
		@ModelAttribute("customer") Customer customer,
		ModelMap map) {
		
		String jspToAccess = "transfer-bank";
		
		System.out.println("\n/transfer-bank GET: customer =\n" + customer.toString());
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
			//AmountForm object that will allow the amount entered to be validated
			map.addAttribute("amount", new AmountForm());
		}
		else {
			jspToAccess = "login";
		}
		System.out.println("/transfer-bank GET: about to go to " + jspToAccess);
		return jspToAccess;
	}
	
	/**
	 * HTTP POST handler for transfers between accounts
	 * @param customer the current logged-in Customer object
	 * @param fromAccount the account type FROM ("chk", "sav", or "loan")
	 * @param toAccount the account type FROM ("chk", "sav", or "loan")
	 * @param amountStr the String representation of the transfer amount
	 * that will be converted to double, if possible
	 * @param br the BindingResult object for the AmountForm
	 * @param map the ModelMap
	 * @return the jsp to access (dashboard if successful)
	 */
	@RequestMapping(value = "/transfer-bank", method = RequestMethod.POST)
	public String processTransfer(
		@ModelAttribute("customer") Customer customer,
		@RequestParam("fromaccount") String fromAccount,
		@RequestParam("toaccount") String toAccount,
		@Valid @ModelAttribute("amount") AmountForm amountStr,
		BindingResult br,
		ModelMap map) {
		
		String jspToAccess = "login";
		
		System.out.println("/transfer-bank POST: amount = " + amountStr);
		System.out.println("/transfer-bank POST: br.hasErrors is: " + br.hasErrors() + " " + br.toString());
		//If the form had errors or if the form input could not be converted to a number,
		//go back to the form so the customer can make corrections
        double amount;
		if (br.hasErrors() || (amount = BankService.stringToDouble(amountStr.getAmount())) < 0.01) {
        	map.addAttribute("errormessage", "Amount must be at least $0.01");
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
            jspToAccess = "transfer-bank";
        }
		else if(fromAccount.equals(toAccount)) {
        	map.addAttribute("errormessage", "FROM Account and TO Account must be different");
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
            jspToAccess = "transfer-bank";
		}
		else {
			//Verify there is a logged-in customer
			if(customer.getCustId() != 0) {
				jspToAccess = "dashboard";
				System.out.println("/transfer-bank POST: Before executing the transaction, amount = " + amount);
				
				//Check for a valid WITHDRAWAL amount before executing the transfer
				boolean fromAmountValid = BankService.validateWithdrawal(customer, fromAccount, amount);
				
				//If the TO account is loan, validate the amount is not greater than the available balance
				boolean toAmountValid = true;
				if(toAccount.equals("loan")) {
					toAmountValid = BankService.validateCashAdvancePayment(customer, amount);
				}
				
				//If either check above failed, validAmount will be false; otherwise it will be true
				if(fromAmountValid && toAmountValid) {
					//Do the transfer and update the dashboard values
					int numRec = BankService.doTransfer(customer, fromAccount, amount, toAccount);
	
					System.out.println("/transfer-bank POST: after transaction, balances are:\n"
						+ "checking:" + customer.getChecking().getAccountBalance() + ", "
						+ "saving:" + customer.getSaving().getAccountBalance() + ", "
						+ "loan:" + customer.getLoan().getAccountBalance());
					System.out.println("/transfer-bank POST: after transaction, numRec = " + numRec);
	
					//Update all dashboard parameters
					updateDashboardModel(customer, map);
				}
				else if(!fromAmountValid) {				
					if(fromAccount.equals("chk")) {
						//Populate the information needed for the error page
						populateTransferErrorModel(customer.getChecking(), amount, toAccount, map);
						jspToAccess = "transfer-bank-error-checking";
					}
					else if(fromAccount.equals("sav")) {
						//Populate the information needed for the error page
						populateTransferErrorModel(customer.getSaving(), amount, toAccount, map);
						jspToAccess = "withdraw-bank-error-saving";
					}
					else if(fromAccount.equals("loan")) {
						//Populate the information needed for the error page
						populateTransferErrorModel(customer.getLoan(), amount, toAccount, map);
						jspToAccess = "withdraw-bank-error-loan";
					}
					
					//Show error page and proceed based on user selection
					System.out.println("/transfer-bank POST: About to leave to " + jspToAccess + "\n"
						+ "amount = " + amount);
				}
				else if(!toAmountValid) {
					//The amount to transfer to the cash advance (loan) exceeds the outstanding balance
					//Populate the information needed for the error page
					populatePaymentErrorModel(customer.getLoan().getAccountBalance(), amount, map);
					map.addAttribute("geturl", "/transfer-bank");
					jspToAccess = "loan-payment-bank-error";
				}
			}
			else {
				jspToAccess = "redirect:login";
			}
		}
		return jspToAccess;
	}
	
	/**
	 * Helper method that populates the parameters necessary for showing a transfer
	 * error page for a checking overdraft
	 * @param chk the Checking account object
	 * @param amount the requested withdrawal amount
	 * @param toAccount the type of account being transferred into ("sav" or "loan" here)
	 * @param map the ModelMap for the current session
	 */
	private void populateTransferErrorModel(Account account, double amount, String toAccount, ModelMap map) {
		//URL to return to after error
		map.addAttribute("urlwithdrawal", "/transfer-bank");
		//Requested withdrawal amount
		map.addAttribute("reqamount", amount);
		//Account to transfer into
		map.addAttribute("toAccount", toAccount);
		//Current account balance
		map.addAttribute("balance", account.getAccountBalance());
		if(account instanceof Loan) {
			map.addAttribute("balance", BankService.computeLoanAvailable((Loan)account));
		}
		if(account instanceof Checking) {
			//Overdraft fee
			map.addAttribute("overdraft", ((Checking)account).getOverdraftFee());
		}
	}
	
	/**
	 * HTTP POST handler for transfer-out-of-checking errors.
	 * Performs the transfer that has been validated by this point
	 * @param customer the current logged-in Customer object
	 * @param amount the amount of the transfer
	 * @param toAccount the account type TO ("chk", "sav", or "loan")
	 * @param map the ModelMap
	 * @return the jsp to access (dashboard if successful)
	 */
	@RequestMapping(value = "/transfer-bank-error-checking", method = RequestMethod.POST)
	public String processTransferCheckingOverdraft(
		@ModelAttribute("customer") Customer customer,
		@RequestParam("amount") double amount,
		@RequestParam("toAccount") String toAccount,
		ModelMap map) {
		
		String jspToAccess = "dashboard";
		
		System.out.println("\n/transfer-bank-error-checking: CHOICE = PROCEED");
		System.out.println("/transfer-bank-error-checking: customer = " + customer.toString());
		System.out.println("/transfer-bank-error-checking: amount = " + amount);
		System.out.println("/transfer-bank-error-checking: toAccount = " + toAccount);
		
		//Update balance in Checking object and write transactions in database
		BankService.doTransfer(customer, "chk", amount, toAccount);
		BankService.doCheckingOverdraft(customer);

		System.out.println("/transfer-bank-error-checking POST: after transaction, balances are:\n"
			+ "checking:" + customer.getChecking().getAccountBalance()
			+ "saving:" + customer.getSaving().getAccountBalance()
			+ "loan:" + customer.getLoan().getAccountBalance());

		//Update all dashboard parameters
		updateDashboardModel(customer, map);
		
		//Return to the dashboard
		return jspToAccess;
	}
	
	/**
	 * HTTP GET handler for generating bank statements. Access all transactions for
	 * the logged-in customer and separates them into lists by account type.
	 * @param customer the current logged-in Customer object
	 * @param map the ModelMap
	 * @return the jsp to access (statements-bank if successful)
	 */
	@RequestMapping(value = "/statements-bank", method = RequestMethod.GET)
	public String showStatementsScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "statements-bank";
		
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			System.out.println("\n/statements-bank GET: making transaction lists");
			//Lists that will store transactions by type and posted to the transactions view
			List<Transaction> transchk;
			List<Transaction> transsav;
			List<Transaction> transloan;
			
			//Get the transaction lists from the database
			DataService ds = new DataService();
			List<Transaction> transList = ds.dbRetrieveTransactionsById(customer.getCustId());
			ds.close();
			
			//Separate transactions by account type and put in their respective lists
			//The query returns transactions sorted by account and transaction date
			if(transList != null) {
				//Separate all transactions into lists by account type
				transchk = BankService.transListByAccount(transList, 'C');
				transsav = BankService.transListByAccount(transList, 'S');
				transloan = BankService.transListByAccount(transList, 'L');
				
				//Update all dashboard parameters
				updateDashboardModel(customer, map);
				//Add the transaction lists to the ModelMap for the jsp page to process
				map.addAttribute("transchk", transchk);
				map.addAttribute("transsav", transsav);
				map.addAttribute("transloan", transloan);
				map.addAttribute("fromdate",
					BankService.DATE_FORMAT.format(transList.get(0).getTransactionDate()));
				map.addAttribute("todate",
					BankService.DATE_FORMAT.format(transList.get(transList.size() - 1).getTransactionDate()));
			}
		}
		else {
			jspToAccess = "login";
		}
		System.out.println("/statements-bank GET: going to: " + jspToAccess + ".jsp");
		return jspToAccess;
	}
	
	/**
	 * HTTP GET handler for the about page
	 * @return the jsp to access
	 */
	@RequestMapping(value = "/about-bank", method = RequestMethod.GET)
	public String showAboutScreen() {
		return "about-bank";
	}
	
	/********* UPDATE A CUSTOMER ******************/
	/**
	 * HTTP GET handler for customer settings screen that allows customers to make changes
	 * to their accounts (email, phone, and password)
	 * @param customer the current logged-in Customer object
	 * @param map the ModelMap
	 * @return the jsp to access
	 */
	@RequestMapping(value = "/customer-settings", method = RequestMethod.GET)
	public String showCustomerSettingsScreen(
		@ModelAttribute("customer") Customer customer, ModelMap map) {
		
		String jspToAccess = "customer-settings";
		
		System.out.println("\n/customer-settings GET: customer =\n" + customer.toString());
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
		}
		else {
			jspToAccess = "login";
		}
		System.out.println("/customer-settings GET: about to go to " + jspToAccess + ".jsp");
		return jspToAccess;
	}
	
	//STEP 1: Have customer confirm password to get to the information updating screen
	/**
	 * HTTP GET handler for customer updating that calls the jsp
	 * requiring customer to enter password to continue
	 * @param customer the current logged-in Customer object
	 * @param map the ModelMap
	 * @return the jsp to access
	 */
	@RequestMapping(value = "/customer-update", method = RequestMethod.GET)
	public String showUpdateCustomerScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "customer-update-password";

		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			//Update all dashboard parameters
			updateDashboardModel(customer, map);			
			map.addAttribute("username", customer.getUsername());			
		}
		else {
			jspToAccess = "login";
		}
		System.out.println("/customer-update GET: about to go to customer-update-password.jsp");
		return jspToAccess;
	}
	
	//STEP 2A: Get the customer's password from the jsp
	
	//STEP 2B: Validate the credentials the customer entered, then go the update information screen
	/**
	 * HTTP POST handler for the customer's password to show the information update screen
	 * @param customer the current logged-in Customer object
	 * @param passwordform a PasswordForm object for validating the password entry
	 * @param br the BindingResult object for the PasswordForm object
	 * @param map the ModelMap
	 * @return the jsp to access
	 */
	@RequestMapping(value = "/update-login", method = RequestMethod.POST)
	public String processUpdateLogin(
		@ModelAttribute("customer") Customer customer,
		@Valid @ModelAttribute("passwordform") PasswordForm passwordform,
		BindingResult br,
		ModelMap map) {
		
		String username = customer.getUsername();
		String password = passwordform.getPassword();
		
		String jspToReturn = "customer-updateinfo";
		int custId = 0;
		System.out.println("\n/update-login POST: About to validate credentials with the database...");
		custId = LoginService.validateCredentials(username, password);
		System.out.println("/update-login POST: DONE validating credentials with the database...");
		
		//If the credentials matched the database, custId should be > 0
		if(custId > 0) {
			System.out.println("/update-login POST: About to get the customer information from the database...");
			
			//Get the customer object data from the database
			customer = CustomerService.getCustomerInfoAndBalances(custId);
			
			System.out.println("/update-login POST: After hitting the DB, the customer object is:\n"
				+ customer.toString());
				
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
			populateUpdateCustomerModel(customer, map);

		}
		else {
			map.addAttribute("errorMessage", "Invalid login credentials, try again");
			map.addAttribute("username", customer.getUsername());
			jspToReturn = "customer-update-password";
		}
		return jspToReturn;
	}
		
	//STEP 3: Receive the now-updated Customer object, validate the new passwords match
	//and go to the confirmation screen if they do; otherwise, go back to the update info screen
	/**
	 * HTTP POST handler for processing the customer's updated information. Validates that
	 * passwords match then sends user to a confirmation page.
	 * @param customer the current logged-in Customer object
	 * @param result the BindingResult object for the Customer object
	 * @param map the ModelMap
	 * @return the jsp to access
	 */
	@RequestMapping(value="/updatecustomer", method = RequestMethod.POST)
	public String processUpdateCustomer(
		@Valid @ModelAttribute("customer") Customer customer,
		BindingResult result,
		ModelMap map) {
		
		String jspToReturn = "redirect:dashboard";
		
		if(result.hasErrors()) {
			System.out.println("/updatecustomer POST: result has errors");
			jspToReturn = "customer-updateinfo";
		}
		else {
			//Put the UPDATED Customer object in the ModelMap
			System.out.println("/updatecustomer POST: customer =\n" + customer.toString());
			map.addAttribute("customer", customer);
			System.out.println("/updatecustomer POST: customer from map.get =\n"
				+ map.get("customer").toString());
			System.out.println("/updatecustomer POST: password = " + customer.getPassword());
			System.out.println("/updatecustomer POST: passCompare = " + customer.getPassCompare());
			
			//Validate the two NEW passwords match before proceeding
			if(!CustomerService.validatePasswordsMatch(customer.getPassword(), customer.getPassCompare())) {
				System.err.println("/updatecustomer POST: PASSWORDS DO NOT MATCH");
				//Populate the model and return to the new customer form
				map.addAttribute("passmatcherror", "Passwords do not match, please re-enter");
				map.addAttribute("password", "");
				map.addAttribute("passCompare", "");
				populateUpdateCustomerModel(customer, map);
				jspToReturn = "customer-updateinfo";
			}
			else {
				//Show the information to the customer for confirmation
				jspToReturn = "customer-update-confirm";
			}
		}
		System.out.println("\nRedirecting to " + jspToReturn);
		return jspToReturn;
	}
	
	/**
	 * Helper method for setting fields for updating customer information
	 * @param customer the current Customer object
	 * @param map the current ModelMap
	 */
	private void populateUpdateCustomerModel(Customer customer, ModelMap map) {
		//UPDATE ALL FIELDS FOR THE MODEL MAP
		map.addAttribute("firstname", customer.getFirstName());
		map.addAttribute("lastname", customer.getLastName());
		map.addAttribute("username", customer.getUsername());
		map.addAttribute("curEmail", customer.getEmailAddress());
		map.addAttribute("curPhone", customer.getPhoneNumber());
	}
	
	//STEP 4: Execute the update in the database
	/**
	 * HTTP POST handler for processing the return from the update customer confirmation
	 * @param customer the current logged-in Customer object
	 * @param map the ModelMap
	 * @return the jsp to access
	 */
	@RequestMapping(value = "/update-customer", method = RequestMethod.POST)
	public String updateCustomer(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToReturn = "redirect:dashboard";
		
		System.out.println("\n/update-customer POST: Back from customer-update-confirm:");
		System.out.println("/update-customer POST: customer =\n" + customer.toString());
		
		//UPDATE the existing customer object in the database
		int numRec = CustomerService.updateExistingCustomer(customer);
		if(numRec > 0) {
			System.out.println("/update-customer POST: updated existing customer:\n" + customer.toString());
			map.put("customer", customer);
			jspToReturn = "customer-update-success";
		}
		
		return jspToReturn;
	}
	
	/********** DELETE A CUSTOMER *****************/
	
	//STEP 1: Have customer confirm password to get to the delete confirmation screen
	/**
	 * HTTP GET handler for deleting a customer
	 * @param customer the current logged-in Customer object
	 * @param map the ModelMap
	 * @return the jsp to access
	 */
	@RequestMapping(value = "/customer-delete", method = RequestMethod.GET)
	public String showDeleteCustomerScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "customer-delete-password";

		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
			map.addAttribute("username", customer.getUsername());			
		}
		else {
			jspToAccess = "login";
		}
		System.out.println("/customer-delete GET: about to go to customer-delete-password.jsp");
		return jspToAccess;
	}
	
	//STEP 2: Validate the credentials the customer entered, then go to the confirmation page
	/**
	 * HTTP POST handler for processing the password the user entered to access the delete screen
	 * @param customer the current Customer object
	 * @param passwordform the PasswordForm object for validating the password entered correctly
	 * @param br the BindingResult object for the PasswordForm object
	 * @param map the ModelMap
	 * @return the jsp to access
	 */
	@RequestMapping(value = "/delete-login", method = RequestMethod.POST)
	public String processDeleteLogin(
		@ModelAttribute("customer") Customer customer,
		@Valid @ModelAttribute("passwordform") PasswordForm passwordform,
		BindingResult br,
		ModelMap map) {
		
		String username = customer.getUsername();
		String password = passwordform.getPassword();
		
		String pageToReturn = "customer-delete-confirm";
		int custId = 0;
		System.out.println("/delete-login POST: About to validate credentials with the database...");
		custId = LoginService.validateCredentials(username, password);
		System.out.println("/delete-login POST: DONE validating credentials with the database...");
		
		//If the credentials matched the database, custId should be > 0
		if(custId > 0) {
			System.out.println("/delete-login POST: About to get the customer information from the database...");
			
			//Get the customer object data from the database
			customer = CustomerService.getCustomerInfoAndBalances(custId);
			
			System.out.println("/delete-login POST: After hitting the DB, the customer object is:\n"
				+ customer.toString());
				
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
		}
		else {
			map.addAttribute("errorMessage", "Invalid login credentials, try again");
			map.addAttribute("username", customer.getUsername());
			pageToReturn = "customer-delete-password";
		}
		return pageToReturn;
	}
		
	//STEP 3: Execute the delete operation in the database
	/**
	 * HTTP GET handler for performing the customer deletion after customer confirms
	 * the intent to delete
	 * @param customer the current logged-in Customer object
	 * @param map the ModelMap
	 * @return the jsp to access
	 */
	@RequestMapping(value = "/delete-customer", method = RequestMethod.GET)
	public String deleteCustomer(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToReturn = "redirect:logout";
		
		System.out.println("\nBack from customer-delete-confirm:");
		System.out.println("/delete-customer GET: customer =\n" + customer.toString());
		
		//DELETE the existing customer object in the database
		int numRec = CustomerService.deleteExistingCustomer(customer);
		if(numRec > 0) {
			System.out.println("/delete-customer POST: deleted exiting customer:\n" + customer.toString());
			jspToReturn = "customer-delete-success";
		}
		
		return jspToReturn;
	}
	
	/**
	 * Method that wouldn't exist in a real customer-facing application, but would
	 * occur at the actual end of the month. Regardless, it does the end of month
	 * transactions which is to compute the savings interest earned, service fee if applicable,
	 * and loan interest paid and service fee if minimum payment not made
	 * @param customer the current Customer object
	 * @param map the ModelMap
	 * @return the jsp to access
	 */
	@RequestMapping(value = "/endmonth", method = RequestMethod.GET)
	public String showEndOfMonthScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "endofmonth-bank";
		
		//Verify there is a logged-in customer
		System.out.println("/endmonth GET: about to check custId != 0:\n" + customer.toString());
		if(customer.getCustId() != 0) {
			System.out.println("/endmonth GET: AFTER check custId != 0:\n" + customer.toString());
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
			
			//Do the end of month and populate the parameters needed for the results screen
			int numRec = BankService.doEndOfMonth(customer);
			if(numRec > 0) {
				//Populate the end of month parameters
				double savFee = customer.getSaving().isFeeRequired() ? customer.getSaving().getServiceFee() : 0.00;
				double loanFee = customer.getLoan().isFeeRequired() ? customer.getLoan().getLateFee() : 0.00;
				map.addAttribute("savfee", savFee);
				map.addAttribute("savinterest", customer.getSaving().getInterestEarned());
				map.addAttribute("loanfee", loanFee);
				map.addAttribute("loaninterest", customer.getLoan().getInterestPaidThisMonth());
			}
			else {
				System.out.println("/endmonth GET: The end of month transactions failed!");
			}
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	/**
	 * Updates the ModelMap parameters for the customer dashboard
	 * @param customer the current Customer object
	 * @param map the ModelMap for the LoginController
	 */
	private void updateDashboardModel(Customer customer, ModelMap map) {
		//Put the customer information into the model
		map.put("fullname", customer.getFirstName() + " " + customer.getLastName());
		map.put("email", customer.getEmailAddress());
		map.put("chkbal", customer.getChecking().getAccountBalance());
		map.put("savbal", customer.getSaving().getAccountBalance());
		map.put("loanbal", customer.getLoan().getAccountBalance());
		map.put("loanavail", BankService.computeLoanAvailable(customer.getLoan()));
		map.put("acctchk", "C..." + customer.getChecking().getAccountNumber().substring(8));
		map.put("acctsav", "S..." + customer.getSaving().getAccountNumber().substring(8));
		map.put("acctloan", "L..." + customer.getLoan().getAccountNumber().substring(8));
		map.put("customer", customer);
	}	
}
