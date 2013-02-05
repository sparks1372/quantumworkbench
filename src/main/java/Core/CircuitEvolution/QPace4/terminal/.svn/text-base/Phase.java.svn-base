/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4.terminal;

import Core.CircuitEvolution.QPace4.Data.QPaceData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

/**
 * @author Sam Ratcliff
 * 
 */
public class Phase extends GPNode {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7107417353085019008L;
	private Parameter			my_base;
	private static final String	INST_ID	= "Phase";
	private int					index;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.gp.GPNode#eval(ec.EvolutionState, int, ec.gp.GPData,
	 * ec.gp.ADFStack, ec.gp.GPIndividual, ec.Problem)
	 */
	@Override
	public void eval(EvolutionState state, int thread, GPData input,
			ADFStack stack, GPIndividual individual, Problem problem) {
		((QPaceData) input).i = index;
	}

	@Override
	public void setup(EvolutionState state, Parameter base) {
		super.setup(state, base);
		my_base = base;
		Parameter Contents_base = (my_base.push(INST_ID));
		index = state.parameters.getInt(Contents_base, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.gp.GPNode#toString()
	 */
	@Override
	public String toString() {
		return "Phase pi/" + index;
	}

}
