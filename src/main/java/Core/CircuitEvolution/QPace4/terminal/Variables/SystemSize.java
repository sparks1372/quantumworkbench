/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4.terminal.Variables;

import Core.Algorithms.ImplementationExpTerm.syssize;
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
public class SystemSize extends GPNode {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6934796916061751832L;
	public static final int	SYSTEM_SIZE_FLAG	= 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.gp.GPNode#eval(ec.EvolutionState, int, ec.gp.GPData,
	 * ec.gp.ADFStack, ec.gp.GPIndividual, ec.Problem)
	 */
	@Override
	public void eval(EvolutionState state, int thread, GPData input,
			ADFStack stack, GPIndividual individual, Problem problem) {
		((QPaceData) input).ex = new syssize();
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
