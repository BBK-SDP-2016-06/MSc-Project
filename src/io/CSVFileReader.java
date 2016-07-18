package io;

/**
 * Charged with the handling of reading in the TEST and TRAINING files
 * used for classification purposes. Valid files that can be read include
 * .csv and .txt files. An attempt to open and read from a file that isn't
 * of the aforementioned types result in an error.
 * Created by George Shiangoli on 18/07/2016.
 */
public interface CSVFileReader {

    /**
     * Retrieves the number of lines within the file that is being read. This
     * should equate to the number of time series data records present in the file.
     * @return the number of lines in this file
     */
    int getLineCount();

    /**
     * Closes the file being read.
     */
    void close();
}
