/**
 * @Author = Sam Ratcliff
 */
package Core.Algorithms.Implementation.ExpArith;

import Core.Algorithms.exp_node;

/**
 * @author Sam Ratcliff
 * 
 */
public class loopvar implements exp_node {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4920171370053452182L;
	private final exp_node		child1;

	/**
 * 
 */
	public loopvar(exp_node c1) {
		child1 = c1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.Algorithms.exp_node#evaluate(int)
	 */
	@Override
	public double evaluate(int sys_size, int[] loopvar) {
		if (loopvar.length > 0) {
			return loopvar[Math.abs((int) child1.evaluate(sys_size, loopvar))
					% loopvar.length];
		} else {
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.Algorithms.exp_node#toPrint()
	 */
	@Override
	public String toPrint() {
		return "Loop_Vars[" + child1.toPrint() + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.Algorithms.exp_node#val_sum()
	 */
	@Override
	public int val_sum() {
		return child1.val_sum();
	}

}
