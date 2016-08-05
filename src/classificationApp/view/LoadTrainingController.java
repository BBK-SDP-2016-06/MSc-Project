package classificationApp.view;
import classificationApp.MainApp;
import classificationApp.model.exception.ClassTypeException;
import classificationApp.model.exception.FileFormatException;
import classificationApp.model.exception.TimeSeriesFormatException;
import classificationApp.model.io.TrainingFileReader;
import classificationApp.model.io.TrainingFileReaderImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

/**
 * Controller class for the LoadTrainingLayout.
 * Created by George Shiangoli on 03/08/2016.
 */
public class LoadTrainingController {

    @FXML
    private Label fileName;

    @FXML
    private Label classCount;

    @FXML
    private Label sampleCount;

    @FXML
    private Label dataLength;

    @FXML
    private Label filePath;

    @FXML
    private Button load;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleNewButtonPress() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/NewTrainingData.fxml"));
        Stage stage = ControllerUtils.getModalStage("New Training Data");
        try {
            stage.setScene(new Scene(loader.load()));
            stage.show();
            NewTrainingDataController newDataController = loader.getController();
            newDataController.setDialogueStage(stage);
            newDataController.setLoadTrainingController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleImportButtonPress() {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("All files (*.txt, *.csv)", "*.txt", "*.csv");
        fc.getExtensionFilters().addAll(allFilter, txtFilter, csvFilter);
        File selectedFile = fc.showOpenDialog(mainApp.getOpeningStage());
        if (selectedFile != null) {
            try {
                TrainingFileReader trainingData = new TrainingFileReaderImpl(selectedFile.getAbsolutePath());
                showTrainingDataStatistics(trainingData, selectedFile);
            } catch (ClassTypeException | FileFormatException | TimeSeriesFormatException e) {
                ControllerUtils.showFileAlert("Testing File Error", e);
                showTrainingDataStatistics(null, null);
            }
        }
    }

    @FXML
    private void handleLoadButtonPress() {
        mainApp.getOpeningStage().close();
        mainApp.showClassificationLayout();
    }

    @FXML
    private void handleExitButtonPress() {
        System.exit(0);
    }

    public void showTrainingDataStatistics(TrainingFileReader trainingData, File trainingFile) {
        mainApp.setTrainingData(trainingData);
        mainApp.setTrainingFile(trainingFile);
        setLoadButton();
        if (mainApp.getTrainingData() == null) {
            fileName.setText("-");
            classCount.setText("0");
            sampleCount.setText("0");
            dataLength.setText("0");
            filePath.setText("-");
        } else {
            fileName.setText(trainingFile.getName());
            classCount.setText("" + trainingData.getClassCount());
            sampleCount.setText("" + trainingData.getTimeSeriesCount());
            dataLength.setText(trainingData.getTimeSeriesLength().toString());
            filePath.setText(trainingFile.getAbsolutePath());
        }
    }

    private void setLoadButton() {
        if (mainApp.getTrainingData() != null) {
            load.setDisable(false);
        } else {
            load.setDisable(true);
        }
    }
}