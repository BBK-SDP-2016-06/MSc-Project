package gui;


import classificationApp.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.loadui.testfx.Assertions.assertNodeExists;

/**
 * Testing class for the root BorderPane layout.
 * Created by George Shiangoli on 13/08/2016.
 */
public class RootLayoutTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @Test
    public void shouldHaveFileMenu() {
        assertNodeExists("#fileMenu");
    }

    @Test
    public void shouldHaveHelpMenu() {
        assertNodeExists("#helpMenu");
    }

    @Test
    public void changeModeAndCloseMenuItemsExistWithinFileMenu() {
        clickOn("#fileMenu");
        assertNodeExists("#changeMode");
        assertNodeExists("#close");
    }

    @Test
    public void aboutMenuItemExistsWithinHelpMenu() {
        clickOn("#helpMenu");
        assertNodeExists("#about");
    }
}
