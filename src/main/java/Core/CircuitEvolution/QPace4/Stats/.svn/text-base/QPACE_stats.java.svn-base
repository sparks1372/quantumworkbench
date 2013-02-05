/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4.Stats;

import Core.CircuitEvolution.QPace4.State.QPaceEvoState;
import ec.EvolutionState;
import ec.Individual;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleStatistics;

/**
 * @author Sam Ratcliff
 * 
 */
public class QPACE_stats extends SimpleStatistics {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2342784512119029234L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.simple.SimpleStatistics#finalStatistics(ec.EvolutionState, int)
	 */
	@Override
	public void finalStatistics(EvolutionState state, int result) {
		// super.finalStatistics(state, result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.Statistics#postBreedingStatistics(ec.EvolutionState)
	 */
	@Override
	public void postBreedingStatistics(EvolutionState state) {
		// super.postBreedingStatistics(state);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ec.simple.SimpleStatistics#postEvaluationStatistics(ec.EvolutionState)
	 */
	@Override
	public void postEvaluationStatistics(EvolutionState state) {
		// super.postEvaluationStatistics(state);
		Float avfit[] = new Float[state.population.subpops.length];
		int count;
		// for now we just print the best fitness per subpopulation.
		Individual[] best_i = new Individual[state.population.subpops.length]; // quiets
																				// compiler
																				// complaints
		for (int x = 0; x < state.population.subpops.length; x++) {
			count = 0;
			best_i[x] = state.population.subpops[x].individuals[0];
			for (int y = 1; y < state.population.subpops[x].individuals.length; y++) {
				if (state.population.subpops[x].individuals[y].fitness
						.betterThan(best_i[x].fitness)) {
					best_i[x] = state.population.subpops[x].individuals[y];
				}
				if (avfit[x] == null) {
					avfit[x] = ((KozaFitness) state.population.subpops[x].individuals[y].fitness)
							.adjustedFitness();
				} else {
					avfit[x] += ((KozaFitness) state.population.subpops[x].individuals[y].fitness)
							.adjustedFitness();

				}
				count++;
			}
			avfit[x] /= count;
			// now test to see if it's the new best_of_run
			if ((best_of_run[x] == null)
					|| best_i[x].fitness.betterThan(best_of_run[x].fitness)) {
				best_of_run[x] = (Individual) (best_i[x].clone());
			}
		}
		// print the best-of-generation individual
		// state.output
		// .println("\nGeneration: " + state.generation, statisticslog);
		// state.output.println("Best Individual:", statisticslog);
		for (int x = 0; x < state.population.subpops.length; x++) {

			// state.output.println("Subpopulation " + x + ":", statisticslog);

			state.output.message("Subpop " + x
					+ " best fitness of generation: "
					+ best_i[x].fitness.fitnessToStringForHumans());
			best_i[x].evaluated = false;

			state.output.message("Subpop " + x
					+ " mean fitness of generation: " + avfit[x]);
			if (((QPaceEvoState) state).getStatsPanel() != null) {
				((QPaceEvoState) state).getStatsPanel().setFitness(
						((KozaFitness) best_of_run[x].fitness)
								.standardizedFitness());
			}
		}

		((QPaceEvoState) state).setGens(state.generation);
		((QPaceEvoState) state).incProgressBar();
		if (((QPaceEvoState) state).getStatsPanel() != null) {
			((QPaceEvoState) state).getStatsPanel().setGens(state.generation);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.Statistics#preBreedingStatistics(ec.EvolutionState)
	 */
	@Override
	public void preBreedingStatistics(EvolutionState state) {
		// TODO Auto-generated method stub
		// super.preBreedingStatistics(state);
	}
}
