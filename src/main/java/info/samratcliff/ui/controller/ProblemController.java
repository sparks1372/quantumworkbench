package info.samratcliff.ui.controller;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public interface ProblemController {

	public ObservableList<String> getItems();

	public void continueWithCustomGateQuantity(Integer quantity)
			throws IOException;

	public void cancelCreateProblem();

	public void createProblemAction(ActionEvent event) throws IOException;

}
