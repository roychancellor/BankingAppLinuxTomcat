package edu.gcu.cst341.controller;

import java.util.ArrayList;
import java.util.Date;
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
import edu.gcu.cst341.model.Customer;
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
	BankService BankService;
	@Autowired
	CustomerService CustomerService;
	
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
		DataService ds = new DataService();
		int custId = ds.dbCreateCustomer(customer.getLastName(),
			customer.getFirstName(),
			customer.getEmailAddress(),
			customer.getPhoneNumber(),
			customer.getUsername(),
			"salt",
			customer.getPassword());
		
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
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String processLoginScreen(
		@ModelAttribute("customer") Customer customer,
		@RequestParam String username,
		@RequestParam String password,
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
					
				//Put the customer into the model
				map.addAttribute("customer", customer);

				//Put the customer's full name in the model
				map.addAttribute("fullname", customer.getFirstName() + " " + customer.getLastName());
				
				//Put the current balances into the model
				map.addAttribute("chkbal", customer.getChecking().getAccountBalance());
				map.addAttribute("savbal", customer.getSaving().getAccountBalance());
				map.addAttribute("loanbal", customer.getLoan().getAccountBalance());
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
			//Put the customer information into the model
			map.put("fullname", customer.getFirstName() + " " + customer.getLastName());
			map.put("email", customer.getEmailAddress());
			map.put("chkbal", customer.getChecking().getAccountBalance());
			map.put("savbal", customer.getSaving().getAccountBalance());
			map.put("loanbal", customer.getLoan().getAccountBalance());
			map.put("customer", customer);
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/deposit-bank", method = RequestMethod.GET)
	public String showDepositScreen(
		@ModelAttribute("customer") Customer customer,
		ModelMap map) {
		String jspToAccess = "deposit-bank";
		
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			System.out.println("/deposit-bank GET: customer =\n" + customer.toString());
			//Put the account numbers into the model
			map.put("fullname", customer.getFirstName() + " " + customer.getLastName());
			map.put("email", customer.getEmailAddress());
			map.addAttribute("acctchk", customer.getChecking().getAccountNumber());
			map.addAttribute("acctsav", customer.getSaving().getAccountNumber());
			map.addAttribute("acctloan", customer.getLoan().getAccountNumber());
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
        	map.addAttribute("errormessge", "Amount must be at least $0.01");
            return "deposit-bank";
        }

		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			jspToAccess = "dashboard";
			
			//Do the deposit and update the dashboard values
			DataService ds = new DataService();
			boolean dbSuccess = false;
			switch(accountType) {
				case "chk":
					//Update the model
					customer.getChecking().doTransaction(Account.DEPOSIT, amount.getAmount());
					//Write to the database
					dbSuccess = ds.dbUpdateBalanceAndTransaction(customer.getCustId(), customer.getChecking());
					if(!dbSuccess) {
						System.err.println("ERROR!!! Unable to write transaction to DB");
					}
					break;
				case "sav":
					//Update the model
					customer.getSaving().doTransaction(Account.DEPOSIT, amount.getAmount());
					//Write to the database
					dbSuccess = ds.dbUpdateBalanceAndTransaction(customer.getCustId(), customer.getSaving());
					if(!dbSuccess) {
						System.err.println("ERROR!!! Unable to write transaction to DB");
					}
					break;
				case "loan":
					//Update the model
					customer.getLoan().doTransaction(Account.DEPOSIT, amount.getAmount());
					//Write to the database
					dbSuccess = ds.dbUpdateBalanceAndTransaction(customer.getCustId(), customer.getLoan());
					if(!dbSuccess) {
						System.err.println("ERROR!!! Unable to write transaction to DB");
					}
					break;
				default:
			}
			System.out.println("/deposit-bank POST: the database result is " + dbSuccess);
			//Populate the dashboard with updated balances
			map.put("fullname", customer.getFirstName() + " " + customer.getLastName());
			map.put("email", customer.getEmailAddress());
			map.put("chkbal", customer.getChecking().getAccountBalance());
			map.put("savbal", customer.getSaving().getAccountBalance());
			map.put("loanbal", customer.getLoan().getAccountBalance());
		}
		else {
			jspToAccess = "redirect:login";
		}
		
		return jspToAccess;
	}	

	@RequestMapping(value = "/withdraw-bank", method = RequestMethod.GET)
	public String showWithdrawScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "withdraw-bank";
		
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			//Put customer name and email in map
			map.put("fullname", customer.getFirstName() + " " + customer.getLastName());
			map.put("email", customer.getEmailAddress());
			//Put the account numbers into the model
			map.addAttribute("acctchk", customer.getChecking().getAccountNumber());
			map.addAttribute("acctsav", customer.getSaving().getAccountNumber());
			map.addAttribute("acctloan", customer.getLoan().getAccountNumber());
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/withdraw-bank", method = RequestMethod.POST)
	public String processWithdrawal(
			@ModelAttribute("customer") Customer customer,
			@RequestParam("account") String accountType,
			@RequestParam("amountwithdraw") double amountToWithdraw,
			ModelMap map) {
		String jspToAccess = "login";
		
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			jspToAccess = "dashboard";
			
			//TODO: Do the withdrawal for the customer
			
			//Update the dashboard values
			map.put("fullname", customer.getFirstName() + " " + customer.getLastName());
			map.put("email", customer.getEmailAddress());
			switch(accountType) {
				case "chk":
					map.put("chkbal", customer.getChecking().getAccountBalance());
					map.put("savbal", customer.getSaving().getAccountBalance());
					map.put("loanbal", customer.getLoan().getAccountBalance());
					break;
				case "sav":
					map.put("chkbal", customer.getChecking().getAccountBalance());
					map.put("savbal", customer.getSaving().getAccountBalance());
					map.put("loanbal", customer.getLoan().getAccountBalance());
					break;
				case "loan":
					map.put("chkbal", customer.getChecking().getAccountBalance());
					map.put("savbal", customer.getSaving().getAccountBalance());
					map.put("loanbal", customer.getLoan().getAccountBalance());
					break;
				default:
			}
		}
		
		return jspToAccess;
	}

	@RequestMapping(value = "/transfer-bank", method = RequestMethod.GET)
	public String showTransferScreen(@ModelAttribute("customer") Customer customer, ModelMap map) {
		String jspToAccess = "transfer-bank";
		
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			//Put customer name and email in the model
			map.put("fullname", customer.getFirstName() + " " + customer.getLastName());
			map.put("email", customer.getEmailAddress());
			//Put the account numbers into the model
			map.addAttribute("acctchk", customer.getChecking().getAccountNumber());
			map.addAttribute("acctsav", customer.getSaving().getAccountNumber());
			map.addAttribute("acctloan", customer.getLoan().getAccountNumber());
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/transfer-bank", method = RequestMethod.POST)
	public String processTransfer(
			@ModelAttribute("customer") Customer customer,
			@RequestParam("accountfrom") String accountFrom,
			@RequestParam("accountto") String accountTo,
			@RequestParam("amounttransfer") double amountToTransfer,
			ModelMap map) {
		String jspToAccess = "login";
		
		//Verify there is a logged-in customer
		if(customer.getCustId() != 0) {
			jspToAccess = "dashboard";
			//TODO: Do the transfer for the customer
			
			//Update the dashboard values
			map.put("fullname", customer.getFirstName() + " " + customer.getLastName());
			map.put("email", customer.getEmailAddress());
			switch(accountFrom) {
				case "chk":
					map.put("chkbal", customer.getChecking().getAccountBalance());
					map.put("savbal", customer.getSaving().getAccountBalance());
					map.put("loanbal", customer.getLoan().getAccountBalance());
					break;
				case "sav":
					map.put("chkbal", customer.getChecking().getAccountBalance());
					map.put("savbal", customer.getSaving().getAccountBalance());
					map.put("loanbal", customer.getLoan().getAccountBalance());
					break;
				case "loan":
					map.put("chkbal", customer.getChecking().getAccountBalance());
					map.put("savbal", customer.getSaving().getAccountBalance());
					map.put("loanbal", customer.getLoan().getAccountBalance());
					break;
				default:
			}
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
				
				//Put the data into the model
				map.put("fullname", customer.getFirstName() + " " + customer.getLastName());
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
}
