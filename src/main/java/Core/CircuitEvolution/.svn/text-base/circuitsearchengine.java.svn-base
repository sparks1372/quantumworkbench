package Core.CircuitEvolution;

import java.util.Observer;

import javax.swing.JDialog;
import javax.swing.JPanel;

import Core.CircuitBuilder.circuitBuilder;
import Core.CircuitEvaluator.CircuitEvaluator;

/**
 * @uml.dependency supplier="Core.CircuitBuilder.circuitBuilder"
 */
public abstract interface circuitsearchengine {

	public void addObserver(Observer ob);

	/**
	 * @return Returns the availableinstructions.
	 * @uml.property name="availableinstructions" multiplicity="(0 -1)"
	 *               dimension="1"
	 */
	public boolean[] getAvailableinstructions();

	/**
	 * @return Returns the cbuilder.
	 * @uml.property name="cbuilder" readOnly="true"
	 */
	public circuitBuilder getCbuilder();

	/**
	 * @return Returns the cevaluator.
	 * @uml.property name="cevaluator" readOnly="true"
	 */
	public CircuitEvaluator getCevaluator();

	/**
	 * @return Returns the name.
	 * @uml.property name="Name" readOnly="true"
	 */
	public String getName();

	public JPanel getProgressBar();

	/**
	 * @return Returns the Search Results.
	 * @uml.property name="SearchResult" readOnly="true"
	 */
	public SearchResult[] getResults();

	public JPanel getSearchStatisticsPanel();

	public SearchEngineState getState();

	/**
	 * @return
	 */
	public JDialog getStatsDialog();

	public void init(circuitBuilder cb, CircuitEvaluator ce);

	/**
	 * 
	 */
	public void removeAllObservers();

	public void removeObserver(Observer ob);

	/**
	 * Initiate search for use with a GUI
	 */
	public void search();

	/**
	 * Initiate search for use without a GUI
	 * 
	 * @return
	 */
	public boolean search(boolean[] availableinstructions, Object[] params);

}
