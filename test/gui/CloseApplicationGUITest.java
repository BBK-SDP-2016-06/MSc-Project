package gui;

import classificationApp.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.loadui.testfx.Assertions.assertNodeExists;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.GuiTest.find;

/**
 * Testing class for the CloseApplication gui aspect of the application.
 * Created by George Shiangoli on 15/08/2016.
 */
public class CloseApplicationGUITest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/CloseApplication.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @Test
    public void checkForAllControls() {
        assertNodeExists("#closeAppQuestion");
        Button yesButton = find("#yesButton");
        Button noButton = find("#noButton");
        verifyThat(yesButton, Button::isVisible);
        verifyThat(noButton, Button::isVisible);
    }
}
