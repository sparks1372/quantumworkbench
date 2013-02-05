package info.samratcliff.ui.controller;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import Core.CircuitEvaluator.SuitabilityMeasureManager;

import com.google.inject.Inject;

public class FitnessFunctionControllerImpl implements
		FitnessFunctionController, Initializable {

	private final SuitabilityMeasureManager smm;
	@FXML
	private ComboBox<String> fitnessfunctionselectioncombo;

	@FXML
	private Text fitnessfunctionselectiondescription;

	@Inject
	public FitnessFunctionControllerImpl(SuitabilityMeasureManager smm) {
		super();
		this.smm = smm;
	}

	@Override
	@FXML
	public ObservableList<String> getItems() {
		return FXCollections.observableArrayList(smm
				.getAvailableFitnessFunctions());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fitnessfunctionselectioncombo.getItems().setAll(getItems());
		// listen for changes to the fruit combo box selection and update the
		// displayed fruit image accordingly.
		fitnessfunctionselectioncombo.getSelectionModel()
				.selectedItemProperty()
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(
							ObservableValue<? extends String> selected,
							String oldSelection, String newSelection) {
						fitnessfunctionselectiondescription.setText(smm
								.getFitnessFunctionDesc(newSelection));
					}
				});
	}

}
