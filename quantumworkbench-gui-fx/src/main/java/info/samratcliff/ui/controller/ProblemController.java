package info.samratcliff.ui.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.IOException;

public interface ProblemController {

    public ObservableList<String> getItems();

    public void continueWithCustomGateQuantity(Integer quantity)
            throws IOException;

    public void cancelCreateProblem();

    public void createProblemAction(ActionEvent event) throws IOException;

}
