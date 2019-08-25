package edu.gcu.cst341.spring;

/**
 * Java bean for a Student
 */
public class Student {
	private int id;
	private String lastname;
	private String firstname;
	private boolean enrolled;
	private String username;
	
	/**
	 * Constructor for the Java bean
	 * @param id the id of the object for overriding .equals
	 * @param lastname the student last name
	 * @param firstname the student first name
	 * @param enrolled true if student enrolled, false if not
	 * @param username the username to which the students is associated
	 */
	public Student(int id, String lastname, String firstname, boolean enrolled, String username) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.enrolled = enrolled;
		this.username = username;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", lastName=" + lastname + ", firstName=" + firstname
			+ ", enrolled=" + enrolled + ", username=" + username + "]";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public boolean isEnrolled() {
		return enrolled;
	}
	public void setEnrolled(boolean enrolled) {
		this.enrolled = enrolled;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
		Student other = (Student) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
