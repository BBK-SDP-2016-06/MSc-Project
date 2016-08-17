package gui;

import classificationApp.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.loadui.testfx.GuiTest.find;
import static org.testfx.api.FxAssert.verifyThat;


/** Testing class for the Animation Root GUI.
 * Created by George on 15/08/2016.
 */
public class AnimationRootGUITest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/AnimationRoot.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @Test
    public void checkInitialButtonState() {
        verifyThat("#prevButton", Node::isDisabled);
        verifyThat("#nextButton", n -> !n.isDisabled());
        verifyThat("#exitButton", n -> !n.isDisabled());
    }

    @Test
    public void checkAllLabelsInitiallyPresent() {
        find("#rawDataRepresentation");
        find("#normalization");
        find("#pAA");
        find("#discretization");
        find("#similarityMeasure");
        find("#kNNClassification");
        find("#result");
    }

    @Test
    public void checkRawDataLabelIsInitiallyHighlighted() {
        Label rawDataLabel = find("#rawDataRepresentation");
        verifyThat(rawDataLabel, l -> l.getStyleClass().get(0).equals("currentStageLabel"));
    }

    @Test
    public void checkAllOtherLabelsAreInitiallyNotHighlighted() {
        verifyThat("#normalization", l -> l.getStyleClass().isEmpty());
        verifyThat("#pAA", l -> l.getStyleClass().isEmpty());
        verifyThat("#discretization", l -> l.getStyleClass().isEmpty());
        verifyThat("#similarityMeasure", l -> l.getStyleClass().isEmpty());
        verifyThat("#kNNClassification", l -> l.getStyleClass().isEmpty());
        verifyThat("#result", l -> l.getStyleClass().isEmpty());
    }

    @Test
    public void testGUIStatusAfterClickingNextOnce() {
        clickOn("#nextButton");
        verifyThat("#rawDataRepresentation", l -> l.getStyleClass().get(0).equals("currentStageLabel"));
        verifyThat("#normalization", l -> l.getStyleClass().isEmpty());
        verifyThat("#pAA", l -> l.getStyleClass().isEmpty());
        verifyThat("#discretization", l -> l.getStyleClass().isEmpty());
        verifyThat("#similarityMeasure", l -> l.getStyleClass().isEmpty());
        verifyThat("#kNNClassification", l -> l.getStyleClass().isEmpty());
        verifyThat("#result", l -> l.getStyleClass().isEmpty());

        verifyThat("#prevButton", n -> !n.isDisabled());
        verifyThat("#nextButton", n -> !n.isDisabled());
        verifyThat("#exitButton", n -> !n.isDisabled());
    }

    @Test
    public void testTrainingDataStatusIsInitiallySetToZero() {
        Label trainingFileName = find("#trainingFileName");
        verifyThat(trainingFileName, n -> n.getText().equals("Name:"));

        Label trainingFileClassCount = find("#trainingFileClassCount");
        verifyThat(trainingFileClassCount, n -> n.getText().equals("0"));

        Label trainingFileDataSetSize = find("#trainingFileDataSetSize");
        verifyThat(trainingFileDataSetSize, n -> n.getText().equals("0"));

        Label trainingFileTimeSeriesLength = find("#trainingFileTimeSeriesLength");
        verifyThat(trainingFileTimeSeriesLength, n -> n.getText().equals("0"));
    }

}
