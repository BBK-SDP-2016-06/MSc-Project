package classificationApp.view.controllers;

import classificationApp.MainApp;
import classificationApp.model.exception.ClassTypeException;
import classificationApp.model.exception.FileFormatException;
import classificationApp.model.exception.TimeSeriesFormatException;
import classificationApp.model.io.TestFileReader;
import classificationApp.model.io.TestFileReaderImpl;
import classificationApp.model.io.TrainingFileReader;
import classificationApp.model.io.TrainingFileReaderImpl;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Controller class for the classification settings layout.
 * Created by George Shiangoli on 07/08/2016.
 */
public class ClassificationSettingsController {

    private MainApp mainApp;
    private Stage stage;
    private ClassificationController classController;
    private IntegerProperty kValue;
    private IntegerProperty frameCount;
    private IntegerProperty alphabetSize;
    private TrainingFileReader train;
    private TestFileReader test;

    @FXML
    private Label trainingFileName;

    @FXML
    private Label trainingFilePath;

    @FXML
    private Label trainingSampleCount;

    @FXML
    private Label trainingClassCount;

    @FXML
    private Label trainingTimeSeriesLength;

    @FXML
    private Label testFileName;

    @FXML
    private Label testFilePath;

    @FXML
    private Label testSampleCount;

    @FXML
    private Label testTimeSeriesLength;

    @FXML
    private TextField kValueText;

    @FXML
    private Slider kValueSlider;

    @FXML
    private TextField frameCountText;

    @FXML
    private Slider frameCountSlider;

    @FXML
    private TextField alphabetText;

    @FXML
    private Slider alphabetSlider;

    @FXML
    private void initialize() {
        kValueSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() % 2 == 0) {
                kValueSlider.setValue(newValue.doubleValue() - 1);
            }
        });
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        train = mainApp.getTrainingData();
        test = mainApp.getTestData();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setClassificationController(ClassificationController controller) {
        this.classController = controller;

        kValue = new SimpleIntegerProperty(controller.getKValue());
        kValueSlider.valueProperty().bindBidirectional(kValue);
        kValueText.textProperty().bind(kValue.asString());

        frameCount = new SimpleIntegerProperty(controller.getFrameCount());
        frameCountSlider.valueProperty().bindBidirectional(frameCount);
        frameCountText.textProperty().bind(frameCount.asString());

        alphabetSize = new SimpleIntegerProperty(controller.getAlphabetSize());
        alphabetSlider.valueProperty().bindBidirectional(alphabetSize);
        alphabetText.textProperty().bind(alphabetSize.asString());

        updateSliderParameters();
    }

    public void setFileDataStatistics(TrainingFileReader train, TestFileReader test) {
        setTrainDataStatistics(train);
        setTestDataStatistics(test);
    }

    private void setTrainDataStatistics(TrainingFileReader train) {
        trainingFileName.setText(train.getFile().getName());
        trainingFilePath.setText(train.getFile().getAbsolutePath());
        trainingSampleCount.setText("" + train.getTimeSeriesCount());
        trainingClassCount.setText("" + train.getClassCount());
        trainingTimeSeriesLength.setText(train.getTimeSeriesLength().toString());
    }

    private void setTestDataStatistics(TestFileReader test) {
        testFileName.setText(test.getFile().getName());
        testFilePath.setText(test.getFile().getAbsolutePath());
        testSampleCount.setText("" + test.getTimeSeriesCount());
        testTimeSeriesLength.setText(test.getTimeSeriesLength().toString());
    }

    private void setDefaultValues() {
        kValue.setValue(ControllerUtils.getDefaultKValue(train));
        frameCount.setValue(ControllerUtils.getDefaultFrameCount(train, test));
        alphabetSize.setValue(ControllerUtils.getDefaultAlphabetSize());
    }

    private void updateSliderParameters() {
        kValueSlider.setBlockIncrement(2);
        kValueSlider.setMin(1);
        kValueSlider.setMax(train.getTimeSeriesCount() +
                ((train.getTimeSeriesCount() % 2) - 1));

        frameCountSlider.setBlockIncrement(1);
        frameCountSlider.setMin(1);
        frameCountSlider.setMax(ControllerUtils.getDefaultFrameCount(train, test));
    }

    @FXML
    private void handleChangeTrain() {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                train = new TrainingFileReaderImpl(selectedFile.getAbsolutePath());
                setTrainDataStatistics(train);
                setDefaultValues();
                updateSliderParameters();
            } catch (ClassTypeException | FileFormatException | TimeSeriesFormatException e) {
                ControllerUtils.showFileAlert("Training File Error", e);
            }
        }
    }

    @FXML
    private void handleChangeTest() {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                test = new TestFileReaderImpl(selectedFile.getAbsolutePath());
                setTestDataStatistics(test);
                setDefaultValues();
                updateSliderParameters();
            } catch (ClassTypeException | FileFormatException | TimeSeriesFormatException e) {
                ControllerUtils.showFileAlert("Test File Error", e);
            }
        }
    }

    @FXML
    private void handleOK() {
        if (!mainApp.getTrainingData().equals(train)) {
            mainApp.setTrainingData(train);
            classController.updateTrainingData();
        }
        if (!mainApp.getTestData().equals(test)) {
            mainApp.setTestData(test);
            classController.setTestData(test.getFile());
        }
        classController.setClassifierParams(kValue.getValue(), frameCount.getValue(), alphabetSize.getValue());
        stage.close();
    }

    @FXML
    private void handleDefault() {
        setDefaultValues();
    }

    @FXML
    private void handleCancel() {
        stage.close();
    }
}
