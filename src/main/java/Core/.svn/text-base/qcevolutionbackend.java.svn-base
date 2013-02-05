package Core;

import java.util.Observable;

import Core.CircuitBuilder.circuitBuilder;
import Core.CircuitEvaluator.SuitabilityMeasure;
import Core.CircuitEvaluator.CircuitEvaluator;
import Core.CircuitEvaluator.SuitabilityMeasureManager;
import Core.CircuitEvolution.circuitsearchengine;
import Core.CircuitEvolution.searchenginemanager;
import Core.Problem.Problem_Manager;
import Core.Problem.quantumproblem;

/**
 * @uml.dependency supplier="Core.CircuitBuilder.circuitBuilder"
 */
public class qcevolutionbackend extends Observable {

	/**
	 * @uml.property name="circuitBuilder"
	 * @uml.associationEnd multiplicity="(0 -1)" dimension="1" ordering="true"
	 *                     inverse=
	 *                     "qcevolutionbackend:Core.CircuitBuilder.circuitBuilder"
	 */

	// public static void main(String[] args) {
	//
	// qcevolutionbackend be = new qcevolutionbackend();
	//
	// circuitBuilder cirbui = new basiccircuitbuilder();
	// circuitevaluator cireval = new basiccircuitevaluator(cirbui);
	// FitnessFunction ff = (new PhaseSensitiveParsimoniousSimpleFitness(
	// "PPSF"));
	// // cireval.setQfitnessfunction(new ParsimoniousSimpleFitness());
	// // cireval.setQfitnessfunction(new SimpleFitness());
	// // cireval.setQproblem(be.getQproblem());
	// boolean[] temp = new boolean[QuantumInstructionEnum.values().length];
	// for (int i = 0; i < temp.length; i++) {
	// if (// (i == QuantumInstructionEnum.Create_Zero.ordinal())
	// // || (i == QuantumInstructionEnum.Create_CRX.ordinal())
	// // || (i == QuantumInstructionEnum.Create_CRY.ordinal())
	// // || (i == QuantumInstructionEnum.Create_CRZ.ordinal())
	// // || (i == QuantumInstructionEnum.Create_RX.ordinal())
	// // || (i == QuantumInstructionEnum.Create_RY.ordinal())
	// // || (i == QuantumInstructionEnum.Create_RZ.ordinal())
	// /* || */(i == QuantumInstructionEnum.Create_P.ordinal())) {
	// temp[i] = false;
	// } else {
	// temp[i] = true;
	// }
	// }
	// be.setCurrentse(new QPace4_Imp());
	// be.setCirbui(cirbui);
	// be.setCireval(cireval);
	// be.setCurrentff(ff);
	// be.setQproblem(new simpleqcproblem("Hadamard"));
	// test_UML_parser tup = new test_UML_parser(args[0]);
	// be.getQproblem().setTestSuite(tup.parse());
	//
	// be.getCurrentse().getEvolveDialog().setVisible(true);
	// }

	protected circuitBuilder		cirbui;
	protected CircuitEvaluator		cireval;

	/**
	 * @uml.property name="probmanager"
	 */
	private Problem_Manager			probmanager;
	/**
	 * @uml.property name="semanager"
	 */
	private searchenginemanager		semanager;

	/**
	 * @uml.property name="currentse"
	 */
	private circuitsearchengine		currentse;

	/**
	 * @uml.property name="ffmanager"
	 */
	private SuitabilityMeasureManager	ffmanager;

	/**
	 * @return the cirbui
	 */
	public circuitBuilder getCirbui() {
		return cirbui;
	}

	/**
	 * @return the cireval
	 */
	public CircuitEvaluator getCireval() {
		return cireval;
	}

	public SuitabilityMeasure getCurrentff() {
		return cireval.getQfitnessfunction();
	}

	/**
	 * Getter of the property <tt>currentse</tt>
	 * 
	 * @return Returns the currentse.
	 * @uml.property name="currentse"
	 */
	public circuitsearchengine getCurrentse() {
		return currentse;
	}

	/**
	 * Getter of the property <tt>ffmanager</tt>
	 * 
	 * @return Returns the ffmanager.
	 * @uml.property name="ffmanager"
	 */
	public SuitabilityMeasureManager getFfmanager() {
		return ffmanager;
	}

	/**
	 * Getter of the property <tt>probmanager</tt>
	 * 
	 * @return Returns the probmanager.
	 * @uml.property name="probmanager"
	 */
	public Problem_Manager getProbmanager() {
		return probmanager;
	}

	/**
	 * Getter of the property <tt>qproblem</tt>
	 * 
	 * @return Returns the qproblem.
	 * @uml.property name="qproblem"
	 */
	public quantumproblem getQproblem() {
		return cireval.getQproblem();
	}

	/**
	 * Getter of the property <tt>semanager</tt>
	 * 
	 * @return Returns the semanager.
	 * @uml.property name="semanager"
	 */
	public searchenginemanager getSemanager() {
		return semanager;
	}

	private void seinit() {
		if (currentse != null) {
			currentse.removeAllObservers();
			currentse.init(getCirbui(), getCireval());
			setChanged();
			super.notifyObservers(this);
		}
	}

	/**
	 * @param cirbui
	 *            the cirbui to set
	 */
	public void setCirbui(circuitBuilder cirbui) {
		this.cirbui = cirbui;
		seinit();
	}

	/**
	 * @param cireval
	 *            the cireval to set
	 */
	public void setCireval(CircuitEvaluator cireval) {
		this.cireval = cireval;
		seinit();
	}

	public void setCurrentff(SuitabilityMeasure currentff) {
		cireval.setQfitnessfunction(currentff);
		seinit();
	}

	/**
	 * Setter of the property <tt>currentse</tt>
	 * 
	 * @param currentse
	 *            The currentse to set.
	 * @uml.property name="currentse"
	 */
	public void setCurrentse(circuitsearchengine currentse) {
		this.currentse = currentse;
		seinit();
	}

	/**
	 * Setter of the property <tt>ffmanager</tt>
	 * 
	 * @param ffmanager
	 *            The ffmanager to set.
	 * @uml.property name="ffmanager"
	 */
	public void setFfmanager(SuitabilityMeasureManager ffmanager) {
		this.ffmanager = ffmanager;
	}

	/**
	 * Setter of the property <tt>probmanager</tt>
	 * 
	 * @param probmanager
	 *            The probmanager to set.
	 * @uml.property name="probmanager"
	 */
	public void setProbmanager(Problem_Manager probmanager) {
		this.probmanager = probmanager;
	}

	public void setQproblem(quantumproblem qproblem) {
		cireval.setQproblem(qproblem);
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Setter of the property <tt>semanager</tt>
	 * 
	 * @param semanager
	 *            The semanager to set.
	 * @uml.property name="semanager"
	 */
	public void setSemanager(searchenginemanager semanager) {
		this.semanager = semanager;
	}

}
