package classificationApp.view.controllers;

import classificationApp.model.data.TimeSeries;
import classificationApp.model.data.TimeSeriesImpl;
import classificationApp.model.preprocessing.PreProcessor;
import classificationApp.model.preprocessing.SAXPreProcessor;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static classificationApp.model.math.MathUtils.*;

/**
 * Controller class for the Data Visualisation view of the animation mode
 * of the application.
 * Created by George Shiangoli on 18/08/2016.
 */
public class DataVisualisationController {

    private AnimationRootController rootController;
    private TimeSeries testSample;
    private TimeSeries normalizedData;
    private XYChart.Series<Double, Double> rawDataSeries;
    private XYChart.Series<Double, Double> normalizedDataSeries;

    private List<XYChart.Series<Double, Double>> partitionLines;

    @FXML
    private LineChart<Double, Double> graph;

    @FXML
    private Button normalizeButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private void initialize() {
        rawDataSeries = new XYChart.Series<>();
        rawDataSeries.setName("Raw data");
        normalizedDataSeries = new XYChart.Series<>();
        normalizedDataSeries.setName("Normalized data");
        graph.setCreateSymbols(false);
        partitionLines = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    @FXML
    private void handleNormalizeButton() {
        rootController.setImageOpacity("arrow2", 1.0);
        rootController.setImageOpacity("normalizedDataIcon", 1.0);
        rootController.setTitle("3. Normalized Data (Pre-processing)");
        rootController.setDescription("As part of the pre-processing stage, the raw data is z-normalised. " +
                "This means that the graph is transformed so that the data now has an average of 0 and variance " +
                "of 1. This is a common step to ensure the structural characteristics of time series are analysed " +
                "by removing amplitude, offset and frequency variations within the data.");
        try {
            Method m = PreProcessor.class.getDeclaredMethod("normalizeData", List.class);
            m.setAccessible(true);
            List<Double> normalizedValues = (List<Double>)m.invoke(new SAXPreProcessor(1, 2), testSample.getData());
            normalizedData = new TimeSeriesImpl(testSample.getClassType(), normalizedValues);
            for (int i = 0; i < normalizedData.getDataSize(); i++) {
                normalizedDataSeries.getData().add(new XYChart.Data<>((double)i, normalizedData.getData().get(i)));
            }
            graph.getData().setAll(normalizedDataSeries);
            showNormalizedDataLayout();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setRootController(AnimationRootController rootController) {
        this.rootController = rootController;
        this.rootController.setImageOpacity("arrow1", 1.0);
        this.rootController.setImageOpacity("rawDataIcon", 1.0);
        this.rootController.getStartAgainButton().setDisable(false);
        this.rootController.setTitle("2. Raw Data Representation");
    }

    public void setTestSample(TimeSeries testSample) {
        this.testSample = testSample;
        for(int i = 0; i < testSample.getDataSize(); i++) {
            rawDataSeries.getData().add(new XYChart.Data<>((double)i, testSample.getData().get(i)));
        }
        graph.getData().add(rawDataSeries);
        String sb = "A graphical representation of the selected test sample " +
                "in its raw format prior to preprocessing." + "\n\nData Statistics:" +
                "\nActual Class Label: " + testSample.getClassType() +
                "\nLength: " + testSample.getDataSize() +
                "\nValue Range: " + testSample.getMin() + " - " + testSample.getMax() +
                "\nMean: " + to5SF(getMean(testSample.getData())) +
                "\nVariance: " + to5SF(getStandardDeviation(testSample.getData()));
        this.rootController.setDescription(sb);
    }

    private void showNormalizedDataLayout() {
        CheckBox showRawCheckBox = new CheckBox("Show Raw Data");
        showRawCheckBox.setSelected(false);
        showRawCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (showRawCheckBox.isSelected()) {
                graph.getData().add(rawDataSeries);
            } else {
                graph.getData().remove(1);
            }
        });
        Button partitionButton = new Button("Partition");
        partitionButton.setPrefWidth(200);
        partitionButton.setOnAction(e -> showPartitionLayout());

        VBox normalizedLayout = new VBox(5, showRawCheckBox, partitionButton);
        normalizedLayout.setAlignment(Pos.CENTER);

        gridPane.getChildren().remove(1);
        gridPane.add(normalizedLayout, 0, 1);
    }

    @SuppressWarnings("unchecked")
    private void showPartitionLayout() {
        gridPane.getChildren().remove(1);

        graph.setLegendVisible(false);
        if (graph.getData().size() > 1) graph.getData().remove(1);
        graph.getData().forEach(series -> series.getNode().setOpacity(0.5));
        graph.setAnimated(false);

        rootController.setImageOpacity("arrow3", 1.0);
        rootController.setImageOpacity("partitionIcon", 1.0);
        rootController.setTitle("4. Data Partitioning (Pre-processing)");
        rootController.setDescription("After z-normalizing the data, a frame partition " +
                "count is specified to determine the degree of data reduction. A greater " +
                "partition count increases the accuracy of data representation, whereas a " +
                "smaller partition count reduces the influence of anomalies within the data. " +
                "Data reduction is generally required to reduce the sample size of larger data " +
                "sets, increasing the speed of various data mining tasks.");

        Label partitionLabel = new Label("Partition Count:");
        Label partitionValue = new Label("1");

        Slider partitionSlider = new Slider(1, (int)testSample.getDataSize(), 1);
        partitionSlider.setMaxWidth(Double.MAX_VALUE);
        partitionSlider.setMajorTickUnit(1);
        partitionSlider.setMinorTickCount(0);
        partitionSlider.setSnapToTicks(true);

        partitionSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            partitionValue.setText(String.valueOf(newValue.intValue()));
            double frameSize = (double)testSample.getDataSize() / newValue.doubleValue();
            if (!partitionLines.isEmpty()) graph.getData().removeAll(partitionLines);
            partitionLines.clear();
            double minValue = normalizedData.getMin();
            double maxValue = normalizedData.getMax();
            for (int i = 0; i < newValue.doubleValue(); i++) {
                XYChart.Data<Double, Double> minPoint = new XYChart.Data<>(i * frameSize, minValue);
                XYChart.Data<Double, Double> maxPoint = new XYChart.Data<>(i * frameSize, maxValue);
                XYChart.Series<Double, Double> partitionLine = new XYChart.Series<>();
                partitionLine.getData().addAll(minPoint, maxPoint);
                partitionLines.add(partitionLine);
            }
            graph.getData().addAll(partitionLines);
            graph.getData().parallelStream()
                    .skip(1)
                    .forEach(series -> series.getNode().getStyleClass().add("partitionLines"));
        });

        HBox hbox = new HBox(10, partitionLabel, partitionSlider, partitionValue);
        HBox.setHgrow(partitionSlider, Priority.ALWAYS);
        HBox.setHgrow(partitionValue, Priority.NEVER);
        HBox.setHgrow(partitionLabel, Priority.NEVER);
        hbox.setMaxWidth(Double.MAX_VALUE);
        hbox.setPadding(new Insets(0, 10, 0, 10));

        Button reduceDataButton = new Button("Reduce Data");
        reduceDataButton.setPrefWidth(200);
        reduceDataButton.setOnAction(e -> showCalculatedMeanLayout());

        VBox partitionLayout = new VBox(5, hbox, reduceDataButton);
        partitionLayout.setAlignment(Pos.CENTER);

        gridPane.add(partitionLayout, 0, 1);
    }

    private void showCalculatedMeanLayout() {

    }
}
