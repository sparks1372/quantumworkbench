/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.core.CircuitEvolution.QPace4.State;

import ec.simple.SimpleEvolutionState;
import info.samratcliff.core.CircuitBuilder.CircuitBuilder;
import info.samratcliff.core.CircuitEvaluator.CircuitEvaluator;

import java.io.Serializable;

/**
 * @author Sam Ratcliff
 */
public class QPaceEvoState extends SimpleEvolutionState implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 704242492846236496L;
    public CircuitBuilder cir_builder;
    public CircuitEvaluator cir_evaluator;
    private int gens;

    /**
     * @return the gens
     */
    public int getGens() {
        return gens;
    }

    /**
     * @param gens the gens to set
     */
    public void setGens(int gens) {
        this.gens = gens;
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
