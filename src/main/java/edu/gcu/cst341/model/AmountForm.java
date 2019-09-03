package edu.gcu.cst341.model;

import javax.validation.constraints.*;

//import org.hibernate.validator.constraints.NotBlank;

/**
 * A very simple Java bean for validating form input
 * for transaction amounts
 */
public class AmountForm {
//	@NotBlank
	@DecimalMin(value = "0.01", message = "Amount must be at least $0.01")
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

	@Override
	public String toString() {
		return "AmountForm [amount=" + amount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AmountForm other = (AmountForm) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		return true;
	}
}
