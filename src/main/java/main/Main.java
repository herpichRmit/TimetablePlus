package main;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Model;

/**
 * Main class, extends application in order to launch the program
 */
public class Main extends Application {

	private static Model model;
	private static Scene scene;

	@Override
	public void init() {
		model = new Model();
	}

	@Override
	public void start(Stage stage) throws IOException, SQLException {
		model.setup();
		scene = new Scene(loadFXML("/view/loginView"));
        stage.setScene(scene);
        stage.show();

	}

	/*
	 * utility function to change screen size
	 */
	public static void changeFullSize() throws IOException {
		Window win = scene.getWindow();
		win.setWidth(1280);
		win.setHeight(720);
		win.centerOnScreen();
    }
	
	/*
	 * utility function to change screen size
	 */
	public static void changeSmallSize() throws IOException {
		Window win = scene.getWindow();
		win.setWidth(600);
		win.setHeight(400);
		win.centerOnScreen();
    }
	
	/*
	 * utility function to access model
	 */
	public static Model getModel() {
		return model;

	}

	/*
	 * used to change scenes
	 */
	public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

	/*
	 * accesses relevant fxml
	 */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /*
	 * runs application
	 */
	public static void main(String[] args) {
		launch(args);
	}


}
