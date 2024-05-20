package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import model.Course;
import model.Model;

/**
 * Controls courseView, which is when the user is viewing all courses
 */
public class CourseController {
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
	private CheckBox checkbox;

	@FXML
	private VBox courseContainer;
	@FXML
	private TextField courseQuery;


	/*
	 * sets up profile icon and course information for courses that are not full or already enrolled in
	 */
	public void initialize() throws IOException, SQLException {
		model = Main.getModel();
		studentID.setText(model.getCurrentUser().getStudentID());
		firstNameText.setText(model.getCurrentUser().getFirstName());
		lastNameText.setText(model.getCurrentUser().getLastName());

		ArrayList<Course> courses = model.getAvailableCourses(model.getCurrentUser().getUsername());

		for (Course element : courses) {
			HBox row = newRow(element);
			courseContainer.getChildren().add(row);
		}

	}

	/*
	 * used within initialize(), updateShowFull(), updateCourseContainer() to create each row entry within the view
	 */
	private HBox newRow(Course course) throws SQLException {
		String status;
		HBox row = new HBox();

		if (course.getCapacity() != 0) {
			status = "Available";
		} else {
			status = "Full";
		}

		if (model.getEnrolledCourseDao().checkEnrolment(model.getCurrentUser().getUsername(), course.getName())) {
			status = "Enrolled";
		}

		row.setMaxWidth(850);
		
		// event handler so that when a course is clicked, the view changes appropriately
		row.setOnMouseClicked(event -> {
			try {
				model.setCurrentCourse(course);
				this.switchCourse();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		row.setOnMouseEntered(event -> {
			row.setStyle("-fx-border-color: #e1e1e1");
		});
		row.setOnMouseExited(event ->{
			row.setStyle("-fx-border-color: none");
		});

		row.getChildren().addAll(
			newRowInfo(course.getName(), 250.0),
			newRowInfo(course.getYear(), 100.0),
			newRowInfo(course.getDelivery(), 100.0),
			newRowInfo(course.getDay(), 100.0),
			newRowInfo(course.getTime(), 100.0),
			newRowInfo(Double.toString(course.getDuration()), 100.0),
			newRowInfo(status, 100.0)
		);
		return row;
	}

	/*
	 * used within newRow() to create labels
	 */
	private HBox newRowInfo(String text, double width) {
		HBox dataLabel = new HBox();
		dataLabel.setAlignment(Pos.BOTTOM_LEFT);
		dataLabel.setPrefWidth(width);
		Label data = new Label(text);
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
	private void switchHome() throws IOException {
		Main.changeSmallSize();
		Main.setRoot("/view/homeView");
	}

	/*
	 * change view
	 */
	@FXML
	private void switchCourse() throws IOException {
		Main.setRoot("/view/CourseInfoView");
	}


	/*
	 * updates courses entries in view, so that all courses are shown including unavailable ones
	 */
	@FXML
	private void updateShowFull() throws IOException, SQLException {
		courseContainer.getChildren().clear();
		ArrayList<Course> courses;

		if (checkbox.isSelected()) {
			courses = model.getAllCourses();
		} else {
			courses = model.getAvailableCourses(model.getCurrentUser().getUsername());
		}

		for (Course element : courses) {
			HBox row = newRow(element);
			courseContainer.getChildren().add(row);
		}
	}

	/*
	 * updates courses entries in view, so that all courses are shown that match given keyword
	 */
	@FXML
	private void updateCourseContainer() throws IOException, SQLException {
		courseContainer.getChildren().clear();
		String keyword = courseQuery.getText();
		ArrayList<Course> courses;

		if (!keyword.equals("") ) {
			courses = model.getCourses(keyword);
		} else {
			courses = model.getAvailableCourses(keyword);
			checkbox.setSelected(false);
		}

		for (Course element : courses) {
			HBox row = newRow(element);
			courseContainer.getChildren().add(row);
		}
		courseQuery.clear();
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
