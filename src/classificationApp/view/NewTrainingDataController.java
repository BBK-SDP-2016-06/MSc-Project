package classificationApp.view;
import classificationApp.model.data.TimeSeries;
import classificationApp.model.exception.ClassTypeException;
import classificationApp.model.exception.FileFormatException;
import classificationApp.model.exception.IOExceptionHandler;
import classificationApp.model.exception.TimeSeriesFormatException;
import classificationApp.model.io.InputUtils;
import classificationApp.model.io.TrainingFileReader;
import classificationApp.model.io.TrainingFileReaderImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller class for the NewTrainingData layout.
 * Created by George Shiangoli on 03/08/2016.
 */
public class NewTrainingDataController {

    @FXML
    private TextArea textArea;

    @FXML
    private Button okButton;

    private Stage dialogueStage;
    private LoadTrainingController loadTrainingController;

    @FXML
    public void initialize() {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(true);
        });
    }

    public void setDialogueStage(Stage dialogueStage) {
        this.dialogueStage = dialogueStage;
    }

    public void setLoadTrainingController(LoadTrainingController loadTrainingController) {
        this.loadTrainingController = loadTrainingController;
    }

    @FXML
    private void handleCancel() {
        dialogueStage.close();
    }

    @FXML
    private void handleOK() {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fc.getExtensionFilters().addAll(txtFilter, csvFilter);
        File file = fc.showSaveDialog(dialogueStage);
        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(textArea.getText());
                writer.close();
                TrainingFileReader trainingData = new TrainingFileReaderImpl(file.getAbsolutePath());
                loadTrainingController.showTrainingDataStatistics(trainingData, file);
                dialogueStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassTypeException | FileFormatException | TimeSeriesFormatException e) {
                ControllerUtils.showTrainingFileAlert(e);
                loadTrainingController.showTrainingDataStatistics(null, null);
            }
        }
    }

    @FXML
    private void handleValidate() {
        try {
            List<TimeSeries> timeSeries = Stream.of(textArea.getText().split("\\r?\\n"))
                    .filter(s -> !s.isEmpty())
                    .map(InputUtils::toTimeSeries)
                    .collect(Collectors.toList());
            IOExceptionHandler.validateTrainingTimeSeries(timeSeries);
            ControllerUtils.showValidDataDialogue();
            okButton.setDisable(false);
        } catch (ClassTypeException | FileFormatException | TimeSeriesFormatException e) {
            ControllerUtils.showTrainingFileAlert(e);
        }
    }
}