package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "users";

	public UserDaoImpl() {
	}
	
	/*
	 * setup, creates user table if it does not exist
	 */
	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(10) NOT NULL,"
					+ "password VARCHAR(8) NOT NULL," + "studentID VARCHAR, "
					+ "firstName VARCHAR," + "lastName VARCHAR, "
					+ "PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
		}
	}

	/*
	 * gets relevant user based on username and password
	 */
	@Override
	public User getUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setStudentID(rs.getString("studentID"));
					user.setFirstName(rs.getString("firstName"));
					user.setLastName(rs.getString("lastName"));
					return user;
				}
				return null;
			}
		}
	}

	/*
	 * adds a user to database
	 */
	@Override
	public User createUser(String username, String password) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, null, null, null)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);

			stmt.executeUpdate();
			return new User(username, password);
		}
	}

	/*
	 * adds details to users profile, used during registration
	 */
	@Override
	public Boolean addDetails(String username, String studentID, String firstName, String lastName) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET studentID = ?, firstName = ? , lastName = ? WHERE username = ? ;";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, studentID);
			stmt.setString(2, firstName);
			stmt.setString(3, lastName);
			stmt.setString(4, username);

			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * check if username exists in database, used in registration to verify if a username is available
	 */
	@Override
	public Boolean checkUserExists(String username) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return true;
				}
				return false;
			}
		}
	}

	/*
	 * check if studentID exists in database, used in registration to verify if a studentID is available
	 */
	@Override
	public Boolean checkStudentIDExists(String studentID) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE studentID = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, studentID);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return true;
				}
				return false;
			}
		}
	}
}
