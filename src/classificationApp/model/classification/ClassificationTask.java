package classificationApp.model.classification;

import classificationApp.model.data.DiscretizedData;
import classificationApp.model.data.DiscretizedDataImpl;
import classificationApp.model.data.TimeSeries;
import classificationApp.model.preprocessing.PreProcessor;
import classificationApp.view.controllers.ClassificationController;
import javafx.concurrent.Task;
import java.util.List;

import lombok.AllArgsConstructor;

/**
 * Runnable class for carrying out the classification process and
 * displaying the classification result to the text area of the
 * main application GUI.
 * Created by George Shiangoli on 09/08/2016.
 */

@AllArgsConstructor
public class ClassificationTask extends Task<ClassificationResult> {

    private ClassificationController controller;
    private Classifier classifier;
    private PreProcessor preProcessor;
    private TimeSeries test;
    private List<DiscretizedData> train;

    @Override
    protected ClassificationResult call() throws Exception {
        DiscretizedData processedTest = new DiscretizedDataImpl(test.getClassType(), preProcessor.discretize(test));
        ClassificationResult result = classifier.classify(processedTest, train);
        result.setIndex(controller.getMainApp().getTestData().getDataSet().indexOf(test));
        return result;
    }
}
