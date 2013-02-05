/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4.nonterminal;

import Core.Algorithms.QuantumAlgorithm;
import Core.Algorithms.QuantumInstructionEnum;
import Core.Algorithms.Implementation.basicquantumalgorithm;
import Core.CircuitEvolution.QPace4.Data.QPaceData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

/**
 * @author Sam Ratcliff
 * 
 */
public class Control extends GPNode {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6503501703546975575L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.gp.GPNode#eval(ec.EvolutionState, int, ec.gp.GPData,
	 * ec.gp.ADFStack, ec.gp.GPIndividual, ec.Problem)
	 */
	@Override
	public void eval(EvolutionState state, int thread, GPData input,
			ADFStack stack, GPIndividual individual, Problem problem) {
		QPaceData rd = ((QPaceData) (input));

		// Produce the Quantum Instruction
		children[0].eval(state, thread, input, stack, individual, problem);
		QuantumAlgorithm[] subalg;
		QPaceData secinput;

		subalg = new basicquantumalgorithm[children.length - 1];
		for (int index = 0; index < subalg.length; index++) {
			secinput = new QPaceData();
			// Produce the subalgorithm
			children[index + 1].eval(state, thread, secinput, stack,
					individual, problem);
			subalg[index] = secinput.qa != null ? secinput.qa
					: new basicquantumalgorithm();
			if (index == 0) {
				rd.ex = secinput.ex;
			}
		}
		rd.qa.addInstruction(QuantumInstructionEnum.Body, null, null, null,
				subalg);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.gp.GPNode#toString()
	 */
	@Override
	public String toString() {
		return "Body";
	}

}
