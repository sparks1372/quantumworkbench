/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.core.CircuitEvolution.QPace4.Data;

import ec.gp.GPData;
import info.samratcliff.core.Algorithms.Implementation.basicquantumalgorithm;
import info.samratcliff.core.Algorithms.QuantumAlgorithm;
import info.samratcliff.core.Algorithms.exp_node;

/**
 * @author Sam Ratcliff
 */
public class QPaceData extends GPData {

    /**
     *
     */
    private static final long serialVersionUID = 7571587128230597512L;
    public QuantumAlgorithm qa = new basicquantumalgorithm();
    public int i;
    public double d;
    public exp_node ex = null;
    public boolean exConst = true;

    /*
     * (non-Javadoc)
     *
     * @see ec.gp.GPData#copyTo(ec.gp.GPData)
     */
    @Override
    public void copyTo(GPData gpd) {
        ((QPaceData) gpd).qa = qa;
        ((QPaceData) gpd).i = i;
        ((QPaceData) gpd).d = d;
        ((QPaceData) gpd).ex = ex;
        ((QPaceData) gpd).exConst = exConst;
    }

}
