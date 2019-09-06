package edu.gcu.cst341.controller;

import java.util.ArrayList;
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
import edu.gcu.cst341.model.AmountForm;
import edu.gcu.cst341.model.Checking;
import edu.gcu.cst341.model.Customer;
import edu.gcu.cst341.model.Loan;
import edu.gcu.cst341.model.Transaction;

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
	
	//TODO: Investigate auto-wiring a DataService object here
	
	//Spring will make this object when needed and will keep it in the session
	//until the session is completed
	@Valid @ModelAttribute("customer")
	public Customer customer() {
		return new Customer();
	}
	
	@RequestMapping(value = "/newcustomer", method = RequestMethod.GET)
	public String showNewCustomerScreen(ModelMap map) {
		return "newcustomer";
	}
	
	@RequestMapping(value="/newcustomer", method = RequestMethod.POST)
	public String processNewCustomer(
		@Valid @ModelAttribute("customer") Customer customer,
		BindingResult result,
		ModelMap map) {
		
		if(result.hasErrors()) {
			System.out.println("processNewCustomer: result has errors");
			return "newcustomer";
		}
		//Put the Customer object in the ModelMap
		System.out.println("/newcustomer POST: customer =\n" + customer.toString());
		map.put("customer", customer);
		System.out.println("/newcustomer POST: customer from map.get =\n"
			+ map.get("customer").toString());
		
		//Show the information to the customer - NOT WORKING YET - FIX LATER
		
		//Write the new customer object to the database
		int custId = CustomerService.createNewCustomer(customer);
		
		DataService ds = new DataService();
		if(custId > 0) {
			System.out.println("/newcustomer POST: created new customer:\n" + customer.toString());
			System.out.println("/newcustomer POST: retrieved the new customer from DB:\n"
				+ ds.dbRetrieveCustomerById(custId).toString());
		}
		ds.close();

		System.out.println("\nRedirecting to login");
		return "redirect:login";
	}
	
	//THIS IS NOT WORKING YET...DEAL WITH CONFIRMING DATA LATER
	@RequestMapping(value = "/confirmcustomer", method = RequestMethod.POST)
	public String confirmcustomerScreen(
		@ModelAttribute("customer") Customer customer,
		@RequestParam("Submit") String submitBtn,
		ModelMap map) {
		System.out.println("\nBack from confirmcustomer.jsp:");
		System.out.println("Submit button: " + submitBtn);
		System.out.println("/confirmcustomer POST: customer =\n" + customer.toString());
		
		//Write the customer to the database
		
		return "redirect:login";
	}
	
	//NOTE: return statements are names of .jsp files
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginScreen(ModelMap map) {
		return "login";
	}
	
	//TODO: Wire in Spring Security for validating credentials
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String processLoginScreen(
		@ModelAttribute("customer") Customer customer,
		@RequestParam String username, @RequestParam String password,
		ModelMap map) {
		String pageToReturn = "dashboard";
		int custId = 0;
		if(username == null || password == null) {
			map.put("errormessage", "Username and password must not be blank");
			pageToReturn = "login";
		}
		else {
			System.out.println("About to validate credentials with the database...");
			custId = LoginService.validateCredentials(username, password);
			System.out.println("DONE validating credentials with the database...");
			
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
				map.put("errormessage", "Invalid login credentials");
				pageToReturn = "login";
			}
		}
		return pageToReturn;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String showLogoutScreen(
		@ModelAttribute("customer") Customer customer,
		HttpSession session,
		ModelMap map) {
		//Reset the model map to a logged-out state
		map.put("username", null);
		map.put("customerid", 0);
		map.put("customer", null);
		
		//Remove session attributes from the session
		session.removeAttribute("customer");
		
		return "login";
	}
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String showDashboardScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "dashboard";
		
		//Verify there is a logged-in customer
		System.out.println("/dashboard GET: about do check custId != 0:\n" + customer.toString());
		if(customer.getCustId() != 0) {
			System.out.println("In dashboard GET: AFTER check custId != 0:\n" + customer.toString());
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/deposit-bank", method = RequestMethod.GET)
	public String showDepositScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "deposit-bank";
		
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			System.out.println("/deposit-bank GET: customer =\n" + customer.toString());
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
			map.addAttribute("amount", new AmountForm());
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/deposit-bank", method = RequestMethod.POST)
	public String processDeposit(
		@ModelAttribute("customer") Customer customer,
		@RequestParam("account") String accountType,
		@Valid @ModelAttribute("amount") AmountForm amount,
		BindingResult br,
		ModelMap map) {
		
		String jspToAccess = "login";
		
		System.out.println("/deposit-bank POST: amount = " + amount.getAmount());
		System.out.println("br.hasErrors = " + br.hasErrors() + " " + br.toString());
		//If the form had errors, go back to the form so the customer can make corrections
        if (br.hasErrors()) {
//        	map.addAttribute("errormessage", "Amount must be at least $0.01");
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
            return "deposit-bank";
        }

		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			jspToAccess = "dashboard";
			
			//If account type is loan, validate the payment amount first
			if(accountType.equals("loan")
				&& !BankService.validateCashAdvancePayment(customer, amount.getAmount())) {
				//Populate the information needed for the error page
				populatePaymentErrorModel(customer.getLoan().getAccountBalance(), amount.getAmount(), map);
				jspToAccess = "loan-payment-bank-error";
			}
			else {
				//Do the deposit and update the dashboard values
				BankService.doTransaction(customer, accountType, Account.DEPOSIT, amount.getAmount());
			}
			
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
		}
		else {
			jspToAccess = "redirect:login";
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
	
	@RequestMapping(value = "/withdraw-bank", method = RequestMethod.GET)
	public String showWithdrawScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "withdraw-bank";
		
		System.out.println("\n/withdraw-bank GET: ready to populate map...");
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
			map.addAttribute("amount", new AmountForm());
		}
		else {
			jspToAccess = "login";
		}
		System.out.println("/withdraw-bank GET: LEAVING to go to JSP page...");
		return jspToAccess;
	}
	
	@RequestMapping(value = "/withdraw-bank", method = RequestMethod.POST)
	public String processWithdrawal(
		@ModelAttribute("customer") Customer customer,
		@RequestParam("account") String accountType,
		@Valid @ModelAttribute("amount") AmountForm amount,
		BindingResult br,
		ModelMap map) {
		
		String jspToAccess = "login";
		
		System.out.println("/withdraw-bank POST: amount = " + amount.getAmount());
		System.out.println("br.hasErrors = " + br.hasErrors() + " " + br.toString());
		//If the form had errors, go back to the form so the customer can make corrections
        if (br.hasErrors()) {
//        	map.addAttribute("errormessage", "Amount must be at least $0.01");
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
            return "withdraw-bank";
        }

		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			jspToAccess = "dashboard";
			System.out.println("\n/withdraw-bank: Before executing the transaction, amount = " + amount.getAmount());
			
			//Check for a valid withdrawal amount before executing the transaction
			boolean validAmount = BankService.validateWithdrawal(customer, accountType, amount.getAmount());
			
			if(validAmount) {
				//Do the withdrawal and update the dashboard values
				BankService.doTransaction(customer, accountType, Account.WITHDRAWAL, amount.getAmount());

				System.out.println("\nwithdraw-bank POST: after transaction, balances are:\n"
					+ "checking:" + customer.getChecking().getAccountBalance()
					+ "saving:" + customer.getSaving().getAccountBalance()
					+ "loan:" + customer.getLoan().getAccountBalance());

				//Update all dashboard parameters
				updateDashboardModel(customer, map);
			}
			else {
				if(accountType.equals("chk")) {
					//Populate the information needed for the error page
					populateWithdrawalErrorModel(customer.getChecking(), amount.getAmount(), map);
					jspToAccess = "withdraw-bank-error-checking";
				}
				else if(accountType.equals("sav")) {
					//Populate the information needed for the error page
					populateWithdrawalErrorModel(customer.getSaving(), amount.getAmount(), map);
					jspToAccess = "withdraw-bank-error-saving";
				}
				else if(accountType.equals("loan")) {
					//Populate the information needed for the error page
					populateWithdrawalErrorModel(customer.getLoan(), amount.getAmount(), map);
					jspToAccess = "withdraw-bank-error-loan";
				}
				
				//Show error page and proceed based on user selection
				System.out.println("/withdraw-bank POST: About to leave to " + jspToAccess + "\n"
					+ "amount = " + amount.getAmount());
			}
		}
		else {
			jspToAccess = "redirect:login";
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
		//Requested withdrawal amount
		map.addAttribute("reqamount", amount);
		//Current account balance
		map.addAttribute("balance", account.getAccountBalance());
		
		//Other model parameters based on account type
		//(note: Saving accounts only need the above two)
		if(account instanceof Checking) {
			map.addAttribute("overdraft", ((Checking)account).getOverdraftFee());
		}
		if(account instanceof Loan) {
			map.addAttribute("balance", BankService.computeLoanAvailable((Loan)account));
		}
	}
	
	@RequestMapping(value = "/withdraw-bank-error-checking", method = RequestMethod.POST)
	public String processWithdrawalCheckingOverdraft(
		@ModelAttribute("customer") Customer customer,
		@Valid @ModelAttribute("amount") AmountForm amountForm,
		ModelMap map) {
		
		String jspToAccess = "dashboard";
		
		System.out.println("\n/withdraw-overdraft-bank: CHOICE = PROCEED");
		System.out.println("\n/withdraw-overdraft-bank: customer = " + customer.toString());
		System.out.println("\n/withdraw-overdraft-bank: amount = " + amountForm.getAmount());
		
		//Update balance in Checking object and write transactions in database
		BankService.doTransaction(customer, "chk", Account.WITHDRAWAL, amountForm.getAmount());
		BankService.doCheckingOverdraft(customer);

		System.out.println("\nwithdraw-overdraft-bank POST: after transaction, balances are:\n"
			+ "checking:" + customer.getChecking().getAccountBalance()
			+ "saving:" + customer.getSaving().getAccountBalance()
			+ "loan:" + customer.getLoan().getAccountBalance());

		//Update all dashboard parameters
		updateDashboardModel(customer, map);
		
		//Return to the dashboard
		return jspToAccess;
	}
	
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
			map.addAttribute("amount", new AmountForm());
		}
		else {
			jspToAccess = "login";
		}
		System.out.println("/transfer-bank: about to go to transfer-bank.jsp");
		return jspToAccess;
	}
	
	@RequestMapping(value = "/transfer-bank", method = RequestMethod.POST)
	public String processTransfer(
		@ModelAttribute("customer") Customer customer,
		@RequestParam("fromaccount") String fromAccount,
		@RequestParam("toaccount") String toAccount,
		@Valid @ModelAttribute("amount") AmountForm amount,
		BindingResult br,
		ModelMap map) {
		String jspToAccess = "login";
		
		System.out.println("/transfer-bank POST: amount = " + amount.getAmount());
		System.out.println("br.hasErrors = " + br.hasErrors() + " " + br.toString());
		//If the form had errors, go back to the form so the customer can make corrections
        if (br.hasErrors()) {
//        	map.addAttribute("errormessage", "Amount must be at least $0.01");
			//Update all dashboard parameters
			updateDashboardModel(customer, map);
            return "transfer-bank";
        }

		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			jspToAccess = "dashboard";
			System.out.println("\n/transfer-bank: Before executing the transaction, amount = " + amount.getAmount());
			
			//Check for a valid WITHDRAWAL amount before executing the transfer
			boolean validFromAmount = BankService.validateWithdrawal(customer, fromAccount, amount.getAmount());
			
			//If the TO account is loan, validate the amount is not greater than the available balance
			boolean validToAmount = false;
			if(toAccount.contentEquals("loan")) {
				validToAmount = BankService.validateCashAdvancePayment(customer, amount.getAmount());
			}
			
			//If either check above failed, validAmount will be false; otherwise it will be true
			if(validFromAmount && validToAmount) {
				//Do the withdrawal and update the dashboard values
				BankService.doTransfer(customer, fromAccount, amount.getAmount(), toAccount);

				System.out.println("\ntransfer-bank POST: after transaction, balances are:\n"
					+ "checking:" + customer.getChecking().getAccountBalance()
					+ "saving:" + customer.getSaving().getAccountBalance()
					+ "loan:" + customer.getLoan().getAccountBalance());

				//Update all dashboard parameters
				updateDashboardModel(customer, map);
			}
			else if(!validFromAmount) {
				if(fromAccount.equals("chk")) {
					//Populate the information needed for the error page
					populateWithdrawalErrorModel(customer.getChecking(), amount.getAmount(), map);
					jspToAccess = "withdraw-bank-error-checking";
				}
				else if(fromAccount.equals("sav")) {
					//Populate the information needed for the error page
					populateWithdrawalErrorModel(customer.getSaving(), amount.getAmount(), map);
					jspToAccess = "withdraw-bank-error-saving";
				}
				else if(fromAccount.equals("loan")) {
					//Populate the information needed for the error page
					populateWithdrawalErrorModel(customer.getLoan(), amount.getAmount(), map);
					jspToAccess = "withdraw-bank-error-loan";
				}
				
				//Show error page and proceed based on user selection
				System.out.println("/withdraw-bank POST: About to leave to " + jspToAccess + "\n"
					+ "amount = " + amount.getAmount());
			}
			else if(!validToAmount) {
				//The amount to transfer to the cash advance (loan) exceeds the outstanding balance
				//Populate the information needed for the error page
				populatePaymentErrorModel(customer.getLoan().getAccountBalance(), amount.getAmount(), map);
				jspToAccess = "loan-payment-bank-error-loan";
			}
		}
		else {
			jspToAccess = "redirect:login";
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/statements-bank", method = RequestMethod.GET)
	public String showStatementsScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "statements-bank";
		
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			//Lists that will store transactions by type and posted to the transactions view
			List<Transaction> transchk = new ArrayList<Transaction>();
			List<Transaction> transsav = new ArrayList<Transaction>();
			List<Transaction> transloan = new ArrayList<Transaction>();
			
			//Get the transaction lists from the database
			DataService ds = new DataService();
			List<Transaction> transList = ds.dbRetrieveTransactionsById(customer.getCustId());
			ds.close();
			
			//Separate transactions by account type and put in their respective lists
			//The query returns transactions sorted by account and transaction date
			if(transList != null) {
				char acctType; 
				for(Transaction t : transList) {
					acctType = t.getAccountNumber().charAt(0);
					if(acctType == 'C') {
						transchk.add(t);
					}
					else if(acctType == 'S') {
						transsav.add(t);
					}
					else if(acctType == 'L') {
						transloan.add(t);
					}
				}
				
				//Update all dashboard parameters
				updateDashboardModel(customer, map);
				map.addAttribute("transchk", transchk);
				map.addAttribute("transsav", transsav);
				map.addAttribute("transloan", transloan);
			}
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/about-bank", method = RequestMethod.GET)
	public String showAboutScreen() {
		return "about-bank";
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
