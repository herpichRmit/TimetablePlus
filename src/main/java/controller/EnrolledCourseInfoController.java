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
 * Controls enrolledCourseInfoView, which is when the user is viewing a courses they are enrolled in
 */
public class EnrolledCourseInfoController {
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
	 * sets up course information
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
	}

	/*
	 * switch view
	 */
	@FXML
	private void switchViewProfile() throws IOException {
		Main.changeSmallSize();
		Main.setRoot("/view/profileView");
	}

	/*
	 * switch view
	 */
	@FXML
	private void switchBack() throws IOException {
		Main.setRoot("/view/enrolledCoursesView");
	}

	/*
	 * switch view
	 */
	@FXML
	private void withdraw() throws IOException, SQLException {
		String username = model.getCurrentUser().getUsername();
		String courseName = model.getCurrentCourse().getName();
		String delivery = model.getCurrentCourse().getDelivery();

		model.getEnrolledCourseDao().withdraw(username, courseName);
		if (!delivery.equals("Online")) {
			model.getCourseDao().increaseCapacity(courseName);
		}
		Main.setRoot("/view/enrolledCoursesView");

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
