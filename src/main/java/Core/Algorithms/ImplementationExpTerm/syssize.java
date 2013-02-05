/**
 * @Author = Sam Ratcliff
 */
package Core.Algorithms.ImplementationExpTerm;

import Core.Algorithms.exp_node;

/**
 * @author Sam Ratcliff
 * 
 */
public class syssize implements exp_node {

	private static final long serialVersionUID = 4039344166927957436L;

	@Override
	public double evaluate(int sys_size, int[] loopvar) {
		return sys_size;
	}

	@Override
	public String toPrint() {
		return "System_Size";
	}

	@Override
	public int val_sum() {
		return 0;
	}

}
