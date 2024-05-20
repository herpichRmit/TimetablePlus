package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.Main;
import model.Model;

/**
 * Controller for profileView
 */
public class ProfileController {

	@FXML
	private Label firstNameText;
	@FXML
	private Label lastNameText;
	@FXML
	private Label studentID;

	private Model model;


	public void initialize() throws IOException {
		model = Main.getModel();
		studentID.setText(model.getCurrentUser().getStudentID());
		firstNameText.setText(model.getCurrentUser().getFirstName());
		lastNameText.setText(model.getCurrentUser().getLastName());
	}


	@FXML
	private void switchEditProfile() throws IOException {
		Main.setRoot("/view/editProfileView");
	}

	@FXML
	private void switchHome() throws IOException {
		Main.setRoot("/view/homeView");
	}





}

