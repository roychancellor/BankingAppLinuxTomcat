package edu.gcu.bootcamp.cst135.milestone.controller;

import java.util.Scanner;

public class Bank {

	Scanner scanner = new Scanner(System.in);
	Saving saving = new Saving("-SAV12345",5000,200,25,.06);
	Checking checking = new Checking("-CHK23456",5000,25);

	private void viewExitScreen() {
		System.out.println("Goodbye, Have a good day!");
	}

	public void viewCustomerMenu() {

		try {
			String option;
			do {
				System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println("                MAIN MENU");
				System.out.println("                GCU BANK");
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println("Pick an option: ");
				System.out.println("-----------------------");
				System.out.println(" 1: Deposit to Checking");
				System.out.println(" 2: Deposit to Savings");
				System.out.println(" 3: Withdraw from Checking");
				System.out.println(" 4: Withdraw from Savings");			
				System.out.println(" 5: Get balance");
				System.out.println(" 6: Get monthly statement");
				System.out.println("------------------------");
				System.out.println(" 9: : Logout");
				option = scanner.nextLine();
				processCustomerMenu(Integer.parseInt(option));
			} while (Integer.parseInt(option) != 9);
		}catch(Exception e) {
			System.out.println("Wrong input\n");
			viewCustomerMenu();
		}
	}

	private void processCustomerMenu(int parseInt) {

		switch(parseInt) {
		case 1: viewDepositChecking();viewBalances();
		break;
		case 2: viewDepositSavings();viewBalances();
		break;
		case 3: viewWithdrawalChecking();viewBalances();
		break;
		case 4: viewWithdrawalSavings();viewBalances();
		break;
		case 5: viewBalances();
		break;
		case 6: viewEndOfMonth();viewBalances();
		break;  
		case 9: viewExitScreen();
		break;
		default: viewCustomerMenu();
		}
	}

	private void viewEndOfMonth() {

		System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("               END OF MONTH");
		System.out.println("                 GCU BANK");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");

		if(saving.getAccountBalance() < saving.getMinBalance()) {
			System.out.printf("A $%.2f service fee is being assessed for below minimum balance in savings", saving.getServiceFee());
			saving.setAccountBalance(saving.getAccountBalance() - saving.getServiceFee());
		}
		if (saving.getAccountBalance() > 0){
			saving.setAccountBalance(saving.getAccountBalance() + (saving.getInterest() * saving.getAccountBalance()));
		}	
	}		

	private void viewWithdrawalChecking() {

		try {
			System.out.println("How much would you like to withdraw: ");
			double input = scanner.nextDouble();
			scanner.nextLine();
			processWithdrawalChecking(input);
		}catch(Exception e) {
			System.out.println("Wrong input\n");
			viewWithdrawalChecking();
		}
	}

	private void processWithdrawalChecking(double input) {

		if(checking.getAccountBalance() < input) {
			System.out.println("A $" + checking.getOverdraft() + " overdraft fee will be assessed if you continue. Continue Y or N?");
			if(scanner.nextLine().toLowerCase().equals("y")) {
				checking.setAccountBalance(checking.getAccountBalance() - input - checking.getOverdraft());
			}else
				viewWithdrawalChecking();
		}else
			checking.setAccountBalance(checking.getAccountBalance() - input);
	}

	private void viewDepositSavings() {

		try {
			System.out.println("How much would you like to deposit: ");
			double input = scanner.nextDouble();
			scanner.nextLine();
			processDepositSavings(input);
		}catch(Exception e) {
			System.out.println("Wrong input\n");
			viewDepositSavings();
		}
	}

	private void processDepositSavings(double input) {

		saving.setAccountBalance(saving.getAccountBalance() + input);
	}

	private void viewDepositChecking() {

		try {
			System.out.println("How much would you like to deposit: ");
			double input = scanner.nextDouble();
			scanner.nextLine();
			processDepositChecking(input);
		}catch(Exception e) {
			System.out.println("Wrong input\n");
			viewDepositChecking();
		}
	}

	private void processDepositChecking(double input) {

		checking.setAccountBalance(checking.getAccountBalance() + input);
	}

	private void viewWithdrawalSavings() {

		try {
			System.out.println("How much would you like to withdraw: ");
			double input = scanner.nextDouble();
			scanner.nextLine();
			processWithdrawalSavings(input);
		}catch(Exception e) {
			System.out.println("Wrong input\n");
			viewWithdrawalSavings();
		}
	}

	private void processWithdrawalSavings(double input) {

		saving.setAccountBalance(saving.getAccountBalance() - input);	
	}

	private void viewBalances() {

		System.out.println("\n------------------------");	
		System.out.println(saving.toString());
		System.out.println(checking.toString());
		System.out.println("------------------------");
	}
}