/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4.Data;

import Core.Algorithms.QuantumAlgorithm;
import Core.Algorithms.exp_node;
import Core.Algorithms.Implementation.basicquantumalgorithm;
import ec.gp.GPData;

/**
 * @author Sam Ratcliff
 * 
 */
public class QPaceData extends GPData {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7571587128230597512L;
	public QuantumAlgorithm	qa		= new basicquantumalgorithm();
	public int				i;
	public double			d;
	public exp_node			ex		= null;
	public boolean			exConst	= true;

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
