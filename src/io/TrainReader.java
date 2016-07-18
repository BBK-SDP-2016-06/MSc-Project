package io;

import java.io.File;

/**
 *
 * Created by George Shiangoli on 18/07/2016.
 */
public class TrainReader implements CSVFileReader {

    private File trainingFile;

    public TrainReader(String filePath) {}

    @Override
    public int getLineCount() {
        return 0;
    }
}
