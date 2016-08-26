package classificationApp.view.controllers;

import static classificationApp.model.math.MathUtils.*;
import classificationApp.MainApp;
import classificationApp.model.classification.*;
import classificationApp.model.data.DiscretizedData;
import classificationApp.model.data.DiscretizedDataImpl;
import classificationApp.model.data.TimeSeries;
import classificationApp.model.exception.ClassTypeException;
import classificationApp.model.exception.FileFormatException;
import classificationApp.model.exception.TimeSeriesFormatException;
import classificationApp.model.io.TestFileReader;
import classificationApp.model.io.TestFileReaderImpl;
import classificationApp.model.io.TrainingFileReader;
import classificationApp.model.preprocessing.PreProcessor;
import classificationApp.model.preprocessing.SAXPreProcessor;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Controller class for the classification mode layout.
 * Created by George Shiangoli on 05/08/2016.
 */
public class ClassificationController {

    @FXML
    private VBox buttonMenu;

    @FXML
    private Label trainingFileName;

    @FXML
    private Label trainingClassCount;

    @FXML
    private Label trainingSetSize;

    @FXML
    private Label trainingDataLength;

    @FXML
    private Label kValueLabel;

    @FXML
    private Label frameCountLabel;

    @FXML
    private Label alphabetSizeLabel;

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
    private Button clearButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button classifyAllButton;

    @FXML
    private Button classifySelectedButton;

    @FXML
    private Button filterButton;

    @FXML
    private Button removeFilterButton;

    @FXML
    private Button statisticsButton;

    @FXML
    private Button clearResultButton;

    @FXML
    private Button exit;

    @FXML
    private TextArea resultTextArea;

    @FXML
    private Label errorRate;

    @FXML
    private Label statusLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button stopButton;

    private ObservableList<TimeSeries> currentTestData;
    private ObservableList<ClassificationResult> classificationResults;

    private IntegerProperty kValue;
    private IntegerProperty frameCount;
    private IntegerProperty alphabetSize;

    private DoubleProperty progress;
    private ExecutorService executor;

    private MainApp mainApp;

