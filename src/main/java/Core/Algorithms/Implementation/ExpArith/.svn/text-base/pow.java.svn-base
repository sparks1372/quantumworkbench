/**
 * @Author = Sam Ratcliff
 */
package Core.Algorithms.Implementation.ExpArith;

import Core.Algorithms.exp_node;

/**
 * @author Sam Ratcliff
 * 
 */
public class pow implements exp_node {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7428229072725781502L;
	private final exp_node	child1;
	private final exp_node	child2;

	/**
	 * 
	 */
	public pow(exp_node c1, exp_node c2) {
		child1 = c1;
		child2 = c2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.Algorithms.exp_node#evaluate(int)
	 */
	@Override
	public double evaluate(int sys_size, int[] loopvar) {
		return (int) Math.pow(child1.evaluate(sys_size, loopvar),
				child2.evaluate(sys_size, loopvar));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.Algorithms.exp_node#toPrint()
	 */
	@Override
	public String toPrint() {
		return "(" + child1.toPrint() + ")^(" + child2.toPrint() + ")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.Algorithms.exp_node#val_sum()
	 */
	@Override
	public int val_sum() {
		return child1.val_sum() + child2.val_sum();
	}

}
