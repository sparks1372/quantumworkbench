package info.samratcliff.ui.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public interface CreateProblemController {

    public void chooseFileAction(ActionEvent event) throws IOException;

    public void okayAction(ActionEvent event) throws IOException;

    public void cancelAction(ActionEvent event);

    public void addTestSet(ActionEvent event);
}
