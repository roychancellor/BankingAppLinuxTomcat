package edu.gcu.cst235.milestone.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import edu.gcu.cst235.milestone.model.Customer;

// These are all the menus for the application
public class Menus {
	
	// Create a scanner for reuse across all methods
	public static Scanner scan = new Scanner(System.in);
	
	//Class data
	public static final int MENU_EXIT = 0;
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
	
	/**
	 * Displays the highest level (main) menu and gets a user selection.
	 * @param bankName the name of the bank
	 * @return the integer value of the user's selection
	 */
	public static int viewMainMenu(String bankName) {
		System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("          MAIN MENU");
		System.out.println("        " + bankName);
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("\nPick an option: ");
		System.out.println("----------------------------");
		System.out.println(" 1 : Customer Management");
		System.out.println(" 2 : Customer Transactions");
		System.out.println("----------------------------");
		System.out.println(" " + MENU_EXIT + " : Exit Banking Application");
		return Utils.getValueFromUser(MENU_EXIT, 2, "Oops, enter a correct menu item.");
	}

	/**
	 * Displays the customer selection menu and gets a user selection.
	 * @return the integer value of the user input
	 */
	public static int viewManageCustomerMenu() {
		System.out.println("\n============================");
		System.out.println("    Customer Management");
		System.out.println("============================");
		System.out.println(" 1 : Enter New Customer");
		System.out.println(" 2 : Modify Customer");
		System.out.println("-------------------------");
		System.out.println(" " + MENU_EXIT + " : Return to Main Menu");
		return Utils.getValueFromUser(MENU_EXIT, 2, "Oops, enter a correct menu item.");
	}
	
	/**
	 * helper method for getting customer names
	 * @param userPrompt the prompt message to the user
	 * @return the customer's name
	 */
	public static String getCustomerName(String userPrompt) {
		return Utils.getPersonName(userPrompt);
	}
	
	/**
	 * helper method for getting customer username
	 * @param userPrompt the prompt message to the user
	 * @return the customer's username
	 */
	public static String getCustomerUserName(String userPrompt) {
		return Utils.getUsername(userPrompt);
	}
	
	/**
	 * helper method for getting customer password
	 * @param userPrompt the prompt message to the user
	 * @return the customer's password
	 */
	public static String getCustomerPassword(String userPrompt) {
		return Utils.getPassword(userPrompt);
	}
	
	/**
	 * Displays the customer selection menu and gets a user selection.
	 * @param customers an ArrayList of Customer objects to display
	 * @return the integer selection of customer from the list of customers
	 */
	public static int viewCustomerSelectionMenu(List<Customer> customers) {
		System.out.println("\n==============================");
		System.out.println("   MASTER CUSTOMER LIST");
		System.out.println("   Select Customer:");
		System.out.println("==============================");
		for(int i = 0; i < customers.size(); i++) {
			System.out.println(
				" " + (i + 1) + " : "
				+ customers.get(i).getFirstName()
				+ " " + customers.get(i).getLastName()
			);
		}
		System.out.println("------------------------");
		System.out.println(" " + MENU_EXIT + " : Return to Main Menu");
		
		return Utils.getValueFromUser(MENU_EXIT, customers.size(), "Oops, enter a correct menu item.");
	}
	
	/**
	 * gets a username and password from a customer
	 * @return username and password in a String array (element 0 = username, element 1 = password)
	 */
	public static String[] viewCustomerLogin() {
		printHeaderLine(29);
		System.out.println("CUSTOMER LOGIN");
		System.out.println("1. Enter username and password");
		printHeaderLine(29);
		System.out.println("0. Cancel and return to main menu");
		printHeaderLine(29);
		
		System.out.println("\nMake selection:");
		int menuSelect = Utils.getValueFromUser(0, 1, "Oops, enter a valid menu item");
		
		if(menuSelect != MENU_EXIT) {
			String[] credentials = new String[2];
			System.out.println("\nUsername:");
			String username = scan.nextLine();
			System.out.println("\nPassword:");
			String password = scan.nextLine();
			credentials[0] = username;
			credentials[1] = password;
			
			return credentials;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Displays the customer action menu and gets a user selection.
	 * @param bankName the name of the bank
	 * @param cust a Customer object for the current customer performing transactions
	 * @return the integer value of the transaction the customer wants to perform
	 */
	public static int viewCustomerActionMenu(String bankName, Customer cust) {
		System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("       CUSTOMER TRANSACTION MENU");
		System.out.println("                " + bankName);
		System.out.println("        Welcome " + cust.getFirstName() + " " + cust.getLastName() + "!");
		System.out.println("          " + dateFormat.format(new Date()));
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("\nPick an option: ");
		printHeaderLine(29);
		System.out.println(" 1 : Deposit to Checking");
		System.out.println(" 2 : Deposit to Savings");
		System.out.println(" 3 : Withdraw from Checking");
		System.out.println(" 4 : Withdraw from Savings");			
		System.out.println(" 5 : Make a Loan Payment");			
		System.out.println(" 6 : View Loan amortization");			
		System.out.println(" 7 : Get Account Balances");
		System.out.println(" 8 : Get Monthly Statement");
		printHeaderLine(29);
		System.out.println(" " + MENU_EXIT + " : Return to Customer Login");
		return Utils.getValueFromUser(MENU_EXIT, 8, "Oops, enter a correct menu item.");
	}

	/**
	 * Shows the end of month screen and performs the end-of-month calculations
	 * @param cust a Customer object for the current customer to print transactions
	 */
	public static void viewEndOfMonth(Customer cust) {

		System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("                                 GCU BANK");
		System.out.println("                         END OF MONTH STATEMENT");
		System.out.println("                       for customer " + cust.getFirstName() + " " + cust.getLastName());
		System.out.println("                          " + Menus.dateFormat.format(new Date()));
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

		//Determine if the end of the month has occurred
		//Stub-out only - NOT IMPLEMENTED IN THIS VERSION
		boolean endOfMonth = true;
		if(endOfMonth) {
			System.out.println("\nMonthly charges and credits:");
			printHeaderLine(65);
			cust.getSaving().doEndOfMonth();
			cust.getLoan().doEndOfMonth();
			
			//Display the transaction list
			System.out.println("\nDate and Time\t\tAccount\t\tAmount\t\tDescription");
			printHeaderLine(75);
			cust.getChecking().displayTransactions();
			printHeaderLine(75);
			cust.getSaving().displayTransactions();
			printHeaderLine(75);
			cust.getLoan().displayTransactions();
		}
		else {
			System.out.println("\nSorry, the <current month> is not complete.");
		}
	}

	/**
	 * Displays all account balances
	 * @param cust a Customer object for the current customer
	 */
	public static void viewBalances(Customer cust) {
		System.out.println(cust.toString(false));
	}
	
	/**
	 * Outputs a message to the customer when exiting the customer transaction menu
	 * @param cust a Customer object for the current customer
	 */
	public static void viewCustomerExit(Customer cust) {
		System.out.println("\nGoodbye " + cust.getFirstName() + ". Have a good day!\n");
	}

	/**
	 * helper method that prints a series of dashes for use as a header underline
	 * @param numDashes the number of dashes to print in a single line
	 */
	public static void printHeaderLine(int numDashes) {
		for(int i = 0; i < numDashes; i++)
			System.out.print("-");
		System.out.println();
	}

	// Get user STRING input
	public static String userStrInput(String message) {
		System.out.println(message);
		return scan.nextLine();
	}
}
