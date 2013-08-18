/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.core.Algorithms.Implementation.ExpArith;

import info.samratcliff.core.Algorithms.exp_node;

/**
 * @author Sam Ratcliff
 */
public class loopvar implements exp_node {
    private static final long serialVersionUID = 4920171370053452182L;
    private final exp_node child1;

    public loopvar(exp_node c1) {
        child1 = c1;
    }

    @Override
    public double evaluate(int sys_size, int[] loopvar) {
        if (loopvar.length > 0) {
            return loopvar[Math.abs((int) child1.evaluate(sys_size, loopvar))
                    % loopvar.length];
        } else {
            return 0;
        }
    }

    private static final String OPEN = "Loop_Vars[";
    private static final String CLOSE = "]";

    @Override
    public String toPrint() {
        StringBuffer sb = new StringBuffer(OPEN);
        sb.append(child1.toPrint());
        sb.append(CLOSE);
        return sb.toString();
    }

    @Override
    public int val_sum() {
        return child1.val_sum();
    }

}
