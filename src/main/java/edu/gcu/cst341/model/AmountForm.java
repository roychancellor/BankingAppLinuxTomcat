package edu.gcu.cst341.model;

import javax.validation.constraints.*;

public class AmountForm {
	@NotNull
	@DecimalMin("0.01")
	private double amount;

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
