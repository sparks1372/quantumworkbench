package info.samratcliff.ui.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.IOException;

public interface CustomGateQuantityController {
    public ObservableList<Integer> getQuantites();

    public void okayAction(ActionEvent event) throws IOException;

    public void cancelAction(ActionEvent event);
}
