package classificationApp.model.classification;

import classificationApp.model.data.DiscretizedData;
import classificationApp.model.data.DiscretizedDataImpl;
import classificationApp.model.data.TimeSeries;
import classificationApp.model.preprocessing.PreProcessor;
import classificationApp.view.ClassificationController;
import javafx.concurrent.Task;
import java.util.List;

/**
 * Runnable class for displaying classification process to text area.
 * Created by George Shiangoli on 09/08/2016.
 */
public class ClassificationPrinter extends Task<String> {

    private ClassificationController controller;
    private Classifier classifier;
    private PreProcessor preProcessor;
    private TimeSeries test;
    private List<DiscretizedData> train;

    public ClassificationPrinter(ClassificationController controller,
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

    private String buildClassificationOutput(ClassificationResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("Index: ").append(controller.getMainApp().getTestData().getDataSet().indexOf(test));
        sb.append("\nLength: ").append(test.getDataSize());
        sb.append("\n").append(controller.getKValue()).append("NN result:");
        sb.append("\n\n" + "Rank\t\tClass\tSimilarity");
        for (NeighbourDistance nd : result.getNeighbourDistances()) {
            sb.append("\n")
                    .append(result.getNeighbourDistances().indexOf(nd))
                    .append("\t\t")
                    .append(nd.getClassType())
                    .append("\t\t")
                    .append(nd.getDistance());
        }
        sb.append("\n\nActual class: ").append(test.getClassType());
        sb.append("\nPredicted class: ").append(result.getClassType());

        if (test.getClassType() != -1) {
            sb.append(result.getClassType() == test.getClassType() ? "\nCORRECT" : "\nINCORRECT");
        }
        sb.append("\n--------------------------------------\n");
        return sb.toString();
    }

    @Override
    protected String call() throws Exception {
        DiscretizedData processedTest = new DiscretizedDataImpl(test.getClassType(), preProcessor.discretize(test));
        ClassificationResult result = classifier.classify(processedTest, train);
        return buildClassificationOutput(result);
    }
}
