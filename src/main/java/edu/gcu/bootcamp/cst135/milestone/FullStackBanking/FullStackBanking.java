/**
 * Class FullStackBanking contains the main() method that creates a new Bank object and
 * calls the viewCustomerMenu() method to initiate the user interface. The user interface
 * takes over control of the program.
 */
package edu.gcu.bootcamp.cst135.milestone.FullStackBanking;

//Imports that are required for this class
import edu.gcu.bootcamp.cst135.milestone.controller.Bank;

public class FullStackBanking {
	/**
	 * controller to open the bank and call the main menu
	 * @param args command-line arguments, not currently implemented
	 */
	public static void main(String[] args) {
		//Open the bank
		Bank bank = new Bank("GCU BANK");
		
		//Call the primary user interface
		bank.viewMainMenu();
	}
}