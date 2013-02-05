/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package Core.CircuitEvolution.QPace4.terminal;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import Core.Algorithms.ImplementationExpTerm.val;
import Core.CircuitEvolution.QPace4.Data.QPaceData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.ERC;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Code;
import ec.util.DecodeReturn;

/* 
 * LawnERC.java
 * 
 * Created: Wed Nov  3 18:26:37 1999
 * By: Sean Luke
 */

/**
 * @author Sean Luke
 * @version 1.0
 */

public class QubitERC extends ERC {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5965155317096177010L;

	public int	maxx	= 3 + 2;	// -2, -1 and 1-10

	public int	q;

	@Override
	public boolean decode(DecodeReturn dret) {
		// store the position and the string in case they
		// get modified by Code.java
		int pos = dret.pos;
		String data = dret.data;

		// decode
		Code.decode(dret);

		if (dret.type != DecodeReturn.T_INT) // uh oh!
		{
			// restore the position and the string; it was an error
			dret.data = data;
			dret.pos = pos;
			return false;
		}

		// store the data
		q = (int) (dret.l);

		return true;
	}

	@Override
	public String encode() {
		return Code.encode(q);
	}

	@Override
	public void eval(final EvolutionState state, final int thread,
			final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		QPaceData rd = ((QPaceData) (input));
		rd.ex = new val(q < 3 ? q - 3 : q - 2);
	}

	@Override
	public boolean nodeEquals(final GPNode node) {
		// check first to see if we're the same kind of ERC --
		// won't work for subclasses; in that case you'll need
		// to change this to isAssignableTo(...)
		if (this.getClass() != node.getClass()) {
			return false;
		}
		// now check to see if the ERCs hold the same value
		QubitERC n = (QubitERC) node;
		return (n.q == q);
	}

	@Override
	public int nodeHashCode() {
		// a reasonable hash code
		return this.getClass().hashCode() + q;
	}

	@Override
	public void readNode(final EvolutionState state, final DataInput dataInput)
			throws IOException {
		q = dataInput.readInt();
	}

	@Override
	public void resetNode(final EvolutionState state, final int thread) {
		q = state.random[thread].nextInt(maxx) + 1;
	}

	@Override
	public String toStringForHumans() {
		return "[" + q + "]";
	}

	@Override
	public void writeNode(final EvolutionState state,
			final DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(q);
	}
}
