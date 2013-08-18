/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.core.Algorithms.Implementation.ExpArith;

import info.samratcliff.core.Algorithms.exp_node;

/**
 * @author Sam Ratcliff
 */
public class pow implements exp_node {
    private static final long serialVersionUID = -7428229072725781502L;
    private final exp_node child1;
    private final exp_node child2;

    public pow(exp_node c1, exp_node c2) {
        child1 = c1;
        child2 = c2;
    }

    @Override
    public double evaluate(int sys_size, int[] loopvar) {
        return (int) Math.pow(child1.evaluate(sys_size, loopvar),
                child2.evaluate(sys_size, loopvar));
    }

    private static final String OPENPARA = "(";
    private static final String CLOSEPARA = ")";
    private static final String OPERATOR = "^";

    @Override
    public String toPrint() {
        StringBuffer sb = new StringBuffer(OPENPARA);
        sb.append(child1.toPrint());
        sb.append(CLOSEPARA);
        sb.append(OPERATOR);
        sb.append(OPENPARA);
        sb.append(child2.toPrint());
        sb.append(CLOSEPARA);
        return sb.toString();
    }

    @Override
    public int val_sum() {
        return child1.val_sum() + child2.val_sum();
    }

}
