package classificationApp;

import classificationApp.view.controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import classificationApp.model.io.TestFileReader;
import classificationApp.model.io.TrainingFileReader;

import java.io.IOException;

/**
 * This class represents the entry point for the application.
 * Created by George Shiangoli on 21/07/2016.
 */
public class MainApp extends Application {

    private TestFileReader testData;
    private TrainingFileReader trainingData;
    private Stage openingStage;
    private Stage mainStage;
    private BorderPane rootLayout;
    private String mode;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.openingStage = stage;
        this.openingStage.getIcons().add(new Image("file:resources/Icon.jpg"));
        this.openingStage.setOnCloseRequest(e -> {
            e.consume();
            closeApplication();
        });
        this.mainStage = new Stage();
        this.mainStage.getIcons().add(new Image("file:resources/Icon.jpg"));
        this.mainStage.setOnCloseRequest(e -> {
            e.consume();
            closeApplication();
        });
        showIntroduction();
    }

    public void showIntroduction() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Introduction.fxml"));
            AnchorPane layout = loader.load();
            openingStage.setTitle("MSc Project - Introduction");
            openingStage.setScene(new Scene(layout));
            openingStage.show();
            IntroductionController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTrainingDataLoader() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoadTraining.fxml"));
            AnchorPane layout = loader.load();
            openingStage.setTitle("MSc Project - Load Training Data");
            openingStage.setScene(new Scene(layout));
            openingStage.show();
            LoadTrainingController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showClassificationLayout() {
        mode = "Classification";
        initRootLayout();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Classification.fxml"));
            AnchorPane layout = loader.load();
            mainStage.setTitle("MSc Project - Classification Mode");
            rootLayout.setCenter(layout);
            ClassificationController controller = loader.getController();
            controller.setMainApp(this);
            controller.updateTrainingData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();
            mainStage.setScene(new Scene(rootLayout));
            mainStage.show();
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAnimationLayout() {
        mode = "Interactive";
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AnimationRoot.fxml"));
            AnchorPane layout = loader.load();
            mainStage.setTitle("MSc Project - Interactive Mode");
            rootLayout.setCenter(layout);
            AnimationRootController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeApplication() {
        try {
            Stage stage = ControllerUtils.getModalStage("Exit Application");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CloseApplication.fxml"));
            stage.setScene(new Scene(loader.load()));
            CloseApplicationController controller = loader.getController();
            controller.setStage(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TrainingFileReader getTrainingData() {
        return trainingData;
    }

    public void setTrainingData(TrainingFileReader trainingData) {
        this.trainingData = trainingData;
    }

    public void setTestData(TestFileReader testData) {
        this.testData = testData;
    }

    public TestFileReader getTestData() {
        return testData;
    }

    public Stage getOpeningStage() {
        return openingStage;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public String getMode() {
        return mode;
    }
}
