package classificationApp.model.data;

import classificationApp.model.exception.DataExceptionHandler;

import java.util.Optional;

/**
 * Implementation of the DiscretizedData interface. Provides a concrete class
 * to hold information about individual classificationApp.model.data approximations and their class type.
 * A class type -1 is represents an unlabelled test approximation.
 * Created by George Shiangoli on 28/07/2016.
 */
public class DiscretizedDataImpl implements DiscretizedData {

    private Optional<Integer> classType;
    private String word;

    public DiscretizedDataImpl(Optional<Integer> classType, String word) {
        DataExceptionHandler.validateDiscretizedData(word);
        setClassType(classType);
        setWord(word);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Integer> getClassType() {
        return classType;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getWord() {
        return word;
    }

    private void setClassType(Optional<Integer> classType) {
        this.classType = classType;
    }

    private void setWord(String word) {
        this.word = word;
    }
}
