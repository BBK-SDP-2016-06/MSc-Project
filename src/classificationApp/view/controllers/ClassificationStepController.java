package classificationApp.view.controllers;

/**
 * Abstract class representing the group of controllers that handle the
 * various layouts involved in the classification model's walk-through mode.
 * Created by George Shiangoli on 17/08/2016.
 */
public abstract class ClassificationStepController {

    protected AnimationRootController controller;

    public void setController(AnimationRootController controller) {
        this.controller = controller;
    }

}
