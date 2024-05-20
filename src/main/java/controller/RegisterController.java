package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.Main;
import model.Model;
import model.User;

/**
 * For register view
 */
public class RegisterController {
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField passwordAgain;
	@FXML
	private TextField studentID;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private VBox entryBox;
	@FXML
	private VBox entryBox2;
	@FXML
	private Button login;
	@FXML
	private Button register;

	private Model model;

	private Label message;

	
	/*
	 * change to login view
	 */
	@FXML
	private void switchRegister() throws IOException {
		Main.setRoot("/view/loginView");
	}

	/*
	 * checks if username and password are correct, then adds user
	 */
	@FXML
	private void checkEntry1() throws IOException {
		model = Main.getModel();
		if (!username.getText().isEmpty() && !password.getText().isEmpty() && !passwordAgain.getText().isEmpty()) {
			//if(password.getText() == passwordAgain.getText()) {
			if(password.getText().equals(passwordAgain.getText())) {
				User user;
				Boolean result;
				try {
					result = model.getUserDao().checkUserExists(username.getText());
					// make sure username is unique
					if (!result) {
						user = model.getUserDao().createUser(username.getText(), password.getText());
						model.setCurrentUser(user);

						entryBox.setVisible(false);
						entryBox.setManaged(false);

						entryBox2.setVisible(true);
						entryBox2.setManaged(true);

					} else {
						entryBox.getChildren().remove(message);
						message = new Label("The username " + username.getText() + " already exists");
						entryBox.getChildren().add(message);

					}
				} catch (SQLException e) {
					entryBox.getChildren().remove(message);
					message = new Label(e.getMessage());
					entryBox.getChildren().add(message);

				}
			} else {
				entryBox.getChildren().remove(message);
				message = new Label("Passwords do not match");
				entryBox.getChildren().add(message);

			}
		} else {
			entryBox.getChildren().remove(message);
			message = new Label("Empty username or password");
			entryBox.getChildren().add(message);

		}
		username.clear();
		password.clear();
		passwordAgain.clear();
	}


	/*
	 * checks if studentID, first and last name details are correct, and adds details
	 */
	@FXML
	private void checkEntry2() throws IOException {
		model = Main.getModel();
		if (!studentID.getText().isEmpty() && !firstName.getText().isEmpty() && !lastName.getText().isEmpty()) {
			User user;
			Boolean result;
			try {
				result = model.getUserDao().checkStudentIDExists(studentID.getText());
				// make sure studentID is unique
				if (!result) {

					user = model.getCurrentUser();
					Boolean success = model.getUserDao().addDetails(user.getUsername(), studentID.getText(), firstName.getText(), lastName.getText());
					user = model.getUserDao().getUser(user.getUsername(), user.getPassword());
					model.setCurrentUser(user);

					System.out.print(success);
					Main.setRoot("/view/homeView");
				} else {
					entryBox2.getChildren().remove(message);
					message = new Label("The student ID " + studentID.getText() + " already exists");
					entryBox2.getChildren().add(message);

				}
			} catch (SQLException e) {
				entryBox2.getChildren().remove(message);
				message = new Label(e.getMessage());
				entryBox2.getChildren().add(message);

			}

		} else {
			entryBox2.getChildren().remove(message);
			message = new Label("Empty username or password");
			entryBox2.getChildren().add(message);

		}
		studentID.clear();
		firstName.clear();
		lastName.clear();
	}



}

