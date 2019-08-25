package edu.gcu.cst341.milestone;

import edu.gcu.cst341.controller.Bank;

/**
 * Class contains the main() method that creates a new Bank object and
 * calls the viewCustomerMenu() method to initiate the user interface. The user interface
 * takes over control of the program.
 */
public class BankingApp {
	/**
	 * controller to open the bank and call the main menu
	 * @param args command-line arguments, not currently implemented
	 */
	public static void main(String[] args) {
		//Open the bank (will open a connection to the bank database
		Bank bank = new Bank("ROY BANK 3.0");
		
		//Run the bank with the primary user interface
		bank.runBank();
	}
}