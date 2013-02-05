package Core.CircuitEvaluator;

import java.io.Serializable;

import Core.Algorithms.QuantumAlgorithm;
import Core.Circuit.Circuit;
import Jama.Matrix;

public interface SuitabilityMeasure extends Serializable {

	/**
	 * @param start_state
	 * @param final_state
	 * @param expected_state
	 * @param circuit
	 * @param algo
	 * @return
	 */
	public abstract Suitability evaluate(Matrix start_state, Matrix final_state,
			Matrix expected_state, Circuit circuit, QuantumAlgorithm algo);

	/**
	 * @return Returns the name.
	 * @uml.property name="Name" readOnly="true"
	 */
	public String getName();

}
