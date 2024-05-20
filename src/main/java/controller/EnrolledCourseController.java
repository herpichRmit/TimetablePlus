package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import model.Course;
import model.Model;
import model.PrintFileManager;

/**
 * Controls enrolledCourseView, which is when the user is viewing all enrolled courses
 */
public class EnrolledCourseController {
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
	private VBox courseContainer;
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

		ArrayList<Course> courses = model.getEnrolledCourses(model.getCurrentUser().getUsername());

		for (Course element : courses) {
			HBox row = newRow(element);
			courseContainer.getChildren().add(row);
		}

	}

	/*
	 * used within initialize() to create each row entry within the view
	 */
	private HBox newRow(Course course) {
		HBox row = new HBox();
		row.setMaxWidth(700);
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
			newRowInfo(course.getName(), 200.0),
			newRowInfo(course.getYear(), 100.0),
			newRowInfo(course.getDelivery(), 100.0),
			newRowInfo(course.getDay(), 100.0),
			newRowInfo(course.getTime(), 100.0),
			newRowInfo(Double.toString(course.getDuration()), 100.0)
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
	 * calls PrintFileManager.print() to create a file with all the users enrollment information
	 */
	@FXML
	private void exportPDF() throws IOException, SQLException {
		ArrayList<Course> courses = model.getAllCourses();
		ArrayList<String> output = new ArrayList<>();
		Course course;

		for (int i=0; i<courses.size(); i++) {
			course = courses.get(i);
			String entry = course.getName() + " " + course.getYear() + " " + course.getDelivery() + " "
					+ course.getDay() + " " + course.getTime() + " " + course.getDuration() + "\n";
			output.add(entry);
			PrintFileManager.printDownloads(output);
			PrintFileManager.printLocalFolder(output);
		}
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
	private void switchEnrolledCourseTimetable() throws IOException {
		Main.setRoot("/view/enrolledCoursesTimetableView");
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
