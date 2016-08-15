package classificationApp.view.controllers;

import classificationApp.MainApp;
import javafx.fxml.FXML;

/**
 * Controller class for the Introduction window.
 * Created by George on 05/08/2016.
 */
public class IntroductionController {

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleStart() {
        mainApp.showTrainingDataLoader();
    }

    @FXML
    private void handleExit() {
        mainApp.closeApplication();
    }
}
