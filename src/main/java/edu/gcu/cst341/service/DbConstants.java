package edu.gcu.cst341.service;

/**
 * Class contains constants for use with SQL queries
 */
public class DbConstants {
	/**
	 * String constants used throughout the application for SQL commands
	 */
	public static final boolean LOCAL = false;
	public static final boolean PRODUCTION = true;
	public static final boolean VERBOSE = true;
	public static final boolean SILENT = false;
	public static final String DB_URL_LOCALDB = "jdbc:mysql://localhost:3306/";
	public static final String DB_URL_AWSDB = "jdbc:mysql://cst235-mysql.czz73jkkcnrj.us-east-2.rds.amazonaws.com:3306";
	public static final String USER_NAME_LOCAL = "root";  //I KNOW THIS IS NOT OK...FOR DEMO ONLY
	public static final String USER_NAME_AWS = "root";  //I KNOW THIS IS NOT OK...FOR DEMO ONLY
	public static final String PASSWORD_LOCAL = "root";  //I KNOW THIS IS NOT OK...FOR DEMO ONLY
	public static final String PASSWORD_AWS = "sqlsqlmysql";  //I KNOW THIS IS NOT OK...FOR DEMO ONLY
	public static final String DB_NAME = "bank";
	public static final String CUSTOMERS_TABLE = "customers";
	public static final String CREDENTIALS_TABLE = "credentials";
	public static final String CUSTOMER_ACCOUNTS_TABLE = "customer_accounts";
	public static final String CUSTOMER_TRANSACTIONS_TABLE = "customer_transactions";
	public static final String CUSTOMER_ID = "customerId";
	public static final String CUSTOMER_LAST_NAME = "lastName";
	public static final String CUSTOMER_FIRST_NAME = "firstName";
	public static final String CUSTOMER_EMAIL = "email";
	public static final String CUSTOMER_PHONE = "phone";
	public static final String CUSTOMER_DATE_OPENED = "dateOpened";
	public static final String CUSTOMER_USER_NAME = "userName";
	public static final String CUSTOMER_PASSWORD_SALT = "passwordSalt";
	public static final String CUSTOMER_PASSWORD_HASH = "passwordHash";
	public static final int CREATE = 1;
	public static final int UPDATE = 0;
	public static final int DELETE = -1;
	
	//SQL command to CREATE a customer in the customers table
	public static final String CREATE_CUSTOMER =
		"INSERT INTO "
		+ DB_NAME + "." + CUSTOMERS_TABLE
		+ "(" + CUSTOMER_LAST_NAME
		+ "," + CUSTOMER_FIRST_NAME
		+ "," + CUSTOMER_EMAIL
		+ "," + CUSTOMER_PHONE
		+ ")"
		+ " VALUES(?,?,?,?)";
	
	//SQL command to CREATE customer login credentials in the credentials table
	public static final String CREATE_CREDENTIALS =
		"INSERT INTO "
		+ DB_NAME + "." + CREDENTIALS_TABLE
		+ "(" + CUSTOMER_ID
		+ "," + CUSTOMER_USER_NAME
		+ "," + CUSTOMER_PASSWORD_SALT
		+ "," + CUSTOMER_PASSWORD_HASH
		+ ")"
		+ " VALUES(?,?,?,?)";
	
	//SQL command to CREATE customer accounts and balances in the customer_accounts table
	public static final String CREATE_CUSTOMER_ACCOUNTS =
		"INSERT INTO "
		+ DB_NAME + "." + CUSTOMER_ACCOUNTS_TABLE
		+ "(" + CUSTOMER_ID
		+ "," + "checkingNumber, checkingBalance"
		+ "," + "savingNumber, savingBalance"
		+ "," + "loanNumber, loanBalance"
		+ ")"
		+ " VALUES(?,?,?,?,?,?,?)";
	
	//SQL command to CREATE opening balance transactions in the customer_transactions table
	public static final String CREATE_CUSTOMER_TRANSACTIONS =
		"INSERT INTO "
		+ DB_NAME + "." + CUSTOMER_TRANSACTIONS_TABLE
		+ "(" + CUSTOMER_ID
		+ "," + "accountNumber"
		+ "," + "transAmount"
		+ "," + "transDescription"
		+ ")"
		+ " VALUES(?,?,?,?)";
	
	//SQL command to RETRIEVE the password salt and password hash by customerId
	public static final String RETRIEVE_CREDENTIALS_BY_CUSTOMER_ID =
		"SELECT " + CUSTOMER_PASSWORD_SALT + ", " + CUSTOMER_PASSWORD_HASH
		+ " FROM " + DB_NAME + "." + CREDENTIALS_TABLE
		+ " WHERE customerId=?";	
	
	//SQL command to RETRIEVE all customers unordered
	public static final String GET_CUSTOMERS_ORDERED =
		"SELECT * FROM " + DB_NAME + "." + CUSTOMERS_TABLE
		+ " ORDER BY " + CUSTOMER_LAST_NAME
		+ "," + CUSTOMER_FIRST_NAME;	
	
	//SQL command to RETRIEVE all customers and ORDER BY lastName, firstName
	public static final String GET_CUSTOMERS_UNORDERED =
		"SELECT * FROM " + DB_NAME + "." + CUSTOMERS_TABLE;
	
