package classificationApp.view.controllers;

import classificationApp.MainApp;
import classificationApp.model.data.DiscretizedData;
import classificationApp.model.data.TimeSeries;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Controller class for the animation mode of the application.
 * Created by George Shiangoli on 17/08/2016.
 */
public class AnimationRootController {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label title;

    @FXML
    private TextArea description;

    @FXML
    private Button startAgain;

    @FXML
    private Button exit;

    @FXML
    private ImageView testSelectionIcon;

    @FXML
    private ImageView arrow1;

    @FXML
    private ImageView rawDataIcon;

    @FXML
    private ImageView arrow2;

    @FXML
    private ImageView normalizedDataIcon;

    @FXML
    private ImageView arrow3;

    @FXML
    private ImageView partitionIcon;

    @FXML
    private ImageView arrow4;

    @FXML
    private ImageView meanIcon;

    @FXML
    private ImageView arrow5;

    @FXML
    private ImageView gaussianIcon;

    @FXML
    private ImageView arrow6;

    @FXML
    private ImageView lcsIcon;

    @FXML
    private ImageView knnIcon;

    private MainApp mainApp;

    @FXML
    private void initialize() {
        title.setText("1. Test Data Selection");
        description.setText("Select a data sample from a chosen file and click start to begin an interactive walk-through" +
                " of the classification process used within this model.");
    }

    @FXML
    private void handleStartAgainButtonPress() {
        mainApp.showAnimationLayout();
        startAgain.setDisable(true);
    }

    @FXML
    private void handleExitButtonPress() {
        mainApp.closeApplication();
    }

    public void showDataVisualisation(TimeSeries dataSample) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DataVisualisation.fxml"));
            AnchorPane dataVisualisationLayout = loader.load();
            gridPane.getChildren().remove(5);
            gridPane.add(dataVisualisationLayout, 1, 1);
            GridPane.setColumnSpan(dataVisualisationLayout, GridPane.REMAINING);
            DataVisualisationController visController = loader.getController();
            visController.setRootController(this);
            visController.setTestSample(dataSample);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLCSVisualisation(TimeSeries testSample, String word, int alphabetSize) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LCSVisualisation.fxml"));
            AnchorPane lcsLayout = loader.load();
            gridPane.getChildren().remove(5);
            gridPane.add(lcsLayout, 1, 1);
            GridPane.setColumnSpan(lcsLayout, GridPane.REMAINING);
            LCSVisualisationController controller = loader.getController();
            controller.setRootController(this);
            controller.setTestSample(testSample);
            controller.setTestWord(word);
            controller.setAlphabetSize(alphabetSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showKNNVisualisation(DiscretizedData testSample, List<DiscretizedData> trainSamples) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/KNNVisualisation.fxml"));
            AnchorPane knnLayout = loader.load();
            gridPane.getChildren().remove(5);
            gridPane.add(knnLayout, 1, 1);
            GridPane.setColumnSpan(knnLayout, GridPane.REMAINING);
            KNNVisualisationController controller = loader.getController();
            controller.setRootController(this);
            controller.setTest(testSample);
            controller.setTrain(trainSamples);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TestDataSelection.fxml"));
            AnchorPane testSelectView = loader.load();
            gridPane.add(testSelectView, 1, 1);
            GridPane.setColumnSpan(testSelectView, GridPane.REMAINING);
            TestDataSelectionController testController = loader.getController();
            testController.setRootController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setImageOpacity(String imageName, double opacity) {
        try {
            Field field = this.getClass().getDeclaredField(imageName);
            ImageView image = (ImageView)field.get(this);
            image.setOpacity(opacity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Button getStartAgainButton() {
        return startAgain;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }
}
