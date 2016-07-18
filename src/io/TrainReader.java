package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads a training file that is selected by the user.
 * Created by George Shiangoli on 18/07/2016.
 */
public class TrainReader implements CSVFileReader {

    private BufferedReader reader;

    public TrainReader(String filePath) {
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLineCount() {
        return (int)reader.lines().count();
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
