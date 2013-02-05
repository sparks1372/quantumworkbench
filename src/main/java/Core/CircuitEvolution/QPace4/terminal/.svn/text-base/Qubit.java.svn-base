/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4.terminal;

import Core.Algorithms.ImplementationExpTerm.val;
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
public class Qubit extends GPNode {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5815319518334002709L;
	private Parameter			my_base;
	private static final String	INST_ID	= "Qubit";
	private int					value;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.gp.GPNode#eval(ec.EvolutionState, int, ec.gp.GPData,
	 * ec.gp.ADFStack, ec.gp.GPIndividual, ec.Problem)
	 */
	@Override
	public void eval(EvolutionState state, int thread, GPData input,
			ADFStack stack, GPIndividual individual, Problem problem) {
		((QPaceData) input).ex = new val(value);
	}

	@Override
	public void setup(EvolutionState state, Parameter base) {
		super.setup(state, base);
		my_base = base;
		Parameter Contents_base = (my_base.push(INST_ID));
		value = state.parameters.getInt(Contents_base, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.gp.GPNode#toString()
	 */
	@Override
	public String toString() {
		return "Qubit " + value;
	}

}
