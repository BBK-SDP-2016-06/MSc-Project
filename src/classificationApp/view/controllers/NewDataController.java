package classificationApp.view.controllers;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

/**
 * Abstract class to represent all new data controllers.
 * Created by George Shiangoli on 06/08/2016.
 */
public abstract class NewDataController {

    protected Stage dialogueStage;

    public void setDialogueStage(Stage dialogueStage) {
        this.dialogueStage = dialogueStage;
    }

    protected File getSelectedFile() {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fc.getExtensionFilters().addAll(txtFilter, csvFilter);
        return fc.showSaveDialog(dialogueStage);
    }
}
