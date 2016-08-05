package classificationApp.view;

import static classificationApp.model.preprocessing.MathUtils.*;
import classificationApp.MainApp;
import classificationApp.model.data.TimeSeries;
import classificationApp.model.exception.ClassTypeException;
import classificationApp.model.exception.FileFormatException;
import classificationApp.model.exception.TimeSeriesFormatException;
import classificationApp.model.io.TestFileReader;
import classificationApp.model.io.TestFileReaderImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import java.io.File;

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
    private TableColumn<TimeSeries, String> timeSeries;

    @FXML
    private LineChart<Integer, Double> testLineChart;

    @FXML
    private Label testClassLabel;

    @FXML
    private Label testLength;

    @FXML
    private Label testNormalized;

    @FXML
    private Label testValueRange;

    @FXML
    private Label testSD;

    @FXML
    private Label testMean;

    @FXML
    private Button exit;

    private ObservableList<TimeSeries> tableData;

    private MainApp mainApp;

    @FXML
    private void initialize() {
        index.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(mainApp.getTestData().getDataSet().indexOf(param.getValue())));
        classLabel.setCellValueFactory(new PropertyValueFactory<>("classType"));
        timeSeries.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getData().toString()));
        showStatistics(null);
        testTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showStatistics(newValue));
        testTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
    private void handleImport() {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("All files (*.txt, *.csv)", "*.txt", "*.csv");
        fc.getExtensionFilters().addAll(allFilter, txtFilter, csvFilter);
        File selectedFile = fc.showOpenDialog(mainApp.getMainStage());
        if (selectedFile != null) {
            try {
                TestFileReader testData = new TestFileReaderImpl(selectedFile.getAbsolutePath());
                mainApp.setTestData(testData);
                tableData = FXCollections.observableArrayList();
                tableData.addAll(testData.getDataSet());
                testTable.setItems(tableData);
            } catch (ClassTypeException | FileFormatException | TimeSeriesFormatException e) {
                ControllerUtils.showFileAlert("Test File Error", e);
            }
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    private void showStatistics(TimeSeries timeSeries) {
        if (timeSeries != null) {
            XYChart.Series<Integer, Double> series = new XYChart.Series<>();
            for (int i = 0; i < timeSeries.getDataSize(); i++) {
                series.getData().add(new XYChart.Data<>(i, timeSeries.getData().get(i)));
            }
            testLineChart.getData().clear();
            testLineChart.getData().add(series);
            testClassLabel.setText((timeSeries.getClassType() == -1) ? "Unlabelled" : "" + timeSeries.getClassType());
            testLength.setText("" + timeSeries.getDataSize());
            testNormalized.setText(isZNormalized(timeSeries.getData()) ? "YES" : "NO");
            testValueRange.setText(timeSeries.getMin() + " - " + timeSeries.getMax());
            testSD.setText("" + to5SF(getStandardDeviation(timeSeries.getData())));
            double mean = to5SF(getMean(timeSeries.getData()));
            testMean.setText(Math.abs(mean - 0) < 0.00001 ? "0.0" : "" + mean);
        } else {
            testLineChart.getData().clear();
            testClassLabel.setText("");
            testLength.setText("");
            testNormalized.setText("");
            testValueRange.setText("");
            testSD.setText("");
            testMean.setText("");
        }
    }
}
