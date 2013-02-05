/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4.State;

import java.io.Serializable;

import Core.CircuitBuilder.circuitBuilder;
import Core.CircuitEvaluator.CircuitEvaluator;
import Core.CircuitEvolution.QPace4.StatsPanel;
import ec.simple.SimpleEvolutionState;

/**
 * @author Sam Ratcliff
 * 
 */
public class QPaceEvoState extends SimpleEvolutionState implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 704242492846236496L;
	public circuitBuilder		cir_builder;
	public CircuitEvaluator		cir_evaluator;
	private int					gens;

	private StatsPanel			statsPanel;

	/**
	 * @return the gens
	 */
	public int getGens() {
		return gens;
	}

	public StatsPanel getStatsPanel() {
		return statsPanel;
	}

	public void incProgressBar() {
		if ((statsPanel != null) && (statsPanel.getProgressBar() != null)) {
			statsPanel.getProgressBar().setValue(
					statsPanel.getProgressBar().getValue() + 1);
		}
	}

	/**
	 * @param gens
	 *            the gens to set
	 */
	public void setGens(int gens) {
		this.gens = gens;
	}

	public void setStatsPanel(StatsPanel statsPanel2) {
		statsPanel = statsPanel2;
	}

	@Override
	public void startFresh() {
		output.message("Setting up");
		setup(this, null); // a garbage Parameter
		// resetProgressBar();

		// POPULATION INITIALIZATION
		output.message("Initializing Generation 0");
		statistics.preInitializationStatistics(this);
		population = initializer.initialPopulation(this, 0); // unthreaded
		statistics.postInitializationStatistics(this);

		// INITIALIZE CONTACTS -- done after initialization to allow
		// a hook for the user to do things in Initializer before
		// an attempt is made to connect to island models etc.
		exchanger.initializeContacts(this);
		evaluator.initializeContacts(this);
	}

}
