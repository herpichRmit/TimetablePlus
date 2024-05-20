package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Course;

public class CourseDaoImpl implements CourseDao {
	private final String TABLE_NAME = "courses";

	public CourseDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		boolean insertValues = true;
		/*
		 * Creating new database
		 */
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (courseName VARCHAR NOT NULL,"
					+ "capacity INTEGER," + "year VARCHAR, "
					+ "delivery VARCHAR," + "day VARCHAR, "
					+ "time VARCHAR," + "duration DOUBLE,"
					+ "PRIMARY KEY (courseName))";
			stmt.executeUpdate(sql);
		}

		/*
		 * Checking if database is being set up for the first time
		 */
		String sql = "SELECT * from " + TABLE_NAME;
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					insertValues = false;
				}
			}

		}
		/*
		 * Initialising database from csv file using buffered reader
		 */
		if (insertValues) {
			sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?, ?)";
			try (Connection connection = Database.getConnection();
					PreparedStatement stmt = connection.prepareStatement(sql);) {
				String line = "";
				BufferedReader reader = new BufferedReader(new FileReader("course.csv"));
				reader.readLine(); // skip headers
				while ((line = reader.readLine()) != null ) {
					String[] courses = line.split(",");
					stmt.setString(1, courses[0]);
					stmt.setString(2, courses[1]);
					stmt.setString(3, courses[2]);
					stmt.setString(4, courses[3]);
					stmt.setString(5, courses[4]);
					stmt.setString(6, courses[5]);
					stmt.setString(7, courses[6]);
					stmt.executeUpdate();
				}
				reader.close();
			} catch (IOException e) {
				System.out.print("fail");
			}
		}

	}

	/*
	 * get a specific course 
	 */
	@Override
	public Course getCourse(String courseName) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE courseName = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, courseName);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Course course = new Course();
					course.setName(rs.getString("courseName"));
					course.setCapacity(rs.getInt("capacity"));
					course.setYear(rs.getString("year"));
					course.setDelivery(rs.getString("delivery"));
					course.setDay(rs.getString("day"));
					course.setTime(rs.getString("time"));
					course.setDuration(rs.getDouble("duration"));
					return course;
				}
				return null;
			}
		}
	}
	
	/*
	 * get all courses that contain a keyword, used for search
	 */
	@Override
	public ArrayList<Course> getCourses(String keyword) throws SQLException{
		ArrayList<Course> courseArrayList = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE courseName LIKE \"%" + keyword + "%\"";
		String capacity;
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
					courseArrayList.add(course);
				}
				return courseArrayList;
			}
		}
	}

	/*
	 * get all 'available' courses, i.e. courses that are not full or the user is already enrolled in
	 */
	@Override
	public ArrayList<Course> getAvailableCourses(String username) throws SQLException{
		ArrayList<Course> courseArrayList = new ArrayList<>();
		String sql = "SELECT courses.courseName, capacity, year, delivery, day, time, duration FROM "
				+ " courses LEFT JOIN (SELECT * FROM enrolledCourses WHERE username = '" + username +"') AS a "
				+ " ON a.courseName = courses.courseName WHERE a.courseName IS NULL AND capacity != 0";
		String capacity;
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
					courseArrayList.add(course);
				}
				return courseArrayList;
			}
		}
	}

	/*
	 * get all courses
	 */
	@Override
	public ArrayList<Course> getAllCourses() throws SQLException{
		ArrayList<Course> courseArrayList = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE_NAME;
		String capacity;
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
					courseArrayList.add(course);
				}
				return courseArrayList;
			}
		}
	}


	/*
	 * edit database, increment course capacity by 1
	 */
	@Override
	public void increaseCapacity(String courseName) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET capacity = capacity + 1 WHERE courseName = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, courseName);
			stmt.executeUpdate();
		}
	}

	/*
	 * edit database, decrement course capacity by 1
	 */
	@Override
	public void decreaseCapacity(String courseName) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET capacity = capacity - 1 WHERE courseName = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, courseName);
			stmt.executeUpdate();
		}
	}

}
