package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Course;

/**
 * Data access object for enrolled course table
 */
public interface EnrollmentDao {
	void setup() throws SQLException;
	void enrol(String username, String courseName) throws SQLException;
	boolean checkEnrolment(String username, String courseName) throws SQLException;
	void withdraw(String username, String courseName) throws SQLException;
	ArrayList<Course> getEnrolledCourses(String username) throws SQLException;
}
