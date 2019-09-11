package edu.gcu.cst341.viewforms;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Simple bean for validating a password-only field
 */
public class PasswordForm {
	@NotNull
	@NotBlank(message="Password may not be blank")
	private String password;
	
	/**
	 * No-argument constructor
	 */
	public PasswordForm() { }
	
	/**
	 * @param password the password field
	 */
	public PasswordForm(String password) {
		super();
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginForm [password=" + password + "]";
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		PasswordForm other = (PasswordForm) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
