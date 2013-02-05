package Core;

import java.util.Observable;

import Core.CircuitBuilder.CircuitBuilder;
import Core.CircuitEvaluator.CircuitEvaluator;
import Core.CircuitEvaluator.SuitabilityMeasure;
import Core.CircuitEvaluator.SuitabilityMeasureManager;
import Core.CircuitEvolution.circuitsearchengine;
import Core.CircuitEvolution.searchenginemanager;
import Core.Problem.ProblemManager;
import Core.Problem.quantumproblem;

import com.google.inject.Inject;

/**
 * @uml.dependency supplier="Core.CircuitBuilder.circuitBuilder"
 */
public class qcevolutionbackend extends Observable {

	protected CircuitBuilder cirbui;

	@Inject
	public qcevolutionbackend(CircuitBuilder cirbui, CircuitEvaluator cireval,
			ProblemManager probmanager, searchenginemanager semanager,
			SuitabilityMeasureManager ffmanager) {
		super();
		this.cirbui = cirbui;
		this.cireval = cireval;
		this.probmanager = probmanager;
		this.semanager = semanager;
		this.ffmanager = ffmanager;
	}

	protected CircuitEvaluator cireval;
	private final ProblemManager probmanager;
	private final searchenginemanager semanager;
	private circuitsearchengine currentse;
	private final SuitabilityMeasureManager ffmanager;

	public CircuitBuilder getCirbui() {
		return cirbui;
	}

	public CircuitEvaluator getCireval() {
		return cireval;
	}

	public SuitabilityMeasure getCurrentff() {
		return cireval.getQfitnessfunction();
	}

	public circuitsearchengine getCurrentse() {
		return currentse;
	}

	public SuitabilityMeasureManager getFfmanager() {
		return ffmanager;
	}

	public ProblemManager getProbmanager() {
		return probmanager;
	}

	public quantumproblem getQproblem() {
		return cireval.getQproblem();
	}

	public searchenginemanager getSemanager() {
		return semanager;
	}

	private void seinit() {
		if (currentse != null) {
			currentse.removeAllObservers();
			currentse.init(getCirbui(), getCireval());
		}
		setChanged();
		super.notifyObservers(this);
	}

	public void setCirbui(CircuitBuilder cirbui) {
		this.cirbui = cirbui;
		seinit();
	}

	public void setCireval(CircuitEvaluator cireval) {
		this.cireval = cireval;
		seinit();
	}

	public void setCurrentff(SuitabilityMeasure currentff) {
		cireval.setQfitnessfunction(currentff);
		seinit();
	}

	public void setCurrentse(circuitsearchengine currentse) {
		this.currentse = currentse;
		seinit();
	}

	public void setQproblem(quantumproblem qproblem) {
		cireval.setQproblem(qproblem);
		setChanged();
		notifyObservers(this);
	}

}
