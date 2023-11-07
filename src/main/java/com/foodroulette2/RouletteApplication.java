package com.foodroulette2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@code RouletteApplication} class is the main entry point for the FoodRoulette JavaFX application.
 * It extends {@code Application}, which is the base class for JavaFX applications.
 */
public class RouletteApplication extends Application {

    /**
     * @param stage The primary stage for this application, onto which the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be primary stages.
     * @throws IOException If the FXML file for the main view cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RouletteApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 340, 300);
        stage.setTitle("FoodRoulette!");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * @param args the command line arguments passed to the application.
     *             Not used in this application.
     */
    public static void main(String[] args) {
        launch();
    }
}



