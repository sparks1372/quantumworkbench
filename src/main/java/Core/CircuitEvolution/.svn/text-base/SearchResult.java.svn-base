/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution;

import java.io.Serializable;

import Core.Algorithms.QuantumAlgorithm;

/**
 * @author Sam Ratcliff
 * 
 */
public class SearchResult implements Serializable {
	/**
	 * 
	 */
	private static final long		serialVersionUID	= -2338602361960864439L;
	private final QuantumAlgorithm	qa;
	private final float				suitability;

	public SearchResult(QuantumAlgorithm qa, float suitability) {
		super();
		this.qa = qa;
		this.suitability = suitability;
	}

	/**
	 * @return the fitness
	 */
	public float getSuitability() {
		return suitability;
	}

	/**
	 * @return the qa
	 */
	public QuantumAlgorithm getQa() {
		return qa;
	}

}
