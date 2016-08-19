package classificationApp.view.controllers;

import classificationApp.model.data.TimeSeries;
import javafx.fxml.FXML;

/**
 * Controller class for the Data Visualisation view of the animation mode
 * of the application.
 * Created by George Shiangoli on 18/08/2016.
 */
public class DataVisualisationController {

    private AnimationRootController rootController;
    private TimeSeries testSample;

    @FXML
    private void initialize() {}

    public void setRootController(AnimationRootController rootController) {
        this.rootController = rootController;
    }

    public void setTestSample(TimeSeries testSample) {
        this.testSample = testSample;
    }
}
