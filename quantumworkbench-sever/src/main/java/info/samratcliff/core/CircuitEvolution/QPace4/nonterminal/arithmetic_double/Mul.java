/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package info.samratcliff.core.CircuitEvolution.QPace4.nonterminal.arithmetic_double;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;
import info.samratcliff.core.Algorithms.Implementation.ExpArith.mul;
import info.samratcliff.core.Algorithms.ImplementationExpTerm.val;
import info.samratcliff.core.Algorithms.exp_node;
import info.samratcliff.core.CircuitEvolution.QPace4.Data.QPaceData;

public class Mul extends GPNode {
    /**
     *
     */
    private static final long serialVersionUID = 6965964974826026348L;

    @Override
    public void checkConstraints(final EvolutionState state, final int tree,
                                 final GPIndividual typicalIndividual, final Parameter individualBase) {
        super.checkConstraints(state, tree, typicalIndividual, individualBase);
        if (children.length != 2) {
            state.output.error("Incorrect number of children for node "
                    + toStringForError() + " at " + individualBase);
        }
    }

    @Override
    public void eval(final EvolutionState state, final int thread,
                     final GPData input, final ADFStack stack,
                     final GPIndividual individual, final Problem problem) {
        exp_node c1;
        exp_node c2;
        boolean c1Const;
        boolean c2Const;
        QPaceData rd = ((QPaceData) (input));

        rd.exConst = true;
        children[0].eval(state, thread, input, stack, individual, problem);
        c1 = rd.ex;
        c1Const = rd.exConst;

        rd.exConst = true;
        children[1].eval(state, thread, input, stack, individual, problem);
        c2 = rd.ex;
        c2Const = rd.exConst;

        if (c1Const && c2Const) {
            rd.ex = new val(c1.evaluate(0, new int[0])
                    * c2.evaluate(0, new int[0]));
        } else {
            rd.ex = new mul(c1, c2);
        }
    }

    @Override
    public String toString() {
        return "*";
    }
}
