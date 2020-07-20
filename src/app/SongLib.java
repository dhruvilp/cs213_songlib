/**
 * @description CS 213 Project 1
 * @author Dhruvil Patel <dhp68>
 * @author Nicholas Bonura <njb127>
 */

package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.ListController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader();   
		    loader.setLocation(getClass().getResource("/view/view.fxml"));
		    BorderPane root = (BorderPane)loader.load();

		    ListController listController = loader.getController();
		    listController.start(primaryStage);
		      
			Scene scene = new Scene(root,650,450);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
		    primaryStage.setTitle("Song(s) Library");
			primaryStage.setScene(scene);
		    primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
