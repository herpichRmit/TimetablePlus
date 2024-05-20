package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Course;

public class EnrollmentDaoImpl implements EnrollmentDao {
	private final String TABLE_NAME = "enrolledCourses";

	public EnrollmentDaoImpl() {
	}
	
	/*
	 * setup, creates database if it is the first time running the program
	 */
	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR, "
					+ "courseName VARCHAR)";
			stmt.executeUpdate(sql);
		}
	}

	/*
	 * adds a new enrollment, taking user and course information
	 */
	@Override
	public void enrol(String username, String courseName) throws SQLException{
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, courseName);

			stmt.executeUpdate();
		}

	}

	/*
	 * removes an enrollment, taking user and course information
	 */
	@Override
	public void withdraw(String username, String courseName) throws SQLException{
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE username = ? AND courseName = ?;";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, courseName);
			stmt.executeUpdate();
		}
	}

	/*
	 * checks if enrollment exists
	 */
	@Override
	public boolean checkEnrolment(String username, String courseName) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND courseName = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, courseName);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return true;
				}
				return false;
			}
		}
	}



	/*
	 * returns all enrollments for a given user
	 */
	@Override
	public ArrayList<Course> getEnrolledCourses(String username) throws SQLException{
		ArrayList<Course> enrolledCourseArrayList = new ArrayList<>();
		String capacity;
		String sql = "SELECT courses.courseName, capacity, year, delivery, day, time, duration FROM "
				+ "courses, enrolledCourses WHERE courses.courseName = enrolledCourses.courseName AND username = '" + username + "'";

		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {

					Course course = new Course();
					course.setName(rs.getString("courseName"));
					capacity = rs.getString("capacity");
					if (capacity.equals("N/A")) {
						course.setCapacity(-1);
					} else {
						course.setCapacity(Integer.valueOf(capacity));
					}
					course.setYear(rs.getString("year"));
					course.setDelivery(rs.getString("delivery"));
					course.setDay(rs.getString("day"));
					course.setTime(rs.getString("time"));
					course.setDuration(rs.getDouble("duration"));
					enrolledCourseArrayList.add(course);
				}
				return enrolledCourseArrayList;
			}
		}
	}





}