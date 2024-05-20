package dao;

import java.sql.SQLException;

import model.User;

/**
 * Data access object for user table
 */
public interface UserDao {
	void setup() throws SQLException;
	User getUser(String username, String password) throws SQLException;
	User createUser(String username, String password) throws SQLException;
	Boolean addDetails(String username, String studentID, String firstName, String lastName) throws SQLException;
	Boolean checkUserExists(String username) throws SQLException;
	Boolean checkStudentIDExists(String username) throws SQLException;
}
