package edu.gcu.cst341.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import edu.gcu.cst341.model.Customer;
import edu.gcu.cst341.model.Transaction;

@Controller
@Scope("session")
@SessionAttributes({"username","customerid"})  //gives access to the name attribute in any page we access in this controller
public class LoginController {
	//Allows Spring to take over control of making these objects
	@Autowired
	LoginService LoginService;
	@Autowired
	BankService BankService;
	
	@ModelAttribute("username")
	public String username() {
		return null;
	}
	//WHEN CLICKING ON A BUTTON IN THE NAV BAR, SPRING MAKES A NEW customerid
	//WHICH RESETS IT TO 0
	@ModelAttribute("customerid")
	public int customerid() {
		return 0;
	}
	
	@Valid @ModelAttribute("customer")
	public Customer customer() {
		return new Customer();
	}
	
	@RequestMapping(value = "/newcustomer", method = RequestMethod.GET)
	public String showNewCustomerScreen(
		@ModelAttribute("customer") Customer customer,
		ModelMap map) {
		System.out.println("/newcustomer GET: customer =\n" + customer.toString());
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
		//Put the customer information in the ModelMap fields
		map.addAttribute("lastName", customer.getLastName());
		map.addAttribute("firstName", customer.getFirstName());
		map.addAttribute("username", customer.getUsername());
		map.addAttribute("emailAddress", customer.getEmailAddress());
		map.addAttribute("phoneNumber", customer.getPhoneNumber());
		System.out.println("\nRedirecting to login");
		
		//Show the information to the customer - NOT WORKING YET - FIX LATER
		
		//Write the new customer to the database
		DataService ds = new DataService();
		int custId = ds.createCustomer(customer.getLastName(),
			customer.getFirstName(),
			customer.getEmailAddress(),
			customer.getPhoneNumber(),
			customer.getUsername(),
			"salt",
			customer.getPassword());
		
		if(custId > 0) {
			//Set the Customer object field custId to the
			//auto-generated customer_id from the database
			customer.setCustId(custId);
			System.out.println("/newcustomer POST: created new customer:\n" + customer.toString());
			System.out.println("/newcustomer POST: retrieved the new customer from DB:\n"
				+ ds.dbRetrieveUserById(custId).toString());
		}
		ds.close();
		
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
	public String processLoginScreen(@RequestParam String username, @RequestParam String password, ModelMap map) {
//		String pageToReturn = "redirect:dashboard";
		String pageToReturn = "dashboard";
		int custId = 0;
		if(username == null || password == null) {
			map.put("errormessage", "Username and password must not be blank");
			pageToReturn = "login";
		}
		else {
			custId = LoginService.validateCredentials(username, password);
			if(custId > 0) {
				map.put("username", username);
				map.put("customerid", custId);
				//Put the current balances into the model
				map.addAttribute("chkbal", 200.0);
				map.addAttribute("savbal", 300.0);
				map.addAttribute("loanbal", 400.0);
				BankService.setCustIndex(custId);
//				BankService.doCustomerTransactions();
			}
			else {
				map.put("errormessage", "Invalid login credentials");
				pageToReturn = "login";
			}
		}
		return pageToReturn;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String showLogoutScreen(@ModelAttribute("customerid") int custId, ModelMap map) {
		//Reset the model map to a logged-out state
		map.put("username", null);
		map.put("customerid", 0);
		
		return "login";
	}
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String showDashboardScreen(
		@RequestParam("username") String username,
		@RequestParam("customerid") int custId,
		@RequestParam("chkbal") double chkBal,
		@RequestParam("savbal") double savBal,
		@RequestParam("loanbal") double loanBal,
		ModelMap map) {
		String jspToAccess = "dashboard";
		
		//Verify there is a logged-in customer
		if(custId != 0) {
			//Put the current balances into the model
			map.put("username", username);
			map.put("customerid", custId);
			map.put("chkbal", chkBal);
			map.put("savbal", savBal);
			map.put("loanbal", loanBal);
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/deposit-bank", method = RequestMethod.GET)
	public String showDepositScreen(@ModelAttribute("username") String username,
		@ModelAttribute("customerid") int custId,
		ModelMap map) {
		String jspToAccess = "deposit-bank";
		//Verify there is a logged-in customer
		if(custId != 0) {
			//Put the account numbers into the model
			map.addAttribute("acctchk", 1234);
			map.addAttribute("acctsav", 2345);
			map.addAttribute("acctloan", 3456);
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/deposit-bank", method = RequestMethod.POST)
	public String processDeposit(@ModelAttribute("customerid") int custId,
			@ModelAttribute("username") String username,
			@RequestParam("account") String accountType,
			@RequestParam("amountdeposit") double amountToDeposit,
			ModelMap map) {
		String jspToAccess = "login";
		
		//Verify there is a logged-in customer
		if(custId != 0) {
			jspToAccess = "dashboard";
			//Do the deposit for the customer
			
			//Update the dashboard values
			switch(accountType) {
				case "chk":
					map.put("chkbal", 333.0);
					map.put("savbal", 444.0);
					map.put("loanbal", 555.0);
					break;
				case "sav":
					map.put("chkbal", 300.0);
					map.put("savbal", 400.0);
					map.put("loanbal", 500.0);
					break;
				case "loan":
					map.put("chkbal", 300.0);
					map.put("savbal", 400.0);
					map.put("loanbal", 500.0);
					break;
				default:
			}
		}
		
		return jspToAccess;
	}	

	@RequestMapping(value = "/withdraw-bank", method = RequestMethod.GET)
	public String showWithdrawScreen(@ModelAttribute("username") String username,
		@ModelAttribute("customerid") int custId,
		ModelMap map) {
		String jspToAccess = "withdraw-bank";
		//Verify there is a logged-in customer
		if(custId != 0) {
			//Put the account numbers into the model
			map.addAttribute("acctchk", 1234);
			map.addAttribute("acctsav", 2345);
			map.addAttribute("acctloan", 3456);
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/withdraw-bank", method = RequestMethod.POST)
	public String processWithdrawal(@ModelAttribute("customerid") int custId,
			@ModelAttribute("username") String username,
			@RequestParam("account") String accountType,
			@RequestParam("amountwithdraw") double amountToWithdraw,
			ModelMap map) {
		String jspToAccess = "login";
		
		//Verify there is a logged-in customer
		if(custId != 0) {
			jspToAccess = "dashboard";
			//Do the withdrawal for the customer
			
			//Update the dashboard values
			switch(accountType) {
				case "chk":
					map.put("chkbal", 222.0);
					map.put("savbal", 333.0);
					map.put("loanbal", 444.0);
					break;
				case "sav":
					map.put("chkbal", 300.0);
					map.put("savbal", 400.0);
					map.put("loanbal", 500.0);
					break;
				case "loan":
					map.put("chkbal", 300.0);
					map.put("savbal", 400.0);
					map.put("loanbal", 500.0);
					break;
				default:
			}
		}
		
		return jspToAccess;
	}

	@RequestMapping(value = "/transfer-bank", method = RequestMethod.GET)
	public String showTransferScreen(@ModelAttribute("username") String username,
		@ModelAttribute("customerid") int custId,
		ModelMap map) {
		String jspToAccess = "transfer-bank";
		//Verify there is a logged-in customer
		if(custId != 0) {
			//Put the account numbers into the model
			map.addAttribute("acctchk", 1234);
			map.addAttribute("acctsav", 2345);
			map.addAttribute("acctloan", 3456);
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/transfer-bank", method = RequestMethod.POST)
	public String processTransfer(@ModelAttribute("customerid") int custId,
			@ModelAttribute("username") String username,
			@RequestParam("accountfrom") String accountFrom,
			@RequestParam("accountto") String accountTo,
			@RequestParam("amounttransfer") double amountToTransfer,
			ModelMap map) {
		String jspToAccess = "login";
		
		//Verify there is a logged-in customer
		if(custId != 0) {
			jspToAccess = "dashboard";
			//Do the transfer for the customer
			
			//Update the dashboard values
			switch(accountFrom) {
				case "chk":
					map.put("chkbal", 444.0);
					map.put("savbal", 222.0);
					map.put("loanbal", 444.0);
					break;
				case "sav":
					map.put("chkbal", 300.0);
					map.put("savbal", 400.0);
					map.put("loanbal", 500.0);
					break;
				case "loan":
					map.put("chkbal", 300.0);
					map.put("savbal", 400.0);
					map.put("loanbal", 500.0);
					break;
				default:
			}
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/statements-bank", method = RequestMethod.GET)
	public String showStatementsScreen(@ModelAttribute("username") String username,
		@ModelAttribute("customerid") int custId,
		ModelMap map) {
		String jspToAccess = "statements-bank";
		//Verify there is a logged-in customer
		if(custId != 0) {
			//Get the transaction lists from each account
			List<Transaction> transchk = new ArrayList<Transaction>();
			transchk.add(new Transaction(new Date(), "chk1234", 123.45, "Test Transaction"));
			transchk.add(new Transaction(new Date(), "chk1234", 234.56, "Test Transaction"));
			transchk.add(new Transaction(new Date(), "chk1234", 345.67, "Test Transaction"));
			List<Transaction> transsav = new ArrayList<Transaction>();
			transsav.add(new Transaction(new Date(), "sav1234", 123.45, "Test Transaction"));
			transsav.add(new Transaction(new Date(), "sav1234", 234.56, "Test Transaction"));
			transsav.add(new Transaction(new Date(), "sav1234", 345.67, "Test Transaction"));
			List<Transaction> transloan = new ArrayList<Transaction>();
			transloan.add(new Transaction(new Date(), "loan1234", 123.45, "Test Transaction"));
			transloan.add(new Transaction(new Date(), "loan1234", 234.56, "Test Transaction"));
			transloan.add(new Transaction(new Date(), "loan1234", 345.67, "Test Transaction"));

			//Put the data into the model
			map.addAttribute("transchk", transchk);
			map.addAttribute("transsav", transsav);
			map.addAttribute("transloan", transloan);
		}
		else {
			jspToAccess = "login";
		}
		
		return jspToAccess;
	}
	
	@RequestMapping(value = "/about-bank", method = RequestMethod.GET)
	public String showAboutScreen(ModelMap map) {
		return "about-bank";
	}
}