    @FXML
    private void initialize() {
        index.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(mainApp.getTestData().getDataSet().indexOf(param.getValue())));
        classLabel.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getClassType().orElse(-1)));
        timeSeries.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getData().toString()));

        showStatistics(null);
        testTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        testTable.getSelectionModel().getSelectedIndices().addListener((ListChangeListener<Integer>) c -> {
            showStatistics(testTable.getSelectionModel().getSelectedItems());
            classifySelectedButton.setDisable(testTable.getSelectionModel().getSelectedItems().isEmpty());
        });

        currentTestData = FXCollections.observableArrayList();
        currentTestData.addListener((ListChangeListener<TimeSeries>) c -> {
            clearButton.setDisable(currentTestData.isEmpty());
            settingsButton.setDisable(currentTestData.isEmpty());
            classifyAllButton.setDisable(currentTestData.isEmpty());
            filterButton.setDisable(currentTestData.isEmpty());
            testTable.setItems(currentTestData);
            removeFilterButton.setDisable(mainApp.getTestData() == null || currentTestData.containsAll(mainApp.getTestData().getDataSet()));
        });


        kValue = new SimpleIntegerProperty();
        kValue.addListener((observable, oldValue, newValue) ->
                kValueLabel.setText((newValue.equals(0)) ? "" : newValue.toString()));

        frameCount = new SimpleIntegerProperty();
        frameCount.addListener((observable, oldValue, newValue) ->
                frameCountLabel.setText((newValue.equals(0)) ? "" : newValue.toString()));

        alphabetSize = new SimpleIntegerProperty();
        alphabetSize.addListener((observable, oldValue, newValue) ->
                alphabetSizeLabel.setText((newValue.equals(0)) ? "" : newValue.toString()));

        resultTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (resultTextArea.getText().isEmpty()) {
                progress.setValue(0);
                errorRate.setText("Error Rate:");
                statusLabel.setText("Status:");
                clearResultButton.setDisable(true);
                statisticsButton.setDisable(true);
            }
        });

        progress = new SimpleDoubleProperty(0);
        progress.addListener((observable, oldValue, newValue) -> {
            if (progress.getValue() == 1.0) {
                statusLabel.setText("Status: Finished!");
                long errorCount = classificationResults.parallelStream().filter(r -> !r.isCorrectPred() && r.getActClass().isPresent()).count();
                long labelledSamples = classificationResults.parallelStream().filter(r -> r.getActClass().isPresent()).count();
                errorRate.setText("Error Rate: " + errorCount + " / " + labelledSamples + " = " + (labelledSamples == 0 ? 0.0 : to5SF((double)errorCount/(double)labelledSamples)));
                buttonMenu.setDisable(false);
                testTable.setDisable(false);
                clearResultButton.setDisable(false);
                stopButton.setDisable(true);
                statisticsButton.setDisable(false);
            }
        });

        progressBar.progressProperty().bind(progress);

        classificationResults = FXCollections.observableArrayList();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        if (mainApp.getTestData() != null) {
            setTestData(mainApp.getTestData().getFile());
        }
    }

    public void updateTrainingData() {
        trainingFileName.setText("Name: " + mainApp.getTrainingData().getFile().getName());
        trainingClassCount.setText("" + mainApp.getTrainingData().getClassCount());
        trainingSetSize.setText("" + mainApp.getTrainingData().getTimeSeriesCount());
        trainingDataLength.setText(mainApp.getTrainingData().getTimeSeriesLength().toString());
    }

    @FXML
    private void handleNew() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/NewTestData.fxml"));
        Stage stage = ControllerUtils.getModalStage("New Test Data");
        try {
            stage.setScene(new Scene(loader.load()));
            stage.show();
            NewTestDataController newDataController = loader.getController();
            newDataController.setDialogueStage(stage);
            newDataController.setClassificationController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                setTestData(selectedFile);
                showStatistics(null);
            } catch (ClassTypeException | FileFormatException | TimeSeriesFormatException e) {
                ControllerUtils.showFileAlert("Test File Error", e);
            }
        }
    }

    @FXML
    private void handleClear() {
        currentTestData.clear();
        setDefaultSettings(mainApp.getTrainingData(), mainApp.getTestData());
        mainApp.setTestData(null);
        removeFilterButton.setDisable(true);
    }

    @FXML
    private void handleSettings() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ClassificationSettings.fxml"));
        Stage stage = ControllerUtils.getModalStage("Classification Settings");
        try {
            stage.setScene(new Scene(loader.load()));
            stage.show();
            ClassificationSettingsController controller = loader.getController();
            controller.setStage(stage);
            controller.setMainApp(mainApp);
            controller.setClassificationController(this);
            controller.setFileDataStatistics(mainApp.getTrainingData(), mainApp.getTestData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFilter() {
        testTable.getSelectionModel().clearSelection();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/Filter.fxml"));
        Stage stage = ControllerUtils.getModalStage("Filter");
        try {
            stage.setScene(new Scene(loader.load()));
            stage.show();
            FilterController controller = loader.getController();
            controller.setStage(stage);
            controller.setMainApp(mainApp);
            controller.setClassificationController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemoveFilter() {
        currentTestData.clear();
        currentTestData.setAll(mainApp.getTestData().getDataSet());
    }

    @FXML
    private void handleClassifySelected() {
        displayClassificationResults(testTable.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void handleClassify() {
        displayClassificationResults(currentTestData);
    }

    @FXML
    private void handleStop() {
        executor.shutdownNow();
        stopButton.setDisable(true);
        progress.setValue(1.0);
        statisticsButton.setDisable(false);
    }

    @FXML
    private void handleStatisticsButton() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ResultStatistics.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Classification Results: " + getKValue() + "NN, frame(" + getFrameCount() + "), alphabet(" + getAlphabetSize() + ")");
            stage.getIcons().add(new Image("file:resources/Icon.jpg"));
            stage.setScene(new Scene(loader.load()));
            stage.show();
            ResultStatisticsController controller = loader.getController();
            controller.setStage(stage);
            controller.setClassificationResults(this.classificationResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClearResultButton() {
        resultTextArea.clear();
        classificationResults.clear();
    }

    @FXML
    private void handleExit() {
        mainApp.closeApplication();
    }

    private void displayClassificationResults(List<TimeSeries> test) {
        stopButton.setDisable(false);
        clearResultButton.setDisable(true);
        buttonMenu.setDisable(true);
        testTable.setDisable(true);
        resultTextArea.clear();
        classificationResults.clear();
        statusLabel.setText("Status: Pre-Processing");
        executor = Executors.newSingleThreadExecutor();
        PreProcessor preProcessor = new SAXPreProcessor(getFrameCount(), getAlphabetSize());
        List<DiscretizedData> processedTrain = mainApp.getTrainingData().getDataSet()
                .parallelStream()
                .map(ts -> new DiscretizedDataImpl(ts.getClassType(), preProcessor.discretize(ts)))
                .collect(Collectors.toList());
        Classifier classifier = new KNNClassifier(getKValue(), new LCSMeasure());
        try {
            executeClassificationTask(0, test, classifier, preProcessor, processedTrain);
        } catch (RejectedExecutionException e) {
            System.out.println("execution stopped");
        }

    }

    private void executeClassificationTask(int index, List<TimeSeries> tests, Classifier classifier, PreProcessor preProcessor, List<DiscretizedData> train)  {
        if (index < tests.size()) {
            Task<ClassificationResult> task = new ClassificationTask(this, classifier, preProcessor, tests.get(index), train);
            task.setOnSucceeded(e -> {
                statusLabel.setText("Status: Classifying test sample " + (index + 1) + " / " + tests.size() );
                resultTextArea.appendText(task.getValue().toString());
                classificationResults.add(task.getValue());
                progress.set((double)index / (double)tests.size());
                executeClassificationTask(index + 1, tests, classifier, preProcessor, train);
            });
            if (executor.isShutdown()) {
                executeClassificationTask(tests.size(), tests, classifier, preProcessor, train);
            } else {
                executor.execute(task);
            }
        } else {
            handleStop();
        }
    }

    public void showStatistics(ObservableList<TimeSeries> selectedItems) {
        testLineChart.getData().clear();
        if (selectedItems != null) {
            selectedItems.forEach(ts -> {assert ts != null;});
            for(TimeSeries ts : selectedItems) {
                XYChart.Series<Integer, Double> series = new XYChart.Series<>();
                series.setName(mainApp.getTestData().getDataSet().indexOf(ts) + " - Class: " + (ts.getClassType().isPresent() ? ts.getClassType().get() : "Unlabelled"));
                for (int i = 0; i < ts.getDataSize(); i++) {
                    series.getData().add(new XYChart.Data<>(i, ts.getData().get(i)));
                }
                testLineChart.getData().add(series);
                testLineChart.setCreateSymbols(false);
            }

            List<Optional<Integer>> classLabels = selectedItems.parallelStream()
                    .map(TimeSeries::getClassType)
                    .distinct().collect(Collectors.toList());
            testClassLabel.setText(classLabels.size() == 1 ? (classLabels.get(0).isPresent() ? classLabels.get(0).get().toString() : "Unlabelled")  : "-");

            List<Long> dataLengths = selectedItems.parallelStream()
                    .mapToLong(TimeSeries::getDataSize)
                    .distinct().boxed().collect(Collectors.toList());
            testLength.setText(dataLengths.size() == 1 ? dataLengths.get(0).toString() : "-");

            List<Boolean> normalized = selectedItems.parallelStream()
                    .map(ts -> isZNormalized(ts.getData()))
                    .distinct().collect(Collectors.toList());
            testNormalized.setText(normalized.size() == 1 ? (normalized.get(0) ? "YES" : "NO") : "-");

            List<Double> minValues = selectedItems.parallelStream()
                    .mapToDouble(TimeSeries::getMin)
                    .distinct().boxed().collect(Collectors.toList());
            List<Double> maxValues = selectedItems.parallelStream()
                    .mapToDouble(TimeSeries::getMax)
                    .distinct().boxed().collect(Collectors.toList());
            testValueRange.setText((minValues.size() == 1 ? minValues.get(0).toString() : "min")
                    + " - " + (maxValues.size() == 1 ? maxValues.get(0).toString() : "max"));

            List<Double> variances = selectedItems.parallelStream()
                    .mapToDouble(ts -> to5SF(getStandardDeviation(ts.getData())))
                    .distinct().boxed().collect(Collectors.toList());
            testSD.setText(variances.size() == 1 ? variances.get(0).toString() : "-");

            List<Double> avgs = selectedItems.parallelStream()
                    .mapToDouble(ts -> to5SF(getMean(ts.getData())))
                    .map(m -> Math.abs(m - 0) < 0.00001 ? 0.0 : m)
                    .distinct().boxed().collect(Collectors.toList());
            testMean.setText(avgs.size() == 1 ? avgs.get(0).toString() : "-");
        } else {
            testClassLabel.setText("");
            testLength.setText("");
            testNormalized.setText("");
            testValueRange.setText("");
            testSD.setText("");
            testMean.setText("");
        }
    }

    public void setTestData(File testFile) {
        TestFileReader testData = new TestFileReaderImpl(testFile.getAbsolutePath());
        mainApp.setTestData(testData);
        ControllerUtils.checkClassLabels(mainApp.getTrainingData(), mainApp.getTestData());
        currentTestData.clear();
        currentTestData.addAll(testData.getDataSet());
        setDefaultSettings(mainApp.getTrainingData(), mainApp.getTestData());
    }

    public void setDefaultSettings(TrainingFileReader train, TestFileReader test) {
        if (currentTestData.isEmpty()) {
            setClassifierParams(0, 0, 0);
        } else {
            setClassifierParams(ControllerUtils.getDefaultKValue(train), ControllerUtils.getDefaultFrameCount(train, test), ControllerUtils.getDefaultAlphabetSize());
        }
    }

    public void setClassifierParams(int kValue, int frameCount, int alphabetSize) {
        this.kValue.setValue(kValue);
        this.frameCount.setValue(frameCount);
        this.alphabetSize.setValue(alphabetSize);
    }

    public int getKValue() {
        return kValue.getValue();
    }

    public int getFrameCount() {
        return frameCount.getValue();
    }

    public int getAlphabetSize() {
        return alphabetSize.getValue();
    }

    public ObservableList<TimeSeries> getCurrentTestData() {
        return currentTestData;
    }

    public MainApp getMainApp() {
        return mainApp;
    }

}
