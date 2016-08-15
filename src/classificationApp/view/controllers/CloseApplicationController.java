package classificationApp.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller class for the CloseApplication dialogue.
 * Created by George Shiangoli on 15/08/2016.
 */
public class CloseApplicationController {

    @FXML
    private Label closeAppQuestion;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleYesButton() {
        System.exit(0);
    }

    @FXML
    private void handleNoButton() {
        stage.close();
    }
}
