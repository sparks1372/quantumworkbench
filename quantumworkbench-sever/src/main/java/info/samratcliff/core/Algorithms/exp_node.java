/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.core.Algorithms;

import java.io.Serializable;

/**
 * @author Sam Ratcliff
 */
public interface exp_node extends Serializable {

    public double evaluate(int sys_size, int[] loopvar);

    public String toPrint();

    public int val_sum();

}
