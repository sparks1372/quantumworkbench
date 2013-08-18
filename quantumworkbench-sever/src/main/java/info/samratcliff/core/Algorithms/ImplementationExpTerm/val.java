/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.core.Algorithms.ImplementationExpTerm;

import info.samratcliff.core.Algorithms.exp_node;

/**
 * @author Sam Ratcliff
 */
public class val implements exp_node {
    private static final long serialVersionUID = -4041346175526464899L;
    private final double value;

    public val(double d) {
        value = d;
    }

    @Override
    public double evaluate(int sys_size, int[] loopvar) {
        return value;
    }

    @Override
    public String toPrint() {
        return Double.toString(value);
    }

    @Override
    public int val_sum() {
        return (int) value;
    }

}
