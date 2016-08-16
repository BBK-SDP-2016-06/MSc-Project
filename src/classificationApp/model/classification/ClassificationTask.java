package classificationApp.model.classification;

import classificationApp.model.data.DiscretizedData;
import classificationApp.model.data.DiscretizedDataImpl;
import classificationApp.model.data.TimeSeries;
import classificationApp.model.preprocessing.PreProcessor;
import classificationApp.view.controllers.ClassificationController;
import javafx.concurrent.Task;
import java.util.List;

/**
 * Runnable class for displaying classification process to text area.
 * Created by George Shiangoli on 09/08/2016.
 */
public class ClassificationTask extends Task<ClassificationResult> {

    private ClassificationController controller;
    private Classifier classifier;
    private PreProcessor preProcessor;
    private TimeSeries test;
    private List<DiscretizedData> train;

    public ClassificationTask(ClassificationController controller,
                              Classifier classifier,
                              PreProcessor preProcessor,
                              TimeSeries test,
                              List<DiscretizedData> train) {
        this.controller = controller;
        this.classifier = classifier;
        this.preProcessor = preProcessor;
        this.test = test;
        this.train = train;
    }

    @Override
    protected ClassificationResult call() throws Exception {
        DiscretizedData processedTest = new DiscretizedDataImpl(test.getClassType(), preProcessor.discretize(test));
        ClassificationResult result = classifier.classify(processedTest, train);
        result.setIndex(controller.getMainApp().getTestData().getDataSet().indexOf(test));
        return result;
    }
}
