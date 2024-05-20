package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import model.Course;
import model.Model;

/**
 * Controls enrolledCourseTimetableView, which is when the user is viewing timetable representation of enrolled courses
 */
public class EnrolledCourseTimetableController {
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
	private GridPane timetableContainer;
	@FXML
	private TextField courseQuery;

	/*
	 * sets up profile icon and enrolled course information
	 */
	public void initialize() throws IOException, SQLException {
		model = Main.getModel();
		studentID.setText(model.getCurrentUser().getStudentID());
		firstNameText.setText(model.getCurrentUser().getFirstName());
		lastNameText.setText(model.getCurrentUser().getLastName());

		// take list of courses, get time, translate to grid
		ArrayList<Course> courses = model.getEnrolledCourses(model.getCurrentUser().getUsername());

		for (int i=0; i<courses.size(); i++) {
			String[] colours = {"#CC9A81","#DEB99E","#FEDBB2","#FED298", "#FEC591", "#DBD9F2", "#EBD2D8", "#EFDAD4"};
			String[] times = {"7:30","8:00","8:30","9:00","9:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00"};
			String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
			String targetTime;
			String targetday;
			int hourStartPos = 0;
			int dayPos = 0;
			int duration = 0;
			String colour = "black";
			Course course;

			// create a vbox with course information and unique colour
			course = courses.get(i);
			colour = colours[i];
			VBox courseBox = newBox(course, colour);

			// determine location on timetable grid from properties
			targetday = course.getDay();
			dayPos = indexOf(targetday, days);
			targetTime = course.getTime();
			hourStartPos = indexOf(targetTime, times);
			duration = (int) (course.getDuration() * 2);

			timetableContainer.add(courseBox, dayPos, hourStartPos, 1, duration);
		}

	}

	// implemented indexOf function in java to assist with adding vboxs to grid
	private int indexOf(String keyword, String[] array) {
		for (int i=0; i<array.length; i++) {
			if (array[i].equals(keyword)) {
				return i;
			}
		}
		return -1;
	}


	/*
	 * used within initialize() to create each block entry within the grid
	 */
	private VBox newBox(Course course, String colour) {
		VBox box = new VBox();
		box.setPrefWidth(200);
		box.setPrefHeight(800);
		box.setStyle("-fx-background-color: "+colour);
		
		// event handler so that when a course is clicked, the view changes appropriately
		box.setOnMouseClicked(event -> {
			try {
				model.setCurrentCourse(course);
				this.switchCourse();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		box.setOnMouseEntered(event -> {
			box.setStyle("-fx-background-color: #e8e8e8");
		});
		box.setOnMouseExited(event ->{
			box.setStyle("-fx-background-color: "+colour);
		});

		box.getChildren().addAll(
			newRowInfo(course.getName(), 12.0),
			newRowInfo(course.getTime(), 11.0),
			newRowInfo(Double.toString(course.getDuration()) + " hrs", 11.0)
		);
		return box;
	}

	/*
	 * used within newBox() to create labels
	 */
	private HBox newRowInfo(String text, double fontSize) {
		HBox dataLabel = new HBox();
		dataLabel.setAlignment(Pos.BOTTOM_LEFT);
		Label data = new Label(text);
		data.setFont(new Font("Arial", fontSize));
		dataLabel.setStyle("-fx-padding: 0 5 0 5;");
		dataLabel.getChildren().add(data);
		return dataLabel;
	}

	/*
	 * change view
	 */
	@FXML
	private void switchViewProfile() throws IOException {
		Main.changeSmallSize();
		Main.setRoot("/view/profileView");
	}

	/*
	 * change view
	 */
	@FXML
	private void switchBack() throws IOException {
		Main.changeSmallSize();
		Main.setRoot("/view/homeView");
	}

	/*
	 * change view
	 */
	@FXML
	private void switchCourse() throws IOException {
		Main.setRoot("/view/enrolledCourseInfoView");
	}

	/*
	 * change view
	 */
	@FXML
	private void switchEnrolledCourse() throws IOException {
		Main.setRoot("/view/enrolledCoursesView");
	}

	/*
	 * change view
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
