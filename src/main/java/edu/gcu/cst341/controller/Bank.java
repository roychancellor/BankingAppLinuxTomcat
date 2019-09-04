package edu.gcu.cst341.controller;

import java.text.DecimalFormat;

public class Bank {
	//Format for dates and money outputs in all classes
	public static DecimalFormat money = new DecimalFormat();
	public static final String MONEY_FORMAT = "$#,##0.00;($#,##0.00)";

}
