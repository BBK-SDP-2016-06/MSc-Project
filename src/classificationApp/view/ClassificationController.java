package classificationApp.view;

import classificationApp.MainApp;
import classificationApp.model.data.TimeSeries;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/**
 * Controller class for the classification mode layout.
 * Created by George Shiangoli on 05/08/2016.
 */
public class ClassificationController {

    @FXML
    private Label trainingFileName;

    @FXML
    private Label trainingClassCount;

    @FXML
    private Label trainingSetSize;

    @FXML
    private Label trainingDataLength;

    @FXML
    private TableView<TimeSeries> testTable;

    @FXML
    private TableColumn<TimeSeries, Integer> index;

    @FXML
    private TableColumn<TimeSeries, Integer> classLabel;

    @FXML
    private TableColumn<TimeSeries, List<Double>> timeSeries;

    @FXML
    private Button exit;

    private ObservableList<TimeSeries> tableData;

    private MainApp mainApp;

    @FXML
    private void initialize() {
        tableData = FXCollections.observableArrayList();
        index.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(testTable.getItems().indexOf(param.getValue())));
        classLabel.setCellValueFactory(new PropertyValueFactory<>("classType"));
        timeSeries.setCellValueFactory(new PropertyValueFactory<>("timeSeriesData"));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void updateTrainingData() {
        trainingFileName.setText("Name: " + mainApp.getTrainingFile().getName());
        trainingClassCount.setText("" + mainApp.getTrainingData().getClassCount());
        trainingSetSize.setText("" + mainApp.getTrainingData().getTimeSeriesCount());
        trainingDataLength.setText(mainApp.getTrainingData().getTimeSeriesLength().toString());
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
