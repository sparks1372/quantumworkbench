package info.samratcliff.ui.model;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Core.Problem.ProblemManager;
import Core.Problem.quantumproblem;

import com.google.inject.Inject;

public class ProblemModel {
	private final ProblemManager probManager;
	private final QuantumProblemProperty qp;
	private final ObservableList<String> availableProblems;

	@Inject
	public ProblemModel(ProblemManager probmanager, QuantumProblemProperty qp) {
		super();
		this.qp = qp;
		this.probManager = probmanager;
		availableProblems = FXCollections.observableArrayList(probManager
				.getAvailableProblems());
	}

	public ObservableList<String> getItems() {
		return availableProblems;
	}

	public StringProperty getProblemDescription() {
		return qp.getDescriptionProperty();
	}

	public quantumproblem getSelectedProblem() {
		return qp.getValue();
	}

	public void setSelectedProblem(String newSelection) {
		qp.setValue(probManager.getProblem(newSelection));
	}

	public void createProblem(String name, String description, String fileName) {
		probManager.addProblem(name, fileName, description);
		availableProblems.setAll(probManager.getAvailableProblems());
	}

}
