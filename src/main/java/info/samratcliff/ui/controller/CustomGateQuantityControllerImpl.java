package info.samratcliff.ui.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class CustomGateQuantityControllerImpl implements
		CustomGateQuantityController, Initializable {
	Logger log = LoggerFactory.getLogger("ProblemController");
	@FXML
	private ComboBox<String> customgatenumberlist;

	private final ProblemController pc;

	@Inject
	public CustomGateQuantityControllerImpl(ProblemController pc) {
		super();
		this.pc = pc;
	}

	@Override
	public ObservableList<Integer> getQuantites() {
		return FXCollections.observableArrayList(0, 1, 2, 3);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		customgatenumberlist.getItems().setAll(
				Lists.transform(getQuantites(),
						new Function<Integer, String>() {
							@Override
							public String apply(Integer input) {
								return input.toString();
							}
						}));
	}

	@Override
	public void okayAction(ActionEvent event) throws IOException {
		if (customgatenumberlist.getSelectionModel().selectedItemProperty()
				.getValue() != null) {
			Integer customGateCount = Integer.parseInt(customgatenumberlist
					.getSelectionModel().selectedItemProperty().getValue()
					.toString());
			pc.continueWithCustomGateQuantity(customGateCount);
		} else {
			log.error("Quantity has not been selected");
		}
	}

	@Override
	public void cancelAction(ActionEvent event) {
		pc.cancelCreateProblem();
	}
}
