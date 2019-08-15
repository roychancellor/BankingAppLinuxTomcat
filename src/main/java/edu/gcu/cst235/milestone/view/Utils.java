package edu.gcu.cst235.milestone.view;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility methods for the banking application
 */
public class Utils {
	private static Scanner scan = new Scanner(System.in);
	
	/**
	 * helper method that gets an integer between minValue and maxValue from the user
	 * If the user enters anything other than an integer, catches the exception
	 * and prints the error message received from the method call
	 * @param minValue the minimum value of the menu
	 * @param maxValue the maximum value of the menu
	 * @param errorMessage the error message to display for an invalid entry
	 * @return the integer value the user entered
	 */
	public static int getValueFromUser(int minValue, int maxValue, String errorMessage) {
		int userValue = 0;
		boolean invalidSelection;
		
		//Loop until the user enters an integer between the given limits
		do {
			invalidSelection = false;
			try {
				userValue = scan.nextInt();
				if(userValue < minValue || userValue > maxValue) {
					showErrorMessage(errorMessage);
					invalidSelection = true;
				}
			}
			catch(InputMismatchException e) {
				showErrorMessage(errorMessage);
				invalidSelection = true;
				scan.nextLine();
			}
		} while(invalidSelection);

		//scan the next line to clear out the newline character before returning
		scan.nextLine();
		
		return userValue;
	}	

	/**
	 * OVERLOADED helper method that gets an integer between minValue and maxValue from the user
	 * If the user enters anything other than an integer, catches the exception
	 * and prints the error message received from the method call
	 * @param minValue the minimum value of the menu
	 * @param maxValue the maximum value of the menu
	 * @param errorMessage the error message to display for an invalid entry
	 * @return the double value the user entered
	 */
	public static double getValueFromUser(double minValue, double maxValue, String errorMessage) {
		double userValue = 0;
		boolean invalidSelection;
		
		//Loop until the user enters an integer between the given limits
		do {
			invalidSelection = false;
			try {
				userValue = scan.nextDouble();
				if(userValue < minValue || userValue > maxValue) {
					showErrorMessage(errorMessage);
					invalidSelection = true;
				}
			}
			catch(InputMismatchException e) {
				showErrorMessage(errorMessage);
				invalidSelection = true;
				scan.nextLine();
			}
		} while(invalidSelection);

		//scan the next line to clear out the newline character before returning
		scan.nextLine();
		
		return userValue;
	}	
	
	/**
	 * shows the cash error message when user enters the wrong type of cash
	 * @param message the error message to display
	 */
	public static void showErrorMessage(String message) {
		System.out.println("\n" + message);		
	}	

	/**
	 * checks to see if the user entry is a double value or not
	 * @param str is the dtring to test if it contains a double
	 * @return true if double, false if not
	 */
	public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	/**
	 * Returns a phone number validated to be of the form nnn-nnn-nnnn
	 * @return a phone number as a String object
	 */
	public static String getPhoneNumber() {
		boolean keepGoing = false;
		String phoneRegex = "([0-9]{3})[-]([0-9]{3})[-]([0-9]{4})";
		String phoneNumber = "";
		do {
			keepGoing = false;
			System.out.println("\nEnter phone number (nnn-nnn-nnnn): ");
			phoneNumber = scan.nextLine();
			if(!verifyRegex(phoneRegex, phoneNumber)) {
				System.err.println("\nOops, enter phone number as nnn-nnn-nnnn");
				keepGoing = true;
			}
		} while(keepGoing);
		
		return phoneNumber;
	}
	
	/**
	 * Gets a user's email address and validates it against a regular expression
	 * @return email address as a String
	 */
	public static String getEmailAddress() {
		boolean emailInvalid = false;
		String emailRegex = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}";
		String emailAddress = "";
		do {
			emailInvalid = false;
			System.out.println("\nEnter your e-mail address --> ");
			emailAddress = scan.nextLine();
			if(!verifyRegex(emailRegex, emailAddress.toUpperCase())) {
				System.err.println("Oops, email format must be address@domainName.extension");
				emailInvalid = true;				
			}
		} while(emailInvalid);
		
		return emailAddress;
	}
	
	/**
	 * Gets a person's first or last name and validates it against a regular expression
	 * @param userPrompt a string containing a prompt to the user
	 * @return name as a String in proper name format (first letter capitalized)
	 */
	public static String getPersonName(String userPrompt) {
		boolean nameInvalid = false;
		String nameRegex = "[A-Z]{2,100}";
		String name = "";
		do {
			nameInvalid = false;
			System.out.println("\n" + userPrompt);
			name = scan.nextLine();
			if(!verifyRegex(nameRegex, name.toUpperCase()) /*|| name.length() < 2 || name.length() > 100*/) {
				System.err.println("Oops, name must be 2-100 characters and contain only letters, dash, or apostrophe.");
				nameInvalid = true;				
			}
		} while(nameInvalid);
		
		return name;
	}
	
	/**
	 * Gets a user's username and validates it against a regular expression
	 * @param userPrompt a string containing a prompt to the user
	 * @return username as a String
	 */
	public static String getUsername(String userPrompt) {
		boolean usernameInvalid = false;
		String usernameRegex = "[A-Za-z0-9]{2,200}";
		String username = "";
		do {
			usernameInvalid = false;
			System.out.println("\n" + userPrompt);
			username = scan.nextLine();
			if(!verifyRegex(usernameRegex, username) /*|| username.length() < 2 || username.length() > 200*/) {
				System.err.println("Oops, username must be 2-200 characters and contain only letters and numbers.");
				usernameInvalid = true;				
			}
		} while(usernameInvalid);
		
		return username;
	}
	
	/**
	 * Gets a user's password and validates it against a regular expression
	 * @param userPrompt a string containing a prompt to the user
	 * @return password as a String
	 */
	public static String getPassword(String userPrompt) {
		boolean passwordInvalid = false;
		String passwordRegex = "[A-Za-z0-9!]{8,200}";
		String password = "";
		do {
			passwordInvalid = false;
			System.out.println("\n" + userPrompt);
			password = scan.nextLine();
			if(!verifyRegex(passwordRegex, password) /*|| password.length() < 2 || password.length() > 200*/) {
				System.err.println("Oops, password must be 8-200 characters"
					+ " and contain only letters, numbers, and the special character '!'.");
				passwordInvalid = true;				
			}
		} while(passwordInvalid);
		
		return password;
	}
	
	/**
	 * Checks whether a string matches a regular expression pattern
	 * @param regex the regular expression string to check against
	 * @param stringToTest the string to test for a match to the regex
	 * @return true if string matches pattern; false otherwise
	 */
	public static boolean verifyRegex(String regex, String stringToTest) {
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(stringToTest);
		if(match.matches()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets a string of minimum length from the user
	 * @param minLength the minimum allowable length of the user-entered string
	 * @param prompt the prompt message to the user for what to enter
	 * @return the string the user entered
	 */
	public static String getStringMinLength(int minLength, String prompt) {
		boolean stringInvalid = false;
		String str = "";
		
		if(minLength > 0 && prompt != null) {
			do {
				stringInvalid = false;
				System.out.println("\n" + prompt);
				str = scan.nextLine();
				
				if(str == null || str.length() < minLength) {
					System.err.println("\nOops, you must enter at least " + minLength + " characters. Try again.");
					stringInvalid = true;
				}
			} while(stringInvalid);
		}
		else {
			str = null;
		}
		
		return str;
	}
	
}
