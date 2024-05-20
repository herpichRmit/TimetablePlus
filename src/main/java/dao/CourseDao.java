package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Course;

/**
 * Data access object for course table
 */
public interface CourseDao {
	void setup() throws SQLException;
	Course getCourse(String courseName) throws SQLException;
	ArrayList<Course> getAvailableCourses(String username) throws SQLException;
	ArrayList<Course> getAllCourses() throws SQLException;
	ArrayList<Course> getCourses(String keyword) throws SQLException;
	void increaseCapacity(String courseName) throws SQLException;
	void decreaseCapacity(String courseName) throws SQLException;
}
