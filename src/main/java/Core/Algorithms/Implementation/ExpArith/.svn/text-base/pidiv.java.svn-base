/**
 * @Author = Sam Ratcliff
 */
package Core.Algorithms.Implementation.ExpArith;

import Core.Algorithms.exp_node;

/**
 * @author Sam Ratcliff
 * 
 */
public class pidiv implements exp_node {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1103588697474932807L;
	private final exp_node		child1;

	/**
	 * 
	 */
	public pidiv(exp_node c1) {
		child1 = c1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.Algorithms.exp_node#evaluate(int)
	 */
	@Override
	public double evaluate(int sys_size, int[] loopvar) {
		double toReturn;
		toReturn = Math.PI
				/ Math.pow(2, child1.evaluate(sys_size, loopvar) % 10);
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.Algorithms.exp_node#toPrint()
	 */
	@Override
	public String toPrint() {
		return "pi/(" + child1.toPrint() + ")";
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
