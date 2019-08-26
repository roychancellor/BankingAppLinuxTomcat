package edu.gcu.cst341.database;

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
	public static final String DB_URL_LOCALDB = "jdbc:mysql://localhost:3306";
	public static final String DB_URL_AWSDB = "jdbc:mysql://cst235-mysql.czz73jkkcnrj.us-east-2.rds.amazonaws.com:3306";
	public static final String USER_NAME_LOCAL = "root";  //I KNOW THIS IS NOT OK...FOR DEMO ONLY
	public static final String USER_NAME_AWS = "root";  //I KNOW THIS IS NOT OK...FOR DEMO ONLY
	public static final String PASSWORD_LOCAL = "root";  //I KNOW THIS IS NOT OK...FOR DEMO ONLY
	public static final String PASSWORD_AWS = "sqlsqlmysql";  //I KNOW THIS IS NOT OK...FOR DEMO ONLY
	public static final String DB_NAME = "bank";
	public static final String CUSTOMER_TABLE = "customers";
	public static final String CREDENTIALS_TABLE = "credentials";
	public static final String CUSTOMER_ID = "customerId";
	public static final String CUSTOMER_LAST_NAME = "lastName";
	public static final String CUSTOMER_FIRST_NAME = "firstName";
	public static final String CUSTOMER_DATE_OPENED = "dateOpened";
	public static final String CUSTOMER_USER_NAME = "userName";
	public static final String CUSTOMER_PASSWORD_SALT = "passwordSalt";
	public static final String CUSTOMER_PASSWORD_HASH = "passwordHash";
	public static final int CREATE = 1;
	public static final int UPDATE = 0;
	public static final int DELETE = -1;
	
	//SQL command to CREATE a customer in the customers table
	public static final String CREATE_CUSTOMER = "INSERT INTO "
		+ DB_NAME + "." + CUSTOMER_TABLE
		+ "(" + CUSTOMER_LAST_NAME + "," + CUSTOMER_FIRST_NAME + ")"
		+ " VALUES(?,?)";
	
	//SQL command to CREATE customer login credentials in the credentials table
	public static final String CREATE_CREDENTIALS = "INSERT INTO "
		+ DB_NAME + "." + CREDENTIALS_TABLE
		+ "(" + CUSTOMER_ID + "," + CUSTOMER_USER_NAME + "," + CUSTOMER_PASSWORD_SALT + "," + CUSTOMER_PASSWORD_HASH + ")"
		+ " VALUES(?,?,?,?)";
	
	//SQL command to RETRIEVE all customers unordered
	public static final String GET_CUSTOMERS_ORDERED = "SELECT * FROM " + DB_NAME + "." + CUSTOMER_TABLE + " "
		+ "ORDER BY " + CUSTOMER_LAST_NAME + "," + CUSTOMER_FIRST_NAME;	
	
	//SQL command to RETRIEVE all customers and ORDER BY lastName, firstName
	public static final String GET_CUSTOMERS_UNORDERED = "SELECT * FROM " + DB_NAME + "." + CUSTOMER_TABLE;
	
	//SQL command to RETRIEVE customerId from a username and password match in the credentials table
	public static final String GET_CUSTOMER_ID_FROM_CREDENTIALS = "SELECT " + CUSTOMER_ID
		+ " FROM " + DB_NAME + "." + CREDENTIALS_TABLE
		+ " WHERE " + CUSTOMER_USER_NAME + "=? AND " + CUSTOMER_PASSWORD_SALT + "=? AND " + CUSTOMER_PASSWORD_HASH + "=?";
	
	//SQL command to UPDATE a customer by id
	public static final String UPDATE_CUSTOMER_BY_ID = "UPDATE " + DB_NAME + "." + CUSTOMER_TABLE + " "
		+ "SET " + CUSTOMER_LAST_NAME + "=?," + CUSTOMER_FIRST_NAME + "=? "
		+ "WHERE " + CUSTOMER_ID + "=?";
	
	//SQL command to UPDATE a credentials by customer id
	public static final String UPDATE_CREDENTIALS_BY_ID = "UPDATE " + DB_NAME + "." + CREDENTIALS_TABLE + " "
		+ "SET " + CUSTOMER_USER_NAME + "=?," + CUSTOMER_PASSWORD_SALT + "=? " + CUSTOMER_PASSWORD_HASH + "=? "
		+ "WHERE " + CUSTOMER_ID + "=?";
	
	//SQL command to DELETE a customer by id
	public static final String DELETE_CUSTOMER_BY_ID = "DELETE FROM " + DB_NAME + "." + CUSTOMER_TABLE + " "
		+ "WHERE " + CUSTOMER_ID + "=?";
	
	//SQL command to DELETE a customer credentials by id
	public static final String DELETE_CREDENTIALS_BY_ID = "DELETE FROM " + DB_NAME + "." + CREDENTIALS_TABLE + " "
		+ "WHERE " + CUSTOMER_ID + "=?";
}
