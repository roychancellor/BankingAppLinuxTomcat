package edu.gcu.cst235.milestone.view;

import java.util.List;
import java.util.Scanner;

import edu.gcu.cst235.milestone.model.Account;
import edu.gcu.cst235.milestone.model.Customer;

// These are all the menus for the application
public class Menus {
	
	// Create a scanner for reuse across all methods
	public static Scanner sc = new Scanner(System.in);
	
	// Opening menu for the application
	public static int custMenu() {
		int option = 0;
		try {
			System.out.println("***************************");
			System.out.println("  BANK CUSTOMER MENU  ");
			System.out.println("***************************");
			System.out.println(" 1. Create a customer");
			System.out.println(" 2. Pick a customer");
			System.out.println("---------------------------");
			System.out.println(" 0. Exit");
			System.out.println("***************************");
			System.out.println("What is your choice?");
			String opt = sc.nextLine();
			option = Integer.parseInt(opt);
		} catch (Exception e) { // Can we parse the user's entry?
			System.out.println("Bad customer menu input. Try again!");
			option = -1;
		}
		return option;
	}
	
	
	// CST235 TASK: REMOVE THE LIST PARAMETER
	// Picking a customer for banking transaction menu
	public static int pickCustomerMenu(List<Customer> custs) {
		int number;
		int cust = 0;
		try {
			number = 1;
			System.out.println("***************************");
			System.out.println("  PICK CUSTOMER MENU  ");
			System.out.println("***************************");
			
			// CST235 TASK: CUSTOMER LIST MUST COME FROM THE DB
			for (Customer c : custs) {
				System.out.println(number + ". " + c.toString());
				number++;
			}
			
			System.out.println("---------------------------");
			System.out.println(" 0. Exit");
			System.out.println("***************************");
			System.out.println("Who is your choice?");
			cust = sc.nextInt();
			sc.nextLine();
		} catch (Exception e) { // Can we parse the user's entry?
			System.out.println("Bad customer select. Try again!");
		}
		
		return cust;
	}
	
	// Get user STRING input
	public static String userStrInput(String message) {
		System.out.println(message);
		return sc.nextLine();
	}
	
	// Get user DOUBLE input	
	public static double userDblInput(String message) {	
		double amount = 0.0;
		try {
			System.out.println(message);
			String input = sc.nextLine();
			amount = Double.parseDouble(input);
		} catch (Exception e) { // Can we parse the user's entry?
			System.out.println("Wrong double input\n");
			amount = -1.0;
		}
		return amount;
	}
	
	// Banking transaction menu
	public static int viewCustomerMenu(Customer c, String name) {

		try {
			String option;
			do {
				System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println("                MAIN MENU");
				System.out.println("                " + name.toUpperCase());
				System.out.println("Hello " + c.getFirstName().toUpperCase() + " " + c.getLastName().toUpperCase());				
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println("Pick an option: ");
				System.out.println("-----------------------");
				System.out.println(" 1: Deposit to Checking");
				System.out.println(" 2: Deposit to Savings");
				System.out.println(" 3: Withdraw from Checking");
				System.out.println(" 4: Withdraw from Savings");			
				System.out.println(" 5: Get balance");
				System.out.println(" 6: Make Loan Payment");
				System.out.println(" 7: Get monthly statement");
				System.out.println("------------------------");
				System.out.println(" 9: : Logout");
				option = sc.nextLine();
				return Integer.parseInt(option);
			} while (Integer.parseInt(option) != 9);
		} catch (Exception e) { // Can we parse the user's entry?
			System.out.println("Wrong transaction menu input\n");
			viewCustomerMenu(c, name);
		}
		return 0;
	}
	
	// Balance displays for each account class type
	public static void viewBalances(Customer cust) {
		System.out.println("------------------------");
		System.out.println("CUSTOMER BALANCES");
		System.out.println("------------------------");
		System.out.println("CHECKING : \t" + cust.getChecking().getAccountNumber() + " \t $" + cust.getChecking().getAccountBalance() );
		System.out.println("SAVING :   \t" + cust.getSaving().getAccountNumber() + " \t $" + cust.getSaving().getAccountBalance() );
		System.out.println("LOAN :     \t" + cust.getLoan().getAccountNumber() + " \t $" + cust.getLoan().getAccountBalance() );
		System.out.println("------------------------");
	}

	// Formatted syso method
	public static void printOut(String message) {
		System.out.println(" > " + message);
	}

	// Formatted balance printing method
	public static <T> void printBalance(T obj){
		System.out.println(((Account) obj).getAccountNumber() + " : $" + ((Account) obj).getAccountBalance());
	}
}
