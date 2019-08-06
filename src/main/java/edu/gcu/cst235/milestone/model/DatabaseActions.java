package edu.gcu.cst235.milestone.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains SQL CRUD functions for the banking application
*/
public class DatabaseActions {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement sql;
	private boolean verboseSQL;
	private boolean connectedToDb;
	private boolean productionDb;
	
	//Constructor
	/**
	 * Constructor for class: Opens a MySQL database and creates a Statement object
	 * @param verboseSQL when true will print all SQL commands to the console
	 * @param productionDb when true will open the AWS-hosted bank db; false opens the local bank db for testing
	 */
	public DatabaseActions(boolean verboseSQL, boolean productionDb) {
		this.verboseSQL = verboseSQL;
		this.productionDb = productionDb;
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
	 * @param productionDbDb the productionDb to set
	 */
	public void setProductionDb(boolean localDb) {
		this.productionDb = localDb;
	}

	/**
	 * connects to a user-specified database
	 * @param verboseSQL
	 */
	public boolean connectToDatabase() {
		String dbURL = "";
		String dbUser = "";
		String dbPassword = "";
		
		//Get the database connection from the user
		if(isProductionDb()) {
			dbURL = DbConstants.DB_URL_AWS;
			dbUser = DbConstants.USER_NAME_AWS;
			dbPassword = DbConstants.PASSWORD_AWS;
		}
		else {
			dbURL = DbConstants.DB_URL_LOCAL;
			dbUser = DbConstants.USER_NAME_LOCAL;
			dbPassword = DbConstants.PASSWORD_LOCAL;
		}

		//Open connection to database
		try {
			System.out.print("Connecting to " + dbURL);
			this.conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
			System.out.println("...SUCCESS!");
			
			//Create a Statement object
			try {
				System.out.print("\nCreating a Statement object for the Connection");
				this.stmt = conn.createStatement();
				System.out.println("...SUCCESS!");
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
		return connectedToDb;
	}
	
	/**
	 * Prints a class name
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
			System.out.print("\nClosing connection to " + DbConstants.DB_URL_LOCAL);
			this.conn.close();
			System.out.println("...SUCCESS!");
		}
		catch(SQLException e) {
			printMethod(new Throwable().getStackTrace()[0].getMethodName());
			e.printStackTrace();
		}
	}

	/**
	 * gets all customers from the database and returns an ArrayList of Customer objects
	 * sorted by last name, first name
	 * @return ArrayList of Customer objects
	 */
	public List<Customer> makeCustomerListFromDatabase() {
		List <Customer> customerList = new ArrayList<Customer>();
		try {
			//Prepare the SQL statement
			String sql = DbConstants.GET_CUSTOMERS;
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
				
				//Add the contact to the list of contacts
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
				System.out.println("...Success, found username and password for customerId = " + custId);
				return custId;
			}
			else {
				System.out.println("...Unable to find the user.");
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

	//Create customer
	//Retrieve customer
	//Update customer
	//Retrieve user name and password
	//Create account
	//Retrieve account balance
	//Retrieve account transactions
	//Update account balance
}
