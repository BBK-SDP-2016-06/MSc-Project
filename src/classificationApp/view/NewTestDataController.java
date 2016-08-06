package classificationApp.view;

import classificationApp.model.data.TimeSeries;
import classificationApp.model.exception.ClassTypeException;
import classificationApp.model.exception.FileFormatException;
import classificationApp.model.exception.IOExceptionHandler;
import classificationApp.model.exception.TimeSeriesFormatException;
import classificationApp.model.io.InputUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller class for the NewTestData layout.
 * Created by George Shiangoli on 03/08/2016.
 */
public class NewTestDataController extends NewDataController {

    @FXML
    private TextArea textArea;

    @FXML
    private Button okButton;

    private ClassificationController controller;

    @FXML
    private void initialize() {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(true);
        });
    }

    public void setClassificationController(ClassificationController controller) {
        this.controller = controller;
    }

    @FXML
    private void handleCancel() {
        dialogueStage.close();
    }

    @FXML
    private void handleOK() {
        File file = getSelectedFile();
        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(textArea.getText());
                writer.close();
                dialogueStage.close();
                controller.setTestData(file);
                controller.showStatistics(null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassTypeException | FileFormatException | TimeSeriesFormatException e) {
                ControllerUtils.showFileAlert("Test File Error", e);
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
            IOExceptionHandler.validateTestTimeSeries(timeSeries);
            ControllerUtils.showValidDataDialogue();
            okButton.setDisable(false);
        } catch (ClassTypeException | FileFormatException | TimeSeriesFormatException e) {
            ControllerUtils.showFileAlert("Test File Error", e);
        }
    }

}