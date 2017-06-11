package classificationApp.view.controllers;

import classificationApp.MainApp;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Controller class for the filter dialogue.
 * Created by George Shiangoli on 07/08/2016.
 */
public class FilterController {

    @FXML
    private Label filterTypeLabel;

    @FXML
    private ChoiceBox<String> filterTypeChoice;

    @FXML
    private TextField userFilter;

    private Stage stage;
    private MainApp mainApp;
    private ClassificationController classificationController;
    private final static String RANGE_KEYWORD = "to";

    @FXML
    private void initialize() {
        filterTypeChoice.setItems(FXCollections.observableArrayList("Class Type", "Length", "Index"));
        filterTypeChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> filterTypeLabel.setText(newValue + " filter:"));
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setClassificationController(ClassificationController classificationController) {
        this.classificationController = classificationController;
    }

    @FXML
    private void handleOK() {
        try {
            List<Integer> singleValues = Stream.of(userFilter.getText().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> !s.contains(RANGE_KEYWORD))
                    .mapToInt(Integer::parseInt)
                    .boxed().collect(Collectors.toList());

            List<List<Integer>> rangeValues = Stream.of(userFilter.getText().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> s.contains(RANGE_KEYWORD))
                    .map(s -> Stream.of(s.split(RANGE_KEYWORD)).map(String::trim).map(Integer::parseInt).collect(Collectors.toList()))
                    .collect(Collectors.toList());

            List<Integer> filterValues = rangeValues.parallelStream().flatMap(l -> IntStream.range(l.get(0), 1 + l.get(1)).boxed()).collect(Collectors.toList());
            filterValues.addAll(singleValues);

            String filterType = filterTypeChoice == null ? "" : filterTypeChoice.getValue();

            if (!filterValues.isEmpty()) {
                switch (filterType) {
                    case "Class Type":
                        classificationController.getCurrentTestData().removeIf(ts -> !filterValues.contains(ts.getClassType().get()));
                        break;
                    case "Length":
                        classificationController.getCurrentTestData().removeIf(ts -> !filterValues.contains((int) ts.getDataSize()));
                        break;
                    case "Index":
                        classificationController.getCurrentTestData().removeIf(ts -> !filterValues.contains(mainApp.getTestData().getDataSet().indexOf(ts)));
                        break;
                    default:
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Filter Alert");
                        alert.setHeaderText("No Filter Type Selected");
                        alert.showAndWait();
                        break;
                }
            }
            stage.close();
        } catch (Exception e) {
            ControllerUtils.showFileAlert("Invalid Filter Alert", e);
        }
    }

    @FXML
    private void handleCancel() {
        stage.close();
    }
}
