package model;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.CourseDao;
import dao.CourseDaoImpl;
import dao.EnrollmentDao;
import dao.EnrollmentDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;

/**
 * Model class, contains back-end functionality 
 */
public class Model {
	private UserDao userDao;
	private CourseDao courseDao;
	private EnrollmentDao enrolledCourseDao;
	private User currentUser;
	private Course currentCourse;

	public Model() {
		userDao = new UserDaoImpl();
		courseDao = new CourseDaoImpl();
		enrolledCourseDao = new EnrollmentDaoImpl();
	}

	public void setup() throws SQLException  {
		userDao.setup();
		courseDao.setup();
		enrolledCourseDao.setup();

	}

	public UserDao getUserDao() {
		return userDao;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public EnrollmentDao getEnrolledCourseDao() {
		return enrolledCourseDao;
	}

	public ArrayList<Course> getAvailableCourses(String username) throws SQLException {
		return courseDao.getAvailableCourses(username);
	}

	public ArrayList<Course> getAllCourses() throws SQLException {
		return courseDao.getAllCourses();
	}

	public ArrayList<Course> getCourses(String keyword) throws SQLException {
		return courseDao.getCourses(keyword);
	}

	public ArrayList<Course> getEnrolledCourses(String username) throws SQLException {
		return enrolledCourseDao.getEnrolledCourses(username);
	}

	public User getCurrentUser() {
		return this.currentUser;
	}

	public void setCurrentUser(User user) {
		currentUser = user;
	}

	public Course getCurrentCourse() {
		return currentCourse;
	}

	public void setCurrentCourse(Course currentCourse) {
		this.currentCourse = currentCourse;
	}

}
