package classificationApp.view.controllers;

import classificationApp.MainApp;
import classificationApp.model.data.TimeSeries;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller class for the animation mode of the application.
 * Created by George Shiangoli on 13/08/2016.
 */
public class AnimationRootController {

    @FXML
    private GridPane gridPaneLayout;

    @FXML
    private Label rawDataRepresentation;

    @FXML
    private Label normalization;

    @FXML
    private Label pAA;

    @FXML
    private Label discretization;

    @FXML
    private Label similarityMeasure;

    @FXML
    private Label kNNClassification;

    @FXML
    private Label result;

    @FXML
    private Label trainingFileName;

    @FXML
    private Label trainingFileClassCount;

    @FXML
    private Label trainingFileDataSetSize;

    @FXML
    private Label trainingFileTimeSeriesLength;

    @FXML
    private TextArea stageDescription;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    @FXML
    private Button exitButton;

    private MainApp mainApp;
    private IntegerProperty stageNumber;

    private TimeSeries rawTestSample;
    private TimeSeries normalizedTestSample;

    @FXML
    private void initialize() {
        stageNumber = new SimpleIntegerProperty();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        stageNumber.addListener((observable, oldValue, newValue) -> {
            removeStyleClasses();
            prevButton.setDisable(newValue.intValue() == 1);
            switch (newValue.intValue()) {
                case 1: //Select test sample / training file
                    rawDataRepresentation.getStyleClass().add("currentStageLabel");
                    stageDescription.setText("Select test data sample and click next to begin " +
                            "classification model walk-through.");
                    loadWalkThroughStep("view/TestSelection.fxml");
                    break;
                case 2: //Raw data representation
                    rawDataRepresentation.getStyleClass().add("currentStageLabel");
                    break;
                case 3: //Normalization process
                    normalization.getStyleClass().add("currentStageLabel");
                    break;
                case 4: //Frame partitioning
                    pAA.getStyleClass().add("currentStageLabel");
                    break;
                case 5: //Average computed within each partition
                    pAA.getStyleClass().add("currentStageLabel");
                    break;
                case 6: //Gaussian Distribution
                    pAA.getStyleClass().add("currentStageLabel");
                    break;
                case 7: //Discretization
                    discretization.getStyleClass().add("currentStageLabel");
                    break;
                case 8: //Similarity measure - string selection
                    similarityMeasure.getStyleClass().add("currentStageLabel");
                    break;
                case 9: //Similarity measure - matrix creation
                    similarityMeasure.getStyleClass().add("currentStageLabel");
                    break;
                case 10: //Similarity measure - result
                    similarityMeasure.getStyleClass().add("currentStageLabel");
                    break;
                case 11: //kNN classification
                    kNNClassification.getStyleClass().add("currentStageLabel");
                    break;
                case 12: //result
                    result.getStyleClass().add("currentStageLabel");
                    break;
                default:
                    break;
            }
        });
        stageNumber.set(1);
        setTRAIN();
    }

    @FXML
    private void handlePrevButton() {
        stageNumber.set(stageNumber.getValue() - 1);
    }

    @FXML
    private void handleNextButton() {
        stageNumber.set(stageNumber.getValue() + 1);
    }

    @FXML
    private void handleExitButton() {
        mainApp.closeApplication();
    }

    private void removeStyleClasses() {
        ObservableList<Node> gridPaneNodes = gridPaneLayout.getChildren();
        VBox stageList = null;
        for(Node n : gridPaneNodes) {
            if (n instanceof VBox) {
                stageList = (VBox)n;
            }
        }
        if (stageList != null) {
            stageList.getChildren().forEach(n -> n.getStyleClass().clear());
        }
    }

    private void loadWalkThroughStep(String filePath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(filePath));
            AnchorPane anchorPane = loader.load();
            gridPaneLayout.add(anchorPane, 2, 0);
            ClassificationStepController controller = loader.getController();
            controller.setController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTRAIN() {
        if (mainApp.getTrainingData() == null) {
            trainingFileName.setText("Name:");
            trainingFileClassCount.setText("0");
            trainingFileDataSetSize.setText("0");
            trainingFileTimeSeriesLength.setText("0");
        } else {
            trainingFileName.setText("Name: " + mainApp.getTrainingData().getFile().getName());
            trainingFileClassCount.setText(String.valueOf(mainApp.getTrainingData().getClassCount()));
            trainingFileDataSetSize.setText(String.valueOf(mainApp.getTrainingData().getTimeSeriesCount()));
            trainingFileTimeSeriesLength.setText(mainApp.getTrainingData().getTimeSeriesLength().toString());
        }
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setNextButtonDisabled(boolean isDisabled) {
        nextButton.setDisable(isDisabled);
    }

    public TimeSeries getRawTestSample() {
        return rawTestSample;
    }

    public void setRawTestSample(TimeSeries rawTestSample) {
        this.rawTestSample = rawTestSample;
    }

    public TimeSeries getNormalizedTestSample() {
        return normalizedTestSample;
    }

    public void setNormalizedTestSample(TimeSeries normalizedTestSample) {
        this.normalizedTestSample = normalizedTestSample;
    }
}
