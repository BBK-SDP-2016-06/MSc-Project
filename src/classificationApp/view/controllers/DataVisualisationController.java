package classificationApp.view.controllers;

import classificationApp.model.data.TimeSeries;
import classificationApp.model.data.TimeSeriesImpl;
import classificationApp.model.preprocessing.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;

import java.lang.reflect.Field;
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

    private List<XYChart.Series<Double, Double>> xPartitionLines;
    private List<XYChart.Series<Double, Double>> yPartitionLines;

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
        xPartitionLines = new ArrayList<>();
        yPartitionLines = new ArrayList<>();
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
            graph.getData().forEach(data -> data.getNode().getStyleClass().add("normalLines"));
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
        graph.getData().get(0).getNode().getStyleClass().add("normalLines");
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
            graph.getData().forEach(data -> data.getNode().getStyleClass().add("normalLines"));
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

        Slider partitionSlider = new Slider(1, (int)testSample.getDataSize() > 50 ? 50 : (int)testSample.getDataSize(), 1);
        partitionSlider.setMaxWidth(Double.MAX_VALUE);
        partitionSlider.setMajorTickUnit(1);
        partitionSlider.setMinorTickCount(0);
        partitionSlider.setSnapToTicks(true);

        partitionSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            partitionValue.setText(String.valueOf(newValue.intValue()));
            double frameSize = (double)testSample.getDataSize() / newValue.doubleValue();
            if (!xPartitionLines.isEmpty()) graph.getData().removeAll(xPartitionLines);
            xPartitionLines.clear();
            double minValue = normalizedData.getMin();
            double maxValue = normalizedData.getMax();
            for (int i = 0; i < newValue.doubleValue(); i++) {
                XYChart.Data<Double, Double> minPoint = new XYChart.Data<>(i * frameSize, minValue);
                XYChart.Data<Double, Double> maxPoint = new XYChart.Data<>(i * frameSize, maxValue);
                XYChart.Series<Double, Double> partitionLine = new XYChart.Series<>();
                partitionLine.getData().addAll(minPoint, maxPoint);
                xPartitionLines.add(partitionLine);
            }
            graph.getData().addAll(xPartitionLines);
            graph.getData().parallelStream()
                    .skip(1)
                    .forEach(series -> series.getNode().getStyleClass().add("partitionLines"));
            graph.getData().get(0).getNode().getStyleClass().add("normalLines");
        });

        HBox hbox = new HBox(10, partitionLabel, partitionSlider, partitionValue);
        HBox.setHgrow(partitionSlider, Priority.ALWAYS);
        HBox.setHgrow(partitionValue, Priority.NEVER);
        HBox.setHgrow(partitionLabel, Priority.NEVER);
        hbox.setMaxWidth(Double.MAX_VALUE);
        hbox.setPadding(new Insets(0, 10, 0, 10));

        Button reduceDataButton = new Button("Reduce Data");
        reduceDataButton.setPrefWidth(200);
        reduceDataButton.setOnAction(e -> showCalculatedMeanLayout(Integer.parseInt(partitionValue.getText())));

        VBox partitionLayout = new VBox(5, hbox, reduceDataButton);
        partitionLayout.setAlignment(Pos.CENTER);

        gridPane.add(partitionLayout, 0, 1);
    }

    private void showCalculatedMeanLayout(int frameCount) {
        rootController.setTitle("5. Data Reduction (Pre-processing)");
        rootController.setDescription("The mean value is then computed for each partition, taking into " +
                "consideration all data values that fall within the allotted frame.");
        rootController.setImageOpacity("arrow4", 1.0);
        rootController.setImageOpacity("meanIcon", 1.0);

        DataApproximator approximator = new PiecewiseAggregateApproximator(frameCount);
        List<Double> reducedData = approximator.reduce(normalizedData.getData());
        List<XYChart.Series<Double, Double>> pAALines = new ArrayList<>();
        double frameWidth = (double)testSample.getDataSize() / (double)frameCount;

        for (int i = 0; i < reducedData.size(); i++) {
            XYChart.Series<Double, Double> pAALine = new XYChart.Series<>();
            XYChart.Data<Double, Double> firstValue = new XYChart.Data<>(i * frameWidth, reducedData.get(i));
            XYChart.Data<Double, Double> secondValue = new XYChart.Data<>((i + 1) * frameWidth, reducedData.get(i));
            pAALine.getData().addAll(firstValue, secondValue);
            pAALines.add(pAALine);
        }

        graph.setAnimated(true);
        graph.getData().addAll(pAALines);
        graph.getData().get(0).getNode().getStyleClass().add("backgroundLines");
        graph.getData().parallelStream().skip(1).limit(frameCount).forEach(data -> data.getNode().getStyleClass().add("partitionLines"));
        graph.getData().parallelStream().skip(1 + frameCount).forEach(data -> data.getNode().getStyleClass().add("meanLines"));

        Button button = new Button("Gaussian Split");
        button.setOnAction(e -> showGaussianSplit(reducedData, frameWidth));

        gridPane.getChildren().remove(1);
        gridPane.add(button, 0, 1);
    }

    private void showGaussianSplit(List<Double> reducedData, double frameWidth) {
        rootController.setTitle("6. Word Generation (Pre-processing)");
        rootController.setDescription("A Gaussian distribution is then used to calculate the symbolic " +
                "approximation of each segment of the new graph. The y-axis is split according to equal probabilities beneath " +
                "the Gaussian distribution and then each segment is assigned a letter depending on which bracket the value falls " +
                "within. The letters are then concatenated to form the resulting SAX approximation of the test sample.");
        rootController.setImageOpacity("arrow5", 1.0);
        rootController.setImageOpacity("gaussianIcon", 1.0);

        XYChart.Series<Double, Double> approximationLine = new XYChart.Series<>();
        for (int i = 0; i < reducedData.size(); i++) {
            XYChart.Data<Double, Double> firstValue = new XYChart.Data<>(i * frameWidth, reducedData.get(i));
            XYChart.Data<Double, Double> secondValue = new XYChart.Data<>((i + 1) * frameWidth, reducedData.get(i));
            approximationLine.getData().addAll(firstValue, secondValue);
        }

        graph.getData().removeIf(data -> graph.getData().indexOf(data) != 0);
        graph.getData().add(approximationLine);
        graph.getData().get(0).getNode().getStyleClass().add("backgroundLines");
        graph.getData().get(1).getNode().getStyleClass().add("meanLines");

        Label alphabetLabel = new Label("Alphabet Size:");
        Label alphabetValue = new Label("2");

        Slider alphabetSlider = new Slider(2, 26, 1);
        alphabetSlider.setMaxWidth(Double.MAX_VALUE);
        alphabetSlider.setMajorTickUnit(1);
        alphabetSlider.setMinorTickCount(0);
        alphabetSlider.setSnapToTicks(true);
        alphabetSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                alphabetValue.setText(String.valueOf(newValue.intValue()));
                DataConverter converter = new GaussianSplitConverter(newValue.intValue());
                Field field = converter.getClass().getDeclaredField("breakPoints");
                field.setAccessible(true);
                List<Double> breakPoints = (List<Double>)field.get(converter);

                if (!yPartitionLines.isEmpty()) graph.getData().removeAll(yPartitionLines);
                yPartitionLines.clear();

                double extent = testSample.getDataSize() + 1;

                for (Double breakPoint : breakPoints) {
                    XYChart.Data<Double, Double> firstPoint = new XYChart.Data<>(0.0, breakPoint);
                    XYChart.Data<Double, Double> lastPoint = new XYChart.Data<>(extent, breakPoint);
                    XYChart.Series<Double, Double> yPartitionLine = new XYChart.Series<>();
                    yPartitionLine.getData().addAll(firstPoint, lastPoint);
                    yPartitionLines.add(yPartitionLine);
                }
                graph.getData().addAll(yPartitionLines);
                graph.getData().get(0).getNode().getStyleClass().add("backgroundLines");
                graph.getData().get(1).getNode().getStyleClass().add("meanLines");
                graph.getData().parallelStream()
                        .skip(2)
                        .forEach(series -> series.getNode().getStyleClass().add("alphabetLines"));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        HBox hbox = new HBox(10, alphabetLabel, alphabetSlider, alphabetValue);
        HBox.setHgrow(alphabetSlider, Priority.ALWAYS);
        HBox.setHgrow(alphabetValue, Priority.NEVER);
        HBox.setHgrow(alphabetLabel, Priority.NEVER);
        hbox.setMaxWidth(Double.MAX_VALUE);
        hbox.setPadding(new Insets(0, 10, 0, 10));

        Button generateWordButton = new Button("Generate Word");
        generateWordButton.setPrefWidth(200);
        generateWordButton.setOnAction(e -> showWord(Integer.parseInt(alphabetValue.getText()), reducedData));

        VBox gaussianLayout = new VBox(5, hbox, generateWordButton);
        gaussianLayout.setAlignment(Pos.CENTER);

        gridPane.getChildren().remove(1);
        gridPane.add(gaussianLayout, 0, 1);
    }

    private void showWord(int alphabetSize, List<Double> reducedData) {
        DataConverter converter = new GaussianSplitConverter(alphabetSize);
        String word = converter.toWord(reducedData);

        Button nextButton = new Button("Next");
        nextButton.setPrefWidth(200);
        nextButton.setOnAction(e -> rootController.showLCSVisualisation(testSample, word, alphabetSize));

        Label wordLabel = new Label("Resulting word approximation: " + word);

        VBox wordResultLayout = new VBox(5, wordLabel, nextButton);
        wordResultLayout.setAlignment(Pos.CENTER);

        gridPane.getChildren().remove(1);
        gridPane.add(wordResultLayout, 0, 1);
        StackPane stack = new StackPane(gridPane.getChildren().remove(0));
        gridPane.add(stack, 0, 0);

        showChartSymbols(stack, word);

        rootController.getMainApp().getMainStage().getScene().heightProperty().addListener((observable, oldValue, newValue) -> {
            stack.getChildren().remove(1);
            showChartSymbols(stack, word);
        });

        rootController.getMainApp().getMainStage().getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
            stack.getChildren().remove(1);
            showChartSymbols(stack, word);
        });
    }

    private AnchorPane generateChartSymbols(String word) {
        AnchorPane symbolLayer = new AnchorPane();
        Node chartPlotArea = graph.lookup(".chart-plot-background");
        double chartZeroX = chartPlotArea.getLayoutX();
        double chartZeroY = chartPlotArea.getLayoutY();

        for (int i = 0; i < word.length(); i++) {
            Label symbol = new Label(word.charAt(i) + "");
            symbolLayer.getChildren().add(symbol);

            XYChart.Data<Double, Double> dataItem = graph.getData().get(1).getData().get(i * 2);
            XYChart.Data<Double, Double> nextDataItem = graph.getData().get(1).getData().get((i * 2) + 1);

            AnchorPane.setLeftAnchor(symbol, ((graph.getXAxis().getDisplayPosition(dataItem.getXValue()) + chartZeroX) +
                    (graph.getXAxis().getDisplayPosition(nextDataItem.getXValue()) + chartZeroX)) / 2.0);

            AnchorPane.setTopAnchor(symbol, graph.getYAxis().getDisplayPosition(dataItem.getYValue()) + chartZeroY - 15.0);
        }
        return symbolLayer;
    }

    private void showChartSymbols(StackPane stack, String word) {
        AnchorPane symbolLayer1 = generateChartSymbols(word);
        stack.getChildren().add(symbolLayer1);
        symbolLayer1.getChildren().parallelStream().forEach(label -> label.getStyleClass().add("symbolAlphabet"));
    }
}
