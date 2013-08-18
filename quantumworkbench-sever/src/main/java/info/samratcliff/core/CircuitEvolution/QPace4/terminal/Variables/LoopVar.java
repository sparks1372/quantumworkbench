/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.core.CircuitEvolution.QPace4.terminal.Variables;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import info.samratcliff.core.CircuitEvolution.QPace4.Data.QPaceData;

/**
 * @author Sam Ratcliff
 */
public class LoopVar extends GPNode {
    /**
     *
     */
    private static final long serialVersionUID = -5086336695739735991L;
    public static final int LOOP_VARIABLE = -2;

    /*
     * (non-Javadoc)
     *
     * @see ec.gp.GPNode#eval(ec.EvolutionState, int, ec.gp.GPData,
     * ec.gp.ADFStack, ec.gp.GPIndividual, ec.Problem)
     */
    @Override
    public void eval(EvolutionState state, int thread, GPData input,
                     ADFStack stack, GPIndividual individual, Problem problem) {
        ((QPaceData) input).i = LOOP_VARIABLE;

    }

    /*
     * (non-Javadoc)
     *
     * @see ec.gp.GPNode#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "";
    }

}
