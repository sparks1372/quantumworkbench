/**
 * @Author = Sam Ratcliff
 */
package Core.Algorithms.ImplementationExpTerm;

import Core.Algorithms.exp_node;

/**
 * @author Sam Ratcliff
 * 
 */
public class val implements exp_node {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4041346175526464899L;
	private final double	value;

	/**
 * 
 */
	public val(double d) {
		value = d;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.Algorithms.exp_node#evaluate(int)
	 */
	@Override
	public double evaluate(int sys_size, int[] loopvar) {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.Algorithms.exp_node#toPrint()
	 */
	@Override
	public String toPrint() {
		return Double.toString(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.Algorithms.exp_node#val_sum()
	 */
	@Override
	public int val_sum() {
		return (int) value;
	}

}
