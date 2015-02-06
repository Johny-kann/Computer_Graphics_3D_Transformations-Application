package com.computer_graphics.controller;

import java.net.URL;

import com.computer_graphics.controller.gui.CanvasController;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;


/**
 * @author Janakiraman 
 * Main class used to start the application
 *
 */
public class Main extends Application {
@Override
public void start(Stage primaryStage) {
	try {
		
			
		FXMLLoader loader = new FXMLLoader();
		
		loader.setBuilderFactory(new JavaFXBuilderFactory());
		
		URL location = getClass().getResource("/3DPage.fxml");
	
		
		FXMLLoader fxmlLoader = new FXMLLoader();
		
		fxmlLoader.setLocation(location);
		
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		
		StackPane root2 =  fxmlLoader.load(location.openStream());
		
	
		CanvasController basementController = fxmlLoader.getController();
	
		Scene scene = new Scene(root2,800,600,true);
	
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	} catch(Exception e) {
		e.printStackTrace();
	}
}

public static void main(String[] args) {
	launch(args);
}
}
