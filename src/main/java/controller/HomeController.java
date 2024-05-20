package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.Main;
import model.Model;

public class HomeController {
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


	public void initialize() throws IOException {
		model = Main.getModel();
		studentID.setText(model.getCurrentUser().getStudentID());
		firstNameText.setText(model.getCurrentUser().getFirstName());
		lastNameText.setText(model.getCurrentUser().getLastName());
	}


	@FXML
	private void switchViewProfile() throws IOException {
		Main.setRoot("/view/profileView");
	}

	@FXML
	private void switchViewCourses() throws IOException {
		Main.changeFullSize();
		Main.setRoot("/view/courseView");
	}

	@FXML
	private void switchViewEnrolledCourses() throws IOException {
		Main.changeFullSize();
		Main.setRoot("/view/enrolledCoursesView");
	}

	@FXML
	private void exit() throws IOException {
		Platform.exit();
	}

	@FXML
	private void showPreview() throws IOException {
		emptyHeaderBlock.setVisible(false);
		emptyHeaderBlock.setManaged(false);

		headerBlockDetails.setVisible(true);
		headerBlockDetails.setManaged(true);
	}


	@FXML
	private void closePreview() throws IOException {
		emptyHeaderBlock.setVisible(true);
		emptyHeaderBlock.setManaged(true);

		headerBlockDetails.setVisible(false);
		headerBlockDetails.setManaged(false);
	}





}
