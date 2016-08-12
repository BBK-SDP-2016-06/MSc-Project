package classificationApp.view;

import classificationApp.model.classification.ClassificationResult;
import classificationApp.model.classification.NeighbourDistance;
import classificationApp.model.preprocessing.MathUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller class for the result statistics window.
 * Created by George Shiangoli on 11/08/2016.
 */
public class ResultStatisticsController {

    @FXML
    private TableView<ClassificationResult> fullResultTable;

    @FXML
    private TableColumn<ClassificationResult, Integer> indexRef;

    @FXML
    private TableColumn<ClassificationResult, Integer> actualClass;

    @FXML
    private TableColumn<ClassificationResult, Integer> predictedClass;

    @FXML
    private TableView<Map.Entry<Integer, List<ClassificationResult>>> classErrorTable;

    @FXML
    private TableColumn<Map.Entry<Integer, List<ClassificationResult>>, Integer> classColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, List<ClassificationResult>>, Integer> samplesColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, List<ClassificationResult>>, Integer> errorsColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, List<ClassificationResult>>, Double> errorRateColumn;

    @FXML
    private Label overallResultLabel;

    @FXML
    private TextArea kNNResultTextArea;

    @FXML
    private Button saveButton;

    @FXML
    private Button closeButton;

    private ObservableList<ClassificationResult> classificationResults;
    private ObservableMap<Integer, List<ClassificationResult>> classErrorResults;
    private Stage stage;

    @FXML
    private void initialize() {
        classificationResults = FXCollections.observableArrayList();
        classificationResults.addListener((ListChangeListener<ClassificationResult>) c -> fullResultTable.setItems(classificationResults));

        classErrorResults = FXCollections.observableHashMap();
        classErrorResults.addListener((MapChangeListener<Integer, List<ClassificationResult>>) change -> {
            ObservableList<Map.Entry<Integer, List<ClassificationResult>>> entryList = FXCollections.observableArrayList();
            entryList.addAll(classErrorResults.entrySet().stream().collect(Collectors.toList()));
            classErrorTable.setItems(entryList);
        });

        fullResultTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Rank\t\tClass\tSimilarity");
            for (NeighbourDistance nd : newValue.getNeighbourDistances()) {
                sb.append("\n")
                        .append(newValue.getNeighbourDistances().indexOf(nd) + 1)
                        .append("\t\t")
                        .append(nd.getClassType())
                        .append("\t\t")
                        .append(nd.getDistance());
            }
            kNNResultTextArea.setText(sb.toString());
        });

        PseudoClass correctPseudoClass = PseudoClass.getPseudoClass("correct");
        PseudoClass wrongPseudoClass = PseudoClass.getPseudoClass("wrong");
        PseudoClass allPseudoClass = PseudoClass.getPseudoClass("all");

        fullResultTable.setRowFactory(table -> new TableRow<ClassificationResult>() {
            @Override
            public void updateItem(ClassificationResult item, boolean empty) {
                super.updateItem(item, empty);
                pseudoClassStateChanged(correctPseudoClass, (!empty) && item.isCorrectPred());
                pseudoClassStateChanged(wrongPseudoClass, (!empty) && !item.isCorrectPred());
                pseudoClassStateChanged(allPseudoClass, true);
            }
        });

        indexRef.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getIndex()));
        actualClass.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getActClass()));
        predictedClass.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getPredClass()));

        classColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getKey()));
        samplesColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().size()));
        errorsColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>((int)param.getValue().getValue().parallelStream().filter(r -> !r.isCorrectPred()).count()));
        errorRateColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(MathUtils.to5SF(
                (double)param.getValue().getValue().parallelStream().filter(r -> !r.isCorrectPred()).count()/(double)param.getValue().getValue().size())));
    }

    public void setClassificationResults(ObservableList<ClassificationResult> classificationResults) {
        this.classificationResults.addAll(classificationResults.parallelStream()
                .filter(r -> !(r.getActClass() == -1))
                .sorted((r1, r2) -> r1.getIndex() - r2.getIndex())
                .collect(Collectors.toList()));
        this.classErrorResults.putAll(classificationResults.parallelStream()
                .filter(r -> !(r.getActClass() == -1))
                .collect(Collectors.groupingBy(ClassificationResult::getActClass)));
        long errors = this.classificationResults.parallelStream().filter(r -> !r.isCorrectPred()).count();
        overallResultLabel.setText("Overall error rate: " + errors + " / " + this.classificationResults.size()
                + " = " + (this.classificationResults.isEmpty() ? 0.0 : MathUtils.to5SF((double)errors / (double)this.classificationResults.size())));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleSaveButton() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt"));
        File file = fc.showSaveDialog(stage);
        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file);
                for(ClassificationResult result : classificationResults) {
                    writer.write(result.toString());
                }
                writer.write(overallResultLabel.getText());
                writer.close();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCloseButton() {
        stage.close();
    }
}
