package classificationApp.view;

import classificationApp.MainApp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
    private TextArea stageDescription;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    @FXML
    private Button exitButton;

    private MainApp mainApp;
    private IntegerProperty stageNumber;

    @FXML
    private void initialize() {
        stageNumber = new SimpleIntegerProperty();
        stageNumber.addListener((observable, oldValue, newValue) -> {
            removeStyleClasses();
            prevButton.setDisable(newValue.intValue() == 1);
            nextButton.setDisable(newValue.intValue() == 12);
            switch (newValue.intValue()) {
                case 1: //Select test sample / training file
                    rawDataRepresentation.getStyleClass().add("currentStageLabel");
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
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
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
}
