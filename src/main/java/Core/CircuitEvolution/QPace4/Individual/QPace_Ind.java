/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4.Individual;

import Core.Algorithms.QuantumAlgorithm;
import Core.Circuit.Circuit;
import Core.CircuitEvolution.QPace4.State.QPaceEvoState;
import ec.EvolutionState;
import ec.gp.GPIndividual;
import ec.util.Code;

/**
 * @author Sam Ratcliff
 * 
 */
public class QPace_Ind extends GPIndividual {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8850922428738610284L;
	public QuantumAlgorithm		qa;

	@Override
	public void printIndividualForHumans(final EvolutionState state,
			final int log) {
		state.output.println(EVALUATED_PREAMBLE + Code.encode(evaluated), log);
		String quanalgstring = qa.print();

		Circuit cirq1 = ((QPaceEvoState) state).cir_builder.Build(qa, 1);
		Circuit cirq2 = ((QPaceEvoState) state).cir_builder.Build(qa, 2);
		Circuit cirq3 = ((QPaceEvoState) state).cir_builder.Build(qa, 3);

		String cir1lat = cirq1.toLatex(1);
		String cir2lat = cirq2.toLatex(2);
		String cir3lat = cirq3.toLatex(3);
		state.output.println(quanalgstring, log);
		state.output.println(cir1lat, log);
		state.output.println(cir2lat, log);
		state.output.println(cir3lat, log);
		fitness.printFitnessForHumans(state, log);
		// state.output.println(genotypeToStringForHumans(), log);
	}
}
