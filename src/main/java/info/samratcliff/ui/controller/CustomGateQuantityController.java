package info.samratcliff.ui.controller;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public interface CustomGateQuantityController {
	public ObservableList<Integer> getQuantites();

	public void okayAction(ActionEvent event) throws IOException;

	public void cancelAction(ActionEvent event);
}
