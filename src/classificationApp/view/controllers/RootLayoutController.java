package classificationApp.view.controllers;

import classificationApp.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Controller class for the root view (top menu bar of application).
 * Created by George Shiangoli on 13/08/2016.
 */
public class RootLayoutController {

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem changeMode;

    @FXML
    private MenuItem close;

    @FXML
    private Menu helpMenu;

    @FXML
    private MenuItem about;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleChangeMode() {
        if (mainApp.getMode().equals("Classification")) {
            mainApp.showAnimationLayout();
        } else {
            mainApp.showClassificationLayout();
        }
    }

    @FXML
    private void handleClose() {
        mainApp.closeApplication();
    }

    @FXML
    private void handleAbout() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage)infoAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:resources/Icon.jpg"));
        infoAlert.setTitle("About");
        infoAlert.setHeaderText("");
        infoAlert.setContentText("Author: George Shiangoli 2016");
        infoAlert.showAndWait();
    }
}
