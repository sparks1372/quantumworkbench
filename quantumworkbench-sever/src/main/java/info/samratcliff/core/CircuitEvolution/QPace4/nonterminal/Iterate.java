/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.core.CircuitEvolution.QPace4.nonterminal;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import info.samratcliff.core.Algorithms.Implementation.basicquantumalgorithm;
import info.samratcliff.core.Algorithms.QuantumAlgorithm;
import info.samratcliff.core.Algorithms.QuantumInstructionEnum;
import info.samratcliff.core.Algorithms.exp_node;
import info.samratcliff.core.CircuitEvolution.QPace4.Data.QPaceData;

/**
 * @author Sam Ratcliff
 */
public class Iterate extends GPNode {
    /**
     *
     */
    private static final long serialVersionUID = -2009759438539440430L;

    public Iterate() {
        System.out.println("Control Constructed");
    }

    /*
     * (non-Javadoc)
     *
     * @see ec.gp.GPNode#eval(ec.EvolutionState, int, ec.gp.GPData,
     * ec.gp.ADFStack, ec.gp.GPIndividual, ec.Problem)
     */
    @Override
    public void eval(EvolutionState state, int thread, GPData input,
                     ADFStack stack, GPIndividual individual, Problem problem) {
        exp_node int1;
        QPaceData rd = ((QPaceData) (input));

        // Produce the Quantum Instruction
        QuantumAlgorithm[] subalg;
        QPaceData secinput;
        // Produce the Number of Iteration
        children[1].eval(state, thread, input, stack, individual, problem);
        int1 = rd.ex;

        subalg = new basicquantumalgorithm[1];
        secinput = new QPaceData();
        // Produce the subalgorithm
        children[4].eval(state, thread, secinput, stack, individual, problem);

        subalg[0] = secinput.qa != null ? secinput.qa
                : new basicquantumalgorithm();

        rd.qa.addInstruction(QuantumInstructionEnum.Iterate, int1, null, null,
                subalg);

    }

    /*
     * (non-Javadoc)
     *
     * @see ec.gp.GPNode#toString()
     */
    @Override
    public String toString() {
        return "Iterate";
    }

}
