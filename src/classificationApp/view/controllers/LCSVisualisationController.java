package classificationApp.view.controllers;

import classificationApp.model.classification.LCSMeasure;
import classificationApp.model.data.DiscretizedData;
import classificationApp.model.data.DiscretizedDataImpl;
import classificationApp.model.data.TimeSeries;
import classificationApp.model.preprocessing.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for the LCS Visualisation layout of the animation mode
 * part of the application.
 * Created by George Shiangoli on 21/08/2016.
 */
public class LCSVisualisationController {

    private AnimationRootController rootController;
    private TimeSeries testSample;
    private String testWord;
    private int alphabetSize;
    private ObservableList<TimeSeries> trainSamples;
    private LCSMeasure lcs;
    private List<DiscretizedData> trainWords;

    @FXML
    private TableView<TimeSeries> preprocessedTrainTable;

    @FXML
    private Label preprocessedTestLabel;

    @FXML
    private Button nextButton;

    @FXML
    private TableColumn<TimeSeries, Integer> indexColumn;

    @FXML
    private TableColumn<TimeSeries, String> wordColumn;

    @FXML
    private TableColumn<TimeSeries, Integer> similarityColumn;

    @FXML
    private GridPane layout;

    private GridPane LCSMatrix;

    @FXML
    private void initialize() {
        lcs = new LCSMeasure();
        preprocessedTestLabel.getStyleClass().add("testResultText");
        trainSamples = FXCollections.observableArrayList();
        trainSamples.addListener((ListChangeListener<TimeSeries>) c -> preprocessedTrainTable.setItems(trainSamples));
        preprocessedTrainTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            LCSMatrix = new GridPane();
            LCSMatrix.setHgap(5);
            LCSMatrix.setVgap(5);

            String trainWord = wordColumn.getCellData(newValue);
            lcs.getSimilarityFactor(testWord, trainWord);
            int[][] numberMatrix = lcs.getMatrix();

            for (int i = 0; i < testWord.length(); i++) {
                Label testSymbol = new Label(String.valueOf(testWord.charAt(i)));
                LCSMatrix.add(testSymbol, i + 2, 0);
            }

            for (int i = 0; i < trainWord.length(); i++) {
                Label trainSymbol = new Label(String.valueOf(wordColumn.getCellData(newValue).charAt(i)));
                LCSMatrix.add(trainSymbol, 0, i + 2);
            }

            for(int i = 0; i <= testWord.length(); i++) {
                for (int j = 0; j <= trainWord.length(); j++) {
                    LCSMatrix.add(new Label(String.valueOf(numberMatrix[i][j])), i + 1, j + 1);
                }
            }

            if (layout.getChildren().size() == 9) layout.getChildren().remove(8);
            ScrollPane scrollPane = new ScrollPane(LCSMatrix);
            layout.add(scrollPane, 2, 1);
            GridPane.setRowSpan(scrollPane, 4);
        });

    }

    @FXML
    private void handleNextButton() {
        DiscretizedData test = new DiscretizedDataImpl(testSample.getClassType(), testWord);
        this.rootController.showKNNVisualisation(test, trainWords);
    }


    public void setRootController(AnimationRootController rootController) {
        this.rootController = rootController;
        this.rootController.setTitle("7. Longest Common Subsequence (Similarity Measure)");
        this.rootController.setDescription("The entire pre-processing for the test sample is " +
                "complete with the resulting word displayed in green. The SAX discretization algorithm " +
                "is also performed on all samples of training data, with these results displayed in the table. " +
                "Once the respective pre-processing is completed, the test word is compared to all train words using " +
                "the longest common subsequence algorithm as a similarity measure. Click on rows of the table to show " +
                "the calculated matrix on completion of the LCS algorithm in each instance.");
        this.rootController.setImageOpacity("arrow6", 1.0);
        this.rootController.setImageOpacity("lcsIcon", 1.0);
    }

    public void setTestSample(TimeSeries testSample) {
        this.testSample = testSample;
    }

    public void setTestWord(String testWord) {
        this.testWord = testWord;
        preprocessedTestLabel.setText(testWord);
    }

    public void setAlphabetSize(int alphabetSize) {
        this.alphabetSize = alphabetSize;
        populatePreProcessedTrainTable();
    }

    private void populatePreProcessedTrainTable() {
        PreProcessor sax = new SAXPreProcessor(testWord.length(), alphabetSize);

        trainWords = rootController.getMainApp().getTrainingData().getDataSet()
                .parallelStream()
                .map(data -> new DiscretizedDataImpl(data.getClassType(), sax.discretize(data)))
                .collect(Collectors.toList());

        List<Integer> similarityMeasures = trainWords.stream()
                .map(w -> lcs.getSimilarityFactor(testWord, w.getWord()))
                .collect(Collectors.toList());

        indexColumn.setCellValueFactory(param ->
                new ReadOnlyObjectWrapper<>(rootController.getMainApp().getTrainingData().getDataSet().indexOf(param.getValue())));
        wordColumn.setCellValueFactory(param ->
                new ReadOnlyObjectWrapper<>(trainWords.get(rootController.getMainApp().getTrainingData().getDataSet().indexOf(param.getValue())).getWord()));
        similarityColumn.setCellValueFactory(param ->
                new ReadOnlyObjectWrapper<>(similarityMeasures.get(rootController.getMainApp().getTrainingData().getDataSet().indexOf(param.getValue()))));

        trainSamples.setAll(rootController.getMainApp().getTrainingData().getDataSet());
    }
}
