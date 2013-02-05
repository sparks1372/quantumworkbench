package info.samratcliff.ui.controller;

import info.samratcliff.SEFxmlEngine;
import info.samratcliff.ui.model.ProblemModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.BoxBlur;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.inject.Inject;

public class ProblemControllerImpl implements ProblemController, Initializable {
	Logger log = LoggerFactory.getLogger("ProblemController");
	private final ProblemModel probModel;
	private final SEFxmlEngine application;
	private Stage customGateQuantityDialog;
	private Stage customProblemDialog;
	@FXML
	private ComboBox<String> problemselectioncombo;

	@FXML
	private Text problemselectiondescription;

	@Inject
	public ProblemControllerImpl(ProblemModel probModel,
			SEFxmlEngine application) throws IOException {
		super();
		this.probModel = probModel;
		this.application = application;
	}

	@Override
	@FXML
	public ObservableList<String> getItems() {
		return probModel.getItems();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		problemselectioncombo.getItems().setAll(getItems());
		// listen for changes to the fruit combo box selection and update the
		// displayed fruit image accordingly.
		Bindings.bindBidirectional(problemselectiondescription.textProperty(),
				probModel.getProblemDescription());
		problemselectioncombo.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(
							ObservableValue<? extends String> selected,
							String oldSelection, String newSelection) {
						probModel.setSelectedProblem(newSelection);
					}
				});
		problemselectioncombo.getSelectionModel().selectFirst();
	}

	@Override
	@FXML
	public void createProblemAction(ActionEvent event) throws IOException {
		if (customGateQuantityDialog != null
				&& customGateQuantityDialog.isShowing()) {
			throw new IllegalStateException(
					"Trying to create customGateQuantityDialog when it is not null or is still shown");
		}
		application.getMain().getScene().getRoot().setEffect(new BoxBlur());
		GuiceFXMLLoader loader = new GuiceFXMLLoader(application.getInjector());
		Parent root = loader.load(getClass().getClassLoader().getResource(
				"fxml/NumberOfCustomGatesDialog.fxml"));
		customGateQuantityDialog = new Stage(StageStyle.UNDECORATED);
		customGateQuantityDialog.initModality(Modality.APPLICATION_MODAL);
		customGateQuantityDialog.setScene(new Scene(root));
		customGateQuantityDialog.showAndWait();
		application.getMain().getScene().getRoot().setEffect(null);
	}

	@Override
	public void continueWithCustomGateQuantity(Integer quantity)
			throws IOException {
		log.info("Passed " + quantity + " quantity");
		GuiceFXMLLoader loader = new GuiceFXMLLoader(application.getInjector());
		Parent root = loader.load(getClass().getClassLoader().getResource(
				"fxml/CreateProblemDialog.fxml"));
		customProblemDialog = new Stage(StageStyle.UNDECORATED);
		customProblemDialog.initModality(Modality.APPLICATION_MODAL);
		customProblemDialog.setScene(new Scene(root));
		customProblemDialog.showAndWait();
		customGateQuantityDialog.close();
	}

	@Override
	public void cancelCreateProblem() {
		if (customGateQuantityDialog != null
				&& customGateQuantityDialog.isShowing())
			customGateQuantityDialog.close();
		if (customProblemDialog != null && customProblemDialog.isShowing())
			customProblemDialog.close();
	}

}
