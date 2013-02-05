/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4;

import Core.Algorithms.QuantumAlgorithm;
import Core.CircuitEvolution.SearchResult;

/**
 * @author Sam Ratcliff
 * 
 */
public class QPaceSearchResult extends SearchResult {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4291680190624574683L;
	private final long			timeinmillis;
	private final int			finalGenNum;

	/**
	 * @param qa
	 * @param fitness
	 */
	public QPaceSearchResult(QuantumAlgorithm qa, float fitness,
			long timeinmillis, int finalGenNum) {
		super(qa, fitness);
		this.timeinmillis = timeinmillis;
		this.finalGenNum = finalGenNum;
	}

	/**
	 * @return the finalGenNum
	 */
	public int getFinalGenNum() {
		return finalGenNum;
	}

	/**
	 * @return the timeinmillis
	 */
	public long getTimeinmillis() {
		return timeinmillis;
	}

}
