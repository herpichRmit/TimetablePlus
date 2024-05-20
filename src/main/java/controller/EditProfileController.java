package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import model.Model;
import model.User;

/**
 * Controls editProfileView, validates user input and updates profile
 */
public class EditProfileController {
	private Model model;

	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField passwordAgainField;
	@FXML
	private Label message;

	/*
	 * Check user input, if appropriate update user information in database and return to home view
	 */
	@FXML
	private void switchViewProfile() throws IOException {
		Boolean success;
		User user;

		try {
			model = Main.getModel();
			user = model.getCurrentUser();

			// check firstName field isn't empty 
			if (!firstNameField.getText().isEmpty()) {
				user.setFirstName(firstNameField.getText());
			}
			
			// check lastName field isn't empty 
			if (!lastNameField.getText().isEmpty()) {
				user.setLastName(lastNameField.getText());
			}
			
			// check either password field has input 
			if (!passwordField.getText().isEmpty() || !passwordAgainField.getText().isEmpty()) {
				
				// check if both password fields have input 
				if ( !passwordField.getText().isEmpty() && !passwordAgainField.getText().isEmpty() ) {

					// check if both password match 
					if (passwordField.getText().equals(passwordAgainField.getText()) ) {
						
						// update password
						user.setPassword(passwordField.getText());
					} else {
						
						throw new IllegalArgumentException("Passwords do not match");
					}

				} else {
					throw new IllegalArgumentException("Please enter password twice");
				}
			}

			// update database
			model.getUserDao().addDetails(user.getUsername(), user.getStudentID(), user.getFirstName(), user.getLastName());
			
			// update currentUser in model
			model.setCurrentUser(user);

			// change view to home
			Main.setRoot("/view/profileView");

		} catch (SQLException e) {
			message.setText(e.getMessage());

		} catch (IllegalArgumentException e) {
			message.setText(e.getMessage());

		}

	passwordField.clear();
	passwordAgainField.clear();

	}
}


