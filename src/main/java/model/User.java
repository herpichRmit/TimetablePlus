package model;

/**
 * User class, contains user data
 */
public class User {
	private String username;
	private String password;
	private String studentID;
	private String firstName;
	private String lastName;

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getStudentID() {
		return this.studentID;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}
}
