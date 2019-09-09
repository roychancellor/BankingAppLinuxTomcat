package edu.gcu.cst341.model;

import org.hibernate.validator.constraints.NotBlank;
/**
 * A very simple Java bean for validating form input
 * for transaction amounts
 */
public class AmountForm {
	private int id;
	@NotBlank(message = "Amount may not be blank")
	private String amount;

	/**
	 * No-argument constructor
	 */
	public AmountForm() { }
	
	/**
	 * @param id
	 * @param amount
	 */
	public AmountForm(int id, String amount) {
		super();
		this.id = id;
		this.amount = amount;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	
	@Override
	public String toString() {
		return "AmountForm2 [id=" + id + ", amount=" + amount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		return true;
	}

	
}
