package classificationApp.view;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Controller class for valid data dialogue confirmation.
 * Created by George Shiangoli on 04/08/2016.
 */
public class ValidDataConfirmationController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleOK() {
        stage.close();
    }
}
