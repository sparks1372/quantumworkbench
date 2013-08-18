/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.core.Algorithms.Implementation.ExpArith;

import info.samratcliff.core.Algorithms.exp_node;

/**
 * @author Sam Ratcliff
 */
public class pidiv implements exp_node {
    private static final long serialVersionUID = 1103588697474932807L;
    private final exp_node child1;

    public pidiv(exp_node c1) {
        child1 = c1;
    }

    @Override
    public double evaluate(int sys_size, int[] loopvar) {
        double toReturn;
        toReturn = Math.PI
                / Math.pow(2, child1.evaluate(sys_size, loopvar) % 10);
        return toReturn;
    }

    private static final String OPENPARA = "pi/(";
    private static final String CLOSEPARA = ")";

    @Override
    public String toPrint() {
        StringBuffer sb = new StringBuffer(OPENPARA);
        sb.append(child1.toPrint());
        sb.append(CLOSEPARA);
        return sb.toString();
    }

    @Override
    public int val_sum() {
        return child1.val_sum();
    }

}
