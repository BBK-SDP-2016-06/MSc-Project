package classificationApp.view.controllers;

import classificationApp.model.data.TimeSeries;
import classificationApp.model.exception.ClassTypeException;
import classificationApp.model.exception.FileFormatException;
import classificationApp.model.exception.TimeSeriesFormatException;
import classificationApp.model.io.TestFileReader;
import classificationApp.model.io.TestFileReaderImpl;
import classificationApp.model.io.TrainingFileReader;
import classificationApp.model.io.TrainingFileReaderImpl;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;

import static classificationApp.model.math.MathUtils.*;

/**
 * Controller class for the TestDataSelection view.
 * Created by George Shiangoli on 18/08/2016.
 */
public class TestDataSelectionController {

    @FXML
    private ListView<TimeSeries> testSampleListView;

    @FXML
    private Label trainName;

    @FXML
    private Label trainClassCount;

    @FXML
    private Label trainSetSize;

    @FXML
    private Label trainLength;

    @FXML
    private Label testClass;

    @FXML
    private Label testLength;

    @FXML
    private Label testVariance;

    @FXML
    private Label testMean;

    @FXML
    private Button startButton;

    private AnimationRootController rootController;

    private ObservableList<TimeSeries> testSamples;

    @FXML
    private void initialize() {
        testSamples = FXCollections.observableArrayList();
        testSamples.addListener((ListChangeListener<TimeSeries>) c -> testSampleListView.setItems(testSamples));
        testSampleListView.getSelectionModel().getSelectedIndices().addListener((ListChangeListener<Integer>) c -> {
            startButton.setDisable(testSampleListView.getSelectionModel().getSelectedIndices().isEmpty());
            updateTestSampleStatistics();
        });

        testSampleListView.getItems().addListener((ListChangeListener<TimeSeries>) c -> {
            if (testSampleListView.getItems().isEmpty()) {
                startButton.setDisable(true);
            }
            updateTestSampleStatistics();
        });
    }

    @FXML
    private void handleChangeTest() {
        openDialogue("TEST");
    }

    @FXML
    private void handleChangeTrain() {
        openDialogue("TRAIN");
    }

    @FXML
    private void handleStart() {
        rootController.showDataVisualisation(testSampleListView.getSelectionModel().getSelectedItem());
    }

    private void openDialogue(String fileType) {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("All files (*.txt, *.csv)", "*.txt", "*.csv");
        fc.getExtensionFilters().addAll(allFilter, txtFilter, csvFilter);
        File selectedFile = fc.showOpenDialog(rootController.getMainApp().getMainStage());
        try {
            if (selectedFile != null) {
                if (fileType.equals("TEST")) {
                    TestFileReader test = new TestFileReaderImpl(selectedFile.getAbsolutePath());
                    setTest(test);
                } else if (fileType.equals("TRAIN")) {
                    TrainingFileReader train = new TrainingFileReaderImpl(selectedFile.getAbsolutePath());
                    setTrain(train);
                }
            }
        } catch (TimeSeriesFormatException | FileFormatException | ClassTypeException e) {
            ControllerUtils.showFileAlert(fileType + " File Error", e);
        }
    }

    public void setRootController(AnimationRootController rootController) {
        this.rootController = rootController;
        if (this.rootController.getMainApp().getTestData() != null) {
            testSamples.setAll(this.rootController.getMainApp().getTestData().getDataSet());
        }
        updateTrainingDataStatistics();
    }

    private void setTrain(TrainingFileReader train) {
        rootController.getMainApp().setTrainingData(train);
        updateTrainingDataStatistics();
    }

    private void setTest(TestFileReader test) {
        rootController.getMainApp().setTestData(test);
        testSamples.setAll(test.getDataSet());
        updateTestSampleStatistics();
    }

    private void updateTrainingDataStatistics() {
        TrainingFileReader train = rootController.getMainApp().getTrainingData();
        if  (train != null) {
            trainName.setText("Name: " + train.getFile().getName());
            trainClassCount.setText(String.valueOf(train.getClassCount()));
            trainSetSize.setText(String.valueOf(train.getTimeSeriesCount()));
            trainLength.setText(train.getTimeSeriesLength().toString());
        } else {
            trainName.setText("Name: -");
            trainClassCount.setText("0");
            trainSetSize.setText("0");
            trainLength.setText("0");
        }
    }

    private void updateTestSampleStatistics() {
        if (!testSampleListView.getSelectionModel().getSelectedItems().isEmpty()) {
            TimeSeries test = testSampleListView.getSelectionModel().getSelectedItem();
            testClass.setText(!test.getClassType().isPresent() ? "Unlabelled" : String.valueOf(test.getClassType().get()));
            testLength.setText(String.valueOf(test.getDataSize()));
            testVariance.setText(String.valueOf(to5SF(getStandardDeviation(test.getData()))));
            testMean.setText(String.valueOf(to5SF(getMean(test.getData()))));
        } else {
            testClass.setText("Unlabelled");
            testLength.setText("0");
            testVariance.setText("0");
            testMean.setText("0");
        }
    }
}
