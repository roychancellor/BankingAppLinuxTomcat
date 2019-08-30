package edu.gcu.cst341.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Driver;

import edu.gcu.cst341.database.DbConstants;
import edu.gcu.cst341.model.Account;
import edu.gcu.cst341.model.Checking;
import edu.gcu.cst341.model.Customer;
import edu.gcu.cst341.model.Loan;
import edu.gcu.cst341.model.Saving;

/**
 * Class contains SQL CRUD functions for the banking application
*/
public class DataService {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement sql;
	private boolean verboseSQL;
	private boolean connectedToDb;
	private boolean productionDb;
	private Driver mySQLDriver;
	
	//Constructor
	/**
	 * Constructor for class: Opens a MySQL database and creates a Statement object
	 * @param verboseSQL when true will print all SQL commands to the console
	 * @param productionDb when true will open the AWS-hosted bank db; false opens the local bank db for testing
	 */
	public DataService() {
		this.verboseSQL = true;
		this.productionDb = false;
		if(connectToDatabase()) {
			System.out.println("\nDataSource CONNECTED TO DB...");
		}
		else {
			System.out.println("\nDataSource ERROR: NOT CONNECTED!!!");
		}
	}
	
	//Getters and Setters
	/**
	 * @return the connection
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @return the stmt (a Statement object)
	 */
	public Statement getStmt() {
		return stmt;
	}

	/**
	 * @return the rs (a ResultSet object)
	 */
	public ResultSet getRs() {
		return rs;
	}

	/**
	 * @return the sql (a PreparedStatement)
	 */
	public PreparedStatement getSql() {
		return sql;
	}

	/**
	 * @return the verboseSQL setting
	 */
	public boolean isVerboseSQL() {
		return verboseSQL;
	}

	/**
	 * @param verboseSQL the verboseSQL to set
	 */
	public void setVerboseSQL(boolean verboseSQL) {
		this.verboseSQL = verboseSQL;
	}

	/**
	 * @return the connectedToDb
	 */
	public boolean isConnectedToDb() {
		return connectedToDb;
	}

	/**
	 * @param connectedToDb the connectedToDb to set
	 */
	public void setConnectedToDb(boolean connectedToDb) {
		this.connectedToDb = connectedToDb;
	}

	/**
	 * @return the productionDb flag
	 */
	public boolean isProductionDb() {
		return productionDb;
	}

	/**
	 * @param productionDb the productionDb to set
	 */
	public void setProductionDb(boolean productionDb) {
		this.productionDb = productionDb;
	}

