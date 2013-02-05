/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4.nonterminal;

import Core.Algorithms.Implementation.ExpArith.loopvar;
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
public class LoopVar extends GPNode {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8698960810548486083L;
	public static final int	LOOP_VARIABLE	= -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.gp.GPNode#eval(ec.EvolutionState, int, ec.gp.GPData,
	 * ec.gp.ADFStack, ec.gp.GPIndividual, ec.Problem)
	 */
	@Override
	public void eval(EvolutionState state, int thread, GPData input,
			ADFStack stack, GPIndividual individual, Problem problem) {
		children[0].eval(state, thread, input, stack, individual, problem);
		((QPaceData) input).ex = new loopvar(((QPaceData) input).ex);
		((QPaceData) input).exConst = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.gp.GPNode#toString()
	 */
	@Override
	public String toString() {
		return "";
	}

}