	//SQL command to RETRIEVE customerId from a username and password match in the credentials table
	public static final String GET_CUSTOMER_ID_FROM_CREDENTIALS =
		"SELECT " + CUSTOMER_ID
		+ " FROM " + DB_NAME + "." + CREDENTIALS_TABLE
		+ " WHERE " + CUSTOMER_USER_NAME + "=?"
		+ " AND "
		+ CUSTOMER_PASSWORD_SALT + "=?"
		+ " AND "
		+ CUSTOMER_PASSWORD_HASH + "=?";
	
	//SQL command to RETRIEVE a customer by username (used to check if user already exists)
	public static final String GET_CUSTOMER_BY_USERNAME = 
		"SELECT " + CUSTOMER_ID
		+ " FROM " + DB_NAME + "." + CREDENTIALS_TABLE
		+ " WHERE " + CUSTOMER_USER_NAME + "=?";
	
	//SQL command to RETRIEVE all customer information from customers table by customerId
	public static final String GET_CUSTOMER_BY_ID =
		"SELECT "
		+ DB_NAME + "." + CUSTOMERS_TABLE + ".customerId,"
		+ DB_NAME + "." + CUSTOMERS_TABLE + ".lastName,"
		+ DB_NAME + "." + CUSTOMERS_TABLE + ".firstName,"
		+ DB_NAME + "." + CUSTOMERS_TABLE + ".email,"
		+ DB_NAME + "." + CUSTOMERS_TABLE + ".phone,"
		+ DB_NAME + "." + CREDENTIALS_TABLE + ".userName,"
		+ DB_NAME + "." + CREDENTIALS_TABLE + ".passwordHash"
		+ " FROM "
		+ DB_NAME + "." + CUSTOMERS_TABLE + "," + DB_NAME + "." + CREDENTIALS_TABLE
		+ " WHERE "
		+ DB_NAME + "." + CREDENTIALS_TABLE + "." + "customerId=?"
		+ " AND "
		+ DB_NAME + "." + CUSTOMERS_TABLE + "." + "customerId=?";
	
	//SQL command to RETRIEVE account numbers and balances by customerId
	public static final String GET_CUSTOMER_ACCOUNTS_BY_ID =
		"SELECT checkingNumber,checkingBalance,"
		+ "savingNumber,savingBalance,"
		+ "loanNumber,loanBalance"
		+ " FROM " + DB_NAME + "." + CUSTOMER_ACCOUNTS_TABLE 
		+ " WHERE customerId=?";
	
	//SQL command to RETRIEVE all transactions by customerId
	public static final String GET_CUSTOMER_TRANSACTIONS_BY_ID =
		"SELECT accountNumber, transTimestamp, transAmount, transDescription"
		+ " FROM " + DB_NAME + "." + CUSTOMER_TRANSACTIONS_TABLE
		+ " WHERE customerId=?"
		+ " ORDER BY accountNumber, transTimestamp";
	
	//SQL command to UPDATE customer names by id
	public static final String UPDATE_CUSTOMER_NAMES_BY_ID =
		"UPDATE " + DB_NAME + "." + CUSTOMERS_TABLE + " "
		+ "SET " + CUSTOMER_LAST_NAME + "=?," + CUSTOMER_FIRST_NAME + "=? "
		+ "WHERE " + CUSTOMER_ID + "=?";
	
	//SQL command to UPDATE customer contact information by id
	public static final String UPDATE_CUSTOMER_CONTACT_BY_ID =
		"UPDATE " + DB_NAME + "." + CUSTOMERS_TABLE + " "
		+ "SET " + CUSTOMER_EMAIL + "=?," + CUSTOMER_PHONE + "=? "
		+ "WHERE " + CUSTOMER_ID + "=?";
	
	//SQL command to UPDATE customer login credentials by customer id
	public static final String UPDATE_CREDENTIALS_BY_ID =
		"UPDATE " + DB_NAME + "." + CREDENTIALS_TABLE
		+ " SET " + CUSTOMER_USER_NAME + "=?," + CUSTOMER_PASSWORD_SALT + "=?," + CUSTOMER_PASSWORD_HASH + "=?"
		+ " WHERE " + CUSTOMER_ID + "=?";
	
	//SQL command to UPDATE the customer_transactions table with a single transaction
	public static final String UPDATE_TRANSACTION = 
		"INSERT INTO "
		+ DB_NAME + "." + CUSTOMER_TRANSACTIONS_TABLE
		+ " (customerId, accountNumber, transAmount, transDescription)"
		+ " VALUES"
		+ " (?,?,?,?)";
	
	//SQL command to DELETE a customer by id
	public static final String DELETE_CUSTOMER_BY_ID =
		"DELETE FROM " + DB_NAME + "." + CUSTOMERS_TABLE + " "
		+ "WHERE " + CUSTOMER_ID + "=?";
	
	//SQL command to DELETE customer credentials by id
	public static final String DELETE_CUSTOMER_CREDENTIALS_BY_ID =
		"DELETE FROM " + DB_NAME + "." + CREDENTIALS_TABLE + " "
		+ "WHERE " + CUSTOMER_ID + "=?";

	//SQL command to DELETE customer accounts by id
	public static final String DELETE_CUSTOMER_ACCOUNTS_BY_ID =
		"DELETE FROM " + DB_NAME + "." + CUSTOMER_ACCOUNTS_TABLE + " "
		+ "WHERE " + CUSTOMER_ID + "=?";

	//SQL command to DELETE customer transactions by id
	public static final String DELETE_CUSTOMER_TRANSACTIONS_BY_ID =
		"DELETE FROM " + DB_NAME + "." + CUSTOMER_TRANSACTIONS_TABLE + " "
		+ "WHERE " + CUSTOMER_ID + "=?";
}
