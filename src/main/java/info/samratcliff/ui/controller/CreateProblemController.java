package info.samratcliff.ui.controller;

import java.io.IOException;

import javafx.event.ActionEvent;

public interface CreateProblemController {

	public void chooseFileAction(ActionEvent event) throws IOException;

	public void okayAction(ActionEvent event) throws IOException;

	public void cancelAction(ActionEvent event);

	public void addTestSet(ActionEvent event);
}
