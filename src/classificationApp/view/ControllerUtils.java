package classificationApp.view;

import classificationApp.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
}
