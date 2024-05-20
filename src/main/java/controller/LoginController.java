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
 * For login view
 */
public class LoginController {
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private VBox entryBox;
	@FXML
	private Button login;
	@FXML
	private Button register;

	private Model model;

	private Label message;


	/*
	 * change to register view
	 */
	@FXML
	private void switchRegister() throws IOException {
		Main.setRoot("/view/registerView1");
	}

	/*
	 * checks if username and password are correct, then goes to home page
	 */
	@FXML
	private void checkPassword() throws IOException {
		model = Main.getModel();
		
		// check both fields are not empty
		if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
			User user;
			try {
				// check if user exists for that information
				user = model.getUserDao().getUser(username.getText(), password.getText());
				if (user != null) {
					model.setCurrentUser(user);
					System.out.println(user.getUsername());
					try {
						Main.setRoot("/view/homeView");
					}catch (IOException e) {
						entryBox.getChildren().remove(message);
						message = new Label(e.getMessage());
						entryBox.getChildren().add(message);
					}

				} else {
					entryBox.getChildren().remove(message);
					message = new Label("Wrong username or password");
					entryBox.getChildren().add(message);
				}
			} catch (SQLException e) {
				entryBox.getChildren().remove(message);
				message = new Label(e.getMessage());
				entryBox.getChildren().add(message);
			}

		} else {
			entryBox.getChildren().remove(message);
			message = new Label("Empty username or password");
			entryBox.getChildren().add(message);

		}
		username.clear();
		password.clear();
	}

}

