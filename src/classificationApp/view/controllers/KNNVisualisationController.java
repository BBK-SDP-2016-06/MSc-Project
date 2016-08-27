package classificationApp.view.controllers;

import classificationApp.model.classification.*;
import classificationApp.model.data.DiscretizedData;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for the KNN Visualisation aspect of the interactive
 * walk-through mode of the application.
 * Created by George Shiangoli on 21/08/2016.
 */
public class KNNVisualisationController {

    private DiscretizedData test;
    private IntegerProperty kValue;
    private List<DiscretizedData> train;
    private ObservableList<NeighbourDistance> sortedDistances;

    @FXML
    private TableView<NeighbourDistance> rankTable;

    @FXML
    private TableColumn<NeighbourDistance, Integer> rankColumn;

    @FXML
    private TableColumn<NeighbourDistance, Integer> fileIndexColumn;

    @FXML
    private TableColumn<NeighbourDistance, Integer> classColumn;

    @FXML
    private TableColumn<NeighbourDistance, Integer> similarityColumn;

    @FXML
    private Slider kValueSlider;

    @FXML
    private Label kValueIndicator;

    @FXML
    private Label classLabel;

    @FXML
    private Label predictedLabel;

    @FXML
    private Label verdictLabel;

    @FXML
    private Label distancesConsideredKeyLabel;

    @FXML
    private Label excludedDistancesKeyLabel;

    @FXML
    private void initialize() {
        kValue = new SimpleIntegerProperty(1);
        kValue.bind(kValueSlider.valueProperty());
        kValue.addListener((observable, oldValue, newValue) -> {
            kValueIndicator.setText(newValue.toString());
            updateResult();
        });

        sortedDistances = FXCollections.observableArrayList();
        sortedDistances.addListener((ListChangeListener<NeighbourDistance>) c -> rankTable.setItems(sortedDistances));

        distancesConsideredKeyLabel.getStyleClass().add("includedKey");
        excludedDistancesKeyLabel.getStyleClass().add("excludedKey");
    }


    public void setRootController(AnimationRootController rootController) {
        rootController.setTitle("8. kNN Classification");
        rootController.setDescription("The final step involves using the k - nearest " +
                "neighbour algorithm to determine a predicted class label for the test sample, using " +
                "the Longest Common Subsequence similarity measure to rank the training samples.");
        rootController.setImageOpacity("arrow7", 1.0);
        rootController.setImageOpacity("knnIcon", 1.0);
    }

    public void setTest(DiscretizedData test) {
        this.test = test;
    }

    public void setTrain(List<DiscretizedData> train) {
        this.train = train;
        kValueSlider.setMax(train.size());
        showResultTableContent();
        updateResult();
    }

    private void showResultTableContent() {
        List<NeighbourDistance> allDistances = train.stream()
                .map(data -> new NeighbourDistance(data.getClassType().get(), new LCSMeasure().getSimilarityFactor(test.getWord(), data.getWord())))
                .collect(Collectors.toList());
        List<NeighbourDistance> sorted = allDistances.parallelStream()
                .sorted((d1, d2) -> d2.getDistance() - d1.getDistance())
                .collect(Collectors.toList());

        rankColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(sortedDistances.indexOf(param.getValue())));
        fileIndexColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(allDistances.indexOf(param.getValue())));
        classColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getClassType()));
        similarityColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getDistance()));

        sortedDistances.setAll(sorted);
    }

    private void updateResult() {
        PseudoClass includedPseudoClass = PseudoClass.getPseudoClass("included");
        PseudoClass excludedPseudoClass = PseudoClass.getPseudoClass("excluded");

        Classifier kNNClassifier = new KNNClassifier(kValue.get(), new LCSMeasure());
        ClassificationResult result = kNNClassifier.classify(test, train);

        classLabel.setText(String.valueOf(result.getActClass().isPresent() ? result.getActClass().get() : "Unlabelled"));
        classLabel.getStyleClass().add("classLabels");

        predictedLabel.setText(String.valueOf(result.getPredictedClass()));
        predictedLabel.getStyleClass().add("classLabels");

        verdictLabel.setText(classLabel.getText().equals("Unlabelled") ? "N/A" : (result.isCorrectPrediction() ? "CORRECT" : "INCORRECT"));

        if (verdictLabel.getText().equals("CORRECT")) {
            verdictLabel.getStyleClass().setAll("correct");
        } else if (verdictLabel.getText().equals("INCORRECT")) {
            verdictLabel.getStyleClass().setAll("incorrect");
        } else {
            verdictLabel.getStyleClass().clear();
        }

        rankTable.setRowFactory(table -> new TableRow<NeighbourDistance>() {
            @Override
            public void updateItem(NeighbourDistance item, boolean empty) {
                super.updateItem(item, empty);
                pseudoClassStateChanged(includedPseudoClass, (!empty) && sortedDistances.indexOf(item) < kValue.get());
                pseudoClassStateChanged(excludedPseudoClass, (!empty) && sortedDistances.indexOf(item) >= kValue.get());
            }
        });
    }
}
