package com.foodroulette2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller class for the Roulette UI.
 * This class handles the user interactions with the UI and utilizes the RouletteService
 * to fetch and display random food information.
 */
public class RouletteController implements Initializable {

    @FXML
    private Label foodInformation;
    @FXML
    private Button startButton;
    private RouletteService rouletteService = new RouletteService();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded. It sets up the UI event handlers.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.setOnAction(actionEvent -> {
            try {
                String result = rouletteService.getRandomFoodInfo();
                foodInformation.setText(result);
            } catch (Exception e){
                foodInformation.setText("Error: " + e.getMessage());
            }
        });
    }

}





