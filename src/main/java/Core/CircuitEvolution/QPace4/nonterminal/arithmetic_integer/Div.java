/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package Core.CircuitEvolution.QPace4.nonterminal.arithmetic_integer;

import Core.Algorithms.exp_node;
import Core.Algorithms.Implementation.ExpArith.div;
import Core.Algorithms.ImplementationExpTerm.val;
import Core.CircuitEvolution.QPace4.Data.QPaceData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

public class Div extends GPNode {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8273582188336744142L;

	public Div() {
		System.out.println("Div Constructed");
	}

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

		try {
			if (c1Const && c2Const) {
				int t2 = (int) c2.evaluate(0, new int[0]);
				int val = (int) (t2 != 0 ? c1.evaluate(0, new int[0]) / t2 : t2);
				rd.ex = new val(val);
			} else {
				rd.ex = new div(c1, c2);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println(this.makeCTree(true, true, true));
		}
	}

	@Override
	public String toString() {
		return "/";
	}
}
