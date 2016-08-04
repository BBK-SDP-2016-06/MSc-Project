package classificationApp.model.data;

import classificationApp.model.exception.DataExceptionHandler;

/**
 * Implementation of the DiscretizedData interface. Provides a concrete class
 * to hold information about individual classificationApp.model.data approximations and their class type.
 * A class type -1 is represents an unlabelled test approximation.
 * Created by George Shiangoli on 28/07/2016.
 */
public class DiscretizedDataImpl implements DiscretizedData {

    private int classType;
    private String word;

    public DiscretizedDataImpl(int classType, String word) {
        DataExceptionHandler.validateDiscretizedData(classType, word);
        setClassType(classType);
        setWord(word);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getClassType() {
        return classType;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getWord() {
        return word;
    }

    private void setClassType(int classType) {
        this.classType = classType;
    }

    private void setWord(String word) {
        this.word = word;
    }
}
