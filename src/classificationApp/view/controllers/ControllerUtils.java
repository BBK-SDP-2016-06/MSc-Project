package classificationApp.view.controllers;

import classificationApp.MainApp;
import classificationApp.model.io.TestFileReader;
import classificationApp.model.io.TrainingFileReader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class to hold methods used by all controller classes.
 * Created by George Shiangoli on 04/08/2016.
 */
public class ControllerUtils {

    public static void showFileAlert(String title, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(e.getClass().getSimpleName());
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public static Stage getModalStage(String stageTitle) {
        Stage stage = new Stage();
        stage.setTitle(stageTitle);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    public static void showValidDataDialogue() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ValidDataConfirmation.fxml"));
            AnchorPane layout = loader.load();
            ValidDataConfirmationController controller = loader.getController();
            controller.setStage(stage);
            stage.setScene(new Scene(layout));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkClassLabels(TrainingFileReader train, TestFileReader test) {
        Set<Integer> trainClasses = train.getClassList().parallelStream().collect(Collectors.toSet());
        Set<Integer> testClasses = test.getClassList().parallelStream().collect(Collectors.toSet());
        testClasses.removeAll(trainClasses);
        if (!testClasses.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Test File Warning");
            alert.setHeaderText("Test Class Label Warning");
            alert.setContentText("Test data contains class labels: " + testClasses.toString()
                    + "\n which are not found in Training data.");
            alert.showAndWait();
        }
    }

    public static int getDefaultKValue(TrainingFileReader train) {
        return train.getTimeSeriesCount() < 3 ? 1 : 3;
    }

    public static int getDefaultFrameCount(TrainingFileReader train, TestFileReader test) {
        return Math.min(
                test.getDataSet().parallelStream()
                        .mapToInt(ts -> ts.getData().size())
                        .min()
                        .getAsInt(),
                (int)train.getTimeSeriesLength().getLowerBound());
    }

    public static int getDefaultAlphabetSize() {
        return 5;
    }
}
