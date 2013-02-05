/*
 * Create by Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4.EvolutionOperators.Mutation;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.koza.MutationPipeline;

public class ReplacementPipeline extends MutationPipeline {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3859400422891077314L;

	@Override
	public int produce(final int min, final int max, final int start,
			final int subpopulation, final Individual[] inds,
			final EvolutionState state, final int thread) {

		// grab individuals from our source and stick 'em right into inds.
		// we'll modify them from there
		int n = sources[0].produce(min, max, start, subpopulation, inds, state,
				thread);

		// // should we bother?
		if (!state.random[thread].nextBoolean(likelihood)) {
			return reproduce(n, start, subpopulation, inds, state, thread,
					false); // DON'T produce children from source -- we already
							// did
		}

		// now let's mutate 'em
		for (int q = start; q < n + start; q++) {
			inds[q] = state.population.subpops[subpopulation].species
					.newIndividual(state, thread);
		}
		return n;

	}
}
