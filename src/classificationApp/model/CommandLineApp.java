package classificationApp.model;

import classificationApp.model.classification.Classifier;
import classificationApp.model.classification.KNNClassifier;
import classificationApp.model.classification.LCSMeasure;
import classificationApp.model.data.DiscretizedData;
import classificationApp.model.data.DiscretizedDataImpl;
import classificationApp.model.io.TestFileReader;
import classificationApp.model.io.TestFileReaderImpl;
import classificationApp.model.io.TrainingFileReader;
import classificationApp.model.io.TrainingFileReaderImpl;
import classificationApp.model.math.MathUtils;
import classificationApp.model.preprocessing.PreProcessor;
import classificationApp.model.preprocessing.SAXPreProcessor;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Small application to obtain error rate results via the command line. The user is required to provide a time series
 * directory name and one of three parameters that is to be tested: alphabet size, frame count and k number. The
 * command line output is in a format that can be easily copied and pasted into a simple text application and saved
 * as a comma separated value file (*.csv).
 * Created by George Shiangoli on 24/08/2016.
 */
public class CommandLineApp {

    // Local absolute root of the UCR Time Series Data repository.
    // This will need to be changed to relative root to work on other machines
    private final static String ROOT = "C:\\Users\\George\\Documents\\Computer Science\\08 - MSc Project\\APP\\Data\\UCR_TS_Archive_2015";

    //Class to hold the test TimeSeries objects
    private TestFileReader test;

    //Class to hold the training TimeSeries objects
    private TrainingFileReader train;

    public static void main(String[] args) {
        CommandLineApp launcher = new CommandLineApp();
        launcher.launch();
    }

    private void launch() {

        // User provides time series data repository name
        System.out.print("Time Series Directory: ");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        // Asserts whether repository provided by user exists. If so initialises test and training FileReader fields.
        File directory = new File(ROOT + File.separator + input);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            assert files != null;
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.contains("TEST")) {
                    test = new TestFileReaderImpl(file.getAbsolutePath());
                } else if (fileName.contains("TRAIN")) {
                    train = new TrainingFileReaderImpl(file.getAbsolutePath());
                }
            }
        }

        // User provides parameter to be tested
        System.out.print("Test parameter type (a = alphabet size, f = frame count, k = k-value): ");
        String choice = scan.nextLine();

        switch (choice) {
            case "a":
                System.out.print("Frame count: ");
                int frame = scan.nextInt();
                System.out.print("k-value: ");
                int kValue = scan.nextInt();
                for (int a = 2; a < 27; a++) {
                    printErrorRates(kValue, frame, a, "alphabetSize");
                }
                break;
            case "f":
                System.out.print("Alphabet size: ");
                int alphabetSize = scan.nextInt();
                System.out.print("k-value: ");
                kValue = scan.nextInt();
                for (int f = 1; f <= test.getTimeSeriesLength().getLowerBound(); f++) {
                    printErrorRates(kValue, f, alphabetSize, "frameCount");
                }
                break;
            case "k":
                System.out.print("Alphabet size: ");
                alphabetSize = scan.nextInt();
                System.out.print("Frame count: ");
                frame = scan.nextInt();
                for (int k = 1; k <= train.getTimeSeriesCount(); k++) {
                    printErrorRates(k, frame, alphabetSize, "kValue");
                }
                break;
            default:
                System.out.println("Invalid input, try again");
                break;
        }
    }

    private void printErrorRates(int kValue, int frameCount, int alphabetSize, String parameter) {
        PreProcessor preProcessor = new SAXPreProcessor(frameCount, alphabetSize);
        assert test != null;
        List<DiscretizedData> processedTest = test.getDataSet().parallelStream()
                .map(data -> new DiscretizedDataImpl(data.getClassType(), preProcessor.discretize(data)))
                .collect(Collectors.toList());

        assert train != null;
        List<DiscretizedData> processedTrain = train.getDataSet().parallelStream()
                .map(data -> new DiscretizedDataImpl(data.getClassType(), preProcessor.discretize(data)))
                .collect(Collectors.toList());

        Classifier classifier = new KNNClassifier(kValue, new LCSMeasure());

        double errorRate = MathUtils.to5SF((double)processedTest.parallelStream().map(data -> classifier.classify(data, processedTrain)).filter(result -> !result.isCorrectPrediction()).count() / (double)processedTest.size());
        switch (parameter) {
            case "alphabetSize":
                System.out.println(alphabetSize + "," + errorRate);
                break;
            case "frameCount":
                System.out.println(frameCount + "," + errorRate);
                break;
            case "kValue":
                System.out.println(kValue + "," + errorRate);
                break;
        }
    }
}
