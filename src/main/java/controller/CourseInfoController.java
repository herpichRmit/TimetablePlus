package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.Main;
import model.Course;
import model.Model;

/**
 * Controls courseInfoView, which is when the uses clicks into a subject
 */
public class CourseInfoController {
	private Model model;

	@FXML
	private Label firstNameText;
	@FXML
	private Label lastNameText;
	@FXML
	private Label studentID;
	@FXML
	private ImageView userIcon;
	@FXML
	private VBox headerBlockDetails;
	@FXML
	private VBox emptyHeaderBlock;

	@FXML
	private Label nameLabel;
	@FXML
	private Label yearLabel;
	@FXML
	private Label deliveryLabel;
	@FXML
	private Label dayLabel;
	@FXML
	private Label timeLabel;
	@FXML
	private Label durationLabel;
	@FXML
	private Label statusLabel;
	@FXML
	private Label errorLabel;


	/*
	 * sets up profile icon, and specific course information
	 */
	public void initialize() throws IOException, SQLException {
		model = Main.getModel();
		studentID.setText(model.getCurrentUser().getStudentID());
		firstNameText.setText(model.getCurrentUser().getFirstName());
		lastNameText.setText(model.getCurrentUser().getLastName());

		Course course = model.getCurrentCourse();

		nameLabel.setText(course.getName());
		yearLabel.setText(course.getYear());
		deliveryLabel.setText(course.getDelivery());
		dayLabel.setText(course.getDay());
		timeLabel.setText(course.getTime());
		durationLabel.setText(Double.toString(course.getDuration()) + " hours");

		if (course.getCapacity() != 0) {
			statusLabel.setText("Available");
		} else {
			statusLabel.setText("Full");
		}

		if (model.getEnrolledCourseDao().checkEnrolment(model.getCurrentUser().getUsername(), course.getName())) {
			statusLabel.setText("Enrolled");
		}
	}


	/*
	 * change view when edit profile button is fired
	 */
	@FXML
	private void switchViewProfile() throws IOException {
		Main.changeSmallSize();
		Main.setRoot("/view/profileView");
	}

	/*
	 * change view when back button is fired
	 */
	@FXML
	private void switchBack() throws IOException {
		Main.setRoot("/view/courseView");
	}

	/*
	 * validate and enrol user when enrol button is fired
	 */
	@FXML
	private void enrol() throws IOException, SQLException {
		String username = model.getCurrentUser().getUsername();
		String courseName = model.getCurrentCourse().getName();
		String delivery = model.getCurrentCourse().getDelivery();
		int capacity = model.getCourseDao().getCourse(courseName).getCapacity();

		//check user is enrolled
		if (!model.getEnrolledCourseDao().checkEnrolment(username, courseName)) {
			//check course is not full
			if (capacity != 0) {
				//enrol
				model.getEnrolledCourseDao().enrol(username, courseName);
				//decrement capacity if it is a face-to-face class
				if (!delivery.equals("Online")) {
					model.getCourseDao().decreaseCapacity(courseName);
				}

			} else {
				errorLabel.setText("Course is full");
				errorLabel.setVisible(true);

			}
			Main.setRoot("/view/courseView");

		} else {
			errorLabel.setText("Cannot enrol in this subject again");
			errorLabel.setVisible(true);

		}

	}

	/*
	 * for profile tab
	 */
	@FXML
	private void showPreview() throws IOException {
		emptyHeaderBlock.setVisible(false);
		emptyHeaderBlock.setManaged(false);

		headerBlockDetails.setVisible(true);
		headerBlockDetails.setManaged(true);
	}

	/*
	 * for profile tab
	 */
	@FXML
	private void closePreview() throws IOException {
		emptyHeaderBlock.setVisible(true);
		emptyHeaderBlock.setManaged(true);

		headerBlockDetails.setVisible(false);
		headerBlockDetails.setManaged(false);
	}





}