	/**
	 * connects to a user-specified database
	 * @return true if connection to DB successful, false if not
	 */
	public boolean connectToDatabase() {
		String dbURL = "";
		String dbUser = "";
		String dbPassword = "";
		
		//Get the database connection from the user
		if(isProductionDb()) {
			dbURL = DbConstants.DB_URL_AWSDB;
			dbUser = DbConstants.USER_NAME_AWS;
			dbPassword = DbConstants.PASSWORD_AWS;
		}
		else {
			dbURL = DbConstants.DB_URL_LOCALDB;
			dbUser = DbConstants.USER_NAME_LOCAL;
			dbPassword = DbConstants.PASSWORD_LOCAL;
		}

		//Open connection to database
		try {
			// load and register JDBC driver for MySQL
//			Class.forName("com.mysql.jdbc.Driver");
			this.mySQLDriver = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(this.mySQLDriver);
			Class.forName("com.mysql.cj.jdbc.Driver");

			if(verboseSQL) System.out.print("Connecting to " + dbURL);
			this.conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
			if(verboseSQL) System.out.println("...SUCCESS!");
			
			//Create a Statement object
			try {
				if(verboseSQL) System.out.print("\nCreating a Statement object for the Connection");
				this.stmt = conn.createStatement();
				if(verboseSQL) System.out.println("...SUCCESS!");
				this.connectedToDb = true;
			}
			catch(SQLException e) {
				printMethod(new Throwable().getStackTrace()[0].getMethodName());
				e.printStackTrace();
				this.connectedToDb = false;
			}				
		}
		catch(SQLException e) {
			printMethod(new Throwable().getStackTrace()[0].getMethodName());
			e.printStackTrace();
			this.connectedToDb = false;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connectedToDb;
	}
	
	/**
	 * Helper method that prints a class name
	 * @param methodName the name of the class to print
	 */
	private void printMethod(String methodName) {
		System.out.println("\n***** Error in " + methodName + "\n");
	}
	
	//Class CRUD methods
	/**
	 * Closes a JDBC database connection
	 */
	public void close() {
		try {
			if(verboseSQL) System.out.print("\nClosing connection to "
				+ (this.isProductionDb() ? DbConstants.DB_URL_AWSDB : DbConstants.DB_URL_LOCALDB));
			this.conn.close();
			DriverManager.deregisterDriver(this.mySQLDriver);
			if(verboseSQL) System.out.println("...SUCCESS!");
		}
		catch(SQLException e) {
			printMethod(new Throwable().getStackTrace()[0].getMethodName());
			e.printStackTrace();
		}
	}

	/**
	 * Adds a customer to the customers table
	 * @param lastName the contact name being added
	 * @param firstName the contact phone being added
	 * @param username the customer's username
	 * @param passSalt the hashed salt for the customer's password
	 * @param passHash the hashed customer password
	 * @return the customerId of the created customer if successful; -1 if unsuccessful
	 */
	public int createCustomer(String lastName, String firstName, String email, String phone,
		String username, String passSalt, String passHash) {
		try {
			if(verboseSQL) System.out.print("Adding customer " + lastName + ", " + firstName);
			
			//WRITE TO CUSTOMERS TABLE
			//Prepare the SQL statement
			sql = conn.prepareStatement(DbConstants.CREATE_CUSTOMER, Statement.RETURN_GENERATED_KEYS);
			if(verboseSQL) printSQL();

			//Populate statement parameters
			sql.setString(1, lastName);
			sql.setString(2, firstName);
			sql.setString(3, email);
			sql.setString(4, phone);
			
			//Execute SQL statement
			int numRec = sql.executeUpdate();
			
			if(verboseSQL) System.out.println("...Success, " + numRec + " record(s) inserted into customers table.");
			
			//GET THE AUTO-GENERATED CUSTOMER ID FOR THE NEW CUSTOMER TO USE FOR WRITING CREDENTIALS
			int customerId = -1;
			rs = sql.getGeneratedKeys();
			if(rs.next()) {
				customerId = rs.getInt(1);
			}
			else {
				System.out.println("\nERROR: No key found!!!");
			}
			
			//WRITE TO CREDENTIALS TABLE
			if(customerId > 0) {
				//Prepare the SQL statement
				sql = conn.prepareStatement(DbConstants.CREATE_CREDENTIALS);
				if(verboseSQL) printSQL();
	
				//Populate statement parameters
				sql.setInt(1, customerId);
				sql.setString(2, username);
				sql.setString(3, passSalt);
				sql.setString(4, passHash);
				
				//Execute SQL statement
				numRec = sql.executeUpdate();
				
				if(verboseSQL) System.out.println("...Success, " + numRec + " record(s) inserted into credentials table.");
				return customerId;
			}
			else {
				if(verboseSQL) System.out.println("\nNO CREDENTIALS WRITTEN");
				return -1;
			}
		}
		catch(SQLException e) {
			printMethod(new Throwable().getStackTrace()[0].getMethodName());
			e.printStackTrace();
		}
		
		return -1;
	}

	/**
	 * Retrieves the customer information from the customers table where customerId = the passed value
	 * @param customerIdToRetrieve the customerId to retrieve from the database
	 * @return a Customer object created from the database information
	 */
	public Customer dbRetrieveCustomerById(int customerIdToRetrieve) {
		Customer cust = new Customer();
//		String sql = "SELECT bank.customers.customerId,"
//				+ "bank.customers.lastName,"
//				+ "bank.customers.firstName,"
//				+ "bank.customers.email,"
//				+ "bank.customers.phone,"
//				+ "bank.credentials.userName,"
//				+ "bank.credentials.passwordHash from bank.customers, bank.credentials"
//				+ " WHERE bank.credentials.customerId=" + customerIdToRetrieve
//				+ " AND bank.customers.customerId=" + customerIdToRetrieve;
		if(this.connectedToDb) {
			//GET THE CUSTOMER INFORMATION
			try {
				//Prepare the SQL statement
				sql = conn.prepareStatement(DbConstants.GET_CUSTOMER_BY_ID);
				if(verboseSQL) printSQL();
	
				//Populate statement parameters
				sql.setInt(1, customerIdToRetrieve);
				sql.setInt(2, customerIdToRetrieve);
				
				//Execute SQL statement
//				numRec = sql.executeUpdate();
				rs = sql.executeQuery();
				//Execute SQL statement
//				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					cust.setCustId(rs.getInt("customerId"));
					cust.setLastName(rs.getString("lastName"));
					cust.setFirstName(rs.getString("firstName"));
					cust.setEmailAddress(rs.getString("email"));
					cust.setPhoneNumber(rs.getString("phone"));
					cust.setUsername(rs.getString("userName"));
					cust.setPassword(rs.getString("passwordHash"));
				}
			}
			catch(SQLException e) {
				System.out.println("\nERROR: UNABLE TO RETRIEVE CUSTOMER ID = " + customerIdToRetrieve + "!!!");
				e.printStackTrace();
			}
			//GET THE CUSTOMER ACCOUNT BALANCE INFORMATION
			try {
				//Prepare the SQL statement
				sql = conn.prepareStatement(DbConstants.GET_CUSTOMER_BALANCES_BY_ID);
				if(verboseSQL) printSQL();
	
				//Populate statement parameters
				sql.setInt(1, customerIdToRetrieve);
				
				//Execute SQL statement
//				numRec = sql.executeUpdate();
				rs = sql.executeQuery();
				//Execute SQL statement
//				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					cust.getChecking().setAccountBalance(rs.getDouble("checkingBalance"));
					cust.getSaving().setAccountBalance(rs.getDouble("savingBalance"));
					cust.getLoan().setAccountBalance(rs.getDouble("loanBalance"));
				}
			}
			catch(SQLException e) {
				System.out.println("\nERROR: UNABLE TO RETRIEVE CUSTOMER ID = " + customerIdToRetrieve + "!!!");
				e.printStackTrace();
			}
		}
		return cust;
	}
	
	/**
	 * Updates account balance and writes a transaction to the database
	 * @param <E> an Account object (Checking, Saving, or Loan)
	 * @param customerId The customerId being updated
	 * @param account The Account object being updated (Checking, Saving, Loan)
	 * @return true if successful SQL; false if the SQL failed
	 */
	public <E> boolean dbUpdateBalanceAndTransaction(int customerId, E account) {
		boolean result = true;
		String accountField = null;
		String accountNumber = null;
		double accountBalance = 0;
		double transAmount = 0;
		String transDescription = null;
		try {
			//WRITE TO CUSTOMER_ACCOUNTS TABLE
			//Populate statement parameters
			if(account instanceof Checking) {
				accountField = "checkingBalance";
				accountBalance = ((Checking) account).getAccountBalance();
				accountNumber = ((Checking) account).getAccountNumber();
				transAmount = ((Checking) account).getLastTrans().getAmount();
				transDescription = ((Checking) account).getLastTrans().getTransactionType();
			}
			else if(account instanceof Saving) {
				accountField = "savingBalance";
				accountBalance = ((Saving) account).getAccountBalance();
				accountNumber = ((Saving) account).getAccountNumber();
				transAmount = ((Saving) account).getLastTrans().getAmount();
				transDescription = ((Saving) account).getLastTrans().getTransactionType();
			}
			else if(account instanceof Loan) {
				accountField = "loanBalance";
				accountBalance = ((Loan) account).getAccountBalance();
				accountNumber = ((Loan) account).getAccountNumber();
				transAmount = ((Loan) account).getLastTrans().getAmount();
				transDescription = ((Loan) account).getLastTrans().getTransactionType();
			}
			
			//Prepare the SQL statement to UPDATE an account balance by customer id
			final String UPDATE_BALANCE_BY_ID =
				"UPDATE " + DbConstants.DB_NAME + "." + DbConstants.CUSTOMER_ACCOUNTS_TABLE
				+ " SET " + accountField + "=?"
				+ " WHERE " + DbConstants.CUSTOMER_ID + "=?";
			
			sql = conn.prepareStatement(UPDATE_BALANCE_BY_ID);
			sql.setDouble(1, accountBalance);
			sql.setInt(2, customerId);
			if(verboseSQL) printSQL();
			
			//Execute SQL statement
			int numRec = sql.executeUpdate();
			
			if(verboseSQL) System.out.println("...Success, " + numRec
				+ " record(s) inserted into customer_accounts table.");
			
			//WRITE TO CUSTOMER_TRANSACTIONS TABLE
			if(numRec > 0) {
				//Prepare the SQL statement
				sql = conn.prepareStatement(DbConstants.UPDATE_TRANSACTION);
	
				//Populate statement parameters
				sql.setInt(1, customerId);
				sql.setString(2, accountNumber);
				sql.setDouble(3, transAmount);
				sql.setString(4, transDescription);
				
				//Execute SQL statement
				if(verboseSQL) printSQL();
				numRec = sql.executeUpdate();
				
				if(verboseSQL) System.out.println("...Success, " + numRec
					+ " record(s) inserted into customer_transactions table.");
			}
			else {
				if(verboseSQL) System.out.println("\nNO DATA WRITTEN");
			}
		}
		catch(SQLException e) {
			printMethod(new Throwable().getStackTrace()[0].getMethodName());
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Retrieves all customers from the database and returns an ArrayList of Customer objects
	 * sorted by last name, first name
	 * @return ArrayList of Customer objects
	 */
	public List<Customer> makeCustomerListFromDatabase() {
		List <Customer> customerList = new ArrayList<Customer>();
		try {
			//Prepare the SQL statement
			String sql = DbConstants.GET_CUSTOMERS_UNORDERED;
			if(verboseSQL) System.out.println("Executing SQL: " + sql);
			
			//Execute SQL statement and get a result set
			this.rs = stmt.executeQuery(sql);
			
			//Process the result set
			while(this.rs.next()) {
				//Read the fields in the current record and store in new Customer object
				Customer cust = new Customer(
					rs.getInt(DbConstants.CUSTOMER_ID),
					rs.getString(DbConstants.CUSTOMER_LAST_NAME),
					rs.getString(DbConstants.CUSTOMER_FIRST_NAME),
					rs.getDate(DbConstants.CUSTOMER_DATE_OPENED)
				);
				
				//Add the customer to the list of customers
				customerList.add(cust);
			}
			
			return customerList;
		}
		catch(SQLException e) {
			printMethod(new Throwable().getStackTrace()[0].getMethodName());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * queries the database for unique hashed username and hashed password combination
	 * @param hashedUsername the hashed username
	 * @param hashedSalt the hashed password salt
	 * @param hashedPassword the hashed password
	 * @return the customer id corresponding to the username and password record
	 */
	public int checkLoginCredentials(String hashedUsername, String hashedSalt, String hashedPassword) {
		try {
			//Prepare the SQL statement
			sql = conn.prepareStatement(DbConstants.GET_CUSTOMER_ID_FROM_CREDENTIALS);
			if(verboseSQL) printSQL();
			
			//Populate statement parameters
			sql.setString(1, hashedUsername);
			sql.setString(2, hashedSalt);
			sql.setString(3, hashedPassword);
			
			//Execute SQL statement
			rs = sql.executeQuery();
			
			//Print the result
			if(rs.next()) {
				int custId = rs.getInt(1);
				if(verboseSQL) System.out.println("...Success, found username and password for customerId = " + custId);
				return custId;
			}
			else {
				if(verboseSQL) System.out.println("...Unable to find the user.");
				return -1;
			}
		}
		catch(SQLException e) {
			printMethod(new Throwable().getStackTrace()[0].getMethodName());
			e.printStackTrace();
		}
		
		return -1;
	}

	/**
	 * helper method prints the SQL statement to the console
	 */
	private void printSQL() {
		System.out.println("\nExecuting SQL: " + sql.toString());
	}
}
