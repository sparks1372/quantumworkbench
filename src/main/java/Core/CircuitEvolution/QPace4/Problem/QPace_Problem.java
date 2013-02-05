/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4.Problem;

import Core.CircuitEvaluator.Suitability;
import Core.CircuitEvaluator.CircuitEvaluator;
import Core.CircuitEvolution.QPace4.Data.QPaceData;
import Core.CircuitEvolution.QPace4.Individual.QPace_Ind;
import Core.CircuitEvolution.QPace4.State.QPaceEvoState;
import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.GPTree;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;

/**
 * @author Sam Ratcliff
 * 
 */
public class QPace_Problem extends GPProblem implements SimpleProblemForm {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2220504647580744551L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.simple.SimpleProblemForm#evaluate(ec.EvolutionState,
	 * ec.Individual, int, int)
	 */
	@Override
	public void evaluate(EvolutionState state, Individual ind,
			int subpopulation, int threadnum) {
		if (!ind.evaluated) { // don't bother reevaluating
			QPaceEvoState qpacestate = (QPaceEvoState) state;
			CircuitEvaluator evaluator = qpacestate.cir_evaluator;
			QPaceData input = new QPaceData();

			for (GPTree tree : ((QPace_Ind) ind).trees) {
				tree.child.eval(qpacestate, threadnum, input, stack,
						(GPIndividual) ind, this);
			}

			((QPace_Ind) ind).qa = input.qa;

			Suitability fit = evaluator.Evaluate(input.qa);

			// the fitness better be KozaFitness!
			KozaFitness f = ((KozaFitness) ind.fitness);
			f.setStandardizedFitness(state, (float) fit.getFitness());
			f.hits = fit.getPerfectcount();
			ind.evaluated = true;
			// KozaFitness test = new KozaFitness();
			// test.setStandardizedFitness(state, (float) fit.getFitness() * 2);
			// System.out.println(f.compareTo(test));

		} else {
			// System.out.println("Not evaluated");
		}
	}
}
